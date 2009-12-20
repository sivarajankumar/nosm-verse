function valide_start_color(){
	if ($("input[name='start_color_r']").getValue() > 1 || $("input[name='start_color_r']").getValue() < 0
	|| IsNumeric($("input[name='start_color_r']").getValue()) == 0){
		alert("Start Color RED must be a value between 0 and 1 (included.)");
		$("input[name='start_color_r']").getValue() = "0";
	}

	if ($("input[name='start_color_g']").getValue() > 1 || $("input[name='start_color_g']").getValue() < 0
	|| IsNumeric($("input[name='start_color_g']").getValue()) == 0){
		alert("Start Color GREEN must be a value between 0 and 1 (included.)");
		$("input[name='start_color_g']").getValue() = "0";
	}

	if ($("input[name='start_color_b']").getValue() > 1 || $("input[name='start_color_b']").getValue() < 0
	|| IsNumeric($("input[name='start_color_b']").getValue()) == 0){
		alert("Start Color BLUE must be a value between 0 and 1 (included.)");
		$("input[name='start_color_b']").getValue() = "0";
	}
	mycol ='#' + tohex($("input[name='start_color_r']").getValue()*255)  +
	tohex($("input[name='start_color_g']").getValue()*255)+
	tohex($("input[name='start_color_b']").getValue()*255);
	document.all.start_col.style.backgroundColor = mycol;
}

function valide_end_color(){
	if ($("input[name='end_color_r']").getValue() > 1 || $("input[name='end_color_r']").getValue() < 0
	|| IsNumeric($("input[name='end_color_r']").getValue()) == 0){
		alert("End Color RED must be a value between 0 and 1 (included.)");
		$("input[name='end_color_r']").getValue() = "0";
	}

	if ($("input[name='end_color_g']").getValue() > 1 || $("input[name='end_color_g']").getValue() < 0
	|| IsNumeric($("input[name='end_color_g']").getValue()) == 0){
		alert("End Color GREEN must be a value between 0 and 1 (included.)");
		$("input[name='end_color_g']").getValue() = "0";
	}

	if ($("input[name='end_color_b']").getValue() > 1 || $("input[name='end_color_b']").getValue() < 0
	|| IsNumeric($("input[name='end_color_b']").getValue()) == 0){
		alert("End Color BLUE must be a value between 0 and 1 (included.)");
		$("input[name='end_color_b']").getValue() = "0";
	}
	mycol ='#' + tohex($("input[name='end_color_r']").getValue()*255)  +
	tohex($("input[name='end_color_g']").getValue()*255)+
	tohex($("input[name='end_color_b']").getValue()*255);
	document.all.end_col.style.backgroundColor = mycol;
}

function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=1;
   var Char;
   for (i = 0; i < sText.length && IsNumber == true; i++)
      {
      Char = sText.charAt(i);
      if (ValidChars.indexOf(Char) == -1)
         {
         IsNumber = 0;
         }
      }
   return IsNumber;
   }


   function hexdec(f1) {
      f1.hexval.value = f1.hexval.value.toUpperCase();
      rval = parseInt(f1.hexval.value,16);
      f1.result.value=rval;
   }
   function dechex(f1) {
      rval= f1.redval.value;
      rhex = tohex(rval);
      f1.hexnot.value = rhex;
   }
   function tohex(i) {
      a2 = ''
      ihex = hexQuot(i);
      idiff = eval(i + '-(' + ihex + '*16)')
      a2 = itohex(idiff) + a2;
      while( ihex >= 16) {
         itmp = hexQuot(ihex);
         idiff = eval(ihex + '-(' + itmp + '*16)');
         a2 = itohex(idiff) + a2;
         ihex = itmp;
      }
      a1 = itohex(ihex);
      return a1 + a2 ;
   }
   function hexQuot(i) {
      return Math.floor(eval(i +'/16'));
   }

   function itohex(i) {
      if( i == 0) {
         aa = '0' }
      else { if( i== 1) {
                aa = '1'}
         else {if( i== 2) {
                  aa = '2'}
            else {if( i == 3) {
                     aa = '3' }
               else {if( i== 4) {
                        aa = '4'}
                  else {if( i == 5) {
                           aa = '5' }
                     else {if( i== 6) {
                              aa = '6'}
                        else {if( i == 7) {
                                 aa = '7' }
                           else {if( i== 8) {
                                    aa = '8'}
                              else {if( i == 9) {
                                        aa = '9'}
                                 else {if( i==10) {
                                          aa = 'A'}
                                    else {if( i==11) {
                                             aa = 'B'}
                                       else {if( i==12) {
                                                aa = 'C'}
                                          else {if( i==13) {
                                                   aa = 'D'}
                                             else {if( i==14) {
                                                      aa = 'E'}
                                                else {if( i==15) {
                                                         aa = 'F'}

                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
      return aa
   }


function GenCode(){

outStr = "";
outStr = outStr +"IS="+$("input[name='interpolate_scale']").getValue()+"~";
outStr = outStr +"SS=<" + $("input[name='start_scale_x']").getValue() + "," + $("input[name='start_scale_y']").getValue() + ", 0>~";
outStr = outStr +"ES=<" + $("input[name='end_scale_x']").getValue() + "," + $("input[name='end_scale_y']").getValue() + ", 0>~";
outStr = outStr +"IC="+$("input[name='interpolate_color']").getValue()+"~";
outStr = outStr +"SC=< " + $("input[name='start_color_r']").getValue() + ", " + $("input[name='start_color_g']").getValue() + ", " + $("input[name='start_color_b']").getValue() + " >~";
outStr = outStr +"EC=< " + $("input[name='end_color_r']").getValue() + ", " + $("input[name='end_color_g']").getValue() + ", " + $("input[name='end_color_b']").getValue() + " >~";
outStr = outStr +"SA=" + $("input[name='start_alpha']").getValue() + "~";
outStr = outStr +"EA=" + $("input[name='end_alpha']").getValue() + "~";
outStr = outStr +"E="+$("input[name='emissive']").getValue()+"~";
outStr = outStr +"A=" + $("input[name='age']").getValue() + "~";
outStr = outStr +"R=" + $("input[name='rate']").getValue() + "~";
outStr = outStr +"C=" + $("input[name='count']").getValue() + "~";
outStr = outStr +"L=" + $("input[name='life']").getValue() + "~";
outStr = outStr +"P=" + $("select[name='patern']").getValue() + "~";
outStr = outStr +"RD=" + $("input[name='radius']").getValue() + "~";
outStr = outStr +"BAN=" + ($("input[name='begin_angle']").getValue() * 3.14159 / 180) + "~";
outStr = outStr +"EAN=" + ($("input[name='end_angle']").getValue() * 3.14159 / 180) + "~";
outStr = outStr +"O=<" + $("input[name='omega_x']").getValue() + ", " + $("input[name='omega_y']").getValue() + ", " + $("input[name='omega_z']").getValue() + " >~";
outStr = outStr +"FS="+$("input[name='follow_source']").getValue()+"~";
outStr = outStr +"FV="+$("input[name='follow_velocity']").getValue()+"~";
outStr = outStr +"W="+$("input[name='wind']").getValue()+"~";
outStr = outStr +"B="+$("input[name='bounce']").getValue()+"~";
outStr = outStr +"MIS=" + $("input[name='min_speed']").getValue() + "~";
outStr = outStr +"MAS=" + $("input[name='max_speed']").getValue() + "~";
outStr = outStr +"AC=< " + $("input[name='acceleration_x']").getValue() + ", " + $("input[name='acceleration_y']").getValue() + ", " + $("input[name='acceleration_z']").getValue() + " >~";
outStr = outStr +"TG="+$("input[name='target']").getValue()+"~";

if ($("input[name='target']").getValue() == "TRUE"){
	var strParam = "";
		if($("input[name='target_key']").getValue() == "parameter"){
					strParam = $("input[name='target_param1']").getValue() +"-"+
					$("input[name='target_param2']").getValue() +"-"+
					$("input[name='target_param3']").getValue() +"-"+
					$("input[name='target_param4']").getValue() +"-"+
					$("input[name='target_param5']").getValue();
		}else{
			if ($("input[name='target_key']").getValue() == "owner") {
					strParam = $("select[name='userlist_particle']").getValue();
			}else{
				if ($("input[name='target_key']").getValue() == "controller"){
					strParam = "2bb64898-bc05-7697-ea07-0854bb64226d";
				}
			}
		}
		outStr = outStr +"TGK="+strParam+"~";
		outStr = outStr + "FN=" + $("input[name='ftcname']").getValue() +" (key "+ strParam+")~";
}else{
	outStr = outStr +"TGK=NULL_KEY~";
	outStr = outStr + "FN=" +$("input[name='ftcname']").getValue() +" ()~";
}

if ($("input[name='ainv_sltexture']").getValue() != ""){
	outStr = outStr +"TX="+ $("select[name='ainv_sltexture']").getValue() +"~";
}else{
	//alert("Texture can't be null !");
}

$("input[name='generatedcode']").setValue(outStr);
}
