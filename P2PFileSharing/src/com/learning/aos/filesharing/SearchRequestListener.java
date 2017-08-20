package com.learning.aos.filesharing;


import com.learning.aos.filesharing.model.FileInfo;
import com.learning.aos.filesharing.model.NodeDetails;
import com.learning.aos.filesharing.model.SearchRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SearchRequestListener implements Runnable {

    private final ObjectInputStream reqInputStream;
    private final ObjectOutputStream reqOutputStream;
    private P2PNode p2PNode;
    private NodeDetails joiningNode;

    private Map<Integer, Boolean> requestMap;

    public SearchRequestListener(NodeDetails joiningNode, ObjectInputStream in, ObjectOutputStream out) {
        this.reqInputStream = in;
        this.reqOutputStream = out;
        this.joiningNode = joiningNode;
        p2PNode = P2PNode.getSingetonNodeInstance();
        requestMap = new HashMap<>();
    }

    @Override
    public void run() {
        debugMsg("Listening to reply from node: " + joiningNode.getNodeID());
        SearchRequest request = null;
        try {
            while (true) {
                debugMsg("Receiving the search request: ");
                request = (SearchRequest) reqInputStream.readObject();
                if (request.getReqID() != -999) {
                    if (request.isFileFound()) {
                        NodeDetails node = request.getResNode();
                        debugMsg("Response for search request: " + node);

                        if (request.getReqNodeID() == p2PNode.currentNode.getNodeID()) {
                            debugMsg("I'm the one who initiated the search request.. I will initiate file transfer");
                            p2PNode.setIsFileFound(true);
                            p2PNode.initTransferFile(request);
                        } else {
                            // TODO: 11/16/15 forward the reply to the node that made the search request
                            debugMsg("whom to forward the request to Node:" + request.getReqNodeID());
                            debugSplMsg("Forward the Response:" + request);
                            p2PNode.getNeighbourOutstreamMap().get(request.getReqNodeID()).writeObject(request);
                        }

                    } else {
                        debugMsg("Search request ");
                        debugSplMsg("Received search request from node: " + request);

                        if (!requestMap.containsKey(request.getReqID())) {
                            debugMsg("New request: Process the Search request");
                            FileInfo fileToSent = processSearchRequest(request);
                            if (fileToSent != null) {
                                requestMap.put(request.getReqID(), true);
                                debugSplMsg("Inform master that file is found");
                                request.setFileName(fileToSent.getFileName());
                                request.setIsFileFound(true);
                                request.setResNode(p2PNode.currentNode);
                                reqOutputStream.flush();
                                reqOutputStream.writeObject(request);
                                ServerSocket welcomeSocket = null;
                                Socket connectionSocket = null;
                                BufferedOutputStream outToClient = null;
                                ObjectInputStream objInputStream;
                                try {
                                    debugMsg("Opening up port " + p2PNode.currentNode + " for file transfer");
                                    welcomeSocket = new ServerSocket(p2PNode.currentNode.getNodePort() + 10);
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
                                requestMap.put(request.getReqID(), false);
                                debugMsg("Request Hop Count: " + request.getHopCount());
                                if (request.getHopCount() > 1) {
                                    debugMsg("Request can be forwarded.. ");
                                    int val = request.getHopCount();
                                    request.setHopCount(--val);
                                    if (p2PNode.getNeighbourNodesList().size() > 1) {
                                        Thread thread = new Thread(new SearchReqForwarder(request));
                                        thread.start();
                                    } else {
                                        debugMsg("File can't be found in the current node.. Search in vain");
                                    }
                                } else {
                                    debugMsg("Request can't be forwarded");
                                }
                            }
                        } else {
                            if (!requestMap.get(request.getReqID())) {
                                debugMsg("Old request: Check the hop count and forward");
                                debugMsg("Request Hop Count: " + request.getHopCount());
                                if (request.getHopCount() > 1) {
                                    debugMsg("Request can be forwarded.. ");
                                    int val = request.getHopCount();
                                    request.setHopCount(--val);
                                    if (p2PNode.getNeighbourNodesList().size() > 1) {
                                        Thread thread = new Thread(new SearchReqForwarder(request));
                                        thread.start();
                                    } else {
                                        debugMsg("File can't be found in the current node.. Search in vain");
                                    }
                                } else {
                                    debugMsg("Request can't be forwarded");
                                }
                            } else {
                                debugMsg("File is found and reply is sent already");
                            }
                        }

                    }
                } else {
                    debugMsg("Exiting Node:  " + request.getReqNodeID());

                    Map<Integer, ObjectOutputStream> neighbourOutStreamMap = p2PNode.getNeighbourOutstreamMap();
                    int newParentNodeID = request.getResNode().getNodeID();
                    if (p2PNode.currentNode.getNodeID() == newParentNodeID) {
                        debugMsg("Its me.. no need to create a new socket connection");
                        debugMsg("Remove the output stream for the request ID :" +
                                neighbourOutStreamMap.remove(request.getReqNodeID()));
                        int indexToRemove = -1;
                        for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                            if (details.getNodeID() == request.getReqNodeID()) {
                                indexToRemove = p2PNode.getNeighbourNodesList().indexOf(details);
                                debugMsg("Node " + details.toString() + " removed from neighbour");
                            }
                        }
                        if (indexToRemove != -1)
                            p2PNode.getNeighbourNodesList().remove(indexToRemove);

                        p2PNode.getNeighbourSockets().get(request.getReqNodeID()).close();
                        p2PNode.getNeighbourSockets().remove(request.getReqNodeID());

                        p2PNode.getNeighbourInstreamMap().get(request.getReqNodeID()).close();
                        p2PNode.getNeighbourInstreamMap().remove(request.getReqNodeID());

                        p2PNode.getReqListenerThreads().get(request.getReqNodeID()).stop();
                        p2PNode.getReqListenerThreads().remove(request.getReqNodeID());

                        debugMsg("************************************************");
                        debugMsg("No. of Nodes Connected: " + p2PNode.getNeighbourNodesList().size());
                        for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                            debugMsg(details.toString());
                        }
                        debugMsg("************************************************");

                        // TODO: 11/16/15 what todo with the socket??
                    } else if (neighbourOutStreamMap.containsKey(newParentNodeID)) {
                        debugMsg("Already a neighbour.. No need to create a new socket connection ");
                    } else {
                        debugSplMsg("Exiting node: " + request.getResNode());
                        Socket socket = new Socket(request.getResNode().getNodeAddress(), request.getResNode().getNodePort());
                        debugMsg(p2PNode.currentNode.getNodeID() + " is connected to " + request.getResNode().getNodeID());

                        p2PNode.getNeighbourNodesList().add(request.getResNode());

                        int indexToRemove = -1;
                        for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                            if (details.getNodeID() == request.getReqNodeID()) {
                                indexToRemove = p2PNode.getNeighbourNodesList().indexOf(details);
                                debugMsg("Node " + details.toString() + " removed from neighbour");
                            }
                        }
                        if (indexToRemove != -1)
                            p2PNode.getNeighbourNodesList().remove(indexToRemove);

                        //send out node details
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.flush();
                        out.writeObject(p2PNode.currentNode);

                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                        p2PNode.getNeighbourOutstreamMap().put(request.getResNode().getNodeID(), out);
                        p2PNode.getNeighbourSockets().put(request.getResNode().getNodeID(), socket);
                        p2PNode.getNeighbourInstreamMap().put(request.getResNode().getNodeID(), in);

                        // Get input stream and listen
                        SearchRequestListener requestListener = new SearchRequestListener(request.getResNode(), in, out);
                        Thread thread = new Thread(requestListener);
                        p2PNode.getReqListenerThreads().put(request.getResNode().getNodeID(), thread);
                        thread.start();

                        p2PNode.getNeighbourOutputStreamList().add(out);

                        debugMsg("************************************************");
                        debugMsg("No. of Nodes Connected: " + p2PNode.getNeighbourNodesList().size());
                        for (NodeDetails details : p2PNode.getNeighbourNodesList()) {
                            debugMsg(details.toString());
                        }
                        debugMsg("************************************************");

                        // TODO: 11/16/15 consider informing the node

                        p2PNode.getNeighbourSockets().get(request.getReqNodeID()).close();
                        p2PNode.getNeighbourSockets().remove(request.getReqNodeID());

                        p2PNode.getNeighbourInstreamMap().get(request.getReqNodeID()).close();
                        p2PNode.getNeighbourInstreamMap().remove(request.getReqNodeID());

                        p2PNode.getReqListenerThreads().get(request.getReqNodeID()).stop();
                        p2PNode.getReqListenerThreads().remove(request.getReqNodeID());

                        debugMsg("Remove the output stream for the request ID :" +
                                neighbourOutStreamMap.remove(request.getReqNodeID()));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileInfo processSearchRequest(SearchRequest request) {
        debugMsg("Received Search request: " + request);
        debugMsg("Current Node Info: " + p2PNode.currentNode);

        if (request.getFileName() != null) {
            debugMsg("Search by File Name:" + request.getFileName());
            for (FileInfo fileInfo : p2PNode.currentNode.getFileInfoList()) {
                if (fileInfo.getFileName().equals(request.getFileName())) {
                    debugSplMsg("Found the file");
                    return fileInfo;
                }
            }
        } else {
            debugMsg("Search by Keyword:" + request.getKeyword());
            for (FileInfo fileInfo : p2PNode.currentNode.getFileInfoList()) {
                for (String keyword : fileInfo.getKeywords()) {
                    if (keyword.equals(request.getKeyword())) {
                        debugSplMsg("Found the file");
                        return fileInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method to transfer the file, once found
     *
     * @param fileToSent
     */
    private void transferFile(FileInfo fileToSent, BufferedOutputStream outToClient) {
        debugMsg("Begin transferring file " + fileToSent);

        if (outToClient != null) {
            File myFile = new File(fileToSent.getFileName());
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

    public void debugMsg(String s) {
        System.out.println(SearchRequestListener.class.getSimpleName() + "-->" + s);
    }

    public void debugError(String s) {
        System.err.println(SearchRequestListener.class.getSimpleName() + "-->" + s);
    }

    private void debugSplMsg(String msg) {
        System.out.println("________________________________________________________");
        System.out.println(SearchRequestListener.class.getSimpleName() + "-->" + msg);
        System.out.println("________________________________________________________");
    }
}
