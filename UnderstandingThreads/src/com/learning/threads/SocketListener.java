package com.learning.threads;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Subbu on 10/1/15.
 */

public class SocketListener extends Thread {
    private Socket socket;
    private boolean isDone = true;

    public SocketListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("____________________________________________________");
        System.out.println("SocketListener.run() entry");
            try {
                // Get input stream and listen
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                while (isDone) {
                    System.out.println("Node Listening in :" + socket.getInetAddress() + ":" + socket.getPort());
                    System.out.println(inputStream.readObject());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println("SocketListener.run() exit");
        System.out.println("____________________________________________________");
    }

}