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


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.authlete.sample.server.database.entity.Feed;


public class FeedDao extends JdbcDaoSupport
{
    private static final String GET_BY_RESOURCE_ID =
            "SELECT number, user_id, title, content, locked, created_at, modified_at FROM feed WHERE number = ?";


    private static final String GET_LIST_BY_USER_ID =
            "SELECT number, user_id, title, content, locked, created_at, modified_at FROM feed WHERE user_id = ?";


    private static final String GET_LIST_BY_USER_ID_AND_LOCK =
            "SELECT number, user_id, title, content, locked, created_at, modified_at FROM feed WHERE user_id = ? and locked = ?";


    private static final String CREATE =
            "INSERT INTO feed(user_id, title, content, locked) VALUES(?,?,?,?)";


    public Feed getByResourceId(long resourceId)
    {
        try
        {
             final List<Map<String, Object>> rows = getJdbcTemplate()
                     .queryForList(GET_BY_RESOURCE_ID, new Object[] { resourceId });

             return toEntity(rows);
        }
        catch (Throwable t)
        {
            final String message = String.format("Failed to get feed: %s", t.getMessage());

            throw new IllegalStateException(message);
        }
    }


    private Feed toEntity(List<Map<String, Object>> rows)
    {
        if (rows == null || rows.size() == 0)
        {
            return null;
        }

        // The 'rows' is expected to have only one element.
        final Map<String, Object> row = rows.get(0);

        return toEntity(row);
    }


    private Feed toEntity(Map<String, Object> row)
    {
        return new Feed()
               .setNumber((Long)row.get("NUMBER"))
               .setUserId((String)row.get("USER_ID"))
               .setTitle((String)row.get("TITLE"))
               .setContent((String)row.get("CONTENT"))
               .setLocked((Boolean)row.get("LOCKED"))
               .setCreatedAt((Timestamp)row.get("CREATED_AT"))
               .setModifiedAt((Timestamp)row.get("MODIFIED_AT"));
    }


    public Feed[] getListByUserId(String userId)
    {
        try
        {
            final List<Map<String, Object>> rows = getJdbcTemplate()
                    .queryForList(GET_LIST_BY_USER_ID, new Object[] { userId });

            return toArray(rows);
        }
        catch (Throwable t)
        {
            final String message = String.format("Failed to get feed list: %s", t.getMessage());

            throw new IllegalStateException(message);
        }
    }


    public Feed[] getListByUserIdAndLock(String userId, boolean locked)
    {
        try
        {
            final List<Map<String, Object>> rows = getJdbcTemplate()
                    .queryForList(GET_LIST_BY_USER_ID_AND_LOCK, new Object[] { userId, locked });

            return toArray(rows);
        }
        catch (Throwable t)
        {
            final String message = String.format("Failed to get feed list: %s", t.getMessage());

            throw new IllegalStateException(message);
        }
    }


    private Feed[] toArray(List<Map<String, Object>> rows)
    {
        if (rows == null)
        {
            return null;
        }

        final List<Feed> feedList = new ArrayList<Feed>();

        for (Map<String, Object> row : rows)
        {
            feedList.add(toEntity(row));
        }

        return feedList.toArray(new Feed[feedList.size()]);
    }


    public long create(Feed feed)
    {
        try
        {
            return doCreate(feed);
        }
        catch (Throwable t)
        {
            final String message = String.format("Failed to create feed: %s", t.getMessage());

            throw new IllegalStateException(message);
        }
    }


    private long doCreate(final Feed feed)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update( new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(CREATE, new String[] {"NUMBER"});

                ps.setString(1, feed.getUserId());
                ps.setString(2, feed.getTitle());
                ps.setString(3, feed.getContent());
                ps.setBoolean(4, feed.isLocked());

                return ps;
           }
        }, keyHolder);

        // Return the value in 'NUMBER' in the last created row.
        return keyHolder.getKey().longValue();
    }
}
