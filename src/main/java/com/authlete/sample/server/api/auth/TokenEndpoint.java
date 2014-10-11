/*
 * Copyright 2014 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.authlete.sample.server.api.auth;


import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.authlete.common.api.AuthleteApiException;
import com.authlete.common.dto.TokenRequest;
import com.authlete.common.dto.TokenResponse;
import com.authlete.common.dto.TokenResponse.Action;
import com.authlete.common.web.BasicCredentials;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.util.ResponseUtil;
import com.authlete.sample.server.web.BaseEndpoint;


/**
 * The implementation of OAuth 2.0 token endpoint with
 * OpenID Connect extension.
 */
@Path("/api/auth/token")
public class TokenEndpoint extends BaseEndpoint
{
    private static final String PATH = "/api/auth/token";


    /**
     * The value for {@code WWW-Authenticate} header.
     */
    private static final String CHALLENGE = "Basic realm=\"/api/auth/token\"";


    public TokenEndpoint()
    {
        super(PATH);
    }


    /**
     * The token endpoint for {@code POST} method.
     *
     * <p>
     * <a href="http://tools.ietf.org/html/rfc6749#section-3.2">RFC 6749,
     * 3.2. Token Endpoint</a> says:
     * </p>
     *
     * <blockquote>
     * <i>The client MUST use the HTTP "POST" method when making access token requests.</i>
     * </blockquote>
     *
     * <p>
     * <a href="http://tools.ietf.org/html/rfc6749#section-2.3">RFC 6749,
     * 2.3. Client Authentication</a> mentions (1) HTTP Basic Authentication
     * and (2) {@code client_id} &amp; {@code client_secret} parameters in
     * the request body as the means of client authentication. This example
     * supports the both means.
     * </p>
     *
     * @see <a href="http://tools.ietf.org/html/rfc6749#section-3.2"
     *      >RFC 6749, 3.2. Token Endpoint</a>
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String auth,
            String parameters)
    {
        // Convert the value of Authorization header (credentials of
        // the client application), if any, into BasicCredentials.
        BasicCredentials credentials = BasicCredentials.parse(auth);

        // The credentials of the client application extracted from
        // 'Authorization' header. These may be null.
        String clientId     = credentials == null ? null : credentials.getUserId();
        String clientSecret = credentials == null ? null : credentials.getPassword();

        // Process the given parameters.
        return process(parameters, clientId, clientSecret);
    }


    /**
     * Process the parameters of the token request.
     */
    private Response process(String parameters, String clientId, String clientSecret)
    {
        // Call Authlete's /auth/token API.
        TokenResponse response = callAuthleteTokenApi(parameters, clientId, clientSecret);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        Action action = response.getAction();

        // The content of the response to the client application.
        String content = response.getResponseContent();

        // Dispatch according to the action.
        switch (action)
        {
            case INVALID_CLIENT:
                // 401 Unauthorized
                return ResponseUtil.unauthorized(content, CHALLENGE);

            case INTERNAL_SERVER_ERROR:
                // 500 Internal Server Error
                return ResponseUtil.internalServerError(content);

            case BAD_REQUEST:
                // 400 Bad Request
                return ResponseUtil.badRequest(content);

            case PASSWORD:
                // Process the token request whose flow is "Resource Owner Password Credentials".
                return handlePassword(response);

            case OK:
                // 200 OK
                return ResponseUtil.ok(content);

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/token} API.
     */
    private TokenResponse callAuthleteTokenApi(String parameters, String clientId, String clientSecret)
    {
        // Create a request for Authlete's /auth/token API.
        TokenRequest request = new TokenRequest()
            .setParameters(parameters)
            .setClientId(clientId)
            .setClientSecret(clientSecret);

        // Response from the API.
        TokenResponse response;

        try
        {
            // Call Authlete's /auth/token API.
            response = Authlete.getApi().token(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/token failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/token response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/token API.
        return response;
    }


    /**
     * Process the token request whose flow is "Resource Owner Password Credentials".
     */
    private Response handlePassword(TokenResponse response)
    {
        try
        {
            // Delegate the processing to PasswordHandler.
            return new PasswordHandler(response).handle();
        }
        catch (WebApplicationException e)
        {
            return e.getResponse();
        }
    }
}
