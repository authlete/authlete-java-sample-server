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
package com.authlete.sample.server.database.entity.dao;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.authlete.sample.server.database.entity.Profile;


public class ProfileDao extends JdbcDaoSupport
{
    private static final String GET_BY_USER_ID =
            "SELECT number, id, name, sex, age, locked, created_at FROM profile WHERE id = ?";


    public Profile getByUserId(String userId)
    {
        try
        {
             final List<Map<String, Object>> rows = getJdbcTemplate()
                     .queryForList(GET_BY_USER_ID, new Object[] { userId });

             return toEntity(rows);
        }
        catch (Throwable t)
        {
            final String message = String.format("Failed to get profile: %s", t.getMessage());

            throw new IllegalStateException(message);
        }
    }


    private Profile toEntity(List<Map<String, Object>> rows)
    {
        if (rows == null || rows.size() == 0)
        {
            return null;
        }

        // The 'rows' is expected to have only one element.
        final Map<String, Object> row = rows.get(0);

        return toEntity(row);
    }


    private Profile toEntity(Map<String, Object> row)
    {
        return new Profile()
        .setNumber((long) row.get("NUMBER"))
        .setUserId((String) row.get("ID"))
        .setName((String) row.get("NAME"))
        .setAge((int) row.get("AGE"))
        .setSex((int) row.get("SEX"))
        .setLocked((boolean) row.get("LOCKED"))
        .setCreatedAt((Timestamp) row.get("CREATED_AT"));
    }
}
