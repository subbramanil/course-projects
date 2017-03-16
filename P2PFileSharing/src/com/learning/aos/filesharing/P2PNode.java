package com.learning.aos.filesharing;

import com.learning.aos.filesharing.model.FileInfo;
import com.learning.aos.filesharing.model.NodeDetails;
import com.learning.aos.filesharing.model.SearchRequest;
import com.learning.aos.filesharing.util.FileUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class P2PNode implements Runnable {

    public NodeDetails currentNode;
    private boolean isFileFound = false;
    private List<ObjectOutputStream> neighbourOutputStreamList;
    private Map<Integer, ObjectOutputStream> neighbourOutstreamMap;
    private Map<Integer, ObjectInputStream> neighbourInstreamMap;
    private Map<Integer, Thread> reqListenerThreads;
    private Map<Integer, Socket> neighbourSockets;
    private List<NodeDetails> neighbourNodesList;
    private List<SearchResponseListener> neighbourNodeSearchResponseListeners;
    private NodeDetails parentNode;

    private P2PNode() {
    }

    public static P2PNode getSingetonNodeInstance() {
        return SingletonMasterHolder.INSTANCE;
    }

    public static void main(String args[]) {
        getSingetonNodeInstance().initMaster(Integer.parseInt(args[0]));
    }

    /**
     * Method to initialize the Master Node
     *
     * @param nodeID : ID of the Node thread
     */
    private void initMaster(int nodeID) {
        try {
            currentNode = FileUtils.readNodeInfo("nodeInfo_" + nodeID + ".json");
            neighbourOutputStreamList = new ArrayList<>();
            reqListenerThreads = new HashMap<>();
            neighbourOutstreamMap = new HashMap<>();
            neighbourInstreamMap = new HashMap<>();
            neighbourSockets = new HashMap<>();
            neighbourNodesList = new ArrayList<>();
            neighbourNodeSearchResponseListeners = new ArrayList<>();
            this.run();
        } catch (Exception e) {
            System.err.println("Exception in Initializing the Master");
        }
    }

    @Override
    public void run() {
        try {
            Thread thread = new Thread(new ConnectionHelperThread());
            thread.start();
            Thread thread2 = new Thread(new SearchHelperThread());
            thread2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTransferFile(SearchRequest request) {
        debugMsg("Initialize Transfer File from Node: " + request.getResNode());

        debugSplMsg("SUCCESS: No. of Hop counts: " + request.getHopCount());
        debugSplMsg("SUCCESS: Time elapsed " + (System.currentTimeMillis() - request.getReqTime()));

        byte[] aByte = new byte[1];
        int bytesRead;

        Socket clientSocket = null;
        InputStream inputStream = null;

        ObjectOutputStream outputStream;

        try {
            clientSocket = new Socket(request.getResNode().getNodeAddress(), request.getResNode().getNodePort() + 10);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = clientSocket.getInputStream();
            outputStream.writeObject("ready");
        } catch (IOException ex) {
            debugError("Exception in establishing connection for file transfer " + ex.getMessage());
            ex.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (inputStream != null) {

            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                // TODO: 11/16/15 consider the filename
                String fileName = "server_" + request.getFileName();
                fos = new FileOutputStream(fileName);
                bos = new BufferedOutputStream(fos);
                inputStream.read(aByte, 0, aByte.length);
                debugMsg("Transferring...");
                do {
                    baos.write(aByte);
                    bytesRead = inputStream.read(aByte);
                    System.out.print(".");
                } while (bytesRead != -1);

                debugSplMsg("File transfer complete");
                String fileContent = baos.toString();
                debugMsg("File content is " + fileContent);
                updateNodeInfo(request.getFileName(), fileContent);
                bos.write(baos.toByteArray());
                bos.flush();
                bos.close();
            } catch (IOException ex) {
                debugError("Exception in receiving file " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void updateNodeInfo(String fileName, String fileContent) {

        FileInfo fileInfo = new FileInfo();
        StringTokenizer tokenizer = new StringTokenizer(fileContent, ",");
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String str = tokenizer.nextToken();
            debugMsg("Token: " + str);
            tokens.add(str);
        }

        fileInfo.setFileName(fileName);
        fileInfo.setKeywords(tokens);
        currentNode.getFileInfoList().add(fileInfo);

        FileUtils.writeNodeInfo("nodeInfo_" + currentNode.getNodeID() + ".json", currentNode);
    }

    public void exitGracefully() {
        debugMsg("Exiting from the P2P system gracefully");
        debugMsg("Announce everyone in the node that you are leaving..");
        debugMsg("Ask neighbor to connect with the parent Node " + parentNode);
        SearchRequest newReq = new SearchRequest();
        newReq.setReqID(-999);
        newReq.setReqNodeID(currentNode.getNodeID());
        if (parentNode != null) {
            newReq.setResNode(parentNode);
        } else {
            debugMsg("I don't have parent node!!");
            NodeDetails node = getNeighbourNodesList().get(ThreadLocalRandom.current().nextInt(0, getNeighbourNodesList().size()));
            newReq.setResNode(node);
            debugMsg("let me send random child node " + node);
        }
        try {
            for (Map.Entry<Integer, ObjectOutputStream> entry : getNeighbourOutstreamMap().entrySet()) {
                entry.getValue().flush();
                debugMsg("Send Search Request with updated params: " + newReq);
                entry.getValue().writeObject(newReq);
            }
        } catch (SocketException e) {
            debugError(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            debugError(e.getLocalizedMessage());
            e.printStackTrace();
        }

        int indexToRemove = -1;
        List<NodeDetails> nodesList = FileUtils.readP2PSysInfo();
        for (NodeDetails node : nodesList) {
            if (node.getNodeID().equals(currentNode.getNodeID())) {
                indexToRemove = nodesList.indexOf(node);
            }
        }

        if (indexToRemove != -1) {
            nodesList.remove(indexToRemove);
        }

        FileUtils.writeP2PSysInfo(nodesList);
        debugMsg("Wait for neighbor nodes to finish connecting to parent Node");
        debugMsg("Close the connection & sockets & exit");
        System.exit(0);
    }

    public List<ObjectOutputStream> getNeighbourOutputStreamList() {
        return neighbourOutputStreamList;
    }

    public List<SearchResponseListener> getNeighbourNodeSearchResponseListeners() {
        return neighbourNodeSearchResponseListeners;
    }

    public boolean isFileFound() {
        return isFileFound;
    }

    public void setIsFileFound(boolean isFileFound) {
        this.isFileFound = isFileFound;
    }

    private void debugMsg(String msg) {
        System.out.println(P2PNode.class.getSimpleName() + "-->" + msg);
    }

    private void debugError(String msg) {
        System.err.println(P2PNode.class.getSimpleName() + "-->" + msg);
    }

    private void debugSplMsg(String msg) {
        System.out.println("________________________________________________________");
        System.out.println(P2PNode.class.getSimpleName() + "-->" + msg);
        System.out.println("________________________________________________________");
    }

    public List<NodeDetails> getNeighbourNodesList() {
        return neighbourNodesList;
    }

    public void setNeighbourNodesList(List<NodeDetails> neighbourNodesList) {
        this.neighbourNodesList = neighbourNodesList;
    }

    public Map<Integer, ObjectOutputStream> getNeighbourOutstreamMap() {
        return neighbourOutstreamMap;
    }

    public void setNeighbourOutstreamMap(Map<Integer, ObjectOutputStream> neighbourOutstreamMap) {
        this.neighbourOutstreamMap = neighbourOutstreamMap;
    }

    public NodeDetails getParentNode() {
        return parentNode;
    }

    public void setParentNode(NodeDetails parentNode) {
        this.parentNode = parentNode;
    }

    public Map<Integer, Socket> getNeighbourSockets() {
        return neighbourSockets;
    }

    public void setNeighbourSockets(Map<Integer, Socket> neighbourSockets) {
        this.neighbourSockets = neighbourSockets;
    }

    public Map<Integer, ObjectInputStream> getNeighbourInstreamMap() {
        return neighbourInstreamMap;
    }

    public void setNeighbourInstreamMap(Map<Integer, ObjectInputStream> neighbourInstreamMap) {
        this.neighbourInstreamMap = neighbourInstreamMap;
    }

    public Map<Integer, Thread> getReqListenerThreads() {
        return reqListenerThreads;
    }

    public void setReqListenerThreads(Map<Integer, Thread> reqListenerThreads) {
        this.reqListenerThreads = reqListenerThreads;
    }

    private static class SingletonMasterHolder {
        private static final P2PNode INSTANCE = new P2PNode();
    }
}
