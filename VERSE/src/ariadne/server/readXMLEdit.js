function Querystring(qs) { // optionally pass a querystring to parse
	this.params = {};
	
	if (qs == null) qs = location.search.substring(1, location.search.length);
	if (qs.length == 0) return;

// Turn <plus> back to <space>
// See: http://www.w3.org/TR/REC-html40/interact/forms.html#h-17.13.4.1
	qs = qs.replace(/\+/g, ' ');
	var args = qs.split('&'); // parse out name/value pairs separated via &
	
// split out each name=value pair
	for (var i = 0; i < args.length; i++) {
		var pair = args[i].split('=');
		var name = decodeURIComponent(pair[0]);
		
		var value = (pair.length==2)
			? decodeURIComponent(pair[1])
			: name;
		
		this.params[name] = value;
	}
}

Querystring.prototype.get = function(key, default_) {
	var value = this.params[key];
	return (value != null) ? value : default_;
}

Querystring.prototype.contains = function(key) {
	var value = this.params[key];
	return (value != null);
}


$(document).ready(function(){
    $.get("assets.xml", {}, function(xml){ ///&mode=admin will return assetTypeattrib xml
        $('Asset', xml).each(function(i){
            assetType = $(this).attr("type");
            assetName = $(this).attr("name");
            // alert ('in asset attribs: ' + assetName);
            var qStr = new Querystring(location.search.substring(1, location.search.length));
            var aName = qStr.get("asset");
            var aType = qStr.get("assetType");
            if (assetName.toLowerCase() == qStr.get("asset").toLowerCase() && assetType.toLowerCase() == qStr.get("assetType").toLowerCase()) {
               // alert("found match: " + aName + '~' + aType);
                $("input[name='assetTypeIN']").setValue(assetType);
                $("input[name='assetNameIN']").setValue(assetName);
                $("input[name='assetTargetIN']").setValue($(this).attr("targettype"));
                $("input[name='assetValueIN']").setValue($(this).attr("value"));
                //alert('assetValueIN populated with: ' + $("input[name='assetValueIN']").getValue());
                qStr = new Querystring(location.search.substring(1, location.search.length));
                populateFields();
                showonlyone("" + qStr.get("asset"), 'type');
            }
        });
        window.oSelector = $("#selector");
        updateGMap( );
    });
    
    //$.get("OLXML.xml",{},function(xml){ // passing current mnode
});

function selectorSwitch(fld){
    showonlyone(fld.options[fld.selectedIndex].value, 'type');
    if(fld.options[fld.selectedIndex].value.indexOf('slgoog') == 0){
        chartSetup();
    }
    if(fld.options[fld.selectedIndex].value.indexOf('vpd') == 0){
        document.getElementById('vpd_vpd').style.display = 'block'
    }else{
        document.getElementById('vpd_vpd').style.display = 'none';
    }
 
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
    var elem = document.getElementById('frmMain').elements;
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


/*
 function getVisibleFieldsValues(divname)
     {
 var myObj = document.getElementById(divname);
 var iInput = myObj.innerHTML.toLowerCase().indexOf("<input");
 var iSelect = myObj.innerHTML.toLowerCase().indexOf("<select");

 if (iInput > -1 || iSelect > -1){
 if (iInput > -1 ) {
 s = iInput;
 } else {
 s = iSelect;
 }
 e = myObj.innerHTML.indexOf(">",s+1);
 var InputTag = myObj.innerHTML.substr(s,e-s+1);
 if (InputTag){
 s = InputTag .indexOf("name=");
 e = InputTag.indexOf("\"",s+6);
 var InputName = InputTag.substr(s+6,e-s-6);
 var thisField = document.getElementById(InputName);
 if (thisField){
 if (thisField.style.display == 'block'){ // is visible, is to be queried
 var curVal = gv4ks($("input[name='assetValueIN']").getValue());
 if (curVal != ""){
 alert(curVal, InputName);
 $("input[name='assetValueOUT']").setValue(curVal);
 //$("select[name='assetValueOUT']").setValue(curVal);
 }
 }
 }

 }
 }
 */
function populateFields(){

    var aName = document.forms[0].assetNameIN.value;
    var aType = document.forms[0].assetTypeIN.value;

    //alert('now in popFields: ' + aName + '~' + aType);

    var aVal = "";
    var aTar = "";

    if (aName != "") {
        aTar = document.forms[0].assetTargetIN.value;
        aVal = document.forms[0].assetValueIN.value;
        var oldVal = aVal;

        // loop innerhtml of the (aType.toLowerCase() +'Div')
        // get their names
        $("input[name='assetName']").setValue(aName);
        //add them to currentUIpairs
        $("select[name='selector']").setValue(aType.toLowerCase());

        if ($("input[name='currentUIpairs']").getValue() != "") {
            aVal = $("input[name='currentUIpairs']").getValue();

        }

        //aVal="assetTargetIN=9993&assetValueIN=load scene shapechoice&assetNameIN=shapechoice&assetTypeIN=SLChat&assetTargetOUT=&assetValueOUT=&assetNameOUT=&assetTypeOUT=&currentUIpairs=assetName=shapechoice&selector=Trigger an external game&isla=&csla=&al=1&ail=false&alc=1&bpt=&bpn=&cc=Other&chatChannelValue=&chatChannelMsg=&chatValue0=&chatValue-8787=&chatValue687687=&chatValue-63342=&Holodeck_chat_cmds=&sname=&sename=&controller_cmds=&hn=&hm=.5&hw=&hap=&imv=&igu=http://&lmn=&mt=&mv=&ncn=&om=.5&ow=&omvp=&on=&oap=&pn=&pm=.5&pw=&psfn=MyParticle&pstn=&psem=false&age=1&psic=false&psscr=1&psscg=1&psscb=1&psecr=1&psecg=1&psecb=1&pssa=1&psea=1&psis=false&psssx=0.04&psssy=0.04&psesx=0.04&psesy=0.04&pspp=&pspr=0&psban=0&psean=180&psmns=1&psmxs=1&psax=0&psay=0&psaz=0&psmox=0&psmoy=0&psmoz=0&psfs=false&psfv=false&psw=false&psb=false&pstr=false&pstrk=object&psr=1&psc=50&psl=0&pt=&pv=&sn=&sv=&sl=false&sd=&tn=&viu=http://&vmu=http://";

        var aFlds = []; // assetAttrib
        switch (aType.toLowerCase()) {
       
            case "slanimation":
                aFlds
                // animation type category
                $("input[name='atc']").setValue(gv4ks(aVal, 'atc'));
                //internal anims
                $("select[name='isla']").setValue(gv4ks(aVal, 'isla'));
                //custom anims
                $("select[name='csla']").setValue(gv4ks(aVal, 'csla'));
                //animLength
                $("input[name='atc']").setValue(gv4ks(aVal, 'atc'));
                //animIsLoop(checkbox),
                $("input[name='ail']").setValue(gv4ks(aVal, 'ail'));
                //animLoopCount')
                $("input[name='alc']").setValue(gv4ks(aVal, 'alc'));
                break

            case "slbodypart":
                //bodypartType')
                $("select[name='bpt']").setValue(gv4ks(aVal, 'bpt'));
                //'bodypartName')
                $("select[name='bpn']").setValue(gv4ks(aVal, 'bpn'));
                break

            case "slchat":
                //'saymode'),
                $("input[name='csm']").setValue(gv4ks(aVal, 'csm'));
                //channels
                $("select[name='cc']").setValue(gv4ks(aVal, 'cc'));
                break
            case "slhud":
                //'hudName')
                $("select[name='hn']").setValue(gv4ks(aVal, 'hn'));
                //'hudmeters')
                $("input[name='hm']").setValue(gv4ks(aVal, 'hm'));
                //hudwhere')
                $("select[name='hw']").setValue(gv4ks(aVal, 'hw'));
                //'hud_attach_point')
                $("select[name='hap']").setValue(gv4ks(aVal, 'hap'));
                break
            case "slim":
                //'imValue')
                $("input[name='imv']").setValue(gv4ks(aVal, 'imv'));
                break
            case "slinnergame":
                //'igURL')
                $("input[name='igu']").setValue(gv4ks(aVal, 'igu'));
                break
            case "sllandmark":
                //landmarkName')
                $("select[name='lmn']").setValue(gv4ks(aVal, 'lmn'));

                break
            case "slmove":
                //moveTarget')
                $("select[name='mt']").setValue(gv4ks(aVal, 'mt'));
                //moveValue')
                $("input[name='mv']").setValue(gv4ks(aVal, 'mv'));

                break
            case "slnotecard":
                //'notecardName')
                $("select[name='ncn']").setValue(gv4ks(aVal, 'ncn'));
                break
            case "slobject":

                //'objectmeters')
                $("input[name='om']").setValue(gv4ks(aVal, 'om'));
                //objectwhere')
                $("select[name='ow']").setValue(gv4ks(aVal, 'ow'));
                // MVP'),
                $("select[name='omvp']").setValue(gv4ks(aVal, 'omvp'));
                //objectName')
                $("select[name='on']").setValue(gv4ks(aVal, 'on'));
                //attach_point')
                $("select[name='oap']").setValue(gv4ks(aVal, 'oap'));
                break
            case "slpackage":
                //'packageName')
                $("select[name='pn']").setValue(gv4ks(aVal, 'pn'));
                //packagemeters')
                $("input[name='pm']").setValue(gv4ks(aVal, 'pm'));
                //packagewhere')
                $("select[name='pw']").setValue(gv4ks(aVal, 'pw'));
                break
            case "slparticlesystem":
                //particleftcname')
                $("input[name='psfn']").setValue(gv4ks(aVal, 'psfn'));
                //particletexture')
                $("select[name='pstn']").setValue(gv4ks(aVal, 'pstn'));
                //particleemissive')
                $("input[name='psem']").setValue(gv4ks(aVal, 'psem'));
                //particleinterpolate_color')
                $("input[name='psic']").setValue(gv4ks(aVal, 'psic'));
                //particlestart_color_r')
                $("input[name='psscr']").setValue(gv4ks(aVal, 'psscr'));
                //particlestart_color_g')
                $("input[name='psscg']").setValue(gv4ks(aVal, 'psscg'));
                //particlestart_color_b')
                $("input[name='psscb']").setValue(gv4ks(aVal, 'psscb'));
                //particleend_color_r')
                $("input[name='psecr']").setValue(gv4ks(aVal, 'psecr'));
                //particleend_color_g')
                $("input[name='psecg']").setValue(gv4ks(aVal, 'psecg'));
                //particleend_color_b')
                $("input[name='psecb']").setValue(gv4ks(aVal, 'psecb'));
                //particlestart_alpha')
                $("input[name='pssa']").setValue(gv4ks(aVal, 'pssa'));
                //particleend_alpha')
                $("input[name='psea']").setValue(gv4ks(aVal, 'psea'));
                //particleinterpolate_scale')
                $("input[name='psis']").setValue(gv4ks(aVal, 'psis'));
                //particlestart_scale_x')
                $("input[name='psssx']").setValue(gv4ks(aVal, 'psssx'));
                //particlestart_scale_y')
                $("input[name='psssy']").setValue(gv4ks(aVal, 'psssy'));
                //particleend_scale_x')
                $("input[name='psesx']").setValue(gv4ks(aVal, 'psesx'));
                //particleend_scale_y')
                $("input[name='psesy']").setValue(gv4ks(aVal, 'psesy'));
                //particleage')
                $("input[name='pspa']").setValue(gv4ks(aVal, 'pspa'));
                //particlepattern')
                $("input[name='pspp']").setValue(gv4ks(aVal, 'pspp'));
                //particleradius')
                $("input[name='pspr']").setValue(gv4ks(aVal, 'pspr'));
                //particlebegin_angle')
                $("input[name='psban']").setValue(gv4ks(aVal, 'psban'));
                //particleend_angle')
                $("input[name='psean']").setValue(gv4ks(aVal, 'psean'));
                //particlemin_speed')
                $("input[name='psmns']").setValue(gv4ks(aVal, 'psmns'));
                //particlemax_speed')
                $("input[name='psmxs']").setValue(gv4ks(aVal, 'psmxs'));
                //particleacceleration_x')
                $("input[name='psax']").setValue(gv4ks(aVal, 'psax'));
                //particleacceleration_y')
                $("input[name='psay']").setValue(gv4ks(aVal, 'psay'));
                //particleacceleration_z')
                $("input[name='psaz']").setValue(gv4ks(aVal, 'psaz'));
                //particleomega_x')
                $("input[name='pseox']").setValue(gv4ks(aVal, 'pseox'));
                //particleomega_y')
                $("input[name='psmoy']").setValue(gv4ks(aVal, 'psmoy'));
                //particleomega_z')
                $("input[name='psmoz']").setValue(gv4ks(aVal, 'psmoz'));
                //particlefollow_source')
                $("input[name='psfs']").setValue(gv4ks(aVal, 'psfs'));
                //particlefollow_velocity')
                $("input[name='psfv']").setValue(gv4ks(aVal, 'psfv'));
                //particlewind')
                $("input[name='psw']").setValue(gv4ks(aVal, 'psw'));
                //particlebounce')
                $("input[name='psb']").setValue(gv4ks(aVal, 'psb'));
                //particletarget')
                $("input[name='pstr']").setValue(gv4ks(aVal, 'pstr'));
                //particletarget_key')
                $("input[name='pstrk']").setValue(gv4ks(aVal, 'pstrk'));
                //particlerate')
                $("input[name='psr']").setValue(gv4ks(aVal, 'psr'));
                //particlecount')
                $("input[name='psc']").setValue(gv4ks(aVal, 'psc'));
                //particlelife')
                $("input[name='psl']").setValue(gv4ks(aVal, 'psl'));

                break
            case "slpoint":
                //'pointTarget')
                $("select[name='pt']").setValue(gv4ks(aVal, 'pt'));
                //'pointValue')
                $("input[name='pv']").setValue(gv4ks(aVal, 'pv'));

                break
            case "slsound":
                //soundName')
                $("select[name='sn']").setValue(gv4ks(aVal, 'sn'));
                //soundVolume')
                $("select[name='sv']").setValue(gv4ks(aVal, 'sv'));
                //soundloop')
                $("input[name='sl']").setValue(gv4ks(aVal, 'sl'));
                //soundduration')
                $("input[name='sd']").setValue(gv4ks(aVal, 'sd'));
                break
            case "sltexture":
                //textureName')
                $("select[name='tn']").setValue(gv4ks(aVal, 'tn'));

                break
            case "vpdimage":
                //vpdimageURL')
                $("input[name='viu']").setValue(gv4ks(aVal, 'viu'));
                break
            case "vpdmedia":
                //vpdmediaURL')
                $("input[name='vmu']").setValue(gv4ks(aVal, 'vmu'));
                break
            case "vpdtext":
                // show node title? edit node title? chose between title and node text?
                break
            default:
            // can we do anything?
        }
    }// end if
}

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
    var elementsForms = top.window.document.getElementsByTagName("form");
    var elementsList = "";
    for (var intCounter = 0; intCounter < elementsForms.length; intCounter++) {
        elementsList = elementsList + getFormValues(elementsForms[intCounter], []).join('&');
        //alert("" + elementsForms.length + elementsList);
    }
    return elementsList;
}



