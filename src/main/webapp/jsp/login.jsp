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
        <link href="/authlete-sample-server/css/login.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="/jsp/common_header.jsp" %>

        <!-- Login Form -->
        <div class="container">
            <h1>Login</h1>

            <form action="" method="POST" accept-charset="UTF-8" id="login">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" placeholder="Login ID">
                </div>

                <div class="form-group">
                    <input type="password" class="form-control" name="password" placeholder="Password">
                </div>

                <div class="checkbox">
                    <input name="rememberMe" type="checkbox" checked/>Remember Me
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>
        </div>

        <%@ include file="/jsp/common_script.jsp" %>
    </body>
</html>
