integer gSignupObjChannel = -8787;
integer button_channel = 8793;
integer quickPlay = 0; // where just showing up makes you eligible
string Rq_register;
string Rq_signup;
key currentSignupUserKey;

default
{
    state_entry()
    {
        llListen(button_channel, "", NULL_KEY, "");
        llListen(gSignupObjChannel, "", NULL_KEY, "");
    }

    touch_start(integer total_number)
    {
        currentSignupUserKey= llDetectedKey(0);

        if (!quickPlay){
            llSay(0, "sending plyr="+ llKey2Name(currentSignupUserKey)+"&plyrK="+(string)currentSignupUserKey);

            // add user to gPlayerList in Controller script
            llSay(gSignupObjChannel, "plyr=" + llKey2Name(currentSignupUserKey) + "&plyrK=" + (string)currentSignupUserKey );

            list buttons = ["signup","unsubscribe"];
            llDialog(currentSignupUserKey, "What the hell do you think yer doin?", buttons, button_channel);
        }else{
            Rq_register = llHTTPRequest("http://142.51.75.111/SLDataCollection/dispatchhack.aspx?api=quickplay&av="
            +llEscapeURL(llKey2Name(currentSignupUserKey)), [HTTP_METHOD,"GET"], "");
        }
    }

 http_response(key request_id, integer status, list metadata, string body) {

        if (Rq_signup == request_id){
            if (llSubStringIndex(body, "ENLISTED") > -1){
                // give id?
                // give bracelet from inventory

            llGiveInventory(currentSignupUserKey, "nossum regional hospital bracelet 0.2");
            llInstantMessage(currentSignupUserKey, "nossum regional hospital bracelet 0.2 has been added to your inventory. "
                +"Attach it to your LEFT hand to enable it. Note: it only works on nossum island!");
            }

            if (llSubStringIndex(body,"REJECTED") > -1){
        llSay(0, "Sorry, we've either exceeded maximum capacity for this game, have shut "
        +"down the server or have found your avatar name on our banned list.");
            }
            // done with key
            currentSignupUserKey = NULL_KEY;
        }else{
            if (Rq_register == request_id){

            }
        }


    }

    listen(integer channel, string name, key id, string msg) {
        if (channel == button_channel){ // buttons from llDialog
            Rq_signup = llHTTPRequest("http://142.51.75.111/SLDataCollection/dispatchhack.aspx?api=" + msg+"&av="+llEscapeURL(llKey2Name(currentSignupUserKey)), [HTTP_METHOD,"GET"], "");
        }

        if (channel == gSignupObjChannel){
            //sendChatCommand(gSignupObjChannel, "nossum regional hospital bracelet 0.2~10.0"); // object scan for, the radius
            list parts = llParseString2List(msg, ["~"], []);
            if (llSubStringIndex((string)llList2String(parts, 0), "bracelet") > -1){
                //llSensor(llList2String(parts, 0), "","", (float)llList2String(parts, 1), TWO_PI);
            }
        }


    }

}
