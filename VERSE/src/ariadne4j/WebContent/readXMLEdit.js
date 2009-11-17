$(document).ready(function(){
    mnode = $(document).getUrlParam("mnodeid");
    $.get("Ariadne?mnodeid=" + mnode + "&mode=admin", {}, function(xml){
        //$.get("asset.xml", {}, function(xml){ ///&mode=admin will return assetTypeattrib xml
        //if (mnode != $('node', xml).attr("id")) {
        //  alert('node id returned (' + $('node', xml).attr("id") + ') in xml does not match node requested: ' + mnode);
        //}
        //else {
        mnode = $('node', xml).attr("id");
        $("input[name='assetMapNodeid']").setValue(mnode);

        // assets
        $('asset', xml).each(function(i){
            assetType = $(this).attr("type");
            assetName = $(this).attr("name");
			assetID = $(this).attr("iid");
			var qsAssetID = $(document).getUrlParam("asset");
            //var aNamexml = $(document).getUrlParam("asset");
            var assetTypexml = $(document).getUrlParam("assetType");

            if (qsAssetID != null && assetID == qsAssetID &&
            assetType.toLowerCase() == assetTypexml.toLowerCase()) {
                $("input[name='assetTypeIN']").setValue(assetTypexml);
                $("select[name='selector']").setValue(assetType); //UI field
                $("input[name='assetNameIN']").setValue(assetName);
                $("input[name='assetNameOUT']").setValue(assetName);
                $("input[name='assetTargetIN']").setValue($(this).attr("targettype"));
                $("input[name='assetValueIN']").setValue($(this).attr("value"));
                $("input[name='assetName']").setValue(assetName); // UI field
                $("input[name='currentUIpairs']").setValue($(this).attr("uipairs"));
                $("input[name='assetid']").setValue(assetID);
				//$("select[name='selector']").setValue(assetType); //UI field
            } else {
                // is new object
                $("input[name='assetTypeIN']").setValue(assetTypexml);
                $("select[name='selector']").setValue(assetType); //UI field
            }
        });

        //links
        $('link', xml).each(function(j){ //label //ref
            // alert('found some links');
            $('select[id^=linklist_]').append("<option value='" + $(this).attr("ref") + "'> " +
            $.URLDecode($(this).attr("label")) +
            "</option>");
        });

        $.get("Users?action=getall&mode=admin", {}, function(xml){
            //users
            $('user', xml).each(function(k){ // name
                //alert('found some users');
                $('select[id^=userlist_]').append("<option value='" + $.URLDecode($(this).attr("name")) + "'> " +
                $(this).attr("name") +
                "</option>");
                // target avatar for SLAction:
                $('select[id^=userlistmove]').append("<option value='" + $.URLDecode($(this).attr("name")) + "'> " +
                $(this).attr("name") +
                "</option>");
            });
        });

        $.get("Inventory?action=getall&mode=admin", {}, function(xml){
            //asset types
            $('atype', xml).each(function(l){
                //<atype name="SHELL black" type="SLChat" sltype="-1">
                //alert('found some types');
                /*
                 0=texture, 1=sound, 3=landmark,
                 5=clothing, 6=object, 7=notecard,
                 10=script, 13=body part, 20=animation,
                 90=hud, 91=crate, 92=feed obj, 99=other
                 holodeck???
                 */
                // if
                $('#ainv_' + $.URLDecode($(this).attr("type")).toLowerCase()).append("<option value='" +
                $(this).attr("name") +
                "'>" +
                $.URLDecode($(this).attr("name")) +
                "</option>");
            });
        });

        // alert("has asset id been set into the assetid field?  " + $("input[name='assetid']").getValue());
        window.oSelector = $("#selector");
        // }
    });

    $("#selector").change(function(){
        thisTypeSel = $("select[name='selector']").getValue().toLowerCase();

        $("div[id^=type_]").hide(); //.siblings().hide(); // hide all other divs
        $("div[id^=help_]").hide(); //auto hide any open help divs onchange
        $("div[id^=type_" + thisTypeSel + "]").show();

        if (thisTypeSel.indexOf('slgoogleapiimage') == 0) {
            chartSetup();
        }
        if (thisTypeSel.indexOf('slgoogleapihtml') == 0) {
            mapLoad();
        }

        if (thisTypeSel.indexOf('slamazonmap') == 0) {
            GUnload();
            loadSLmap();
        }
        else {
            // do this for google maps to? any other google objects? viz?
            updateGMap();
			mapLoad();
        }
        if (thisTypeSel.indexOf('vpdmedia') == 0)
            showYT();

        if (thisTypeSel.indexOf('vpd') == 0) {
            $("#vpd_vpd").show();
        }
        else {
            $("#vpd_vpd").hide();
        }
    });

    $('#hidden_dummy').ajaxForm({
        // dataType identifies the expected content type of the server response
        dataType: 'html',

        //target:        '#output1',   // target element(s) to be updated with server response
        //beforeSubmit: dropngo, // pre-submit callback
        success: processXml, // post-submit callback
        // other available options:
        //url: /AriadneController
        // override for form's 'action' attribute
        //type:      type        // 'get' or 'post', override for form's 'method' attribute
        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
        //clearForm: true        // clear all form fields after successful submit
        //resetForm: true        // reset the form after successful submit

        // $.ajax options can be used here too, for example:
        //timeout:   3000

    });

    $("#chatchannel").change(function(){
		$("div[id^=channelType_]").hide();
        $("div[id^=channelType_" + $("#chatchannel :selected").text()+"]").show();
       // $('#selectList :selected').text();
    });


    $("#isctrlsanim").change(function(){
		if ( $("input[id='isctrlsanim']").getValue() == 1){
				$("div[id=anim_ctrls]").show();
			}else{
				$("div[id=anim_ctrls]").hide();
		}
	});

	$("#help_ref").click(function(){
		if ($("select[name='selector']").getValue() != "") {
			$("div[id=help_" + $("select[name='selector']").getValue().toLowerCase() + "]").show();
		} else {
			$("div[id=help_sldoc]").show();
		}
    });

    $("#isctrlssnd").change(function(){
		if ( $("input[id='isctrlssnd']").getValue() == 1){
				$("div[id=snd_ctls]").show();
			}else{
				$("div[id=snd_ctls]").hide();
		}
	});


    $("#mt").change(function(){
        // if move towards person, show person to select
    });

    $("#Holodeck_chat_cmds").change(function(){
        if ($("select[name='Holodeck_chat_cmds']").getValue().indexOf('run ') == 0 ||
        $("select[name='Holodeck_chat_cmds']").getValue().indexOf('end ') == 0) {
            $("#scenename").show();
            $("div[id^=scenename]").show();
        } else {
            $("#scenename").hide();
            $("div[id^=scenename]").hide();
        }
    });

    $("input[name='atc']").change(function(){
		$("div[id^=animType_]").hide();
        $("div[id^=animType_" + $("input[name='atc']").getValue()+"]").show();
        //$('#selectList :selected').text();
    });

    $("#hioc").click(function(){
        if ($("input[name='hioc']").getValue() == "rez") {
            $("div[id^=hud_rez]").show();
        } else {
            $("div[id^=hud_rez]").hide();
        }
    });


    $("#oioc").click(function(){
        if ($("input[name='oioc']").getValue() == "rez") {
            $("div[id^=obj_rez]").show();
        } else {
            $("div[id^=obj_rez]").hide();
        }
    });

    $("#pioc").click(function(){
        if ($("input[name='pioc']").getValue() == "rez") {
            $("div[id^=crate_rez]").show();
        } else {
            $("div[id^=crate_rez]").hide();
        }
    });

    $("#ainv_slextfeedobject").click(function(){
		$("div[id^=objs_]").hide();
        $("div[id^=objs_" + $("select[name='ainv_slextfeedobject']").getValue() + "]").show();
    });

    $("#controller_cmds").click(function(){
        if ($("select[name='controller_cmds']").getValue() == "option") {
            $("div[id^=linkopt_option]").show();
        } else {
            $("div[id^=linkopt_option]").hide();
        }
    });

    $("#a[id^=submit]").click(function(){

        $('#hidden_dummy').submit(function(){
            // submit the form

            alert('submitting dammit');
            $(this).ajaxSubmit();
            // return false to prevent normal browser submit and page navigation
            return true;
        });
    });

    $("#selector").change();
    if ($("div[id^=type_] :visible").children.change) {
        $("div[id^=type_] :visible").children.change();
    }
    if ($("div[id^=type_] :visible").children.click) {
        $("div[id^=type_] :visible").children.click();
    }
});

function processXml(responseXML){
    $('#hidden_dummy').ajaxSubmit();

    $(":hidden").removeAttr('disabled');
    //$("div[id^=type_]").children.("select").removeAttr('disabled');
    //$("div[id^=type_]").children.("input").removeAttr('disabled');

    //alert("responseXML: " + responseXML);
    location.reload(true);
}

    var isInt = function(n){
        var reInt = new RegExp(/^-?\d+$/);
        if (!reInt.test(n)) {
            return false;
        }
        return true;
    }

function submitIt(){

    //$("div[id^=type_] :hidden").attr('disabled', 'disabled');
    //$("div[id^=type_] :hidden").children.$("select").attr("disabled", true);
    //$("div[id^=type_] :hidden").children.$("input").attr("disabled", true);

    //$("div[id^=type_] :visible").removeAttr('disabled');
    //$("div[id^=type_] :visible").children.$("select").attr("disabled", false);
    //$("div[id^=type_] :visible").children.$("input").attr("disabled", false);

    var curType = $("select[name='selector']").getValue();

    var outgoingVal = '';
    var outgoingTarget = '';
    var outgoingName = '';

    // params built here MUST match the controller LSL switch statement in assignSL():
    switch (curType) {
        case 'SLChat':
            var curChan = $("select[name='chatchannel']").getValue();
            if (curChan == '9993') { //HOLODECK
                outgoingVal = $("select[name='Holodeck_chat_cmds']").getValue();
                outgoingVal = outgoingVal + ' ' + $("input[id^=ainv_slchat] :visible").val();
            }
            else {
                if (curChan == "687686") { //PIVOTE
                    outgoingVal = $("select[name='controller_cmds']").getValue();
                    if (outgoingVal == 'option') {
                        outgoingVal = outgoingVal + ' ' + $("select[name='controller_opt']").getValue();
                    }
                }
                else {
                    if (curChan == "7051674") { //MANNEQUIN
                        if ($("input[name='manq_int']").getValue() != 'manq_talk') {
                            outgoingVal = $("select[name='mannequin_cmds']").getValue();
                        }
                        else {
                            outgoingVal = $("select[name='mannequin_parts']").getValue();
                        }
                    }
                    else {
                        //get textfield
                        outgoingVal = $("input[name='chatChannelMsg']").getValue();
                    }
                }
            }

            outgoingVal = outgoingVal + '~' + $("input[id='csm']").getValue();
            //outgoingTarget = $("select[name='chatchannel']").getValue();
            outgoingTarget = $("select[name='linklist_manq']").getValue(); // node link
            outgoingName = $("#chatchannel :selected").text();
            break;

        case 'VPDText':
            /*
         if ($("input[name='useExtText']").getValue() == 'wado_useVPD'){
         outgoingTarget = 'VPD';
         }else{
         outgoingTarget = 'wado_useVPD';
         }
         outgoingVal = 'url~wado_VPDTextContentType|wado_VPDTextContentType~wado_charset|wado_charset|wado_charset';
         outgoingName = '???';
         */
            break;

        case 'SLAnimation':
            if ($("input[name='atc']").getValue() == 'custom') {
                outgoingName = $("select[name='ainv_slanimation']").getValue();
            } else {
                if ($("input[name='atc']").getValue() == 'canned') {
                    outgoingName = $("select[name='isla']").getValue();
                } else {
                    if ($("input[name='atc']").getValue() == 'cannedOS') {
                        outgoingName = $("select[name='iosa']").getValue();
                    } else {
                        //emote
                        outgoingName = $("select[name='isle']").getValue();
                    }
                }
            }

			if(isInt($("input[name='al']").getValue()) != false){ //duration
                outgoingVal =  $("input[name='al']").getValue();
            }else{
				outgoingVal = outgoingVal + "~0"
			}

			if ($("input[name='ail']").getValue() != "1") { //isLoop
				outgoingVal = outgoingVal + "~1";
			}else{
				outgoingVal = outgoingVal + "~0"
			}

			if(isInt($("input[name='alc']").getValue()) != false){  //loop Count
             	 outgoingVal = outgoingVal +  "~" +$("input[name='alc']").getValue();
            }else{
				outgoingVal = outgoingVal +  "~0"
			}

			if ($("input[name='isctrlsanim']").getValue() == "1") { // animation states, comma-delimited
				outgoingVal = outgoingVal + "~" + $("select[name='ao_ctrls']").getValue();
			}
			else{
				outgoingVal = outgoingVal +  "~"
			}

            outgoingTarget = $("select[id='userlist_anim']").getValue(); // do we need to fetch the key instead?
            break;

        case 'SLBodypart':
            outgoingVal = $("select[id='bpt']").getValue();
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_slbodypart']").getValue();
            break;

        case 'SLHud':
            // outgoingVal = 'r/i, dist, rel, attch point';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_slhud']").getValue();
            break;

        case 'SLIM':
            //outgoingVal = 'msg';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            //outgoingName = "";
            break;

        case 'SLExtFeedObject':
            /*
         outgoingVal = 'encode(URL)';
         outgoingTarget = 'channel?';
         outgoingName = 'gamename?';
         */
            break;

        case 'SLLandmark':
            //outgoingVal = "";
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_sllandmark']").getValue();
            break;

        case 'SLAction':
            //outgoingVal = 'av/obj(alpha) or vector (<x,x,x>)';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            //outgoingName = '';
            break;

        case 'SLNotecard':
            //outgoingVal = '';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_slnotecard']").getValue();
            break;

        case 'SLObject':
            //outgoingVal = 'r/i, http (for chattable objs)/or node to call, params';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_slobject']").getValue();
            break;

        case 'SLPackage':

            //outgoingVal = 'r/i, dist, rel,';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = $("select[id='ainv_slpackage']").getValue();
            break;

        case 'SLParticleSystem':
			GenCode();

			outgoingVal = $("input[id='generatedcode']").getValue();

			outgoingTarget = $("input[id^=userlist_] :visible").val();
			if ($("select[id='target_key']").getValue() != "") {
				outgoingTarget = $("select[id='target_key']").getValue();
			}

            outgoingName = $("select[id='ftcname']").getValue();
            break;

        case 'SLSound':
            if ($("input[name='stc']").getValue() == 'canned') {
                outgoingName = $("select[name='isls']").getValue();
            } else {
                outgoingName = $("select[name='ainv_slsound']").getValue();
            }

			outgoingVal = "~" + $("select[sv']").getValue(); // volume

			if ($("input[name='sloop']").getValue() != "1") { //isLoop
				outgoingVal = outgoingVal + "~1";
			}else{
				outgoingVal = outgoingVal + "~0"
			}

			if(isInt($("input[name='sd']").getValue()) != false){  //duration
             	 outgoingVal = outgoingVal +  "~" +$("input[name='sd']").getValue();
            }else{
				outgoingVal = outgoingVal +  "~0"
			}

			if ($("input[name='isctrlssnd']").getValue() == "1") { // animation states, comma-delimited
				outgoingVal = outgoingVal + "~" + $("select[name='snd_ao_ctrls']").getValue();
			}
			else{
				outgoingVal = outgoingVal +  "~"
			}


            // outgoingVal = 'vol, isloop, dur, ctrls[]';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            break;

        case 'SLTexture':
            //outgoingVal = '';
            outgoingTarget = $("input[id^=userlist_] :visible").val();
			outgoingName = $("select[name='ainv_sltexture']").getValue();

            break;

        case 'VPDImage':

        case 'VPDMedia':

        case 'SLGoogleAPIImage':
			outgoingTarget = 'PIVOTE';
			outgoingVal = $("input[name='chart_url']").getValue();
			outgoingName = $("input[name='chart_name']").getValue();

        case 'SLGoogleAPIHTML':
			outgoingTarget = 'PIVOTE';

        case 'SLAmazonMapImage':
            // outgoingVal = 'URL~interval (if live), MIME_MIME_MIME, ';
            outgoingTarget = 'PIVOTE';
            outgoingName = '';
            break;

        case 'VPMannequin':
            // outgoingVal = 'attach/touch';
            // outgoingTarget = 'node-to-call';
            // outgoingName = 'part';
            break;

        default:
            alert('oops... did not get the current type');
            //break;
			return;

    }
//alert("outgoingVal: "+outgoingVal+", outgoingTarget: "+outgoingTarget+", outgoingName: "+outgoingName);
    $("input[id='assetValueIN']").setValue(outgoingVal);
    $("input[id='assetTargetIN']").setValue(outgoingTarget);
    $("input[id='assetNameOUT']").setValue(outgoingName);
    $("input[id='assetType']").setValue(curType);

	$("input :hidden").attr('disabled', 'disabled');
	$("select :hidden").attr('disabled', 'disabled');

    //var queryString = $('#mainDummy').serialize();

	var queryString = $(":visible").serialize();

	//alert(queryString);

   // var queryString = '' + $('#hidden_dummy').formHash();

	$("input :hidden").removeAttr('disabled');
	$("select :hidden").removeAttr('disabled');

    $("input[name='currentUIpairs']").setValue(queryString);
    $("input[id='savedUIPairs']").setValue(queryString);

    document.forms["hidden_dummy"].submit();
}
