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
<!DOCTYPE html>
<html>
    <head>
        <title>Authlete Sample Server</title>
        <%@ include file="/jsp/common_meta.jsp" %>
        <%@ include file="/jsp/common_css.jsp" %>
        <link href="/authlete-sample-server/css/client_develop.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <!-- Client Application List -->
        <div class="container">

            <h1>Your Client Applications</h1>
            <hr>
            <c:forEach var="client" items="${model.clients}">
            <div class="media">
                <a class="pull-left">
                    <img class="media-object" src="/authlete-sample-server/image/client_logo.png">
                </a>
                <div class="media-body">
                    <h3 class="media-heading">
                        <a href="<c:url value="/user/develop/${client.clientId}/detail"/>">${client.clientName}</a>
                    </h3>
                    <p>${client.description}</p>
                </div>
            </div>
            <hr>
            </c:forEach>

            <button class="btn btn-primary" onClick="location.href='<c:url value="/user/develop/create"/>'">
            Create a new client app
            </button>

        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>
