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
package com.authlete.sample.server.database.entity;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder={"number", "userId", "title", "content", "locked", "createdAt", "modifiedAt"})
public class Feed implements Lockable<Feed>
{
    private long number;


    private String userId;


    private String title;


    private String content;


    private boolean locked;


    private Timestamp createdAt;


    @XmlElement(nillable=true)
    private Timestamp modifiedAt;


    public long getNumber()
    {
        return number;
    }


    public Feed setNumber(long number)
    {
        this.number = number;

        return this;
    }


    public String getUserId()
    {
        return userId;
    }


    public Feed setUserId(String userId)
    {
        this.userId = userId;

        return this;
    }


    public String getTitle()
    {
        return title;
    }


    public Feed setTitle(String title)
    {
        this.title = title;

        return this;
    }


    public String getContent()
    {
        return content;
    }


    public Feed setContent(String content)
    {
        this.content = content;

        return this;
    }


    @Override
    public boolean isLocked()
    {
        return locked;
    }


    @Override
    public Feed setLocked(boolean locked)
    {
        this.locked = locked;

        return this;
    }


    public Timestamp getCreatedAt()
    {
        return createdAt;
    }


    public Feed setCreatedAt(Timestamp createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }


    public Timestamp getModifiedAt()
    {
        return modifiedAt;
    }


    public Feed setModifiedAt(Timestamp modifiedAt)
    {
        this.modifiedAt = modifiedAt;

        return this;
    }
}
