package com.utd.aos.casualordering;

import com.utd.aos.casualordering.model.NodeDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Subbu on 10/1/15.
 */

public class RequestHandler extends Thread {
    private Socket socket;
    private NodeDetails nodeDetails;

    RequestHandler(Socket socket, NodeDetails nodeDetails) {
        this.socket = socket;
        this.nodeDetails = nodeDetails;
    }

    @Override
    public void run() {
        System.out.println("____________________________________________________");
        System.out.println("RequestHandler.run() entry");
        try {
            // Get input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            System.out.println("Node Waiting for "+nodeDetails.getNodeWaitDuration());
            sleep(nodeDetails.getNodeWaitDuration());

            for (int i = 0; i < 10; i++) {
                out.println("Msg: " + i + " " + nodeDetails.getNodeAddress() + ":" + nodeDetails.getNodePort());
                out.flush();
            }

            String line = in.readLine();
            while (line != null && line.length() > 0) {
                out.println("Msg from " + nodeDetails.getNodeAddress() + ":" + nodeDetails.getNodePort());
                out.println("---> " + line);
                out.flush();
                line = in.readLine();
            }

            // Close our connection
            in.close();
            out.close();
            socket.close();

            System.out.println("Connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("RequestHandler.run() exit");
        System.out.println("____________________________________________________");
    }
}