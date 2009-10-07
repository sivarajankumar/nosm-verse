
string continuance;
list continuances;
integer continuance_count;
integer continuance_index = 0;
integer gCurrentContinuanceChannel;
list continuance_times;
list continuance_targets;


list sequence_continuances;
list sequence_times;
integer sequence_index;
integer sequence_count;
float time_to_next_sequence;

float gSensorRange = 196.0;
float gSoundVolume = 0.8;

integer gFauxIMChannel = -696969; // never gets used as channel, just a mask

integer gPIVOTEChannel = 687686;

integer gAttachChannel = 687687;

//integer gHUDChannel = 72; // needed? can determine chatter by key, safer than checking this

integer handle = -1;
integer handleHUD = -99;

string Letters= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
string Numbers = "0123456789.";


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

playContinuance(){

    if (llGetInventoryType(continuance) == INVENTORY_SOUND) {
        llSetSoundQueueing(TRUE);
        llSetSoundRadius(gSensorRange);
        llPlaySound(continuance, gSoundVolume);
        // llLoopSound, llTriggerSound, llTriggerSoundLimited() ???
    }else{
            if (llGetInventoryType(continuance) == INVENTORY_ANIMATION) {
                llStartAnimation(continuance);
            } else{ // it's another object, with hard-coded duration:
                float continuance_duration = (float)llList2String(continuance_times, continuance_index);
                if (llGetInventoryType(continuance) == INVENTORY_OBJECT && continuance_duration > 0) //
                {
                    llSetTimerEvent(continuance_duration);
                }
            }
    }
}

runSequence(){
    if (llGetInventoryType(continuance) == INVENTORY_ANIMATION) {
        llStopAnimation(continuance);
    }else {
            if (llGetInventoryType(continuance) == INVENTORY_SOUND) {
                llStopSound();
            }
    }

    if (!sequence_count && continuance_count > 1){
        continuance_index = 0;
        continuance = llList2String(continuances, continuance_index);
    } else {
            llSetTimerEvent(0);
        sequence_index = 0;
        continuance = llList2String(sequence_continuances, sequence_index);
    }

    playContinuance();

    if (sequence_count) {
        llSetTimerEvent(time_to_next_sequence);
    }


} //end runSequence


assignSL(string type, string name, string val){

    if (type == "slinnersequence"){
        sequence_index = 0; // see timer event
        sequence_continuances = [];
        sequence_times = [];

        list parts = llParseString2List(val, ["," ,"~"], []);
        integer n = llGetListLength(parts);
        integer i;
        for(i = 0; i < n; i++){
            string seqStr = llList2String(parts, i);
            if(IsNumeric( seqStr)) {
                sequence_times += [seqStr];
            }else{ //is name
                    sequence_continuances += [seqStr];
            }
        }
        sequence_count = llGetListLength(sequence_continuances);
        continuance = llList2String(sequence_continuances,0);
        time_to_next_sequence = llList2Float(sequence_times, 0);
        runSequence();
        jump out;
    }

    //scene loaded, run playlist
    // if user-triggered (ie.clickable) object, wait for obj's onSit to tell us

    if (type == "slanimation"){


            if (continuance != "") llStopAnimation(continuance);
            llSetTimerEvent(0);
            continuance = llStringTrim(name,STRING_TRIM);
            runSequence();


        llMessageLinked(LINK_SET,0,"ON",NULL_KEY); // call chatbot to change color of bracelet?? if so, call llSetColor cmd
        llRequestPermissions(llGetOwner(),PERMISSION_TAKE_CONTROLS | PERMISSION_TRIGGER_ANIMATION);
        llTakeControls(CONTROL_ML_LBUTTON | CONTROL_LBUTTON | CONTROL_UP | CONTROL_FWD
        | CONTROL_BACK | CONTROL_ROT_LEFT | CONTROL_LEFT | CONTROL_RIGHT
        | CONTROL_ROT_RIGHT | CONTROL_DOWN, TRUE, TRUE);

        llStartAnimation(name);
        jump out;

    }

    if (type == "slbodypart"){
        if(llGetInventoryType(name) != INVENTORY_NONE && llGetInventoryType(name) == INVENTORY_BODYPART){
            llGiveInventory(llGetOwner(), name);
            llInstantMessage(llGetOwner(), name + " has been added to your inventory. "
                +"Drag it to appropriate area on your avatar to wear it. ");
        }
        jump out;
    }

    if (type == "slsound"){
        if(llGetInventoryType(name) != INVENTORY_NONE && llGetInventoryType(name) == INVENTORY_SOUND){
           // llTriggerSound(name,gSoundVolume);

            // if duration > sound length
                //llLoopSound(name,gSoundVolume);
                llSetSoundQueueing(TRUE);
            //}

            llSetSoundRadius(gSensorRange);
            llPlaySound(name, gSoundVolume);
        }
        jump out;

    }

    if (type == "slobject"){
        //show dialog on where to attach this object
        if(llGetInventoryType(name) != INVENTORY_NONE && llGetInventoryType(name) == INVENTORY_OBJECT){
            llGiveInventory(llGetOwner(),name);
            llInstantMessage(llGetOwner(), name + " has been added to your inventory. "
                +"Drag it to the ground to rez it");
        }
        //llAttachToAvatar(integer)
        jump out;
    }

    if (type == "slpoint"){
        llPointAt((vector)((string)"<"+val+">"));
        jump out;
    }

    if (type == "slhud"){
        if(llGetInventoryType(name) != INVENTORY_NONE && llGetInventoryType(name) == INVENTORY_OBJECT){
            //deternine target, get ther key
            llGiveInventory(llGetOwner(), "HUD_"+name);
            llInstantMessage(llGetOwner(), "The "+ name +" HUD has been added to your inventory. "
                +"Please attach it to the " + val + "of your display.");
        }
        jump out;
    }

    if (type == "slpackage"){
        if(llGetInventoryType(name) != INVENTORY_NONE &&  llGetInventoryType(name) == INVENTORY_OBJECT){
            //deternine target, get ther key
            llGiveInventory(llGetOwner(), "crate_"+name);
            llInstantMessage(llGetOwner(), "The " +name + "crate has been added to your inventory. "
                +"Drag it to the ground to rez it, and right-click to open it.");

        }
        jump out;
    }

    if (type == "slmove"){
        vector dest = (vector)("<"+llList2String(llParseString2List(val,["~"],[]),1)+">");
        do //Do-while loop.
        {
            llPushObject(llGetOwner(),(dest-llGetPos())*(llVecDist(llGetPos(),dest)),ZERO_VECTOR,FALSE); //Pushes the avatar to the position.
            llMoveToTarget(dest,0.05); //If your agent gets close to the avatar it will direct the path.
        }
        while(llVecDist(dest,llGetPos()) > 40.0); //End of do-while loop.

        llMoveToTarget(dest,0.05); //Movement
        llSleep(0.25); //Prevents you from flying.
        llStopMoveToTarget(); //Stops the movement
    }

    @out;

}


default
{
    state_entry(){
        state playing;
    }

    attach(key attached)
    {
        if(attached == llGetOwner())
        {
            llListen(0,"",llGetOwner(),"");
            llListen(gPIVOTEChannel,"",NULL_KEY,"");

            llSetStatus(STATUS_BLOCK_GRAB,TRUE);
           // atk = FALSE;
            //llRequestPermissions(llGetOwner(),PERMISSION_TAKE_CONTROLS | PERMISSION_TRIGGER_ANIMATION);
            // llMessageLinked(LINK_SET,0,"OFF",NULL_KEY);
            state playing;

        }
    }


}

state scanning
{
    state_entry(){
        state playing;
    }

    sensor(integer num_detected)
    {
       // llSay(0, "found the controller!");
        if (handle== -1){
            handle  = llListen( gAttachChannel, "", NULL_KEY, "" );

        }

       if( handleHUD == -99){
            handleHUD =  llListen(72,"","",""); // listen for HUD
        }
    }

    listen (integer ch, string s, key k, string msg) {

        if (ch == gAttachChannel){
            list parts = llParseString2List(msg, ["~"], []);

            key iKey = (key)llList2String(parts, 0);
            string itype =  llList2String(parts, 1);
            string iname = llList2String(parts, 2);
            string ival = llList2String(parts, 3);

            // parse from msg the player key, if yours, go:
            if (llGetOwner() == iKey || llGetOwnerKey(iKey) == llGetOwner())
            {
                llSay(0,"itype, iname, ival: "+ itype + iname + ival);
                  assignSL(itype, iname, ival);
                  state playing;
            }
        }else {
            if (k == llGetOwner()  || llGetOwnerKey(k) == llGetOwner()){ // from the HUD
                //llGetParcelPrimOwners(vector)
            }
        }

    }




}

state playing
{

    state_entry(){
        handle = -1;
        handleHUD= -99;
         llSetTimerEvent(1); //Repeats the sensor every 1 second.

    }



    run_time_permissions(integer perms)
    {
        if(perms & PERMISSION_TAKE_CONTROLS)
        {
            llTakeControls(CONTROL_ML_LBUTTON | CONTROL_LBUTTON | CONTROL_UP | CONTROL_FWD | CONTROL_BACK | CONTROL_ROT_LEFT | CONTROL_LEFT | CONTROL_RIGHT | CONTROL_ROT_RIGHT | CONTROL_DOWN, TRUE, TRUE);
        }
    }



    listen (integer ch, string s, key k, string msg) {

        if (ch == gAttachChannel){

            if (msg == "REQUEST_CONTROL") // what does this do??
            {
                llWhisper(gAttachChannel,"GRANT_CONTROL," + llList2CSV(continuances));
                return;
            }

            list parts = llParseString2List(msg, ["~"], []);

            key iKey = (key)llList2String(parts, 0);
            string itype =  llList2String(parts, 1);
            string iname = llList2String(parts, 2);
            string ival = llList2String(parts, 3);

            // parse from msg the player key, if yours, go:
            if (llGetOwner() == iKey || llGetOwnerKey(iKey) == llGetOwner())
            {
                llSay(0,"itype, iname, ival: "+ itype+ iname+ ival);
                  assignSL(itype, iname, ival);
            }
        }else {
            if (k == llGetOwner()  || llGetOwnerKey(k) == llGetOwner()){ // from the HUD
                //llGetParcelPrimOwners(vector)
            }
        }

    }


    timer()
    {
        // are we within range of the controller
        //llSensor("PIVOTE Controller NOSSUM",NULL_KEY, ACTIVE, 95,PI);

        //TODO: untested, prob needs lots of work
        if (!sequence_count && continuance_count > 1){
            ++continuance_index;
            continuance = llList2String(continuances, continuance_index);
            playContinuance();
            llSetTimerEvent(llList2Float(continuance_times, continuance_index));
        } else {
                if (sequence_index == sequence_count - 1) {
                    sequence_index = 0;
                }else{
                        ++sequence_index;
                }
            continuance = llList2String(sequence_continuances, sequence_index);
            playContinuance();
            llSetTimerEvent(llList2Float(sequence_times, sequence_index));

        }



    }

    sensor(integer num_detected)
    {
       // llSay(0, "found the controller!");
        if (handle== -1){
            handle  = llListen( gAttachChannel, "", NULL_KEY, "" );

        }

       if( handleHUD == -99){
            handleHUD =   llListen(72,"","",""); // listen for HUD
        }
    }
}

