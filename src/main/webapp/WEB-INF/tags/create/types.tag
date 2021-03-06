<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="paramName"   required="true"%>
<%@ attribute name="inputName"   required="true"%>
<%@ attribute name="types"       required="true" type="java.lang.Enum[]"%>
<%@ attribute name="description" required="true"%>

<div class="form-group">

    <label class="control-label">${paramName}</label>
    <span class="glyphicon glyphicon-question-sign" data-toggle="tooltip" title="${description}"></span>

    <div class="checkbox col-sm-12">
        <label>
            <input type="checkbox" id="set_${inputName}">
            Set ${paramName}
        </label>
    </div>

    <div class="checkbox col-sm-12">

        <c:forEach var="type" items="${types}">
        <div class="check">
            <label>
                <input type="checkbox" name="${inputName}" value="${type.toString()}">
                ${type.toString()}
            </label>
        </div>
        </c:forEach>

    </div>

</div>
