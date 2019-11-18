package com.comviva.cdr.client;

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;
public class UdpClient 
{
	public static void main(String args[]) throws IOException 
    { 
		System.out.println("client init");
        // Step 1:Create the socket object for 
        // carrying the data. 
        DatagramSocket ds = new DatagramSocket(); 
  
        InetAddress ip2 = InetAddress.getByName("169.254.163.85"); 
        byte[] bytes = ip2.getAddress();
        InetAddress ip =  InetAddress.getByAddress(bytes);
      //  InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null; 
  
        // loop while user not enters "bye" 
        int x=1,y=1;
        boolean z = false;
        while (y<500) 
        { 
        	
        	String inp = "category"; 
            String aa;
            if(z) {
            	aa = "|Crbt|Hello Ankit |Good Morning|Have a Nice Day";
            	z = false;
            }
            else {
            	aa = "|Uber|Hello Ankit |Good Morning|Have a Nice Day";
            	z = true;
            }
            inp=inp+Integer.toString(x)+aa+Integer.toString(y);
            
      //  	String inp = "reloadconfig|hello";
 
            // convert the String input into the byte array. 
            buf = inp.getBytes(); 
  
            // Step 2 : Create the datagramPacket for sending 
            // the data. 
            DatagramPacket DpSend = 
                  new DatagramPacket(buf, buf.length, ip, 49155); 
            
            System.out.println("client ddresss " +DpSend.getAddress());
  
            // Step 3 : invoke the send call to actually send 
            // the data. 
            ds.send(DpSend); 
  
            // break the loop if user enters "bye" 
            if (inp.equals("bye")) 
                break; 
            if(x==2) {
            	x=1;
            }
            else {
            	x++;
            }  
            y++;  
        } 
    } 

}
