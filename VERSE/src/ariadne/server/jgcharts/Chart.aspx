<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Chart.aspx.cs" Inherits="Chart" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>jQuery Google Chart Demo</title>
    
    <script src="include/gui/lib/jquery-1.2.6.min.js" type="text/javascript"></script>
	<script src="include/gui/lib/jquery.metadata.js" type="text/javascript"></script>
	<script src="include/gui/lib/jquery-ui-personalized-1.6rc2.min.js" type="text/javascript"></script>
    <script src="include/gui/lib/farbtastic/farbtastic.js" type="text/javascript"></script>
	<link href="include/gui/lib/farbtastic/farbtastic.css" type="text/css" rel="stylesheet" />
		
	<script src="jgcharts.js" type="text/javascript"></script>
	<script src="plugins/jgtable/jgtable.js" type="text/javascript"></script>
	<script src="include/gui/plugins/jggui/jggui.js" type="text/javascript"></script>
	<link href="include/gui/plugins/jggui/jggui.css" type="text/css" rel="stylesheet" />
	<link href="include/gui/plugins/jggui/theme/jquery-ui-themeroller.css" type="text/css" rel="stylesheet" />
    
    
    
    
    <script type="text/javascript">
        $(document).ready(function()
        {
            var api = new jGCharts.Api();
            var opt = 
            { 
                data : eval($("[id$='hidChartData']").val()),   
                axis_labels : ['01','02','03','04','05','06','07','08','09','10','11','12'],
                legend : ['Incshfcghsdcdsjcgjome', 'Expevsbccbbsmnse'],
                size : '600x335',
                bar_width : 15
            };
            jQuery('<img>').attr('src', api.make(opt)).appendTo("#bar_chart"); 
        });
    </script>
    
    
<script type="text/javascript">
		var STATIC = 0;
		jQuery(document).bind("jggui", function(){
			var _callback = function(){
				STATIC++;
				jQuery(".jgchart").remove();
				jQuery(".jgtable")
				.jgtable({
					single : STATIC,
					gui    : true
				});
			};
			_callback();
			
			jQuery("img.jggui").jggui({
				url : "include/gui/plugins/jggui/",
				height : 400,
				width : 600,
				callback : _callback
			});
		});
		jQuery(document).ready(function(){
			jQuery(document).trigger("jggui");
		});
	</script>
    
    
        <style type="text/css">
      .colorPicker { width: 16px; height: 16px; border: 2px #cccccc inset; }
    </style>
</head>
<body onload="Setup();">



    <table border="0">
      <tbody>

        <tr>

          <td valign="top">
            <form action="null" onsubmit="return false;">
              <table border="0">
                <tbody>

                  <tr>
                    <td colspan="3">
                      Title:
		      <input value="Sample" id="title" title="chart's title text" size="33" type="text">
                    </td>
                  </tr>

                  <tr>
                    <td>
                      Chart&nbsp;type:
		    </td>
                    <td colspan="2" align="right">
                      <select id="kind" title="what kind of chart">
                        <option value="line">Line Graph</option>
                        <option selected="selected" value="bar">Bar Chart</option>
                        <option value="pie">Pie Chart</option>
                        <option value="venn">Venn Diagram</option>
                        <option value="scatter">Scatter Plot</option>
                      </select>
                    </td>
                  </tr>

		  <tr id="barOptionsContainer" style="">
		    <td colspan="3">
		      <select id="barDir" title="whether the bars are vertical or horizontal">
			<option selected="selected" value="vertical">Vertical</option>
			<option value="horizontal">Horizontal</option>
		      </select>
		      <select id="barType" title="whether the bars are stacked or side-by-side">
			<option selected="selected" value="stacked">Stacked</option>
			<option value="groups">Groups</option>
		      </select>
		    </td>
		  </tr>

		  <tr id="pieOptionsContainer" style="display: none;">
		    <td colspan="3">
		      <select id="pieType" title="flat pie or 3-D pie">
			<option selected="selected" value="flat">Flat</option>
			<option value="3d">3-D</option>
		      </select>
		    </td>
		  </tr>

                  <tr>
                    <td>
                      Width:
		      <input value="400" id="width" title="width of the chart" size="3" type="text">
                    </td>
                    <td>
                    </td>
                    <td align="right">
                      Height:
		      <input value="300" id="height" title="height of the chart" size="3" type="text">
                    </td>
                  </tr>

                  <tr>
                    <td colspan="2">
                      Background&nbsp;color:
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(255, 255, 255);" id="backgroundColor" title="background color" class="colorPicker"></div>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="2">
                      Chart&nbsp;color:
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(255, 255, 204);" id="chartColor" title="chart color" class="colorPicker"></div>
                    </td>
                  </tr>

                  <tr>
                    <td colspan="3">
                      <span id="gridContainer" style="">
		        Grid:
			<select id="grid" title="type of grid lines">
			  <option value="none">&nbsp;</option>
			  <option value="horizontal">-</option>
			  <option value="vertical">|</option>
			  <option selected="selected" value="both">+</option>
			</select>
                      </span>
                      <span id="marksContainer" title="mark data points" style="display: none;">
		        <input checked="checked" id="marks" type="checkbox">Marks
                      </span>
		    </td>
                  </tr>

                  <tr id="data1Container1">
		    <td></td>
                  </tr>

                  <tr id="data1Container2">
                    <td>
                      Data&nbsp;set&nbsp;1:
                    </td>
                    <td>
                      <input value="x^2" id="data1Legend" title="data set 1 legend" size="9" type="text">
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(255, 0, 0);" id="data1Color" title="data set 1 color" class="colorPicker"></div>
		      <table id="vennColorsContainer" style="display: none;" border="0" cellpadding="0" cellspacing="2">
		        <tbody>
			  <tr>
			    <td>
			      <div style="background-color: rgb(255, 0, 0); display: none;" id="vennColor1" title="Venn diagram color 1" class="colorPicker"></div>
			    </td>
			    <td>
			      <div style="background-color: rgb(0, 255, 0); display: none;" id="vennColor2" title="Venn diagram color 2" class="colorPicker"></div>
			    </td>
			    <td>
			      <div style="background-color: rgb(0, 0, 255); display: none;" id="vennColor3" title="Venn diagram color 3" class="colorPicker"></div>
			    </td>
			  </tr>
		        </tbody>
		      </table>
                    </td>
                  </tr>
                  <tr id="data1Container3">
                    <td colspan="3">
                      <input value="0,0 1,1 2,4 3,9 4,16 5,25 6,36 7,49" id="data1" title="data set 1" size="39" type="text">
                    </td>
                  </tr>

                  <tr id="data2Container1">
		    <td></td>
                  </tr>

                  <tr id="data2Container2">
                    <td>
                      Data&nbsp;set&nbsp;2:
                    </td>
                    <td>
                      <input value="2^x" id="data2Legend" title="data set 2 legend" size="9" type="text">
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(0, 255, 0);" id="data2Color" title="data set 2 color" class="colorPicker"></div>
                    </td>
                  </tr>
                  <tr id="data2Container3">
                    <td colspan="3">
                      <input value="0,1 1,2 2,4 3,8 4,16 5,32 6,64 7,128" id="data2" title="data set 2" size="39" type="text">
                    </td>
                  </tr>

                  <tr id="data3Container1">
		    <td></td>
                  </tr>

                  <tr id="data3Container2">
                    <td>
                      Data&nbsp;set&nbsp;3:
                    </td>
                    <td>
                      <input id="data3Legend" title="data set 3 legend" size="9" type="text">
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(0, 0, 255);" id="data3Color" title="data set 3 color" class="colorPicker"></div>
                    </td>
                  </tr>
                  <tr id="data3Container3">
                    <td colspan="3">
                      <input id="data3" title="data set 3" size="39" type="text">
                    </td>
                  </tr>

                  <tr id="data4Container1">
		    <td></td>
                  </tr>

                  <tr id="data4Container2">
                    <td>
                      Data&nbsp;set&nbsp;4:
                    </td>
                    <td>
                      <input id="data4Legend" title="data set 4 legend" size="9" type="text">
                    </td>
                    <td align="right">
		      <div style="background-color: rgb(255, 0, 255);" id="data4Color" title="data set 4 color" class="colorPicker"></div>
                    </td>
                  </tr>
                  <tr id="data4Container3">
                    <td colspan="3">
                      <input id="data4" title="data set 4" size="39" type="text">
                    </td>
                  </tr>

		  <tr id="pieLabelsColorsContainer1" style="display: none;">
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
                              <input value="One" style="display: none;" id="pieLabel1" title="pie chart label 1" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(255, 0, 0); display: none;" id="pieColor1" title="pie chart color 1" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Two" style="display: none;" id="pieLabel2" title="pie chart label 2" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(0, 255, 0); display: none;" id="pieColor2" title="pie chart color 2" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Three" style="display: none;" id="pieLabel3" title="pie chart label 3" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(0, 0, 255); display: none;" id="pieColor3" title="pie chart color 3" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		  </tr>

		  <tr id="pieLabelsColorsContainer2" style="display: none;">
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
                              <input value="Four" style="display: none;" id="pieLabel4" title="pie chart label 4" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(255, 255, 0); display: none;" id="pieColor4" title="pie chart color 4" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Five" style="display: none;" id="pieLabel5" title="pie chart label 5" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(255, 0, 255); display: none;" id="pieColor5" title="pie chart color 5" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Six" style="display: none;" id="pieLabel6" title="pie chart label 6" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(0, 255, 255); display: none;" id="pieColor6" title="pie chart color 6" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		  </tr>

		  <tr id="pieLabelsColorsContainer3" style="display: none;">
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
                              <input value="Seven" style="display: none;" id="pieLabel7" title="pie chart label 7" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(255, 153, 0); display: none;" id="pieColor7" title="pie chart color 7" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Eight" style="display: none;" id="pieLabel8" title="pie chart label 8" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(255, 153, 153); display: none;" id="pieColor8" title="pie chart color 8" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		    <td width="33%">
		      <table border="0" cellpadding="0" cellspacing="2">
			<tbody>
			  <tr>
			    <td>
			      <input value="Nine" style="display: none;" id="pieLabel9" title="pie chart label 9" size="7" type="text">
			    </td>
			    <td>
			      <div style="background-color: rgb(153, 153, 153); display: none;" id="pieColor9" title="pie chart color 9" class="colorPicker"></div>
			    </td>
			  </tr>
			</tbody>
		      </table>
		    </td>
		  </tr>

                </tbody>
              </table>
            </form>
          </td>

          <td align="left" valign="top">
            <table border="0">
              <tbody>

                <tr>
                  <td>
                    <div id="chartContainer" style="position: relative; width: 416px; height: 316px;">
                      <img id="chart" title="chart" style="border: 3px outset rgb(204, 204, 153); padding: 5px; width: 400px; height: 300px; background-color: rgb(255, 255, 255);" src="GoogleChartGenerator2_files/chart.png" alt="chart">
		      <div id="resize" title="resize chart by dragging" style="position: absolute; right: 0px; bottom: 0px; width: 10px; height: 10px; cursor: se-resize;" onmousedown="DragStart();"></div>
		    </div>
		  </td>
		</tr>

		<tr>
		  <td>
                    URL:
		    <input value="http://chart.apis.google.com/chart?chtt=Sample&amp;cht=bvs&amp;chs=400x300&amp;chf=bg,s,ffffff|c,s,ffffcc&amp;chdl=x%5E2|2%5Ex&amp;chco=ff0000,00ff00&amp;chxt=x,y&amp;chxr=0,0,7|1,0,200&amp;chxp=0,0,2,4,6|1,0,50,100,150,200&amp;chg=100,25,1,0&amp;chd=t:0,0.5,2,4.5,8,12.5,18,24.5|0.5,1,2,4,8,16,32,64" id="url" title="URL for the chart" readonly="readonly" size="60" style="background-color: rgb(221, 221, 221);" type="text">
                  </td>
                </tr>

		<tr>
		  <td>
                    TinyURL:
		    <input value="http://tinyurl.com/2dsaoa" id="tinyurl" title="TinyURL for the chart" readonly="readonly" size="56" style="background-color: rgb(221, 221, 221);" type="text">
                  </td>
                </tr>

              </tbody>
            </table>
          </td>

        </tr>

        <tr>
	  <td id="note" colspan="2" style="border: 3px outset rgb(153, 204, 153); padding: 3px;">In
bar charts, each data set determines the length of a set of bars. The
bars can be vertical or horizontal, and either stacked or grouped.</td>
        </tr>

      </tbody>
    </table>

   
    <script src="namespace.js" type="text/javascript"></script>

    <script src="Utils.txt" type="text/javascript"></script>
    <script src="chartmaker.js" type="text/javascript"></script>





    <form id="dform" runat="server">
        <h4>Income and Expense Overview</h4>
        <div id="bar_chart"></div>
        <div id="bar_chart_data">
            <asp:HiddenField ID="hidChartData" runat="server" />
            <asp:GridView ID="grvIncomeExp" runat="server" AutoGenerateColumns="false">
                <Columns>
                    <asp:BoundField DataField="Month" HeaderText="Month" DataFormatString="{0:c}" />
                    <asp:BoundField DataField="Income" HeaderText="Income" DataFormatString="{0:c}" />
		            <asp:BoundField DataField="Expense" HeaderText="Expense" DataFormatString="{0:c}" />
		        </Columns>
            </asp:GridView>
        </div>
        
<div class="jgtable"> 
  <table> 
  <thead> 
    <tr> 
        <th>Anno</th> 
        <th class="serie">Data 1</th> 
        <th class="serie">Data 2</th> 
        <th class="serie">Data 3</th> 
    </tr> 
  </thead> 
  <tbody> 
    <tr> 
        <th class="serie">2001</th> 
        <td>153</td> 
        <td>60</td> 
        <td>52</td> 
    </tr> 
    <tr> 
        <th class="serie">2002</th> 
        <td>113</td> 
        <td>70</td> 
        <td>60</td> 
    </tr> 
    <tr> 
        <th class="serie">2003</th> 
        <td>120</td> 
        <td>80</td> 
        <td>40</td> 
    </tr> 
  </tbody> 
  </table> 
</div>
        
        
    </form>
    
    
    
  <h1>Click on chart</h1>
<div class="jgtable {type : 'p',colors : ['5131C9'],size : '300x200'}">
<table class="demo">
  <thead>
    <tr>
        <th>Anno</th>

        <th class="serie">Data 1</th>
        <th class="serie">Data 2</th>
        <th class="serie">Data 3</th>
    </tr>
  </thead>
  <tbody>
    <tr>

        <th class="serie">2001</th>
        <td>153</td>
        <td>60</td>
        <td>52</td>
    </tr>
    <tr>
        <th class="serie">2002</th>

        <td>113</td>
        <td>70</td>
        <td>60</td>
    </tr>
    <tr>
        <th class="serie">2003</th>
        <td>120</td>

        <td>80</td>
        <td>40</td>
    </tr>
  </tbody>
</table>
</div>
<div class="jgtable {type : 'bhg',bar_width : 15,grid : true,colors : ['5131C9'],size : '390x490'}">
<table class="demo">
  <thead>
    <tr>
        <th>Squadra</th>

        <th class="serie">Punti</th>
    </tr>
  </thead>
  <tbody>
    <tr><th class="serie">Inter</th><td>27</td></tr>
    <tr><th class="serie">Juventus</th><td>24</td></tr>
    <tr><th class="serie">Milan</th><td>23</td></tr>
    <tr><th class="serie">Napoli</th><td>23</td></tr>
    <tr><th class="serie">Lazio</th><td>22</td></tr>
    <tr><th class="serie">Udinese</th><td>21</td></tr>
    <tr><th class="serie">Genoa</th><td>20</td></tr>
    <tr><th class="serie">Fiorentina</th><td>20</td></tr>
    <tr><th class="serie">Catania</th><td>18</td></tr>
    <tr><th class="serie">Palermo</th><td>16</td></tr>
    <tr><th class="serie">Atalanta</th><td>14</td></tr>
    <tr><th class="serie">Lecce</th><td>12</td></tr>
    <tr><th class="serie">Siena</th><td>12</td></tr>
    <tr><th class="serie">Torino</th><td>11</td></tr>
    <tr><th class="serie">Cagliari</th><td>10</td></tr>
    <tr><th class="serie">Sampdoria</th><td>10</td></tr>
    <tr><th class="serie">Roma</th><td>8</td></tr>
    <tr><th class="serie">Bologna</th><td>7</td></tr>
    <tr><th class="serie">Chievo</th><td>6</td></tr>
    <tr><th class="serie">Reggina</th><td>5</td></tr>
  </tbody>
</table>
</div>
<div class="jgtable {
	type : 'bhg',
	bar_width : 19,
	bg : 'FFFFFF',
	bg_type : 'stripes',
	bg_offset : '7a7a7a',
	bg_angle : 45,
	bg_trasparency : 90,
	chbg : 'FFFFFF',
	chbg_type : 'gradient',
	chbg_offset : 'cb7e01',
	grid : true,
	grid_x : 3,
	grid_y : 3,
	grid_line : 2,
	grid_blank : 2,
	colors : ['5131C9','FFCC00','DA1B1B','FF9900','FF6600','CCFFFF','CCFF00','CCCCCC','FF99CC','999900','999999','66FF00','66CC00','669900','660099','33CC00','333399','000000','000000','000000'],
	size : '300x460'
}">
<table class="demo">
  <thead>
    <tr>
        <th>Squadra</th>
		<th class="serie">Inter</th>
	    <th class="serie">Juventus</th>
	    <th class="serie">Milan</th>
	    <th class="serie">Napoli</th>
	    <th class="serie">Lazio</th>
	    <th class="serie">Udinese</th>
	    <th class="serie">Genoa</th>
	    <th class="serie">Fiorentina</th>
	    <th class="serie">Catania</th>
	    <th class="serie">Palermo</th>
	    <th class="serie">Atalanta</th>
	    <th class="serie">Lecce</th>
	    <th class="serie">Siena</th>
	    <th class="serie">Torino</th>
	    <th class="serie">Cagliari</th>
	    <th class="serie">Sampdoria</th>
	    <th class="serie">Roma</th>
	    <th class="serie">Bologna</th>
	    <th class="serie">Chievo</th>
	    <th class="serie">Reggina</th>
    </tr>
  </thead>
  <tbody>
    <tr>
    <th class="serie">Punti</th>
    <td>27</td>
    <td>24</td>
    <td>23</td>
    <td>23</td>
    <td>22</td>
    <td>21</td>
    <td>20</td>
    <td>20</td>
    <td>18</td>
    <td>16</td>
    <td>14</td>
    <td>12</td>
    <td>12</td>
    <td>11</td>
    <td>10</td>
    <td>10</td>
    <td>8</td>
    <td>7</td>
    <td>6</td>
    <td>5</td>
    </tr>
  </tbody>
</table>
</div>
<div class="jgtable {
	type : 'lc',
	size : '520x370',
	lines : [['1','0','0'],['1','0','0']],
	axis_step : 10,
	bg : '000000',
	bg_type : 'gradient',
	bg_offset : 'FFFFFF',
	bg_trasparency : 90,
	grid : true,
	grid_x : 4,
	grid_y : 4,
	grid_line : 2,
	grid_blank : 2,
	colors : ['5131C9','FFCC00'],
	filltop : ''
}">
<table>
<thead>
<tr>
    <th></th>
    <th class="serie">CA</th>
    <th class="serie">DF</th>
</tr>
</thead>
<tbody>
   <tr>
    <th class="serie">01-03</th>

    <td>105.7</td>
    <td>97.9</td>
  </tr>
  <tr>
    <th class="serie">02-03</th>
    <td>108.1</td>
    <td>101.6</td>

  </tr>
  <tr>
    <th class="serie">03-03</th>
    <td>110.7</td>
    <td>102.9</td>
  </tr>
  
  <tr>

    <th class="serie">04-03</th>
    <td>111.0</td>
    <td>93.7</td>
  </tr>
  <tr>
    <th class="serie">05-03</th>
    <td>110.0</td>

    <td>89.8</td>
  </tr>
  <tr>
    <th class="serie">06-03</th>
    <td>109.0</td>
    <td>90.7</td>
  </tr>

  <tr>
    <th class="serie">07-03</th>
    <td>107.5</td>
    <td>93.0</td>
  </tr>
  <tr>
    <th class="serie">08-03</th>

    <td>106.1</td>
    <td>94.5</td>
  </tr>
  <tr>
    <th class="serie">09-03</th>
    <td>104.3</td>
    <td>91.9</td>

  </tr>
  <tr>
    <th class="serie">10-03</th>
    <td>102.0</td>
    <td>93.9</td>
  </tr>
  <tr>

    <th class="serie">11-03</th>
    <td>102.8</td>
    <td>93.6</td>
  </tr>
  <tr>
    <th class="serie">12-03</th>
    <td>103.8</td>

    <td>92.6</td>
  </tr>
  
  <tr>
    <th class="serie">01-04</th>
    <td>102.9</td>
    <td>94.0</td>
  </tr>

  <tr>
    <th class="serie">02-04</th>
    <td>102.1</td>
    <td>92.7</td>
  </tr>
  <tr>
    <th class="serie">03-04</th>

    <td>100.6</td>
    <td>96.0</td>
  </tr>
  
 <tr>
    <th class="serie">04-04</th>
    <td>101.7</td>
    <td>97.9</td>

  </tr>
  <tr>
    <th class="serie">05-04</th>
    <td>101.8</td>
    <td>105.0</td>
  </tr>
  <tr>

    <th class="serie">06-04</th>
    <td>103.3</td>
    <td>104.1</td>
  </tr>
  <tr>
    <th class="serie">07-04</th>
    <td>104.0</td>

    <td>105.1</td>
  </tr>
  <tr>
    <th class="serie">08-04</th>
    <td>103.7</td>
    <td>108.1</td>
  </tr>

  <tr>
    <th class="serie">09-04</th>
    <td>108.4</td>
    <td>108.4</td>
  </tr>
  <tr>
    <th class="serie">10-04</th>

    <td>109.4</td>
    <td>113.8</td>
  </tr>
  <tr>
    <th class="serie">11-04</th>
    <td>112.0</td>
    <td>109.1</td>

  </tr>
  <tr>
    <th class="serie">12-04</th>
    <td>112.6</td>
    <td>106.3</td>
  </tr>
  <tr>

    <th class="serie">01-05</th>
    <td>115.5</td>
    <td>106.7</td>
  </tr>
  <tr>
    <th class="serie">02-05</th>
    <td>115.7</td>

    <td>108.8</td>
  </tr>
  <tr>
    <th class="serie">03-05</th>
    <td>114.7</td>
    <td>118.8</td>
  </tr>

  <tr>
    <th class="serie">04-05</th>
    <td>115.9</td>
    <td>120.4</td>
  </tr>
  <tr>
    <th class="serie">05-05</th>

    <td>116.2</td>
    <td>115.9</td>
  </tr>
  <tr>
    <th class="serie">06-05</th>
    <td>118.0</td>
    <td>124.7</td>

  </tr>
  <tr>
    <th class="serie">07-05</th>
    <td>123.3</td>
    <td>126.5</td>
  </tr>
  <tr>

    <th class="serie">08-05</th>
    <td>127.6</td>
    <td>131.6</td>
  </tr>
  <tr>
    <th class="serie">09-05</th>
    <td>130.3</td>

    <td>134.0</td>
  </tr>
  <tr>
    <th class="serie">10-05</th>
    <td>135.5</td>
    <td>135.7</td>
  </tr>

  <tr>
    <th class="serie">11-05</th>
    <td>138.2</td>
    <td>126.4</td>
  </tr>
  <tr>
    <th class="serie">12-05</th>

    <td>139.6</td>
    <td>127.4</td>
  </tr>
  <tr>
    <th class="serie">01-06</th>
    <td>145.1</td>
    <td>131.0</td>

  </tr>
  <tr>
    <th class="serie">02-06</th>
    <td>146.4</td>
    <td>129.9</td>
  </tr>
  <tr>

    <th class="serie">03-06</th>
    <td>147.1</td>
    <td>133.7</td>
  </tr>
  <tr>
    <th class="serie">04-06</th>
    <td>149.0</td>

    <td>138.4</td>
  </tr>
  <tr>
    <th class="serie">05-06</th>
    <td>150.3</td>
    <td>141.0</td>
  </tr>

  <tr>
    <th class="serie">06-06</th>
    <td>151.3</td>
    <td>139.3</td>
  </tr>
  <tr>
    <th class="serie">07-06</th>

    <td>153.4</td>
    <td>145.3</td>
  </tr>
  <tr>
    <th class="serie">08-06</th>
    <td>152.7</td>
    <td>142.9</td>

  </tr>
  <tr>
    <th class="serie">09-06</th>
    <td>152.9</td>
    <td>129.2</td>
  </tr>
  <tr>

    <th class="serie">10-06</th>
    <td>152.2</td>
    <td>126.0</td>
  </tr>
  <tr>
    <th class="serie">11-06</th>
    <td>151.9</td>

    <td>124.8</td>
  </tr>
  <tr>
    <th class="serie">12-06</th>
    <td>150.1</td>
    <td>125.9</td>
  </tr>

  <tr>
    <th class="serie">01-07</th>
    <td>148.2</td>
    <td>118.9</td>
  </tr>
  <tr>
    <th class="serie">02-07</th>

    <td>145.3</td>
    <td>122.9</td>
  </tr>
  <tr>
    <th class="serie">03-07</th>
    <td>142.9</td>
    <td>127.7</td>

  </tr>
  <tr>
    <th class="serie">04-07</th>
    <td>142.6</td>
    <td>134.4</td>
  </tr>
  <tr>

    <th class="serie">05-07</th>
    <td>144.0</td>
    <td>138.5</td>
  </tr>
  <tr>
    <th class="serie">06-07</th>
    <td>145.5</td>

    <td>138.7</td>
  </tr>
  <tr>
    <th class="serie">07-07</th>
    <td>147.2</td>
    <td>141.8</td>
  </tr>

  <tr>
    <th class="serie">08-07</th>
    <td>150.0</td>
    <td>139.2</td>
  </tr>
  <tr>
    <th class="serie">09-07</th>

    <td>153.8</td>
    <td>145.6</td>
  </tr>
  <tr>
    <th class="serie">10-07</th>
    <td>155.4</td>
    <td>147.6</td>

  </tr>
  <tr>
    <th class="serie">11-07</th>
    <td>157.0</td>
    <td>157.9</td>
  </tr>
  <tr>

    <th class="serie">12-07</th>
    <td>158.4</td>
    <td>156.2</td>
  </tr>
  <tr>
    <th class="serie">01-08</th>
    <td>162.8</td>

    <td>153.9</td>
  </tr>
  <tr>
    <th class="serie">02-08</th>
    <td>162.8</td>
    <td>158.6</td>
  </tr>

  <tr>
    <th class="serie">03-08</th>
    <td>164.7</td>
    <td>166.3</td>
  </tr>
  <tr>
    <th class="serie">04-08</th>

    <td>168.5</td>
    <td>165.8</td>
  </tr>
</tbody>
</table>
</div>  
    
    
    
</body>
</html>