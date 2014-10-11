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


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;


class AuthorizationViewable extends Viewable
{
    /**
     * {@code "text/html;charset=UTF-8"}
     */
    private static final MediaType MEDIA_TYPE_HTML = MediaType.TEXT_HTML_TYPE.withCharset("UTF-8");


    /**
     * The page template to ask the resource owner (= end-user) for authorization.
     */
    private static final String TEMPLATE = "/api/auth/authorization.jsp";


    public AuthorizationViewable(Object model) throws IllegalArgumentException
    {
        super(TEMPLATE, model);
    }


    public Response toResponse()
    {
        return Response.ok(this, MEDIA_TYPE_HTML).build();
    }
}
