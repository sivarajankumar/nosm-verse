> <br />needed: Object/prim storage bin that will send object UUID and label to server, so we know which objects are available for which Asset Node (Device in Pivote Manager)
> <br />Ie. each DAM node must be able to associate a particular asset with an SL device ID
> <br />It is up to the exercise designer to script an SL object to listen for that device ID and the take the appropriate action to display the asset (fo instance text could be displayed as SL float text, or an image (which is referenced as a URL) displayed by activating the media texture on a device. - PIVOTE Editor User Manual 0a.pdf
> <br />holodeck dispatches to media channel
> <br />xml returned from OL includes:
> <br />sessionid
> <br />current node: text to display, options/links, media(if any)
> <br />send sessid id and node id:
> <br />service should retrieve a UUID of a holodeck scene
> <br />a node can/should have but one state, one scene
> <br />when the user is traversing the nodal decision tree of the game, a node doesn't necessarily need to shift holodeck states. in fact, most of the time (a hospital ward, an exam room), many nodes use the same scene. however, the holodeck must still have the ability to dynamically shift scene at every node.
> <br />scene building should be assigned to actual professionals who can lay out a hospital. NOT the ellisDon guys, but someone down at that clusterfuck can give us a professional opinion, say, when watching sanchorelaxo moving bits around @ nossum regional hospital, all being done on the mac right in front of them…  well, here's hoping they would throw in their two cents as it's being done....
> <br />useful SL service:
> metaversedata:
> <br /> http://marcuswelz.com/2007/12/26/metaversedata-beta-starts
> <br />4
> > services:

> <br /> http://metaversedata.com/services/mex
> <br />http://metaversedata.com/services/avatars
> <br />http://metaversedata.com/services/regions
> <br />http://metaversedata.com/services/textures

> <br />Other code:
> <br />
> pseudo lsl over chat<br /> http://wiki.secondlife.com/wiki/Chatbot
> <br /> http://cvs.drupal.org/viewvc.py/drupal/contributions/modules/slcontact_xt/lsl/server/server.lsl?revision=1.1
> <br />server.lsl: passes taskname in QS to server
> <br /> http://joomlacode.org/gf/project/slcontact
> <br /> http://wiki.secondlife.com/wiki/Multi_Item_Rezzer
> <br /> http://wiki.secondlife.com/wiki/User:Jesse_Barnett#Scripts_.26_Snippets
> <br /> edusim
> <br /> opensimulator
> <br /> sloodle/moodle
> <br /> Troy McConaghy's Open Source Holo-Emitter
> <br />Scene Rezzer
> <br />Literary Holodeck Project
> <br />http://cvs.drupal.org/viewvc.py/drupal/contributions/modules/slfeed/slfeed.module?revision=1.1
> <br />can the OL 'avatar' be the SL patient
> <br />if so:
> <br />assign that guy some skins!
> <br />Turn a script on or off
> <br />play a sound
> <br />gesture/animation
> <br />pass another link message
> <br />read
> > the associated notecard

> <br />put
> > on or take off some clothing

> <br />change a texture
> <br /> http://blog.worldvillage.com/games/a_step_up_in_second_life_scripting_getting_flexible_with_llgetinventory.html
> <br />llDialog
> <br />llVolumeDetect
> <br />llAttachToAvatar
> <br />llDetachFromAvatar
> <br />llGetPermissions
> <br />llRequestPermissions
> <br />Labyrinth Admin Screen:
> <br />
> needs isSLEnabledCHECKBOX
> <br />
> if checked
> <br />pick a scene: 

&lt;api-call-to-horizons&gt;

 SELECT  (for now hardcode aquarium, asylum and movie)
> <br />
> Node
> > Admin Screen:

> <br />SL
> > scene:

> <br />(current labyrinth scene selection is displayed and checked by default)
> <br />if unchecked
> <br />pick a scene: 

&lt;api-call-to-horizons&gt;

SELECT  (for now hardcode aquarium, asylum and movie)
> <br />
> quickstart (load the first scene) CHECKBOX
> <br />
> (however, an empty holodeck is a perfect opportunity for credential checking, team intros, if possible)
> <br />Hacking Say and Reviving ELIZA: Lessons from Virtual Environments By Rochelle Mazar and Jason Nolan
> <br /> http://innovateonline.info/index.php?view=article&id=547
> <br />in a MOO space, the script that controls the user's ability to speak resides not with the user but with the space itself. This is different from other environments, like Second Life, where communicative functions reside with the user's avatar.... but... The owner of a room can modify how speech is represented, for instance, or even decide if it is possible at all. While such control over the say script is conceivably problematic, it allows for tremendous creativity and provides great teaching and learning opportunities.
> <br />Using Second Life with Learning-Disabled Students in Higher Education By Stephanie McKinney, et al.
> <br /> http://innovateonline.info/index.php?view=article&id=573

> <br />Knowledge-Driven
> > Design of Virtual Patient Simulations By Victor Vergara, et al.

> <br />http://innovateonline.info/index.php?view=article&id=579
> <br />Blitz's
> > TruSim medical-triage simulator

> <br />



> <br />According to dox, we should place the
> > holodeck button a few meters above ground.    If it is on another platform
> > or in a building, put the AVRS Cloaking script in all of your root
> > prims. This will cause them to vanish temporarily when certain scenes, like
> > when the panoramas are rezzed. (i cannot find AVRS Cloaking script! -R.S.)

> <br />script will listen for msgs.
> <br />link prims to HORIZONS itself, or
> > install your own script into it, and use these messages for your own use:



> <br />holodeck broadcasts the name of the
> > rezzed scene or shell, and a texture key, on channel 42010.    The format
> > is:

> <br />HORIZONS::scene name::texture uuid
> <br />When a scene is cleared, the message
> > sent is:

> <br />HORIZONS::cleared
> <br />Parcel primitive
> > limitation:

> <br />About Land -> Objects tab,  under
> > primitives parcel supports.

> <br /> As holodeck rezzes and derezzes
> > objects dynamically, hitting this threshold is a very real possibility, from
> > one scene to the next… it is already happening in the viewers area on nossum
> > island…. It supports only 146 prims (this limitation is tied to parcel size)

> <br />
> http://wiki.secondlife.com/wiki/Holodeck <br />
> http://wiki.secondlife.com/wiki/Open_Source_Holodeck <br />
> The Holodeck by Loki Clifton
> <br />Horizons by Cheshyr Pontchartrain
> <br />HyperCube by D-VTech
> <br />Super Sofa by LayZeeBones
> <br />Holodeck by Soulmates Creations
> <br />Paradise Blanket by OctoberWerks
> <br />HoloRez by HoloRez Rang
> <br />Skyboxer by Ethereal Fremont
> <br />Primitizer by Revolution Parenti
> <br />The Titan by Jack Hathor
> <br />Room Switch by Loki Ball
> <br />The Green Wonder by Tina Freund
> <br />Holodeck by Professor Eisenberg (Panocube)
> <br />The Virtual Reality Room by Stephane Zugzwang (Panocube)
> <br />Krull's VR Room System
> <br />Mobius Box by Fox Absolute
> <br />DRUID Holodeck by Darwin Recreant and Ui Beam
> <br />
> The Ultimate Virtual Reality Holodeck by Vander Reich & RichSz
> > Rexen(Panocube) R&R-VR-HOLODECK-INSTRUCTIONS<br />
> > SkyBox Lab HoloDeck SkyMaps by ThoseGuys Footmen

> <br />
> AWESOME BALLS 3D
> > Environments - HoloDecks & SkyMaps<br />
> > llHTTPRequest

> <br />Data passed in the HTTP request is
> > limited only by the script's free memory. Remember that a script is limited to
    1. K of memory. In LSL, function arguments are passed by value, so before
> > issuing the HTTP request the script must have an amount of free memory with at
> > least the size of the arguments.

> <br />Data passed in the HTTP query response
> > is limited to 2049 bytes.

> <br />A user should not query more than one
> > query per second. If a script violates this limit, it gets
> > throttled for a period of time.

> <br />The HTTP call is asynchronous - after
> > the script issues the call, the script keeps running. Only when the response is
> > returned, the script is informed (using a special event) that it has an HTTP
> > response. A consequence of this is the fact that there is no guaranty that
> > several HTTP responses will get back in the same order their corresponding
> > requests were issued - the script should handle the bookkeeping by
> > itself.

> <br />
> http://www.cs.tau.ac.il/~shalitaa/courses/mpve-workshop/Wikka/wikka.php?wakka=OutInCommunication <br />
> - getting around 2k http limit in SL:
> <br />generating a page with inpage links that are to be expected
> <br />generating a bunch of 2k pages on the fly
> <br />Working with llHTTPRequest:
> <br />
> http://sl-devcorner.blogspot.com/2007/03/display-external-xml-resources-on.html <br />
> http://ja.pastebin.ca/1041757?srch=prims <br />
> http://robsmart.co.uk/tag/c/ <br />
> http://robsmart.co.uk/tag/scripting/ <br />
> http://blogs.ipona.com/chris/archive/2008/02/17/8465.aspx <br />
> http://www.orient-lodge.com/node/2523 <br />
> http://eightbar.co.uk/2006/05/25/outbound-communications-from-second-life/ <br />
> http://community.pachube.com/?q=node/82 <br />
> http://w-hat.com/libhttpdb <br />
> http://sl-devcorner.blogspot.com/2007/03/second-life-twitter.html <br />
> http://secondlife.wikia.com/wiki/WikiHUD_(Source) <br />
> http://secondloop.wordpress.com/2008/12/16/motion-capture-second-life-script/ <br />
> http://expobadge.com/dldev/partners/file/Whitepaper-FusingOracleAndSecondLife.pdf <br />
> http://www.sparticarroll.com/Pandora+Chatbot.ashx <br />
> http://blogs.msdn.com/jrule/archive/2009/04/22/using-cloud-services-from-second-life.aspx <br />
> http://www.cl.cam.ac.uk/~mm753/papers/hotemnets08.pdf <br />
> http://www.ernw.de/content/e15/e26/e1018/Hacking2ndLife-en_ger.pdf <br />
> http://ukpmc.ac.uk/articlerender.cgi?artid=1242340 <br />
> http://www.yuri.tijerino.com/nucleus/index.php <br />
> http://drupal.org/node/346964 <br />
> http://gwynethllewelyn.net/2007/08/11/figuring-out-your-online-status-revisited/ <br />
> http://www.hilarymason.com/blog/lsl-newspaper-stand-pull-data-from-an-api-and-display-it-in-second-life/comment-page-1/ <br />
> sss: rss in Sl (crude xml parser)
> <br />
> http://www.lslwiki.net/lslwiki/wakka.php?wakka=exchangeSecondSimpleSyndication <br />
> libsecondlife: http://code.google.com/p/feathertail/wiki/ProxyBot
> > --> HTTP in/out of world

> <br />
> http://code.google.com/p/feathertail/wiki/ObjectOverlord --> tracking
> <br />
> Services that use
> > llHTTPRequest:

> <br />
> http://w-hat.com/httpdb <br />
> http://w-hat.com/name2key <br />
> Silo (by Zero Linden), written in PHP




