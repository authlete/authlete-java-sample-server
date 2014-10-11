<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="paramName"   required="true"%>
<%@ attribute name="inputName"   required="true"%>
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

    <div id="${inputName}">

        <div id="${inputName}_inputs">
            <input type="text" class="form-control col-sm-12" name="${inputName}">
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
