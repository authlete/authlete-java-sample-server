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


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.glassfish.jersey.server.mvc.Viewable;

import com.authlete.sample.server.web.LocationResponse;


abstract public class BasePageController
{
    /**
     * The login page.
     */
    private static final String LOGIN_PAGE = "jsp/login.jsp";


    /**
     * The error page.
     */
    private static final String CLIENT_ERROR_PAGE_TEMPLATE = "/general/error.jsp";


    /**
     * The logger.
     */
    private final Logger mLogger = Logger.getLogger(getClass().getName());


    /**
     * The path to the page.
     */
    private final String mPath;


    protected BasePageController(String path)
    {
        mPath = path;
    }


    protected <TRequest> Response process(RequestProcessor requestProcessor)
    {
        // Get the id of user who is requesting the process.
        final String userId = getCurrentUserId();

        // If the user is not logged in, redirect the user to the login page.
        if (userId == null)
        {
            return showLoginPage();
        }

        try
        {
            return requestProcessor.process(userId);
        }
        catch (WebApplicationException e)
        {
            error(e.getMessage());

            // Create the error page containing the entity included in the exception.
            return createResponseOfErrorPage(e.getResponse().getEntity());
        }
        catch (Throwable t)
        {
            error(t.getMessage());

            // Internal Server Error.
            return createResponseOfErrorPage("Internal Server Error.");
        }
    }


    private String getCurrentUserId()
    {
        final Object currentUserId = SecurityUtils.getSubject().getPrincipal();

        return currentUserId == null ? null : currentUserId.toString();
    }


    private Response showLoginPage()
    {
        return LocationResponse.build(LOGIN_PAGE);
    }


    //------------------------
    // Response
    //------------------------

    protected Response createResponseOfErrorPage(Object object)
    {
        return createResponse(CLIENT_ERROR_PAGE_TEMPLATE, object);
    }


    protected Response createResponse(String page)
    {
        final Viewable viewable = new Viewable(page);

        return Response.ok(viewable, MediaType.TEXT_HTML_TYPE).build();
    }


    protected Response createResponse(String template, Object model)
    {
        if (model == null)
        {
            throw new IllegalArgumentException("model is null");
        }

        final Viewable viewable = new Viewable(template, model);

        return Response.ok(viewable, MediaType.TEXT_HTML_TYPE).build();
    }


    protected Response createLocationResponse(String location)
    {
        return LocationResponse.build(location);
    }


    //--------------------
    // Logging
    //--------------------

    private void error(String message)
    {
        error("Authlete %s failed : %s", mPath, message);
    }


    private void log(Level level, String format, Object... args)
    {
        if (args.length == 0)
        {
            mLogger.log(level, format);
        }
        else
        {
            mLogger.log(level, String.format(format, args));
        }
    }


    /**
     * Emit an error log message.
     */
    protected void error(String format, Object... args)
    {
        log(Level.SEVERE, format, args);
    }


    /**
     * Emit a warning log message.
     */
    protected void warn(String format, Object... args)
    {
        log(Level.WARNING, format, args);
    }


    /**
     * Emit an info log message.
     */
    protected void info(String format, Object... args)
    {
        log(Level.INFO, format, args);
    }


    /**
     * Emit a debug log message.
     */
    protected void debug(String format, Object... args)
    {
        log(Level.FINE, format, args);
    }
}
