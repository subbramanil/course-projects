package com.learning.aos.filesharing.model;

import java.net.Socket;

/**
 * Created by Subbu on 10/8/15.
 */
public class SocketInfo {
    private Socket socket;
    private NodeDetails nodeDetails;


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public NodeDetails getNodeDetails() {
        return nodeDetails;
    }

    public void setNodeDetails(NodeDetails nodeDetails) {
        this.nodeDetails = nodeDetails;
    }

    public SocketInfo(Socket peerNode, NodeDetails nodeDetails) {
        this.nodeDetails = nodeDetails;
        this.socket = peerNode;
    }
}
