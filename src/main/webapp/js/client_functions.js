/*
 * Copyright 2014 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
compiled_text_template  = _.template(_get_text_template());
compiled_tag_template   = _.template(_get_tag_template());
compiled_value_template = _.template(_get_value_template());

//-----------------------
// Template
//-----------------------

function _get_text_template()
{
    return '<input type="text" class="form-control col-sm-12" name="<%= input%>">';
}

function _get_tag_template()
{
    return '<div class="tv-tag col-xs-3 col-sm-2">' +
           '<input type="text" class="form-control" name="<%= input%>_tags">' +
           '</div>';
}

function _get_value_template()
{
    return '<div class="tv-value col-xs-9 col-sm-10">' +
           '<input type="text" class="form-control" name="<%= input%>_values">' +
           '</div>';
}

//-----------------------
// Texts Input
//-----------------------

function _init_texts_input(input)
{
    // Set function on click.
    _init_texts_input_enabler(input);

    _init_texts_input_appender(input);

    _init_texts_input_remover(input);

    _init_hint(input);
}

function _init_texts_input_enabler(input)
{
    var enabler_id = "set_" + input;

    $("#" + enabler_id).click(function() {
        _enable_texts_input(input, this.checked);
    });
}

function _init_texts_input_appender(input)
{
    var inputs_id   = input + "_inputs";
    var appender_id = "append_" + input;

    if ($("#" + appender_id).length > 0)
    {
        // Make the appender to be able to add an input field.
        $("#" + appender_id).click(function() {
            var data = { "input" : input };

            $("#" + inputs_id).append(compiled_text_template(data));
        });
    }
}

function _init_texts_input_remover(input)
{
    var inputs_id  = input + "_inputs";
    var remover_id = "remove_" + input;

    if ($("#" + remover_id).length > 0)
    {
        // Make the remover to be able to remove the last input field.
        $("#" + remover_id).click(function() {
            if ($("#" + inputs_id).children().length > 1)
            {
                $("#" + inputs_id).children().last().remove();
            }
        });
    }
}

function _enable_texts_input(input, flag)
{
    var input_name  = input;
    var appender_id = "append_" + input;
    var remover_id  = "remove_" + input;

    $("input[name='" + input_name + "']").prop("disabled", !flag);

    if ($("#" + appender_id).length > 0)
    {
        $("#" + appender_id).prop("disabled", !flag);
    }

    if ($("#" + remover_id).length > 0)
    {
        $("#" + remover_id).prop("disabled", !flag);
    }
}

//-----------------------
// Tagged Texts Input
//-----------------------

function _init_tagged_texts_input(input)
{
    // Set function on checking the box.
    _init_tagged_texts_input_enabler(input);

    // Make the appender to be able to add an input field.
    _init_tagged_texts_input_appender(input);

    // Make the remover to be able to remove the last input field.
    _init_tagged_texts_input_remover(input);
}

function _init_tagged_texts_input_enabler(input)
{
    var enabler_id = "set_" + input;

    $("#" + enabler_id).click(function() {
        _enable_tagged_texts_input(input, this.checked);
    });
}

function _init_tagged_texts_input_appender(input)
{
    var inputs_id   = input + "_inputs";
    var appender_id = "append_" + input;

    $("#" + appender_id).click(function() {
        var data = { "input" : input };

        $("#" + inputs_id).append(compiled_tag_template(data));
        $("#" + inputs_id).append(compiled_value_template(data));
    });
}

function _init_tagged_texts_input_remover(input)
{
    var inputs_id  = input + "_inputs";
    var remover_id = "remove_" + input;

    $("#" + remover_id).click(function() {
        // Remove the children elements if the number of them is over 2,
        // which is equal to a pair of 'tag' and 'value' field.
        if ($("#" + inputs_id).children().length > 2)
        {
            $("#" + inputs_id).children(".tv-tag").last().remove();
            $("#" + inputs_id).children(".tv-value").last().remove();
        }
    });
}

function _enable_tagged_texts_input(input, flag)
{
    var tags_name   = input + "_tags";
    var values_name = input + "_values";
    var appender_id = "append_" + input;
    var remover_id  = "remove_" + input;

    // Change the tag inputs state.
    $("input[name='" + tags_name + "']").prop("disabled", !flag);

    // Change the value inputs state.
    $("input[name='" + values_name + "']").prop("disabled", !flag);

    // Change the input appender state.
    $("#" + appender_id).prop("disabled", !flag);

    // Change the input remover state.
    $("#" + remover_id).prop("disabled", !flag);
}

//-----------------------
// Types Input
//-----------------------

function _init_types_input(input)
{
    _init_types_input_enabler(input);
}

function _init_types_input_enabler(input)
{
    var enabler_id = "set_" + input;

    // Set function on click.
    $("#" + enabler_id).click(function() {
        _enable_types_input(input, this.checked);
    });
}

function _enable_types_input(input, flag)
{
    // If checked, enable inputs; otherwise, disable them.
    $("input[name='" + input + "']").prop("disabled", !flag);
}

//-----------------------
// Checker
//-----------------------

function _check_enabler(input, flag)
{
    var enabler_id  = "set_" + input;

    // Change the check box state.
    $("#" + enabler_id).prop("checked", flag);
};

//------------------------
// Hint
//------------------------

function _init_hint()
{
    $('span[data-toggle="tooltip"]').tooltip({
        'placement': 'right'
    });
}
