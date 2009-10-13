var mapInstance;
function loadSLmap() {
    // creates the map
    mapInstance = new SLMap(document.getElementById('type_slamazonmapimage'),
                          { hasZoomControls: false, hasPanningControls: false });
    mapInstance.centerAndZoomAtSLCoord(new XYPoint(1148, 1164), 2);

    (document.getElementById('zoom_level')).innerHTML = mapInstance.getCurrentZoomLevel();
}

function setZoom(level) {
    mapInstance.setCurrentZoomLevel(level);
    (document.getElementById('zoom_level')).innerHTML = mapInstance.getCurrentZoomLevel();
}

function zoomIn() {
    mapInstance.zoomIn();
    (document.getElementById('zoom_level')).innerHTML = mapInstance.getCurrentZoomLevel();
}

function zoomOut() {
    mapInstance.zoomOut();
    (document.getElementById('zoom_level')).innerHTML = mapInstance.getCurrentZoomLevel();
}
var regionName = 'nossum';

var xmlhttp;

function loadXMLDoc(url) {
    xmlhttp = GetXmlHttpObject();
    if (xmlhttp == null) {
        alert("Your browser does not support XMLHTTP!");
        return;
    }
    xmlhttp.onreadystatechange = stateChanged;
    xmlhttp.open("GET", url, true);
    xmlhttp.send(null);
}

function GetXmlHttpObject() {
    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject) {
        // code for IE6, IE5
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}

function stateChanged() {
    if (xmlhttp.readyState == 4) {
        if (xmlhttp.status == 200) {
            alert(xmlhttp.responseXML);
        }
        else {
            alert("Problem retrieving XML data:" + xmlhttp.statusText);
        }
    }
}


function SLPoint(params, callback) {
    var s = params[0].innerHTML.split("/"); //first element of params must be this format: region/x/y/z
    var regionName = s[0];
    var localX = s[1];
    var localY = s[2];
    var varName = "slRegionPos_result";
    var scriptURL = "https://cap.secondlife.com/cap/0/d661249b-2b5a-4436-966a-3d3b8d7a574f?var=" + varName + "&sim_name=" + encodeURIComponent(regionName);
    var onLoadHandler = function() {
        if (slRegionPos_result.error) {
            alert("The region name '" + regionName + "' was not recognised.");
        }
        else {
            callback(params, regionName, slRegionPos_result.x + (localX / 256), slRegionPos_result.y + (localY / 256));
        }
    };
    slAddDynamicScript(scriptURL, onLoadHandler);
}

//loadXMLDoc('https://cap.secondlife.com/cap/0/d661249b-2b5a-4436-966a-3d3b8d7a574f?var=foo&sim_name="+regionName+"')

var foo;
//JSON.decode(loadXMLDoc('https://cap.secondlife.com/cap/0/d661249b-2b5a-4436-966a-3d3b8d7a574f?var=foo&sim_name="+regionName+"');




var foo = { 'x': 1148, 'y': 1164 };
zoomInt = "1";
var imageURL = "http://map.secondlife.com/map-" + zoomInt + "-" + foo[0] + "-" + foo[1] + "-objects.jpg";

