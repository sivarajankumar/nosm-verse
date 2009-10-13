//global variables
_mSvgEnabled = true;
_mSvgForced = true;
var map, projection;
var diagrams = [];
var svgNS = "http://www.w3.org/2000/svg";
var initLat = 47;
var initLng = 8.5;
var prevViewBoxX, prevViewBoxY; // = 33915 for 47, 8.5 and 22751 for 47, 8.5;
var prevZoom = 8;
var top = 0;
var left = 0;

function load() {

    if (GBrowserIsCompatible()) {

        //load map
        //if (map == null){ 
        map = new GMap2(document.getElementById("map"), G_NORMAL_MAP);
        //}
        //add control and set map center
        map.addControl(new GLargeMapControl());
        map.setCenter(new GLatLng(initLat, initLng), prevZoom, G_NORMAL_MAP);
        projection = map.getCurrentMapType().getProjection();

        prevViewBoxX = getSWBoundsInPixel().x;
        prevViewBoxY = getNEBoundsInPixel().y;

        //create new div node for the svg root element
        var svgDiv = document.createElement("div");
        svgDiv.setAttribute("id", "svgDivison");
        map.getPane(G_MAP_MAP_PANE).appendChild(svgDiv);

        //create new svg root element and set attributes
        var svgRoot = document.createElementNS(svgNS, "svg");
        svgRoot.setAttribute("id", "customSvgRoot");
        svgRoot.setAttribute("style", "position:absolute; top:0px; left:0px");
        svgRoot.setAttribute("viewBox", "0 0 800 600");
        svgRoot.setAttribute("width", "800");
        svgRoot.setAttribute("height", "600");
        svgDiv.appendChild(svgRoot);

        //read the svg file
        readData(svgRoot);

        //add all event listener
        //event listener which place the diagrams after zoom or move
        GEvent.addListener(map, "moveend", function() {
            removeSvgChildren(svgRoot);
            for (var i = 0; i < diagrams.length; i++) {
                replaceSvgNode(svgRoot, diagrams[i].cloneNode(true),
			parseFloat(diagrams[i].getAttribute("lat")),
			parseFloat(diagrams[i].getAttribute("lng")),
			map.getZoom());
            }

            var sw = getSWBoundsInPixel();
            var ne = getNEBoundsInPixel();

            var zoom = map.getZoom();
            if (zoom != prevZoom) prevZoom = zoom;
            else {
                left += (sw.x - prevViewBoxX);
                top += (ne.y - prevViewBoxY);
                svgRoot.setAttributeNS(null, "style", "position:absolute; top:" + top +
				"px; left:" + left + "px");
            }
            prevViewBoxX = sw.x;
            prevViewBoxY = ne.y;
        });

        //event listeners which shows the coordinates
        GEvent.addListener(map, "mousemove", function(latlng) {
            var latStr = "Lat: " + (Math.round(latlng.lat() * 100) / 100);
            var lngStr = "Lng: " + (Math.round(latlng.lng() * 100) / 100);
            document.getElementById("lat").firstChild.nodeValue = latStr;
            document.getElementById("lng").firstChild.nodeValue = lngStr;
        });

        GEvent.addListener(map, "mouseout", function(latlng) {
            document.getElementById("lat").firstChild.nodeValue = "";
            document.getElementById("lng").firstChild.nodeValue = "";
        });

    }

    // display a warning if the browser was not compatible
    else {
        alert("Sorry, the Google Maps API is not compatible with this browser");
    }
}

function replaceSvgNode(svgNode, node, lat, lng, zoom) {
    var sw = getSWBoundsInPixel();
    var ne = getNEBoundsInPixel();

    svgNode.setAttribute("viewBox", sw.x + " " + ne.y + " 800 600");

    var curr = projection.fromLatLngToPixel(new GLatLng(lat, lng), zoom);

    //var newScale = 0.0011 * Math.pow(2, zoom);
    var newScale = 0.2;
    var transformStr = "translate(" + curr.x + ", " + curr.y
		+ ") scale(" + newScale + ")";
    node.setAttribute("transform", transformStr);

    svgNode.appendChild(node);

    //add dom event listeners to the diagrams
    //on click show info window
    GEvent.addDomListener(node, "click", function(evt) {
        var htmlStr = "Stadt: " + node.getAttribute("id") +
			"<br/>L&auml;nge: " + node.getAttribute("lat") +
			"<br/>Breite: " + node.getAttribute("lng");
        map.openInfoWindowHtml(new GLatLng(node.getAttribute("lat"),
			node.getAttribute("lng")), htmlStr);
    });
    //on mouse over show coordinates and higlight diagram
    GEvent.addDomListener(node, "mouseover", function(evt) {
        var currentOpacity = parseFloat(node.getAttributeNS(null,
			"fill-opacity"));
        node.setAttributeNS(null, "fill-opacity", currentOpacity * 2);
        document.getElementById("text").firstChild.nodeValue =
			node.getAttribute("id");
    });
    GEvent.addDomListener(node, "mouseout", function(evt) {
        var currentOpacity = parseFloat(node.getAttributeNS(null,
			"fill-opacity"));
        node.setAttributeNS(null, "fill-opacity", currentOpacity / 2);
        document.getElementById("text").firstChild.nodeValue = "";
    });
}

function readData(svgNode) {
    GDownloadUrl("symbol.svg", function(data, responseCode) {
        var xml = GXml.parse(data);
        diagrams = xml.documentElement.getElementsByTagName("g");
        for (var i = 0; i < diagrams.length; i++) {
            replaceSvgNode(svgNode, diagrams[i].cloneNode(true),
			parseFloat(diagrams[i].getAttribute("lat")),
			parseFloat(diagrams[i].getAttribute("lng")),
			map.getZoom());
        }
    });
}

function getSWBoundsInPixel() {
    var currentBounds = map.getBounds();
    var sw = currentBounds.getSouthWest();
    return projection.fromLatLngToPixel(new GLatLng(sw.lat(), sw.lng()), map.getZoom());
}

function getNEBoundsInPixel() {
    var currentBounds = map.getBounds();
    var ne = currentBounds.getNorthEast();
    return projection.fromLatLngToPixel(new GLatLng(ne.lat(), ne.lng()), map.getZoom());
}

function removeSvgChildren(svgNode) {
    while (svgNode.lastChild != null) {
        svgNode.removeChild(svgNode.lastChild);
    }
}


