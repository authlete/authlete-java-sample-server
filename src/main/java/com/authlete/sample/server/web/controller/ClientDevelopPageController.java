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
package com.authlete.sample.server.web.controller;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.authlete.common.dto.ClientListResponse;


@Path("/user/develop")
public class ClientDevelopPageController extends BaseClientPageController
{
    private static final String PATH = "/user/develop";


    public ClientDevelopPageController()
    {
        super(PATH);
    }


    @GET
    public Response get()
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processGettingClientDevelopPage(userId);
            }
        });
    }


    private Response processGettingClientDevelopPage(String userId)
    {
        // Get the list of client applications owned by the user.
        final ClientListResponse clientList = getClientList(userId);

        // Create a response with the page containing the list.
        return createResponseOfClientDevelopPage(clientList);
    }
}
