<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="name" required="true"%>
<%@attribute name="values" required="true" type="Object[]"%>

<tr>
    <td><h5>${name}</h5></td>
    <td>
        <c:forEach var="value" items="${values}">
        ${value.toString()}
        <br>
        </c:forEach>
    </td>
</tr>
