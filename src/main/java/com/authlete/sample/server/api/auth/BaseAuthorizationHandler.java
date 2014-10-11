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


import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.authlete.common.api.AuthleteApiException;
import com.authlete.common.dto.AuthorizationFailRequest;
import com.authlete.common.dto.AuthorizationFailRequest.Reason;
import com.authlete.common.dto.AuthorizationFailResponse;
import com.authlete.common.dto.AuthorizationIssueRequest;
import com.authlete.common.dto.AuthorizationIssueResponse;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.util.ResponseUtil;


/**
 * The base class for handlers which are used in the implementation
 * of the authorization endpoint.
 */
abstract class BaseAuthorizationHandler extends BaseHandler
{
    /**
     * Create an exception that describes the failure. This method
     * calls Authlete's {@code /auth/authorization/fail} API.
     */
    protected WebApplicationException fail(String ticket, Reason reason)
    {
        // Create a response to the client application with the help of
        // Authlete's /auth/authorization/fail API.
        Response response = createFailureResponse(ticket, reason);

        // Create an exception containing the response.
        return new WebApplicationException(response);
    }


    /**
     * Create a response that describes the failure. This method
     * calls Authlete's {@code /auth/authorization/fail} API.
     */
    private Response createFailureResponse(String ticket, Reason reason)
    {
        // Call Authlete's /auth/authorization/fail API.
        AuthorizationFailResponse response = callAuthleteAuthorizationFailApi(ticket, reason);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        AuthorizationFailResponse.Action action = response.getAction();

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

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/authorization/fail} API.
     */
    private AuthorizationFailResponse callAuthleteAuthorizationFailApi(String ticket, Reason reason)
    {
        // Create a request for /auth/authorization/fail API.
        AuthorizationFailRequest request = new AuthorizationFailRequest()
            .setTicket(ticket)
            .setReason(reason);

        // Response from the API.
        AuthorizationFailResponse response;

        try
        {
            // Call Authlete's /auth/authorization/fail API.
            response = Authlete.getApi().authorizationFail(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/authorization/fail failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/authorization/fail response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/authorization/fail API.
        return response;
    }


    /**
     * Issue an authorization code, an ID token and/or an access token.
     * This method calls Authlete's {@code /auth/authorization/issue} API.
     */
    protected Response issue(AuthorizationIssueRequest request)
    {
        // Call Authlete's /auth/authorization/issue API.
        AuthorizationIssueResponse response = callAuthleteAuthorizationIssueApi(request);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        AuthorizationIssueResponse.Action action = response.getAction();

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

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/authorization/issue} API.
     */
    private AuthorizationIssueResponse callAuthleteAuthorizationIssueApi(AuthorizationIssueRequest request)
    {
        // Response from the API.
        AuthorizationIssueResponse response;

        try
        {
            // Call Authlete's /auth/authorization/issue API.
            response = Authlete.getApi().authorizationIssue(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/authorization/issue failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/authorization/issue response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/authorization/issue API.
        return response;
    }
}
