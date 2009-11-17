function valide_start_color(){
	if (document.data.start_color_r.value > 1 || document.data.start_color_r.value < 0 || IsNumeric(document.data.start_color_r.value) == 0){
		alert("Start Color RED must be a value between 0 and 1 (included.)");
		document.data.start_color_r.value = "0";
	}

	if (document.data.start_color_g.value > 1 || document.data.start_color_g.value < 0 || IsNumeric(document.data.start_color_g.value) == 0){
		alert("Start Color GREEN must be a value between 0 and 1 (included.)");
		document.data.start_color_g.value = "0";
	}

	if (document.data.start_color_b.value > 1 || document.data.start_color_b.value < 0 || IsNumeric(document.data.start_color_b.value) == 0){
		alert("Start Color BLUE must be a value between 0 and 1 (included.)");
		document.data.start_color_b.value = "0";
	}
	mycol ='#' + tohex(document.data.start_color_r.value*255)  +  tohex(document.data.start_color_g.value*255)+  tohex(document.data.start_color_b.value*255);
	document.all.start_col.style.backgroundColor = mycol;
}

function valide_end_color(){
	if (document.data.end_color_r.value > 1 || document.data.end_color_r.value < 0 || IsNumeric(document.data.end_color_r.value) == 0){
		alert("End Color RED must be a value between 0 and 1 (included.)");
		document.data.end_color_r.value = "0";
	}

	if (document.data.end_color_g.value > 1 || document.data.end_color_g.value < 0 || IsNumeric(document.data.end_color_g.value) == 0){
		alert("End Color GREEN must be a value between 0 and 1 (included.)");
		document.data.end_color_g.value = "0";
	}

	if (document.data.end_color_b.value > 1 || document.data.end_color_b.value < 0 || IsNumeric(document.data.end_color_b.value) == 0){
		alert("End Color BLUE must be a value between 0 and 1 (included.)");
		document.data.end_color_b.value = "0";
	}
	mycol ='#' + tohex(document.data.end_color_r.value*255)  +  tohex(document.data.end_color_g.value*255)+  tohex(document.data.end_color_b.value*255);
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

function GetBool(valONOFF) {
	rez = "TRUE";
	if (valONOFF == false){
		rez = "FALSE";
	}
	return rez;
}

function GenCode(){
ttt = "";
if (document.data.ftcname.value != ""){
	if (document.data.target.checked == true && document.data.target_key.value == "parameter"){
		ttt = ttt + document.data.ftcname.value +" (key myTarget){\n";
	}else{
		ttt = ttt + document.data.ftcname.value +" (){\n";
	}
}else{
	alert("Function name can't be null !");
}

if (document.data.ainv_sltexture.value != ""){
	ttt = ttt +'Texture = "'+ document.data.ainv_sltexture.value +'";\n';
}else{
	alert("Texture can't be null !");
}


ttt = ttt +"Interpolate_Scale = "+GetBool(document.data.interpolate_scale.checked)+";\n";
ttt = ttt +"Start_Scale = <" + document.data.start_scale_x.value + "," + document.data.start_scale_y.value + ", 0>;\n";
ttt = ttt +"End_Scale = <" + document.data.end_scale_x.value + "," + document.data.end_scale_y.value + ", 0>;\n";
ttt = ttt +"Interpolate_Colour = "+GetBool(document.data.interpolate_color.checked)+";\n";
ttt = ttt +"Start_Colour = < " + document.data.start_color_r.value + ", " + document.data.start_color_g.value + ", " + document.data.start_color_b.value + " >;\n";
ttt = ttt +"End_Colour = < " + document.data.end_color_r.value + ", " + document.data.end_color_g.value + ", " + document.data.end_color_b.value + " >;\n";
ttt = ttt +"Start_Alpha = " + document.data.start_alpha.value + ";\n";
ttt = ttt +"End_Alpha =" + document.data.end_alpha.value + ";\n";
ttt = ttt +"Emissive = "+GetBool(document.data.emissive.checked)+";\n";
ttt = ttt +"Age = " + document.data.age.value + ";\n";
ttt = ttt +"Rate = " + document.data.rate.value + ";\n";
ttt = ttt +"Count = " + document.data.count.value + ";\n";
ttt = ttt +"Life = " + document.data.life.value + ";\n";
ttt = ttt +"Pattern = " + document.data.patern.value + ";\n";
ttt = ttt +"Radius = " + document.data.radius.value + ";\n";
ttt = ttt +"Begin_Angle = " + (document.data.begin_angle.value * 3.14159 / 180) + ";\n";
ttt = ttt +"End_Angle = " + (document.data.end_angle.value * 3.14159 / 180) + ";\n";
ttt = ttt +"Omega = < " + document.data.omega_x.value + ", " + document.data.omega_y.value + ", " + document.data.omega_z.value + " >;\n";
ttt = ttt +"Follow_Source = "+GetBool(document.data.follow_source.checked)+";\n";
ttt = ttt +"Follow_Velocity = "+GetBool(document.data.follow_velocity.checked)+";\n";
ttt = ttt +"Wind = "+GetBool(document.data.wind.checked)+";\n";
ttt = ttt +"Bounce = "+GetBool(document.data.bounce.checked)+";\n";
ttt = ttt +"Minimum_Speed = " + document.data.min_speed.value + ";\n";
ttt = ttt +"Maximum_Speed = " + document.data.max_speed.value + ";\n";
ttt = ttt +"Acceleration = < " + document.data.acceleration_x.value + ", " + document.data.acceleration_y.value + ", " + document.data.acceleration_z.value + " >;\n";
ttt = ttt +"Target = "+GetBool(document.data.target.checked)+";\n";

if (document.data.target.checked == false){
	ttt = ttt +"Target_Key = NULL_KEY;\n";
}else{
	if (document.data.target_key.value == "parameter"){
		ttt = ttt +"Target_Key = myTarget;\n";
	}

	if (document.data.target_key.value == "object"){
		ttt = ttt +"Target_Key =  llGetKey ();\n";
	}

	if (document.data.target_key.value == "owner"){
		ttt = ttt +"Target_Key = llGetOwner ();\n";
	}

}


if (document.data.target.checked == true && document.data.target_key.value == "parameter"){
	ttt = ttt +"key MyTraget_key = NULL_KEY;"+"/"+"/ Replace by your value;\n";
	ttt = ttt + document.data.ftcname.value + " (MyTraget_key); "+"/"+"/Start the Particles\n";
}else{
	ttt = ttt + document.data.ftcname.value +" ();\n";
}

document.data.generatedcode.value = ttt;
}
