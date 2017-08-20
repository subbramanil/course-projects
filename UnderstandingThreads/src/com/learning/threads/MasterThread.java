package com.learning.threads;

import com.learning.threads.model.NodeDetails;

import javax.xml.soap.Node;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Subbu on 10/1/15.
 */
public class MasterThread extends Thread {
    private static final int SERVER_PORT = 3000;
    private static int numClients;
    private ServerSocket serverSocket;
    private boolean running = false;

    private List<NodeDetails> nodesList;
    List<NodeDetails> newNodeList;
    private List<ObjectOutputStream> outputStreamList;

    private int nodeID = 0;
    int numNodesSent = 0;


    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Please provide the number of client nodes: " +
                    "\n java -cp bin/ com.learning.threads.MasterThread <number of nodes>");
            System.exit(0);
        }
        numClients = Integer.parseInt(args[0]);
        new MasterThread().initMaster();
    }

    /**
     * Method to initialize the Master Thread
     */
    public void initMaster() {
        debugMsg("____________________________________________________");
        debugMsg("MasterThread.initMaster() method entry");
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            debugMsg(" MasterThread listening on " + serverSocket.getInetAddress().getHostAddress() + ":" + SERVER_PORT);
            nodesList = new ArrayList<>();
            outputStreamList = new ArrayList<>();
            newNodeList = new ArrayList<>();

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        debugMsg("MasterThread.initMaster() method exit");
        debugMsg("____________________________________________________");
    }

    @Override
    public void run() {
        debugMsg("____________________________________________________");
        debugMsg("MasterThread.run() method entry");
        running = true;
        NodeDetails node;
        debugMsg("Waiting for " + numClients + " clients to connect");
        while (running) {
            try {
                // Call accept() to receive the next connection
                Socket client = serverSocket.accept();
                debugMsg("Client IP address: " + client.getInetAddress().getHostAddress());

                // Get the connecting node details
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                node = (NodeDetails) inputStream.readObject();

                //Update IP address
                node.setNodeAddress(client.getInetAddress().getHostAddress());

                // Assign node ID
                node.setNodeID(nodeID++);
                debugMsg("Node Details:" + node + " connected ");

                // Add to the list of connected nodes
                nodesList.add(node);

                // Send updated Node details
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.flush();
                out.writeObject(node);

                outputStreamList.add(out);

                debugMsg("No of Clients connected: " + nodesList.size());
                if (nodesList.size() == numClients) {
                    debugMsg("All Nodes are connected. Publish Nodes List... " + nodesList.size());

                    int ch;
                    while (true) {
                        debugMsg("Choose \n 1.Broadcast" +
                                "\n 2.Multicast");
                        Scanner in = new Scanner(System.in);
                        ch = in.nextInt();
                        if (ch == 1 || ch == 2) {
                            break;
                        }
                        debugMsg("Enter valid choice");
                    }

                    debugMsg("User picked... " + ch);

                    try {
                        for (ObjectOutputStream ostream : outputStreamList) {
                            ostream.flush();
                            ostream.writeObject("" + ch);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (ch == 2) {
                        debugMsg("Multicasting... ");
                        chooseRandomNodes();
                        debugMsg("Sorting... ");
                        Collections.sort(newNodeList, new NodeComparator());
                        for (NodeDetails n : newNodeList) {
                            debugMsg(""+n);
                        }
                    }

                    // Send the number of nodes connected
                    try {
                        for (ObjectOutputStream ostream : outputStreamList) {
                            ostream.flush();
                            debugMsg("Number of Nodes: " + nodesList.size());
                            ostream.writeObject(nodesList.size());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    // All Nodes connected.. Share the node details
                    publishNodesList(ch);

                    //todo receive completion from all nodes and bring down the computation
                    int nodeID = (int) inputStream.readObject();
                    debugMsg("Msg Sending completed by Node: " + nodeID);
                    numNodesSent++;
                    if (numNodesSent == numClients) {
                        out.flush();
                        out.writeObject(true);
                        stopMaster();
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                debugMsg("bringing down the process");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                debugMsg("bringing down the process");
                System.exit(0);
            }
        }
        debugMsg("MasterThread.run() method exit");
        debugMsg("____________________________________________________");
    }

    /**
     * Util method to sort the NodeDetails based on the node ID
     */
    public class NodeComparator implements Comparator<NodeDetails> {
        @Override
        public int compare(NodeDetails o1, NodeDetails o2) {
            return o1.getNodeID().compareTo(o2.getNodeID());
        }
    }

    /**
     * Method to choose a Random count of nodes, and random set of nodes for multicasting
     */
    private void chooseRandomNodes() {
        debugMsg("Picking Random nodes");
        Set<Integer> randomNodeSet = new HashSet<>();
        int numNodes = randInt(2, numClients - 1);
        for (int i = 0; i < numNodes; i++) {
            int id = randInt(0, numClients - 1);
            debugMsg("Picked node: " + id);
            if (!randomNodeSet.contains(id)) {
                randomNodeSet.add(id);
                newNodeList.add(nodesList.get(id));
            } else {
                debugMsg("Picked up the same node... Repick");
                i--;
            }
        }
        debugMsg("Picked up the nodes: " + Arrays.toString(randomNodeSet.toArray()));
    }

    /**
     * Method to publish the node list to the connected nodes
     *
     * @param ch : user's choice, 1. Broadcasting, 2. Multicasting
     */
    public void publishNodesList(int ch) {
        try {
            for (ObjectOutputStream out : outputStreamList) {
                out.flush();
                if (ch == 2) {
                    out.writeObject(newNodeList);
                } else {
                    out.writeObject(nodesList);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to stop the Master Thread
     */
    public void stopMaster() {
        debugMsg("____________________________________________________");
        debugMsg("MasterThread.stopMaster() method exit");
        running = false;
        this.interrupt();
        debugMsg("MasterThread.stopMaster() method exit");
        debugMsg("____________________________________________________");
    }

    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void debugMsg(String s){
        System.out.println(MasterThread.class.getSimpleName() + "-->" + s);
    }
}
