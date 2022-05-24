/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.generalUse.project1.email;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author SSRN
 */
public class TestConnectivity {


public  boolean testInet() {
    Socket sock = new Socket();
    InetSocketAddress addr = new InetSocketAddress("google.com",80);
    try {
        sock.connect(addr,3000);
        return true;
    } catch (IOException e) {
        return false;
    } finally {
        try {sock.close();}
        catch (IOException e) {}
    }
}
    
}
