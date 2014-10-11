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
        <link href="/authlete-sample-server/css/profile.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <!-- Profile -->
        <div class="container">

            <h1>About</h1>

            <div class="well well-sm">
                <dl class="dl-horizontal">
                    <dt>User Id</dt>
                    <dd>${model.userId}</dd>
                    <dt>Name</dt>
                    <dd>${model.name}</dd>
                    <dt>Sex</dt>
                    <dd>
                        <c:choose>
                            <c:when test="${model.sex==0}">Male</c:when>
                            <c:otherwise>Female</c:otherwise>
                        </c:choose>
                    </dd>
                    <dt>Age</dt>
                    <dd>${model.age}</dd>
                </dl>
            </div>

        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>
