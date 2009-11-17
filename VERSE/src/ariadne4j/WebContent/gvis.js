
function removeChildrenFromNode(node) {
    if(node.hasChildNodes()) {
        while(node.childNodes.length >= 1 ) {
            node.removeChild(node.firstChild);
        }
    }
}

var deliObj;

function deliList(o){
	deliObj=o;
	var ds=document.getElementById('datastore');
	for (var i=0; i<o.length;i++) {
		//o[i].d, o[i].u o[i].n.split('--')[1]
		var opt=document.createElement('option');
		opt.setAttribute('value',i);
		opt.innerHTML=o[i].d;
		ds.appendChild(opt);
	}
}

function preview3(){
	var sel=document.getElementById('datastore');
	var selection=sel.selectedIndex;
	sel=sel[selection].value;
	var key=deliObj[sel].n.split('--')[1];
	if (/key=([^&]*)/.test(key)) key=/key=([^&]*)/.exec(key)[1];
	document.getElementById('gsKey').value=key;
	var txt='<a href="'+deliObj[sel].u+'">'+deliObj[sel].d+'</a>';
	document.getElementById('statrep').innerHTML=txt;
	preview();
}

function preview2(){
	preview();
	drawViz();
}

  var key='rvWgEEGK9xuUQBR1EFcxHWA';

  function preview() {
  	  if (document.getElementById('gsKey').value!='') key=document.getElementById('gsKey').value;
  	  if (/key=([^&]*)/.test(key)) key=/key=([^&]*)/.exec(key)[1];

  	  var sheet=document.getElementById('gsSheet').value;
  	  if (sheet!='') key+='&gid='+sheet;

  	  var url='http://spreadsheets.google.com/tq?tq=select%20*where%20A=false&key='+key;
      var query = new google.visualization.Query(url);

      query.send(handleQueryResponse);
    }

  function handleQueryResponse(response) {
      if (response.isError()) {
        alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
        return;
      }

      var data = response.getDataTable();
      visualization = new google.visualization.Table(document.getElementById('headings'));
      visualization.draw(data, null);
     columnList(data);
     selTest(data)
  }

  function createCell(str){
    var el=document.createElement('td');
    el.innerHTML=str;
  	return el;
  }

  function columnList(data){

  	var t=document.getElementById('colLookup');
  	removeChildrenFromNode(t);
  	var labels=document.createElement('tr');
  	var headings=document.createElement('tr');

  	for (var i=0;i<data.getNumberOfColumns();i++){
  	  var cell=createCell(data.getColumnId(i));
  	  labels.appendChild(cell);
  	  cell=createCell(data.getColumnLabel(i));
  	  headings.appendChild(cell);
  	}

  	t.appendChild(labels);
  	t.appendChild(headings);
  }

  function createSelItem(str, col){
    var el=document.createElement('option');
    el.innerHTML=str;
    el.setAttribute('value',col);
   // el.setAttribute('label',col);
  	return el;
  }

  function optionsChange(){
  	var gqo; var flag=false;
  	var sel=document.getElementById('nameTest3');
  	for (var i = 0; i < sel.options.length; i++)
    	if (sel.options[ i ].selected) {
    		if (flag){gqo+=','+sel.options[i].value;}
    		else {gqo=sel.options[i].value;flag=true;}
    	}
    document.getElementById('gqo').value=gqo;
  }

  function whereChange(){
  	var gqw='';
    var where=document.getElementById('nameTest2');
    var flag=-1;
    for (var i = 0; i < where.options.length; i++)
    	if (where.options[ i ].selected) {
    		if (flag!=-1){if (flag==0) gqw='('+gqw;gqw+=' and|or '+where.options[i].value+' **contains|matches|<|>|=[0-9|\']**';flag++}
    		else {gqw+=where.options[i].value+' **contains|matches|<|>|=[0-9|\']**';flag++; }
    }
    if (flag>0) gqw+=')';
    document.getElementById('gqw').value=gqw;
  }

  function whereSel(){
  	//iterate options in nameTest and clone selected ones into here
  	var sel=document.getElementById('nameTest');
  	var where=document.getElementById('nameTest2');
  	var order=document.getElementById('nameTest3');

  	while (where.childNodes[0]) { where.removeChild(where.childNodes[0]);}
  	while (order.childNodes[0]) { order.removeChild(order.childNodes[0]);}

	var flag=false;
	var gqc; var gqo;
  	for (var i = 0; i < sel.options.length; i++)
    	if (sel.options[ i ].selected) {
    		if (flag){gqo=gqc+=','+sel.options[i].value;}
    		else {gqo=gqc=sel.options[i].value;flag=true;}
    		where.appendChild(createSelItem(sel.options[i].innerHTML, sel.options[i].value));
    		order.appendChild(createSelItem(sel.options[i].innerHTML, sel.options[i].value));
    	}
    document.getElementById('gqc').value=gqc;
   // document.getElementById('gqo').value=gqo;
  }

  function childSelTest(id,fn,node){
    var sl=document.createElement('select');
    sl.setAttribute('id',id);//sl.setAttribute('id','nameTest2');
    sl.setAttribute('multiple','');
    sl.setAttribute('size','8');
    sl.setAttribute('onchange',fn);//sl.setAttribute('onchange','whereChange()');

    document.getElementById(node).appendChild(sl);//document.getElementById('boxes2').appendChild(sl);
  }

  function selTest(data){
    var sl=document.createElement('select');
    sl.setAttribute('id','nameTest');
    sl.setAttribute('multiple','');
    sl.setAttribute('size','8');
    sl.setAttribute('onchange','whereSel()');
    for (var i=0;i<data.getNumberOfColumns();i++){
      var txt='('+data.getColumnId(i)+') '+data.getColumnLabel(i);
      sl.appendChild(createSelItem(txt, data.getColumnId(i)));
    }
    removeChildrenFromNode(document.getElementById('boxes'));
    removeChildrenFromNode(document.getElementById('boxes2'));
    removeChildrenFromNode(document.getElementById('boxes3'));

    document.getElementById('boxes').appendChild(sl);

    childSelTest('nameTest2','whereChange()','boxes2');
    childSelTest('nameTest3','optionsChange()','boxes3');
  }

  function drawViz() {
  	  var gqc=document.getElementById('gqc').value
  	  var gq='select '+gqc;
  	  gq=encodeURIComponent(gq);

  	  var gqw=document.getElementById('gqw').value;

  	  if (gqw!='') gq+=' where '+escape(gqw);

  	  var gqo=document.getElementById('gqo').value;
  	  if (gqo!='') gq+=' order by '+encodeURIComponent(gqo)+' '+document.qform.gqad.options[document.qform.gqad.selectedIndex].value;

	  document.getElementById('gqpreview').innerHTML=gq;
  	  //gq=encodeURIComponent(gq);

  	  var url='http://spreadsheets.google.com/tq?tq='+gq+'&key='+key;

  	  var purl='http://spreadsheets.google.com/tq?tqx=out:html&tq='+gq+'&key='+key;
  	  var l=document.getElementById('htmlout');
  	  l.innerHTML="<a href='"+purl+"'>HTML preview URL</a>";
  	  purl='http://spreadsheets.google.com/tq?tqx=out:csv&tq='+gq+'&key='+key;
  	  l=document.getElementById('csvout');
  	  l.innerHTML="<a href='"+purl+"'>CSV URL</a>";


  	  var b=document.getElementById('bookmark');
  	  var bookmark="http://localhost/gspreadsheetdb4.php?run=true&gsKey="+key+"&gqc="+encodeURIComponent(gqc)+"&gqw="+escape(gqw)+"&gqo"+encodeURIComponent(gqo);
  	  b.innerHTML="<a href='"+bookmark+"'>bookmark</a>";

      var query = new google.visualization.Query(url);

      query.send(handleQueryResponse2);
    }


   function handleQueryResponse2(response) {
      if (response.isError()) {
        alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
        return;
      }

      var data = response.getDataTable();


      removeChildrenFromNode(document.getElementById('preview'));

      var typ;
      var sel=document.getElementsByName('opType');
      for (var i = 0; i < sel.length; i++)
    	if (sel[ i ].checked) typ=sel[i].value;
      switch (typ){
      	case 'table': tablechart(data);break;
      	case 'linechart': linechart(data);break;
      	case 'scatterchart': scatterchart(data);break;
      	case 'piechart': piechart(data);break;
      	case 'barchart': barchart(data);break;
      	case 'columnchart': columnchart(data);break;
      	default:;
      }

      //visualization = new google.visualization.Table(document.getElementById('preview'));
      //visualization.draw(data, null);

      //linechart(data);
      //piechart(data);
      //scatterchart(data);
  }

  function tablechart(data){
     new google.visualization.Table(document.getElementById('preview')).draw(data, null);
  }

  function scatterchart(data){
  	new google.visualization.ScatterChart(document.getElementById('preview')).draw(data, {width: 800, height: 400});
  }

  function linechart(data){
  	new google.visualization.LineChart(document.getElementById('preview')).draw(data, {width: 800, height: 400});
  }

  function piechart(data){
  	new google.visualization.PieChart(document.getElementById('preview')).draw(data, {width: 800, height: 400});
  }

  function barchart(data){
  	new google.visualization.BarChart(document.getElementById('preview')).draw(data, {width: 800, height: 400});
  }

  function columnchart(data){
  	new google.visualization.ColumnChart(document.getElementById('preview')).draw(data, {
  		width: 800,
  		height: 400
  	});
  }
