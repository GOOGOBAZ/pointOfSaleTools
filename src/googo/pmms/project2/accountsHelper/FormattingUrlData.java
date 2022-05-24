/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

/**
 *
 * @author STAT SOLUTIONS
 */
public class FormattingUrlData {
    
    public String urlDataFormating(String theRawData){
    String theNewData="";
//    JOptionPane.showMessageDialog(c, theRawData);
    switch(theRawData){
    
case "":
theNewData="%20";  
break;
case "!":		theNewData="%21";break;
case"\"":	theNewData=	"%22";break;
case"#"	:	theNewData="%23";break;
case"$"	:	theNewData="%24";break;
case"%"	:	theNewData="%25";break;
case"&"	:	theNewData="%26";break;
case"'"	:	theNewData="%27";break;
case"("	:	theNewData="%28";break;
case")"	:	theNewData="%29";break;
case"*"	:	theNewData="%2A";break;
case"+"	:theNewData=	"%2B";break;
case","	:theNewData=	"%2C";break;
case"-"	:theNewData=	"%2D";break;
case"."	:theNewData=	"%2E";break;
case"/"	:	theNewData="%2F";break;
case"0"	:theNewData=	"%30";break;
case"1":theNewData=		"%31";break;
case"2":theNewData=		"%32";break;
case"3"	:theNewData=	"%33";break;
case"4"	:theNewData=	"%34";break;
case"5"	:theNewData=	"%35";break;
case"6"	:theNewData=	"%36";break;
case"7"	:theNewData=	"%37";break;
case"8"	:theNewData=	"%38";break;
case"9"	:theNewData=	"%39";break;
case":"	:theNewData=	"%3A";break;
case";"	:theNewData=	"%3B";break;
case"<"	:theNewData=	"%3C";break;
case"="	:theNewData=	"%3D";break;
case">"	:theNewData=	"%3E";break;
case"?"	:theNewData=	"%3F";break;
case"@"	:theNewData=	"%40";break;
case"A":theNewData=		"%41";break;
case"B"	:theNewData=	"%42";break;
case"C"	:theNewData=	"%43";break;
case"D":theNewData=		"%44";break;
case"E"	:theNewData=	"%45";break;
case"F":theNewData=		"%46";break;
case"G":theNewData=		"%47";break;
case"H":theNewData=	"%48";break;
case"I"	:theNewData=	"%49";break;
case"J"	:theNewData=	"%4A";break;
case"K"	:theNewData=	"%4B";break;
case"L":theNewData=	"%4C";break;
case"M"	:theNewData=	"%4D";break;
case"N"	:theNewData=	"%4E";break;
case"O":theNewData=		"%4F";break;
case"P"	:theNewData=	"%50";break;
case"Q"	:theNewData=	"%51";break;
case"R"	:theNewData=	"%52";break;
case"S"	:theNewData=	"%53";break;
case"T"	:theNewData=	"%54";break;
case"U"	:theNewData=	"%55";break;
case"V"	:theNewData=	"%56";break;
case"W"	:theNewData=	"%57";break;
case"X"	:theNewData=	"%58";break;
case"Y"	:theNewData=	"%59";break;
case"Z"	:theNewData=	"%5A";break;
case"["	:theNewData=	"%5B";break;
case"]"	:	theNewData="%5D";break;
case"^"	:theNewData=	"%5E";break;
case"_"	:	theNewData="%5F";break;
case"`"	:theNewData=	"%60";break;
case"a"	:theNewData=	"%61";break;
case"b":theNewData=		"%62";break;
case"c"	:theNewData=	"%63";break;
case"d"	:theNewData=	"%64";break;
case"e"	:theNewData=	"%65";break;
case"f":theNewData=		"%66";break;
case"g"	:theNewData=	"%67";break;
case"h"	:theNewData=	"%68";break;
case"i":theNewData=		"%69";break;
case"j"	:theNewData=	"%6A";break;
case"k"	:theNewData=	"%6B";break;
case"l"	:theNewData=	"%6C";break;
case"m"	:theNewData=	"%6D";break;
case"n"	:theNewData=	"%6E";break;
case"o":theNewData=		"%6F";break;
case"p":theNewData=		"%70";break;
case"q":theNewData=		"%71";break;
case"r":theNewData=		"%72";break;
case"s":theNewData=		"%73";break;
case"t"	:theNewData=	"%74";break;
case"u":theNewData=		"%75";break;
case"v"	:theNewData=	"%76";break;
case"w":theNewData=		"%77";break;
case"x":theNewData=		"%78";break;
case"y":theNewData=		"%79";break;
case"z":theNewData=		"%7A";break;
case"{"	:theNewData=	"%7B";break;
case"|"	:theNewData=	"%7C";break;
case"}"	:theNewData=	"%7D";break;
case"~"	:theNewData=	"%7E";break;

case"‚"	:theNewData=	"%82";break;
case"ƒ"	:theNewData=	"%83";break;
case"„"	:theNewData=	"%84";break;
case"…"	:theNewData=	"%85";break;
case"†"	:theNewData=	"%86";break;
case"‡"	:theNewData=	"%87";break;
case"ˆ"	:theNewData=	"%88";break;
case"‰"	:theNewData=	"%89";break;
case"Š"	:theNewData=	"%8A";break;
case"‹"	:	theNewData="%8B";break;
case"Œ"	:theNewData=	"%8C";break;
case"Ž"	:theNewData=	"%8E";break;
case"‘"	:theNewData=	"%91";break;
case"’"	:theNewData=	"%92";break;
case"“"	:theNewData=	"%93";break;
case"”":theNewData=		"%94";break;
case"•"	:theNewData=	"%95";break;
case"–":	theNewData=	"%96";break;
case"—"	:theNewData=	"%97";break;
case"™"	:theNewData=	"%99";break;
case"š"	:theNewData=	"%9A";break;
case"›":	theNewData=	"%9B";break;
case"œ":theNewData=		"%9C";break;
case"ž"	:theNewData=	"%9E";break;
case"Ÿ"	:theNewData=	"%9F";break;
case"¡"	:theNewData=	"%A1";break;
case"¢"	:theNewData=	"%A2";break;
case"£"	:theNewData=	"%A3";break;
case"¤"	:theNewData=	"%A4";break;
case"¥"	:theNewData=	"%A5";break;
case"¦"	:theNewData=	"%A6";break;
case"§"	:theNewData=	"%A7";break;
case"¨"	:theNewData=	"%A8";break;
case"©"	:theNewData=	"%A9";break;
case"ª":theNewData=	"%AA";break;
case"«"	:	theNewData="%AB";break;
case"¬"	:theNewData=	"%AC";break;
case"®":theNewData=	    "%AE";break;
case"¯"	:theNewData=	"%AF";break;
case"°"	:	theNewData="%B0";break;
case"±"	:theNewData=	"%B1";break;
case"²"	:theNewData=	"%B2";break;
case"³":theNewData=		"%B3";break;
case"´"	:theNewData=	"%B4";break;
case"µ"	:theNewData=	"%B5";break;
case"¶"	:theNewData=	"%B6";break;
case"·"	:	theNewData="%B7";break;
case"¸":theNewData=		"%B8";break;
case"¹"	:theNewData=	"%B9";break;
case"º"	:theNewData=	"%BA";break;
case"»"	:theNewData=	"%BB";break;
case"¼"	:theNewData=	"%BC";break;
case"½"	:theNewData=	"%BD";break;
case"¾"	:theNewData=	"%BE";break;
case"¿":theNewData=		"%BF";break;
case"À"	:theNewData=	"%C0";break;
case"Á"	:theNewData=	"%C1";break;
case"Â"	:theNewData=	"%C2";break;
case"Ã"	:theNewData=	"%C3";break;
case"Ä"	:theNewData=	"%C4";break;
case"Å"	:theNewData=	"%C5";break;
case"Æ"	:theNewData=	"%C6";break;
case"Ç":theNewData=		"%C7";break;
case"È":theNewData=		"%C8";break;
case"É"	:	theNewData="%C9";break;
case"Ê"	:theNewData=	"%CA";break;
case"Ë"	:theNewData=	"%CB";break;
case"Ì"	:theNewData=	"%CC";break;
case"Í"	:theNewData=	"%CD";break;
case"Î"	:theNewData=	"%CE";break;
case"Ï"	:theNewData=	"%CF";break;
case"Ð"	:theNewData=	"%D0";break;
case"Ñ"	:theNewData=	"%D1";break;
case"Ò"	:theNewData=	"%D2";break;
case"Ó":theNewData=		"%D3";break;
case"Ô":theNewData=		"%D4";break;
case"Õ"	:theNewData=	"%D5";break;
case"Ö":theNewData=		"%D6";break;
case"×"	:theNewData=	"%D7";break;
case"Ø"	:theNewData=	"%D8";break;
case"Ù"	:theNewData=	"%D9";break;
case"Ú":theNewData=	    "%DA";break;
case"Û"	:theNewData=	"%DB";break;
case"Ü"	:theNewData=	"%DC";break;
case"Ý"	:theNewData=	"%DD";break;
case"2Þ":theNewData=	"%DE";break;
case"ß"	:theNewData=	"%DF";break;
case"à"	:theNewData=	"%E0";break;
case"á"	:theNewData=	"%E1";break;
case"â":theNewData=		"%E2";break;
case"ã"	:theNewData=	"%E3";break;
case	"ä":		theNewData="%E4";break;
case	"å":	theNewData=	"%E5";break;
case	"æ":	theNewData=	"%E6";break;
case	"ç":	theNewData=	"%E7";break;
case	"è":	theNewData=	"%E8";break;
case	"é":	theNewData=	"%E9";break;
case	"ê":	theNewData=	"%EA";break;
case"ë"	:theNewData=	"%EB";break;
case"ì"	:theNewData=	"%EC";break;
case"í":theNewData=		"%ED";break;
case"î":theNewData=		"%EE";break;
case"ï"	:theNewData=	"%EF";break;
case"ð"	:theNewData=	"%F0";break;
case"ñ"	:theNewData=	"%F1";break;
case"ò"	:theNewData=	"%F2";break;
case"ó"	:theNewData=	"%F3";break;
case"ô"	:theNewData=	"%F4";break;
case"õ":theNewData=		"%F5";break;
case"ö"	:theNewData=	"%F6";break;
case"÷"	:theNewData=	"%F7";break;
case"ø"	:theNewData=	"%F8";break;
case"ù":theNewData=		"%F9";break;
case"ú"	:theNewData=	"%FA";break;
case"û"	:theNewData=	"%FB";break;
case"ü":theNewData=		"%FC";break;
case"ý":theNewData=		"%FD";break;
case"þ"	:theNewData=	"%FE";break;
case"ÿ":	theNewData=	"%FF";break;
default: theNewData=	"%3F"; break;


}



return theNewData;
}

    
    public String formSMSMessage(String theMessage){
        
    
    String[] message=theMessage.split("\\s+");
      
      
     int t=0;
     
     String actualMessage="",other="",concats="";
     
     

         
     while(t<message.length){
//         JOptionPane.showMessageDialog(this, message.length);   
    char[] ms=message[t].toCharArray();
    
        int t1=0;
        
     while(t1<ms.length){
         
//      JOptionPane.showMessageDialog(this, ms.length); 
      
//      JOptionPane.showMessageDialog(this, Character.toString(ms[t1]));  
      
other=urlDataFormating(Character.toString(ms[t1]));
         
//  JOptionPane.showMessageDialog(this, other);
        actualMessage = actualMessage.concat(other);

t1++;

     }
     actualMessage = actualMessage.concat("%20");  
         t++;
     }

   
     
     return actualMessage;
}




}