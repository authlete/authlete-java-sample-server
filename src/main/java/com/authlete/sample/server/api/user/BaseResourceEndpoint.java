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
package com.authlete.sample.server.api.user;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.authlete.common.api.AuthleteApiException;
import com.authlete.common.dto.IntrospectionRequest;
import com.authlete.common.dto.IntrospectionResponse;
import com.authlete.common.web.BearerToken;
import com.authlete.sample.server.authlete.Authlete;
import com.authlete.sample.server.database.entity.Lockable;
import com.authlete.sample.server.database.entity.dao.ProfileDao;
import com.authlete.sample.server.util.ApiException;
import com.authlete.sample.server.util.AppContext;
import com.authlete.sample.server.util.RequestValidator;
import com.authlete.sample.server.web.BaseEndpoint;


abstract public class BaseResourceEndpoint extends BaseEndpoint
{
    /**
     * The alias for access token owner's user id.<br>
     * For example, if your user id is "joe" and the URI of the protected resource
     * to access your feed information is "http://your.service.com/api/{user_id}/feed",
     * you can get the information by making a request to "http://your.service.com/api/me/feed"
     * (as well as "http://your.service.com/api/joe/feed").
     */
    private static final String TOKEN_OWNER_ID_ALIAS = "me";


    private static final ProfileDao PROFILE_DAO = AppContext.getBean("profileDao");


    protected BaseResourceEndpoint(String path)
    {
        super(path);
    }


    protected Response process(String accessToken, Object... args)
    {
        try
        {
            return doProcess(accessToken, args);
        }
        catch (WebApplicationException e)
        {
            error(e.getMessage());

            return e.getResponse();
        }
        catch (Throwable t)
        {
            error(t.getMessage());

            return ApiException.internalServerError("Server Error").getResponse();
        }
    }


    abstract protected Response doProcess(String accessToken, Object... args);


    //----------------------------
    // Introspection
    //----------------------------

    public String getTokenOwnerIdViaIntrospection(String accessToken, String[] requiredScopes)
    {
        // Ensure that the access token is associated with the read scope.
        final IntrospectionResponse response =
                callIntrospectionApi(requiredScopes, accessToken);

        if (response == null)
        {
            throw new IllegalStateException("Introspected response is null.");
        }

        // Validate the next action to take.
        validateAction(response);

        // Get the user id of access token owner.
        final String tokenOwnerId = response.getSubject();

        // If the access token is not associated with any user,
        // it has no right to access to the requesting resource.
        if (tokenOwnerId == null)
        {
            // 400 Bad Request.
            throw ApiException.badRequest("The user id associated with the given access token is null.");
        }

        return tokenOwnerId;
    }


    protected IntrospectionResponse callIntrospectionApi(String[] scopes, String accessToken)
    {
        final IntrospectionRequest request = new IntrospectionRequest()

                // Set the required scopes to access the requested resource.
                .setScopes(scopes)

                // Set the access token after being parsed.
                .setToken(BearerToken.parse(accessToken));

        try
        {
            // Introspect.
            return Authlete.getApi().introspection(request);
        }
        catch (AuthleteApiException e)
        {
            // 400 Bad Request.
            throw ApiException.badRequest(e.getResponseBody());
        }
    }


    private void validateAction(IntrospectionResponse response)
    {
        // Get the response content.
        final String content = response.getResponseContent();

        // Check the next action.
        switch (response.getAction())
        {
            case BAD_REQUEST:
                // 400 Bad Request.
                throw ApiException.badRequest(content);

            case FORBIDDEN:
                // 403 Forbidden.
                throw ApiException.forbidden(content);

            case UNAUTHORIZED:
                // 401 Unauthorized.
                throw ApiException.unauthorized(content, getChallenge());

            case INTERNAL_SERVER_ERROR:
                // 500 Internal Server Error.
                throw ApiException.internalServerError(content);

            case OK:
                // OK.
                break;

            default:
                // This never happens.
                throw ApiException.internalServerError("Unknown action");
        }
    }


    private String getChallenge()
    {
        return String.format("Basic realm=\"%s\"", getPath());
    }


    /**
     * Check if the {@code "requestedUserId"} is equivalent to {@code "tokenOwnerId"}.<br>
     * For instance, if the both values of{@code "requestedUserId"} and {@code "tokenUserId"}
     * are "joe", this return true. Even if that's not the case, this also returns true,
     * if the {@code "requestedUserId"} is the value of {@link #TOKEN_OWNER_ID_ALIAS}.
     */
    protected boolean isTokenOwner(String requestedUserId, String tokenOwnerId)
    {
        if (requestedUserId == null || requestedUserId.length() == 0)
        {
            return false;
        }

        if (tokenOwnerId == null || tokenOwnerId.length() == 0)
        {
            return false;
        }

        return TOKEN_OWNER_ID_ALIAS.equals(requestedUserId) || tokenOwnerId.equals(requestedUserId);
    }


    //----------------------------
    // Validation
    //----------------------------

    protected void validateUserId(String userId)
    {
        // Validate that the user id is not null and not blank.
        RequestValidator.validateNotNullAndNotBlank("userId", userId);

        // If the user id is the alias for the token owner's user id, it is valid.
        if (TOKEN_OWNER_ID_ALIAS.equals(userId) == true)
        {
            return;
        }

        // Check if the user id exists in the database.
        if (PROFILE_DAO.getByUserId(userId) == null)
        {
            // 404 Not Found.
            throw ApiException.notFound("The given user id is not found.");
        }
    }


    /**
     * Validate the given entity is not locked (meaning it is not private information).
     */
    protected void validateNotLocked(Lockable<?> lockable)
    {
        if (lockable == null)
        {
            return;
        }

        if (lockable.isLocked())
        {
            // 400 Bad Request.
            throw ApiException.badRequest("The requested data is protected.");
        }
    }


    //-------------------------------
    // Logging
    //-------------------------------

    private void error(String message)
    {
        super.error("Authlete %s failed: %s", getPath(), message);
    }
}
