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


import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.authlete.sample.server.database.entity.Feed;
import com.authlete.sample.server.util.RequestValidator;
import com.authlete.sample.server.web.JsonResponse;


@Path("/api/resources/feed/create")
public class FeedCreateEndpoint extends BaseFeedEndpoint
{
    private static final String PATH = "/api/resources/feed/create";


    public FeedCreateEndpoint()
    {
        super(PATH);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String accessToken,
            @Context UriInfo uriInfo,
            Feed entity)
    {
        return process(accessToken, uriInfo, entity);
    }


    @Override
    protected Response doProcess(String accessToken, Object... args)
    {
        final UriInfo uriInfo = (UriInfo) args[0];
        final Feed feed       = (Feed) args[1];

        // Validation.
        validate(accessToken, uriInfo, feed);

        // Get the token owner id after introspection.
        final String tokenOwnerId =
                getTokenOwnerIdViaIntrospection(accessToken, new String[]{ SCOPE_CREATE });

        // Associate the token owner's user id with the feed.
        feed.setUserId(tokenOwnerId);

        // Create the feed.
        final long createdId = DAO.create(feed);

        // 201 Created.
        return JsonResponse
                .buildCreated(createEntity(createdId), createUri(uriInfo, createdId));
    }


    private void validate(String accessToken, UriInfo uriInfo, Feed feed)
    {
        RequestValidator.validateNotNullAndNotBlank("accessToken", accessToken);
        RequestValidator.validateNotNull("uriInfo", uriInfo);
        RequestValidator.validateNotNull("feed", feed);
    }


    private String createEntity(long createdFeedNumber)
    {
        return String.format("{\"number\":%d}", createdFeedNumber);
    }


    private URI createUri(UriInfo uriInfo, long result)
    {
        return URI.create(
                   uriInfo
                   .getAbsolutePathBuilder()
                   .toString()
                   .replace("create",  Long.toString(result))
        );
    }
}
