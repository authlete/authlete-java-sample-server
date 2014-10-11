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


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("/user/develop/{client_id}/delete")
public class ClientDeletePageController extends BaseClientPageController
{
    private static final String PATH = "/user/develop/{client_id}/delete";


    public ClientDeletePageController()
    {
        super(PATH);
    }


    @POST
    public Response delete(@PathParam("client_id") final long clientId)
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processDeletingClient(clientId);
            }
        });
    }


    private Response processDeletingClient(long clientId)
    {
        // Delete the client.
        deleteClient(clientId);

        // Create a response redirecting to the page
        // containing the list of clients owned by the user.
        return createLocationResponseOfClientDevelopPage();
    }
}
