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
package com.authlete.sample.server.web;


import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


/**
 * Builder for {@code 302 Found} response with {@code Location} header.
 *
 * @author Takahiko Kawasaki
 */
public class LocationResponse
{
    private LocationResponse()
    {
    }


    /**
     * Create a {@code 302 Found} response with {@code Location} header.
     *
     * <pre>
     * HTTP/1.1 302 Found
     * Location: <i>(location)</i>
     * </pre>
     *
     * @param location
     *         The value of {@code Location} header.
     *
     * @return
     *         A {@code 302 Found} response with {@code Location} header.
     */
    public static Response build(String location)
    {
        return Response.status(Status.FOUND)
                .header(HttpHeaders.LOCATION, location).build();
    }
}
