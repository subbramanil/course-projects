package com.learning.aos.filesharing;

import com.learning.aos.filesharing.model.FileInfo;
import com.learning.aos.filesharing.model.NodeDetails;
import com.learning.aos.filesharing.model.SearchRequest;
import com.learning.aos.filesharing.util.FileUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class ClientNode implements Runnable {

    public NodeDetails currentNode;
    Queue<SearchRequest> requestQueue;
    private ObjectOutputStream reqOutputStream;
    private ObjectInputStream reqInputStream;
    private SearchRequest request;
    private boolean isFileFound;
    private Socket socket;
    private List<NodeDetails> p2pNodesList;
    private List<ObjectOutputStream> neighbourOutputStreamList;

    private ClientNode() {
    }

    public static ClientNode getClientInstance() {
        return SingletonMasterHolder.INSTANCE;
    }

    public static void main(String args[]) {
        ClientNode.getClientInstance().initNode(Integer.parseInt(args[0]));
    }

    /**
     * Method to initialize the Node
     *
     * @param nodeID : NodeID of the Node thread
     */
    private void initNode(int nodeID) {
        try {

            requestQueue = new PriorityQueue<>();
            neighbourOutputStreamList = new ArrayList<>();

            // TODO: 11/8/15 Initialize the node & update the node details
            currentNode = FileUtils.readNodeInfo("nodeInfo_" + nodeID + ".json");
            if (currentNode != null) {
                debugMsg("Current Node Information: " + currentNode);
                Thread thread = new Thread(new NodeConnectionHelper());
                thread.start();
                debugMsg("Updated Node Information: " + currentNode);
                Thread thread1 = new Thread(this);
                thread1.start();
            }

        } catch (Exception e) {
            System.err.println("Exception in Initializing the Node" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void joinP2PSystem() throws IOException {

        debugMsg("Get the current P2P System info");
        p2pNodesList = FileUtils.readP2PSysInfo();
        debugMsg("List of nodes in the system: " + Arrays.toString(p2pNodesList.toArray()));

        debugMsg("Choose a random node and join in P2P System of size " + p2pNodesList.size());
        NodeDetails node = null;
        if (p2pNodesList.size() == 1) {
            debugMsg("Only one node is available, First client node to join");
            node = p2pNodesList.get(0);
        } else {
            node = p2pNodesList.get(ThreadLocalRandom.current().nextInt(0, p2pNodesList.size()));
        }

        debugMsg("Connecting to " + node);

        socket = new Socket(node.getNodeAddress(), node.getNodePort());
        // Get input stream and listen
        reqInputStream = new ObjectInputStream(socket.getInputStream());
        reqOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        socket.getOutputStream().flush();

        p2pNodesList.add(currentNode);
        debugMsg("Update the system info to the file");
        FileUtils.writeP2PSysInfo(p2pNodesList);

    }

    private FileInfo processSearchRequest(SearchRequest request) {
        debugMsg("Received Search request: " + request);
        debugMsg("Current Node Info: " + currentNode);

        if (request.getFileName() != null) {
            debugMsg("Search by File Name:" + request.getFileName());
            for (FileInfo fileInfo : currentNode.getFileInfoList()) {
                if (fileInfo.getFileName().equals(request.getFileName())) {
                    debugMsg("Found the file");
                    isFileFound = true;
                    return fileInfo;
                }
            }
        } else {
            debugMsg("Search by Keyword:" + request.getKeyword());
            for (FileInfo fileInfo : currentNode.getFileInfoList()) {
                for (String keyword : fileInfo.getKeywords()) {
                    if (keyword.equals(request.getKeyword())) {
                        isFileFound = true;
                        return fileInfo;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {

            joinP2PSystem();

            while (true) {
                debugMsg("Receiving the search request: ");

                request = (SearchRequest) reqInputStream.readObject();
                debugMsg("Received search request from Master node: " + request);

                // TODO: 11/14/15 Check for duplicate requests
                if (!requestQueue.isEmpty()) {
                    if (!requestQueue.contains(request)) {
                        debugMsg("Not a duplicate request.. Add it to queue");
                        requestQueue.add(request);
                    } else {
                        debugMsg("Duplicate request");
                    }
                } else {
                    debugMsg("Queue is empty.. add the request");
                    requestQueue.add(request);
                }

                while (!requestQueue.isEmpty()) {
                    request = requestQueue.peek();
                    FileInfo fileToSent = processSearchRequest(request);
                    // file found
                    if (fileToSent != null) {
                        debugMsg("Inform master that file is found");
                        reqOutputStream.flush();
                        reqOutputStream.writeObject(currentNode);

                        ServerSocket welcomeSocket = null;
                        Socket connectionSocket = null;
                        BufferedOutputStream outToClient = null;
                        ObjectInputStream objInputStream;
                        try {
                            debugMsg("Opening up port " + currentNode + " for file transfer");
                            welcomeSocket = new ServerSocket(currentNode.getNodePort() + 10);
                            connectionSocket = welcomeSocket.accept();

                            outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());

                            objInputStream = new ObjectInputStream(connectionSocket.getInputStream());
                            String serverStatus = (String) objInputStream.readObject();
                            if (serverStatus.equals("ready")) {
                                transferFile(fileToSent, outToClient);
                            }
                        } catch (IOException ex) {
                            debugError("Exception in establishing connection for file transfer " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    } else {
                        debugMsg("File is not found");
                        debugMsg("Request Hop Count: " + request.getHopCount());
                        if (request.getHopCount() > 1) {
                            debugMsg("Request can be forwarded.. ");
                            // TODO: 11/15/15 reduce the hopcount and forward the request to the neighbor nodes
                            Thread thread = new Thread(new NodeSearchReqForwarder(request));
                            thread.start();
                        } else {
                            debugMsg("Request can't be forwarded");
                        }
                    }
                    debugMsg("Done with search request: " + requestQueue.remove(request));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to transfer the file, once found
     *
     * @param fileToSent
     */
    private void transferFile(FileInfo fileToSent, BufferedOutputStream outToClient) {
        debugMsg("Begin transferring file " + fileToSent);

        if (outToClient != null) {
//            File myFile = new File(fileToSent.getFileName());
            File myFile = new File("a.sk");
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = null;

            try {
                fis = new FileInputStream(myFile);
            } catch (FileNotFoundException ex) {
                debugMsg("File not found on the Node " + ex.getMessage());
                ex.printStackTrace();
            }

            BufferedInputStream bis = null;
            if (fis != null) {
                bis = new BufferedInputStream(fis);
                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    debugMsg("File transfer complete");
//                    socket.close();
                } catch (IOException ex) {
                    debugError("Exception in transferring file " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private void debugMsg(String msg) {
        System.out.println(ClientNode.class.getSimpleName() + "-->" + msg);
    }

    private void debugError(String msg) {
        System.err.println(ClientNode.class.getSimpleName() + "-->" + msg);
    }

    public List<ObjectOutputStream> getNeighbourOutputStreamList() {
        return neighbourOutputStreamList;
    }

    private static class SingletonMasterHolder {
        private static final ClientNode INSTANCE = new ClientNode();
    }
}
