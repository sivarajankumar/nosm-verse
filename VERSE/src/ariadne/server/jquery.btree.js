/**
Script for displaying binary trees
@author: FM L'Heureux
@date: 2009-10-20
@version: 1.0.4
@see: http://frank-mich.com/jQuery

*/

jQuery.fn.btree = function(options){
	var btree = new Btree($(this), options);
	btree.displayTree();
	return btree;
};

function Btree(caller, options){
	var jg = new jsGraphics(document.getElementById(caller.attr("id")));
	caller.css("position", "relative");
	this.caller = caller;
	this.size = null;
	this.settings = jQuery.extend({
		hSpace: 10,
		vSpace: 10,
		borderWidth: 1,
		branchColor: "#000000",
		branchStroke: 2,
		jsGraphics: jg,
		horizontal: true
	}, options);
};

Btree.prototype.displayTree = function(){
	//display settings
	var hSpace = parseInt(this.settings.hSpace, 10);
	var vSpace = parseInt(this.settings.vSpace, 10);
	var borderWidth = parseInt(this.settings.borderWidth, 10);
	var size = this.size;//avoids interference with the jsGraphics lib
	var branchStroke = parseInt(this.settings.branchStroke, 10);
	var branchColor = this.settings.branchColor;
	var i = 0;
	//compute the max width and max height and apply it	
	var spanWidth = 0;
	var spanHeight = 0;
	var jg = this.settings.jsGraphics;
	jg.clear();
	var callerId = this.caller.attr("id");
	this.caller.children("div").children("span").each(function(){
		$(this).css("white-space", "nowrap");
		if(this.size == null || i <= this.size){
			spanWidth = Math.max(spanWidth, $(this).width());
			spanHeight = Math.max(spanHeight, $(this).height());
			i ++;
		}
	});
	if(this.size == null){
		this.size = i;
		size = i;
	}
	i = 0;
	if(vSpace < 0){
		vSpace = 0;
	}
	if(hSpace < 0){
		hSpace = 0;
	}
	var maxPos = btreeBoxDistance((size - 1)/2);
	if(this.settings.horizontal){
		var usingVSpace = vSpace/2 - spanHeight / 2 - borderWidth;
		var usingHSpace = hSpace - spanWidth / 2 - borderWidth;
		//position the boxes and set size
		this.caller.children("div").each(function(){
			var leftPos = btreeBoxDistance(i);
			if(i < size){
				$(this).css("position", "relative");
				$(this).css("left", (leftPos * (spanWidth + borderWidth * 2 + hSpace) + "px"));
				$(this).css("top", ((i * usingVSpace) + "px"));
				$(this).css("border-width", borderWidth + "px");
				$(this).css("width", spanWidth);
				$(this).css("height", spanHeight);
				i ++
			}
		});
		
		//once everything is in place, refresh jg
		this.caller.height((spanHeight + borderWidth*2) * i + usingVSpace * (i - 1));
		this.caller.width((hSpace + spanWidth + borderWidth*2) * (maxPos + 1) - hSpace);
		var callerOffset = this.caller.offset();
		var callerLeftBorder = this.caller.css("borderLeftWidth");
		callerLeftBorder = parseInt(callerLeftBorder.substr(0, callerLeftBorder.length - 2), 10);
		if(isNaN(callerLeftBorder)){
			//IE fix
			callerLeftBorder = 0;		
		}
		var callerTopBorder = this.caller.css("borderTopWidth");
		callerTopBorder = parseInt(callerTopBorder.substr(0, callerTopBorder.length - 2), 10);
		if(isNaN(callerTopBorder)){
			//IE fix
			callerTopBorder = 0;		
		}
		if(branchStroke > 0){
			refreshJg(jg, callerId, branchStroke, branchColor);
			i = 0;
			this.caller.children("div").each(function(){
				var leftPos = btreeBoxDistance(i);
				if(i < size){
					//on the left, draw straight horizontal and vertical lines
					var offset = $(this).offset();
					offset.top -= callerOffset.top + callerTopBorder;
					offset.left -= callerOffset.left + callerLeftBorder;
					if(leftPos > 0){
						var x = offset.left - hSpace / 2 - branchStroke / 2;
						var y = offset.top + (($(this).height() + borderWidth * 2) / 2);
						//horizontal line
						jg.drawLine(x, y - (branchStroke / 2), offset.left - (branchStroke / 2) - 1, y - (branchStroke / 2));
						var halfLength = ((Math.pow(2, leftPos) * (spanHeight + usingVSpace + borderWidth * 2)) - 1) / 2;
						//vertical line
						jg.drawLine(x, y - halfLength - (branchStroke / 2), x, y + halfLength - 1);
						//2 extremities
						jg.drawLine(x - hSpace / 2 + branchStroke / 2, y - halfLength - (branchStroke / 2), x, y - halfLength - (branchStroke / 2));
						jg.drawLine(x - hSpace / 2 + branchStroke / 2, y + halfLength - (branchStroke / 2), x, y + halfLength - (branchStroke / 2));
					}
					i ++;
				}
			});
		}
	}else{
		var usingHSpace = hSpace/2 + spanWidth / 2 + borderWidth;
		var last = null;
		this.caller.children("div").each(function(){
			if(i < size){
				//position the boxes and set size
				var topPos = btreeBoxDistance(i);
				$(this).css("top", ((topPos - i) * (spanHeight + borderWidth * 2) + (topPos * vSpace)) + "px");
				$(this).css("left", ((i * usingHSpace) + "px"));
				$(this).css("border-width", borderWidth + "px");
				$(this).css("width", spanWidth);
				$(this).css("height", spanHeight);
			}
			i ++;
		});
		this.caller.height((spanHeight + borderWidth * 2) * (maxPos + 1) + maxPos * vSpace);		
		this.caller.width((size + 1) / 2 * (spanWidth + borderWidth * 2 + hSpace) - hSpace);
		var callerOffset = this.caller.offset();
		var callerLeftBorder = this.caller.css("borderLeftWidth");
		callerLeftBorder = parseInt(callerLeftBorder.substr(0, callerLeftBorder.length - 2), 10);
		if(isNaN(callerLeftBorder)){
			//IE fix
			callerLeftBorder = 0;		
		}
		var callerTopBorder = this.caller.css("borderTopWidth");
		callerTopBorder = parseInt(callerTopBorder.substr(0, callerTopBorder.length - 2), 10);
		if(isNaN(callerTopBorder)){
			//IE fix
			callerTopBorder = 0;		
		}
		//once everything is in place, refresh jg
		refreshJg(jg, callerId, branchStroke, branchColor);
		i = 0;
		this.caller.children("div").each(function(){
			if(i < size){
				var topPos = btreeBoxDistance(i);
				//on the top, draw straight horizontal and vertical lines
				var offset = $(this).offset();
				offset.top -= callerOffset.top + callerTopBorder;
				offset.left -= callerOffset.left + callerLeftBorder;
				if(topPos > 0){
					var x = offset.left + ($(this).width() + borderWidth * 2) / 2;
					var y = offset.top - vSpace / 2;
					//middle
					jg.drawLine(x - (branchStroke / 2), y, x - (branchStroke / 2), offset.top - (branchStroke / 2));
					var halfLength = Math.pow(2, topPos) * (usingHSpace) / 2 - 1;
					//horizontal line
					jg.drawLine(x - halfLength - (branchStroke / 2), y, x + halfLength, y);
					//2 extremities
					jg.drawLine(x - halfLength - (branchStroke / 2), 2 * y - offset.top, x - halfLength - (branchStroke / 2), y);
					jg.drawLine(x + halfLength - (branchStroke - 2) / 2, 2 * y - offset.top, x + halfLength - (branchStroke - 2) / 2, y);
				}
				i ++;
				last = $(this);
			}
		});
	}
	jg.paint();
	this.settings.jsGraphics = jg;
};

Btree.prototype.clear = function(){
	this.settings.jsGraphics.clear();
};

btreeBoxDistance = function(i){
	var counter = 0;
	while(i % 2 == 1){
		counter ++;
		i = (i - 1) / 2;
	}
	return counter;
};

refreshJg = function(jg, callerId, branchStroke, branchColor){
	jg.clear();
	jg.setStroke(branchStroke);
	jg.setColor(branchColor);
};