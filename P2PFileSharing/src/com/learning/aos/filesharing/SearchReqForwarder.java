package com.learning.aos.filesharing;


import com.learning.aos.filesharing.model.SearchRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Map;


public class SearchReqForwarder implements Runnable {

    private SearchRequest request;
    private P2PNode p2PNode;

    public SearchReqForwarder(SearchRequest request) {
        this.request = request;
        this.p2PNode = P2PNode.getSingetonNodeInstance();
    }

    @Override
    public void run() {
        forwardSearchRequest(request);
    }


    private void forwardSearchRequest(SearchRequest request) {
        try {
            Map<Integer, ObjectOutputStream> neighborOutstreamMap = p2PNode.getNeighbourOutstreamMap();
            if (neighborOutstreamMap != null) {
                for (Map.Entry<Integer, ObjectOutputStream> entry : neighborOutstreamMap.entrySet()) {
                    if (request.getReqNodeID() != entry.getKey()) {
                        entry.getValue().flush();
//                        request.setReqNodeID(p2PNode.currentNode.getReqNodeID());
                        debugMsg("Forward Search Request to Node " + entry.getKey() + " with updated params: " + request);
                        entry.getValue().writeObject(request);
                    }
                }
            } else {
                debugMsg("Output Stream list is null");
            }
        } catch (SocketException e) {
            debugError(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            debugError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void debugMsg(String s) {
        System.out.println(SearchReqForwarder.class.getSimpleName() + "-->" + s);
    }

    private void debugError(String msg) {
        System.err.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
    }
}
