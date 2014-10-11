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


import org.apache.shiro.subject.Subject;

import com.authlete.common.dto.AuthorizationIssueRequest;
import com.authlete.common.dto.AuthorizationResponse;


class AuthorizationIssueRequestCreator
{
    private final AuthorizationResponse mAuthorizationResponse;
    private final Subject mUser;


    AuthorizationIssueRequestCreator(AuthorizationResponse response, Subject user)
    {
        mAuthorizationResponse = response;
        mUser = user;
    }


    public AuthorizationIssueRequest create()
    {
        // Create a request for Authlete's /auth/authorization/issue API.
        return new AuthorizationIssueRequest()
            .setTicket(ticket())
            .setSubject(subject())
            .setAuthTime(authTime())
            .setAcr(acr())
            .setClaims(claims());
    }


    private String ticket()
    {
        return mAuthorizationResponse.getTicket();
    }


    private String subject()
    {
        return (String)mUser.getPrincipal();
    }


    private int authTime()
    {
        // TODO
        //
        // HttpSession.getCreationTime() may not work in some cases.
        // For example, Google App Engine. Probably an explicit
        // attribute which holds the authentication time should be
        // set into the session and it should be referred to here.
        return (int)(mUser.getSession().getStartTimestamp().getTime() / 1000);
    }


    private String acr()
    {
        // TODO

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


    private String claims()
    {
        // Collect values of the requested claims.
        return new ClaimCollector()
            .setSubject(subject())
            .setClaimNames(mAuthorizationResponse.getClaims())
            .setClaimLocales(mAuthorizationResponse.getClaimsLocales())
            .collect();
    }
}
