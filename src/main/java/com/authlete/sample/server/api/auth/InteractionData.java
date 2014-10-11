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


import java.io.Serializable;

import com.authlete.common.dto.AuthorizationResponse;


public class InteractionData implements Serializable
{
    private static final long serialVersionUID = 1L;


    private String errorMessage;
    private AuthorizationResponse res;


    public String getErrorMessage()
    {
        return errorMessage;
    }


    public InteractionData setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;

        return this;
    }


    public AuthorizationResponse getRes()
    {
        return res;
    }


    public InteractionData setRes(AuthorizationResponse res)
    {
        this.res = res;

        return this;
    }
}
