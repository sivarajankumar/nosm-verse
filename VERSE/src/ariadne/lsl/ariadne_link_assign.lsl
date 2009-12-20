integer mvpch = -9898;
string gNode;

default
{
    state_entry()
    {
        llListen(mvpch, "", NULL_KEY, "");
    }

    
    listen(integer channel, string name, key id, string msg) {

        string myname = llList2String(llGetObjectDetails(llGetKey(), ([OBJECT_NAME,
                    OBJECT_DESC, OBJECT_POS, OBJECT_ROT, OBJECT_VELOCITY,
                    OBJECT_OWNER, OBJECT_GROUP, OBJECT_CREATOR])),0); // this obj's name

        if (channel==mvpch){
            llSay(0, "Heard msg from controller: " + msg);
            if (llSubStringIndex(msg, "set ") == 0) {
                msg = llGetSubString(msg, llStringLength("set "), -1);
                
                string thisname = llList2String(llParseString2List(msg, [":"], []), 0);
               if (llSubStringIndex(thisname, myname) !=  -1) {
                      gNode= llList2String(llParseString2List(msg, [":"], []), 1);
                      state active;
               }
            }
        
        }
    }
}

state active
{
    
   touch_start(integer total_number)
    {
      //  llSay(0, "node="+gNode);
         llSay(687686, "node="+gNode);
         gNode = "";
         state default;
    }
}
