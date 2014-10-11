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
package com.authlete.sample.server.api.auth;


import static com.authlete.common.types.StandardClaims.GENDER;
import static com.authlete.common.types.StandardClaims.NAME;

import java.util.HashMap;
import java.util.Map;

import com.authlete.sample.server.database.entity.Profile;
import com.authlete.sample.server.database.entity.dao.ProfileDao;
import com.authlete.sample.server.util.AppContext;
import com.google.gson.Gson;


class ClaimCollector
{
    private static final Gson GSON = new Gson();


    private static final ProfileDao DAO = AppContext.getBean("profileDao");


    private String mSubject;
    private String[] mClaimNames;
    private String[] mClaimLocales;


    public ClaimCollector setSubject(String subject)
    {
        mSubject = subject;

        return this;
    }


    public ClaimCollector setClaimNames(String[] names)
    {
        mClaimNames = names;

        return this;
    }


    public ClaimCollector setClaimLocales(String[] locales)
    {
        mClaimLocales = locales;

        return this;
    }


    public String collect()
    {
        // If no claims are requested.
        if (mClaimNames == null)
        {
            return null;
        }

        // Collect the values for each requested claim
        // and then put them into the map.
        final Map<String, Object> claimsMap = doCollect();

        // Convert it into a JSON and return it.
        return GSON.toJson(claimsMap);
    }


    private Map<String, Object> doCollect()
    {
        // Get the subject's profile information form the database.
        final Profile profile = getProfile();

        // The Map holding the values of the requested claims keyed by their names.
        final Map<String, Object> claimsMap = new HashMap<String, Object>();

        // For each requested claims.
        for (String claimName : mClaimNames)
        {
            // Set the map up with the profile information.
            setClaimsMap(claimsMap, claimName, profile);
        }

        return claimsMap;
    }


    private Profile getProfile()
    {
        // Subject must not be null.
        if (mSubject == null)
        {
            throw new IllegalStateException("The subject must not be null.");
        }

        // Get the subject's information from the database.
        return DAO.getByUserId(mSubject);
    }


    private void setClaimsMap(Map<String, Object> claimsMap, String claimName, Profile profile)
    {

        // "NAME" and "GENDER" are the only claims
        // that are supported in this sample server.
        switch (claimName)
        {
            // When "NAME" is requested as a claim.
            case NAME:
                claimsMap.put(NAME, profile.getName());
                break;

            // When "GENDER" is requested as a claim.
            case GENDER:
                claimsMap.put(GENDER, profile.getSex());
                break;

            // Otherwise, just ignore.
            default:
                break;
        }
    }
}
