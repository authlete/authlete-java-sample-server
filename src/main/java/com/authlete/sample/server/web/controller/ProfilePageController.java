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
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.authlete.sample.server.database.entity.Profile;
import com.authlete.sample.server.database.entity.dao.ProfileDao;
import com.authlete.sample.server.util.AppContext;


@Path("/user/profile")
public class ProfilePageController extends BasePageController
{
    private static final String PATH ="/user/profile";


    private static final String PROFILE_PAGE_TEMPLATE = "/user/profile.jsp";


    private static final ProfileDao DAO = AppContext.getBean("profileDao");


    public ProfilePageController()
    {
        super(PATH);
    }


    @GET
    public Response get()
    {
        return process(new RequestProcessor() {
            @Override
            public Response process(String userId)
            {
                return processGettingProfilePage(userId);
            }
        });
    }


    private Response processGettingProfilePage(String userId)
    {
        // Get the user's profile.
        final Profile profile = DAO.getByUserId(userId);

        // Create a response with the page containing the profile.
        return createResponse(PROFILE_PAGE_TEMPLATE, profile);
    }
}
