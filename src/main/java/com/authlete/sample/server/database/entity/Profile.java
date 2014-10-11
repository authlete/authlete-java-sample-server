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

import javax.xml.bind.annotation.XmlType;


@XmlType(propOrder={"number", "userId", "name", "sex", "age", "locked", "createdAt"})
public class Profile implements Lockable<Profile>
{
    private long number;


    private String userId;


    private String name;


    private int sex;


    private int age;


    private boolean locked;


    private Timestamp createdAt;


    public long getNumber()
    {
        return number;
    }


    public Profile setNumber(long number)
    {
        this.number = number;

        return this;
    }


    public String getUserId()
    {
        return userId;
    }


    public Profile setUserId(String userId)
    {
        this.userId = userId;

        return this;
    }


    public String getName()
    {
        return name;
    }


    public Profile setName(String name)
    {
        this.name = name;

        return this;
    }


    public int getSex()
    {
        return sex;
    }


    public Profile setSex(int sex)
    {
        this.sex = sex;

        return this;
    }


    public int getAge()
    {
        return age;
    }


    public Profile setAge(int age)
    {
        this.age = age;

        return this;
    }


    @Override
    public boolean isLocked()
    {
        return locked;
    }


    @Override
    public Profile setLocked(boolean locked)
    {
        this.locked = locked;

        return this;
    }


    public Timestamp getCreatedAt()
    {
        return createdAt;
    }


    public Profile setCreatedAt(Timestamp created_at)
    {
        this.createdAt = created_at;

        return this;
    }
}
