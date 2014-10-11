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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.authlete.common.dto.Client;


@Path("/user/develop/{client_id}/update")
public class ClientUpdatePageController extends BaseClientPageController
{
    private static final String PATH = "/user/develop/{client_id}/update";


    public ClientUpdatePageController()
    {
        super(PATH);
    }

    //----------------------
    // GET
    //----------------------


    @GET
    public Response get(@PathParam("client_id") final long clientId)
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processGettingClientUpdatePage(userId, clientId);
            }
        });
    }


    private Response processGettingClientUpdatePage(String userId, long clientId)
    {
        // Get the client.
        final Client client = getClient(clientId);

        // Create a response with the page to update the client.
        return createResponseOfClientUpdatePage(client);
    }


    //----------------------
    // POST
    //----------------------

    @POST
    public Response post(
            @PathParam("client_id") final long clientId,
            final MultivaluedMap<String, String> parameters)
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processUpdatingClient(parameters, userId, clientId);
            }
        });
    }


    private Response processUpdatingClient(MultivaluedMap<String, String> parameters, String userId, long clientId)
    {
        // Put the user id as 'developer'.
        parameters.putSingle("developer", userId);

        // Put the client id.
        parameters.putSingle("client_id", Long.toString(clientId));

        // Extract the identifiable client (meaning the client having its id) based on the parameters.
        final Client client = ClientExtractor.extractIdentifiable(parameters);

        // Update the client.
        final Client updated = updateClient(client);

        // Create a response redirecting to the page
        // containing the detail information about the updated client.
        return createLocationResponseOfClientDetailPage(updated);
    }
}
