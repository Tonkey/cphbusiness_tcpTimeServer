package com.mycompany.tcptimeserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas Molving
 */
public class TCPConnect {

    private static String ip = "localhost";
    private static int port = 8080;

    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        }

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));
        System.out.println("Server started - listening on port " + port + " bound to ip " + ip);

        while (true) {
            Socket link = ss.accept();
            System.out.println("\nNew client connection:" + link.getPort() + link.getInetAddress());
            outTime(link);

        }

    }

    static void outTime(Socket s) {
        try {
            BufferedReader scn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(s.getOutputStream());
            String msg = "";
            String returnMsg = "";
            while (!msg.equals("stop")) {
                msg = scn.readLine();
                
                if (msg.equals("get time")) {
                    outToClient.writeBytes(new java.util.Date().toString() + '\n');
                } else if (msg.substring(0,6).equals("UPPER#")){
                    
                    returnMsg = msg.substring(6);
                    
                    outToClient.writeBytes(returnMsg.toUpperCase() + '\r' + '\n');
                } else if (msg.substring(0,6).equals("LOWER#")){
                    returnMsg = msg.substring(6);
                    
                    outToClient.writeBytes(returnMsg.toLowerCase() + '\r' + '\n');
                } else if (msg.substring(0,8).equals("REVERSE#")){
                    StringBuilder sb = new StringBuilder(msg.substring(8));
                    
                    
                    
                    
                    sb.reverse();
                    System.out.println(sb.toString());
                    String letter1 = sb.substring(0,1);
                    System.out.println(sb.toString());
                    returnMsg = sb.substring(1);
                    
                    outToClient.writeBytes(letter1.toUpperCase() + returnMsg + '\r' + '\n');
                    
                } else if (msg.substring(0,10).equals("TRANSLATE#")){
                    returnMsg = msg.substring(10);
                    
                    System.out.println(returnMsg);
                    
                    returnMsg = translate(returnMsg);
                    outToClient.writeBytes(returnMsg + '\r' + '\n');
                    
                    
                } else {
                    outToClient.writeBytes("command not recognised" + '\n' + '\r');
                }

            }
            scn.close();
            outToClient.close();
            s.close();
            System.exit(1);

        } catch (IOException ex) {
            Logger.getLogger(TCPConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String translate(String word) {
        String translatedWord = "";
        switch (word) {
            case "hund":
                translatedWord = "dog";
                break;
            case "kage":
                translatedWord = "cake";
                break;
            case "kasse":
                translatedWord = "box";
                break;
            default:
                translatedWord = "word not recognized";
        }

        return translatedWord;
    }

}
