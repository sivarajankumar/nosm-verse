function valide_color(m) {
    var aClr = ['red', 'green', 'blue'];
    for (i = 0; i < aClr.length; i++) {
        var sc = document.getElementById("ps" + m.substring(0, 1) + "c" + aClr[i].substring(0, 1));
        if (sc.value > 1 || sc.value < 0 || IsNumeric(sc.value) == 0) {
            alert(m + " color " + aClr[i] + " must be a value between 0 and 1 (inclusive)");
            sc.value = "0";
        }
    }
}

function IsNumeric(sText) {
    var ValidChars = "0123456789.";
    var IsNumber = 1;
    var Char;


    for (i = 0; i < sText.length && IsNumber == true; i++) {
        Char = sText.charAt(i);
        if (ValidChars.indexOf(Char) == -1) {
            IsNumber = 0;
        }
    }
    return IsNumber;

}


function hexdec(f1) {
    f1.hexval.value = f1.hexval.value.toUpperCase();
    rval = parseInt(f1.hexval.value, 16);
    f1.result.value = rval;
}
function dechex(f1) {
    rval = f1.redval.value;
    rhex = tohex(rval);
    f1.hexnot.value = rhex;
}
function tohex(i) {
    a2 = ''
    ihex = hexQuot(i);
    idiff = eval(i + '-(' + ihex + '*16)')
    a2 = itohex(idiff) + a2;
    while (ihex >= 16) {
        itmp = hexQuot(ihex);
        idiff = eval(ihex + '-(' + itmp + '*16)');
        a2 = itohex(idiff) + a2;
        ihex = itmp;
    }
    a1 = itohex(ihex);
    return a1 + a2;
}
function hexQuot(i) {
    return Math.floor(eval(i + '/16'));
}

function itohex(i) {
    if (i == 0) {
        aa = '0'
    }
    else {
        if (i == 1) {
            aa = '1'
        }
        else {
            if (i == 2) {
                aa = '2'
            }
            else {
                if (i == 3) {
                    aa = '3'
                }
                else {
                    if (i == 4) {
                        aa = '4'
                    }
                    else {
                        if (i == 5) {
                            aa = '5'
                        }
                        else {
                            if (i == 6) {
                                aa = '6'
                            }
                            else {
                                if (i == 7) {
                                    aa = '7'
                                }
                                else {
                                    if (i == 8) {
                                        aa = '8'
                                    }
                                    else {
                                        if (i == 9) {
                                            aa = '9'
                                        }
                                        else {
                                            if (i == 10) {
                                                aa = 'A'
                                            }
                                            else {
                                                if (i == 11) {
                                                    aa = 'B'
                                                }
                                                else {
                                                    if (i == 12) {
                                                        aa = 'C'
                                                    }
                                                    else {
                                                        if (i == 13) {
                                                            aa = 'D'
                                                        }
                                                        else {
                                                            if (i == 14) {
                                                                aa = 'E'
                                                            }
                                                            else {
                                                                if (i == 15) {
                                                                    aa = 'F'
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
    }
    return aa
}

function GetBool(valONOFF) {
    rez = "TRUE";
    if (valONOFF == false) {
        rez = "FALSE";
    }
    return rez;
}
