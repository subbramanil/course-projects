package com.learning.threads;

import com.learning.threads.model.NodeDetails;
import com.learning.threads.model.SocketInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Subbu on 10/4/15.
 */
public class NodeThread implements Runnable {

    private static final int SERVER_PORT = 3000;
    private static String SERVER_IP = "";

    private List<NodeDetails> nodeList;
    private NodeDetails currentNode;
    private List<SocketInfo> socketInfos;
    private ServerSocket serverSocket;
    private ObjectOutputStream masterOutStream;
    private ObjectInputStream masterInStream;
    private boolean isMulticast;
    private Integer numNodes;

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Insufficient details to start the Node \n" +
                    "Usage: java -cp bin\\ NodeThread <Master_ip_address> <Port_number_for_Node>");
            System.exit(0);
        }
        SERVER_IP = args[0];
        new NodeThread().initNode(Integer.parseInt(args[1]));
    }

    /**
     * Method to initialize the Node
     *
     * @param port : Port number of the Node thread
     */
    private void initNode(int port) {
        socketInfos = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            debugMsg("Node listening on " + serverSocket.getInetAddress().getHostAddress() + ":" + port);
            currentNode = new NodeDetails();
            currentNode.setNodePort(port);
            currentNode.setNodeAddress(serverSocket.getInetAddress().getHostAddress());
            currentNode.setNodeWaitDuration(ThreadLocalRandom.current().nextInt(20, 100));
            currentNode.setSendComplete(false);
            currentNode.setRecvComplete(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.run();
    }


    /**
     * Method to connect to Master Thread and collect information about the Peer nodes
     */
    private void connectToMaster() {
        try {
            // Connect to the Master Thread
            debugMsg("Connecting to Master");
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            // Send current Node details
            debugMsg("Share Node details" + currentNode + " with the Master");
            masterOutStream = new ObjectOutputStream(socket.getOutputStream());
            masterOutStream.flush();
            masterOutStream.writeObject(currentNode);

            debugMsg("Waiting for Master to get the list of Peer Nodes");
            // Get input stream and listen
            masterInStream = new ObjectInputStream(socket.getInputStream());

            // Get the updated Node details
            currentNode = (NodeDetails) masterInStream.readObject();
            debugMsg("Updated Node details: " + currentNode);

            //Get the choice
            String choice = (String) masterInStream.readObject();
            debugMsg("Received: is Multicasting: " + choice);

            if (!choice.equals("2")) {
                debugMsg("Broadcast");
                isMulticast = false;
            } else {
                debugMsg("Multicast");
                isMulticast = true;
            }

            // Get the number of nodes
            numNodes = (Integer) masterInStream.readObject();
            debugMsg("Total Number of Nodes: " + numNodes);

            // Get the details of the list of nodes
            debugMsg("Fetching the list of Peer nodes from Master");
            nodeList = (ArrayList<NodeDetails>) masterInStream.readObject();

            debugMsg("List of nodes to be messaged:");
            for (NodeDetails node : nodeList) {
                if (!node.getNodeID().equals(currentNode.getNodeID()))
                    debugMsg(""+node);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to connect to the other peer nodes
     */
    private void connectToPeerNodes() {

        debugMsg("________________________ConnectToPeerNodes() entry _________________________________");

        if (!isMulticast) {
            debugMsg("Node ID is :" + currentNode.getNodeID());
            for (int i = 0; i < currentNode.getNodeID(); i++) {
                debugMsg("Current Node:" + currentNode.getNodeAddress() + ":" + currentNode.getNodePort());
                NodeDetails node = nodeList.get(i);
                debugMsg("--------------> Connecting to Node:" + node.getNodeAddress() + ":" + node.getNodePort());
                Socket s;
                try {
                    s = new Socket(node.getNodeAddress(), node.getNodePort());
                    socketInfos.add(new SocketInfo(s, node));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            debugMsg("Current Node is :" + currentNode);
            for (int i = 0; i < nodeList.size(); i++) {
                NodeDetails node = nodeList.get(i);
                debugMsg("Connecting to Node: " + node);

                if (node.getNodeID() < currentNode.getNodeID()) {
                    debugMsg("Current Node:" + currentNode.getNodeAddress() + ":" + currentNode.getNodePort());
                    Socket s;
                    try {
                        s = new Socket(node.getNodeAddress(), node.getNodePort());
                        debugMsg("--------------> Connected to Node:" + node.getNodeAddress() + ":" + node.getNodePort());
                        socketInfos.add(new SocketInfo(s, node));
                        if (socketInfos.size() == nodeList.size() - 1) {
                            debugMsg("enough nodes connected");
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        debugMsg("________________________ConnectToPeerNodes() exit _________________________________");
    }

    @Override
    public void run() {
        connectToMaster();
        debugMsg("******** Connected to Master and Got the Peer Nodes details *********");
        boolean isChosen = false;
        for (NodeDetails n : nodeList) {
            if (n.getNodeID().equals(currentNode.getNodeID())) {
                debugMsg("Current Node is picked for Multicast");
                isChosen = true;
            }
        }

        if (!isChosen) {
            debugMsg("Not picked for Multicast... Can be brought down");
            finishComputation();
        } else {

            connectToPeerNodes();

            while (true) {
                try {
                    // Call accept() to receive the next connection
                    debugMsg("Waiting for connections...");
                    debugMsg(socketInfos.size() + " <---> " + (nodeList.size() - 1));
                    if (socketInfos.size() < nodeList.size() - 1) {
                        Socket peerNode = serverSocket.accept();
                        String peerIpAddress = peerNode.getInetAddress().getHostAddress();
                        debugMsg(peerIpAddress + ":" + peerNode.getPort() + " is connected");
                        debugMsg("Get the Node details with the IP address: " + peerIpAddress);
                        SocketInfo info = new SocketInfo(peerNode, getNodeDetails(peerIpAddress));
                        socketInfos.add(info);
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            debugMsg("******** Connected to the Peer Nodes *********");

            debugMsg("Number Of PeerNodes: " + socketInfos.size());

            if (socketInfos.size() == nodeList.size() - 1) {
                debugMsg("All the other Nodes are connected");

                if (!isMulticast) {
                    currentNode.setNodeVector(new int[numNodes]);
                } else {
                    currentNode.setNodeMatrix(new int[numNodes][numNodes]);
                }

                Thread thread1 = new Thread(new Sender(socketInfos, currentNode, isMulticast));
                thread1.start();

                for (SocketInfo info : socketInfos) {
                    Thread thread = new Thread(new Listener(info, currentNode, isMulticast));
                    thread.start();
                }
            }

            debugMsg("Waiting for send complete:" + currentNode.getSendComplete());
            if (currentNode.getSendComplete()) {
                debugMsg("Sending messages is done\n Informing Master");
                finishComputation();
            }
        }
    }

    /**
     * Method to finish the computation
     */
    public void finishComputation() {

        try {
            masterOutStream.writeObject(currentNode.getNodeID());

            debugMsg("Waiting for every other node to complete");
            boolean isEveryoneDone = (boolean) masterInStream.readObject();
            debugMsg("Every node has completed: " + isEveryoneDone);

            if (isEveryoneDone) {
                currentNode.setRecvComplete(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get the Node details for the given IP address
     *
     * @param ipAddress : IP address
     * @return NodeDetails
     */
    NodeDetails getNodeDetails(String ipAddress) {
        for (NodeDetails node : nodeList) {
            debugMsg(node.getNodeAddress() + " <---> " + ipAddress);
            if (node.getNodeAddress().equals(ipAddress)) {
                debugMsg("Node Details Found: " + node);
                return node;
            }
        }
        debugMsg("Node Details Not Found");
        return null;
    }

    public void debugMsg(String s){
        System.out.println(NodeThread.class.getSimpleName() + "-->" + s);
    }
}
