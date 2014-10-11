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
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <div class="container">
            <ol>
                <shiro:guest>
                <c:redirect url="/jsp/login.jsp" />
                </shiro:guest>

                <shiro:user>
                <c:redirect url="/user/home" />
                </shiro:user>
            </ol>
        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>