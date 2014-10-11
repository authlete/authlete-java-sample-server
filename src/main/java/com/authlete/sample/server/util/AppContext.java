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
package com.authlete.sample.server.util;


import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AppContext
{
    // This part is required for the DriveManager to know the EmbeddedDriver,
    // which is needed to execute some processes in Derby database (e.g. creating a database, executing SQLs, etc.)
    // while loading the following Spring context file.
    static
    {
        try
        {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        }
        catch (SQLException e)
        {
            // Won't happen. Just ignore.
        }
    }


    /**
     * The Spring Context file.<br>
     *
     * On loading this file, some processes that are defined in detail in this file
     * (e.g. creating a database, executing SQLs, etc.) will be executed.
     */
    private static final String CONTEXT_FILE = "applicationContext.xml";


    /**
     * The context based on the {@link #CONTEXT_FILE}.
     */
    private static final ConfigurableApplicationContext context =
                                   new ClassPathXmlApplicationContext(CONTEXT_FILE);


    private AppContext()
    {
    }


    /**
     * Get a bean that is define in {@link CONTEXT_FILE} by its name.
     *
     * @param beanName
     *         The name of bean to get.
     */
    @SuppressWarnings("unchecked")
    public static <TBean> TBean getBean(String beanName)
    {
        return (TBean) context.getBean(beanName);
    }
}
