var map;
var polys = [];
var markers = [];
var editingNow = false;

function updateImage() {
   var baseUrl = "http://maps.google.com/maps/api/staticmap?";

   var params = [];

   if (!document.getElementById("mapFitCHECKBOX").checked) {
     params.push("center=" + map.getCenter().lat().toFixed(6) + "," + map.getCenter().lng().toFixed(6));
     params.push("zoom=" + map.getZoom());
   }

   var markerSize = document.getElementById("markerSizeSELECT").value;
   var markerColor = document.getElementById("markerColorSELECT").value;
   var markerLetter = document.getElementById("markerLetterSELECT").value;
   var markerParams = [];
   if (markerSize != "") markerParams.push(markerSize);
   if (markerColor != "") markerParams.push(markerColor);
   if (markerLetter != "") markerParams.push(markerLetter);

   var markersArray = [];
   for (var i = 0; i < markers.length; i++) {
     if (document.getElementById("markerTypeCHECKBOX").checked) {
       markersArray.push(markers[i].getTitle().replace(" ", "+", "g"));
     } else {
       markersArray.push(markers[i].getLatLng().lat().toFixed(6) + "," + markers[i].getLatLng().lng().toFixed(6));
     }
   }
   if (markersArray.length) {
     var markersString = markerParams.join("|");
     if (markerParams.length) markersString += "|";
     markersString += markersArray.join("|");
     params.push("markers=" + markersString);
   }

   var polyColor = "color:0x" + document.getElementById("polyColorSELECT").value + document.getElementById("polyAlphaSELECT").value;
   var polyWeight = "weight:" + document.getElementById("polyWeightSELECT").value;
   var polyParams = polyColor + "|" + polyWeight;
   for (var i = 0; i < polys.length; i++) {
     var poly = polys[i];
     var polyLatLngs = [];
     for (var j = 0; j < poly.getVertexCount(); j++) {
       polyLatLngs.push(poly.getVertex(j).lat().toFixed(5) + "," + poly.getVertex(j).lng().toFixed(5));
     }
     params.push("path=" + polyParams + "|" + polyLatLngs.join("|"));
   }
   if (map.getCurrentMapType() == G_SATELLITE_MAP) {
     params.push("maptype=satellite");
   }
   if (map.getCurrentMapType() == G_HYBRID_MAP) {
     params.push("maptype=hybrid");
   }
   if (map.getCurrentMapType() == G_PHYSICAL_MAP) {
     params.push("maptype=terrain");
   }

   params.push("size=" + document.getElementById("mapWidthTEXT").value + "x" + document.getElementById("mapHeightTEXT").value);
   var img = document.createElement("img");
   //img.src = baseUrl + params.join("&") + "&sensor=false&key=ABQIAAAAKtBwpoUmQp880AOn2bPHchT-tJ5jYecKhc1KUg5XP_L4bJqgbxTPd-I4LxiNZJw06GFyH4-V7-8Zng";
   img.src = baseUrl + params.join("&") + "&sensor=false&key=ABQIAAAAKtBwpoUmQp880AOn2bPHchT-tJ5jYecKhc1KUg5XP_L4bJqgbxTPd-I4LxiNZJw06GFyH4-V7-8Zng";
   document.getElementById("staticMapIMG").innerHTML = "";
   document.getElementById("staticMapIMG").appendChild(img);

   document.getElementById("staticMapURL").innerHTML = baseUrl + params.join("&") + "&sensor=TRUE&key=ABQIAAAAKtBwpoUmQp880AOn2bPHchT-tJ5jYecKhc1KUg5XP_L4bJqgbxTPd-I4LxiNZJw06GFyH4-V7-8Zng";
}

function mapLoad() {
  if (GBrowserIsCompatible()) {
    map = new GMap2(document.getElementById("map"));
    map.setCenter(new GLatLng(37.400470,-122.072981), 13);
    map.addMapType(G_PHYSICAL_MAP);
    map.addControl(new GSmallMapControl());
    map.addControl(new GMapTypeControl());
    GEvent.addListener(map, "moveend", function(marker, point) {
      updateImage();
    });
    GEvent.addListener(map, "maptypechanged", function(marker, point) {
      updateImage();
    });
    GEvent.addListener(map, "click", function(overlay, latlng) {
      if (latlng && !editingNow) {
        createPolyAt(latlng);
      }
      updateImage();
     });
    geocoder = new GClientGeocoder();
    updateImage();
  }
}


function createPolyAt(latlng) {
  var poly = new GPolyline([latlng]);
  map.addOverlay(poly);
  poly.enableDrawing();
  editingNow = true;
  GEvent.addListener(poly, "mouseover", function() {
    poly.enableEditing();
  });
  GEvent.addListener(poly, "mouseout", function() {
    poly.disableEditing();
  });
  GEvent.addListener(poly, "lineupdated", function() {
    updateImage();
  });
  GEvent.addListener(poly, "endline", function() {
    editingNow = false;
  });
  polys.push(poly);
}

function createMarkerAt(latlng, address) {
  var marker = new GMarker(latlng, {draggable:true, title: address});
  GEvent.addListener(marker, 'dragend', function() {
    updateImage();
  });
  map.addOverlay(marker);
  markers.push(marker);
}

function clearMarkers() {
  for (var i = 0; i < markers.length; i++) {
    map.removeOverlay(markers[i]);
  }
  markers = [];
  updateImage();
}

function clearPolys() {
  for (var i = 0; i < polys.length; i++) {
    map.removeOverlay(polys[i]);
  }
  polys = [];
  updateImage();
}

function showAddress() {
  var address = document.getElementById("addressTEXT").value;
  geocoder.getLatLng(
    address,
    function(latlng) {
      if (!latlng) {
        alert(address + " not found");
      } else {
        map.setCenter(latlng, 13);
        createMarkerAt(latlng, address);
        updateImage();
      }
    }
  );
}

function disableSelects() {
  document.getElementById("markerSizeSELECT").disabled = false;
  document.getElementById("markerColorSELECT").disabled = false;
  document.getElementById("markerLetterSELECT").disabled = false;

  var markerColor = document.getElementById("markerColorSELECT").value;
  var markerSize = document.getElementById("markerSizeSELECT").value;
  if (markerSize == "small" || markerSize == "tiny") {
    document.getElementById("markerLetterSELECT").selectedIndex = 0;
    document.getElementById("markerLetterSELECT").disabled = true;
  }
}


// SLURLs:
function build_url()
{
var slurl = "http://slurl.com/secondlife/" + escape(document.getElementById("region").value) + "/" + document.getElementById("x").value + "/" + document.getElementById("y").value + "/" + document.getElementById("z").value + "/";
var slurlOptions = new Array;
if (document.getElementById("windowImage").value) slurlOptions.push("img=" + escape(document.getElementById("windowImage").value));
if (document.getElementById("windowTitle").value) slurlOptions.push("title=" + escape(document.getElementById("windowTitle").value));
if (document.getElementById("windowMessage").value) slurlOptions.push("msg=" + escape(document.getElementById("windowMessage").value));
if (document.getElementById("zoom_level").value) slurlOptions.push("zoom=" + escape(document.getElementById("zoom_level").value));
if (document.getElementById("u").value) slurlOptions.push("u=" + escape(document.getElementById("u").value));

if (slurlOptions.length > 0) {
        slurl += "?";
        for(f=0; f<slurlOptions.length; f++) {
                slurl += slurlOptions[f];
                if (f < slurlOptions.length - 1) slurl += "&";
        }
}
return slurl;
}

