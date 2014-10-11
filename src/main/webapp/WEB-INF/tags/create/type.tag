<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/create" %>
<%@ taglib uri="/WEB-INF/tld/custom_functions.tld" prefix="fn" %>

<%@ attribute name="paramName"   required="true"%>
<%@ attribute name="inputName"   required="true"%>
<%@ attribute name="types"       required="true" type="java.lang.Enum[]"%>
<%@ attribute name="description" required="true"%>
<%@ attribute name="required"    required="false"%>

<div class="form-group <c:if test="${required == 'true'}">required</c:if>">

    <label class="control-label">${paramName}</label>
    <span class="glyphicon glyphicon-question-sign" data-toggle="tooltip" title="${description}"></span>

    <c:if test="${required != 'true'}">
    <div class="checkbox col-sm-12">
        <label>
            <input type="checkbox" id="set_${inputName}">
            Set ${paramName}
        </label>
    </div>
    </c:if>

    <div class="radio-group col-sm-12">
        <c:forEach var="type" items="${types}">

        <c:choose>

        <%-- When the class of the type is 'JWEAlg' --%>
        <c:when test="${ type.getDeclaringClass().getName() == 'com.authlete.common.types.JWEAlg' }">

        <%-- If the type is supported, the type division is created --%>
        <c:if test="${ fn:isSupported(type) == true }">
        <t:type_div inputName="${inputName}" type="${type}"></t:type_div>
        </c:if>
        </c:when>

        <%-- When the class of the type is 'ClientType' and the type is "PUBLIC",
        the type division is created being checked --%>
        <c:when test="${ type.getDeclaringClass().getName() == 'com.authlete.common.types.ClientType' && type == 'PUBLIC' }">
        <t:type_div inputName="${inputName}" type="${type}" checked="true"></t:type_div>
        </c:when>

        <%-- Otherwise --%>
        <c:otherwise>
        <t:type_div inputName="${inputName}" type="${type}"></t:type_div>
        </c:otherwise>

        </c:choose>

        </c:forEach>
    </div>

</div>
