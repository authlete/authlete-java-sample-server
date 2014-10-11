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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.authlete.common.dto.AuthorizationIssueRequest;


class Authorizer extends BaseAuthorizationHandler
{
    private final HttpSession mSession;
    private final InteractionData mModel;
    private final String mUsername;
    private final String mPassword;


    Authorizer(HttpSession session, InteractionData model, String username, String password)
    {
        mSession  = session;
        mModel    = model;
        mUsername = username;
        mPassword = password;
    }


    public Response handle()
    {
        // Login using the credentials.
        Subject user = login();

        // Check if the username matches the requested subject.
        checkRequiredSubject(user);

        // Issue an authorization code, an ID token and/or an access token.
        return issue(user);
    }


    private Subject login()
    {
        // Pack the username and password into AuthenticationToken
        // which Apache Shiro's SecurityManager can accept.
        AuthenticationToken credentials = new UsernamePasswordToken(mUsername, mPassword);

        Subject user = SecurityUtils.getSubject();

        try
        {
            // Login using the credentials.
            user.login(credentials);
        }
        catch (AuthenticationException e)
        {
            // Login failed.
            error("Login failed: username=%s, error=%s (%s)",
                    mUsername, e.getMessage(), e.getClass().getSimpleName());

            // TODO: Internationalize the error message.
            String errorMessage = "The user's credentials are invalid.";

            // Go back to the authorization page.
            throw goBack(errorMessage);
        }

        return user;
    }


    private void checkRequiredSubject(Subject user)
    {
        // The subject that the client application requires.
        String requiredSubject = mModel.getRes().getSubject();

        if (requiredSubject == null)
        {
            // Not required.
            return;
        }

        // The current subject.
        String currentSubject = (String)user.getPrincipal();

        if (requiredSubject.equals(currentSubject))
        {
            // OK.
            return;
        }

        // TODO: Internationalize the error message.
        String errorMessage = "Login by the account '" + requiredSubject + "'.";

        // Go back to the authorization page.
        throw goBack(errorMessage);
    }


    private WebApplicationException goBack(String errorMessage)
    {
        // Set the error message.
        mModel.setErrorMessage(errorMessage);

        // Set back the model object into the session.
        mSession.setAttribute("model", mModel);

        // Create a response to show the authorization page again.
        Response response = new AuthorizationViewable(mModel).toResponse();

        // Show the page.
        return new WebApplicationException(response);
    }


    private Response issue(Subject user)
    {
        // Create a request for Authlete's /auth/authorization/issue API.
        AuthorizationIssueRequest request = createIssueRequest(user);

        // Issue an authorization code, an ID token and/or an access token.
        return issue(request);
    }


    private AuthorizationIssueRequest createIssueRequest(Subject user)
    {
        return new AuthorizationIssueRequestCreator(mModel.getRes(), user).create();
    }
}
