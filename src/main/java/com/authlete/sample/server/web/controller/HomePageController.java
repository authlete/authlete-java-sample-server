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

import com.authlete.sample.server.database.entity.Feed;
import com.authlete.sample.server.database.entity.dao.FeedDao;
import com.authlete.sample.server.util.AppContext;


@Path("/user/home")
public class HomePageController extends BasePageController
{
    private static final String PATH = "/user/home";


    private static final String HOME_PAGE_TEMPLATE = "/user/home.jsp";


    private static final FeedDao DAO = AppContext.getBean("feedDao");


    public HomePageController()
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
                return processGettingHomePage(userId);
            }
        });
    }


    private Response processGettingHomePage(String userId)
    {
        // Get the user's feed list.
        final Feed[] feedArray = DAO.getListByUserId(userId);

        // Re-order the feeds in the array
        // so that the newest one is displayed first.
        reverse(feedArray, 0);

        // Create a response with the page containing the list.
        return createResponse(HOME_PAGE_TEMPLATE, feedArray);
    }


    private void reverse(Object[] array, int i)
    {
        if (i > array.length/2 - 1)
        {
            return;
        }

        final int last = array.length - 1;

        // Swap the i-th element for the (last - i)-th element in the array.
        final Object tmp = array[i];
        array[i] = array[last - i];
        array[last - i] = tmp;

        reverse(array, i+1);
    }
}
