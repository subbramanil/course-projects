package com.utd.aos.casualordering.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Subbu on 10/1/15.
 */
public class NodeDetails {

    // IP address of the node
    private String nodeAddress;
    // Port number where the program listens for data
    private int nodePort;
    // Duration for which the node waits before sending out msg
    private int nodeWaitDuration;

    public NodeDetails(String nodeAddress, int nodePort) {
        this.nodeAddress = nodeAddress;
        this.nodePort = nodePort;

    }

    @Override
    public String toString() {
        return "NodeDetails{" +
                "nodeAddress='" + nodeAddress + '\'' +
                ", nodePort=" + nodePort +
                ", nodeWaitDuration=" + nodeWaitDuration +
                '}';
    }

    public int getNodeWaitDuration() {
        return nodeWaitDuration;
    }

    public void setNodeWaitDuration(int nodeWaitDuration) {
        this.nodeWaitDuration = nodeWaitDuration;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

}
