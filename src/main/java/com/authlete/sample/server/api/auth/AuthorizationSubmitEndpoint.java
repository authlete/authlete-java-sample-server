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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.authlete.sample.server.web.BaseEndpoint;


@Path("/api/auth/authorization/submit")
public class AuthorizationSubmitEndpoint extends BaseEndpoint
{
    private static final String PATH = "/api/auth/authorization/submit";


    public AuthorizationSubmitEndpoint()
    {
        super(PATH);
    }


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response post(
            @Context HttpServletRequest request,
            @FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("authorized") boolean authorized)
    {
        // The HTTP session.
        HttpSession session = request.getSession();

        // Extract the model object from the session.
        InteractionData model = extractModelObject(session);

        if (authorized)
        {
            // The user authorized the request.
            return authorize(session, model, username, password);
        }
        else
        {
            // THe user denied the request.
            return deny(model);
        }
    }


    private InteractionData extractModelObject(HttpSession session)
    {
        // Extract the model object from the session.
        Object model = session.getAttribute("model");

        if (model == null)
        {
            throw new InternalServerErrorException("'model' is not found in the session.");
        }

        // If the model object cannot be cast to InteractionData.
        if ((model instanceof InteractionData) == false)
        {
            throw new InternalServerErrorException("The model object cannot be cast to InteractionData.");
        }

        return (InteractionData)model;
    }


    private Response authorize(HttpSession session, InteractionData model, String username, String password)
    {
        try
        {
            // Delegate the processing to Authorizer.
            return new Authorizer(session, model, username, password).handle();
        }
        catch (WebApplicationException e)
        {
            return e.getResponse();
        }
    }


    private Response deny(InteractionData model)
    {
        try
        {
            // Delegate the processing to Denier.
            return new Denier(model).handle();
        }
        catch (WebApplicationException e)
        {
            return e.getResponse();
        }
    }
}
