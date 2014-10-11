<%@attribute name="name" required="true"%>
<%@attribute name="value" required="true"%>

<tr>
    <td><h5>${name}</h5></td>
    <td>${value.toString()}</td>
</tr>
