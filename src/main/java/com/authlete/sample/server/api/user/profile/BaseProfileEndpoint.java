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
package com.authlete.sample.server.api.user.profile;


import com.authlete.sample.server.api.user.BaseResourceEndpoint;
import com.authlete.sample.server.database.entity.dao.ProfileDao;
import com.authlete.sample.server.util.AppContext;


abstract public class BaseProfileEndpoint extends BaseResourceEndpoint
{
    protected static final String RESOURCE_NAME = "profile";

    protected static final String SCOPE_READ    = "profile_read";

    protected static final ProfileDao DAO = AppContext.getBean("profileDao");


    protected BaseProfileEndpoint(String path)
    {
        super(path);
    }
}
