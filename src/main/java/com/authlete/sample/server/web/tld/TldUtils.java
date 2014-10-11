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
package com.authlete.sample.server.web.tld;


/**
 * This class is used to create the jsp files.
 */
public class TldUtils
{
    public static boolean containsInTypes(Enum<?>[] types, String string)
    {
        if (types == null || string == null)
        {
            return false;
        }

        for (Enum<?> type : types)
        {
            if (type.name().equals(string))
            {
                return true;
            }
        }

        return false;
    }
}
