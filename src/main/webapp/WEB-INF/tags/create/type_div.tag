<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="inputName"   required="true"%>
<%@attribute name="type"       required="true" type="java.lang.Enum"%>
<%@attribute name="checked" required="false"%>

<div class="radio">
    <label>
        <input type="radio" name="${inputName}" value="${type.toString()}"
        <c:if test="${ checked == 'true' }">checked="true"</c:if>>
        ${type.toString()}
    </label>
</div>
