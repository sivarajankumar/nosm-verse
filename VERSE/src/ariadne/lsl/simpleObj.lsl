string  charset = "0123456789abcdef";

string XMLextractTag (string stream, string tag) {
    string param;
    integer sts = llSubStringIndex(stream, "<"+tag);

    //string attribChunk = llGetSubString(stream,sts + len + 1,llSubStringIndex(stream, ">");
    //integer endAttrib = llSubStringIndex(stream, attribChunk) + llStringLength(attribChunk) +1;
    integer ste = llSubStringIndex(stream, "</"+tag+">");
    integer len = llStringLength(tag);
    //sts = sts + len + 2;
    sts += len + 2;
    ste--;

    if (sts > ste) {
        param = "";
    } else {
       // param = llGetSubString(stream,sts,ste);
          string chunk =  llGetSubString(stream,sts,ste); // chunk with closing caret (ie. '>') from opening tag
            param = llGetSubString(chunk,llSubStringIndex(chunk, ">")+1,-1);// truncate to front of said caret...
    }
    llWhisper(0, "XMLextractTag: p:"+param);
    return param;
}


string XMLextractAttribByTag (string stream, string tag, string attrib) {
    string param;
    integer sts = llSubStringIndex(stream, "<"+tag);

    //string attribChunk = llGetSubString(stream,sts + len + 1,llSubStringIndex(stream, ">");
    //integer endAttrib = llSubStringIndex(stream, attribChunk) + llStringLength(attribChunk) +1;
    integer ste = llSubStringIndex(stream, "</"+tag+">");
    integer len = llStringLength(tag);
    //sts = sts + len + 2;
    sts += len + 2;
    ste--;

    if (sts > ste) {
        param = "";
    } else {
          string chunk =  llGetSubString(stream,sts,ste);
            string attribChunk = llGetSubString(chunk,0,llSubStringIndex(chunk, ">"));// truncate to front of said caret...
            if (llSubStringIndex(attribChunk, attrib) > -1){
                string myAttribChunk = llGetSubString(attribChunk,llSubStringIndex(attribChunk, attrib + "=\"") + llStringLength(attrib + "=\""),-1);
                param = llGetSubString(myAttribChunk, 0, llSubStringIndex(myAttribChunk, "\"")); // grab everything before the first double-quote
            }else{
                param = "";
            }

    }
    llWhisper(0, "XMLextractAttribByTag: p:"+param);
    return param;
}

list ListItemDelete(list mylist,string element_old) {
    integer placeinlist = llListFindList(mylist, [element_old]);
    if (placeinlist != -1)
    return llDeleteSubList(mylist, placeinlist, placeinlist);
    return mylist;
}

list ListStridedMove(list myList, integer start, integer end, integer stride, integer target) {
    if(stride <= 0) stride = 1;
    list item = llList2List(myList, start *= stride, end = ((stride * (end + 1)) - 1));
    return llListInsertList(llDeleteSubList(myList, start, end), myList = item, target * stride);
}

list ListStridedRemove(list src, integer start, integer end, integer stride) {
    return llDeleteSubList(src, start * stride, (stride * (end + 1)) - 1);
}

list ListStridedUpdate(list dest, list src, integer start, integer end, integer stride) {
    return llListReplaceList(dest, src, start * stride, ((end + 1) * stride) - 1 );
}

list ListItemReplace(list mylist,string element_old, string element_new) {
    integer placeinlist = llListFindList(mylist, [element_old]);
    list newlist = llListReplaceList(mylist, [element_new], placeinlist, placeinlist);
    return newlist;
}

list List2ListStrided(list src, integer start, integer end, integer stride) { // just to note...
    return llList2ListStrided(src,  start,  end,  stride );
}

list ListSorted(list src, integer stride, integer isAscending){
    return llListSort(src,stride,isAscending);
}

checkAttachmentSlot()
{
    integer slot = llGetAttached();
    if (slot == 0)
    {
        llOwnerSay("Not attached.");
    }

    else if (slot > 0 && slot <= 30)
    {
        llOwnerSay("Attached to the avatar.");
    }

    else if (slot > 30 && slot <= 38)
    {
        llOwnerSay("Attached to the HUD.");
    }
}
string padToFour( string inString )      //Outputs the string padded to four characters with spaces on the left
{                                                //( or cut to four characters -- pad("01234")="1234")
    //Note: this function uses a clever trick for efficiency
    integer inStringLength = llStringLength( inString );        //The length of the input string
    if ( inStringLength < 4 )
        return ( llGetSubString( "0000", 0, 3 - inStringLength ) + inString );
    else if ( inStringLength == 4 )
        return inString;
    else
        return llGetSubString( inString, inStringLength - 4, inStringLength );
}

string list2String( list inList )        //Converts a list to a string -- with no possible chance of error
{
    string outputString = llDumpList2String( inList, "" );
    integer listLength = llGetListLength( inList );
    integer baseOffset = listLength * 5;
    integer lastOffset;
    string prefixString;

    integer listElementNum;
    for ( listElementNum = 0 ; listElementNum < listLength ; ++listElementNum )
    {
        prefixString    += padToFour( (string)( baseOffset + lastOffset ) )
                        + (string)llGetListEntryType( inList, listElementNum );
        lastOffset += llStringLength( llList2String( inList, listElementNum ) );
    }

    outputString = prefixString + outputString;
    return outputString;
}

list string2List( string inString )
{
    list outputList;
    integer baseOffset = (integer)llGetSubString( inString, 0, 3 );
    integer listLength = baseOffset / 5;
    integer elementLength;
    integer elementType;
    integer listElementNum;
    for ( listElementNum = 0 ; listElementNum < listLength ; ++listElementNum )
    {
        elementType = (integer)llGetSubString( inString, listElementNum * 5 + 4, listElementNum * 5 + 4 );
        integer a = (integer)llGetSubString( inString,  listElementNum    * 5,  listElementNum    * 5 + 3 );
        integer b = (integer)llGetSubString( inString, (listElementNum+1) * 5, (listElementNum+1) * 5 + 3 );
        if (b == 0) b = llStringLength(inString);
        elementLength = b - a;
//        llOwnerSay(
//        "Element    "+(string)listElementNum+
//        " Type      "+(string)elementType+
//        " Begins at "+(string)a+
//        " ends at   "+(string)b+
//        " length    "+(string)elementLength);

        if      ( elementType == 1 )
            outputList += [ (integer)llGetSubString( inString, a, b - 1 ) ];
        else if ( elementType == 2 )
            outputList += [ (float)llGetSubString( inString, a, b - 1 ) ];
        else if ( elementType == 3 )
            outputList += [ llGetSubString( inString, a, b - 1 ) ];
        else if ( elementType == 4 )
            outputList += [ (key)llGetSubString( inString, a, b - 1 ) ];
        else if ( elementType == 5 )
            outputList += [ (vector)llGetSubString( inString, a, b - 1 ) ];
        else if ( elementType == 6 )
            outputList += [ (rotation)llGetSubString( inString, a, b - 1 ) ];
    }

    return outputList;
}

integer chr2int(string chr) { // convert unsigned charset to integer
    integer base = llStringLength(charset);
    integer i = -llStringLength(chr);
    integer j = 0;
    while(i)
        j = (j * base) + llSubStringIndex(charset, llGetSubString(chr, i, i++));
    return j;
}

string int2chr(integer int) { // convert integer to unsigned charset
    integer base = llStringLength(charset);
    string  out;
    integer j;
    if(int < 0) {
        j = ((0x7FFFFFFF & int) % base) - (0x80000000 % base);
        integer k = j % base;
        int = (j / base) + ((0x7FFFFFFF & int) / base) - (0x80000000 / base);
        out = llGetSubString(charset, k, k);
    }
    do
        out = llGetSubString(charset, j = int % base, j) + out;
    while(int /= base);
    return out;
}

vector getVecFromRot(rotation r)
{ //llRot2Axis
    vector v = <r.x, r.y, r.z>;
    if(v) //Is the vector a zero vector?
        return llVecNorm(v); //not a zero vector, so normalize it
    return v; //vector was zero.
}

float getAngleFromRot(rotation r)
{ //llRot2Angle
    return 2 * llAcos(r.s / llSqrt(r.x * r.x + r.y * r.y + r.z * r.z + r.s * r.s));
}

rotation getRotFromVecAngle(vector v, float a)
{ //llAxisAngle2Rot, not normalized
    return <v.x, v.y, v.z, 1 / llTan(a / 2)>;
}

string llVector2String(vector w)
{
    return "<" + (string)llRound(w.x) + "," + (string)llRound(w.y) + "," + (string)llRound(w.z) + ">";
}

integer DAYS_PER_YEAR    = 365;            // Non leap year
integer SECONDS_PER_DAY  = 86400;
integer SECONDS_PER_HOUR = 3600;

integer LeapYear(integer year)
{
    if (year % 4 == 0)
    {
        if (year % 100 == 0)
        {
            if (year % 400 == 0)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 1;
        }
    }
    else
    {
        return 0;
    }
}

integer DaysPerMonth(integer year,integer month)
{
    if (month < 8)
    {
        if (month % 2 == 0)
        {
            if (month == 2)
            {
                if (LeapYear(year))
                {
                    return 29;
                }
                else
                {
                    return 28;
                }
            }
            else
            {
                return 30;
            }
        }
        else
        {
            return 31;
        }
    }
    else
    {
        if (month % 2 == 0)
        {
            return 31;
        }
        else
        {
            return 30;
        }
    }
}

integer DaysPerYear(integer year)
{
    if (LeapYear(year))
        return DAYS_PER_YEAR + 1;
    else
        return DAYS_PER_YEAR;
}

list Unix2DateTime(integer unixtime)
{
    integer days_since_1_1_1970     = unixtime / SECONDS_PER_DAY;
    integer day = days_since_1_1_1970 + 1;    // Add 1 day since days are 1 based, not 0!
    integer year  = 1970;
    integer days_per_year = DaysPerYear(year);

       // Calculate year, month and day

    while (day > days_per_year)
    {
        day -= days_per_year;
        ++year;
        days_per_year = DaysPerYear(year);
    }

    integer month = 1;
    integer days_per_month = DaysPerMonth(year,month);

    while (day > days_per_month)
    {
        day -= days_per_month;

        if (++month > 12)
        {
            ++year;
            month = 1;
        }

        days_per_month = DaysPerMonth(year,month);
    }

        // Calculate the hours, minutes and seconds

    integer seconds_since_midnight  = unixtime % SECONDS_PER_DAY;
    integer hour        = seconds_since_midnight / SECONDS_PER_HOUR;
    integer second     = seconds_since_midnight % SECONDS_PER_HOUR;
    integer minute    = second / (SECONDS_PER_HOUR / 60);
    second          = second % (SECONDS_PER_HOUR / 60);

    return [ year, month, day, hour, minute, second ];
}

///////////////////////////////// MonthName() ////////////////////////////

list MonthNameList = [     "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                        "JUL", "AUG", "SEP", "OCT" , "NOV", "DEC" ];

string MonthName(integer month)
{
    if (month >= 0 && month < 12)
        return llList2String(MonthNameList, month);
    else
        return "";
}

///////////////////////////////// DateString() ///////////////////////////

string DateString(list timelist)
{
    integer year       = llList2Integer(timelist,0);
    integer month      = llList2Integer(timelist,1);
    integer day        = llList2Integer(timelist,2);

    return (string)day + "-" + MonthName(month - 1) + "-" + (string)year;
}

///////////////////////////////// TimeString() ////////////////////////////

string TimeString(list timelist)
{
    integer hour          = llList2Integer(timelist,3);
    integer minute         = llList2Integer(timelist,4);
    integer second         = llList2Integer(timelist,5);
    string  hourstr     = (string)hour;
    string  minutestr   = (string)minute;
    string  secondstr   = (string)second;

    if (hour < 10)         hourstr     = "0" + hourstr;
    if (minute < 10)     minutestr     = "0" + minutestr;
    if (second < 10)    secondstr    = "0" + secondstr;
    return hourstr + ":" + minutestr + ":" + secondstr;
}

integer IsValidKeyFormat(string str)
{
    string keychars = "0123456789abcdef";

    if (llStringLength(str) != 36)
        return FALSE;

    if (   (llGetSubString( str, 8, 8 )    != "-" ||
             llGetSubString( str, 13, 13 ) != "-" ||
            llGetSubString( str, 18, 18 ) != "-" ||
            llGetSubString( str, 23, 23 ) != "-" ) )
        return FALSE;

    integer i;

    for (i = 0; i < 8; ++i)
    {
        if (llSubStringIndex(keychars, llGetSubString(str,i,i)) == -1)
            return FALSE;
    }

    for (i = 9; i < 13; ++i)
    {
        if (llSubStringIndex(keychars, llGetSubString(str,i,i)) == -1)
            return FALSE;
    }

    for (i = 14; i < 18; ++i)
    {
        if (llSubStringIndex(keychars, llGetSubString(str,i,i)) == -1)
            return FALSE;
    }

    for (i = 19; i < 23; ++i)
    {
        if (llSubStringIndex(keychars, llGetSubString(str,i,i)) == -1)
            return FALSE;
    }

    for (i = 24; i < 36; ++i)
    {
        if (llSubStringIndex(keychars, llGetSubString(str,i,i)) == -1)
            return FALSE;
    }

    return TRUE;
}
string DIGITS="0123456789";

integer IsValidFloatFormat(string rawvalue)
{
    string  value=llStringTrim(rawvalue,STRING_TRIM);
    integer len=llStringLength(value);
    integer ndigits=0;
    integer nperiods=0;
    string  char=llGetSubString(value,0,0);
    integer i=0;

    // Skip leading sign
    if (char == "+" || char== "-")
        ++i;

    for(; i < len; ++i)
    {
        char=llGetubString(value,i,i);
        if (llSubStringIndex(DIGITS,char) != -1)
        {
            ++ndigits;
        }
        else if (char == ".")
        {
            if (++nperiods > 1)
                return FALSE;
        }
        else
        {
            return FALSE;
        }
    }

    // A float consists of minimal 1 digit and must have a period
    if (ndigits < 1 || nperiods != 1)
        return FALSE;

    return TRUE;
}

//usage InSim(llDetectedKey(0))
float InSim(key id)
{
     return (llGetObjectMass(id));
}


string ASCII = "             \n                   !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
integer ord(string chr)
{
    if(llStringLength(chr) != 1) return -1;
    if(chr == " ") return 32;
    return llSubStringIndex(ASCII, chr);
}
string chr(integer i)
{
    i %= 127;
    return llGetSubString(ASCII, i, i);
}

default
{
    state_entry()
    {
        //llSay(0, "Hello, Avatar!");
    }

    touch_start(integer total_number)
    {
        llSay(687686, "node=shapechoice");
    }
}
