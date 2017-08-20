package com.learning.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by Subbu on 10/1/15.
 */

public class SocketMessenger extends Thread {
    private Socket socket;
    private boolean isDone = true;
    private ObjectOutputStream out;

    public SocketMessenger(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("____________________________________________________");
        System.out.println("SocketMessenger.run() entry");
        try {
//            while (isDone) {
                // Get output stream and listen
                out = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Writing message through socket" +socket.getInetAddress() + ":" + socket.getPort());
                for(int i=0;i<10;i++){
                    out.writeObject(socket.getInetAddress() + ":" + socket.getPort()+"--> Msg: "+i);
                }
                out.flush();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("SocketMessenger.run() exit");
        System.out.println("____________________________________________________");
    }

    public void sendMsgToNodes(String msg) {
        try {
            if (out != null) {
                out.flush();
                out.writeObject(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}