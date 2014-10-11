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


import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import com.authlete.common.dto.AuthorizationResponse;


/**
 * The handler to process the authorization request with
 * user interaction. This handler is used when {@code "action"}
 * in the response from Authlete's {@code /auth/authorization}
 * API is {@link AuthorizationResponse.Action#INTERACTION
 * INTERACTION}.
 */
class InteractionHandler extends BaseAuthorizationHandler
{
    /**
     * The HTTP session.
     */
    private final HttpSession mSession;


    /**
     * The response from Authlete's {@code /auth/authorization} API.
     */
    private final AuthorizationResponse mAuthorizationResponse;


    InteractionHandler(HttpSession session, AuthorizationResponse response)
    {
        // The HTTP session.
        mSession = session;

        // The response that has already been received from Authlete's
        // /auth/authorization API.
        mAuthorizationResponse = response;
    }


    Response handle()
    {
        // An HTML page needs to be shown to process the authorization request
        // with user interaction.

        // Prepare a model object.
        InteractionData model = new InteractionData().setRes(mAuthorizationResponse);

        // Set the model object into the session.
        mSession.setAttribute("model", model);

        // AuthorizationViewable is a subclass of Viewable. Viewable is a
        // Jersy-specific class for MVC. The argument given to the constructor
        // of AuthorizationViewable is a model object which can be referred
        // to from within the view (e.g. JSP).
        AuthorizationViewable viewable = new AuthorizationViewable(model);

        // Show the page.
        return viewable.toResponse();
    }
}
