package com.learning.aos.filesharing.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Subbu on 11/8/15.
 */
public class NodeDetails implements Serializable {

    private Integer nodeID;
    // IP address of the node
    private String nodeAddress;
    // Port number where the program listens for data
    private Integer nodePort;

    private List<FileInfo> fileInfoList;

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

    public List<FileInfo> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfo> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    @Override
    public String toString() {
        return "NodeDetails{" +
                "nodeID=" + nodeID +
                ", nodeAddress='" + nodeAddress + '\'' +
                ", nodePort=" + nodePort +
                ", fileInfoList=" + fileInfoList +
                '}';
    }
}
