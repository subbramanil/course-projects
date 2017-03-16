package com.learning.aos.filesharing;


import com.learning.aos.filesharing.model.NodeDetails;

import java.io.IOException;
import java.io.ObjectInputStream;

public class SearchResponseListener implements Runnable {

    private final ObjectInputStream inputStream;
    private P2PNode p2PNode;
    private NodeDetails joiningNode;

    public SearchResponseListener(NodeDetails joiningNode, ObjectInputStream in) {
        this.inputStream = in;
        this.joiningNode = joiningNode;
        p2PNode = P2PNode.getSingetonNodeInstance();
    }

    @Override
    public void run() {
        debugMsg("Listening to reply from node: " + joiningNode.getNodeID());
        do {
            try {
                debugMsg("Response: " + inputStream.readObject());
                /*NodeDetails node = (NodeDetails) inputStream.readObject();
                if (node != null) {
                    debugMsg("Reply from Node: " + node);
                    p2PNode.setIsFileFound(true);
                    p2PNode.initTransferFile(node);
                }*/
            } catch (IOException e) {
                debugError("Exception in reading reply " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            } catch (ClassNotFoundException e) {
                debugError("Exception in reading reply " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        } while (!p2PNode.isFileFound());
    }

    public void debugMsg(String s) {
        System.out.println(SearchResponseListener.class.getSimpleName() + "-->" + s);
    }

    public void debugError(String s) {
        System.err.println(SearchResponseListener.class.getSimpleName() + "-->" + s);
    }
}
