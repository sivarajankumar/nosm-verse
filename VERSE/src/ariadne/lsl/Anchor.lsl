//    Copyright 2009 St. George's University of London, Daden Limited
//
//    This file is part of PIVOTE.
//
//    PIVOTE is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    PIVOTE is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with PIVOTE.  If not, see <http://www.gnu.org/licenses/>.

string gNode;
integer mvpch = 7051674;
string version = "1.0";
//Note: this code was adapted from someone.
//

key mkLoungingAgentKey = NULL_KEY;
integer miPermissionsAcquired = FALSE;

string TrimRight(string src, string chrs)//LSLEditor Unsafe, LSL Safe
{
    integer i = llStringLength(src);
    do ; while(~llSubStringIndex(chrs, llGetSubString(src, i = ~-i, i)) && i);
    return llDeleteSubString(src, -~(i), 0x7FFFFFF0);
}




default
{
    state_entry()
    {
        llListen(0, "", NULL_KEY, "");
       llListen( mvpch, "", NULL_KEY, ""); // cmds from controller
                //overriden sit target
        //lower them a bit
        vector vLoungeTarget = <0.00, 0.00, 0.00>;

        rotation rX;
        rotation rY;
        rotation rZ;
        rotation r;

        //build rotations
        //Note: this is broken out like this to simplify the
        //        process of finding the correct sit angle.  I
        //        use the following form until I have the rotation
        //        that I want perfect, and then I simply
        //        hardcode the perfected quaterion and remove
        //        this mess.
        //
        rX = llAxisAngle2Rot( <1,0,0>, 0 * DEG_TO_RAD);         //cartwheel
        rY = llAxisAngle2Rot( <0,1,0>, 0 * DEG_TO_RAD);       //sumersault
        rZ = llAxisAngle2Rot( <0,0,1>, 0 * DEG_TO_RAD);       //turn in place

        //combine rotations
        r = rX * rY * rZ;

        //override 'sit' on pie menu
        llSetSitText( "Stand" );

        //override default sit target and rotation on prim
        llSitTarget( vLoungeTarget, r );
    }



    changed(integer change)
    {
        if (change & CHANGED_LINK)
        {
            key agent = llAvatarOnSitTarget();
            if ( mkLoungingAgentKey == NULL_KEY && agent != NULL_KEY )
            {

                //changed user
                //cache new user key and request their permissions
                mkLoungingAgentKey = agent;
                 llRequestPermissions(mkLoungingAgentKey,PERMISSION_TRIGGER_ANIMATION);
            }
            else if ( mkLoungingAgentKey != NULL_KEY && agent == NULL_KEY)
            {

                //user is getting up
                if ( miPermissionsAcquired )
                {

                    //restore anims
                    llStopAnimation("turn_180");

                }

                //reset the script to release permissions
                llResetScript();
            }
        }
    }

    run_time_permissions(integer parm)
    {
        if(parm == PERMISSION_TRIGGER_ANIMATION)
        {

            //set permission flag
            miPermissionsAcquired = TRUE;

            //cancel the sit anim
            llStopAnimation("sit");

            llStartAnimation("turn_180"); // default 'Edit Appearance' pose
        }
    }


    listen (integer ch, string s, key k, string msg) {

        if (ch == mvpch){ // from controller
        llSay(0, "Heard msg from controller: " + msg);
            if (llSubStringIndex(msg, "set ") == 0) {
                msg = llGetSubString(msg, llStringLength("set "), -1);
                //Hard-code mannequin child prim names:
                msg = "PIVOTE " + TrimRight(llList2String(llParseString2List(msg, [":"], []), 0), " ")+
                    " Touch 1.0~node="+ llList2String(llParseString2List(msg, [":"], []), 1);
                    llSay(0, "sending to my prim set this msg: "+msg);
                llMessageLinked (LINK_SET, 0, msg, NULL_KEY);
            }
        }


        if (llSubStringIndex(msg, "Node is") != -1) {
            msg = "";
        }
        if (llSubStringIndex(s, "MVP") != -1) {
            msg = "";
        }

        if (llGetSubString(msg, 0, 3) == "ask:") {
            if (llSubStringIndex(msg, "can you hear me") != -1) {
                llSay(mvpch, "node=responsiveness");
                msg = "";
            }
            if (llSubStringIndex(msg, "previous") != -1) {
                llSay(mvpch, "node=history_brief");
                msg = "";
            }
            if (llSubStringIndex(msg, "history") != -1) {
                if (llSubStringIndex(msg, "full") != -1) {
                    llSay(mvpch, "node=history_full");
                }
                else if (llSubStringIndex(msg, "brief") != -1) {
                    llSay(mvpch, "node=history_brief");
                }
                else {
                    llSay(mvpch, "node=history_full");
                }
                msg = "";
            }
            if (llSubStringIndex(msg, "pain") != -1) {
                if (llSubStringIndex(msg, "score") != -1) {
                    llSay(mvpch, "node=pain");
                }
                else if (llSubStringIndex(msg, "where") != -1) {
                    llSay(mvpch, "node=pain_where");
                }
                else if (llSubStringIndex(msg, "describe") != -1) {
                    llSay(mvpch, "node=pain_describe");
                }
                else if (llSubStringIndex(msg, "long") != -1) {
                    llSay(mvpch, "node=pain_duration");
                }
                else if (llSubStringIndex(msg, "duration") != -1) {
                    llSay(mvpch, "node=pain_duration");
                }
                else if (llSubStringIndex(msg, "change") != -1) {
                    llSay(mvpch, "node=pain_change");
                }
                else {
                    llSay(mvpch, "node=pain");
                }
                msg = "";
            }
            if (llSubStringIndex(msg, "hurt") != -1) {
                if (llSubStringIndex(msg, "score") != -1) {
                    llSay(mvpch, "node=pain");
                }
                else if (llSubStringIndex(msg, "where") != -1) {
                    llSay(mvpch, "node=pain_where");
                }
                else if (llSubStringIndex(msg, "describe") != -1) {
                    llSay(mvpch, "node=pain_describe");
                }
                else if (llSubStringIndex(msg, "long") != -1) {
                    llSay(mvpch, "node=pain_duration");
                }
                else if (llSubStringIndex(msg, "duration") != -1) {
                    llSay(mvpch, "node=pain_duration");
                }
                else if (llSubStringIndex(msg, "change") != -1) {
                    llSay(mvpch, "node=pain_change");
                }
                else {
                    llSay(mvpch, "node=pain");
                }
                msg = "";
            }
            if (llSubStringIndex(msg, "walk to the ambulance") != -1) {
                llSay(mvpch, "node=walking_escort");
                msg = "";
            }
            if (llSubStringIndex(msg, "how") != -1) {
                if (llSubStringIndex(msg, "are you") != -1) {
                    llSay(mvpch, "node=how");
                }
                else if (llSubStringIndex(msg, "old") != -1) {
                    llSay(mvpch, "node=age");
                }
                else {
                    llSay(mvpch, "node=how");
                }
                msg = "";
            }
            if (llSubStringIndex(msg, "happened") != -1) {
                llSay(mvpch, "node=happened");
                msg = "";
            }
            if (llSubStringIndex(msg, "medication") != -1) {
                llSay(mvpch, "node=medication");
                msg = "";
            }
            if (llSubStringIndex(msg, "allerg") != -1) {
                llSay(mvpch, "node=allergies");
                msg = "";
            }
            if (llSubStringIndex(msg, "name") != -1) {
                llSay(mvpch, "node=walking_escort");
                msg = "";
            }
            if (llSubStringIndex(msg, "age") != -1) {
                llSay(mvpch, "node=age");
                msg = "";
            }
            if (llSubStringIndex(msg, "stand") != -1) {
                llSay(mvpch, "node=stand");
                msg = "";
            }
            if (llSubStringIndex(msg, "move") != -1) {
                llSay(mvpch, "node=move");
                msg = "";
            }


        }


        if (msg == "show attach") {
            llMessageLinked (LINK_SET, 0, "attach show", NULL_KEY);
        }
        if (msg == "hide attach") {
            llMessageLinked (LINK_SET, 0, "attach hide", NULL_KEY);
        }
        if (msg == "show touch") {
            llMessageLinked (LINK_SET, 0, "touch show", NULL_KEY);
        }
        if (msg == "hide touch") {
            llMessageLinked (LINK_SET, 0, "touch hide", NULL_KEY);
        }
        if (msg == "show anchor") {
            llSetAlpha(1.0, ALL_SIDES);
        }
        if (msg == "hide anchor") {
            llSetAlpha(0, ALL_SIDES);
        }
        if (msg == "show all") {
            llMessageLinked (LINK_SET, 0, "attach show", NULL_KEY);
            llMessageLinked (LINK_SET, 0, "touch show", NULL_KEY);
            llSetAlpha(1.0, ALL_SIDES);
        }
        if (msg == "hide all") {
            llMessageLinked (LINK_SET, 0, "attach hide", NULL_KEY);
            llMessageLinked (LINK_SET, 0, "touch hide", NULL_KEY);
            llSetAlpha(0, ALL_SIDES);
        }
        if (msg == "tidy") {
            llSay(mvpch, "all|die");
        }
        if (msg == "give paramedic box") {
            llRezObject("MVP Paramedic Equipment Box", llGetPos()+<0,0,1>, <0,0,0>, <0,0,0,0>, 0);
            msg = "";
        }
        if (llSubStringIndex(msg, "give ") == 0) {
            string object = llGetSubString(msg, 5, -1);
            llRezObject(object, llGetPos()+<0,0,1>, <0,0,0>, <0,0,0,0>, 0);
        }


    }
    link_message (integer fm, integer i, string msg, key k) {
        if (llSubStringIndex(msg, "rez") == 0) {
            //integer intNameEnd = llSubStringIndex(msg, ".") - 2;
            string object = llGetSubString(msg, 4, -1);
            llRezObject(object, llGetPos()+<0,0,1>, <0,0,0>, <0,0,0,0>, 0);
        }
    }
}
