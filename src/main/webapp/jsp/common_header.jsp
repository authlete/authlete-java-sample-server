<!-- Navigation -->
<nav class="navbar  navbar-static-top" role="navigation">
    <div class="container">

        <!-- Header -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Authlete Sample Server</a>

            <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <!-- Links -->
        <div class="collapse navbar-collapse navHeaderCollapse">
            <ul class="nav navbar-nav navbar-right">

                <!-- User info -->
                <shiro:user>
                <li>
                    <a href="<c:url value="/user/profile"/>">
                        <span class="glyphicon glyphicon-user"></span>&nbsp;
                        <%= (String)org.apache.shiro.SecurityUtils.getSubject().getPrincipal() %>
                    </a>
                </li>
                </shiro:user>

                <!-- Home -->
                <li>
                    <a href="<c:url value="/user/home"/>"><span class="glyphicon glyphicon-home"></span>&nbsp;Home</a>
                </li>

                <!-- Develop -->
                <li>
                    <a href="<c:url value="/user/develop"/>"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Develop</a>
                </li>

                <!-- Log in/out -->
                <li>
                    <shiro:guest>
                    <a href="<c:url value="/jsp/login.jsp"/>">Log in</a>
                    </shiro:guest>
                    <shiro:user>
                    <a href="<c:url value="/jsp/logout"/>">Log out</a>
                    </shiro:user>
                </li>

            </ul>
        </div>

    </div>
</nav>
