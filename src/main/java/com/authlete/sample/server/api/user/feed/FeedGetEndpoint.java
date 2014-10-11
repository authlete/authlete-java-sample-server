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


@Path("/api/resources/feed/{feed_id}")
public class FeedGetEndpoint extends BaseFeedEndpoint
{
    private static final String PATH = "/api/resources/feed/{feed_id}";


    public FeedGetEndpoint()
    {
        super(PATH);
    }


    @GET
    public Response get(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathParam("feed_id") long feedId)
    {
        return process(accessToken, feedId);
    }


    @Override
    protected Response doProcess(String accessToken, Object... args)
    {
        final long feedId = (long) args[0];

        // Validation.
        validate(accessToken, feedId);

        // Get the token owner id after introspection.
        final String tokenOwnerId =
                getTokenOwnerIdViaIntrospection(accessToken, new String[] { SCOPE_READ });

        final Feed feed = getFeedByResourceId(feedId, tokenOwnerId);

        return JsonResponse.build(feed);
    }


    private void validate(String accessToken, long feedId)
    {
        RequestValidator.validateNotNullAndNotBlank("accessToken", accessToken);
        RequestValidator.validateNotNegative("feedId", feedId);
    }


    private Feed getFeedByResourceId(long feedId, String tokenOwnerId)
    {
        // Get the feed from the database.
        final Feed feed = DAO.getByResourceId(feedId);

        if (feed == null)
        {
            // 404 Not Found.
            throw ApiException.notFound("The requested data was not found.");
        }

        // The user id of the feed owner.
        final String feedOwnerId = feed.getUserId();

        if (feedOwnerId == null)
        {
            throw new IllegalStateException("The user id of the feed owner is null.");
        }

        // If the feed owner's user id is not equal to the token onwer's user id,
        // ensure that the feed is not locked.
        if ( feedOwnerId.equals(tokenOwnerId) == false )
        {
            validateNotLocked(feed);
        }

        return feed;
    }
}
