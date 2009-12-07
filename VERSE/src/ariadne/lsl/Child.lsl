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

//Single activation via dialog box

string gOption = "optionNotSetException";
integer gOneShot = TRUE;
string gNode = "start";
string gMsgOneShot = "";

// change only if menu's clash
integer menuch = 550;

// change only for whole system
integer mvpch = 687686;

// do not change below this line
integer gActivated = FALSE;

announceOptionSelected(key _id, string _option) {
    string _clickerName = llKey2Name(_id);
    llSay(0, _clickerName + " has chosen the following option: " + _option);
}

announceOptions(key _id, string _msg, list _options) {

    string _clickerName;
    string _optionsMsg;
    integer _i;

    //Code below allows dialogue options to be output to chat
    _clickerName = llKey2Name(_id);
    llSay(0, _clickerName + " has selected the " + llKey2Name(llGetKey()) + ". Please discuss the following options as a group before deciding what to do.");
    _optionsMsg = "Choose from: ";
    for (_i=0; _i < llGetListLength(_options); _i++) {
            _optionsMsg += llList2String(_options, _i) + ", ";
    }
    _optionsMsg = llGetSubString(_optionsMsg, 0, llStringLength(_optionsMsg) - 3);
    llSay(0, _msg + " " + _optionsMsg);

}

default
{
    state_entry()
    {
        //llListen(menuch, "", NULL_KEY, "");
    }



    link_message(integer fm, integer i, string s,key k) {

        string myname = llList2String(llGetObjectDetails(llDetectedKey(0), ([OBJECT_NAME,
                    OBJECT_DESC, OBJECT_POS, OBJECT_ROT, OBJECT_VELOCITY,
                    OBJECT_OWNER, OBJECT_GROUP, OBJECT_CREATOR])),0); // this obj's name
        llSay(0, "this object's name" + myname);

        if (llSubStringIndex(s,myname) !=  -1) {
            llSay(0, "msg " + s + " contains  my name: " + myname);
             if (llSubStringIndex(s,myname) ==  0) {
                    llSay(0, "msg " + s + " starts with my name: " + myname);
                    string pn = llList2String(llParseString2List(s, ["node="], []), 1);
                    gNode = llGetSubString(pn, 0, llSubStringIndex(pn,"&name"));
                    gOption = llGetSubString(pn, llSubStringIndex(pn,"&name") + llStringLength("&name="),-1);

                    llSay(0, "this part " + myname + " is now assigned the link " + gNode + " called: " + gOption);
 					state active;
             }
        }

        if (s == "touch show") {
            llSetAlpha(0.3, ALL_SIDES);
        }
        if (s == "touch hide") {
            llSetAlpha(0.0, ALL_SIDES);
        }
    }
}

state active
{
    state_entry()
    {
        llListen(menuch, "", NULL_KEY, "");
    }

    touch_start(integer total_number)
    {
        if (!gActivated || !gOneShot) {
            string msg = "What do you want to do?\n\n"+gOption;
            list buttons = [gOption];
            announceOptions(llDetectedKey(0), msg, buttons);
            llDialog(llDetectedKey(0), msg, buttons, menuch);
        } else {
            llSay(0, gMsgOneShot);
        }
    }
    listen(integer c, string n, key id, string msg) {
        if (msg == gOption) {
            announceOptionSelected(id, msg);
            llSay(mvpch, "node="+gNode);
            gActivated = TRUE;
            state default;
        }
    }
    link_message(integer fm, integer i, string s,key k) {
        if (s == "touch show") {
            llSetAlpha(0.3, ALL_SIDES);
        }
        if (s == "touch hide") {
            llSetAlpha(0.0, ALL_SIDES);
        }
    }
}