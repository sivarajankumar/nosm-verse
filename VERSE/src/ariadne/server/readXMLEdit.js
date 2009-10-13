


$(document).ready(function() {
    $.get("assets.xml", {}, function(xml) { ///&mode=admin will return assetTypeattrib xml
        $('Asset', xml).each(function(i) {
            assetType = $(this).attr("type");
            assetName = $(this).attr("name");
            // alert ('in asset attribs: ' + assetName);
            //var qStr = new Querystring(location.search.substring(1, location.search.length));
            var aName = $(document).getUrlParam("asset");
            var assetType = $(document).getUrlParam("assetType");

            //var aName = qStr.get("asset");
            //var aType = qStr.get("assetType");

            if (assetName.toLowerCase() == aName.toLowerCase() && assetType.toLowerCase() == assetType.toLowerCase()) {
                // alert("found match: " + aName + '~' + aType);
                $("input[name='assetTypeIN']").setValue(assetType);
                $("input[name='assetNameIN']").setValue(assetName);
                $("input[name='assetTargetIN']").setValue($(this).attr("targettype"));
                $("input[name='assetValueIN']").setValue($(this).attr("value"));
                $("input[name='currentUIpairs']").setValue($(this).attr("uipairs"));
                $("input[name='assetMapNodeID']").setValue($(this).attr("id"));

                //alert('assetValueIN populated with: ' + $("input[name='assetValueIN']").getValue());
                //qStr = new Querystring(location.search.substring(1, location.search.length));
                //populateFields();
                showonlyone(aName, 'type');
            }
        });
        window.oSelector = $("#selector");
        updateGMap(); // still needed?

        //eventually call all fields that are visible, not just main selector
        document.getElementById("selector").onchange();
    });

    $(function() {

        function popFlds() {
            var aName = document.forms[0].assetNameIN.value;
            var aType = document.forms[0].assetTypeIN.value;
            //alert('now in popFields: ' + aName + '~' + aType);
            var aVal = "";
            var aTar = "";

            if (aName != "") {
                aTar = $("input[name='assetTargetIN']").getValue();
                aVal = $("input[name='assetValueIN']").getValue(); // document.forms[0].assetValueIN.value;

                var oldVal = aVal;

                // loop innerhtml of the (aType.toLowerCase() +'type')
                // get their names
                $("input[name='assetName']").setValue(aName);
                //add them to currentUIpairs
                $("select[name='selector']").setValue(aType.toLowerCase());
                //alert('do we even get here?');
                if (document.getElementById('currentUIpairs').value != "") {
                    //alert('we have pairs: ' + $("input[name='currentUIpairs']").getValue());
                    $.query.load(decodeURIComponent(document.getElementById('currentUIpairs').value));
                    //var test = $(".output").empty();
                    $.each($.query.get(), function(key, value) {
                        //aVal = $("input[name='currentUIpairs']").getValue();
                        if (document.getElementById(key) != null) {
                            $("input[name='" + key + "']").setValue(value);
                            alert('hooray! ' + document.getElementById(key).name + ' - ' + document.getElementByName(key).value);
                        } else {
                            alert('the field ' + key + ' does not exist!');
                        }

                    });
                }
            }

        }
        // do we need this?
        $(window).bind("hashchange", function() {
            $.query = $.query.load(location.href);
            popFlds();
        });
        // start everything
        popFlds();


    });

});


function selectorSwitch(fld){
    showonlyone(fld.options[fld.selectedIndex].value, 'type');
    
    if(fld.options[fld.selectedIndex].value.indexOf('slgoog') == 0){
        chartSetup();
    }

    if (fld.options[fld.selectedIndex].value.indexOf('slamazonmap') == 0) {
        loadSLmap();
    } else {
    // do this for google maps to? any other google objects? viz?
        GUnload();
    }
    if (fld.options[fld.selectedIndex].value.indexOf('vpdmedia') == 0) showYT();
    
    if(fld.options[fld.selectedIndex].value.indexOf('vpd') == 0){
        document.getElementById('vpd_vpd').style.display = 'block'
    }else{
        document.getElementById('vpd_vpd').style.display = 'none';
    }

    var selTypes = fld.options;
    for (var i = 0, n = selTypes.length; i < n; ++i) {
        var elem = selTypes[i].value;
        if (document.getElementById('type_' + elem) && document.getElementById('type_' + elem).value != 'separator') {
            var divNodes = document.getElementById('type_' + elem).childNodes; // all nodes for current visible div (selected asset type)
            if (fld.options[fld.selectedIndex].value == elem) triggerVisibleFormElems(divNodes);
        } 
    }
}

function triggerVisibleFormElems(node) {
    for (var j = 0; j < node.length; j++) {
        if (node.nodeName.toLowerCase() == 'div' || node.tagName.toLowerCase() == 'div') {
                node.style.display = 'block';
                triggerVisibleFormElems(node.childNodes);
                return;
            } else {
            if (node.nodeName.toLowerCase() == 'input' || node.nodeName.toLowerCase() == 'select' || node.tagName.toLowerCase() == 'input' || node.tagName.toLowerCase() == 'select') {
                    if (node.onchange) node.onchange();
                    return;
                } else {
                if (node.nodeName.toLowerCase() == 'checkbox' || node.nodeName.toLowerCase() == 'radio' || node.tagName.toLowerCase() == 'checkbox' || node.tagName.toLowerCase() == 'radio') {
                        if (node.onclick) node.onclick();
                        return;
                    }
                }
            }

    }
    return;
}

var ListHandler = new Object();
var CheckboxHandler = new Object();
var RadioHandler = new Object();

Array.prototype.in_array = function(value){
    for (var i = 0; i < this.length; i++) {
        if (this[i] == value) {
            return true;
        }
    }

    return false;

};

RadioHandler.getCheckedValue = function(radio_name){
    oRadio = document.forms[0].elements[radio_name];
    for (var i = 0; i < oRadio.length; i++) {
        if (oRadio[i].checked) {
            return oRadio[i].value;
        }
    }

    return '';
};


ListHandler.getSelectedIndices = function(oList){
    var indices = [];
    for (var i = 1; i < oList.options.length; i++) {
        if (oList.options[i].selected == true) {
            indices.push(i);
        }
    }

    return indices;

};



ListHandler.getSelectedValues = function(oList){
    var sValues = [];
    for (var i = 1; i < oList.options.length; i++) {
        if (oList.options[i].selected == true) {
            sValues.push(oList.options[i].value);
        }
    }

    return sValues;

};



ListHandler.getSelectedOptionsDisplayText = function(oList){
    var sdValues = [];
    for (var i = 1; i < oList.options.length; i++) {
        if (oList.options[i].selected == true) {
            sdValues.push(oList.options[i].text);
        }
    }

    return sdValues;

};




ListHandler.getAllValues = function(oList){
    var aValues = [];

    for (var i = 1; i < oList.options.length; i++) {
        aValues.push(oList.options[i].value);
    }

    return aValues;

};




ListHandler.getAllOptionsDisplayText = function(oList){
    var aValues = [];

    for (var i = 1; i < oList.options.length; i++) {
        aValues.push(oList.options[i].text);
    }
    return aValues;

};




ListHandler.addOption = function(oList, optionName, optionValue){
    var oOption = document.createElement("option");
    oOption.appendChild(document.createTextNode(optionName));
    oOption.setAttribute("value", optionValue);

    oList.appendChild(oOption);

};



ListHandler.removeOption = function(oList, index){
    oList.remove(index);
};



CheckboxHandler.isChecked = function(checkboxObj){
    return (checkboxObj.checked == true);
};


function trim(str){
    return str.replace(/^\s+|\s+$/g, '');
}

function isEmpty(str){
    str = trim(str);
    return ((str == null) || (str.length == 0));
}


function isDigit(c){
    return ((c >= "0") && (c <= "9"));
}

function saveChanges(){
    $("input[name='currentUIpairs']").setValue(getAllFormsList());
    //document.getElementsByName("currentUIpairs").value = getAllFormsList();
    populateFields();
}

function isInteger(str){
    for (var i = 0; i < str.length; i++) {
        var c = str.charAt(i);
        if (!isDigit(c)) {
            return false;
        }
    }

    return true;
}

function DisplayFormValues(){
    var str = '';
    var elem = document.getElementById('mainDummy').elements;
    for (var i = 0; i < elem.length; i++) {
        str += "" + elem[i].name + "=" + elem[i].value + "&";
    }
    document.getElementById('lblValues').innerHTML = str;
}



function getFormValues(oForm, skip_elements){

    var elements = oForm.elements;
    var data = [];
    var element_value = null;

    for (var i = 0; i < elements.length; i++) {

        var field_type = elements[i].type.toLowerCase();
        var element_name = elements[i].getAttribute("name");

        if (!skip_elements.length || !skip_elements.in_array(element_name)) {

            switch (field_type) {

                case "text":
                case "password":
                case "textarea":
                case "hidden":

                    element_value = elements[i].value;
                    data.push(element_name + '=' + element_value);
                    break;

                case "checkbox":

                    element_value = CheckboxHandler.isChecked(elements[i]);
                    data.push(element_name + '=' + element_value);
                    break;


                case "select-one":

                    var ind = elements[i].selectedIndex;
                    if (ind > 0) {
                        element_value = elements[i].options[ind].text;
                    }
                    else {
                        element_value = '';
                    }
                    data.push(element_name + '=' + element_value);
                    break;

                default:
                    break;
            }

        }

    }

    return data;

}


function gv4ks(url, key2look){
    var pairs = url.split('&');
    for (i = 0; i < pairs.length; i++) {
        var keyval = pairs[i].split('=');
        if (keyval[0].toLowerCase() == key2look.toLowerCase()) {
            var v = keyval[1];
            break;
        }
    }
    if (v) {
        return v;
    }
    return "";
}



 function getVisibleFieldsValues()
     {
                //var curVal = gv4ks($("input[name='assetValueIN']").getValue());
                //if (curVal != ""){
                  //  alert(curVal, InputName);
                   // $("input[name='assetValueOUT']").setValue(curVal);
                    
                var form = document.forms['mainDummy'];

                for(var i = 0, n = form.length; i < n; ++i) {
                    var elem = form.elements[i];

                    if(elem.id != '' && elem.name != '' && elem.style && 'hidden' != elem.style.visibility) {
                         alert( $(elem.parentNode).id + ' ---- ' +  elem.name + " -- " + elem.value );
                    }
               }
 }
                                           
 //$("select[name='assetValueOUT']").setValue(curVal);




function toggle_sname(){
    var HH_sel = document.getElementById('Holodeck_chat_cmds');
    if (HH_sel.options[HH_sel.selectedIndex].value.indexOf("run s") > -1 ||
    HH_sel.options[HH_sel.selectedIndex].value.indexOf("clear s") > -1) {
        doc.getElementById('HH_sname').className = 'unhidden';
    }
    else {
        doc.getElementById('HH_sname').className = 'hidden';
    }
}


function showonlyone(thechosenone, cat){
        var alldivs = document.getElementsByTagName("div");
        for (var x = 0; x < alldivs.length; x++) {
            thisid = alldivs[x].id.toLowerCase();
            if(thisid.indexOf(cat + "_") == 0){
                alldivs[x].style.display = 'none';
                if (thisid.toLowerCase() == (cat +"_" + thechosenone.toLowerCase())) {
                    alldivs[x].style.display = 'block';
                }
            }
        }//end for
    }



function getUrlVars(){
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < hashes.length; i++) {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function getAllFormsList(){
    var elementsForms = top.window.document.getElementsByTagName("mainDummy");
    var elementsList = "";
    for (var intCounter = 0; intCounter < elementsForms.length; intCounter++) {
        elementsList = elementsList + getFormValues(elementsForms[intCounter], []).join('&');
        //alert("" + elementsForms.length + elementsList);
    }
    return elementsList;
}



