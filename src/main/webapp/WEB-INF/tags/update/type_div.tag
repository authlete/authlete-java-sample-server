<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="inputName"     required="true"%>
<%@ attribute name="existingValue" required="true" type="java.lang.Enum"%>
<%@ attribute name="type"         required="true" type="java.lang.Enum"%>

 <div class="radio">
    <label>
        <input type="radio" name="${inputName}" value="${type.toString()}"
        <c:if test="${existingValue == type}">checked</c:if>>
        ${type.toString()}
    </label>
</div>
