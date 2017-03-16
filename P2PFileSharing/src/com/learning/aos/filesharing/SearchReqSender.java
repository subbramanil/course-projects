package com.learning.aos.filesharing;


import com.learning.aos.filesharing.model.SearchRequest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class SearchReqSender implements Runnable {

    private static final long SEARCH_TIMEOUT = 10 * 1000;

    private SearchRequest request;
    private P2PNode p2PNode;

    public SearchReqSender(SearchRequest request) {
        this.request = request;
        this.p2PNode = P2PNode.getSingetonNodeInstance();
    }

    @Override
    public void run() {
        sendSearchRequest(request);
    }


    private void sendSearchRequest(SearchRequest request) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            int prevHopCount = 0;

            @Override
            public void run() {
                debugMsg("Make the search request once in every 10 seconds");
                request.setReqNodeID(p2PNode.currentNode.getNodeID());
                if (prevHopCount == 0) {
                    prevHopCount = 1;
                } else {
                    prevHopCount *= 2;
                }
                if (prevHopCount > 16 || p2PNode.isFileFound()) {
                    timer.cancel();
                    if (!p2PNode.isFileFound()) {
                        debugMsg("Canceling the request since the Hop Count > 16");
                        debugSplMsg("FAILURE: File not found after max hop count");
                    } else {
                        debugMsg("Canceling the request since File is found");
                        debugSplMsg("SUCCESS: File found and transferred");
                    }
                } else {
                    SearchRequest newReq = new SearchRequest();
                    newReq.setReqNodeID(request.getReqNodeID());
                    newReq.setReqID(request.getReqID());
                    newReq.setHopCount(prevHopCount);
                    newReq.setKeyword(request.getKeyword());
                    newReq.setFileName(request.getFileName());
                    try {
                        if (p2PNode != null) {

                            newReq.setReqTime(System.currentTimeMillis());

                            for (Map.Entry<Integer, ObjectOutputStream> entry : p2PNode.getNeighbourOutstreamMap().entrySet()) {
                                entry.getValue().flush();
                                debugSplMsg("Send Search Request with updated params: " + newReq);
                                entry.getValue().writeObject(newReq);
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
            }
        }, 1000, SEARCH_TIMEOUT);
    }

    public void debugMsg(String s) {
        System.out.println(SearchReqSender.class.getSimpleName() + "-->" + s);
    }

    private void debugError(String msg) {
        System.err.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
    }

    private void debugSplMsg(String msg) {
        System.out.println("________________________________________________________");
        System.out.println(SearchReqSender.class.getSimpleName() + "-->" + msg);
        System.out.println("________________________________________________________");
    }
}
