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


import javax.ws.rs.core.Response;
import com.authlete.common.dto.TokenFailRequest.Reason;
import com.authlete.common.dto.TokenResponse;
import com.authlete.sample.server.security.Authenticator;


/**
 * The handler to process the token request whose flow is
 * <a href="http://tools.ietf.org/html/rfc6749#section-4.3">
 * "Resource Owner Password Credentials"</a>.
 *
 * <p>
 * This handler is used when {@code "action"} in the response
 * from Authlete's {@code /auth/token} API is {@link
 * TokenResponse.Action#PASSWORD PASSWORD}.
 */
class PasswordHandler extends BaseTokenHandler
{
    /**
     * The response from Authlete's {@code /auth/token} API.
     */
    private final TokenResponse mTokenResponse;


    PasswordHandler(TokenResponse response)
    {
        mTokenResponse = response;
    }


    Response handle()
    {
        // The credentials of the resource owner.
        String username = mTokenResponse.getUsername();
        String password = mTokenResponse.getPassword();

        // Validate the credentials.
        String subject = validateCredentials(username, password);

        // The ticket for Authlete's /auth/token/* API.
        String ticket = mTokenResponse.getTicket();

        if (subject != null)
        {
            // Issue an access token and optionally an ID token.
            return issue(ticket, subject);
        }
        else
        {
            // The credentials are invalid. An access token is not issued.
            throw fail(ticket, Reason.INVALID_RESOURCE_OWNER_CREDENTIALS);
        }
    }


    private String validateCredentials(String subject, String password)
    {
        return Authenticator.authenticate(subject, password);
    }
}
