package com.learning.threads.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Subbu on 10/1/15.
 */
public class NodeDetails implements Serializable{

    private Integer nodeID;
    // IP address of the node
    private String nodeAddress;
    // Port number where the program listens for data
    private Integer nodePort;
    // Duration for which the node waits before sending out msg
    private Integer nodeWaitDuration;

    private Boolean sendComplete;

    private Boolean recvComplete;

    private int[] nodeVector;

    private int[][] nodeMatrix;

    @Override
    public String toString() {
        return "NodeDetails{" +
                "nodeID=" + nodeID +
                ", nodeAddress='" + nodeAddress + '\'' +
                ", nodePort=" + nodePort +
                ", nodeWaitDuration=" + nodeWaitDuration +
                ", sendComplete=" + sendComplete +
                ", recvComplete=" + recvComplete +
                ", nodeVector=" + Arrays.toString(nodeVector) +
                ", nodeMatrix=" + Arrays.toString(nodeMatrix) +
                '}';
    }

    public int[][] getNodeMatrix() {
        return nodeMatrix;
    }

    public void setNodeMatrix(int[][] nodeMatrix) {
        this.nodeMatrix = nodeMatrix;
    }

    public Boolean getRecvComplete() {
        return recvComplete;
    }

    public void setRecvComplete(Boolean recvComplete) {
        this.recvComplete = recvComplete;
    }

    public int[] getNodeVector() {
        return nodeVector;
    }

    public void setNodeVector(int[] nodeVector) {
        this.nodeVector = nodeVector;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public Integer getNodeWaitDuration() {
        return nodeWaitDuration;
    }

    public void setNodeWaitDuration(Integer nodeWaitDuration) {
        this.nodeWaitDuration = nodeWaitDuration;
    }

    public Boolean getSendComplete() {
        return sendComplete;
    }

    public void setSendComplete(Boolean sendComplete) {
        this.sendComplete = sendComplete;
    }
}
