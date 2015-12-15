this project explores hardware, software and methodologies required to support and enable educational research that utilizes virtual reality, 3D visualization and haptovisual interfaces to produce and enable clinical simulation environments.

### SUMMARY ###

with openLabyrinth, Ariadne, and (a modified) Pivote controller, we are attempting to create a browser-based 2nd Life and opensim game storyline player and editor. OL is a decision tree of sorts, down which a user picks a path, just by playing the game, and relevant variables change accordingly. as the game editor, one can chose to assign the players' clothing, rooms, animations, sounds, objects, etc., at each node of the tree. also, as any SL object can 'react' to (a simplified) xml web service (since every prim can have httprequest/response capability), this really allows for infinite possibilities. ariadne is a framework where we can hopefully define and extend those, and then use them in a context of teaching medicine.

### ARIADNE ###

in a nutshell, ariadne is the openLabyrinth remote service xml feed decorator/aggregator/sequencer, essentially. some of that additional xml just happens to be a list of available goodies in your instance (read 'parcel') of the game, which will be triggered, played, given, done, etc, if AND ONLY IF the player traverses to that node of the game's decision tree.

ariadne4j (ie. java version) is the current active development stream, with ariadne.NET and others to come.

the actual servlets don't really do much... it's the in-world controller, badges & bracelets that are doing all the parsing, dispatching, coordination of proper timing of the sequence of events, etc...

basically, the main files are:

`ariadne4j/src/Ariadne.java `
see (simplified) sequence diagram for it [here](http://nosm-verse.googlecode.com/hg/VERSE/etc/uml_seq_ariadne.png).

`ariadne4j/WebContent/index2.html `

`ariadne4j/WebContent/assetEdit.html `

`ariadne/lsl/controller.lsl `

`ariadne/lsl/bracelet.lsl  `


### immediate TODOs ###

- inventory service: in-world, we can simply add everything to the holodeck's contents for now, and see if we can add to it an onInventoryChange script (sending new obj attribs to inventory service), a script that doesn't interfere with horizon's...

- google api: maps and charts

- google spreadsheets: importing OL node/counter xml to feed map markers and chart values

- amazon maps of 2nd Life, @ diff zoom levels

- want to do api.flickr.com, jgCharts, eventually make a puck-shaped prim that will be the 3d google pie-chart, then do a bar chart, etc

- use [slcicp api](http://www.xstreetsl.com/modules.php?name=Marketplace&file=item&ItemID=1749108)

- USE THE RLV RELAY!

also, see [in-world presentation](http://slurl.com/secondlife/Nossum/98/154/28) ... esp. the Wishlist slide... ;)

### MEDIA ###

- [here is a video demo](http://www.youtube.com/watch?v=Ba2TefyXNco) on how to quickly create a playable scenario using VUE, OL and A4J


- [here is the webcast](http://normedstream.lakeheadu.ca/1/watch/648.aspx) to a presentation put on @ NOSM East on January 28th, where Dr. Ellaway gives a tour of nossum island and presents some of our recent SL activities

