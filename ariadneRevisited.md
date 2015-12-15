<br />The Project: <br />
<br />i liked the anti-acronym project-naming nomenclature that CS used in a previous life. He was simply inspired by a random TV show (in HD, no doubt). well, let's do it again! a name for name's sake, so my Aurora will be Ariadne! <br />
<br />therefore, may i present... (drumroll): <br />
<br /><b><i>openLabyrinth's Ariadne plug-in for Second Life </i></b> <br />
<br />so what do we know about it <br />
<br />it has avatars, i.e. characters, a game, a puzzle being played (oh, and you're carefully being watched!)  <br />
<br />It's specifically based on PREVIEW's PIVOTE Controller & Mannequin Framework, and http-driven in-world quizzes, questionnaires, and rudimentary gaming engines more generally. <br />
<br />The name isn't as random as initially stated... actually, there's a relevant theme: the story of Ariadne is the one where she gave a guiding thread to Theseus, her beau at the time, and that red fleece got him to, thru, and away from the big bad minotaur. that much i know... and that it's greek. <br />
<br /><a href='http://en.wikipedia.org/wiki/Ariadne'><a href='http://en.wikipedia.org/wiki/Ariadne'>http://en.wikipedia.org/wiki/Ariadne</a></a> <br />
<br />this myth, being so old, has been a lesson for every generation who has heard it since greek times. We can therefore conclude that this technique, often called breadcrumb, has been used by thousands, if no millions of people. It it indeed also all over the web's multitudes of portals and sites.  <br />
<br />So, nothing new here...  <br />
<br />well, not to stretch a metaphor too far, our particular mythical guiding string is a short one... and it's in xml: a feed from openLabyrinth that guides the player down a path that has a fixed number of possibilities at set intervals  (like the traversal of a maze).  <br />
<br />It's just that in our case, the 'player' is a virtual one, on a virtual piece of land, that has virtual value... but in a REAL game, with REAL interactivity, and most importantly, a REAL(ly) stimulated thought processes, and REAL telemetric data & evaluations of that data.  <br />
<br />stylistically (and also because of it's inherently segmented animating), we are aspiring to give it that Dragon's Lair cartoon feel! more art styling ideas below...
<br />The Methodology: <br />
<br />THIS SHOULD BE an experiment in tandem programming over sd-video. We (RS & CS) will document the real advantages to tandem programming face-2-face (as interestingly enough, this is something that usually happens from the side, in shared physical space, 4 eyes staring at the same screen, and sharing the keyboard. Our remote variation will inherently rely more on screen sharing, Second Life co-existence, and OneNote shared sessions. so this paradigm is unique and rare enough to track and document.
<br />as Agile Programming would dictate, the sharing of keyboard controls, or more precisely, control of the process of writing code happens in a fairly dynamic fashion. This is tricky, and maybe not necessarily be timed (like in is older methods), but is driven by who ever has the current idea/solution simply takes control, and he/she makes the edit.  <br />
<br />in SL, this will manifest as ONLY one avatar at a time holding control of the script object in the controller, and will appear visually (and to other avatars present) as a stream of subtle white traveling cloud-dots, from the active avatar's raised hand pointing towards the controller prim-set.  <br />
<br />Since both coders can't look at the same object's contents (this is to restrict overwrites, I assume), the other dev guy will need to be looking at his local copy. annoyingly, when changes happen at a faster rate, we'll need to just keep saving rapidly, and refreshing local copies. <br />
<br />Some other rules: <br />
<br />- never optimize-as-u-go, do it later <br />
<br />- just get it to work, hack away <br />
<br />- strive to make it context relevant: as true as possible medical scenarios (ie. a patient!): there are real process trees out there on the net that we can use for testing labyrinths (had a nice one in pocket size wilderness medicine & survival book… lost it)<br />
<br />Current Project Status:  <br />
<br />What's Pressing: <br />
<br />- parser performance: might be a huge factor, notecard reading to a lesser extent (but also very slow) <br />
<br /> http\_response is in need of overhaul: must dispatch all incoming tasks as fast as possible <br />
<br />- sequencing:  <br />
<br />do we pass that in as asset attribute or as the value of an InnerSequence element How to address recursion of sequences (want to be able to nest sequences) <br />
<br />right now: implicit sequence of Asset XML tags determines play order. <br />
<br />does it matter (xsl can save us anyway) <br />
<br />also, can we replace the arrays of global Asset attributes to one multidimensional one Will that be faster or slower  <br />
<br />- Links/Options/Actions (whatever we're calling them) should also have sequence defined: would be an easy/bulletproof way to assign option\_option() call: position in the array is number on the PIVOTE controller (don't like counting on parse order, even if we wrote it, it's non-xml compliant) <br />
<br /> holodeck needs to be silent: mute it for now (right click), long term solution is to ask author to notecard-parameterize majel barrett's voice! MUST TURN OFF END SIMULATION  <br />
<br />more faking the server with static XML: recreate the tester xml files in SLDataCollection, maybe one for each number on the PIVOTE board: simple but a must for testing: <br />
<br />rotation of key strings (persist): <br />
<br />oldkey = new key; (patient and player) <br />
<br />old NodeId = newNodeID; // back button depends on this, or will this be breadcrumb, ie array <br />
<br />javascript: font size on SLDataCollection\show.html, both for nodes and for options, should be linked to length of string to display and/or one of the parameters passed <br />
<br />django:  <br />
<br />our hello world with django will be displaying the SELECT fields in the node editor pane for the sl object library (every object asset attribute form field may need to allow for lang-specific clone) <br />
<br />will define Ariadne web UI as we learn django, working backwards from Second Life's possible assets: <br />
<br /><i>types inherited from Medbiquitous MVP/VPD/PIVOTE model, and their parameters:</i> <br />
<table cellpadding='0' border='1' cellspacing='0'>
<blockquote><tr>
<blockquote><td width='141' valign='top'><br />Text </td>
<td width='506' valign='top'><br />text to display, which devices get this text (parcel viewers,<br>
<blockquote>poseballs, mannequin) </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='141' valign='top'><br />Media </td>
<td width='506' valign='top'><br />movie (.mov) URL, mime/type </td>
</blockquote></tr>
<tr>
<blockquote><td width='141' valign='top'><br />Image </td>
<td width='506' valign='top'><br />URL, type (can we animate gifs) </td>
</blockquote></tr>
</table>
<br /><i>Our NEW Second Life-specific assets and<br>
their parameters:</i>
<table cellpadding='0' border='1' cellspacing='0'>
<tr>
<blockquote><td width='142' valign='top'><br />Animation </td>
<td width='505' valign='top'><br />object name, target avatar/objet, duration </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />Sound </td>
<td width='505' valign='top'><br />wav object's name, volume, duration </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />HUD </td>
<td width='505' valign='top'><br />hud name, where to wear it, current hud state (could be node val of<br>
<blockquote>the 'hud xml tree')  </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />Chat </td>
<td width='505' valign='top'><br />what to say, which channel (very powerful, example: if included at<br>
<blockquote>the end of a InnerSequence, can tell the controller to choose the next link<br>
('option') : this simulates a non-interactive sequence, or a “movie”.)  </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />IM </td>
<td width='505' valign='top'><br />what to say, avatar's name </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />AvatarCommand </td>
<td width='505' valign='top'><br />type (Point, Move... so far), avatar's name, target object/avatar's<br>
<blockquote>name, adjacence (where to move relative to the target object) OR vector to<br>
move/point to (if any). may have too many values here to parse, might need<br>
2 sepreate assets: MoveCmd & PointCmd) </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />InnerSequence </td>
<td width='505' valign='top'><br />comma-delimited object name & duration pairing, ie. “karate99~3,austin~2”<br>
<blockquote>(but careful! object name can be another InnerSequence!), isSynchronized boolean </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />Package </td>
<td width='505' valign='top'><br />package name, target avatar's name OR vector to rez it to (if any)  </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />BodyPart </td>
<td width='505' valign='top'><br />part name, avatar's name  </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />Object </td>
<td width='505' valign='top'><br />object name, target avatar's name OR vector to rez it to (if any)  </td>
</blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />InnerGame </td>
<td width='505' valign='top'><br />server type (openlabyrinth, sloodle, pivote, cascadingllDialogs ), external URL (username & pwd): can OL support nested<br>
<blockquote>labyrinths easily If so, we can eliminate api=list (from PIVOTE code), as<br>
now the list IS a labyrinth (with an InnerGame as each of the options!) </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />CameraControl </td>
<td width='505' valign='top'><br />target avatar, name of one of angles in player's Camera HUD script<br>
<blockquote>(24 presets, plus 3 fav slots) </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />Gesture </td>
<td width='505' valign='top'><br />not supported, but can be mimicked with a combination of Chat,<br>
<blockquote>Animation and Sound (ours can even be sequenced!) </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />PoseballCmd </td>
<td width='505' valign='top'><br />not supported, (can be accomplished with Chat asset): command to<br>
<blockquote>shout to the poseball, channel (ie. we currently shout “hide” or “show” to<br>
toggle transparent-phantom mode of the poseball (any other text shouted is<br>
interpreted as the text to display floating above the poseball, but as with<br>
other chatbot driven approaches, this can be much more powerful. Note: you<br>
must change this value before “show” command), might we also allow an<br>
(optional) vector to be shouted, to be able to move it while it's hiding... </td>
</blockquote></blockquote></tr>
<tr>
<blockquote><td width='142' valign='top'><br />VPDCommand </td>
<td width='505' valign='top'><br />not supported, see above </td>
</blockquote></tr>
</table>
<br />therefore, ALL of these assets must be presented (their parameters also, in a conditional way), right underneath the current tinyMCE text area object in the OL-HSVO node editor pane (that is the VPDText, in fact! But it cannot be HTML... too superfluous, will make our xml too long!).  <br />
<br />(...all this is the server component of the Ariadne plug-in. This will indeed make a django module with a multitude of form elements!) <br />
<br />Misc. functions/routines needed: <br />
<br />finding out who is the patient (in order of priority):  <br />
<br />1-named (in notecard)<br>
<br />2-assigned at run time by llDialog presented to the player<br>
<br />3-find patient algorithm within x radius:<br>
<br />closest to the player/controller wins, or present this list to the player with llDialog <br />
<br />in any case, get key, set global vars<br>
<br />if no other players, rez pivote mannequin, move to MUMOFF states<br>
<br />parcel rights checking for owner when installing media relay on parcel<br>
<br />can we check max prims allowed<br>
<br />getObjectPositionVector(object/avatar name): returns the coords at the centre of the object or avatar if so if (Y coord + avatar size lower than the floor && in HORIZONS.transparentShellMode)<br>
<br />return Z coords just above the floor of the current shell<br>
<br />else<br>
<br />return default “centered”value<br>
<br />need breadcrumb function: must keep all nodes traversed. Back button depends on it<br>
<br />isPlayerInRange()<br>
<br />if MUM<br>
<br />isPatientInRange() <br />
<br />need script to drag onto every object in a scene (along with AVRS_Scene) at build-time which sends object name, type (and relative coords, if needed) to Ariadne  saved to DB, to be retrieved when in OL node editor pane<br>
<br />need to minimize and/or control the cross talk of chatting both in and out of world: <br />
<br />telemetry HUDs (prob all HUDs)<br>
<br />poseballs<br>
<br />bracelets<br>
<br />controller<br>
<br />mannequin(s)<br>
<br />media relay: VERY busy & important: constantly changing the parcel's media URL <br />
<br />secure the chat lines: anyone can chat on any line, so the lsl code must check that the speaker's name/key matches that of an allowed object (we know these objects at build-time) <br />
<br />replicate the telemetry http_requests to :<br>
<br />- bracelet<br>
<br />- controller<br>
<br />- HUDS (eg. would we like to know which charts the tutor prefers, which objects or scenes the builder uses most) <br />
<br />animator:<br>
<br />get sequence from xml, duration if any (currently the 'value' param)<br>
<br />create sequence tree from nested InnerSequence assets (if any)<br>
<br />create second temp array of sequence-able objs<br>
<br />stopOldAnim(), startNewAnim()<br>
<br />if isSynchronized, invoke solop server script <br /><br />
<br />On the immediate Horizon (TODO): <br />
<br />have not tried <b>anything</b> with the PIVOTE mannequin:  <br />
<br />first step, make the commands you shout to it configurable it it's notecard, and optionally overridden by chat. Currently, the name of the node shouted by the mannequin is hard coded, there should be a way by chat-assign each anchor point a node name, ie. when in build mode, touching the mannequin should llDialog the builder to assign it a node name (pulled at run-time by an http_request to Ariadne) <br />
<br />second, chatbot should be integrated on every prim! and/or the whole set! meaning, the whole set can rotate/change color, say something, or just individual prims... lots of potential. implement by prefixing the lsl calls with ALL if not root prim (ie if called from child prim) to make scope of the call higher, at the root prim-set level <br />
<br />bracelet vending machine: gives mandatory bracelet to players, also HUDs<br>
<br />this will be a HUD intensive system.. and why not! great interaction! but can get cluttered.. so there is a need for a hud to morph in place, so maybe a hierarchical container structure (see lsl wiki) containing:  <br />
<br />tutor hud (PIVOTE ppl on that right now) <br />
<br />builder hud (horizons HUD): should easily rez the scene crates for you , can you rename objects with their correct prefixes: YES! shells, huds, crates (if no, use description field) <br />
<br />NOSM_HUD:<br>
<br />- start with Sloodle HUD<br>
<br />- student HUD, essentially<br>
<br />- can it be packaged with bracelet need such an attached object to move the guy<br>
<br />- other school utilities: chairs, a laser pointer, drag n drop prez boards,<br>
<br />ZHAO-like movements hud<br>
<br />OL Admin hud: have toggle switch for : server debug statements, logging level (a la log4j), ability to call a node by name <br />
<br />cascading hud display:<br>
<br />start with a root HUD, i'm calling mine zHUDbury hm... you should be able to name it, actually (there's work done on other board in viewer area: can that be worn as a HUD), and have it serve you the current HUD as you traverse down the tree, and shows you builder-tutor-stats-olAdmin HUD in-place <br />
<br />cascadingllDialogs needs a tree of llDialogcalls,<br>
<br />root HUD need tree of possible HUDs to display,<br>
<br />InnerSequence needs recursive capabilities...<br>
<br />this all begs one common and generic tree set maker! <br /><br />
<br />memory mgt:<br>
<br />scripts sending msgs to one another: linked_message + if llGiveInventory(<a href='SCRIPT.md'>SCRIPT</a>): will be able to divy up the KB usage: can start with parser. Can maybe also write global var persistence store (xml in once, then on every subsequent call, name in  value out) <br />
<br />Skins:<br>
<br />configurable skins for the actual in-world controller board: pretty easy to do....<br>
<br />one might be white oval with half-sphere buttons, one should be transparent-phantom!<br>
<br />maybe different for each language<br>
<br />rotating cube <br /><br />
<br />when we spread the assets out across a timeline, we're gonna need callback routines for poseballs, animations, sounds, anything that supports duration, on_terminated(), we launch next asset in the sequence (poseball ends not when user stands up, but when “duration” value times out)<br>
<br />mandatory asset: do we always need text hm... actually, should we make VPDTEXT mandatory <br /><br />
<br />have controller script be more state driven! even have multilevel states:<br>
<br />HDECKON_MUMON_GAME3_INPROGRESS<br>
<br />HDECKON_MUMON_GAME3_LOADCFG<br>
<br />HDECKON_MUMON_GAME3_WAITING<br>
<br />HDECKON_MUMON_GAME3_PAUSED (maybe needed for hsvo should try to change to this state by xml-rpc)<br>
<br />(mum = multi-player mode) <br /><br />
<br />log.4j type of functionality for telemetry, (and maybe all code!): warn; info; error, etc.... linear escalation of msg details <br />
<br />counters changing assets/node in world (ie. pivote v2): why do we need that should just be calling nodes <br />
<br />help: use the holodeck to create: walkthru/tutorial, with arrows,   fun stuff, text, etc: , maybe a context-sensitive help button! Use movie technique described above<br>
<br />therefore, will need multiple parcel viewer screens to be controlled, not just pivote: dtext=DEVICE <br />
<br />bracelet:<br>
<br />has ability to capture basic control interaction (navigation arrows, left mouse click), therefore we can get telemetry capturing, and this can also be good model for “device-side” parameter capturing, one that could eventually be the basis for a X3D routine for the haptics <br />
<br />question: can we rotate the avatar towards an object can we rotate an avatar at all if no, what about rotating the room <br /> <br />
<br />note: terms cited in this text should be used in function/var naming (i like MUM) <br />
<br />gonna have to do:<br>
<br />&lt;pseudo code&gt;<br>
<br />on every student's bracelet or HUD<br>
<br />on inventory change<br>
<br />loop all items in NOSM folders,<br>
<br />find the texture you just dispatched<br>
<br />prompt the user to drag that texture to the screen (the best we can do)<br>
<br />Camera:<br>
<br />need an SL camera-follower module on the player HUD<br>
<br />one that follows player OR patient,<br>
<br />can snap to 24 different angles around a circular vector, with the avatar at it's centre, can set height, pitch, etc...<br>
<br />(will be needed for FRAPS recording, ie. “machinima”) <br />
<br />we can call the angle/views buttons actual relevant medical names, surgeon's view<br>
<br />assistant surgeon's view, anesthesiologist's view, student @ a clinic (we create a HUDdle grouping of students and their cameras) <br />
<br />combine the use of the podium-platform widget (which we already have, putting your avatar into the default Change Appearance stance), along with a different camera angle/zoom, and you can get the SL user to point at their own avatar to show where the pain is (almost like building an actual functional self-diagnostic tool!). Then the camera zooms in on the part in question, and changes texture (but that doesn't seem possible!) <br /><br />
<br />Nice to haves:<br>
<br />investigate more thoroughly recursive object containment: very powerful feature:<br>
<br />some things come to mind:<br>
<br />can be used as a persistence store, object parameters (name, description, author, etc):<br>
<br />can then even clone/rez the tree!!!<br>
<br />scripts (up to 16) at each level of containment: if you build them to find and call each other recursively... oh, that may get nutty...<br>
</blockquote>> <br />
<br />Real-time labyrinth statistical feedback:
<br />the google chart option is almost too easy not to implement: might also try server side api calling: local relay with static URL
<br />django page that takes post data and makes a QS for google chart API &lt;IMG REF&gt;
<br />new asset in-world: google chart! sweet
<br />configure the X-Y axis in Ariadne!!!
<br />point it to some columns
<br />or a simple reg exp (including actual reg exp!)
<br />if external textures work, that's how we create a chart HUD.  <br />
<br />we should have an in-world olAdmin HUD, or shout commands over chat lines.. create our own syntax for parameter changing at node/labyrinth/hsvo level
<br />inter-parcel configuration dispatching: must be able to see/tutor/build for multiple parcels:
<br />maybe have a parcel smudging routine as part of admin hud: name 3 prims, scripts will interpolate bounded box from them (only 3 as scene & shells are assumed to be level)  <br />
<br />want to be able to delegate a node to another avatar (like calling a friend), when that user clicks the proper controller button, the active player should be prompted: sanchoRelaxo Algoma wants you to pick x, do you agree along with the ability to filter options (by calling the node again, but passing a filter option in query string, naming the options to remove from the list), we can mimic the millionaire game format gFilter was implemented in PIVOTE, but not used <br />
<br />inter-parcel configuration dispatching: must be able to see tutor/build/admin data in HUD for multiple parcels:
<br />maybe have a parcel smudging routine as part of admin hud: name 3 prims, scripts will interpolate bounded box from them (only 3 as scene & shells are assumed to be level)  <br /><br />
<br />from the wiki entry for Dragon's Lair:
<br />“ the player executes an action by selecting a direction or pressing the (sword) button with correct timing....instead of controlling the character's actions directly, players control his reflexes...” hmmm... <br /><br />
<br />language API: soooo close to implementing.... but needs work: if we get any close to a ojibway/cree/huron/algonquin setup, we will be kings of youTUBE... <br /><br />
<br />should use en\_ca, fr\_ca, etc... standard locale notation
<br />this should also apply to:
<br />VPDtext
<br />VPDMedia
<br />VPDImage
<br />poseball labels
<br />Sound
<br />HUDs
<br />etc...
<br />...and controller skins!!!! <br /><br />
<br />lang picking's gotta work like joomfish: if defined for this lang, show it, if none, show default
<br />q: can we control HUD dimensions the same way we rez prims can we tile HUDs in an easy way a stats HUD could have multiple charts... that is just an html page, with multiple google chart calls! <br />
<br />new asset types:
<br />AnimatedObject
<br />Presentation (same as InnerSequence of VPDImages, but with front-back controls)
<br />Chart: can we have Hans Rosling-style real-time movement of charts as results are coming in (means polling)
<br />AnimatedGIFtype=dicom...
<br />ShapedView/Image (via Batik or something similar, skew the image to be spherical, concave, convex, cone, get all parameters in Ariadne etc. can maybe do basic molding of images around avatar too, ability to “dress” the player: surgical apron, patient gown, lab coat, etc <br /><br /><br />
<br />  ...and now the cherry:
<br />The Facebook App: The Second Life Content Creator
<br />tell the FB audience: want your art/photo to be in a Second Life virtual doctor-patient scenario how about having your kids doodlings be in a video game, for the entire planet to see (and right away!) want your diagrams to be used in a real-life medical classroom
<br />we tell them to take a 8x11 paper, divide into 6 squares (easy to fold it that way), draw in each square the scene we will ask of you (vomiting, face down dead, epileptic, broken arm, drunk, on and on....), and upload scan into FB App.
<br />Then our server, which is hosting this app, retrieves image, cuts into 6 (or 12 if we do both sides), again with something like Batik, and will include these sets as image options to choose in Ariadne in the node editor pane (select by artist name, set-name, etc), and so we have our tutor define which artist's work he'd like to see on screen, they snap in place due to filename (or some other key)
<br />VPDImage url = SLFacebookAppuser=suzanna&uploaded=YellowDyingPatient.jpg
<br />also , we can ostensibly get relevant photos by tagging for an image in Ariadne: we have the page that goes to flicker or an aggregation service, and then it extrapolates content for our scene, creates a JPG set for us to choose from in Ariadne... <br /><br />
ok.. maybe not.... <br /><br />
<br />new Asset type: TaggedMedia
<br />TaggedImagesrc=flickr&tags=surgery,heart,bypass
<br />TaggedMediasrc=...&tags=vomit,drink,wasted
<br />  if fact we should be able to snap in many aggregator (and other useful) services by simply defining the api/query string in Ariadne, and presenting the calculated image set result back to the labyrinth editor, so that he/she can choose the node's VPDImage.<br /><br />
<br /><b>What's next:</b> <br />
<br />i see this text as being useful for many things: <br />
<br />beginnings of slideshow-powerpoint-presentation, or youtube movie-presentation script (with voice-over walk through, we just change all verbs from future to past tense, will be called NOSM: The Video Game or openLabyrinth & Ariadne: How we wrote a medical gaming engine.)  <br />
<br />also, this can be the basis for our first google code issues list, ranked by severity/preference... and then we'll hammer at it, give it a good go, and what ever is left to do, we leave it “out there” for anyone to continue...
<br />but for now, this can be used as a software feature wish list, one that will no doubt only be partially attained when all is said and done.... <br /><br />