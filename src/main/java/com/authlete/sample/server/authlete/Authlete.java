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
package com.authlete.sample.server.authlete;


import com.authlete.common.api.AuthleteApi;
import com.authlete.common.api.AuthleteApiFactory;
import com.authlete.common.conf.AuthleteConfiguration;
import com.authlete.common.conf.AuthletePropertiesConfiguration;


public class Authlete
{
    /**
     * Secret key given to a cipher which decrypts encrypted property
     * values in the configuration file.
     */
    private static final String SECRET_KEY = "a281ac2de1195e8c91ea383d38d05d1c";


    /**
     * Initial vector given to a cipher which decrypts encrypted property
     * values in the configuration file.
     */
    private static final String INITIAL_VECTOR = "b6f5d0f0dd7146b0e3915ebd2dd078f3";


    /**
     * Authlete API.
     */
    private static final AuthleteApi mAuthleteApi = createAuthleteApi();


    /**
     * Create Authlete API.
     */
    private static AuthleteApi createAuthleteApi()
    {
        // Load the configuration file.
        AuthleteConfiguration configuration
            = new AuthletePropertiesConfiguration(SECRET_KEY, INITIAL_VECTOR);

        // Create AuthleteApi using the configuration. The factory implementation
        // searches for a known implementation of AuthleteApi.
        return AuthleteApiFactory.getInstance(configuration);
    }


    /**
     * Get Authlete API.
     *
     * @return
     *         {@link AuthleteApi} instance.
     */
    public static AuthleteApi getApi()
    {
        return mAuthleteApi;
    }
}
