package com.utd.aos.casualordering;

import com.utd.aos.casualordering.model.NodeDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Subbu on 10/1/15.
 */
public class Node extends Thread {
    private ServerSocket serverSocket;
    private NodeDetails nodeDetails;
    private List<NodeDetails> nodeList;

    private boolean running = false;

    public Node(NodeDetails node, List<NodeDetails> nodeList) {
        this.nodeDetails = node;
        this.nodeList = nodeList;
    }

    public void startServer() {
        System.out.println("____________________________________________________");
        System.out.println("Node.startServer() method entry");
        try {
            serverSocket = new ServerSocket(nodeDetails.getNodePort());
            System.out.println(" Server listening on " + serverSocket.getInetAddress().getHostAddress() + ":" + nodeDetails.getNodePort());
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Node.startServer() method exit");
        System.out.println("____________________________________________________");
    }

    public void establishConnection() {
        System.out.println("____________________________________________________");
        System.out.println("Node.establishConnection() method entry");
        String currentNodeAddress = nodeDetails.getNodeAddress() + ":" + nodeDetails.getNodePort();
        for (NodeDetails node : nodeList) {
            String nodeAddress = node.getNodeAddress() + ":" + node.getNodePort();
            if (!nodeAddress.equals(currentNodeAddress)) {
                System.out.println("Connect to node: " + nodeAddress);
                Socket s = null;
                try {
                    s = new Socket(node.getNodeAddress(), node.getNodePort());
                    BufferedReader input =
                            new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String answer = input.readLine();
                    System.out.println("Msg from " + nodeAddress + " is " + answer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Node.establishConnection() method exit");
        System.out.println("____________________________________________________");
    }

    public void stopServer() {
        System.out.println("____________________________________________________");
        System.out.println("Node.stopServer() method exit");
        running = false;
        this.interrupt();
        System.out.println("Node.stopServer() method exit");
        System.out.println("____________________________________________________");
    }

    @Override
    public void run() {
        System.out.println("____________________________________________________");
        System.out.println("Node.run() method entry");
        running = true;
        while (running) {
            try {

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                String serverAddress = socket.getInetAddress().getHostAddress();
                System.out.println("Listening for a connection: " + serverAddress);

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler(socket, nodeDetails);
                requestHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Node.run() method exit");
        System.out.println("____________________________________________________");
    }
}
