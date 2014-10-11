<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="value" required="true" type="com.authlete.sample.server.database.entity.Feed" %>

<div class="well">

    <!-- Title -->
    <p class="lead">${value.title}</p>

    <!-- Create time & privacy info -->
    <p>
        <span class="glyphicon glyphicon-time"></span> ${value.createdAt}
        &nbsp;
        <c:choose>
            <c:when test="${value.locked==true}">
            <span class="glyphicon glyphicon-lock"></span>
            </c:when>
        </c:choose>
    </p>

    <!-- Content -->
    <p>${fn:replace(value.content, '\\n', '<br>')}</p>

</div>
