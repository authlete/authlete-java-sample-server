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
package com.authlete.sample.server.web.controller;


import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import com.authlete.common.dto.Client;
import com.authlete.common.dto.TaggedValue;
import com.authlete.common.types.ApplicationType;
import com.authlete.common.types.ClientAuthMethod;
import com.authlete.common.types.ClientType;
import com.authlete.common.types.GrantType;
import com.authlete.common.types.JWEAlg;
import com.authlete.common.types.JWEEnc;
import com.authlete.common.types.JWSAlg;
import com.authlete.common.types.ResponseType;
import com.authlete.common.types.SubjectType;
import com.authlete.sample.server.util.ApiException;
import com.authlete.sample.server.util.RequestValidator;


/**
 * This class has two roles.
 * One is getting the input values to create a client
 * from the information that are given in the client create page.
 * The other one validating the values.<br>
 *
 * Note that the validation part here is just one example,
 * which shows simply how to extract the input parameters.<br>
 *
 * In future, the functions that this class performs might be improved
 * especially in terms of the validation part
 * and will be provided as part of authlete-common library
 * so that you can create clients in more effortlessly.
 */
public class ClientExtractor
{
    /**
     * The parameter names.
     */
    private static final String APPLICATION_TYPE         = "application_type";
    private static final String CLIENT_ID                = "client_id";
    private static final String CLIENT_NAME              = "client_name";
    private static final String CLIENT_NAMES_TAGS        = "client_names_tags";
    private static final String CLIENT_NAMES_VALUES      = "client_names_values";
    private static final String CLIENT_TYPE              = "client_type";
    private static final String CLIENT_URI               = "client_uri";
    private static final String CLIENT_URIS_TAGS         = "client_uris_tags";
    private static final String CLIENT_URIS_VALUES       = "client_uris_values";
    private static final String CONTACTS                 = "contacts";
    private static final String DEFAULT_ACRS             = "default_acrs";
    private static final String DEFAULT_MAX_AGE          = "default_max_age";
    private static final String DESCRIPTION              = "description";
    private static final String DESCRIPTIONS_TAGS        = "descriptions_tags";
    private static final String DESCRIPTIONS_VALUES      = "descriptions_values";
    private static final String DEVELOPER                = "developer";
    private static final String GRANT_TYPES              = "grant_types";
    private static final String ID_TOKEN_ENCRYPTION_ALG  = "id_token_encryption_alg";
    private static final String ID_TOKEN_ENCRYPTION_ENC  = "id_token_encryption_enc";
    private static final String ID_TOKEN_SIGN_ALG        = "id_token_sign_alg";
    private static final String JWKS                     = "jwks";
    private static final String JWKS_URI                 = "jwks_uri";
    private static final String LOGIN_URI                = "login_uri";
    private static final String LOGO_URI                 = "logo_uri";
    private static final String LOGO_URIS_TAGS           = "logo_uris_tags";
    private static final String LOGO_URIS_VALUES         = "logo_uris_values";
    private static final String POLICY_URI               = "policy_uri";
    private static final String POLICY_URIS_TAGS         = "policy_uris_tags";
    private static final String POLICY_URIS_VALUES       = "policy_uris_values";
    private static final String REDIRECT_URIS            = "redirect_uris";
    private static final String REQUEST_ENCRYPTION_ALG   = "request_encryption_alg";
    private static final String REQUEST_ENCRYPTION_ENC   = "request_encryption_enc";
    private static final String REQUEST_SIGN_ALG         = "request_sign_alg";
    private static final String REQUEST_URIS             = "request_uris";
    private static final String RESPONSE_TYPES           = "response_types";
    private static final String SECTOR_IDENTIFIER        = "sector_identifier";
    private static final String SUBJECT_TYPE             = "subject_type";
    private static final String TOKEN_AUTH_METHOD        = "token_auth_method";
    private static final String TOKEN_AUTH_SIGN_ALG      = "token_auth_sign_alg";
    private static final String TOS_URI                  = "tos_uri";
    private static final String TOS_URIS_TAGS            = "tos_uris_tags";
    private static final String TOS_URIS_VALUES          = "tos_uris_values";
    private static final String USER_INFO_ENCRYPTION_ALG = "user_info_encryption_alg";
    private static final String USER_INFO_ENCRYPTION_ENC = "user_info_encryption_enc";
    private static final String USER_INFO_SIGN_ALG       = "user_info_sign_alg";


    /**
     * The parameter max length.
     */
    private static final int CLIENT_NAME_MAX_LENGTH           = 100;
    private static final int CLIENT_URI_MAX_LENGTH            = 200;
    private static final int CONTACT_MAX_LENGTH               = 100;
    private static final int ACR_MAX_LENGTH                   = 200;
    private static final int DESCRIPTION_MAX_LENGTH           = 200;
    private static final int DEVELOPER_MAX_LENGTH             = 100;
    private static final int JWKS_URI_MAX_LENGTH              = 200;
    private static final int LOGIN_URI_MAX_LENGTH             = 200;
    private static final int LOGO_URI_MAX_LENGTH              = 200;
    private static final int POLICY_URI_MAX_LENGTH            = 200;
    private static final int REDIRECT_URI_MAX_LENGTH          = 200;
    private static final int REQUEST_URI_MAX_LENGTH           = 200;
    private static final int SECTOR_IDENTIFIER_MAX_LENGTH     = 200;
    private static final int TOS_URI_MAX_LENGTH               = 200;

    private static final int TAGGED_VALUES_TAG_MAX_LENGTH     = 30;
    private static final int TAGGED_VALUES_VALUE_MAX_LENGTH   = 200;


    private ClientExtractor()
    {
    }


    public static Client extract(MultivaluedMap<String, String> map)
    {
        return buildClient(map);
    }


    public static Client extractIdentifiable(MultivaluedMap<String, String> map)
    {
        return buildClient(map).setClientId(extractClientId(map));
    }


    private static Client buildClient(MultivaluedMap<String, String> map)
    {
        return new Client()
                .setApplicationType(extractApplicationType(map))
                .setClientName(extractClientName(map))
                .setClientNames(extractClientNames(map))
                .setClientType(extractClientType(map))
                .setClientUri(extractClientUri(map))
                .setClientUris(extractClientUris(map))
                .setContacts(extractContacts(map))
                .setDefaultAcrs(extractDefaultAcrs(map))
                .setDefaultMaxAge(extractDefaultMaxAge(map))
                .setDescription(extractDescription(map))
                .setDescriptions(extractDescriptions(map))
                .setDeveloper(extractDeveloper(map))
                .setGrantTypes(extractGrantTypes(map))
                .setIdTokenEncryptionAlg(extractIdTokenEncryptionAlg(map))
                .setIdTokenEncryptionEnc(extractIdTokenEncryptionEnc(map))
                .setIdTokenSignAlg(extractIdTokenSignAlg(map))
                .setJwks(extractJwks(map))
                .setJwksUri(extractJwksUri(map))
                .setLoginUri(extractLoginUri(map))
                .setLogoUri(extractLogoUri(map))
                .setLogoUris(extractLogoUris(map))
                .setPolicyUri(extractPolicyUri(map))
                .setPolicyUris(extractPolicyUris(map))
                .setRedirectUris(extractRedirectUris(map))
                .setRequestEncryptionAlg(extractRequestEncryptionAlg(map))
                .setRequestEncryptionEnc(extractRequestEncryptionEnc(map))
                .setRequestSignAlg(extractRequestSignAlg(map))
                .setRequestUris(extractRequestUris(map))
                .setResponseTypes(extractResponseTypes(map))
                .setSectorIdentifier(extractSectorIdentifier(map))
                .setSubjectType(extractSubjectType(map))
                .setTokenAuthMethod(extractTokenAuthMethod(map))
                .setTokenAuthSignAlg(extractTokenAuthSignAlg(map))
                .setTosUri(extractTosUri(map))
                .setTosUris(extractTosUris(map))
                .setUserInfoEncryptionAlg(extractUserInfoEncryptionAlg(map))
                .setUserInfoEncryptionEnc(extractUserInfoEncryptionEnc(map))
                .setUserInfoSignAlg(extractUserInfoSignAlg(map))
                ;
    }


    //----------------------
    // Common Methods
    //----------------------

    private static String extractString(MultivaluedMap<String, String> map, String name, int max, boolean required)
    {
        // Get the first element as the value.
        final String value = map.getFirst(name);

        // If the value is required one, validate that it is not null.
        if (required == true)
        {
            RequestValidator.validateNotNull(name, value);
        }

        // If the value is null,
        // meaning the user did not specify the value, return null.
        if (value == null)
        {
            return null;
        }

        // Validate the max length of the string.
        RequestValidator.validateMaxLength(name, value, max);

        return value;
    }


    private static String extractRequiredString(MultivaluedMap<String, String> map, String name, int max)
    {
        // Extract a string value as a required value.
        return extractString(map, name, max, true);
    }


    private static String extractOptionalString(MultivaluedMap<String, String> map, String name, int max)
    {
        // Extract a string value as an optional value.
        return extractString(map, name, max, false);
    }


    private static String[] extractStringArray(MultivaluedMap<String, String> map, String name, int max)
    {
        // Get the values as a list.
        final List<String> values = map.get(name);

        // If the value is null,
        // meaning the user did not specify the value,
        // or size-zero, return null.
        if (values == null || values.size() == 0)
        {
            return null;
        }

        // The set to hold validated values.
        final Set<String> validatedValues = new HashSet<String>();

        // For each element in the list.
        for (int i=0; i<values.size(); i++)
        {
            // Get the i-th element.
            final String value = values.get(i);

            // Validate that the value is not null or blank.
            RequestValidator.validateNotNullAndNotBlank(name, value);

            // Validate that the length of the value.
            RequestValidator.validateMaxLength(name, value, max);

            // Validate that the value is not duplicated.
            RequestValidator.validateNotDuplicateInArray(name, value, validatedValues);

            // Add the value as the validated one.
            validatedValues.add(value);
        }

        // Convert the set to an array.
        return validatedValues.toArray(new String[validatedValues.size()]);
    }


    private static URI extractUri(MultivaluedMap<String, String> map, String name, int max)
    {
        // Get the first element as the value.
        final String value = map.getFirst(name);

        // If the value is null,
        // meaning the user did not specify the value, return null.
        if (value == null)
        {
            return null;
        }

        // Validate the length of the value.
        RequestValidator.validateMaxLength(name, value, max);

        try
        {
            // Return the URI based on the value.
            return new URI(value);
        }
        catch (Throwable t)
        {
            // The value is malformed as an URI.
            final String message = String.format("%s is malformed as an URI.", value);

            // 400 Bad Request.
            throw ApiException.badRequest(message);
        }
    }


    private static TaggedValue[] extractTaggedValues(MultivaluedMap<String, String> map, String tagsName, String valuesName)
    {
        // Get the tags as a list.
        final List<String> tags   = map.get(tagsName);

        // Get the values as a list.
        final List<String> values = map.get(valuesName);

        // If either or both of the tags and the values are null or size zero,
        // return null.
        if (tags == null || values == null || tags.size() == 0 || values.size() == 0)
        {
            return null;
        }

        // The set to hold validated tags.
        final Set<String>      validatedTags   = new HashSet<String>();

        // The set to hold validated values.
        final Set<TaggedValue> validatedValues = new HashSet<TaggedValue>();

        // For each element.
        // Note that the i-th element of the tags
        // is assumed to be associated with the i-th element of the values.
        for (int i=0; i<values.size(); i++)
        {
            // Extract the i-th tag.
            final String tag   = extractTag(tagsName, tags, i, validatedTags);

            // Extract the i-th value.
            final String value = extractValue(valuesName, values, i);

            // Add them as the validated one.
            validatedValues.add(new TaggedValue(tag, value));
        }

        return validatedValues.toArray(new TaggedValue[validatedValues.size()]);
    }


    private static String extractTag(String tagsName, List<String> tags, int index, Set<String> validatedTags)
    {
        // Get the value with the index.
        final String tag = tags.get(index);

        // Validate that the value is not null or blank.
        RequestValidator.validateNotNullAndNotBlank(tagsName, tag);

        // Validate that the length of the value.
        RequestValidator.validateMaxLength(tagsName, tag, TAGGED_VALUES_TAG_MAX_LENGTH);

        // Validate that the value is not duplicated.
        RequestValidator.validateNotDuplicateInArray(tagsName, tag, validatedTags);

        // Add the value as the validated one.
        validatedTags.add(tag);

        return tag;
    }


    private static String extractValue(String valuesName, List<String> values, int index)
    {
        // Get the value with the index.
        final String value = values.get(index);

        // Validate that the value is not null or blank.
        RequestValidator.validateNotNullAndNotBlank(valuesName, value);

        // Validate that the length of the value.
        RequestValidator.validateMaxLength(valuesName, value, TAGGED_VALUES_VALUE_MAX_LENGTH);

        return value;
    }


    private static JWSAlg extractJWSAlg(MultivaluedMap<String, String> map, String name)
    {
        // Get the first element and parse it to a JWSAlg.
        return JWSAlg.parse(map.getFirst(name));
    }


    private static JWEAlg extractJWEAlg(MultivaluedMap<String, String> map, String name)
    {
        // Get the first element and parse it to a JWEAlg.
        return JWEAlg.parse(map.getFirst(name));
    }


    private static JWEEnc extractJWEEnc(MultivaluedMap<String, String> map, String name)
    {
        // Get the first element and parse it to a JWEEnc.
        return JWEEnc.parse(map.getFirst(name));
    }


    //---------------------------------
    // Extracting Specific Parameters
    //----------------------------------

    private static ApplicationType extractApplicationType(MultivaluedMap<String, String> map)
    {
        // Get the 'application_type' and parse it to an ApplicationType.
        return ApplicationType.parse(map.getFirst(APPLICATION_TYPE));
    }


    private static String extractClientName(MultivaluedMap<String, String> map)
    {
        // Get the 'client_name'.
        return extractRequiredString(map, CLIENT_NAME, CLIENT_NAME_MAX_LENGTH);
    }


    private static TaggedValue[] extractClientNames(MultivaluedMap<String, String> map)
    {
        // Get the 'client_names'.
        return extractTaggedValues(map, CLIENT_NAMES_TAGS, CLIENT_NAMES_VALUES);
    }


    private static ClientType extractClientType(MultivaluedMap<String, String> map)
    {
        // Get the 'client_type' and parse it to a ClientType.
        final ClientType value = ClientType.parse(map.getFirst(CLIENT_TYPE));

        // Validate that it is not null.
        RequestValidator.validateNotNull(CLIENT_TYPE, value);

        return value;
    }


    private static URI extractClientUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'client_uri'.
        return extractUri(map, CLIENT_URI, CLIENT_URI_MAX_LENGTH);
    }


    private static TaggedValue[] extractClientUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'client_uris'.
        return extractTaggedValues(map, CLIENT_URIS_TAGS, CLIENT_URIS_VALUES);
    }


    private static String[] extractContacts(MultivaluedMap<String, String> map)
    {
        // Extract the 'contacts'.
        return extractStringArray(map, CONTACTS, CONTACT_MAX_LENGTH);
    }


    private static String[] extractDefaultAcrs(MultivaluedMap<String, String> map)
    {
        // Extract the 'default_acrs'.
        return extractStringArray(map, DEFAULT_ACRS, ACR_MAX_LENGTH);
    }


    private static int extractDefaultMaxAge(MultivaluedMap<String, String> map)
    {
        try
        {
            // Get the 'default_max_age' and parse it into an int.
            final int value = Integer.parseInt(map.getFirst(DEFAULT_MAX_AGE));

            // Validate that the value is not negative.
            RequestValidator.validateNotNegative(DEFAULT_MAX_AGE, value);

            return value;
        }
        catch (Throwable t)
        {
            // Failed to parse the value into an int.
            return 0;
        }
    }


    private static String extractDescription(MultivaluedMap<String, String> map)
    {
        // Extract the 'description'.
        return extractOptionalString(map, DESCRIPTION, DESCRIPTION_MAX_LENGTH);
    }


    private static TaggedValue[] extractDescriptions(MultivaluedMap<String, String> map)
    {
        // Extract the 'descriptions'.
        return extractTaggedValues(map, DESCRIPTIONS_TAGS, DESCRIPTIONS_VALUES);
    }


    private static GrantType[] extractGrantTypes(MultivaluedMap<String, String> map)
    {
        // Get the 'grant_types' as a list.
        final List<String> values = map.get(GRANT_TYPES);

        // If the value is null,
        // meaning the user did not specify the value,
        // or size-zero, return null.
        if (values == null || values.size() == 0)
        {
            return null;
        }

        // The set to hold validated values.
        final Set<GrantType> validatedValues = new HashSet<GrantType>();

        // For each element.
        for (int i=0; i<values.size(); i++)
        {
            // Get the i-th element.
            final String value = values.get(i);

            // Validate that the value is not null or blank.
            RequestValidator.validateNotNullAndNotBlank(GRANT_TYPES, value);

            // Validate that the value is not duplicated.
            RequestValidator.validateNotDuplicateInArray(GRANT_TYPES, value, validatedValues);

            // Add it as a validated value.
            validatedValues.add( GrantType.parse(values.get(i)) );
        }

        return validatedValues.toArray(new GrantType[validatedValues.size()]);
    }


    private static JWEAlg extractIdTokenEncryptionAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'id_token_encryption_alg'.
        return extractJWEAlg(map, ID_TOKEN_ENCRYPTION_ALG);
    }


    private static JWEEnc extractIdTokenEncryptionEnc(MultivaluedMap<String, String> map)
    {
        // Extract the 'id_token_encryption_enc'.
        return extractJWEEnc(map, ID_TOKEN_ENCRYPTION_ENC);
    }


    private static JWSAlg extractIdTokenSignAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'id_token_sign_alg'.
        return extractJWSAlg(map, ID_TOKEN_SIGN_ALG);
    }


    private static String extractJwks(MultivaluedMap<String, String> map)
    {
        // Extract the 'jwks'.
        return extractOptionalString(map, JWKS, JWKS_URI_MAX_LENGTH);
    }


    private static URI extractJwksUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'jwks_uri'
        return extractUri(map, JWKS_URI, JWKS_URI_MAX_LENGTH);
    }


    private static URI extractLoginUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'login_uri'.
        return extractUri(map, LOGIN_URI, LOGIN_URI_MAX_LENGTH);
    }


    private static URI extractLogoUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'logo_uri'.
        return extractUri(map, LOGO_URI, LOGO_URI_MAX_LENGTH);
    }


    private static TaggedValue[] extractLogoUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'logo_uris_tags'.
        return extractTaggedValues(map, LOGO_URIS_TAGS, LOGO_URIS_VALUES);
    }


    private static URI extractPolicyUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'policy_uri'.
        return extractUri(map, POLICY_URI, POLICY_URI_MAX_LENGTH);
    }


    private static TaggedValue[] extractPolicyUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'policy_uris_tags'.
        return extractTaggedValues(map, POLICY_URIS_TAGS, POLICY_URIS_VALUES);
    }


    private static String[] extractRedirectUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'redirect_uris'.
        return extractStringArray(map, REDIRECT_URIS, REDIRECT_URI_MAX_LENGTH);
    }


    private static JWEAlg extractRequestEncryptionAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'request_encryption_alg'.
        return extractJWEAlg(map, REQUEST_ENCRYPTION_ALG);
    }


    private static JWEEnc extractRequestEncryptionEnc(MultivaluedMap<String, String> map)
    {
        // Extract the 'request_encryption_enc'.
        return extractJWEEnc(map, REQUEST_ENCRYPTION_ENC);
    }


    private static JWSAlg extractRequestSignAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'request_sign_alg'.
        return extractJWSAlg(map, REQUEST_SIGN_ALG);
    }


    private static String[] extractRequestUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'request_uris'.
        return extractStringArray(map, REQUEST_URIS, REQUEST_URI_MAX_LENGTH);
    }


    private static ResponseType[] extractResponseTypes(MultivaluedMap<String, String> map)
    {
        // Get the 'response_types'.
        final List<String> values = map.get(RESPONSE_TYPES);

        // If the value is null,
        // meaning the user did not specify the value, return null.
        if (values == null)
        {
            return null;
        }

        // The set to hold validated values.
        final Set<ResponseType> validatedValues = new HashSet<ResponseType>();

        // For each element.
        for (int i=0; i<values.size(); i++)
        {
            // Get the i-th element.
            final String value = values.get(i);

            // Validate that the value is not null or blank.
            RequestValidator.validateNotNullAndNotBlank(GRANT_TYPES, value);

            // Validate that the value is not duplicated.
            RequestValidator.validateNotDuplicateInArray(GRANT_TYPES, value, validatedValues);

            // Add it as a validate value.
            validatedValues.add( ResponseType.parse(values.get(i)) );
        }

        // Convert the set to an array.
        return validatedValues.toArray(new ResponseType[validatedValues.size()]);
    }


    private static URI extractSectorIdentifier(MultivaluedMap<String, String> map)
    {
        // Extract the 'sector_identifier'.
        return extractUri(map, SECTOR_IDENTIFIER, SECTOR_IDENTIFIER_MAX_LENGTH);
    }


    private static SubjectType extractSubjectType(MultivaluedMap<String, String> map)
    {
        // Extract the 'subject_type'.
        return SubjectType.parse(map.getFirst(SUBJECT_TYPE));
    }


    private static ClientAuthMethod extractTokenAuthMethod(MultivaluedMap<String, String> map)
    {
        // Extract the 'token_auth_method'.
        return ClientAuthMethod.parse(map.getFirst(TOKEN_AUTH_METHOD));
    }


    private static JWSAlg extractTokenAuthSignAlg(MultivaluedMap<String, String> map)
    {
        return extractJWSAlg(map, TOKEN_AUTH_SIGN_ALG);
    }


    private static URI extractTosUri(MultivaluedMap<String, String> map)
    {
        // Extract the 'tos_uri'.
        return extractUri(map, TOS_URI, TOS_URI_MAX_LENGTH);
    }


    private static TaggedValue[] extractTosUris(MultivaluedMap<String, String> map)
    {
        // Extract the 'tos_uris_tags'.
        return extractTaggedValues(map, TOS_URIS_TAGS, TOS_URIS_VALUES);
    }


    private static JWEAlg extractUserInfoEncryptionAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'user_info_encryption_alg'.
        return extractJWEAlg(map, USER_INFO_ENCRYPTION_ALG);
    }


    private static JWEEnc extractUserInfoEncryptionEnc(MultivaluedMap<String, String> map)
    {
        // Extract the 'user_info_encryption_enc'.
        return extractJWEEnc(map, USER_INFO_ENCRYPTION_ENC);
    }


    private static JWSAlg extractUserInfoSignAlg(MultivaluedMap<String, String> map)
    {
        // Extract the 'user_info_sign_alg'.
        return extractJWSAlg(map, USER_INFO_SIGN_ALG);
    }


    private static long extractClientId(MultivaluedMap<String, String> map)
    {
        try
        {
            // Get the 'client_id' and parse it into a long.
            final long value = Long.parseLong(map.getFirst(CLIENT_ID));

            // Validate that the value is not negative.
            RequestValidator.validateNotNegative(CLIENT_ID, value);

            return value;
        }
        catch (Throwable t)
        {
            // Failed to parse the value into long.
            return 0;
        }
    }


    private static String extractDeveloper(MultivaluedMap<String, String> map)
    {
        // Extract the 'developer'.
        return extractRequiredString(map, DEVELOPER, DEVELOPER_MAX_LENGTH);
    }
}
