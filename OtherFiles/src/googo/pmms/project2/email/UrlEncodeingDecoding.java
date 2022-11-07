/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.email;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 *
 * @author SSRN
 */
public class UrlEncodeingDecoding {
    
    public String decodeString(String URL)
    {

    String urlString="";
    try {
        urlString = URLDecoder.decode(URL,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

        }

        return urlString;

    }
    
     
    public String encodeString(String URL)
    {

    String urlString="";
    try {
        urlString = URLEncoder.encode(URL,"UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

        }

        return urlString;

    }
}
