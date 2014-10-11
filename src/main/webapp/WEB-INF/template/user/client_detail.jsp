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
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/detail" %>
<%@ include file="/jsp/common_setting.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Authlete Sample Server</title>
        <%@ include file="/jsp/common_meta.jsp" %>
        <%@ include file="/jsp/common_css.jsp" %>
        <link href="/authlete-sample-server/css/client_detail.css" rel="stylesheet">
        <link href="/authlete-sample-server/css/box-container.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <!-- Client Application List -->
        <div class="container">

            <h1>${model.clientName}</h1>

            <div class="box-container">
               <div class="box">
                    <button class="btn btn-primary box" onClick="location.href='<c:url value="/user/develop/${model.clientId}/update"/>'">
                        <span class="glyphicon glyphicon-cog"></span>&nbsp;Settings
                    </button>
                </div>

                <div class="box">
                    <form action='<c:url value="/user/develop/${model.clientId}/delete"/>' method="POST">
                        <button type="submit" class="btn btn-danger">Delete this app</button>
                    </form>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover">

                    <!-- Client Number -->
                    <t:single name="Client number" value="${model.number}"></t:single>

                    <!-- Service Number -->
                    <t:single name="Service Number" value="${model.serviceNumber}"></t:single>

                    <!-- Developer -->
                    <t:single name="Developer" value="${model.developer}"></t:single>

                    <!-- Client Id -->
                    <t:single name="Client Id" value="${model.clientId}"></t:single>

                    <!-- Client Secret -->
                    <t:single name="Client Secret" value="${model.clientSecret}"></t:single>

                    <!-- Client Type -->
                    <t:single name="Client Type" value="${model.clientType}"></t:single>

                    <!-- Redirect URIs -->
                    <t:multiple name="Redirect URIs" values="${model.redirectUris}"></t:multiple>

                    <!-- Response Types -->
                    <t:multiple name="Response Types" values="${model.responseTypes}"></t:multiple>

                    <!-- Grant Types -->
                    <t:multiple name="Grant Types" values="${model.grantTypes}"></t:multiple>

                    <!-- Application Type -->
                    <t:single name="Application Type" value="${model.applicationType}"></t:single>

                    <!-- Contacts -->
                    <t:multiple name="Contacts" values="${model.contacts}"></t:multiple>

                    <!-- Client Name -->
                    <t:single name="Client Name" value="${model.clientName}"></t:single>

                    <!-- Client Names -->
                    <t:tagged_values name="Client Names" values="${model.clientNames}"></t:tagged_values>

                    <!-- Logo URI -->
                    <t:single name="Logo URI" value="${model.logoUri}"></t:single>

                    <!-- Logo URIs -->
                    <t:tagged_values name="Logo URIs" values="${model.logoUris}"></t:tagged_values>

                    <!-- Client URI -->
                    <t:single name="Client URI" value="${model.clientUri}"></t:single>

                    <!-- Client URIs -->
                    <t:tagged_values name="Client URIs" values="${model.clientUris}"></t:tagged_values>

                    <!-- Policy URI -->
                    <t:single name="Policy URI" value="${model.policyUri}"></t:single>

                    <!-- Policy URIs -->
                    <t:tagged_values name="Policy URIs" values="${model.policyUris}"></t:tagged_values>

                    <!-- Terms of Service URI -->
                    <t:single name="Terms of Service URI" value="${model.tosUri}"></t:single>

                    <!-- Terms of Service URIs -->
                    <t:tagged_values name="Terms of Service URIs" values="${model.tosUris}"></t:tagged_values>

                    <!-- JSON Web Key Set URI -->
                    <t:single name="JSON Web Key Set URI" value="${model.jwksUri}"></t:single>

                    <!-- JSON Web Key Set -->
                    <t:single name="JSON Web Key Set" value="${model.jwks}"></t:single>

                    <!-- Sector Identifier -->
                    <t:single name="Sector Identifier" value="${model.sectorIdentifier}"></t:single>

                    <!-- Subject Type -->
                    <t:single name="Subject Type" value="${model.subjectType}"></t:single>

                    <!-- Id Token Sign Algorithm -->
                    <t:single name="Id Token Sign Algorithm" value="${model.idTokenSignAlg}"></t:single>

                    <!-- Id Token Encryption Algorithm -->
                    <t:single name="Id Token Encryption Algorithm" value="${model.idTokenEncryptionAlg}"></t:single>

                    <!-- Id Token Encryption Encoding Algorithm -->
                    <t:single name="Id Token Encryption Encoding Algorithm" value="${model.idTokenEncryptionEnc}"></t:single>

                    <!-- User Info Sign Algorithm -->
                    <t:single name="User Info Sign Algorithm" value="${model.userInfoSignAlg}"></t:single>

                    <!-- User Info Encryption Algorithm -->
                    <t:single name="User Info Encryption Algorithm" value="${model.userInfoEncryptionAlg}"></t:single>

                    <!-- User Info Encryption Encoding Algorithm -->
                    <t:single name="User Info Encryption Encoding Algorithm" value="${model.userInfoEncryptionEnc}"></t:single>

                    <!-- Request Sign Algorithm -->
                    <t:single name="Request Sign Algorithm" value="${model.requestSignAlg}"></t:single>

                    <!-- Request Encryption Algorithm -->
                    <t:single name="Request Encryption Algorithm" value="${model.requestEncryptionAlg}"></t:single>

                    <!-- Request Encryption Encoding Algorithm -->
                    <t:single name="Request Encryption Encoding Algorithm" value="${model.requestEncryptionEnc}"></t:single>

                    <!-- Token Auth Method -->
                    <t:single name="Token Auth Method" value="${model.tokenAuthMethod}"></t:single>

                    <!-- Token Auth Sign Algorithm -->
                    <t:single name="Token Auth Sign Algorithm" value="${model.tokenAuthSignAlg}"></t:single>

                    <!-- Default Max Age -->
                    <t:single name="Default Max Age" value="${model.defaultMaxAge}"></t:single>

                    <!-- Default Acrs -->
                    <t:multiple name="Default Acrs" values="${model.defaultAcrs}"></t:multiple>

                    <!-- Login URI -->
                    <t:single name="Login URI" value="${model.loginUri}"></t:single>

                    <!-- Request URIs -->
                    <t:multiple name="Request URIs" values="${model.requestUris}"></t:multiple>

                    <!-- Description -->
                    <t:single name="Description" value="${model.description}"></t:single>

                    <!-- Descriptions -->
                    <t:tagged_values name="Descriptions" values="${model.descriptions}"></t:tagged_values>

                </table>
            </div>

        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>
