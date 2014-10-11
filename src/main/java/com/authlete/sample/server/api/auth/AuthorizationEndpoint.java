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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.authlete.common.api.AuthleteApiException;
import com.authlete.common.dto.AuthorizationRequest;
import com.authlete.common.dto.AuthorizationResponse;
import com.authlete.common.dto.AuthorizationResponse.Action;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.util.ResponseUtil;
import com.authlete.sample.server.web.BaseEndpoint;


/**
 * The implementation of OAuth 2.0 authorization endpoint with
 * OpenID Connect extension.
 */
@Path("/api/auth/authorization")
public class AuthorizationEndpoint extends BaseEndpoint
{
    private static final String PATH = "/api/auth/authorization";


    public AuthorizationEndpoint()
    {
        super(PATH);
    }


    /**
     * The authorization endpoint for {@code GET} method.
     *
     * <p>
     * <a href="http://tools.ietf.org/html/rfc6749#section-3.1">RFC 6749,
     * 3.1 Authorization Endpoint</a> says that the authorization endpoint
     * MUST support {@code GET} method.
     * </p>
     *
     * @see <a href="http://tools.ietf.org/html/rfc6749#section-3.1"
     *      >RFC 6749, 3.1 Authorization Endpoint</a>
     */
    @GET
    public Response get(@Context HttpServletRequest request, @Context UriInfo uriInfo)
    {
        // The query parameters contained in the authorization request.
        String parameters = uriInfo.getRequestUri().getQuery();

        // Process the given parameters.
        return process(request, parameters);
    }


    /**
     * The authorization endpoint for {@code POST} method.
     *
     * <p>
     * <a href="http://tools.ietf.org/html/rfc6749#section-3.1">RFC 6749,
     * 3.1 Authorization Endpoint</a> says that the authorization endpoint
     * MAY support {@code POST} method.
     * </p>
     *
     * <p>
     * In addition, <a href="http://openid.net/specs/openid-connect-core-1_0.html#AuthRequest"
     * >OpenID Connect Core 1.0, 3.1.2.1. Authentication Request</a> says
     * that the authorization endpoint MUST support {@code POST} method.
     * </p>
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(@Context HttpServletRequest request, String parameters)
    {
        // Process the given parameters.
        return process(request, parameters);
    }


    /**
     * Process the parameters of the authorization request.
     */
    private Response process(HttpServletRequest request, String parameters)
    {
        // Call Authlete's /auth/authorization API.
        AuthorizationResponse response = callAuthleteAuthorizationApi(parameters);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        Action action = response.getAction();

        // The content of the response to the client application.
        // The format of the content varies depending on the action.
        String content = response.getResponseContent();

        // Dispatch according to the action.
        switch (action)
        {
            case INTERNAL_SERVER_ERROR:
                // 500 Internal Server Error
                return ResponseUtil.internalServerError(content);

            case BAD_REQUEST:
                // 400 Bad Request
                return ResponseUtil.badRequest(content);

            case LOCATION:
                // 302 Found
                return ResponseUtil.location(content);

            case FORM:
                // 200 OK
                return ResponseUtil.form(content);

            case NO_INTERACTION:
                // Process the authorization request without user interaction.
                return handleNoInteraction(response);

            case INTERACTION:
                // Process the authorization request with user interaction.
                return handleInteraction(request.getSession(), response);

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/authorization} API.
     */
    private AuthorizationResponse callAuthleteAuthorizationApi(String parameters)
    {
        // Create a request for Authlete's /auth/authorization API.
        AuthorizationRequest request = new AuthorizationRequest().setParameters(parameters);

        // Response from the API.
        AuthorizationResponse response;

        try
        {
            // Call Authlete's /auth/authorization API.
            response = Authlete.getApi().authorization(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/authorization failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/authorization response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/authorization API.
        return response;
    }


    /**
     * Process the authorization request without user interaction.
     */
    private Response handleNoInteraction(AuthorizationResponse response)
    {
        try
        {
            // Delegate the processing to NoInteractionHandler.
            return new NoInteractionHandler(response).handle();
        }
        catch (WebApplicationException e)
        {
            return e.getResponse();
        }
    }


    /**
     * Process the authorization request with user interaction.
     */
    private Response handleInteraction(HttpSession session, AuthorizationResponse response)
    {
        try
        {
            // Delegate the processing to InteractionHandler.
            return new InteractionHandler(session, response).handle();
        }
        catch (WebApplicationException e)
        {
            return e.getResponse();
        }
    }
}
