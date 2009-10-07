<%@ Page Language="C#" AutoEventWireup="true" CodeFile="simpleChart.aspx.cs" Inherits="Chart" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>

    <meta http-equiv="Content-type" content="text/html; charset=ISO-8859-1">
    <style type="text/css">
      .colorPicker { width: 16px; height: 16px; border: 2px #cccccc inset; }
    </style>

    <title>ACME Chartmaker</title>

  </head><body onload="Setup();">

    <noscript><h1>You must enable JavaScript to use this page.</h1></noscript>

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

  </body></html>