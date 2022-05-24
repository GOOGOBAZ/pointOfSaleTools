/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;




import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

/**
 *
 * @author Stanchart
 */
public class TestInternetConnection {
   
      
 
    public boolean getStatus() {
         String url="http://WWW.aljazeera.com/watch_now/"; boolean correct=false; CloseableHttpClient httpclient=null;CloseableHttpResponse response=null;
            int respo=1000; 
ClassLoader classLoader = TestInternetConnection.class.getClassLoader();
URL resource = classLoader.getResource("org/apache/http/message/BasicLineFormatter.class");
System.out.println(resource);
        try {
  
            
   HttpHost proxy = new HttpHost("10.1.15.3", 8080);
   
DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

httpclient = HttpClients.custom()
.setRoutePlanner(routePlanner)
.build();
            
            
            
            
            HttpGet httpget = new HttpGet(url);
            
            response = httpclient.execute(httpget);
            
            respo=response.getStatusLine().getStatusCode();
            if(respo==200){
             correct=true;
            } else{
            
             correct=false;
            }
        } catch (IOException ex) {
            Logger.getLogger(TestInternetConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    return correct;
    }
 
}
    
    
    

