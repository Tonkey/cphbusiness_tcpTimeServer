package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author Nicklas Molving
 */
public class TCPClient {

    public static void main(String args[]) {
        try {

            String msg = "";
            String returnMsg = "";
            Socket skt = new Socket("localhost", 8080);
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream((skt.getOutputStream()));
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(skt.getInputStream()));

            while (!msg.equals("stop")) {

                msg = inFromUser.readLine();
                outToServer.writeBytes(msg + '\n');
                System.out.println(inFromServer.readLine());
                

            }
            
            
            

        } catch (Exception e) {
            System.out.print("Whoops! It didn't work!\n");
        }
    }

}
