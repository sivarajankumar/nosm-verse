
var maxDataSets = 4;
var nDataSetContainers = 3;
var nPieLabelsColorsContainers = 3;
var nPieLabelsColors = 9;
var nVennColors = 3;
var defaultMarkerSize = 1;
var maxArea = 300000;

// Chart kinds.
var kindLine = 'line';
var kindBar = 'bar';
var kindPie = 'pie';
var kindVenn = 'venn';
var kindScatter = 'scatter';

// Chart types.
var typeLine = 'lc';
var typeLineXY = 'lxy';
var typeVerticalBar = 'bvs';
var typeVerticalBarGroups = 'bvg';
var typeHorizontalBar = 'bhs';
var typeHorizontalBarGroups = 'bhg';
var typePie = 'p';
var typePie3d = 'p3';
var typeVenn = 'v';
var typeScatter = 's';

// Setting variables.
var title;
var kind;
var width;
var height;
var backgroundColor;
var chartColor;
var grid;
var marks;
var barDir;
var barType;
var pieType;
var dataLegends = [];
var dataColors = [];
var data = [];
var pieLabels = [];
var pieColors = [];
var vennColors = [];

var titleElement = document.getElementById( 'title' );
var kindElement = document.getElementById( 'kind' );

var barOptionsContainerElement = document.getElementById( 'barOptionsContainer' );
var barDirElement = document.getElementById( 'barDir' );
var barTypeElement = document.getElementById( 'barType' );

var pieOptionsContainerElement = document.getElementById( 'pieOptionsContainer' );
var pieTypeElement = document.getElementById( 'pieType' );

var widthElement = document.getElementById( 'width' );
var heightElement = document.getElementById( 'height' );

var backgroundColorElement = document.getElementById( 'backgroundColor' );
var chartColorElement = document.getElementById( 'chartColor' );

var gridContainerElement = document.getElementById( 'gridContainer' );
var gridElement = document.getElementById( 'grid' );
var marksContainerElement = document.getElementById( 'marksContainer' );
var marksElement = document.getElementById( 'marks' );

var dataContainerElements = [];
var dataLegendElements = [];
var dataColorElements = [];
var dataElements = [];

for ( var d = 1; d <= maxDataSets; ++d )
    {
    dataContainerElements[d] = [];
    for ( var c = 1; c <= nDataSetContainers; ++c )
	    dataContainerElements[d][c] = document.getElementById( 'data' + d + 'Container' + c );
    dataLegendElements[d] = document.getElementById( 'data' + d + 'Legend' );
    dataColorElements[d] = document.getElementById( 'data' + d + 'Color' );
    dataElements[d] = document.getElementById( 'data' + d );
    }

var pieLabelsColorsContainerElements = [];
var pieLabelElements = [];
var pieColorElements = [];
for ( var c = 1; c <= nPieLabelsColorsContainers; ++c )
    pieLabelsColorsContainerElements[c] = document.getElementById( 'pieLabelsColorsContainer' + c );
for ( var p = 1; p <= nPieLabelsColors; ++p )
    {
    pieLabelElements[p] = document.getElementById( 'pieLabel' + p );
    pieColorElements[p] = document.getElementById( 'pieColor' + p );
    }

var vennColorsContainerElement = document.getElementById( 'vennColorsContainer' );
var vennColorElements = [];
for ( var v = 1; v <= nVennColors; ++v )
    vennColorElements[v] = document.getElementById( 'vennColor' + v );

var chartContainerElement = document.getElementById( 'chartContainer' );
var chartElement = document.getElementById( 'chart' );
var resizeElement = document.getElementById( 'resize' );

var urlElement = document.getElementById( 'url' );
var tinyUrlElement = document.getElementById( 'tinyurl' );
//var noteElement = document.getElementById( 'note' );

function chartInit( )
    {
    
    
        maxDataSets = 4;
        nDataSetContainers = 3;
        nPieLabelsColorsContainers = 3;
        nPieLabelsColors = 9;
        nVennColors = 3;
        defaultMarkerSize = 1;
        maxArea = 300000;

        // Chart kinds.
        kindLine = 'line';
        kindBar = 'bar';
        kindPie = 'pie';
        kindVenn = 'venn';
        kindScatter = 'scatter';

        // Chart types.
        typeLine = 'lc';
        typeLineXY = 'lxy';
        typeVerticalBar = 'bvs';
        typeVerticalBarGroups = 'bvg';
        typeHorizontalBar = 'bhs';
        typeHorizontalBarGroups = 'bhg';
        typePie = 'p';
        typePie3d = 'p3';
        typeVenn = 'v';
        typeScatter = 's';

        // Setting variables.
        title;
        kind;
        width;
        height;
        backgroundColor;
        chartColor;
        grid;
        marks;
        barDir;
        barType;
        pieType;
        dataLegends = [];
        dataColors = [];
        data = [];
        pieLabels = [];
        pieColors = [];
        vennColors = [];
    
    
        titleElement = document.getElementById( 'title' );
        kindElement = document.getElementById( 'kind' );

        barOptionsContainerElement = document.getElementById( 'barOptionsContainer' );
        barDirElement = document.getElementById( 'barDir' );
        barTypeElement = document.getElementById( 'barType' );

        pieOptionsContainerElement = document.getElementById( 'pieOptionsContainer' );
        pieTypeElement = document.getElementById( 'pieType' );

        widthElement = document.getElementById( 'width' );
        heightElement = document.getElementById( 'height' );

        backgroundColorElement = document.getElementById( 'backgroundColor' );
        chartColorElement = document.getElementById( 'chartColor' );

        gridContainerElement = document.getElementById( 'gridContainer' );
        gridElement = document.getElementById( 'grid' );
        marksContainerElement = document.getElementById( 'marksContainer' );
        marksElement = document.getElementById( 'marks' );

        dataContainerElements = [];
        dataLegendElements = [];
        dataColorElements = [];
        dataElements = [];

                for ( var d = 1; d <= maxDataSets; ++d )
            {
            dataContainerElements[d] = [];
            for ( var c = 1; c <= nDataSetContainers; ++c )
	        dataContainerElements[d][c] = document.getElementById( 'data' + d + 'Container' + c );
            dataLegendElements[d] = document.getElementById( 'data' + d + 'Legend' );
            dataColorElements[d] = document.getElementById( 'data' + d + 'Color' );
            dataElements[d] = document.getElementById( 'data' + d );
            }

        pieLabelsColorsContainerElements = [];
        pieLabelElements = [];
        pieColorElements = [];
        for ( var c = 1; c <= nPieLabelsColorsContainers; ++c )
            pieLabelsColorsContainerElements[c] = document.getElementById( 'pieLabelsColorsContainer' + c );
        for ( var p = 1; p <= nPieLabelsColors; ++p )
            {
            pieLabelElements[p] = document.getElementById( 'pieLabel' + p );
            pieColorElements[p] = document.getElementById( 'pieColor' + p );
            }

        vennColorsContainerElement = document.getElementById( 'vennColorsContainer' );
        vennColorElements = [];
        for ( var v = 1; v <= nVennColors; ++v )
            vennColorElements[v] = document.getElementById( 'vennColor' + v );

        chartContainerElement = document.getElementById( 'chartContainer' );
        chartElement = document.getElementById( 'chart' );
        resizeElement = document.getElementById( 'resize' );

        urlElement = document.getElementById( 'url' );
        tinyUrlElement = document.getElementById( 'tinyurl' );
 }


function chartSetup( )
    {
    acme.Initialize();

    chartInit();
    
    // Get the default values.
    GetDefaults();

    // Get the cookie values.
    GetVars();
    titleElement.value = title;
    kindElement.value = kind;
    widthElement.value = width;
    heightElement.value = height;
    gridElement.value = grid;
    marksElement.checked = marks;
    barDirElement.checked = barDir;
    barTypeElement.checked = barType;
    pieTypeElement.checked = pieType;
    for ( var d = 1; d <= maxDataSets; ++d )
	{
	dataLegendElements[d].value = dataLegends[d];
	dataElements[d].value = data[d];
	}
    for ( var p = 1; p <= nPieLabelsColors; ++p )
	pieLabelElements[p].value = pieLabels[p];

    // Add event handlers.
    titleElement.onkeypress = LazyUpdate;
    //titleElement.onpaste = LazyUpdate;
    titleElement.onchange = Update;
    kindElement.onchange = Update;
    widthElement.onkeypress = NumericFilter;
    //widthElement.onpaste = NumericFilter;
    widthElement.onchange = Update;
    heightElement.onkeypress = NumericFilter;
    //heightElement.onpaste = NumericFilter;
    heightElement.onchange = Update;
    backgroundColorElement.onclick = function () { PopUpColorPicker( backgroundColorElement, { left: '0px', border: '5px ridge #000000' }, function ( c ) { backgroundColor = c; Update(); } ); };
    chartColorElement.onclick = function () { PopUpColorPicker( chartColorElement, { left: '0px', border: '5px ridge #000000' }, function ( c ) { chartColor = c; Update(); } ); };
    gridElement.onchange = Update;
    marksElement.onchange = Update;
    barDirElement.onchange = Update;
    barTypeElement.onchange = Update;
    pieTypeElement.onchange = Update;
    for ( var d = 1; d <= maxDataSets; ++d )
	{
	dataLegendElements[d].onkeypress = LazyUpdate;
	//dataLegendElements[d].onpaste = LazyUpdate;
	dataLegendElements[d].onchange = Update;
	AddDataColorElementOnClick( d );
	dataElements[d].onkeypress = LazyUpdate;
	//dataElements[d].onpaste = LazyUpdate;
	dataElements[d].onchange = Update;
	}
    for ( var p = 1; p <= nPieLabelsColors; ++p )
	{
	pieLabelElements[p].onkeypress = LazyUpdate;
	//pieLabelElements[p].onpaste = LazyUpdate;
	pieLabelElements[p].onchange = Update;
	AddPieColorElementOnClick( p );
	}
    for ( var v = 1; v <= nVennColors; ++v )
	AddVennColorElementOnClick( v );

    // And display the chart - a little later.
    LazyUpdate();
    }

function AddDataColorElementOnClick( d )
    {
    dataColorElements[d].onclick = function () { PopUpColorPicker( dataColorElements[d], { left: '0px', border: '5px ridge #000000' }, function ( c ) { dataColors[d] = c; Update(); } ); };
    }

function AddPieColorElementOnClick( p )
    {
    pieColorElements[p].onclick = function () { PopUpColorPicker( pieColorElements[p], { left: '0px', border: '5px ridge #000000' }, function ( c ) { pieColors[p] = c; Update(); } ); };
    }

function AddVennColorElementOnClick( v )
    {
    vennColorElements[v].onclick = function () { PopUpColorPicker( vennColorElements[v], { left: '0px', border: '5px ridge #000000' }, function ( c ) { vennColors[v] = c; Update(); } ); };
    }


function Display()
    {
    var nDataSets;
    var type;
    var n;

    // Set up for each kind of chart.
    if ( kind == kindLine )
	{
	//noteElement.innerHTML = 'In a line chart, each data set is graphed as a separate line.  The data can be either x,y coordinate pairs, or just a sequence of y coordinates.';
	nDataSets = maxDataSets;
	barOptionsContainerElement.style.display = 'none';
	pieOptionsContainerElement.style.display = 'none';
	gridContainerElement.style.display = '';
	marksContainerElement.style.display = '';
	dataLegendElements[1].style.display = '';
	dataColorElements[1].style.display = '';
	//if ( AnyCommasAll( nDataSets ) )
	//    type = typeLineXY;
	//else
	//    type = typeLine;
	type = typeLineXY;	// use LineXY due to Line's x-axis auto-scaling
	}
    else if ( kind == kindBar )
	{
	//noteElement.innerHTML = 'In bar charts, each data set determines the length of a set of bars.  The bars can be vertical or horizontal, and either stacked or grouped.';
	nDataSets = maxDataSets;
	barOptionsContainerElement.style.display = '';
	pieOptionsContainerElement.style.display = 'none';
	gridContainerElement.style.display = '';
	marksContainerElement.style.display = 'none';
	dataLegendElements[1].style.display = '';
	dataColorElements[1].style.display = '';
	if ( barDir == 'vertical' )
	    if ( barType == 'stacked' )
		type = typeVerticalBar;
	    else
		type = typeVerticalBarGroups;
	else
	    if ( barType == 'stacked' )
		type = typeHorizontalBar;
	    else
		type = typeHorizontalBarGroups;
	}
    else if ( kind == kindPie )
	{
	//noteElement.innerHTML = 'Pie charts only show one data set, where each number determines the size of a slice of the pie.  They can be either flat or 3-D.';
	nDataSets = 1;
	barOptionsContainerElement.style.display = 'none';
	pieOptionsContainerElement.style.display = '';
	gridContainerElement.style.display = 'none';
	marksContainerElement.style.display = 'none';
	dataLegendElements[1].style.display = 'none';
	dataColorElements[1].style.display = 'none';
	if ( pieType == 'flat' )
	    type = typePie;
	else
	    type = typePie3d;
	}
    else if ( kind == kindVenn )
	{
	//noteElement.innerHTML = 'A Venn diagram is formed from a single data set with at most seven values.  The first three values specify the relative sizes of three circles, A, B, and C.  The fourth value specifies the area of A intersecting B.  The fifth value specifies the area of A intersecting C.  The sixth value specifies the area of B intersecting C.  The seventh value specifies the area of A intersecting B intersecting C.';
	nDataSets = 1;
	barOptionsContainerElement.style.display = 'none';
	pieOptionsContainerElement.style.display = 'none';
	gridContainerElement.style.display = 'none';
	marksContainerElement.style.display = 'none';
	dataLegendElements[1].style.display = '';
	dataColorElements[1].style.display = 'none';
	type = typeVenn;
	}
    else if ( kind == kindScatter )
	{
	//noteElement.innerHTML = 'In a scatter plot, a single data set of x,y coordinate pairs is displayed as separate markers.  An optional third value can be given to specify the size of each marker - x,y,s.';
	nDataSets = 1;
	barOptionsContainerElement.style.display = 'none';
	pieOptionsContainerElement.style.display = 'none';
	gridContainerElement.style.display = '';
	marksContainerElement.style.display = 'none';
	dataLegendElements[1].style.display = '';
	dataColorElements[1].style.display = '';
	type = typeScatter;
	}
    else
	{
	//noteElement.innerHTML = 'Unknown graph type!';
	return;
	}

    // Twiddle data set visibility according to the variable set above.
    for ( var d = 1; d <= nDataSets; ++d )
	for ( var c = 1; c <= nDataSetContainers; ++c )
	    dataContainerElements[d][c].style.display = '';
    for ( var d = nDataSets + 1; d <= maxDataSets; ++d )
	for ( var c = 1; c <= nDataSetContainers; ++c )
	    dataContainerElements[d][c].style.display = 'none';

    // Twiddle pie settings visibility.
    for ( var c = 1; c <= nPieLabelsColorsContainers; ++c )
	pieLabelsColorsContainerElements[c].style.display = kind == kindPie ? '' : 'none';
    for ( var p = 1; p <= nPieLabelsColors; ++p )
	{
	pieLabelElements[p].style.display = kind == kindPie ? '' : 'none';
	pieColorElements[p].style.display = kind == kindPie ? '' : 'none';
	}

    // Twiddle Venn settings visibility.
    vennColorsContainerElement.style.display = kind == kindVenn ? '' : 'none';
    for ( var v = 1; v <= nVennColors; ++v )
	vennColorElements[v].style.display = kind == kindVenn ? '' : 'none';

    if ( kind == kindLine || kind == kindBar || kind == kindScatter )
	{
	// Measure the data.
	var xMeasure = null;
	var yMeasure = null;
	for ( var d = 1; d <= nDataSets; ++d )
	    if ( data[d] != '' )
		{
		var vv = ParseDataSet( data[d] );
		xMeasure = MeasureAxis( vv[0], xMeasure, ( kind != kindScatter ) );
		if ( kind == kindBar && barType == 'stacked' && d > 1 )
		    {
		    var tmpMeasure = MeasureAxis( vv[1], null, false );
		    yMeasure.minData += tmpMeasure.minData;
		    yMeasure.maxData += tmpMeasure.maxData;
		    FigureChartSize( yMeasure, false );
		    }
		else
		    yMeasure = MeasureAxis( vv[1], yMeasure, false );
		}
	var xGridInc = FigureGridInc( xMeasure );
	var yGridInc = FigureGridInc( yMeasure );
	}

    // Build the chart URL.  See: http://code.google.com/apis/chart/
    var url = 'http://chart.apis.google.com/chart?';

    // Title.   
    if ( title != '' )
	url += 'chtt=' + encodeURIComponent( title );

    // Type.
    url += '&cht=' + type;

    // Size.
    if ( width * height > maxArea )
	{
	var ratio = Math.sqrt( maxArea / ( width * height ) );
	width = Math.floor( width * ratio );
	height = Math.floor( height * ratio );
	widthElement.value = width;
	heightElement.value = height;
	}
    url += '&chs=' + width + 'x' + height;

    // Background & chart colors.
    url += '&chf=bg,s,' + backgroundColor + '|c,s,' + chartColor;

    // Data legends.
    if ( kind != kindPie )
	{
	n = 0;
	for ( var d = 1; d <= nDataSets; ++d )
	    {
	    if ( data[d] != '' )
		{
		if ( n == 0 )
		    url += '&chdl=';
		else
		    url += '|';
		url += encodeURIComponent( dataLegends[d] );
		++n;
		}
	    }
	}

    // Colors.
    if ( kind == kindPie )
	{
	// Don't specify extra colors or it will interpolate.
	var vv = ParseDataSet( data[1] );
	var nColors = Math.min( nPieLabelsColors, vv[0].length );
	for ( var p = 1; p <= nColors; ++p )
	    {
	    if ( p == 1 )
		url += '&chco=';
	    else
		url += ',';
	    url += pieColors[p];
	    }
	}
    else if ( kind == kindVenn )
	{
	for ( var v = 1; v <= nVennColors; ++v )
	    {
	    if ( v == 1 )
		url += '&chco=';
	    else
		url += ',';
	    url += vennColors[v];
	    }
	}
    else
	{
	n = 0;
	for ( var d = 1; d <= nDataSets; ++d )
	    if ( data[d] != '' )
		{
		if ( n == 0 )
		    url += '&chco=';
		else
		    url += ',';
		url += dataColors[d];
		++n;
		}
	}

    // Pie chart labels.
    if ( kind == kindPie )
	{
	n = 0;
	for ( var p = 1; p <= nPieLabelsColors; ++p )
	    {
	    if ( n == 0 )
		url += '&chl=';
	    else
		url += '|';
	    url += encodeURIComponent( pieLabels[p] );
	    ++n;
	    }
	}

    if ( kind == kindLine || kind == kindBar || kind == kindScatter )
	{
	// Axis type.
	if ( kind == kindBar && barDir == 'horizontal' )
	    url += '&chxt=y,x';
	else
	    url += '&chxt=x,y';

	// Axis ranges.
	url += '&chxr=0,' + xMeasure.minChart + ',' + xMeasure.maxChart + '|1,' + yMeasure.minChart + ',' + yMeasure.maxChart;

	// Axis label positions.  
	url += '&chxp=0';
	for ( i = xMeasure.minChart; i <= xMeasure.maxChart; i += xGridInc )
	    url += ',' + i;
	url += '|1';
	for ( i = yMeasure.minChart; i <= yMeasure.maxChart; i += yGridInc )
	    url += ',' + i;

	// Grid lines.
	var xStep, yStep;
	if ( kind == kindBar && barDir == 'horizontal')
	    {
	    if ( grid == 'vertical' || grid == 'both' )
		xStep = Math.round( yGridInc / ( yMeasure.maxChart - yMeasure.minChart ) * 100.0 );
		// (Note reversed coordinates.)
	    else
		xStep = 100;
	    yStep = 100;
	    }
	else
	    {
	    if ( ( grid == 'vertical' || grid == 'both' ) && kind != kindBar )
		xStep = Math.round( xGridInc / ( xMeasure.maxChart - xMeasure.minChart ) * 100.0 );
	    else
		xStep = 100;
	    if ( grid == 'horizontal' || grid == 'both' )
		yStep = Math.round( yGridInc / ( yMeasure.maxChart - yMeasure.minChart ) * 100.0 );
	    else
		yStep = 100;
	    }
	url += '&chg=' + xStep + ',' + yStep + ',1,0';
	}

    // Data.
    var dataSetIndex = 0;
    var marksStr = '';
    for ( var d = 1; d <= nDataSets; ++d )
	if ( data[d] != '' )
	    {
	    var vv = ParseDataSet( data[d] );
	    var scaledX, scaledY;
	    if ( kind == kindLine || kind == kindBar || kind == kindScatter )
		{
		scaledX = ScaleData( vv[0], xMeasure );
		scaledY = ScaleData( vv[1], yMeasure );
		}
	    else
		scaledY = vv[1];

	    if ( dataSetIndex == 0 )
		url += '&chd=t:';
	    else
		url += '|';
	    if ( type == typeLineXY || kind == kindScatter )
		{
		url += EncodeData( scaledX );
		url += '|';
		url += EncodeData( scaledY );
		if ( kind == kindScatter && vv.length == 3 )
		    {
		    var sMeasure = MeasureAxis( vv[2], null, true );
		    var scaledS = ScaleData( vv[2], sMeasure );
		    url += '|';
		    url += EncodeData( scaledS );
		    }
		}
	    else
		url += EncodeData( scaledY );
	    if ( kind == kindLine && marks )
		{
		for ( var i = 0; i < scaledY.length; ++i )
		    {
		    if ( marksStr != '' )
			marksStr += '|';
		    marksStr += 's,' + dataColors[d] + ',' + dataSetIndex + ',' + i + ',5';
		    }
		}
	    ++dataSetIndex;
	    }

    // Marks.
    if ( marksStr != '' )
	url += '&chm=' + marksStr;

    // Display the results.
    chartContainerElement.style.width = ( width + 16 ) + 'px';
    chartContainerElement.style.height = ( height + 16 ) + 'px';
    chartElement.style.width = width + 'px';
    chartElement.style.height = height + 'px';
    chartElement.style.backgroundColor = '#' + backgroundColor;
    chartElement.src = url;
    urlElement.value = url;
    tinyUrlElement.value = '';
    HttpGet( 'http://tinyurl.com/api-create.php?url=' + encodeURIComponent( url ), TinyUrlCallback );
    }


function TinyUrlCallback( request )
    {
    tinyUrlElement.value = request.responseText;
    }


// Returns an array of either two or three arrays of floats.
// vv[0] is xcoordinates and vv[1] is y coordinates, and
// vv[2] is the optional marker sizes for scatter plots.
function ParseDataSet( dataSet )
    {
    var vv = [ [], [], [] ];
    var gotThird = false;
    var items = dataSet.split( ' ' );
    for ( var i = 0; i < items.length; ++i )
	{
	var coords = ArrayMap( parseFloat, (items[i]+'').split( ',' ) );
	if ( coords.length == 1 )
	    {
	    vv[0][i] = i + 1;
	    vv[1][i] = coords[0];
	    vv[2][i] = defaultMarkerSize;
	    }
	else if ( coords.length == 2 )
	    {
	    vv[0][i] = coords[0];
	    vv[1][i] = coords[1];
	    vv[2][i] = defaultMarkerSize;
	    }
	else
	    {
	    vv[0][i] = coords[0];
	    vv[1][i] = coords[1];
	    vv[2][i] = coords[2];
	    gotThird = true;
	    }
	}
    if ( ! gotThird )
	vv.length = 2;
    return vv;
    }


function ScaleData( values, measure )
    {
    var scaledValues = [];
    for ( var i = 0; i < values.length; ++i )
	{
	if ( isNaN( values[i] ) )
	    scaledValues[i] = -1;
	else
	    scaledValues[i] = ( values[i] - measure.minChart ) / ( measure.maxChart - measure.minChart ) * 100.0;
	}
    return scaledValues;
    }


function EncodeData( v )
    {
    var s = '';
    for ( var i = 0; i < v.length; ++i )
	{
	if ( i != 0 )
	    s += ',';
	s += Math.round( v[i] * 10.0 ) / 10.0;
	}
    return s;
    }


function AnyCommasAll( nDataSets )
    {
    for ( var d = 1; d <= nDataSets; ++d )
	if ( AnyCommasOne( data[d] ) )
	    return true;
    return false;
    }

function AnyCommasOne( dataSet )
    {
    return dataSet.indexOf( ',' ) != -1;
    }


function MeasureAxis( values, measure, exact )
    {
    if ( measure == null )
	measure = { minData: 2000000000, maxData: -2000000000, minChart: 2000000000, maxChart: -2000000000 };

    for ( var i = 0; i < values.length; ++i )
	{
	if ( values[i] > measure.maxData )
	    measure.maxData = values[i];
	if ( values[i] < measure.minData )
	    measure.minData = values[i];
	}

    // Tweak for negative data.
    measure.maxData = Math.max( Math.abs( measure.maxData ), Math.abs( measure.minData ) );

    FigureChartSize( measure, exact );

    return measure;
    }


function FigureChartSize( measure, exact )
    {
    var thisMaxChart;
    if ( exact )
	thisMaxChart = measure.maxData;
    else
	{
	// Figure out the graph size and grid spacing.
	if ( measure.maxData < 1 )
	    thisMaxChart = 1;
	else if ( measure.maxData < 2 )
	    thisMaxChart = 2;
	else if ( measure.maxData < 5 )
	    thisMaxChart = 5;
	else if ( measure.maxData < 10 )
	    thisMaxChart = 10;
	else if ( measure.maxData < 20 )
	    thisMaxChart = 20;
	else if ( measure.maxData < 50 )
	    thisMaxChart = 50;
	else if ( measure.maxData < 100 )
	    thisMaxChart = 100;
	else if ( measure.maxData < 200 )
	    thisMaxChart = 200;
	else if ( measure.maxData < 500 )
	    thisMaxChart = 500;
	else if ( measure.maxData < 1000 )
	    thisMaxChart = 1000;
	else if ( measure.maxData < 2000 )
	    thisMaxChart = 2000;
	else if ( measure.maxData < 5000 )
	    thisMaxChart = 5000;
	else if ( measure.maxData < 10000 )
	    thisMaxChart = 10000;
	else if ( measure.maxData < 20000 )
	    thisMaxChart = 20000;
	else if ( measure.maxData < 50000 )
	    thisMaxChart = 50000;
	else if ( measure.maxData < 100000 )
	    thisMaxChart = 100000;
	else if ( measure.maxData < 200000 )
	    thisMaxChart = 200000;
	else if ( measure.maxData < 500000 )
	    thisMaxChart = 500000;
	else if ( measure.maxData < 1000000 )
	    thisMaxChart = 1000000;
	else if ( measure.maxData < 2000000 )
	    thisMaxChart = 2000000;
	else if ( measure.maxData < 5000000 )
	    thisMaxChart = 5000000;
	else if ( measure.maxData < 10000000 )
	    thisMaxChart = 10000000;
	else if ( measure.maxData < 20000000 )
	    thisMaxChart = 20000000;
	else if ( measure.maxData < 50000000 )
	    thisMaxChart = 50000000;
	else if ( measure.maxData < 100000000 )
	    thisMaxChart = 100000000;
	else if ( measure.maxData < 200000000 )
	    thisMaxChart = 200000000;
	else if ( measure.maxData < 500000000 )
	    thisMaxChart = 500000000;
	else if ( measure.maxData < 1000000000 )
	    thisMaxChart = 1000000000;
	else
	    thisMaxChart = 2000000000;
	}
    measure.maxChart = Math.max( measure.maxChart, thisMaxChart );

    if ( measure.minData < 0 )
	measure.minChart = -measure.maxChart;
    else
	if ( exact )
	    measure.minChart = Math.min( measure.minChart, measure.minData );
	else
	    measure.minChart = 0;
    }


function FigureGridInc( measure )
    {
    var chartSpan = measure.maxChart - measure.minChart;
    if ( chartSpan <= 1 )
	return 0.2;
    if ( chartSpan <= 2 )
	return 0.5;
    if ( chartSpan <= 5 )
	return 1;
    if ( chartSpan <= 10 )
	return 2;
    if ( chartSpan <= 20 )
	return 5;
    if ( chartSpan <= 50 )
	return 10;
    if ( chartSpan <= 100 )
	return 20;
    if ( chartSpan <= 200 )
	return 50;
    if ( chartSpan <= 500 )
	return 100;
    if ( chartSpan <= 1000 )
	return 200;
    if ( chartSpan <= 2000 )
	return 500;
    if ( chartSpan <= 5000 )
	return 1000;
    if ( chartSpan <= 10000 )
	return 2000;
    if ( chartSpan <= 20000 )
	return 5000;
    if ( chartSpan <= 50000 )
	return 10000;
    if ( chartSpan <= 100000 )
	return 20000;
    if ( chartSpan <= 200000 )
	return 50000;
    if ( chartSpan <= 500000 )
	return 100000;
    if ( chartSpan <= 1000000 )
	return 200000;
    if ( chartSpan <= 2000000 )
	return 500000;
    if ( chartSpan <= 5000000 )
	return 1000000;
    if ( chartSpan <= 10000000 )
	return 2000000;
    if ( chartSpan <= 20000000 )
	return 5000000;
    if ( chartSpan <= 50000000 )
	return 10000000;
    if ( chartSpan <= 100000000 )
	return 20000000;
    if ( chartSpan <= 200000000 )
	return 50000000;
    if ( chartSpan <= 500000000 )
	return 100000000;
    if ( chartSpan <= 1000000000 )
	return 200000000;
    return 500000000;
    }


var dragPrevX, dragPrevY;

function DragStart()
    {
    dragPrevX = null;
    dragPrevY = null;
    document.body.style.cursor = 'se-resize';
    document.body.onmousemove = Drag;
    document.body.onmouseup = DragStop;
    }

function DragStop()
    {
    document.body.style.cursor = '';
    document.body.onmousemove = null;
    document.body.onmouseup = null;
    SaveVars();
    Display();
    }

function Drag( e )
    {
    e = GetEvent( e );
    var x = e.clientX;
    var y = e.clientY;
    if ( dragPrevX != null && dragPrevY != null )
	{
	var dx = x - dragPrevX;
	var dy = y - dragPrevY;
	width += dx;
	height += dy;
	widthElement.value = width;
	heightElement.value = height;
	chartContainerElement.style.width = ( width + 16 ) + 'px';
	chartContainerElement.style.height = ( height + 16 ) + 'px';
	chartElement.style.width = width + 'px';
	chartElement.style.height = height + 'px';
	}
    dragPrevX = x;
    dragPrevY = y;
    }


function GetDefaults()
    {
    title = 'Sample';
    kind = kindLine;
    width = 400;
    height = 300;
    backgroundColor = 'ffffff';
    chartColor = 'ffffcc';
    grid = 'both';
    marks = true;
    barDir = 'vertical';
    barType = 'stacked';
    pieType = 'flat';
    dataLegends[1] = 'x^2';
    dataColors[1] = 'ff0000';
    data[1] = '0,0 1,1 2,4 3,9 4,16 5,25 6,36 7,49'
    dataLegends[2] = '2^x';
    dataColors[2] = '00ff00';
    data[2] = '0,1 1,2 2,4 3,8 4,16 5,32 6,64 7,128'
    dataLegends[3] = '';
    dataColors[3] = '0000ff';
    data[3] = ''
    dataLegends[4] = '';
    dataColors[4] = 'ff00ff';
    data[4] = ''
    pieColors[1] = 'ff0000';
    pieLabels[1] = 'One';
    pieColors[2] = '00ff00';
    pieLabels[2] = 'Two';
    pieColors[3] = '0000ff';
    pieLabels[3] = 'Three';
    pieColors[4] = 'ffff00';
    pieLabels[4] = 'Four';
    pieColors[5] = 'ff00ff';
    pieLabels[5] = 'Five';
    pieColors[6] = '00ffff';
    pieLabels[6] = 'Six';
    pieColors[7] = 'ff9900';
    pieLabels[7] = 'Seven';
    pieColors[8] = 'ff9999';
    pieLabels[8] = 'Eight';
    pieColors[9] = '999999';
    pieLabels[9] = 'Nine';
    vennColors[1] = 'ff0000';
    vennColors[2] = '00ff00';
    vennColors[3] = '0000ff';
    }


var kcBS = 8;
var kc0 = 48;
var kc9 = 57;

function NumericFilter( e )
    {
    e = GetEvent( e );
    var kc = e.keyCode || e.which;
    if ( kc == kcBS ||
         kc >= kc0 && kc <= kc9 )
	{ LazyUpdate(); return true; }
    return false;
    }


var timeout = null;

function LazyUpdate()
    {
    if ( timeout != null )
	clearTimeout( timeout );
    timeout = setTimeout( "LazyUpdateLater();", 500 );
    }

function LazyUpdateLater()
    {
    timeout = null;
    Update();
    }


function Update()
    {
    title = titleElement.value;
    kind = kindElement.value;
    width = parseInt( widthElement.value );
    height = parseInt( heightElement.value );
    backgroundColorElement.style.backgroundColor = '#' + backgroundColor;
    chartColorElement.style.backgroundColor = '#' + chartColor;
    grid = gridElement.value;
    marks = marksElement.checked;
    barDir = barDirElement.value;
    barType = barTypeElement.value;
    pieType = pieTypeElement.value;
    for ( var d = 1; d <= maxDataSets; ++d )
	{
	dataLegends[d] = dataLegendElements[d].value;
	dataColorElements[d].style.backgroundColor = '#' + dataColors[d];
	data[d] = dataElements[d].value;
	}
    for ( var p = 1; p <= nPieLabelsColors; ++p )
	{
	pieColorElements[p].style.backgroundColor = '#' + pieColors[p];
	pieLabels[p] = pieLabelElements[p].value;
	}
    for ( var v = 1; v <= nVennColors; ++v )
	vennColorElements[v].style.backgroundColor = '#' + vennColors[v];
    SaveVars();
    Display();
    }


var cookieName = 'Chartmaker';

function GetVars()
    {
    var cookie = GetCookie( cookieName );
    if ( cookie )
	{
	var cookieParts = cookie.split( '|' );
	for ( var i = 0; i < cookieParts.length; ++i )
	    {
	    var nameval = cookieParts[i].split( '=' );
	    if ( nameval.length == 2 )
		{
		var name = nameval[0];
		var val = nameval[1];
		if ( name == 'title' )
		    title = val;
		else if ( name == 'kind' )
		    kind = val;
		else if ( name == 'width' )
		    width = parseInt( val );
		else if ( name == 'height' )
		    height = parseInt( val );
		else if ( name == 'backgroundColor' )
		    backgroundColor = val;
		else if ( name == 'chartColor' )
		    chartColor = val;
		else if ( name == 'grid' )
		    grid = val;
		else if ( name == 'marks' )
		    marks = ( val == 'true' );
		else if ( name == 'barDir' )
		    barDir = val;
		else if ( name == 'barType' )
		    barType = val;
		else if ( name == 'pieType' )
		    pieType = val;
		else
		    {
		    for ( var d = 1; d <= maxDataSets; ++d )
			{
			if ( name == 'data' + d + 'Legend' )
			    dataLegends[d] = val;
			else if ( name == 'data' + d + 'Color' )
			    dataColors[d] = val;
			else if ( name == 'data' + d )
			    data[d] = val;
			}
		    for ( var p = 1; p <= nPieLabelsColors; ++p )
			{
			if ( name == 'pieLabel' + p )
			    pieLabels[p] = val;
			else if ( name == 'pieColor' + p )
			    pieColors[p] = val;
			}
		    for ( var v = 1; v <= nVennColors; ++v )
			{
			if ( name == 'vennColor' + v )
			    vennColors[v] = val;
			}
		    }
		}
	    }
	}
    }

function SaveVars()
    {
    var cookieValue =
      'title=' + title +
      '|kind=' + kind +
      '|width=' + width +
      '|height=' + height +
      '|backgroundColor=' + backgroundColor +
      '|chartColor=' + chartColor +
      '|grid=' + grid +
      '|marks=' + marks +
      '|barDir=' + barDir +
      '|barType=' + barType +
      '|pieType=' + pieType;
    for ( var d = 1; d <= maxDataSets; ++d )
	cookieValue +=
	  '|data' + d + 'Legend=' + dataLegends[d] +
	  '|data' + d + 'Color=' + dataColors[d] +
	  '|data' + d + '=' + data[d];
    for ( var p = 1; p <= nPieLabelsColors; ++p )
	cookieValue +=
	  '|pieLabel' + p + '=' + pieLabels[p] +
	  '|pieColor' + p + '=' + pieColors[p];
    for ( var v = 1; v <= nVennColors; ++v )
	cookieValue +=
	  '|vennColor' + v + '=' + vennColors[v];
    SaveCookie( cookieName, cookieValue );
    }
