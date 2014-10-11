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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.authlete.common.dto.Client;


@Path("/user/develop/create")
public class ClientCreatePageController extends BaseClientPageController
{
    private static final String PATH = "/user/develop/create";


    public ClientCreatePageController()
    {
        super(PATH);
    }


    //----------------------
    // GET
    //----------------------

    @GET
    public Response get()
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processGettingClientCreatePage();
            }
        });
    }


    private Response processGettingClientCreatePage()
    {
        // Create the response with the page to create a client.
        return createResponseOfClientCreatePage();
    }


    //----------------------
    // POST
    //----------------------

    @POST
    public Response post(final MultivaluedMap<String, String> parameters)
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                // Create a client based on the parameters.
                return processCreatingClient(parameters, userId);
            }
        });
    }


    private Response processCreatingClient(MultivaluedMap<String, String> parameters, String userId)
    {
        // Put the user id as 'developer'.
        parameters.putSingle("developer", userId);

        // Extract information about the client from the parameters.
        final Client client = ClientExtractor.extract(parameters);

        // Create the client.
        final Client created = createClient(client);

        // Create a response redirecting the page
        // containing the information about the created client.
        return createLocationResponseOfClientDetailPage(created);
    }
}
