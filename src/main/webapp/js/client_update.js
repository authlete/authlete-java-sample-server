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
$(function ($) {

    function init_text_input(input)
    {
        _init_texts_input(input);

        // The value of the first element in the inputs.
        var first_val = $("input[name='" + input + "']").val();

        if (first_val === "")
        {
            // If the value is empty, disable the elements.
            _enable_texts_input(input, false);
        }
        else
        {
            _check_enabler(input, true);

            // If the value is not empty, enable the elements.
            _enable_texts_input(input, true);
        }
    }

    function init_tagged_texts_input(input)
    {
        var inputs_id   = input + "_inputs";

        _init_tagged_texts_input(input);

        if ($("#" + inputs_id).children().length > 2)
        {
            _check_enabler(input, true);

            _enable_tagged_texts_input(input, true);
        }
        else
        {
            _enable_tagged_texts_input(input, false);
        }
    }

    function init_types_input(input)
    {
        _init_types_input(input);

        // The flag showing if any of the checked boxes is checked or not.
        checked = $("input[name='" + input + "']").is(":checked");

        if (checked === false)
        {
            // If the value is empty, disable the elements.
            _enable_types_input(input, false);
        }
        else
        {
            _check_enabler(input, true);

            // If the value is not empty, enable the elements.
            _enable_types_input(input, true);
        }
    }

    // Redirect URIs.
    init_text_input("redirect_uris");

    // Response Types.
    init_types_input("response_types");

    // Grant Types.
    init_types_input("grant_types");

    // Application Type.
    init_types_input("application_type");

    // Contacts.
    init_text_input("contacts");

    // Client Names.
    init_tagged_texts_input("client_names");

    // Logo URI.
    init_text_input("logo_uri");

    // Logo URIs.
    init_tagged_texts_input("logo_uris");

    // Client URI.
    init_text_input("client_uri");

    // Client URIs.
    init_tagged_texts_input("client_uris");

    // Policy URI.
    init_text_input("policy_uri");

    // Policy URIs.
    init_tagged_texts_input("policy_uris");

    // Terms of Service URI.
    init_text_input("tos_uri");

    // Terms of Service URIs.
    init_tagged_texts_input("tos_uris");

    // JSON Web Key Set URI.
    init_text_input("jwks_uri");

    // JSON Web Key Set.
    init_text_input("jwks");

    // Sector Identifier.
    init_text_input("sector_identifier");

    // Subject Types.
    init_types_input("subject_type");

    // ID Token Sign Algorithm.
    init_types_input("id_token_sign_alg");

    // ID Token Encryption Algorithm.
    init_types_input("id_token_encryption_alg");

    // ID Token Encryption Encoding Algorithm.
    init_types_input("id_token_encryption_enc");

    // User Info Sign Algorithm.
    init_types_input("user_info_sign_alg");

    // User Info Encryption Algorithm.
    init_types_input("user_info_encryption_alg");

    // User Info Encryption Encoding Algorithm.
    init_types_input("user_info_encryption_enc");

    // Request Sign Algorithm.
    init_types_input("request_sign_alg");

    // Request Encryption Algorithm.
    init_types_input("request_encryption_alg");

    // Request Encryption Encoding Algorithm.
    init_types_input("request_encryption_enc");

    // Token Auth Method.
    init_types_input("token_auth_method");

    // Token Auth Sign Algorithm.
    init_types_input("token_auth_sign_alg");

    // Default Max Age.
    init_text_input("default_max_age");

    // Default Arcs.
    init_text_input("default_acrs");

    // Login URI.
    init_text_input("login_uri");

    // Request URIs.
    init_text_input("request_uris");

    // Description.
    init_text_input("description");

    // Descriptions.
    init_tagged_texts_input("descriptions");

    // Hints.
    _init_hint();
});
