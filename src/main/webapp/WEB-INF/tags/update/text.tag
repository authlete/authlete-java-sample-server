<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="paramName"     required="true"%>
<%@ attribute name="inputName"     required="true"%>
<%@ attribute name="existingValue" required="true"%>
<%@ attribute name="required"      required="false"%>
<%@ attribute name="description"   required="true"%>

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

    <input type="text" class="form-control col-lg-12" name="${inputName}" value="${existingValue}">

</div>
