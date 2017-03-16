package com.learning.aos.filesharing;


import com.learning.aos.filesharing.model.SearchRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.List;


public class NodeSearchReqForwarder implements Runnable {

    private SearchRequest request;
    private ClientNode clientNode;

    public NodeSearchReqForwarder(SearchRequest request) {
        this.request = request;
        this.clientNode = ClientNode.getClientInstance();
    }

    @Override
    public void run() {
        forwardSearchRequest(request);
    }


    private void forwardSearchRequest(SearchRequest request) {
        try {
            if (clientNode != null) {
                List<ObjectOutputStream> neighbourOutputStreamList = clientNode.getNeighbourOutputStreamList();
                if (neighbourOutputStreamList != null) {
                    for (ObjectOutputStream out : neighbourOutputStreamList) {
                        out.flush();
                        debugMsg("Send Search Request with updated params: " + request);
                        out.writeObject(request);
                    }
                } else {
                    debugMsg("Output Stream list is null");
                }
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
        System.out.println(NodeSearchReqForwarder.class.getSimpleName() + "-->" + s);
    }

    private void debugError(String msg) {
        System.err.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
    }
}
