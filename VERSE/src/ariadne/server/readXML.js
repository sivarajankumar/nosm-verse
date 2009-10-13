
var selectedAsset = "";
var selectedAssetType = "";

var currentSeq = new Array();

// Start function when DOM has completely loaded 
$(document).ready(function(){ 

	// Open the students.xml file
	$.get("assets.xml",{},function(xml){
      	
		// Run the function for each student tag in the XML file
		$('Asset',xml).each(function(i) {
					        assetType = $(this).attr("type");
			if (assetType.toLowerCase().indexOf("innerseq") > -1 ) {
			    currentSeq = $(this).attr("value").split("&");
			}
		});
		
		// Run the function for each student tag in the XML file
		$('Asset',xml).each(function(i) {
		
			assetType = $(this).attr("type");
		    if (assetType.toLowerCase().indexOf("innerseq") == -1){
		    
			        assetName = $(this).attr("name");
			       // alert ('in asset attribs: ' + assetName);

        			
			        assetTarget = $(this).attr("targettype");
			        assetValue = $(this).attr("value");
			        //studentPost = $(this).find("name").attr("post"); 
        			
			        // Build row HTML data and store in string
			        mydata = addAsset2Seq(assetName,assetType,assetTarget,assetValue);
    		
		    }
		
		});
		
		
		// Update the DIV called Content Area with the HTML string
		$("#ContentArea").append(BuildStudentHTML());
	});
});
 
 
 
 function BuildStudentHTML(){
	
	
		// Build an HTML string
		myHTMLOutput = '';
		myHTMLOutput += '<form name="assetselect" action="assets.aspx">';
	 	myHTMLOutput += '<table width="98%" border="1" cellpadding="0" cellspacing="0">';
	  	myHTMLOutput += '<th>select</th><th>Order</th><th>Name</th><th>Type</th><th>target</th><th>Value</th>';
	  	
	 var output = '';
	 var x = 0;	
	for (x in currentSeq){

	    var assetAr = currentSeq[x].split(',');

        
	    output += '<tr>';
	    output += '<td><input onClick="selectedAsset=\''+assetAr[0] + '\';selectedAssetType=\''+assetAr[1] + '\'" type="radio" name="assetSel" value="'+assetAr[0] + '" /></td>';
	    var orderint = 1 * x + 1;
	    output += '<td>'+ orderint + '</td>';
	    output += '<td>'+ assetAr[0] + '</td>';
	    output += '<td>'+ assetAr[1] +'</td>';
	    output += '<td>'+ assetAr[2] +'</td>';
	    output += '<td>'+ assetAr[3] +'</td>';
	    output += '</tr>';

    }
	// Build HTML string and return

	
	
	 myHTMLOutput = myHTMLOutput + output;
	// alert ('in html builder: ' + myHTMLOutput + '</table>');
	//var an = getSelectedAsset(); 
	 return myHTMLOutput + '</table> </form> '+
	 '<a href=\'javascript:window.location.href = "assetEdit.html?asset="+selectedAsset+"&assetType="+selectedAssetType\'>Edit Selected Asset</A> '+
	 '<A href=\'javascript:window.location.href = "ariadne3.aspx?action=del&asset="+selectedAsset\' >Remove Selected Asset</A> '+
	 '<A onclick="unselect()" href=\'return false;\' >Unselect</A>';

}


 function addAsset2Seq(assetName,assetType,assetTarget,assetValue){
    for (x in currentSeq){
        if (currentSeq[x].split('=')[0] == assetName){
            currentSeq[x] = assetName+","+assetType+","+assetTarget+","+assetValue;
        }
    }  
}


function unselect()
{
for (x in document.forms[0].assetSel.length){
  document.forms[x].assetSel[x].checked = false;
}
  

  
}