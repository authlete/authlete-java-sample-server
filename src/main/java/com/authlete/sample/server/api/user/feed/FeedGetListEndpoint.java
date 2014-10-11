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
package com.authlete.sample.server.api.user.feed;


import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.authlete.sample.server.database.entity.Feed;
import com.authlete.sample.server.util.ApiException;
import com.authlete.sample.server.util.RequestValidator;
import com.authlete.sample.server.web.JsonResponse;


@Path("/api/resources/feed/list/{user_id}")
public class FeedGetListEndpoint extends BaseFeedEndpoint
{
    private static final String PATH = "/api/resources/feed/list/{user_id}";


    public FeedGetListEndpoint()
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
                getTokenOwnerIdViaIntrospection(accessToken, new String[]{ SCOPE_READ });

        // Get the entity associated with the requested user id.
        final Feed[] feedList = getFeedListByUserId(userId, tokenOwnerId);

        if (feedList == null || feedList.length == 0)
        {
            // 404 Not Found.
            throw ApiException.notFound("No data accessible.");
        }

        return JsonResponse.build(feedList);
    }


    private void validate(String accessToken, String userId)
    {
        RequestValidator.validateNotNullAndNotBlank("accessToken", accessToken);
        validateUserId(userId);
    }


    private Feed[] getFeedListByUserId(String userId, String tokenOwnerId)
    {
        if ( isTokenOwner(userId, tokenOwnerId) == true )
        {
            // If the user id is equal to token owner's user id,
            // get all the list by the token owner's id.
            return DAO.getListByUserId(tokenOwnerId);
        }
        else
        {
            // If the user id is different from the token owner's user id,
            // get all the unlocked (public) feed list by the user id.
            return DAO.getListByUserIdAndLock(userId, false);
        }
    }
}
