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
import com.authlete.common.dto.TokenFailRequest;
import com.authlete.common.dto.TokenFailRequest.Reason;
import com.authlete.common.dto.TokenFailResponse;
import com.authlete.common.dto.TokenIssueRequest;
import com.authlete.common.dto.TokenIssueResponse;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.util.ResponseUtil;


/**
 * The base class for handlers which are used in the implementation
 * of the token endpoint.
 */
abstract class BaseTokenHandler extends BaseHandler
{
    /**
     * Create an exception that describes the failure. This method
     * calls Authlete's {@code /auth/token/fail} API.
     */
    protected WebApplicationException fail(String ticket, Reason reason)
    {
        // Create a response to the client application with the help of
        // Authlete's /auth/token/fail API.
        Response response = createFailureResponse(ticket, reason);

        // Create an exception containing the response.
        return new WebApplicationException(response);
    }


    /**
     * Create a response that describes the failure. This method
     * calls Authlete's {@code /auth/token/fail} API.
     */
    private Response createFailureResponse(String ticket, Reason reason)
    {
        // Call Authlete's /auth/token/fail API.
        TokenFailResponse response = callAuthleteTokenFailApi(ticket, reason);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        TokenFailResponse.Action action = response.getAction();

        // The content of the response to the client application.
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

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/token/fail} API.
     */
    private TokenFailResponse callAuthleteTokenFailApi(String ticket, Reason reason)
    {
        // Create a request for /auth/token/fail API.
        TokenFailRequest request = new TokenFailRequest()
            .setTicket(ticket)
            .setReason(reason);

        // Response from the API.
        TokenFailResponse response;

        try
        {
            // Call Authlete's /auth/token/fail API.
            response = Authlete.getApi().tokenFail(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/token/fail failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/token/fail response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/token/fail API.
        return response;
    }


    /**
     * Issue an access token and optionally an ID token.
     * This method calls Authlete's {@code /auth/token/issue} API.
     */
    protected Response issue(String ticket)
    {
        // Call Authlete's /auth/token/issue API.
        TokenIssueResponse response = callAuthleteTokenIssueApi(ticket);

        // 'action' in the response denotes the next action which
        // this service implementation should take.
        TokenIssueResponse.Action action = response.getAction();

        // The content of the response to the client application.
        String content = response.getResponseContent();

        // Dispatch according to the action.
        switch (action)
        {
            case INTERNAL_SERVER_ERROR:
                // 500 Internal Server Error
                return ResponseUtil.internalServerError(content);

            case OK:
                // 200 OK
                return ResponseUtil.ok(content);

            default:
                // This never happens.
                throw new InternalServerErrorException("Unknown action");
        }
    }


    /**
     * Call Authlete's {@code /auth/token/issue} API.
     */
    private TokenIssueResponse callAuthleteTokenIssueApi(String ticket)
    {
        // Create a request for Authlete's /auth/token/issue API.
        TokenIssueRequest request = new TokenIssueRequest().setTicket(ticket);

        // Response from the API.
        TokenIssueResponse response;

        try
        {
            // Call Authlete's /auth/token/issue API.
            response = Authlete.getApi().tokenIssue(request);
        }
        catch (AuthleteApiException e)
        {
            // The API call failed.
            error("Authlete /auth/token/issue failed: %s", e.getMessage());

            // 500 Internal Server Error
            throw new InternalServerErrorException(e);
        }

        // Print the result just for debugging.
        debug("Authlete /auth/token/issue response: <%s> %s",
                response.getAction(),
                response.getResultMessage());

        // Return the response from Authlete's /auth/token/issue API.
        return response;
    }
}
