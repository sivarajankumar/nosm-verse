
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
			        //assetPost = $(this).find("name").attr("post");

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
	 	myHTMLOutput += '<table width="98%" border="0" cellpadding="0" cellspacing="0">';
	  	myHTMLOutput += '<th></th><th>Order</th><th>type</th><th>target</th>';

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
		    //output += '<td>'+ assetAr[1] + '</td>';
		    output += '<td>'+ assetAr[2] +'</td>';
		    output += '<td>'+ assetAr[3] +'</td>';
		    //output += '<td>'+ assetAr[4] +'</td>';
	    output += '</tr>';

		// append name to master sequence string
	    masterseq = masterseq + ','+ assetAr[1];
    }
	$("input[name='masterseqIN']").setValue($.URLEncode(masterseq)); // should we encode?
	 myHTMLOutput = myHTMLOutput + output;
	// alert ('in html builder: ' + myHTMLOutput + '</table>');
	//var an = getSelectedAsset();
	 return myHTMLOutput + '</table> </form> '+
	 '<a href=\'javascript:window.location.href = "assetEdit.html?asset="+selectedAsset+"&assetType="+selectedAssetType + "&mnodeid=" + $(document).getUrlParam("mnodeid") \'>Edit Selected Asset</A> <BR/>'+
	 '<A href=\'javascript:window.location.href = "assetEdit.html?mnodeid=" + $(document).getUrlParam("mnodeid") \'>Append New Asset to this Sequence</A><br/> <BR/>'+
	 '<A href=\'javascript:window.location.href = "Ariadne?mnodeid=" + $(document).getUrlParam("mnodeid")+"&mode=admin&action=del&asset="+selectedAsset\' >Remove Selected Asset from this sequence</A> <BR/>'+
	 '<A onclick="unselect()" href=\'javascript:void(0);\' >Unselect</A>';
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
	for (x in document.forms[0].assetSel.length){
	  document.forms[x].assetSel[x].checked = false;
	  //document.form1.reset();
	}

}
