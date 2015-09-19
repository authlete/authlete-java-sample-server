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
package com.authlete.sample.server.security;


import java.util.logging.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;


/**
 * Authenticator for resource owners.
 *
 * @author Takahiko Kawasaki
 */
public class Authenticator
{
    private Authenticator()
    {
    }


    /**
     * Authenticate the resource owner.
     *
     * @param username
     *         The resource owner's user name.
     *
     * @param password
     *         The resource owner's password.
     *
     * @return
     *         The subject (unique identifier) of the user when he/she
     *         was authenticated successfully. {@code null} when the
     *         user was not authenticated.
     */
    public static String authenticate(String username, String password)
    {
        // Pack the username and password into AuthenticationToken
        // which Apache Shiro's SecurityManager can accept.
        AuthenticationToken credentials = new UsernamePasswordToken(username, password);

        try
        {
            // Authenticate the resource owner using Apache Shiro.
            AuthenticationInfo info = SecurityUtils.getSecurityManager().authenticate(credentials);

            // Get the subject of the authenticated user.
            String subject = info.getPrincipals().getPrimaryPrincipal().toString();

            // Successfully authenticated.
            return subject;
        }
        catch (AuthenticationException e)
        {
            // Authentication failed.
            String message = String.format("Authentication failed: username=%s, error=%s (%s)",
                    username, e.getMessage(), e.getClass().getSimpleName());

            // Emit a debug log message.
            Logger.getLogger(Authenticator.class.getName()).fine(message);

            // Not authenticated.
            return null;
        }
    }
}
