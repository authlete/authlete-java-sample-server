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


import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.authlete.common.dto.AuthorizationFailRequest.Reason;
import com.authlete.common.dto.AuthorizationIssueRequest;
import com.authlete.common.dto.AuthorizationResponse;


/**
 * The handler to process the authorization request without
 * user interaction. This handler is used when {@code "action"}
 * in the response from Authlete's {@code /auth/authorization}
 * API is {@link AuthorizationResponse.Action#NO_INTERACTION
 * NO_INTERACTION}.
 */
class NoInteractionHandler extends BaseAuthorizationHandler
{
    /**
     * The response from Authlete's {@code /auth/authorization} API.
     */
    private final AuthorizationResponse mAuthorizationResponse;


    /**
     * The current user.
     */
    private final Subject mUser;


    NoInteractionHandler(AuthorizationResponse response)
    {
        // The response that has already been received from Authlete's
        // /auth/authorization API.
        mAuthorizationResponse = response;

        // The current user.
        mUser = SecurityUtils.getSubject();
    }


    Response handle()
    {
        // 1. End-User Authentication
        checkAuthentication();

        // 2. Max Age
        checkMaxAge();

        // 3. Subject
        checkSubject();

        // 4. ACR
        checkAcr();

        // 5. Issue
        return issue();
    }


    private WebApplicationException fail(Reason reason)
    {
        return fail(mAuthorizationResponse.getTicket(), reason);
    }


    private void checkAuthentication()
    {
        // If the current user has already been authenticated.
        if (mUser.isAuthenticated())
        {
            // OK.
            return;
        }

        // A user must have logged in.
        throw fail(Reason.NOT_LOGGED_IN);
    }


    private void checkMaxAge()
    {
        // Get the requested maximum authentication age.
        int maxAge = mAuthorizationResponse.getMaxAge();

        // If no maximum authentication age is requested.
        if (maxAge == 0)
        {
            // No check is needed.
            return;
        }

        // Get the timestamp when the user logged in.
        Date authTime = mUser.getSession().getStartTimestamp();

        // The time at which the authentication expires.
        long expiresAt = authTime.getTime() + maxAge * 1000;

        // If the authentication has not expired yet.
        if (System.currentTimeMillis() < expiresAt)
        {
            // OK.
            return;
        }

        // The maximum authentication age has elapsed.
        throw fail(Reason.EXCEEDS_MAX_AGE);
    }


    private void checkSubject()
    {
        // Get the requested subject.
        String requestedSubject = mAuthorizationResponse.getSubject();

        // If no subject is requested.
        if (requestedSubject == null)
        {
            // No check is needed.
            return;
        }

        // The current subject, i.e. the unique ID assigned by
        // the service to the current user.
        String currentSubject = (String)mUser.getPrincipal();

        // If the requested subject matches the current user.
        if (requestedSubject.equals(currentSubject))
        {
            // OK.
            return;
        }

        // The current user is different from the requested subject.
        throw fail(Reason.DIFFERENT_SUBJECT);
    }


    private void checkAcr()
    {
        // Get the list of requested ACRs.
        String[] requestedAcrs = mAuthorizationResponse.getAcrs();

        // If no ACR is requested.
        if (requestedAcrs == null || requestedAcrs.length == 0)
        {
            // No check is needed.
            return;
        }

        // Get the ACR that was performed for authentication of
        // the current user.
        String acr = getAcr();

        for (String requestedAcr : requestedAcrs)
        {
            if (requestedAcr.equals(acr))
            {
                // OK. The ACR performed for authentication of the
                // current user satisfies one of the requested ACRs.
                return;
            }
        }

        // If one of the requested ACRs must be satisfied.
        if (mAuthorizationResponse.isAcrEssential())
        {
            // None of the requested ACRs is satisfied.
            throw fail(Reason.ACR_NOT_SATISFIED);
        }

        // The ACR performed for authentication of the current user
        // does not satisfy any one of the requested ACRs, but the
        // authorization request from the client application did
        // not request ACR as essential. Therefore, it is not
        // necessary to raise an error here.
    }


    private String getAcr()
    {
        // The following is an excerpt from "OpenID Connect Core 1.0,
        // 2. ID Token, acr".
        //
        //     Parties using this claim will need to agree upon
        //     the meanings of the values used, which may be
        //     context-specific.
        //
        // So, the value of ACR is service-dependent.

        // The ACR value should be able to be extracted via mUser, I think.

        // This example returns just null.
        return null;
    }


    private Response issue()
    {
        // Create a request for Authlete's /auth/authorization/issue API.
        AuthorizationIssueRequest request = createIssueRequest();

        // Issue an authorization code, an ID token and/or an access token.
        return issue(request);
    }


    private AuthorizationIssueRequest createIssueRequest()
    {
        return new AuthorizationIssueRequestCreator(mAuthorizationResponse, mUser).create();
    }
}
