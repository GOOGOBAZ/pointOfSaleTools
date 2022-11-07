/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.databases;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stanchart
 */
public class IpAddressSelector {
    
    public String getThisComputerIp(){
        
 String theIp = "";
    
    
        try {
            theIp=  InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(IpAddressSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    return theIp;
                 }
    
}
