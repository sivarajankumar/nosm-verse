
var selectedAsset = "";
var selectedAssetType = "";

var currentSeq = new Array();

// Start function when DOM has completely loaded
$(document).ready(function(){
	mnode = $(document).getUrlParam("mnodeid");
	//$.get("assets.xml",{},function(xml){
		$.get("Ariadne?mnodeid=" + mnode + "&mode=admin",{},function(xml){
		// Run the function for each asset tag in the XML file
		$('asset',xml).each(function(i) {
					        assetType = $(this).attr("type");
			if (assetType.toLowerCase().indexOf("innerseq") > -1 ) {
			    currentSeq = $.URLDecode($(this).attr("value")).split(",");
				$("input[name='masterseqIN']").setValue(currentSeq);
			}
		});
		// Run the function for each asset tag in the XML file
		$('asset',xml).each(function(i) {
			assetType = $(this).attr("type");
		    if (assetType.toLowerCase().indexOf("innerseq") == -1){
			        assetName = $(this).attr("name");
			       // alert ('in asset attribs: ' + assetName);
			        assetTarget = $(this).attr("targettype");
			        assetValue = $(this).attr("value");
					assetID = $(this).attr("iid");

			        // Build row HTML data and store in string
			        mydata = addAsset2Seq(assetID,assetName,assetType,assetTarget,assetValue);
		    }
		});
		// Update the DIV called Content Area with the HTML string
		$("#ContentArea").append(BuildAssetHTML());
	});
});

 function BuildAssetHTML(){
		// Build an HTML string
		myHTMLOutput = '';
		myHTMLOutput += '<form name="assetselect" action="assets.aspx">';
	 	myHTMLOutput += '<table width="400px" border="0" cellpadding="0" cellspacing="0">';
		myHTMLOutput += '<tr><th></th><th><img src="http://nosm-verse.googlecode.com/hg/VERSE/src/ariadne/server/ariadne.jpg" align="left" height="90" width="75"></th><th>&nbsp;</th><th colspan="2" align="left">Ariadne4j</th><th>&nbsp;</th><th></th></tr>';
		myHTMLOutput += '<tr><th></th><th>&nbsp;</th><th>&nbsp;</th><th colspan="2" align="left">&nbsp;</th><th>&nbsp;</th><th></th></tr>';
		myHTMLOutput += '<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
		myHTMLOutput += '<tr><td></td><td align="left"><i>order</i></td><td align="left">&nbsp;</td><td></td><td align="left"><i>type</i></td><td></td><td align="left"><i>target</i></td></tr>';

	var output = '';
	var x = 0;
	var masterseq = '';

	for (x in currentSeq){
	    var assetAr = currentSeq[x].split('*');
	    output += '<tr>';
		    output += '<td><input onClick="selectedAsset=\''+assetAr[0]
			+ '\';selectedAssetType=\''+assetAr[2]
			+ '\'" type="radio" name="assetSel" value="'
			+assetAr[0] + '" /></td>';

		    var orderint = 1 * x + 1;
		    output += '<td>'+ orderint + '</td>';
			output += '<td>&nbsp;</td><td></td>';

			var dType = assetAr[2];
			if (dType.indexOf('SL') == 0){
						dType = dType.substring(2, dType.length);
			}

		    output += '<td>'+ dType + '</td>';
			output += '<td></td>';
		    output += '<td>'+ assetAr[3] +'</td>';
		    //output += '<td>'+ assetAr[4] +'</td>';
	    	output += '</tr>';

		// append name to master sequence string
	    masterseq = masterseq + ','+ assetAr[1];
    }
	$("input[name='masterseqIN']").setValue($.URLEncode(masterseq));

	 myHTMLOutput = myHTMLOutput + output;

	 return myHTMLOutput + '</tbody></table> </form> '+
	 '<a href=\'javascript:window.location.href = "assetEdit.html?asset="+selectedAsset+"&assetType="+selectedAssetType + "&mnodeid=" + $(document).getUrlParam("mnodeid") \'>Edit Selected Asset</A> <BR/>'+
	 '<A href=\'javascript:window.location.href = "assetEdit.html?mnodeid=" + $(document).getUrlParam("mnodeid") \'>Append New Asset</A><br/> <BR/>'+
	 '<A href=\'javascript:window.location.href = "Ariadne?mnodeid=" + $(document).getUrlParam("mnodeid")+"&mode=admin&action=del&asset="+selectedAsset\' >Remove Selected Asset</A> <BR/>'+
	 '<a href="#" onClick="unselect()">Unselect</a>';
}


 function addAsset2Seq(assetID, assetName,assetType,assetTarget,assetValue){
    for (x in currentSeq){
        if (currentSeq[x] == assetName){
            currentSeq[x] = assetID+"*"+assetName+"*"+assetType+"*"+assetTarget+"*"+assetValue;
        }
    }
}


function unselect()
{
	//for (var i=0; i <= document.forms[1].assetSel.length; i++){
	  //document.forms[1].assetSel[i].checked = false;
	//}
	document.forms[1].assetSel.checked = false;
}
