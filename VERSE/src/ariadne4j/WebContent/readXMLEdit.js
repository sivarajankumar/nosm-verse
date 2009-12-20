var thissessid;
$(document).ready(function(){
	var sessidStr = "";
    mnode = $(document).getUrlParam("mnodeid");
	thissessid = $(document).getUrlParam("sessid");
	if (thissessid != null  ){
		sessidStr = "&sessid="+ thissessid;
	}else{
		/*
		$.get("Ariadne?v=2&mnodeid=start&mode=admin", {}, function(xml){
			$('node', xml).each(function(k){
				mnode = $(this).attr("id");
	        });
			// get session
			$('session', xml).each(function(y){
				//if (sessidStr != $(this).attr("id")) {
				sessidStr = "&sessid=" + $(this).attr("id");
	        });
		});
		*/
	}

// now gets called with correct node and session ids from returned /data/classic/ xml:
    $.get("Ariadne?v=2&mnodeid=" + mnode + "&mode=admin"+sessidStr, {}, function(xml){//&mode=admin will return assetTypeattrib xml
        //$.get("asset.xml", {}, function(xml){ // testing xml

        // get node id (decouples from url )
		$('node', xml).each(function(k){
			mnode = $(this).attr("id");
        });
        $("input[name='assetMapNodeid']").setValue(mnode);

		// get session
		$('session', xml).each(function(y){
			if (thissessid != $(this).attr("id")) {
				//alert(thissessid +' does not match: '+$(this).attr("id"));
			}

        });

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
			$("div[id^=help_sldoc]").show();
		}
    });

	$("input[name='useRespChannel']").change(function(){
		if ($("input[id='useRespChannel']").getValue() == '1'){
			$("div[id^=use_resp_chatchannel]").show();
		}else{
			$("div[id^=use_resp_chatchannel]").hide();
		}
	});

	$("select[name='sel_resp_chatchannel']").click(function(){
		if ($("select[name='sel_resp_chatchannel']").getValue() == "-1"){
			$("div[id^=resp_chatchannel_Other]").show();
			$("input[id='resp_chatchannel_Other_text']").setValue($("select[name='sel_resp_chatchannel']").getValue()) ;
		}else{
			$("div[id^=resp_chatchannel_Other]").hide();
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
        if ($("select[name='Holodeck_chat_cmds']").getValue().indexOf('run') == 0 ||
        $("select[name='Holodeck_chat_cmds']").getValue().indexOf('end') == 0 ) {
			// issue 32: this does not work:
            //$("#sceneName").show();
            $("div[id^=sceneName]").show();
        } else {
            //$("#sceneName").hide();
            $("div[id^=sceneName]").hide();
        }
    });

    $("input[name='atc']").change(function(){
		$("div[id^=animType_]").hide();
        $("div[id^=animType_" + $("input[name='atc']").getValue()+"]").show();
        //$('#selectList :selected').text();
    });

    $("input[name='stc']").change(function(){
		$("div[id^=soundType_]").hide();
        $("div[id^=soundType_" + $("input[name='stc']").getValue()+"]").show();
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

    $("#manq_int").change(function(){
        if ($("select[name='manq_int']").getValue() == "talk") {
            $("div[id^=mannequin_talk]").show();
            $("div[id^=mannequin_part]").hide();
        } else {
            $("div[id^=mannequin_talk]").hide();
            $("div[id^=mannequin_part]").show();
        }
    });

    $("input[name='manq_action']").change(function(){
		$("div[id^=manq_]").hide();
        $("div[id^=manq_" + $("input[name='manq_action']").getValue()+"]").show();
        //$('#selectList :selected').text();
    });

    $("input[name='type_objs']").change(function(){
		$("div[id^=_objs]").hide();
        $("div[id^="+$("input[name='type_objs']").getValue()+"_objs]").show();
        //$('#selectList :selected').text();
    });

    $("input[name='type_linkable_objs']").change(function(){
		$("div[id^=_objs]").hide();
		if ($("input[name='type_linkable_objs']").getValue() != "mvp_paramedic"){
				$("div[id^=linkable_obj_name]").show();
				$("div[id^=mvp_paramedic_linkable_objs]").hide();
			}else{
				$("div[id^=linkable_obj_name]").hide();
				$("div[id^=mvp_paramedic_linkable_objs]").show();
			}
    });

	$("input[name='target_key']").change(function(){
        if ($("select[name='target_key']").getValue() == 'parameter' ) {
            $("div[id^=target_key_param]").show();
        } else {
	        if ($("select[name='target_key']").getValue() == 'owner') {
				$("div[id^=target_key_user]").show();
			} else {
				$("div[id^=target_key_user]").hide();
			}
            $("div[id^=target_key_param]").hide();
        }
	});

    $("#mannequin_cmds").change(function(){
        if ($("select[name='mannequin_cmds']").getValue() == 'resize' ) {
            $("div[id^=mannequin_func_resize]").show();
        } else {
            $("div[id^=mannequin_func_resize]").hide();
        }
    });

}); // end ready

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
				if (outgoingVal.indexOf('run') == 0) {
					if (outgoingVal == 'run shell') {
						var shTrim = $("select[id^=ainv_slchat]").getValue().replace("SHELL ", "");
						outgoingVal = outgoingVal + ' ' + shTrim;
						outgoingName = shTrim;
					}else {
						outgoingName = $("select[id^=ainv_slchat]").getValue();
						outgoingVal = outgoingVal + ' ' + $("select[id^=ainv_slchat]").getValue();
					}
				}
				else {
					outgoingName = $("select[id^=ainv_slchat]").getValue();
				}
			}
			if (curChan == "687686") { //PIVOTE
				outgoingVal = $("select[name='controller_cmds']").getValue();
				if (outgoingVal == 'option') {
					outgoingVal = outgoingVal + ' ' + $("select[name='controller_opt']").getValue();
				}
			}

			if (curChan == "7051674") { //MANNEQUIN
				if ($("input[name='manq_action']").getValue().indexOf("set") == 0) {
					if ($("select[name='manq_int']").getValue() == "talk") {
						outgoingVal = 'ask '+ $("select[name='mannequin_ask']").getValue();
					}
					else {
						outgoingVal = $("select[name='mannequin_parts']").getValue();
					}
					outgoingName = outgoingVal;
					outgoingVal = "set " + outgoingVal + ":" + $("select[name='linklist_manq']").getValue() + ":" + $("#linklist_manq :selected").text();
				}else{
					var tCmd = $("select[name='mannequin_cmds']").getValue();
					if (tCmd.indexOf("resize") > -1){
						tCmd = tCmd + "~" + $("select[name='mannequin_resize']").getValue();
					}
					outgoingVal = tCmd;
				}
			}

			if (curChan == "-9898") {

			if ($("input[name='type_linkable_objs']").getValue() != "mvp_paramedic"){
//
			}else{
//
			}


				if ($("input[name='linkable_ch_val']").getValue() != "") curChan = $("input[name='linkable_ch_val']").getValue();
				outgoingVal = "set " + $("input[name='linkable_obj_val']").getValue() + ":" + $("select[name='linklist_linkable']").getValue() + ":" + $("#linklist_linkable :selected").text();
				outgoingName = $("input[name='linkable_obj_val']").getValue();
			}

			if (curChan == "23") { //Multigadget_Multicast
				outgoingName = $("select[name='mg_cmd']").getValue();
				outgoingVal = "cmd|" + $("select[name='mg_cmd']").getValue();
			}


			if (curChan == "-8787") { //Registration_Booth-viewer_area_edition
				outgoingName = $("input[name='chatChannelBoothMsg']").getValue();
				outgoingVal = $("input[name='chatChannelBoothMsg']").getValue();
			}

			if (curChan == "-63342") { //Controller_Media_Relay-viewer_area_edition
				outgoingName = $("input[name='chatChannelRelayMsg']").getValue();
				outgoingVal = $("input[name='chatChannelRelayMsg']").getValue();
			}

			if (curChan == "603") { //NOSM_Bracelet_Multicast
				outgoingName = $("input[name='chatChannelBraceletMsg']").getValue();
				outgoingVal = $("input[name='chatChannelBraceletMsg']").getValue();
			}

			if (curChan == "-3857343") { //Sloodle_Object
				//sloodle_name, lesson_id, page_id
				outgoingName = $("select[name='sloodle_api']").getValue();
				outgoingVal = $("select[name='sloodle_api']").getValue();
			}

			if (curChan == "1001") { //Sloodle_Player_Multicast
				outgoingName = $("input[name='Sloodle_Player_msg']").getValue();
				outgoingVal = $("input[name='Sloodle_Player_msg']").getValue();
			}

			if (curChan == "55") { //Lite_Prim_Animator
				// chatChannelLite_Prim_Animator
				outgoingName = $("select[name='prim_anim_cmd']").getValue();
				outgoingVal = $("select[name='prim_anim_cmd']").getValue();
			}

			if (curChan == "56") { //PrimPuppeteer
				outgoingVal = $("select[name='prim_pup_cmd']").getValue();
				outgoingName = outgoingVal;
				if (outgoingName == "show") {
					outgoingVal = outgoingVal + " " +
					$("select[name='chatChannelLite_PrimPuppeteer + ").getValue();
				}
			}

			if (curChan == "0") {//Public
				outgoingName = $("input[name='chatChannelPubMsg']").getValue();
				outgoingVal = $("input[name='chatChannelPubMsg']").getValue();
			}

			if (curChan == ""){
				outgoingName = $("input[name='chatChannelMsg']").getValue(); // other
				curChan = $("input[name='chatChannelValue']").getValue();
				outgoingVal = $("input[name='chatChannelMsg']").getValue();
			}


			outgoingTarget = curChan;
            outgoingVal = outgoingVal + '~' + $("input[id='csm']").getValue();
            //outgoingTarget = $("select[name='linklist_manq']").getValue(); // node link

			//outgoingName = $("#chatchannel :selected").text();

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

			if ($("input[name='isctrlsanim']").getValue() == "1") { // animation states
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
            outgoingVal = $("input[id='imt']").getValue()
            outgoingTarget = $("input[id^=userlist_] :visible").val();
            outgoingName = "IM:"+ $("input[id^=userlist_] :visible").val();
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
			if ($("select[id='target_key']").getValue() != "owner") {
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

			outgoingVal = "~" + $("select[name='sv']").getValue(); // volume

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
		    outgoingVal = $("input[name='staticMapURL']").getValue();
			outgoingTarget = 'PIVOTE';

        case 'SLAmazonMapImage':
            // outgoingVal = 'URL~interval (if live), MIME_MIME_MIME, ';
            outgoingTarget = 'PIVOTE';
            outgoingName = '';
            break;

        default:
            alert('oops... did not get the current type');
            //break;
			return;
    }

	if (outgoingVal.indexOf("~") == -1){
		outgoingVal = outgoingVal + "~"; // append one to not break assignSL()
	}

    $("input[id='assetValueIN']").setValue(outgoingVal);
    $("input[id='assetTargetIN']").setValue(outgoingTarget);
    $("input[id='assetNameOUT']").setValue(outgoingName);
    $("input[id='assetType']").setValue(curType);

	$("input :hidden").attr('disabled', 'disabled');
	$("select :hidden").attr('disabled', 'disabled');

    //var queryString = $('#mainDummy').serialize();

	var queryString = $(":visible").serialize();
   // var queryString = '' + $('#hidden_dummy').formHash();

	$("input :hidden").removeAttr('disabled');
	$("select :hidden").removeAttr('disabled');

    $("input[name='currentUIpairs']").setValue(queryString);
    $("input[id='savedUIPairs']").setValue(queryString);

    document.forms["hidden_dummy"].submit();
}
