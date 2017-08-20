package com.learning.aos.filesharing.model;

import java.io.Serializable;

/**
 * Created by Subbu on 11/8/15.
 */
public class SearchRequest implements Serializable {

    private int reqID;
    private String fileName;
    private String keyword;
    private int hopCount;
    private int reqNodeID;
    private boolean isFileFound;
    private NodeDetails resNode;
    private long reqTime;

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getHopCount() {
        return hopCount;
    }

    public void setHopCount(int hopCount) {
        this.hopCount = hopCount;
    }

    public int getReqNodeID() {
        return reqNodeID;
    }

    public void setReqNodeID(int reqNodeID) {
        this.reqNodeID = reqNodeID;
    }

    public boolean isFileFound() {
        return isFileFound;
    }

    public void setIsFileFound(boolean isFileFound) {
        this.isFileFound = isFileFound;
    }

    public NodeDetails getResNode() {
        return resNode;
    }

    public void setResNode(NodeDetails resNode) {
        this.resNode = resNode;
    }

    public long getReqTime() {
        return reqTime;
    }

    public void setReqTime(long reqTime) {
        this.reqTime = reqTime;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "reqID=" + reqID +
                ", fileName='" + fileName + '\'' +
                ", keyword='" + keyword + '\'' +
                ", hopCount=" + hopCount +
                ", reqNodeID=" + reqNodeID +
                ", isFileFound=" + isFileFound +
                ", resNode=" + resNode +
                ", reqTime=" + reqTime +
                '}';
    }
}
