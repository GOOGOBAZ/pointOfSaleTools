/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author Stanchart
 */
public class HttpClientH {
  fileInputOutPutStreams fios= new fileInputOutPutStreams();
    CloseableHttpClient httpclient=null;
            
            CloseableHttpResponse response=null;
            
            
            
            String respo1="";
            int respo=1000;
    public int sendGet(String urlToSendTo)  {
        
          
            
//            URIBuilder uriB = new URIBuilder ();
//            URI uri=null;
            
            if( fios.intFileReader(fios.createFileName("emailDetails", "sendMail", "smsProxy.txt"))==25){  
                
            try {
//                try {
//                   uri=
//                            uriB.setScheme ("http")
//                            .setHost ("www.google.com")
//                            .setPath ("/search")
//                            .setParameter ("q", "httpclient")
//                            .setParameter ("btnG", "Google Search")
//                            .setParameter ("aq", "f")
//                            .setParameter ("oq", "")
//                            .build ();
//                } catch (URISyntaxException ex) {
//                    Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
//                }

            HttpHost proxy = new HttpHost(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "ProxyDetails.txt")).split("[;]", 2)[0], parseInt(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "ProxyDetails.txt")).split("[;]", 2)[1].replace(";", "")));

            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

            httpclient = HttpClients.custom().setRoutePlanner(routePlanner) .build(); 
         
            HttpGet httpget = new HttpGet(urlToSendTo);

            response = httpclient.execute(httpget);

            respo=response.getStatusLine().getStatusCode(); 
            
             response.close();
             
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }

            }else{    

            try {
            httpclient = HttpClients.createDefault();

            HttpGet httpget = new HttpGet(urlToSendTo);

            response = httpclient.execute(httpget);

            respo=response.getStatusLine().getStatusCode(); 
            
             response.close();
             
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }   


            }  
            return respo;

            }

            // HTTP POST request
            public String sendPost(String urlToSendTo) {


            
            if( fios.intFileReader(fios.createFileName("emailDetails", "sendMail", "smsProxy.txt"))==25){  
                
            try {

            HttpHost proxy = new HttpHost(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "ProxyDetails.txt")).split("[;]", 2)[0], parseInt(fios.stringFileReader(fios.createFileName("emailDetails", "sendMail", "ProxyDetails.txt")).split("[;]", 2)[1].replace(";", "")));

            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

            httpclient = HttpClients.custom().setRoutePlanner(routePlanner) .build(); 
         
            HttpPost post = new HttpPost(urlToSendTo);

            response = httpclient.execute(post);

            respo1=response.getStatusLine().getReasonPhrase(); 
              
            
            response.close();
            
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }}else{    

            try {
                
            httpclient = HttpClients.createDefault();

             HttpPost post = new HttpPost(urlToSendTo);

            response = httpclient.execute(post);

            respo1=response.getStatusLine().getReasonPhrase(); 
            
       
             
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }  


            }  
            return respo1;

            }

    public String sendPostEntity(String uri) {

 try {
        
            httpclient = HttpClients.createDefault();

              HttpGet httppost = new HttpGet(uri);


            response = httpclient.execute(httppost);

            respo1=  EntityUtils.toString(response.getEntity());
            
       
             
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }  


             
            return respo1;

        
        



            }
    public String sendPostEntity2(String uri) {

 try {
        
            httpclient = HttpClients.createDefault();

              HttpPost httppost = new HttpPost(uri);


            response = httpclient.execute(httppost);

        
          respo1=  EntityUtils.toString(response.getEntity());
       
             
            } catch (IOException ex) {
            Logger.getLogger(HttpClientH.class.getName()).log(Level.SEVERE, null, ex);
            }  


             
            return respo1;

        
        



            }
}