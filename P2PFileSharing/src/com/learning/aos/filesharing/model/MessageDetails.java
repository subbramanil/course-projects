package com.learning.aos.filesharing.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Subbu on 10/8/15.
 */
public class MessageDetails implements Serializable{

    private int nodeID;
    private int msgNumber;
    private int[] msgVector;
    private long timeStamp;
    private int[][] msgMatrix;


    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getMsgNumber() {
        return msgNumber;
    }

    public void setMsgNumber(int msgNumber) {
        this.msgNumber = msgNumber;
    }

    public int[] getMsgVector() {
        return msgVector;
    }

    public void setMsgVector(int[] msgVector) {
        this.msgVector = msgVector;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int[][] getMsgMatrix() {
        return msgMatrix;
    }

    public void setMsgMatrix(int[][] msgMatrix) {
        this.msgMatrix = msgMatrix;
    }

    @Override
    public String toString() {
        return "MessageDetails{" +
                "nodeID=" + nodeID +
                ", msgNumber=" + msgNumber +
                ", msgVector=" + Arrays.toString(msgVector) +
                ", timeStamp=" + timeStamp +
                ", msgMatrix=" + Arrays.toString(msgMatrix) +
                '}';
    }
}
