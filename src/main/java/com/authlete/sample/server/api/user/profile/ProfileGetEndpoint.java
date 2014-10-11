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
package com.authlete.sample.server.api.user.profile;


import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.authlete.sample.server.database.entity.Profile;
import com.authlete.sample.server.util.ApiException;
import com.authlete.sample.server.util.RequestValidator;
import com.authlete.sample.server.web.JsonResponse;


@Path("/api/resources/profile/{user_id}")
public class ProfileGetEndpoint extends BaseProfileEndpoint
{
    private static final String PATH = "/api/resources/profile/{user_id}";


    public ProfileGetEndpoint()
    {
        super(PATH);
    }


    @GET
    public Response get(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathParam("user_id") String userId)
    {
        return process(accessToken, userId);
    }


    @Override
    protected Response doProcess(String accessToken, Object... args)
    {
        final String userId = (String) args[0];

        // Validation.
        validate(accessToken, userId);

        // Get the token owner id after introspection.
        final String tokenOwnerId =
                getTokenOwnerIdViaIntrospection(accessToken, new String[] { SCOPE_READ });

        // Get the profile.
        final Profile profile = getProfileByUserId(userId, tokenOwnerId);

        return JsonResponse.build(profile);
    }


    private void validate(String accessToken, String userId)
    {
        RequestValidator.validateNotNullAndNotBlank("accessToken", accessToken);
        validateUserId(userId);
    }


    private Profile getProfileByUserId(String userId, String tokenOwnerId)
    {
        final Profile profile = getByUserId(userId, tokenOwnerId);

        if (profile == null)
        {
            // 404 Not Found.
            throw ApiException.notFound("The requested data was not found.");
        }

        return profile;
    }


    private Profile getByUserId(String userId, String tokenOwnerId)
    {
        Profile profile;

        if ( isTokenOwner(userId, tokenOwnerId) == true )
        {
            profile = DAO.getByUserId(tokenOwnerId);
        }
        else
        {
            profile = DAO.getByUserId(userId);

            // If the user id is not the access token owner's user id,
            // ensure that the entity is not locked.
            validateNotLocked(profile);
        }

        return profile;
    }
}
