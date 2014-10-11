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
package com.authlete.sample.server.web;


import java.util.logging.Level;
import java.util.logging.Logger;


public class BaseEndpoint
{
    private final Logger mLogger = Logger.getLogger(getClass().getName());


    private final String mPath;


    protected BaseEndpoint(String path)
    {
        mPath = path;
    }


    protected String getPath()
    {
        return mPath;
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
