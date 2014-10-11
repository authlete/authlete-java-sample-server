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
package com.authlete.sample.server.web.descriptions;


public class Descriptions
{
    public static final String  APPLICATION_TYPE =
            "The application type. WEB, NATIVE or null. " +
            "The value of this property affects the validation steps for a redirect URI. " +
            "See the description about redirectUris property above. " +
            "This property corresponds to application_type " +
            "in OpenID Connect Dynamic Client ReGistration 1.0, 2. Client Metadata.";


    public static final String CLIENT_NAME =
            "The name of the client application. At most 100 unicode letters. " +
            "This property corresponds to client_name in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String CLIENT_NAMES =
            "Client names with language tags. If the client application has different names for different languages, " +
            "this property can be used to register the names.";


    public static final String CLIENT_TYPE =
            "The client type, either CONFIDENTIAL or PUBLIC. See RFC 6749, 2.1. Client Types for details.";


    public static final String CLIENT_URI =
            "The URL pointing to the home page of the client application. " +
            "The URL must consist of printable ASCII letters only and its length must not exceed 200." +
            "This property corresponds to client_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String CLIENT_URIS =
            "Home page URLs with language tags. " +
            "If the client application has different home pages for different languages, " +
            "this property can be used to register the URLs.";


    public static final String CONTANCTS =
            "An array of email addresses of people responsible for the client application. " +
            "Each element must consist of printable ASCII letters only and its length must not exceed 100. " +
            "This property corresponds to contacts in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String DEFAULT_ACRS =
            "The default ACRs (Authentication Context Class References). " +
            "This value is used when an authorization request " +
            "from the client application has neither acr_values request parameter nor acr claim in claims request parameter. " +
            "Each element must consist of printable ASCII letters only and its length must not exceed 200.";


    public static final String DEFAULT_MAX_AGE =
            "The default maximum authentication age in seconds. " +
            "This value is used when an authorization request " +
            "from the client application does not have max_age request parameter. " +
            "This property corresponds to default_max_age in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String DESCRIPTION =
            "The description about the client application. At most 200 letters in unicode.";


    public static final String DESCRIPTIONS =
            "Descriptions about the client application with language tags. " +
            "If the client application has different descriptions for different languages, " +
            "this property can be used to register the descriptions.";


    public static final String GRANT_TYPES =
            "A string array of grant types which the client application declares that it will restrict itself to using. " +
            "This property corresponds to grant_types in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String ID_TOKEN_ENCRYPTION_ALG =
            "The value of alg header parameter of JWE " +
            "that the client application requires the service to use for encrypting an ID token." +
            "One of the supported values listed in JWE Algorithm. " +
            "The default value is null, meaning that an ID token is not encrypted. " +
            "When idTokenEncryptionEnc is not null, this property must not be null." +
            "If the value of this property indicates an asymmetric encryption algorithm, " +
            "the client application must make available its JWK Set " +
            "which contains a public key for encryption at the URL referred to by its jwksUri configuration property. " +
            "This property corresponds to id_token_encrypted_response_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String ID_TOKEN_ENCRYPTION_ENC =
            "The value of enc header parameter of JWE " +
            "that the client application requires the service to use for encrypting an ID token. " +
            "One of the values listed in JWE Encryption Algorithm. " +
            "The default value is (1) A128CBC_HS256 when idTokenEncryptionAlg is not null, " +
            "or (2) null when idTokenEncryptionAlg is null. " +
            "This property corresponds to id_token_encrypted_response_enc in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String ID_TOKEN_SIGN_ALG = "The value of alg header parameter of JWS " +
            "that the client application requires the service to use for signing an ID token. " +
            "One of the values listed in JWS Algorithm. " +
            "The default value is RS256. " +
            "NONE may be specified, but in that case, " +
            "the client application cannot obtain an ID token from the service. " +
            "That is, an authorization request requesting an ID token fails." +
            "This property corresponds to id_token_signed_response_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String JWKS =
            "The content of the JWK Set of the client application. " +
            "The format is described in 'JSON Web Key (JWK), 5. JSON Web Key Set (JWK Set) Format'. " +
            "Of course, the JWK Set must not include private keys of the client application.";


    public static final String JWKS_URI =
            "The URL pointing to the JWK Set of the client application. " +
            "The URL must consist of printable ASCII letters only and its length must not exceed 200. " +
            "The content pointed to by the URL must be JSON which complies with the format described " +
            "in 'JSON Web Key (JWK), 5. JSON Web Key Set (JWK Set) Format'. " +
            "Of course, the JWK Set must not include private keys of the client application. " +
            "This property corresponds to jwks_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String LOGIN_URI =
            "The URL which a third party can use to initiate a login by the client application. " +
            "The URL must start with https and consist of ASCII letters only. " +
            "Its length must not exceed 200. " +
            "This property corresponds to initiate_login_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String LOGO_URI =
            "The URL pointing to the logo image of the client application. The URL must consist of printable ASCII letters only " +
            "and its length must not exceed 200. " +
            "This property corresponds to logo_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String LOGO_URIS =
            "Logo image URLs with language tags. " +
            "If the client application has different logo images for different languages, " +
            "this property can be used to register URLs of the images.";


    public static final String POLICY_URI =
            "The URL pointing to the page which describes the policy as to how end-users' profile data are used. " +
            "The URL must consist of printable ASCII letters only and its length must not exceed 200. " +
            "This property corresponds to policy_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";

    public static final String POLICY_URIS =
            "URLs of policy pages with language tags. " +
            "If the client application has different policy pages for different languages, " +
            "this property can be used to register the URLs.";


    public static final String REDIRECT_URIS =
            "Redirect URIs that the client application uses to receive a response from the authorization endpoint.";


    public static final String REQUEST_ENCRYPTION_ALG =
            "The value of alg header parameter of JWE " +
            "that the client application uses for encrypting a request object. " +
            "One of the supported values listed in JWE Algorithm. " +
            "The default value is null. " +
            "When requestEncryptionEnc is not null, this property must not be null." +
            "Regardless of whether the value of this property is null or not, " +
            "the client application may and may not encrypt a request object. " +
            "Furthermore, the client application may use other supported encryption algorithms." +
            "This property corresponds to request_object_encryption_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String REQUEST_ENCRYPTION_ENC =
            "The value of enc header parameter of JWE " +
            "that the client application uses for encrypting a request object." +
            " One of the values listed in JWE Encryption Algorithm. " +
            "The default value is (1) A128CBC_HS256 when requestEncryptionAlg is not null, " +
            "or (2) null when requestEncryptionAlg is null. " +
            "This property corresponds to request_object_encryption_enc in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String REQUEST_SIGN_ALG =
            "The value of alg header parameter of JWS " +
            "that the client application uses for signing a request object. " +
            "One of the values listed in JWS Algorithm. The default value is null, " +
            "meaning that the client application may use any algorithm (among those supported by the service) " +
            "to sign a request object (including none)." +
            "If the value of this property is not null, " +
            "request objects sent from the client application must be signed using the algorithm. " +
            "Request objects signed by other algorithms are rejected. " +
            "Note that null and NONE are different for this property. "+
            "If the value of this property indicates an asymmetric signing algorithm, " +
            "the client application must make available its JWK Set " +
            "which contains a public key for the service " +
            "to verify the signature of the request object at the URL referred to " +
            "by its jwksUri configuration property. " +
            "This property corresponds to request_object_signing_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String REQUEST_URIS =
            "An array of URLs each of which points to a request object. " +
            "Each URL must consist of printable ASCII letters only and its length must not exceed 200.";


    public static final String RESPONSE_TYPES =
            "A string array of response types which the client application declares that it will restrict itself to using." +
            " This property corresponds to response_types in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String SECTOR_IDENTIFIER =
            "The sector identifier which is a URL starting with https. " +
            "The URL must consist of printable ASCII letters only and its length must not exceed 200. " +
            "This URL is used by the service to calculate pairwise subject values. " +
            "See OpenID Connect Core 1.0, 8.1. Pairwise Identifier Algorithm. " +
            "This property corresponds to sector_identifier_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String SUBJECT_TYPE =
            "The subject type that the client application requests. " +
            "Either PUBLIC or PAIRWISE. the default value is PUBLIC. " +
            "Details about the subject type are described in OpenID Connect Core 1.0, 8. Subjct Identifier Types. " +
            "This property corresponds to subject_type in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String TOKEN_AUTH_METHOD =
            "The client authentication method " +
            "that the client application declares that it uses at the token endpoint. " +
            "One of the values listed in Client Authentication Method. " +
            "The default value is CLIENT_SECRET_BASIC. " +
            "This property corresponds to token_endpoint_auth_method in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String TOKEN_AUTH_SIGN_ALG =
            "The value of alg header parameter of JWS " +
            "which is used for client authentication at the token endpoint. " +
            "One of the values listed in JWS Algorithm except NONE. " +
            "The default value is null, " +
            "meaning that the client may sign using any algorithm which is supported by the service. " +
            "If the value of this property is not null, the client application must use the algorithm. " +
            "This property corresponds to token_endpoint_auth_signing_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String TOS_URI =
            "The URL pointing to the 'Terms Of Service' page. " +
            "The URL must consist of printable ASCII letters only and its length must not exceed 200. " +
            "This property corresponds to tos_uri in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String TOS_URIS =
            "URLs of 'Terms Of Service' pages with language tags. " +
            "If the client application has different 'Terms Of Service' pages for different languages, " +
            "this property can be used to register the URLs.";


    public static final String USER_INFO_ENCRYPTION_ALG =
            "The value of alg header parameter of JWE " +
            "that the client application requires the service to use for encrypting the JWT returned from the user info endpoint. " +
            "One of the supported values listed in JWE Algorithm. " +
            "The default value is null, meaning that the data from the user info endpoint is not encrypted. " +
            "When userInfoEncryptionEnc is not null, this property must not be null. " +
            "If the value of this property indicates an asymmetric encryption algorithm, " +
            "the client application must make available its JWK Set " +
            "which contains a public key for encryption at the URL referred to by its jwksUri configuration property." +
            "If both userInfoSignAlg and userInfoEncryptionAlg are null, " +
            "the format of the response from the user info endpoint is a plain JSON (not JWT). " +
            "This property corresponds to userinfo_encrypted_response_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String USER_INFO_ENCRYPTION_ENC =
            "The value of enc header parameter of JWE " +
            "that the client application requires the service to use for encrypting the JWT returned from the user info endpoint. " +
            "One of the values listed in JWE Encryption Algorithm. " +
            "The default value is (1) A128CBC_HS256 when userInfoEncryptionAlg is not null, " +
            "or (2) null when userInfoEncryptionAlg is null. " +
            "This property corresponds to userinfo_encrypted_response_enc in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";


    public static final String USER_INFO_SIGN_ALG =
            "The value of alg header parameter of JWS " +
            "that the client application requires the service to use for signing the JWT returned from the user info endpoint. " +
            "One of the values listed in JWS Algorithm. " +
            "The default value is null, meaning that the data from the user info endpoint is not signed. " +
            "If both userInfoSignAlg and userInfoEncryptionAlg are null, " +
            "the format of the response from the user info endpoint is a plain JSON (not JWT). " +
            "Note that null and NONE are different for this property." +
            "This property corresponds to userinfo_signed_response_alg in OpenID Connect Dynamic Client Registration 1.0, 2. Client Metadata.";
}
