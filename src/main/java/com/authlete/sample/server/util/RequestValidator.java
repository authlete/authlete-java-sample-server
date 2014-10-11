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


import java.util.List;
import java.util.Set;


public class RequestValidator
{
    private RequestValidator()
    {
    }


    public static void validateNotNull(String name, Object object)
    {
        if (object == null)
        {
            final String message = String.format("%s is null.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotNegative(String name, int value)
    {
        if (value < 0)
        {
            final String message = String.format("%s is nagetive.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotNegative(String name, long value)
    {
        if (value < 0)
        {
            final String message = String.format("%s is nagetive.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotNullAndNotSizeZero(String name, Object[] value)
    {
        if (value == null || value.length == 0)
        {
            final String message = String.format("%s is null or has no element.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotNullAndNotSizeZero(String name, List<Object> value)
    {
        if (value == null || value.size() == 0)
        {
            final String message = String.format("%s is null or has no element.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotNullAndNotBlank(String name, String value)
    {
        if (value == null || "".equals(value))
        {
            final String message = String.format("%s is null or blank.", name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateNotDuplicateInArray(String name, String value, Set<?> validatedValues)
    {
        if (validatedValues.contains(value))
        {
            final String message = String.format("%s is a duplicate in %s.", value, name);

            throw ApiException.badRequest(message);
        }
    }


    public static void validateMaxLength(String name, String value, int max)
    {
        if (value.length() > max)
        {
            final String message =
                    String.format("The length of %s must be or %d less than that.", name, max);

            throw ApiException.badRequest(message);
        }
    }
}
