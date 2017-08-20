package com.learning.aos.filesharing;

import com.learning.aos.filesharing.model.NodeDetails;
import com.learning.aos.filesharing.util.FileUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Subbu on 11/8/15.
 */
public class ConnectionHelperThread implements Runnable {

    private P2PNode p2PNode;

    public ConnectionHelperThread() {
        p2PNode = P2PNode.getSingetonNodeInstance();
    }


    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(p2PNode.currentNode.getNodePort());
            debugMsg("Master listening on " + serverSocket.getInetAddress().getHostAddress() + ":" + p2PNode.currentNode.getNodePort());
//            p2PNode.currentNode.setNodeAddress(serverSocket.getInetAddress().getHostAddress());

            joinP2PSystem();

            // Call accept() to receive the next connection
            while (true) {
                debugMsg("Waiting for connections...");
                Socket client = serverSocket.accept();
                String peerIpAddress = client.getInetAddress().getHostAddress();
                debugMsg(peerIpAddress + ":" + client.getPort() + " is connected");

                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                p2PNode.getNeighbourOutputStreamList().add(out);

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                NodeDetails joiningNode = (NodeDetails) in.readObject();

                debugSplMsg(joiningNode.getNodeID() + " is connected to current node: " + p2PNode.currentNode.getNodeID());
                p2PNode.getNeighbourNodesList().add(joiningNode);
                p2PNode.getNeighbourOutstreamMap().put(joiningNode.getNodeID(), out);
                p2PNode.getNeighbourSockets().put(joiningNode.getNodeID(), client);
                p2PNode.getNeighbourInstreamMap().put(joiningNode.getNodeID(), in);

                // Get input stream and listen
                SearchRequestListener requestListener = new SearchRequestListener(joiningNode, in, out);
                Thread thread = new Thread(requestListener);
                p2PNode.getReqListenerThreads().put(joiningNode.getNodeID(), thread);
                thread.start();


//                p2PNode.getNeighbourNodeSearchResponseListeners().add(new SearchResponseListener(in));

                debugMsg("************************************************");
                debugMsg("No. of Nodes Connected: " + p2PNode.getNeighbourNodesList().size());

                for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                    debugMsg(details.toString());
                }
                debugMsg("************************************************");
            }
        } catch (IOException e) {
            debugError("Exception in creating connections " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            debugError("Exception in creating connections " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void joinP2PSystem() throws IOException {

        debugMsg("Get the current P2P System info");
        List<NodeDetails> p2pNodesList = FileUtils.readP2PSysInfo();
        if (p2pNodesList != null) {
            debugMsg("List of nodes in the system: " + Arrays.toString(p2pNodesList.toArray()));

            debugMsg("Choose a random node and join in P2P System of size " + p2pNodesList.size());
            NodeDetails node = null;
            if (p2pNodesList.size() == 1) {
                debugMsg("Only one node is available, First client node to join");
                node = p2pNodesList.get(0);
            } else {
                node = p2pNodesList.get(ThreadLocalRandom.current().nextInt(0, p2pNodesList.size()));
            }

            p2PNode.setParentNode(node);

//            p2PNode.getParentNode().setNodeID(node.getNodeID());

            Socket socket = new Socket(node.getNodeAddress(), node.getNodePort());
            debugMsg(p2PNode.currentNode.getNodeID() + " is connected to " + node.getNodeID());

            p2PNode.getNeighbourNodesList().add(node);

            //send out node details
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            out.writeObject(p2PNode.currentNode);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            p2PNode.getNeighbourOutstreamMap().put(node.getNodeID(), out);
            p2PNode.getNeighbourSockets().put(node.getNodeID(), socket);
            p2PNode.getNeighbourInstreamMap().put(node.getNodeID(), in);

            // Get input stream and listen
            SearchRequestListener requestListener = new SearchRequestListener(node, in, out);
            Thread thread = new Thread(requestListener);
            p2PNode.getReqListenerThreads().put(node.getNodeID(), thread);
            thread.start();

            p2PNode.getNeighbourOutputStreamList().add(out);

            debugMsg("************************************************");
            debugMsg("No. of Nodes Connected: " + p2PNode.getNeighbourNodesList().size());

            for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                debugMsg(details.toString());
            }
            debugMsg("************************************************");
        } else {
            p2pNodesList = new ArrayList<>();
        }

        p2pNodesList.add(p2PNode.currentNode);
        debugMsg("Update the system info to the file");
        FileUtils.writeP2PSysInfo(p2pNodesList);
    }


    private void debugMsg(String msg) {
        System.out.println(ConnectionHelperThread.class.getSimpleName() + "-->" + msg);
    }

    private void debugSplMsg(String msg) {
        System.out.println(ConnectionHelperThread.class.getSimpleName() + "---------->" + msg + " <-----------");
    }

    private void debugError(String msg) {
        System.err.println(ConnectionHelperThread.class.getSimpleName() + "-->" + msg);
    }
}
