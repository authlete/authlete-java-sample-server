<!--
Copyright 2014 Authlete, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
<%@ include file="/jsp/common_setting.jsp" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/update" %>
<%@ page import="com.authlete.common.types.ApplicationType"%>
<%@ page import="com.authlete.common.types.ClientType"%>
<%@ page import="com.authlete.common.types.GrantType"%>
<%@ page import="com.authlete.common.types.ResponseType"%>
<%@ page import="com.authlete.common.types.SubjectType"%>
<%@ page import="com.authlete.common.types.JWSAlg"%>
<%@ page import="com.authlete.common.types.JWEAlg"%>
<%@ page import="com.authlete.common.types.JWEEnc"%>
<%@ page import="com.authlete.common.types.ClientAuthMethod"%>
<%@ page import="com.authlete.sample.server.web.descriptions.Descriptions" %>
<%@ page import="java.util.Locale"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Authlete Sample Server</title>
        <%@ include file="/jsp/common_meta.jsp" %>
        <%@ include file="/jsp/common_css.jsp" %>
        <link href="/authlete-sample-server/css/client_change.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <div class="container">

            <div class="well well-lg">

                <h1 class="page-title">Update Your Application</h1>

                <form class="form-horizontal app-details" action='<c:url value="/user/develop/${model.clientId}/update"/>' method="POST" accept-charset="UTF-8" >

                    <fieldset>

                        <legend>Your Application Details</legend>

                        <!-- Client Type -->
                        <t:type paramName="Client Type" inputName="client_type" types="<%= ClientType.values()%>" existingValue="${model.clientType}" required="true" description="<%= Descriptions.CLIENT_TYPE %>"></t:type>

                        <!-- Redirect URIs -->
                        <t:texts paramName="Redirect URIs" inputName="redirect_uris" existingValues="${model.redirectUris}" description="<%= Descriptions.REDIRECT_URIS %>"></t:texts>

                        <!-- Response Types -->
                        <t:types paramName="Response Types" inputName="response_types" existingValues="${model.responseTypes}" types="<%= ResponseType.values()%>" description="<%= Descriptions.RESPONSE_TYPES %>"></t:types>

                        <!-- Grant Types -->
                        <t:types paramName="Grant Types" inputName="grant_types" existingValues="${model.grantTypes}" types="<%= GrantType.values()%>" description="<%= Descriptions.GRANT_TYPES %>"></t:types>

                        <!-- Application Type -->
                        <t:type paramName="Application Type" inputName="application_type" types="<%= ApplicationType.values()%>" existingValue="${model.applicationType}" description="<%= Descriptions.APPLICATION_TYPE %>"></t:type>

                        <!-- Contacts -->
                        <t:texts paramName="Contacts" inputName="contacts" existingValues="${model.contacts}" description="<%= Descriptions.CONTANCTS %>"></t:texts>

                        <!-- Client Name -->
                        <t:text paramName="Client Name" inputName="client_name" existingValue="${model.clientName}" required="true" description="<%= Descriptions.CLIENT_NAME %>"></t:text>

                        <!-- Client Names -->
                        <t:tagged_texts paramName="Client Names" inputName="client_names" existingValues="${model.clientNames}" description="<%= Descriptions.CLIENT_NAMES %>" formId="client_update"></t:tagged_texts>

                        <!-- Logo URI -->
                        <t:text paramName="Logo URI" inputName="logo_uri" existingValue="${model.logoUri}" description="<%= Descriptions.LOGO_URI %>"></t:text>

                        <!-- Logo URIs -->
                        <t:tagged_texts paramName="Logo URIs" inputName="logo_uris" existingValues="${model.logoUris}" description="<%= Descriptions.LOGO_URIS %>" formId="client_update"></t:tagged_texts>

                        <!-- Client URI -->
                        <t:text paramName="Client URI" inputName="client_uri" existingValue="${model.clientUri}" description="<%= Descriptions.CLIENT_URI %>"></t:text>

                        <!-- Client URIs -->
                        <t:tagged_texts paramName="Client URIs" inputName="client_uris" existingValues="${model.clientUris}" description="<%= Descriptions.CLIENT_URIS %>" formId="client_update"></t:tagged_texts>

                        <!-- Policy URI -->
                        <t:text paramName="Policy URI" inputName="policy_uri" existingValue="${model.policyUri}" description="<%= Descriptions.POLICY_URI %>"></t:text>

                        <!-- Policy URIs -->
                        <t:tagged_texts paramName="Policy URIs" inputName="policy_uris" existingValues="${model.policyUris}" description="<%= Descriptions.POLICY_URIS %>" formId="client_update"></t:tagged_texts>

                        <!-- Terms of Service URI -->
                        <t:text paramName="Terms of Service URI" inputName="tos_uri" existingValue="${model.tosUri}" description="<%= Descriptions.TOS_URI %>"></t:text>

                        <!-- Terms of Service URIs -->
                        <t:tagged_texts paramName="Terms of Service URIs" inputName="tos_uris" existingValues="${model.tosUris}" description="<%= Descriptions.TOS_URIS %>" formId="client_update"></t:tagged_texts>

                        <!-- JSON Web Key Set URI -->
                        <t:text paramName="JSON Web Key Set URI" inputName="jwks_uri" existingValue="${model.jwksUri}" description="<%= Descriptions.JWKS_URI %>"></t:text>

                        <!-- JSON Web Key Set-->
                        <t:text paramName="JSON Web Key Set" inputName="jwks" existingValue="${model.jwks}" description="<%= Descriptions.JWKS %>"></t:text>

                        <!-- Sector Identifier -->
                        <t:text paramName="Sector Identifier" inputName="sector_identifier" existingValue="${model.sectorIdentifier}" description="<%= Descriptions.SECTOR_IDENTIFIER %>"></t:text>

                        <!-- Subject Type -->
                        <t:type paramName="Subject Type" inputName="subject_type" types="<%= SubjectType.values()%>" existingValue="${model.subjectType}" description="<%= Descriptions.SUBJECT_TYPE %>"></t:type>

                        <!-- ID Token Sign Algorithm -->
                        <t:type paramName="ID Token Sign Algorithm" inputName="id_token_sign_alg" types="<%= JWSAlg.values()%>" existingValue="${model.idTokenSignAlg}" description="<%= Descriptions.ID_TOKEN_SIGN_ALG %>"></t:type>

                        <!-- ID Token Encryption Algorithm -->
                        <t:type paramName="ID Token Encryption Algorithm" inputName="id_token_encryption_alg" types="<%= JWEAlg.values()%>" existingValue="${model.idTokenEncryptionAlg}" description="<%= Descriptions.ID_TOKEN_ENCRYPTION_ALG %>"></t:type>

                        <!-- ID Token Encryption Encoding Algorithm -->
                        <t:type paramName="ID Token Encryption Encoding Algorithm" inputName="id_token_encryption_enc" types="<%= JWEEnc.values()%>" existingValue="${model.idTokenEncryptionEnc}" description="<%= Descriptions.ID_TOKEN_ENCRYPTION_ENC %>"></t:type>

                        <!-- User Info Sign Algorithm -->
                        <t:type paramName="User Info Sign Algorithm" inputName="user_info_sign_alg" types="<%= JWSAlg.values()%>" existingValue="${model.userInfoSignAlg}" description="<%= Descriptions.USER_INFO_SIGN_ALG %>"></t:type>

                        <!-- User Info Encryption Algorithm -->
                        <t:type paramName="User Info Encryption Algorithm" inputName="user_info_encryption_alg" types="<%= JWEAlg.values()%>" existingValue="${model.userInfoEncryptionAlg}" description="<%= Descriptions.USER_INFO_ENCRYPTION_ALG %>"></t:type>

                        <!-- User Info Encryption Encoding Algorithm -->
                        <t:type paramName="User Info Encryption Encoding Algorithm" inputName="user_info_encryption_enc" types="<%= JWEEnc.values()%>" existingValue="${model.userInfoEncryptionEnc}" description="<%= Descriptions.USER_INFO_ENCRYPTION_ENC %>"></t:type>

                        <!-- Request Sign Algorithm -->
                        <t:type paramName="Request Sign Algorithm" inputName="request_sign_alg" types="<%= JWSAlg.values()%>" existingValue="${model.requestSignAlg}" description="<%= Descriptions.REQUEST_SIGN_ALG %>"></t:type>

                        <!-- Request Encryption Algorithm -->
                        <t:type paramName="Request Encryption Algorithm" inputName="request_encryption_alg" types="<%= JWEAlg.values()%>" existingValue="${model.requestEncryptionAlg}" description="<%= Descriptions.REQUEST_ENCRYPTION_ALG %>"></t:type>

                        <!-- Request Encryption Encoding Algorithm -->
                        <t:type paramName="Request Encryption Encoding Algorithm" inputName="request_encryption_enc" types="<%= JWEEnc.values()%>" existingValue="${model.requestEncryptionEnc}" description="<%= Descriptions.REQUEST_ENCRYPTION_ENC %>"></t:type>

                        <!-- Token Auth Method -->
                        <t:type paramName="Token Auth Method" inputName="token_auth_method" types="<%= ClientAuthMethod.values()%>" existingValue="${model.tokenAuthMethod}" description="<%= Descriptions.TOKEN_AUTH_METHOD %>"></t:type>

                        <!-- Token Auth Sign Algorithm -->
                        <t:type paramName="Token Auth Sign Algorithm" inputName="token_auth_sign_alg" types="<%= JWSAlg.values()%>" existingValue="${model.tokenAuthSignAlg}" description="<%= Descriptions.TOKEN_AUTH_SIGN_ALG %>"></t:type>

                        <!-- Default Max Age -->
                        <t:text paramName="Default Max Age" inputName="default_max_age" existingValue="${model.defaultMaxAge}" description="<%= Descriptions.DEFAULT_MAX_AGE %>"></t:text>

                        <!-- Default Acrs -->
                        <t:texts paramName="Default Acrs" inputName="default_acrs" existingValues="${model.defaultAcrs}" description="<%= Descriptions.DEFAULT_ACRS %>"></t:texts>

                        <!-- Login URI -->
                        <t:text paramName="Login URI" inputName="login_uri" existingValue="${model.loginUri}" description="<%= Descriptions.LOGIN_URI %>"></t:text>

                        <!-- Request URIs-->
                        <t:texts paramName="Request URIs" inputName="request_uris" existingValues="${model.requestUris}" description="<%= Descriptions.REDIRECT_URIS %>"></t:texts>

                        <!-- Description -->
                        <t:text paramName="Description" inputName="description" existingValue="${model.description}" description="<%= Descriptions.DESCRIPTION %>"></t:text>

                        <!-- Descriptions -->
                        <t:tagged_texts paramName="Descriptions" inputName="descriptions" existingValues="${model.descriptions}" description="<%= Descriptions.DESCRIPTIONS %>" formId="client_update"></t:tagged_texts>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary col-lg-1">Update</button>
                        </div>

                    </fieldset>

                </form>

            </div>

        </div>

    <%@ include file="/jsp/common_script.jsp" %>
    <script src="/authlete-sample-server/js/underscore-min.js"></script>
    <script src="/authlete-sample-server/js/client_functions.js"></script>
    <script src="/authlete-sample-server/js/client_update.js"></script>
    </body>
</html>
