package com.learning.aos.filesharing;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Subbu on 11/8/15.
 */
public class NodeConnectionHelper implements Runnable {

    private ServerSocket serverSocket;
    private ClientNode clientNode;

    public NodeConnectionHelper() {
        this.clientNode = ClientNode.getClientInstance();
    }

    @Override
    public void run() {
        try {
            startListening();
            // Call accept() to receive the next connection
            while (true) {
                debugMsg(clientNode.currentNode.getNodeID() + " Waiting for connections...");
                Socket client = serverSocket.accept();

                String peerIpAddress = client.getInetAddress().getHostAddress();
                debugMsg(peerIpAddress + ":" + client.getPort() + " is connected");

                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                debugMsg("Client Output stream: " + out);
                clientNode.getNeighbourOutputStreamList().add(out);

            }
        } catch (IOException e) {
            debugError("Exception in creating connections " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to listen on the current Node port; waits for connections
     *
     * @throws IOException
     */
    private void startListening() throws IOException {
        debugMsg("Client Node: " + clientNode.currentNode);
        serverSocket = new ServerSocket(clientNode.currentNode.getNodePort());
        debugMsg(clientNode.currentNode.getNodeID() + " listening on " + serverSocket.getInetAddress().getHostAddress() + ":" + clientNode.currentNode.getNodePort());
//        clientNode.currentNode.setNodeAddress(serverSocket.getInetAddress().getHostAddress());
    }

    private void debugMsg(String msg) {
        System.out.println(NodeConnectionHelper.class.getSimpleName() + "-->" + msg);
    }

    private void debugError(String msg) {
        System.err.println(NodeConnectionHelper.class.getSimpleName() + "-->" + msg);
    }
}
