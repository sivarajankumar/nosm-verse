<br />CICP Data Object (for use with slcicp, greenphosphor)<br />

<br />integer freePrimsOnParcel = llGetParcelMaxPrims() - llGetParcelPrimCount()
<br />
<br />http://w-hat.com/httpdb  <br />
<br />openCollar<br />
> locking attachments, <br />
> http://tiessas-sl-code.googlecode.com/svn/trunk/Controller/ideas.txt <br />
> http://opencuffs.googlecode.com/svn/trunk/lsl/AO/betas/ <br />
> http://simondemand.ec29.com/index.html <br />
> http://code.google.com/p/jqcommons/source/browse/#svn/trunk


<br />chattable api:<br />
> storage script:
<br />storagefeed: operation, name,  value:<br />
> tranlslate feed: channel, sourceLang,  destLang
<br />gameOLfeed sessionid, mnode id
<br />mediafeed: depends, varies
<br />gameAriadnefeed<br />
> asset vals for thsi node<br />
> possible asset attribs<br />
> other asset names (type assist AJAX for the asset name field)
<br />
<br />GoogleAppEngineFeed:<br />
> Datastore API<br />
> Images API<br />
> Mail API<br />
> Memcache API<br />
> URL Fetch API<br />
> Users API
<br />Authentication<br />
> Google Base<br />
> Calendar<br />
> Contacts<br />
> Documents<br />
> Picasa Web Albums<br />
> Spreadsheets<br />
> YouTube
<br />GoggleData: need AuthSub user name and password <br />
> Base Data API,<br />
> base Service Name:<br />
> GoogleInc-basejsguide-1.0<br />
> http://www.google.com/base/feeds/items
<br /> Blogger Data API,<br />
> Calendar Data API,<br />
> Contacts Data API, <br />
> Finance Data API.<br />
> Google Calendar<br />
> Google Base Items Mapper<br />
> Blogger<br />
> llsd for parcel stats
<br />w-hat<br />
> appspot?<br />
> google datastore<br />
> google spreadsheets<br />
> URL provided by the sharing dialogs, http://spreadsheets.google.com/pub?key=rocYeX7smxw1E2FFdhwsiiQ&output=csv&gid=0 <br />

<br />
<br />for all players, given player, palyers with roles,
ZHAO Engine: llMessageLinked(LINK\_SET, 0, ZHAO\_AOON, NULL\_KEY);
<br />ZHAO\_LOCK<br />
> ZHAO\_UNLOCK<br />
> ZHAO\_PAUSE<br />
> ZHAO\_UNPAUSE<br />
> ZHAO\_STANDOFF<br />
> ZHAO\_STANDON<br />
> ZHAO\_MENU<br />
> ZHAO\_AOSHOW<br />
> ZHAO\_AOHIDE
<br />Sitting on Ground, Sitting, Striding, Crouching, CrouchWalking,<br />
> Soft Landing, Standing Up, Falling Down, Hovering Down, Hovering Up,<br />
> FlyingSlow, Flying, Hovering, Jumping, PreJumping, Running,<br />
> Turning Right, Turning Left, Walking, Landing, Standing
<br />for flying and hovering: isUnderwater?
<br />multiAnimTokenIndexes:<br />
> Sitting On Ground<br />
> Sitting<br />
> Walking<br />
> Standing<br />

<br />
<br />what http://w-hat.com/httpdb can be:
<br />SL object storage<br />
> player List<br />
> asset type list<br />
> Name2key Database<br />
> Server Tracker <br />
> XML-RPC Key Tracker<br />
> HTTPDB\_SAVE<br />
> HTTPDB\_LOAD <br />
> HTTPDB\_DELETE
<br />QHUD tracks the location, land permissions (build/script/push/entry), and status (standing/flying/sitting) of every avatar in the region.<br />
> QRadar (free with QHUD) shows the location of everyone in the region, complete with the direction they are facing, weapons they are using, distance, height, and more in a friendly colour coded map.<br />

<br />FlickImageSet<br />
> search<br />
> FlickrXplorer
<br />
<br />extend charts:
<br />geomapped data: name value pairing of countty=querystringval<br />
> specialvisualizations<br />
> (google gauge, google meter, in-world 3d pie chart, in-world 3d bar chart)
<br />isLiveFeed boolean
<br />weatherSystem (particle manifestation with live weather feed)<br />
> http://weather.gov.xml<br />
> http://weather.aero<br />
> weatheroffice.gc.ca<br />
> http://rssweather/xml
<br />particleSystem<br />
> if isLiveFeed=true<br />
> change which parameters: name=value pairing<br />
> livegooglechart<br />
> if isLiveFeed=true gChart doc<br />
> add support for motionchart (will it work in SL? if svg, prob not, but will in the near future)<br />
> inworld3dchart (pie only), make it listen to a feed, and poll it at x interval
<br />votingmachine(with bar chart)<br />
> ask the current node question to the audience, #  of max options (6) is the width of the bar chart <br />
> no need to return results, strictly for group input for the player (or do we send to telemetry?)<br />

<br />the output gtable of chart data:
<br /> Static Data (current field)<br />
> User Telemetry<br />
> avg fly time<br />
> avg sit time<br />
> avg characters chatted<br />
> parcel's most popular spot<br />
> plot labyrinth traversals by most likely<br />

<br /> ParcelStats<br />
> in world-building of url with llParcelSettings cmd
<br /> OL <br />
> counter history for this labyrinth <br />
> current counter val (as google gauge/meter, ie, against max val)
<br /> avg time taken for this node for all/this user(s) <br />
> avg counter values for this node/labyrinth<br />
> <br />
> Ariadne <br />
> asset types currently most used<br />
> avg sequence duration<br />
> inventory Count of all posted SL objects, by Type<br />

<br /> create gauge for sequence page that displays visually how close we are to the 2048 char limit <br />

<br />get list builder field set working for a sequence<br />
> use jquery to parse xml, parse name/value pairing, populate fields<br />
> if any asset gets added, removed, re-named, re-ordered (and eventually when it has has a different duration), recompile innerSequence <br />

<br />ParcelMediaAudio!!!!! forgot it!<br />
> could also be shouctast, others<br />
> install shoucast widget, hack so that it sets url instead of launching player
<br />
<br />Universal Translator: Translate most descriptive fields into french. at least.<br />
> (already have in-world POSLO chatting up francais on channel 7)<br />
> jquery: $(body).translate(en)<br />

<br />
<br />http://www.sparticarroll.com/Pandora+Chatbot.ashx
<br />AIML-enabled BOT URL: ALICE base chatbot hosted at Pandorabots.com: http://www.pandorabots.com/botmaster/en/create <br />
> PandoraBot: http://www.pandorabots.com/pandora/talk-xml?botid=+botid+&input=+llEscapeURL(msg)+&custid= <br />
> bot id<br />
> customerid<br />
> or<br />
> AIML text<br />
> http://www.darooster.net/mythalice/tools/aimlbuilder/aimlbuilder.html<br />
> http://www.pandorabots.com/botmaster/en/aiml-converter.html<br />
> http://aiml.harrybailey.com/beta/
<br />LibraryLindenAIML output channel: default public/IM
<br />other chart type:<br />
> the output gtable of a <br />
> SPARQL query: http://virtuoso.deri.ie/sparql<br />
> OLQuery
<br />http://sourceforge.net/projects/aimlbot/files/ to do it in c#<br />

<br />isGTranslatable : can wrap the text value and/or name in a call to google translate, select language<br />
> isXSLTransformable: must be valid xml, if not, extract name value/target/name fields, build simple xml <br />
> isCallBackable so when the controller waits for this assettype to end this is what to say:  hasduration assets, node for mannequin<br />
> isVirtualPatientData if so maybe stop everything (also point, make sound, shoved to play position) and show/play patient data<br />

<br />SLChat<br />
> send message, all objects<br />
> pseudo lsl that is in chatbot script (from wiki)
<br />
<br />SLObjects:<br />
> Misc:<br />
> nossum utils<br />
> Chair(s)<br />
> skubble (sky hubs)<br />
> camp fire<br />
> universal translator, default en - fr<br />
> nosm HUD<br />
> HeartMonitor w/ LiveFeed<br />

<br />Build Utils<br />
> platform<br />
> grid<br />
> stand<br />
> backpack
<br />Actions:<br />
> move<br />
> throw an object<br />
> list attachable objects only <br />
> will make prim physical and throw it irrelevant of current position<br />
> fly to current camera angle<br />
> <br />
> Special:<br />
> Avatar cloak (invisibility)<br />
> Turn personal light on/off<br />
> Personal shoving bubble<br />
> Crowd displacement<br />
> Draw Telemetry Replay Line (maybe in red) for avatar(s)
<br />RPC<br />
> parcel reports (land permissions, parcel prim count)<br />
> player Stats Radar (current users standing in parcel) <br />

<br />HeartMonitor<br />
> YouTube<br />
> Radio
<br />http://www.sparticarroll.com/Pandora+Chatbot.ashx
<br /> <br />
> PDF representations of archived<br />
> Structured Report Documents requested by IHE Retrieve Information for Display <br />
> (RID) Services
<br />JPEG representations<br />
> of archived images requested by Web Access to DICOM-persistent Objects (WADO) <br />


<br />
<br />javascript:getVisibleFieldsValues()<br />

<br /> dcm4chee-mysql-2.14.6\doc\INSTALL<br />
> jboss etc...
<br />jquery dimensions<br />
> scrolltomin<br />
> tablesorter<br />
> dsg.main.ajax.js<br />
> bgiframe<br />
> maskedinput
<br />form.js<br />
> treeview<br />

<br />XmlDocument xmldoc = new XmlDocument();<br />
> xmldoc.Load(xml);<br />
> System.IO.StringReader sr = new System.IO.StringReader(xmldoc.InnerXml);<br />
> XPathDocument doc = new XPathDocument(sr);<br />
> XPathNavigator nav = doc.CreateNavigator(); <br />
> XPathExpression ex = nav.Compile(@nodePath);<br />
> return ex.Value<br />

<br />SNORQL