integer gUseNewParser = TRUE;
string cTestURL = "http://142.51.75.111/cs_dispatch.aspx?";

//string errorTXT = "";

//string tempNodeID = "";

string cRootNode= "ariadne";

string cSIDName = "session";
string cSSID = "id";
string cServerType = "type";

string cNodeName = "node";
string cNodeId = "id";
string cNodeLabel = "label";

string cAsset = "asset";
string cAssetType = "type";
string cAssetName = "name";
string cAssetTarget = "target";

string cLink = "link";
string cLinkLabel = "label";
string cLinkRef = "ref";

list knownItems = [cSSID, cNodeName];
list knownSets = [cAsset, cLink];

list gPlayerList;
list gPlayerRoleList;
list gPlayerAgentKeyList;

//string continuance;
//list continuances;
//integer continuance_count;
//integer continuance_index = 0;
//integer gCurrentContinuanceChannel;
//list continuance_times;
//list continuance_targets;

list orderedAssets;

//list sequence_continuances;
//list sequence_times;
//integer sequence_index;
//integer sequence_count;
//float time_to_next_sequence;

// END newParser constants

// ******** GLOBAL VARS ********

vector txtcol = <1,1,1>;
float txtalpha = 1.0;

integer gOffset;
integer gBlock = 5;
string gPage = "node";

integer lh;
integer lhd;
integer lhc;
//key mediakey;
//integer mediamode;
string localtext;

string gNodeLabel;
string gNodeDesc;
string gNodeImage;
string gNodeMedia;
string gNodeOptions;
string gSSID;
string gPIVOTEPrefix;

integer gQuickStart=FALSE;
integer gQuickOption=FALSE;

string gStartCommands;
string gEndCommands;
string gResetCommands;

//holodeck stuff
string gScene;
string gShell;

float gSensorRange = 196.0;
float gSoundVolume = 0.8;
string gCurrentSceneStatus;

integer gHolodeckChatChannel = 9993;
integer gHolodeckAPIChannel = -9993;

integer gSignupObjChannel = -8787;

integer gPIVOTEChannel = 687686; // different for each master within 20m - MUST CHANGE ALL OBJECTS
integer gMediaCh = -63342;
//integer gFauxIMChannel = -696969; // never gets used as channel, just a mask

integer gPlayerTrackingObjChannel = 687687; // bracelet
//integer gQuizChannel = 555;

// newParser globals
string gServerType;
list assetTypes;
list assetNames;
list assetTargets;
list assetValues;
list linkLabels;
list linkRefs;

string gServiceUID = "";
string gServicePwd = "";

string gTutorGroupID="";
string gControllerName="";

// configs
integer gShowNodeLabel=FALSE;
string gFilter = "";

string gServiceURL;
string gServicePageURL;
string gHelpURL;
string gMenuURL;
//string gIdleURL;
string gUpdatingURL;
string gQSParserPageURL = "http://142.51.75.111/SLDataCollection/show.html";
string gObjectBox = "PIVOTE Paramedic Equipment Box 1.0";
integer gShowHUD=TRUE;
integer gShowObjects=TRUE;
integer gShowSession=TRUE;

integer gAutoShow = FALSE;
integer gAutoShowTime = 5;
key gUserKey = NULL_KEY;

string gNode = "";
string oldNode = "";
string urlroot;

string gCase;
list gOptions;
string gExerciseList = "";

key Rq_getnode;
key Rq_getpage;

key Ds_getLine;
key XMLParser_getLine;
string gConfigCard = "pivotecontroller.cfg";
string gXMLConfigCard = "feedparserconstants.cfg";
integer gLine;
integer gXMLParserLine;

key prekey;
key PatientAgentKey;
key PlayerAgentKey;

//string Characters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
string Letters= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
string Numbers = "0123456789";

// ******** END GLOBAL VARS ********

// ******** FUNCTIONS ********

string getAssetAttribValByName(string assetName, string attrib){
    integer nAssets = llGetListLength(assetNames);
    integer i;
    for (i = 0; i < nAssets; i++) {
        if (assetName == llList2String(assetNames,i)){
            if (attrib == "target"){
                return llList2String(assetTargets,i);
            }else{
                if (attrib == "value"){
                    return llList2String(assetValues,i);
                }else{
                    return llList2String(assetTypes,i);

                }
            }
        }
    }
    return "";
}

string xtraktSeq (string stream, string type, string attrib) {
    string param;
    integer sts = llSubStringIndex(stream, type);

    string chunk =  llGetSubString(stream,sts,-1);
    //llSay(0, "chunk " + chunk);
    string attribChunk = llGetSubString(chunk,llStringLength(type),llSubStringIndex(chunk, "/>") - 1);// truncate to front of said caret...
    //llSay(0, "attribChunk " + attribChunk);
    if (llSubStringIndex(attribChunk, attrib) > -1){
        string myAttribChunk = llGetSubString(attribChunk,llSubStringIndex(attribChunk, attrib + "=\"") + llStringLength(attrib + "=\""),-1);
        //llSay(0, "myAttribChunk " + myAttribChunk);
        param = llGetSubString(myAttribChunk, 0, llSubStringIndex(myAttribChunk, "\"")- 1); // grab everything before the first double-quote
    }else{
        param = "";
    }

    //llWhisper(0, "XMLextractAttribByTag: p:"+param);
    return param;
}

string tempGetNodeID(string p) {

    if (p == "black") {
        return "8338";
    }
    if (p == "blue") {
        return "8342";
    }
    if (p == "carbon") {
        return "8341";
    }
    if (p == "door") {
        return "8344";
    }
    if (p == "red") {
        return  "8343";
    }
    if (p == "salt") {
        return "8340";
    }
    if (p == "shapechoice") {
        return "8336";
    }
    if (p == "water") {
        return "8339";
    }
    if (p == "white") {
        return "8337";
    }

    return p;
}

key getKeyforPlayerName(string target){

    key response = NULL_KEY;
    integer nPlayers = llGetListLength(gPlayerList);
    integer j;
    for (j = 0; j < nPlayers; j++) {
        if (llList2String(gPlayerList,j) == target){
            response = (key)llList2String(gPlayerAgentKeyList,j);
        }
    }
    return response;
}

integer OnlyContains(string a, string b){
    integer len = llStringLength(a);
    integer result = FALSE;
    if(len != 0) {
        result = TRUE;
        integer index = 0;
        do {
            string chara = llGetSubString(a,index,index);
            integer posa = llSubStringIndex(b,chara);
            if(posa < 0){
                result = FALSE;
                index = len;
            }
            ++index;
        }
        while(index < len);
    }
    return result;
}

integer IsNumeric(string a){
    return OnlyContains(a,Numbers);
}

integer IsAlpha(string a){
    return OnlyContains(a,Letters);
}

integer IsAlphanumeric(string a){
    return OnlyContains(a,Letters + Numbers);
}

assignSL(string type, string target, string name, string val, integer duration){

    //scene loaded, run playlist
    // if user-triggered (ie.clickable) object, wait for obj's onSit to tell us

    if (type == "slanimation"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type+"~"+name);
        jump out;

    }

    // should just be a chat command to the target player's bracelet:
    if (type == "slbodypart"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type+"~"+name);
        sendChatCommand (-1, name + " has been added to your inventory. "
        +"Drag it to appropriate area on your avatar to wear it. ");

        jump out;
    }

    // should just be a chat command to the target player's bracelet:
    if (type == "slsound"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        jump out;

    }

    // should just be a chat command to the target player's bracelet:
    if (type == "slobject"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        sendChatCommand (-1, name + " has been added to your inventory. "
        +"Drag it to the ground to rez it");

        //llAttachToAvatar(integer)
        jump out;
    }
    if (type == "slpoint"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        //llPointAt((vector)((string)"<"+target+">"));
        jump out;
    }
    if (type == "slhud"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        sendChatCommand (-1, "The "+ name +" HUD has been added to your inventory. "
        +"Please attach it to the " + val + "of your display.");

        jump out;
    }
    if (type == "slchat"){
        //llSay(0, "about to chat >" +val+"< on channel:"+ name);
        sendChatCommand((integer)target, val);
        jump out;
    }

    if (type == "slim"){
        sendChatCommand (-1, val);
        jump out;
    }
    if (type == "sltexture"){
        //if it starts with http, it's a remote texture
        //else in inv
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        jump out;

    }

    // should just be a chat command to the target player's bracelet:
    if (type == "slpackage"){
        sendChatCommand(gPlayerTrackingObjChannel, (string)getKeyforPlayerName(target)+"~"+ type +"~"+ name);
        sendChatCommand (-1, "The " +name + "crate has been added to your inventory. "
        +"Drag it to the ground to rez it, and right-click to open it.");

        jump out;
    }
    // is a  chat command to the target player's bracelet, nothin to do
    if (type == "slmove"){
        //llSay(0,"in assignSL"+(string)PlayerAgentKey+"~"+val);
        //call llMoveToTarget() on the bracelet
        sendChatCommand(gPlayerTrackingObjChannel, (string)PlayerAgentKey+"~"+val);
        //OR do it 'violently':
        //llPushObject(PlayerAgentKey, (vector)name, (vector)target, 1);
        jump out;
    }

    if (type == "slinnergame"){
        sendChatCommand(0,"show "+name);
        //set parcel media to sloodle URL
        sendChatCommand(gMediaCh,val);
        //wait for end of game msg, tranlate into call to option_option(i)
    }

    @out;

}

resetParserConstants(){
    cNodeId= cSSID;
    cLinkLabel = cNodeLabel;
    knownItems = [cSSID, cNodeName];
    knownSets = [cAsset, cLink];
}

resetElements(){
    //gSSID = ""; // do we wanna do this?
    gServerType = "";

    gNode = "";
    gNodeLabel = "";
    gScene = "";
    gShell = "";

    assetTypes = [];
    assetNames = [];
    assetTargets = [];
    assetValues = [];

    linkLabels = [];
    linkRefs = [];

    gNodeDesc= "";
    gNodeImage= "";
    gNodeMedia= "";
    gNodeOptions= "";

orderedAssets = [];

    // errorTXT = "";

}
string ampSepChar = "&amp;";

parseFeed(string body){

    list lines = llParseString2List(body, ["/>","<", ">", "\n"], []);
    // loop through elements
    integer n = llGetListLength(lines);
    integer i;
    for(i = 0; i < n; i++){

        string line = llToLower(llList2String(lines, i));
        /// if root - new feed
        if(line == cRootNode){
            resetElements();
            //return;
        }
        string element = llGetSubString(line, 0, llSubStringIndex(line, " ") - 1);
        line =llGetSubString(line, llStringLength(element) + 1, -1);

        list parts = llParseString2List(line, ["=\"", "\" "], []);
        integer n2 = llGetListLength(parts);
        integer i2;

        for(i2 = 0; i2 < n2; i2 += 2){
            //llSay(0, "in parsefeed " + (string)llGetFreeMemory());
            string name = llToLower(llList2String(parts, i2));
            string value = llList2String(parts, i2 + 1);
            //string label = element;

            // nodeSets attribs:
            if(element == cAsset){
                //stridedAssetsList  = (stridedAssetsList=[]) + stridedAssetsList + [value];
                if(name == cAssetType) assetTypes = (assetTypes=[]) + assetTypes + [value];

                //if(name == cAssetType) assetTypes += [value];

                if(name == cAssetName) assetNames = (assetNames=[]) + assetNames + [value];
                //if(name == cAssetName) assetNames += [value];

                if(name == cAssetTarget)assetTargets = (assetTargets=[]) + assetTargets + [value];
                //if(name == cAssetTarget) assetTargets += [value];

                if(name == "value") assetValues = (assetValues=[]) + assetValues + [value];
                //if(name == "value") assetValues += [value];
            }

            if(element == cLink){

                 if(name == cLinkLabel) linkLabels = (linkLabels=[]) + linkLabels + [value];

                //if(name == cLinkLabel) linkLabels += [value];
                if(name == cLinkRef) linkRefs = (linkRefs=[]) + linkRefs + [value];
                //if(name == cLinkRef) linkRefs += [value];
            }

            // single nodes attribs:
            if(element == cSIDName){
                if(name == cSSID) {
                    if (gSSID == value){
                        //llSay(0, "SSIDs match. All is fine");
                    }else{
                        if (gSSID == ""){ // first timer
                           // llSay(0, "SSID set for the 1st time.");
                            gSSID = value;
                        }else{
                           // llSay(0, "SSID present, but does not match current session. What happened?");
                        }
                    }
                }
                if(name == cServerType) gServerType = value;
            }

            if(element == cNodeName){
                if(name == cNodeId){
                    if (gNode != "")oldNode = gNode;
                    gNode = value;
                }
                if(name == cNodeLabel) gNodeLabel = value;
            }
            // TODO: counters!!
        }

    }

   // llSay(0, "in parsefeed " + llList2CSV(assetTypes));
}

sendChatCommand (integer channel, string cmd) {

    if (channel == -1){
        llInstantMessage(PlayerAgentKey, cmd);
    }else{
        if (channel == gPIVOTEChannel && gPIVOTEPrefix != ""){
            cmd = gPIVOTEPrefix+":"+ cmd;
        }
    }
    llSay(channel, cmd);
}

parserConfig_load(string p, string v) {
    if (p == "xmllabel.RootNode") {
        cRootNode= v;
        jump out;
    }
    if (p == "xmllabel.SIDName") {
        cSIDName = v;
        jump out;
    }
    if (p == "xmlitem.SSID") {
        cSSID= v;
        jump out;
    }
    if (p == "xmllabel.ServerType") {
        cServerType = v;
        jump out;
    }
    if (p == "xmlitem.NodeName") {
        cNodeName = v;
        jump out;
    }
    if (p == "xmllabel.NodeLabel") {
        cNodeLabel = v;
        jump out;
    }
    if (p == "xmlset.Asset") {
        cAsset = v;
        jump out;
    }
    if (p == "xmllabel.AssetType") {
        cAssetType = v;
        jump out;
    }
    if (p == "xmllabel.AssetName") {
        cAssetName = v;
        jump out;
    }
    if (p == "xmllabel.AssetTarget") {
        cAssetTarget = v;
        jump out;
    }
    if (p == "xmlset.Link") {
        cLink = v;
        jump out;
    }
    if (p == "xmllabel.LinkRef") {
        cLinkRef = v;
    }
    @out;

}

config_load(string p, string v) {
    if (p == "gShowNodeLabel") {
        gShowNodeLabel = TRUE;
        if (v == "FALSE") {
            gShowNodeLabel = FALSE;
        }
        jump out;
    }
    if (p == "gShowHUD") {
        gShowHUD = TRUE;
        if (v == "FALSE") {
            gShowHUD = FALSE;
            set_button("hud", "hide");
        } else {
            set_button("hud", "show");
        }
        jump out;
    }
    if (p == "gShowObjects") {
        gShowObjects = TRUE;
        if (v == "FALSE") {
            gShowObjects = FALSE;
            set_button("objects", "hide");
        } else {
            set_button("objects", "show");
        }
        jump out;
    }
    if (p == "gShowSession") {
        gShowSession = FALSE;
        if (v == "TRUE") { gShowSession = TRUE;}
        jump out;
    }
    if (p == "gServiceURL") {
        gServiceURL = v;
        jump out;
    }
    if (p == "gServicePageURL") {
        gServicePageURL = v;
        jump out;
    }
    if (p == "gHelpURL") {
        gHelpURL = v;
        jump out;
    }
    if (p == "gMenuURL") {
        gMenuURL = v;
        jump out;
    }
    if (p == "gUpdatingURL") {
        gUpdatingURL = v;
        jump out;
    }
    if (p == "gFilter") {
        gFilter = llEscapeURL(v);
        jump out;
    }
    if (p == "gExerciseList") {
        gExerciseList = v;
        jump out;
    }
    if (p == "gPIVOTEChannel") {
        gPIVOTEChannel = (integer)v;
        jump out;
    }
    if (p == "gPIVOTEPrefix") {
        gPIVOTEPrefix = v;
        jump out;
    }
    if (p == "gMediaCh") {
        gMediaCh = (integer)v;
        jump out;
    }
    if (p == "gQuickStart") {
        gQuickStart=FALSE;
        if (v == "TRUE") { gQuickStart = TRUE;}
        jump out;
    }
    if (p == "gQuickOption") {
        gQuickOption=FALSE;
        if (v == "TRUE") { gQuickOption = TRUE;}
        jump out;
    }

    if (p == "gServiceUID") {
        gServiceUID = v;
        jump out;
    }
    if (p == "gServicePwd") {
        gServicePwd = v;
        jump out;
    }
    if (p == "gTutorGroupID") {
        gTutorGroupID = v;
        jump out;
    }
    if (p == "gControllerName") {
        gControllerName = v;
        jump out;
    }
    if (p == "gStartCommands") {
        gStartCommands = v;
        jump out;
    }
    if (p == "gEndCommands") {
        gEndCommands = v;
        jump out;
    }
    if (p == "gResetCommands") {
        gResetCommands = v;
        jump out;
    }

    if (p == "gHolodeckChatChannel") {
        gHolodeckChatChannel = (integer)v;
        jump out;
    }

    if (p == "gHolodeckAPIChannel") {
        gHolodeckAPIChannel = (integer)v;
    }
    @out;

}

option_start(key id) {
    sendChatCommand(gPIVOTEChannel, gResetCommands);
    resetElements();
    gSSID="";
    string url = cTestURL + "&mnodeid=8336";
    Rq_getpage = llHTTPRequest(url, [HTTP_METHOD,"GET"], "");
}

option_text() {
    //set_media(gServicePageURL+gSSID+"_title.html?"+gNode);

    sendChatCommand(gMediaCh, gQSParserPageURL + "?options="
    + llEscapeURL(localtext)+"&dtext="
    + llEscapeURL(llList2CSV(gOptions))); // page with doc.write() JS, presents QS params
}

option_media() {
    string mediaurl = "";

    if (gNodeImage != "") {
        mediaurl = gNodeImage;
    } else {
        if (gNodeMedia != "") {
            mediaurl =gNodeMedia;
        }
    }
    sendChatCommand(gMediaCh, mediaurl);
}

option_back() {
    integer canGoBack = FALSE;
    if (gUseNewParser) urlroot = cTestURL;
    // should just be value of oldNode
    //llSay(0, "gSSID = "+ gSSID);

    if (oldNode != ""){
        integer n = llGetListLength(linkLabels);
        integer i;

        for(i = 0; i < n; i++){

            if (oldNode == llList2String(linkRefs,i)){
                canGoBack = TRUE;
            }
        }
    }else{
        llSay(0, "There is no record of a previous node");
    }

    if (canGoBack){

        Rq_getnode = llHTTPRequest(urlroot+"&mnodeid="+oldNode+
        "&sessid="+gSSID, [HTTP_METHOD,"GET"], "");
    }else{
        llSay(0, "You cannot go back to the "+oldNode+" node from this one (" + gNode + " node)");
    }
}

option_help() {
    sendChatCommand(gMediaCh, gHelpURL);
}

option_menu() {
    sendChatCommand(gMediaCh, gMenuURL);
}

option_showbrowser(key id) {
    sendChatCommand(gMediaCh, (string)id + "~showbrowser");
}

option_options(key id) {
    integer l = llGetListLength(gOptions);
    //    integer i;
    if ((gQuickOption) && (l==1)) {
        option_option(0);
    } else {
        sendChatCommand(gMediaCh, gQSParserPageURL + "?dtext="+ llEscapeURL(llList2CSV(gOptions)));
    }
}

option_option(integer num) {
    //todo: must have a session id and oldNode by now!

    list o = gOptions;
    integer l = llGetListLength(o);

    if (llList2String(o, 0) != "") {

        sendChatCommand(gMediaCh, gQSParserPageURL + "?options=Updating...");

        string nodeName = llList2String(o, 1);

        if (gUseNewParser) nodeName = llList2String(linkRefs, num); // is it that easy? must we subt by 1

        // TODO: create a few newtesterX.xml, to fake state changes
        if (gUseNewParser) urlroot = cTestURL;

        if (gPage == "node") {
            nodeName = llEscapeURL(nodeName);
            //if (gUseNewParser) urlroot = cTestURL + "1.xml";
            //llSay(0, "gSSID = "+ gSSID);

            Rq_getnode = llHTTPRequest(urlroot+"&api=shownode&case="+gCase+
            "&mnodeid="+nodeName+"&sessid="+gSSID, [HTTP_METHOD,"GET"], "");
        }else{
            if (gPage == "case list") {
                if (nodeName == "_start") {
                    nodeName = "";
                    option_start(NULL_KEY);
                    nodeName = "";
                }else{
                    if (nodeName == "_more") {
                        gOffset += gBlock;
                        nodeName = "";
                        //if (gUseNewParser) urlroot = cTestURL + "1.xml";
                        Rq_getpage = llHTTPRequest(urlroot+"&api=list&offset="+(string)gOffset+
                        "&block="+(string)gBlock+"&filter="+gFilter+"&avail="+llEscapeURL(gExerciseList),
                        [HTTP_METHOD,"GET"], "");
                    }else{
                        if (nodeName != "") {
                            gCase = nodeName;
                            nodeName = llEscapeURL(nodeName);
                            //if (gUseNewParser) urlroot = cTestURL + "1.xml";
                            Rq_getnode = llHTTPRequest(urlroot+"&api=shownode&av="+llEscapeURL(llKey2Name(gUserKey))+
                            "&case="+nodeName, [HTTP_METHOD,"GET"], "");
                        }
                    }
                }
            }
        }
    }
}

set_button(string btn, string cond) {
    llMessageLinked(LINK_SET, 0, btn+"_"+cond, NULL_KEY);
}

resetAnimator()
{
    //llMessageLinked(0, 0, "cancel", prekey);
    prekey = NULL_KEY;
    PatientAgentKey  = NULL_KEY;
    PlayerAgentKey = NULL_KEY;
    //llSetTimerEvent(0);
}

// ******** END FUNCTIONS ********

default {
    state_entry()
    {
        state load_config;
    }
}

state findingPlayers {
    state_entry() {

        string obj2Scan = "nossum regional hospital bracelet 0.2";

        //llListenRemove(lh);
        lh = llListen(gSignupObjChannel, "", NULL_KEY, "");
        sendChatCommand(gSignupObjChannel, obj2Scan+"~10.0"); // object scan for, the radius

        // state active;

    }

    listen(integer channel, string name, key id, string msg) {
        // each person returned is wearing a nossum_bracelet, then you're sent an end of msg file (Signup machine not yet implemented: TODO):

        // TODO: bracelet must pass in owner key on wear/register
        gPlayerAgentKeyList =["phantomKey"] + gPlayerAgentKeyList;
        gPlayerList = [llKey2Name("phantomKey")] +gPlayerList;
        state active;

        if (channel==gSignupObjChannel){
            if (msg != "EOM"){
                gPlayerList = (gPlayerList=[]) + gPlayerList + [name];
                //gPlayerList += name;
                list parts = llParseString2List(msg, ["~"], []);
                gPlayerRoleList = [llList2String(parts, 0)] + gPlayerRoleList;
                gPlayerAgentKeyList = [(key)llList2String(parts, 1)]+gPlayerAgentKeyList;
            }else{
                state active;
            }
        }
    }

}

state sceneLocked   {
    state_entry() {

        integer sceneRezzed = FALSE;

        while (!sceneRezzed)
        {

            if (llSubStringIndex(gCurrentSceneStatus, "REZZED_SCENE") > -1)
            {
                sceneRezzed = TRUE;
                gCurrentSceneStatus = "";
            }
            else
            {
                llSleep(0.5);
            }
        }
        state active;

    }

    listen(integer channel, string name, key id, string msg) {

        if (channel==gHolodeckChatChannel){
            gCurrentSceneStatus = msg;
        }
    }

}

state load_config
{
    state_entry()
    {
        sendChatCommand(0, "Please wait, loading configuration data ...");
        sendChatCommand(gMediaCh, gQSParserPageURL + "?options=Updating...");
        sendChatCommand(gPIVOTEChannel, gResetCommands);

        if (llGetInventoryType(gXMLConfigCard) != INVENTORY_NOTECARD || llGetInventoryType(gConfigCard) != INVENTORY_NOTECARD)
        {
            //has no notecard!
            //blow a gasket here?
            sendChatCommand(0, "no configuration assigned to this controller!");
        }else{

            gXMLParserLine = 0;
            XMLParser_getLine = llGetNotecardLine(gXMLConfigCard, gXMLParserLine);
            resetParserConstants();

            gLine = 0;
            Ds_getLine = llGetNotecardLine(gConfigCard, gLine);
        }

        urlroot = gServiceURL+"?op=sl&wl=sl";
        urlroot += "&uid="+llEscapeURL(gServiceUID);
        urlroot += "&pwd="+llEscapeURL(gServicePwd);
        urlroot += "&controller="+llEscapeURL(gControllerName);
        urlroot += "&tutor="+llEscapeURL(gTutorGroupID);

        sendChatCommand(gMediaCh, gQSParserPageURL + "?dtext=Ready!");
    }

    dataserver(key query_id, string data) {
        if (query_id == Ds_getLine) {
            list l;
            string param;
            string value;
            if (data != EOF) {
                if ((llSubStringIndex(data, "//") == 0) || (llSubStringIndex(data, "=") == -1)) {
                    // comment or invalid
                } else {
                    // valid
                    l = llParseString2List(data, ["="], []);
                    param = llList2String(l, 0);
                    value = llList2String(l, 1);
                    config_load(param, value);
                }
                // next line
                Ds_getLine = llGetNotecardLine(gConfigCard, ++gLine);
            } else {
                sendChatCommand(0, "Config loaded. Scanning for registered players...");

                //state findingPlayers;
                state active;
            }
        } else {
            if (query_id == XMLParser_getLine){
                list l2;
                string param2;
                string value2;
                if (data != EOF) {
                    if ((llSubStringIndex(data, "//") == 0) || (llSubStringIndex(data, "=") == -1)) {
                        // comment or invalid
                    } else {
                        // valid
                        l2 = llParseString2List(data, ["="], []);
                        param2 = llList2String(l2, 0);
                        value2 = llList2String(l2, 1);
                        parserConfig_load(param2, value2);
                    }
                    // next line
                    XMLParser_getLine = llGetNotecardLine(gXMLConfigCard, ++gXMLParserLine);
                }
            }
        }
    }
}

state active
{
    state_entry() {

        // reset listener
        llListenRemove(lh);
        llListenRemove(lhd);
        // llListenRemove(lhc);

        // TODO: need to llListen on all the channels, poseball, bracelet, etc, not just pivote
        lh = llListen(gPIVOTEChannel, "", NULL_KEY, "");

        lhd = llListen(gHolodeckChatChannel, "", NULL_KEY, "");
        //  lhc = llListen(gCurrentContinuanceChannel, "", NULL_KEY, "");

    }

    listen(integer channel, string name, key id, string msg) {
        integer update = FALSE;
        gUserKey = id;
        if (llSubStringIndex(msg, "user=") == 0) {
            integer amp = llSubStringIndex(msg, "&");
            amp--;
            gUserKey = (key)llGetSubString(msg, 5, amp);
            id = gUserKey;
            amp += 2;
            msg = llGetSubString(msg, amp, -1);
            llOwnerSay((string)gUserKey+" = "+llKey2Name(id)+" leaving "+msg);
        }
        if (msg == "start") {
            option_start(NULL_KEY);
        }
        if (msg == "reset") {
            state load_config;
        }
        if (msg == "options") {
            option_options(id);
        }
        if (llSubStringIndex(msg, "option=") != -1) {
            //llSay(0, msg);
            integer num = (integer) llGetSubString(msg, 7, -1);
            option_option(num);
        }
        if (msg == "text") {
            option_text();
        }
        if (msg == "media") {
            option_media();
        }
        if (msg == "back") {
            option_back();
        }

        if (msg == "menu") {
            option_menu();
        }
        if (msg == "help") {
            option_help();
        }
        if (msg == "hardreset") {
            llResetScript();
        }
        if (llSubStringIndex(msg, "node=") == 0) {
            msg = llGetSubString(msg, 5, -1);
            //llSay(0, "heard "+msg);
            update = TRUE;
        }

        if (update) {
            // fetch next scene
            //llWhisper(0, "Fetching Node "+msg+" from web ...");

            sendChatCommand(gMediaCh, gUpdatingURL);
            msg = llEscapeURL(msg);

            if (gUseNewParser) urlroot = cTestURL;
            //llSay(0, "listen: gSSID = "+ gSSID + "mem: "+(string)llGetFreeMemory());

            //if (!IsNumeric(msg)){
                //integer n = llGetListLength(linkLabels);
                // integer i;

                //  for(i = 0; i < n; i++){

                    //if (msg == llList2String(linkLabels,i)) msg = llList2String(linkRefs,i);
                    msg = tempGetNodeID(msg);
                    //  }
                //}
            resetElements();
            if (gSSID == "" ) gSSID = "y";
            //llSay(0, "the string: mnodeid="+msg+"&sessid="+gSSID);
            Rq_getnode = llHTTPRequest(urlroot+"&mnodeid="+msg+"&sessid="+gSSID, [HTTP_METHOD,"GET"], "");
        }

        if (channel==gHolodeckChatChannel){
            gCurrentSceneStatus = msg;

        }

        //if (channel==gCurrentContinuanceChannel){
          //  gCurrentSceneStatus = msg;
        //}

    }

    // where the 2 http response types get handled: (get node or get node-list)
    http_response(key request_id, integer status, list metadata, string body) {
        string errorTXT = "";
        //llSay(0,"body:"+ llGetSubString(body,0,200) + ", free mem: "+ (string)llGetFreeMemory());

        //if (llSubStringIndex(llToLower(body), "<html>") > -1 || llSubStringIndex(llToLower(body), "<errors>") > -1){
            // is not xml, or is an error:

            //if (llSubStringIndex(body, "<html>") > -1){

                //errorTXT = "Error! Node has been reset, labyrinth compromised: "+XMLextractTag(body, "Error") + XMLextractAttribByTag(body, "Error", "message");
                // errorTXT = "Error! Node has been reset, labyrinth compromised...";
                //  jump errorCatch;
                //}

           // sequence_index = 0;
           // sequence_continuances = [];
           // sequence_times = [];

          //  continuance_targets = [];
           // continuances = [];
           // continuance_times= [];
           // continuance_count=0;

           // llSay(0, "free mem"+ (string)llGetFreeMemory());

            parseFeed(body);

           // llSay(0, "free mem post: "+ (string)llGetFreeMemory());

            // broadcastCounterValues(); ???

            set_button("media", "hide");
            set_button("options", "hide");

            gPage = "node";
            gNodeMedia = "";
            gNodeImage = "";

            integer nAssets = llGetListLength(assetTypes);

            // integer i;

            // first, get sequence
            string seq = llUnescapeURL(xtraktSeq(body, "SLInnerSequence", "value"));
           // llSay(0, "free mem post seq:"+ (string)llGetFreeMemory()+ " - seq: " + seq);

            list parts = llParseString2List(llUnescapeURL( seq ), ["&amp;"], []); // llUnescapeURL doesn't seem to get rid of this!
            integer n = llGetListLength(parts);
            integer i;
            for(i = 0; i < n; i++){
                string seqStr = llList2String(parts, i);
                list nameVal = llParseString2List(seqStr, ["="], []);
                string thisAssetName = llList2String(nameVal, 0);
                //llSay(0, "thisAssetName"+thisAssetName);
                string thisAssetValue = llList2String(nameVal, 1);
                //llSay(0, "thisAssetValue"+thisAssetValue);
                orderedAssets  = (orderedAssets=[]) + orderedAssets + [i] + [thisAssetName] + [thisAssetValue];
                // llSay(0, stridedAssetsList);
            }

            list  runnableAssetlist = llList2ListStrided(llDeleteSubList(orderedAssets, 0, 0), 0, -1, 3);
         //   llSay(0, "runnableAssetlist" + llList2CSV( runnableAssetlist));

            // run the assets in order
            n = llGetListLength(runnableAssetlist);
            for(i = 0; i < n; i++){
                string name = llList2String(runnableAssetlist, i);
                string value = getAssetAttribValByName(name, "value");
                string type = getAssetAttribValByName(name, "type");

                string dtext = "";
                string device = "";

                if(type=="vpdtext"){
                    //llSay(0,"******** IN VPDTEXT: "+llList2String(assetValues,i) );

                    dtext = value;
                    dtext = llGetSubString(dtext, llSubStringIndex(dtext, "]]]]")+4, -1); // trims that id stuff off the front end...
                }

                if (dtext != "") {
                    // check device
                    if (device == "") {
                        //llSay(0, dtext);
                        //localtext = localtext + dtext + ". ";
                        localtext = dtext;

                    } else {
                        // assume at beginning
                        sendChatCommand(gPIVOTEChannel, "<device>"+device+"</device>"+dtext);
                    }
                }

                integer done = FALSE;
                // todo: if image does not begin with http, add prefix
                if (type == "vpdimage") {
                    gNodeImage =  value;
                    dtext = "";
                    //set_media(gNodeImage);
                    done = TRUE;
                    set_button("media", "show");
                }

                // todo: if image does not begin with http, add prefix
                if (type == "vpdmedia") {
                    gNodeMedia = value;
                    dtext = "";
                    //set_media(gNodeMedia);
                    done = TRUE;
                    set_button("media", "show");

                }

                if(llGetSubString( type, 0, 1 ) == "sl"){

                    //gPlayerList += name;
                    //integer nRoles = llGetListLength(gPlayerRoleList);
                    // integer j;
                    //  for (j = 0; j < nRoles; j++) {
                        // string maybe_role = llList2String(gPlayerRoleList,j);
                        // if (maybe_role == llList2String(assetTargets,i)){ // does target=role
                            // continuances += llList2String(assetNames,i);
                            //  continuance_times += llList2String(assetValues,i); //implicit here is that any asset that supports duration will pass it in as a value
                            //  continuance_count++;
                            // not very clean or efficient, this:
                            //assignSL(type, llList2String(gPlayerList,j), llList2String(assetNames,i), llList2String(assetValues,i) );
                            assignSL(type, getAssetAttribValByName(name, "target"), name, value, 0 );
                            //    }
                        //  } //end for
                }

                if (gNode == "finish") {
                    sendChatCommand(gPIVOTEChannel, gEndCommands);
                    if (gPIVOTEPrefix == "") {
                        sendChatCommand(gPIVOTEChannel, "<device>"+device+"</device>"+dtext);
                    } else {
                        sendChatCommand(gPIVOTEChannel, gPIVOTEPrefix+":"+"<device>"+device+"</device>"+dtext);
                    }
                    if (gShowSession) {
                        llSay(0, "Please note that your Session ID was "+(string)gSSID+
                        ". Please make a note of it as you may need it for later assessment or review.");
                    }

                }

            }
            set_button("options", "show");
            gOptions = linkLabels;

            option_text();

            @errorCatch;
            if (errorTXT != ""){
                sendChatCommand(gMediaCh, gQSParserPageURL + "?options="+errorTXT);
            }
            resetElements();
            runnableAssetlist = [];
        }

        link_message(integer sender_num, integer num, string str, key id) {

            integer done = FALSE;
            gUserKey = id;

            if (str == "start") {
                done = TRUE;
                option_start(NULL_KEY);
            }
            if (str == "reset") {
                done = TRUE;
                state load_config;
            }

            if (str == "options") {
                done = TRUE;
                option_options(id);
            }
            if (str == "text") {
                done = TRUE;
                option_text();
            }

            if (str == "media") {
                option_media();
                done = TRUE;
            }
            if (str == "back") {
                option_back();
                done = TRUE;
            }

            if (str == "help") {
                done = TRUE;
                option_help();
            }
            if (str == "menu") {
                done = TRUE;
                option_menu();
            }

            if (str == "option") {
                option_option(num);
            }
            if (str == "hud") {
                llGiveInventory(id, "PIVOTE HUD Player 1.0");
            }
            if (str == "objects") {
                llGiveInventory(id, gObjectBox);

            }
            if (str == "showbrowser") {
                option_showbrowser(gUserKey);
            }
            if (str == "hardreset") {
                llResetScript();
            }

            if (str == "playerAgentKey"){
                PlayerAgentKey = id;
            }

            if (str == "patientAgentKey"){
                PatientAgentKey = id;
            }

        }

        timer() {

          //  if (llGetInventoryType(continuance) == INVENTORY_ANIMATION) {
           //     llStopAnimation(continuance);
           /// }else {
              //  if (llGetInventoryType(continuance) == INVENTORY_SOUND) {
                   // llStopSound();
           //     }
           // }

            //TODO: untested, prob needs lots of work
          //  if (!sequence_count && continuance_count > 1){
           //     ++continuance_index;
            //    continuance = llList2String(continuances, continuance_index);
                //playContinuance();
              //  llSetTimerEvent(llList2Float(continuance_times, continuance_index));
            //} else {
              ///  if (sequence_index == sequence_count - 1) {
                //    sequence_index = 0;
               // }else{
                //    ++sequence_index;
              //  }
               // continuance = llList2String(sequence_continuances, sequence_index);
                // playContinuance();
               // llSetTimerEvent(llList2Float(sequence_times, sequence_index));

            //}

        }

        run_time_permissions(integer perms)
        {
            if (perms & PERMISSION_TRIGGER_ANIMATION)
            {
                //llTakeControls(CONTROL_UP|CONTROL_DOWN, TRUE, FALSE);
                // runSequence();
            }
        }

    }

