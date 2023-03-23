/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedizioneautomatizzata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author mattiapannone
 */
public class SocketApp {

    DatagramPacket sendPacket;
    byte[] sendDataX = new byte[256];
    byte[] sendDataY = new byte[256];
    byte[] sendDataX2 = new byte[256];
    byte[] sendDataY2 = new byte[256];
    byte[] sendFlag = new byte[256];

    public SocketApp(String ip, int port, int x, int y, int flag) throws SocketException, IOException{
 
        DatagramSocket clientSocket = new DatagramSocket(); 
        InetAddress IPAddress = InetAddress.getByName(ip);
        
        //invio coordinata x
        sendDataX = String.valueOf(x).getBytes();
        sendPacket = new DatagramPacket(sendDataX, sendDataX.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio coordinata y
        sendDataY = String.valueOf(y).getBytes();
        sendPacket = new DatagramPacket(sendDataY, sendDataY.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio flag
        flag = 0;
        sendFlag = String.valueOf(flag).getBytes();
        sendPacket = new DatagramPacket(sendDataY, sendDataY.length, IPAddress, port);
        clientSocket.send(sendPacket);
    }
    
    public SocketApp(String ip, int port, int x, int y, int x2, int y2, int flag) throws SocketException, IOException{
 
        DatagramSocket clientSocket = new DatagramSocket(); 
        InetAddress IPAddress = InetAddress.getByName(ip);
        
        //invio coordinata x
        sendDataX = String.valueOf(x).getBytes();
        sendPacket = new DatagramPacket(sendDataX, sendDataX.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio coordinata y
        sendDataY = String.valueOf(y).getBytes();
        sendPacket = new DatagramPacket(sendDataY, sendDataY.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio flag
        flag = 1;
        sendFlag = String.valueOf(flag).getBytes();
        sendPacket = new DatagramPacket(sendDataY, sendDataY.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio coordinata x2
        sendDataX2 = String.valueOf(x).getBytes();
        sendPacket = new DatagramPacket(sendDataX2, sendDataX2.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
        //invio coordinata y2
        sendDataY2 = String.valueOf(y).getBytes();
        sendPacket = new DatagramPacket(sendDataY2, sendDataY2.length, IPAddress, port);
        clientSocket.send(sendPacket);
        
    }
}
