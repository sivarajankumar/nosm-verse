//************** verion 0.5  *********************
//retrieves and uses session ID 

//************** verion 0.4  *********************
//calls tm_capture.aspx

//************** verion 0.3  *********************
//calls Update.aspx and inserts into SL_Telemetrics



//configuration constants

float gap = 3.0;
string verbose = "true";
string svrurl = "xxx.xx.xx.xxx";
integer allowedSIDattempts = 4; // attempts at getting new session id



// global variables

float range = 40.00; 
float counter = 0.0;
integer gRadius = 0;

string gSSID = "";
key getSSID_requestID; 
integer sidAttemptCounter = 0;
string hasteleported="false";

string telemetryEnabled = "false"; // clicking the prim flips this switch
integer chatTelemetryEnabled = FALSE; // currently disabled
string isOwnerInWorld = "false";


string XMLextractTag (string stream, string tag) {
    string param;
    integer sts = llSubStringIndex(stream, "<"+tag+">");
    integer ste = llSubStringIndex(stream, "</"+tag+">");
    integer len = llStringLength(tag);
    sts += len + 2;                
    ste--;
    if (sts > ste) {
        param = "";
    } else {
        param = llGetSubString(stream,sts,ste);            
    }
    //llWhisper(0,"s:"+(string)sts+" e:"+(string)ste+" l:"+(string)len+" p:"+param);
    return param;
}


setupListener() {
        llSetTimerEvent(gap);
        llListen(0, "", llGetOwner(), "");
        llSensorRepeat("", "", AGENT, range, TWO_PI, gap);
    }


string buildURL() {
    string url = "http://"+ svrurl +"/sldatacollection/tm_capture.aspx?";
        key owner = llGetOwner();
        string name = llKey2Name(owner);

        vector pos = llList2Vector(llGetObjectDetails(owner, [OBJECT_POS]),0);
        vector rot = llRot2Euler(llList2Rot(llGetObjectDetails(owner, [OBJECT_ROT]),0))*RAD_TO_DEG;
        vector vel = llList2Vector(llGetObjectDetails(owner, [OBJECT_VELOCITY]),0);

        string region = llGetRegionName();
        
        if (region == "Nossum"){
       // if(isLike(region,"%ossu%")){
            isOwnerInWorld = "true";
        }

        vector size = llGetAgentSize(owner);

        integer agent = llGetAgentInfo(owner);

        string runmode = "false";
        string attachment = "false";
        string isaway = "false";
        string isbusy = "false";
        string crouching = "false";
        string flying = "false";
        string inair = "false";
        string mouselook = "false";
        string onobject = "false";
        string scripted = "false";
        string sitting = "false";
        string typing = "false";
        string walking = "false";

        if (agent & AGENT_FLYING)
            flying = "true";

        if (agent & AGENT_ALWAYS_RUN)
            runmode = "true";

        if (agent & AGENT_AWAY)
            isaway = "true";

        if (agent & AGENT_BUSY)
            isbusy = "true";

        if (agent & AGENT_CROUCHING)
            crouching = "true";

        if (agent & AGENT_FLYING)
            flying = "true";

        if (agent & AGENT_IN_AIR)
            inair = "true";

        if (agent & AGENT_MOUSELOOK)
            mouselook = "true";

        if (agent & AGENT_ON_OBJECT)
            onobject = "true";

        if (agent & AGENT_SCRIPTED)
            scripted = "true";

        if (agent & AGENT_SITTING)
            sitting = "true";

        if (agent & AGENT_TYPING)
            typing = "true";

        if (agent & AGENT_WALKING)
            walking = "true";

        if (verbose == "true")
        {
            llOwnerSay("Position: " + (string)pos.x + ", " + (string)pos.y + ", " + (string)pos.z);
            llOwnerSay("Rotation: " + (string)rot.x + ", " + (string)rot.y + ", " + (string)rot.z);
            llOwnerSay("Velocity: " + (string)vel.x + ", " + (string)vel.y + ", " + (string)vel.z);
            llOwnerSay("Size: " + (string)size.x + "," + (string)size.y + ", " + (string)size.z);
            llOwnerSay("Region: " + region);
        }

        
        url += "avatarname="+llEscapeURL(name)+"&";
        url += "uid="+llEscapeURL(name)+"&";
        url += "region="+llEscapeURL(region)+"&";

        url += "xpos="+(string)pos.x+"&";
        url += "ypos="+(string)pos.y+"&";
        url += "zpos="+(string)pos.z+"&";
        
        url += "xgpos="+(string)pos.x+"&";
        url += "ygpos="+(string)pos.y+"&";
        url += "zgpos="+(string)pos.z+"&";
        

        url += "xrot="+(string)rot.x+"&";
        url += "yrot="+(string)rot.y+"&";
        url += "zrot="+(string)rot.z+"&";

        url += "xvel="+(string)vel.x+"&";
        url += "yvel="+(string)vel.y+"&";
        url += "zvel="+(string)vel.z+"&";

        url += "xsize="+(string)size.x+"&";
        url += "ysize="+(string)size.y+"&";
        url += "zsize="+(string)size.z+"&";

        url += "isrunmode="+(string)runmode+"&";
        url += "isattachment="+(string)attachment+"&";
        url += "isaway="+(string)isaway+"&";
        url += "isbusy="+(string)isbusy+"&";
        url += "iscrouching="+(string)crouching+"&";
        url += "isflying="+(string)flying+"&";
        url += "isinair="+(string)inair+"&";
        url += "ismouselook="+(string)mouselook+"&";
        url += "isonobject="+(string)onobject+"&";
        url += "isscripted="+(string)scripted+"&";
        url += "issitting="+(string)sitting+"&";
        url += "istyping="+(string)typing+"&";
        url += "iswalking="+(string)walking+"&";

        url += "inradius="+(string)gRadius+"&";
        url += "dilation="+(string)llGetRegionTimeDilation()+"&";
        url+= "clienttimestamp="+ llGetTimestamp() +"&";
        url += "hasteleported=true&";
        
        return url;
        //return llEscapeURL(url);
}

getSSID(){
  string confURL = "http://"+ svrurl +"/SLDataCollection/tm_capture.aspx?uid="+llEscapeURL(llKey2Name(llGetOwner()));
  if (allowedSIDattempts > sidAttemptCounter){
      //llSay(0,"Sending SID getter URL: " + confURL);
     getSSID_requestID = llHTTPRequest(confURL, [HTTP_METHOD, "POST"], "");
     sidAttemptCounter++;
  }else{
     llSay(0,"Sorry, a total of "+(string)sidAttemptCounter+" failed attempts have been made to "+
     "start a telemetry session. Contact server administrator for an explanation... ;)");
  }       
}

string buildChatURL(string msg) {
    string chaturl = "http://"+ svrurl +"/chatupdate.php?";
        key owner = llGetOwner();
        string name = llKey2Name(owner);

        integer strlength = llStringLength(msg);

        chaturl += "name="+llEscapeURL(name)+"&";
        chaturl += "message="+llEscapeURL(msg)+"&";
        chaturl += "length="+(string)strlength;
        return chaturl;
}




default
{
    state_entry()
    {
    setupListener();
    }

    timer()
    {
        if (llGetAttached() < 39 && llGetAttached() > 30){ // is attached as a HUD - R.S. 03/12/2009
        // llSay(0,"-->"+llGetRegionName()+"<--");

        
        if (llGetRegionName() == "Nossum"){
     // if(isLike(region,"%ossu%")){
            isOwnerInWorld = "true";
        }
        
            if (telemetryEnabled== "true" && isOwnerInWorld == "true") {
                //if (telemetryEnabled== "true") {
               // llSay(0,"sending http request: "+isOwnerInWorld);
                //llHTTPRequest(buildURL(), [HTTP_METHOD, "POST"], "");
                if (gSSID == ""){
                    getSSID();
                }
                llSay(0,"Sending URL: " + buildURL()+"sid="+gSSID);
                llHTTPRequest(buildURL()+"sid="+gSSID, [HTTP_METHOD, "POST"], "");
            }
            
        }
    }

    // When owner speaks, log it
    listen(integer channel, string name, key id, string message)
    {
        if (chatTelemetryEnabled){
            llHTTPRequest(buildChatURL(message), [HTTP_METHOD, "POST"], "");
        }
    }


    sensor (integer numberDetected)
    {
        if (telemetryEnabled){
            gRadius = numberDetected;
            string msg = "Detected "+(string) numberDetected+" avatar(s): ";
            integer i;
            msg += llDetectedName(0);
            for (i = 1; i < numberDetected; i++)
            {
                msg += ", ";
                msg += llDetectedName(i);
            }
            if (telemetryEnabled== "true" && verbose == "true" && llGetAttached() < 39 && llGetAttached() > 30){
                llOwnerSay(msg);
            }
        }

    }

    no_sensor()
    {
        gRadius = 0;
    }
    
  http_response(key request_id, integer status, list metadata, string body) {
   
        if(request_id ==getSSID_requestID)
        {
            integer i;
            list lbody=llParseString2List(body,["\n"],[]);
            integer count=llGetListLength(lbody);
            for(i=0;i<count;i++)
            {
                string sidVal = XMLextractTag(llList2String(lbody,i), "SID");
                if (llStringLength(sidVal) > 0){
                    llSay(0, "value of XML: " + sidVal);
                    if (gSSID == ""){
                        gSSID = sidVal;
                        sidAttemptCounter=0;
                    }else{
                        if (gSSID != sidVal){ // is a new session
                            gSSID = "";
                            getSSID();
                        }else{
                                //do nothing, SIDs match                               
                        }                                       
                    }
                }
            }//end for
        }//end if
    }//end http_resp
    
touch_start(integer n) {
        if (telemetryEnabled != "true"){
            //getSSID();
            //if  (gSSID != ""){
                telemetryEnabled = "true";
                llSetPrimitiveParams([PRIM_COLOR, ALL_SIDES, <0.6,1.0,0.0>, 1.0]);
            //}
        }else{
            telemetryEnabled = "false";
            gSSID = "";
            sidAttemptCounter = 0;
            llSetPrimitiveParams([PRIM_COLOR, ALL_SIDES, <1.0,0.0,0.0>, 1.0]);
        }

}
    
    

}//************** verion 0.5  *********************
//retrieves and uses session ID 

//************** verion 0.4  *********************
//calls tm_capture.aspx

//************** verion 0.3  *********************
//calls Update.aspx and inserts into SL_Telemetrics



//configuration constants

float gap = 3.0;
string verbose = "true";
string svrurl = "142.51.75.111";
integer allowedSIDattempts = 4; // attempts at getting new session id



// global variables

float range = 40.00; 
float counter = 0.0;
integer gRadius = 0;

string gSSID = "";
key getSSID_requestID; 
integer sidAttemptCounter = 0;
string hasteleported="false";

string telemetryEnabled = "false"; // clicking the prim flips this switch
integer chatTelemetryEnabled = FALSE; // currently disabled
string isOwnerInWorld = "false";


string XMLextractTag (string stream, string tag) {
    string param;
    integer sts = llSubStringIndex(stream, "<"+tag+">");
    integer ste = llSubStringIndex(stream, "</"+tag+">");
    integer len = llStringLength(tag);
    sts += len + 2;                
    ste--;
    if (sts > ste) {
        param = "";
    } else {
        param = llGetSubString(stream,sts,ste);            
    }
    //llWhisper(0,"s:"+(string)sts+" e:"+(string)ste+" l:"+(string)len+" p:"+param);
    return param;
}


setupListener() {
        llSetTimerEvent(gap);
        llListen(0, "", llGetOwner(), "");
        llSensorRepeat("", "", AGENT, range, TWO_PI, gap);
    }


string buildURL() {
    string url = "http://"+ svrurl +"/sldatacollection/tm_capture.aspx?";
        key owner = llGetOwner();
        string name = llKey2Name(owner);

        vector pos = llList2Vector(llGetObjectDetails(owner, [OBJECT_POS]),0);
        vector rot = llRot2Euler(llList2Rot(llGetObjectDetails(owner, [OBJECT_ROT]),0))*RAD_TO_DEG;
        vector vel = llList2Vector(llGetObjectDetails(owner, [OBJECT_VELOCITY]),0);

        string region = llGetRegionName();
        
        if (region == "Nossum"){
       // if(isLike(region,"%ossu%")){
            isOwnerInWorld = "true";
        }

        vector size = llGetAgentSize(owner);

        integer agent = llGetAgentInfo(owner);

        string runmode = "false";
        string attachment = "false";
        string isaway = "false";
        string isbusy = "false";
        string crouching = "false";
        string flying = "false";
        string inair = "false";
        string mouselook = "false";
        string onobject = "false";
        string scripted = "false";
        string sitting = "false";
        string typing = "false";
        string walking = "false";

        if (agent & AGENT_FLYING)
            flying = "true";

        if (agent & AGENT_ALWAYS_RUN)
            runmode = "true";

        if (agent & AGENT_AWAY)
            isaway = "true";

        if (agent & AGENT_BUSY)
            isbusy = "true";

        if (agent & AGENT_CROUCHING)
            crouching = "true";

        if (agent & AGENT_FLYING)
            flying = "true";

        if (agent & AGENT_IN_AIR)
            inair = "true";

        if (agent & AGENT_MOUSELOOK)
            mouselook = "true";

        if (agent & AGENT_ON_OBJECT)
            onobject = "true";

        if (agent & AGENT_SCRIPTED)
            scripted = "true";

        if (agent & AGENT_SITTING)
            sitting = "true";

        if (agent & AGENT_TYPING)
            typing = "true";

        if (agent & AGENT_WALKING)
            walking = "true";

        if (verbose == "true")
        {
            llOwnerSay("Position: " + (string)pos.x + ", " + (string)pos.y + ", " + (string)pos.z);
            llOwnerSay("Rotation: " + (string)rot.x + ", " + (string)rot.y + ", " + (string)rot.z);
            llOwnerSay("Velocity: " + (string)vel.x + ", " + (string)vel.y + ", " + (string)vel.z);
            llOwnerSay("Size: " + (string)size.x + "," + (string)size.y + ", " + (string)size.z);
            llOwnerSay("Region: " + region);
        }

        
        url += "avatarname="+llEscapeURL(name)+"&";
        url += "uid="+llEscapeURL(name)+"&";
        url += "region="+llEscapeURL(region)+"&";

        url += "xpos="+(string)pos.x+"&";
        url += "ypos="+(string)pos.y+"&";
        url += "zpos="+(string)pos.z+"&";
        
        url += "xgpos="+(string)pos.x+"&";
        url += "ygpos="+(string)pos.y+"&";
        url += "zgpos="+(string)pos.z+"&";
        

        url += "xrot="+(string)rot.x+"&";
        url += "yrot="+(string)rot.y+"&";
        url += "zrot="+(string)rot.z+"&";

        url += "xvel="+(string)vel.x+"&";
        url += "yvel="+(string)vel.y+"&";
        url += "zvel="+(string)vel.z+"&";

        url += "xsize="+(string)size.x+"&";
        url += "ysize="+(string)size.y+"&";
        url += "zsize="+(string)size.z+"&";

        url += "isrunmode="+(string)runmode+"&";
        url += "isattachment="+(string)attachment+"&";
        url += "isaway="+(string)isaway+"&";
        url += "isbusy="+(string)isbusy+"&";
        url += "iscrouching="+(string)crouching+"&";
        url += "isflying="+(string)flying+"&";
        url += "isinair="+(string)inair+"&";
        url += "ismouselook="+(string)mouselook+"&";
        url += "isonobject="+(string)onobject+"&";
        url += "isscripted="+(string)scripted+"&";
        url += "issitting="+(string)sitting+"&";
        url += "istyping="+(string)typing+"&";
        url += "iswalking="+(string)walking+"&";

        url += "inradius="+(string)gRadius+"&";
        url += "dilation="+(string)llGetRegionTimeDilation()+"&";
        url+= "clienttimestamp="+ llGetTimestamp() +"&";
        url += "hasteleported=true&";
        
        return url;
        //return llEscapeURL(url);
}

getSSID(){
  string confURL = "http://"+ svrurl +"/SLDataCollection/tm_capture.aspx?uid="+llEscapeURL(llKey2Name(llGetOwner()));
  if (allowedSIDattempts > sidAttemptCounter){
      //llSay(0,"Sending SID getter URL: " + confURL);
     getSSID_requestID = llHTTPRequest(confURL, [HTTP_METHOD, "POST"], "");
     sidAttemptCounter++;
  }else{
     llSay(0,"Sorry, a total of "+(string)sidAttemptCounter+" failed attempts have been made to "+
     "start a telemetry session. Contact server administrator for an explanation... ;)");
  }       
}

string buildChatURL(string msg) {
    string chaturl = "http://"+ svrurl +"/chatupdate.php?";
        key owner = llGetOwner();
        string name = llKey2Name(owner);

        integer strlength = llStringLength(msg);

        chaturl += "name="+llEscapeURL(name)+"&";
        chaturl += "message="+llEscapeURL(msg)+"&";
        chaturl += "length="+(string)strlength;
        return chaturl;
}




default
{
    state_entry()
    {
    setupListener();
    }

    timer()
    {
        if (llGetAttached() < 39 && llGetAttached() > 30){ // is attached as a HUD - R.S. 03/12/2009
        // llSay(0,"-->"+llGetRegionName()+"<--");

        
        if (llGetRegionName() == "Nossum"){
     // if(isLike(region,"%ossu%")){
            isOwnerInWorld = "true";
        }
        
            if (telemetryEnabled== "true" && isOwnerInWorld == "true") {
                //if (telemetryEnabled== "true") {
               // llSay(0,"sending http request: "+isOwnerInWorld);
                //llHTTPRequest(buildURL(), [HTTP_METHOD, "POST"], "");
                if (gSSID == ""){
                    getSSID();
                }
                llSay(0,"Sending URL: " + buildURL()+"sid="+gSSID);
                llHTTPRequest(buildURL()+"sid="+gSSID, [HTTP_METHOD, "POST"], "");
            }
            
        }
    }

    // When owner speaks, log it
    listen(integer channel, string name, key id, string message)
    {
        if (chatTelemetryEnabled){
            llHTTPRequest(buildChatURL(message), [HTTP_METHOD, "POST"], "");
        }
    }


    sensor (integer numberDetected)
    {
        if (telemetryEnabled){
            gRadius = numberDetected;
            string msg = "Detected "+(string) numberDetected+" avatar(s): ";
            integer i;
            msg += llDetectedName(0);
            for (i = 1; i < numberDetected; i++)
            {
                msg += ", ";
                msg += llDetectedName(i);
            }
            if (telemetryEnabled== "true" && verbose == "true" && llGetAttached() < 39 && llGetAttached() > 30){
                llOwnerSay(msg);
            }
        }

    }

    no_sensor()
    {
        gRadius = 0;
    }
    
  http_response(key request_id, integer status, list metadata, string body) {
   
        if(request_id ==getSSID_requestID)
        {
            integer i;
            list lbody=llParseString2List(body,["\n"],[]);
            integer count=llGetListLength(lbody);
            for(i=0;i<count;i++)
            {
                string sidVal = XMLextractTag(llList2String(lbody,i), "SID");
                if (llStringLength(sidVal) > 0){
                    llSay(0, "value of XML: " + sidVal);
                    if (gSSID == ""){
                        gSSID = sidVal;
                        sidAttemptCounter=0;
                    }else{
                        if (gSSID != sidVal){ // is a new session
                            gSSID = "";
                            getSSID();
                        }else{
                                //do nothing, SIDs match                               
                        }                                       
                    }
                }
            }//end for
        }//end if
    }//end http_resp
    
touch_start(integer n) {
        if (telemetryEnabled != "true"){
            //getSSID();
            //if  (gSSID != ""){
                telemetryEnabled = "true";
                llSetPrimitiveParams([PRIM_COLOR, ALL_SIDES, <0.6,1.0,0.0>, 1.0]);
            //}
        }else{
            telemetryEnabled = "false";
            gSSID = "";
            sidAttemptCounter = 0;
            llSetPrimitiveParams([PRIM_COLOR, ALL_SIDES, <1.0,0.0,0.0>, 1.0]);
        }

}
    
    

}