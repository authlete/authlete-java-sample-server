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
        <%@ include file="/jsp/common_meta.jsp" %>
        <%@ include file="/jsp/common_css.jsp" %>
        <link href="/authlete-sample-server/css/authorization.css" rel="stylesheet">
        <link href="/authlete-sample-server/css/box-container.css" rel="stylesheet">

        <title>Authorization</title>

    </head>

    <body>
        <!-- Header -->
        <nav class="navbar navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand">Authlete Sample Server</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="well well-lg">

            <h1>
                Do you authorize <a href="${model.res.client.clientUri}">${model.res.client.clientName}</a> to access your data?
                <br>
                <small><span style="color: red;">${model.errorMessage}</span></small><br/>
                <c:if test="${empty model.res.scopes == false}">
                <small>This application is requesting the following permissions.</small>
                </c:if>
            </h1>

            <ul class="media-list">

            <c:forEach var="scope" items="${model.res.scopes}">
            <hr>
            <li class="media">
                <a class="pull-left" href="#">
                    <img class="media-object" src="/authlete-sample-server/image/scope.png" height="42">
                </a>
                <div class="media-body">
                    <h4 class="media-heading">${scope.name}</h4>
                    ${scope.description}
                </div>
            </li>
            </c:forEach>

            </ul>


            <form class="form-horizontal" action='<c:url value="/api/auth/authorization/submit"/>' method="POST" accept-charset="UTF-8">
                <div class="form-group">
                    <label class="control-label">Username</label>
                    <input type="text" class="form-control" placeholder="Username" name="username">
                </div>

                <div class="form-group">
                    <label class="control-label">Password</label>
                    <input type="password" class="form-control" placeholder="Password" name="password">
                </div>

                <br>

                <div class="box-container">
                    <div class="form-group box">
                        <button type="submit" class="btn btn-primary" name="authorized" value="true">Authorize</button>
                    </div>

                    <div class="form-group box">
                        <button type="submit" class="btn btn-danger" name="denied" value="true">Deny</button>
                    </div>
                </div>
            </form>

            </div>
        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>
