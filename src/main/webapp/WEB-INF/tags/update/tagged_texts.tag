<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="paramName"      required="true"%>
<%@ attribute name="inputName"      required="true"%>
<%@ attribute name="existingValues" required="true" type="com.authlete.common.dto.TaggedValue[]"%>
<%@ attribute name="description"    required="true"%>
<%@ attribute name="formId"         required="true"%>
<%@ tag import="java.util.Locale"%>

<div class="form-group">

    <label class="control-label">${paramName}</label>
    <span class="glyphicon glyphicon-question-sign" data-toggle="tooltip" title="${description}"></span>

    <div class="checkbox col-sm-12">
        <label>
            <input type="checkbox" id="set_${inputName}">
            Set ${paramName}
        </label>
    </div>

    <div id="${inputName}">

        <div id="${inputName}_inputs">
            <c:choose>

            <%-- When no existing values were found --%>
            <c:when test="${empty existingValues}">
            <div class="tv-tag col-xs-3 col-sm-2">
                <input type="text" class="form-control" name="${inputName}_tags">
            </div>

            <div class="tv-value col-xs-9 col-sm-10">
                <input type="text" class="form-control" name="${inputName}_values">
            </div>
            </c:when>

            <%-- When the existing values were found --%>
            <c:otherwise>
            <c:forEach var="value" items="${existingValues}">
            <div class="tv-tag col-xs-3 col-sm-2">
                <input type="text" class="form-control" name="${inputName}_tags" value="${value.getTag()}">
            </div>

            <div class="tv-value col-xs-9 col-sm-10">
                <input type="text" class="form-control" name="${inputName}_values" value="${value.getValue()}">
            </div>
            </c:forEach>
            </c:otherwise>

            </c:choose>
        </div>

        <div id="${inputName}_controls">
            <button type="button" class="btn btn-default" id="append_${inputName}">
                <span class="glyphicon glyphicon-plus"></span>
            </button>

            <button type="button" class="btn btn-default" id="remove_${inputName}">
                <span class="glyphicon glyphicon-minus"></span>
            </button>
        </div>

     </div>

</div>
