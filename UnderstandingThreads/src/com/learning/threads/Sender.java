package com.learning.threads;

import com.learning.threads.model.MessageDetails;
import com.learning.threads.model.NodeDetails;
import com.learning.threads.model.SocketInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Subbu on 10/8/15.
 */
public class Sender implements Runnable {


    private final List<SocketInfo> socketInfos;
    boolean isMulticast;
    MessageDetails msgDetails;
    NodeDetails currentNode;

    public Sender(List<SocketInfo> socketInfos, NodeDetails currentNode, boolean isMulticast) {
        this.socketInfos = socketInfos;
        this.currentNode = currentNode;
        this.isMulticast = isMulticast;
    }

    @Override
    public void run() {
        debugMsg("Is Mulitcasting:: " + isMulticast);
        if (!isMulticast) {
            debugMsg("BroadCast");
            startBroadCasting();
        } else {
            debugMsg("Multicast");
            startMulticasting();
        }
    }

    /**
     * Method to broadcast the message to every node that's connected
     */
    public void startBroadCasting() {

        debugMsg("No of Nodes to be messaged: " + socketInfos.size());

        System.out.println("+++++++++++++++++++++++++");
        for (SocketInfo s : socketInfos) {
            debugMsg("" + s.getNodeDetails());
            try {
                debugMsg("" + s.getSocket().getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("+++++++++++++++++++++++++");

        for (int i = 1; i <= 3; i++) {
            for (SocketInfo info : socketInfos) {
                NodeDetails node = info.getNodeDetails();
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(info.getSocket().getOutputStream());

                    //prepare the message
                    msgDetails = new MessageDetails();
                    msgDetails.setMsgNumber(i);
                    msgDetails.setNodeID(node.getNodeID());
                    msgDetails.setTimeStamp(new Date().getTime());

                    // increment the message counter for self

                    debugMsg("Initial: " + Arrays.toString(currentNode.getNodeVector()));
                    debugMsg("Incrementing self counter");
                    ++currentNode.getNodeVector()[currentNode.getNodeID()];
                    debugMsg("updated 1: " + Arrays.toString(currentNode.getNodeVector()));
                    msgDetails.setMsgVector(currentNode.getNodeVector());
                    debugMsg("updated 2: " + Arrays.toString(msgDetails.getMsgVector()));

                    // delay introduced to realise the buffering in casual broadcasting
                    debugMsg("Node " + currentNode.getNodeID() + " waiting for " + node.getNodeWaitDuration() + " ms before sending msg ");
                    Thread.sleep(node.getNodeWaitDuration());

                    debugMsg("Node " + currentNode.getNodeID() + " Sending Msg:" + Arrays.toString(msgDetails.getMsgVector()) + " to node " + node.getNodeID() + " " + msgDetails.getNodeID());

                    outputStream.flush();
                    outputStream.writeObject(msgDetails);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Breaking the execution");
                    System.exit(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("Breaking the execution");
                    System.exit(1);
                }
            }
        }

        // set send complete to true
        currentNode.setSendComplete(true);
    }

    /**
     * Method to multicast messages to selected nodes
     */
    public void startMulticasting() {
        debugMsg("No of Nodes to be messaged: " + socketInfos.size());

        for (int i = 1; i <= 100; i++) {
            for (SocketInfo info : socketInfos) {
                NodeDetails node = info.getNodeDetails();
                ObjectOutputStream outputStream;
                try {
                    outputStream = new ObjectOutputStream(info.getSocket().getOutputStream());

                    //prepare the message
                    msgDetails = new MessageDetails();
                    msgDetails.setMsgNumber(i);
                    msgDetails.setNodeID(node.getNodeID());
                    msgDetails.setTimeStamp(new Date().getTime());

                    // Update the column of the node
                    for (int j = 0; j < currentNode.getNodeMatrix()[currentNode.getNodeID()].length; j++) {
                        if (j == node.getNodeID()) {
                            currentNode.getNodeMatrix()[node.getNodeID()][j]++;
                        }
                    }
                    msgDetails.setMsgMatrix(currentNode.getNodeMatrix());

                    debugMsg(node.getNodeID() + " waiting for " + node.getNodeWaitDuration() + " ms before sending msg ");
                    Thread.sleep(node.getNodeWaitDuration());

                    debugMsg(currentNode.getNodeID() + " Sending Msg to " + node.getNodeID());
                    printMatrix(msgDetails.getMsgMatrix());

                    outputStream.writeObject(msgDetails);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Breaking the execution");
                    System.exit(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.err.println("Breaking the execution");
                    System.exit(1);
                }
            }
        }
        //set send complete to true
        currentNode.setSendComplete(true);
        debugMsg(currentNode.getNodeID() + " send complete " + currentNode.getSendComplete());
    }

    /**
     * Utility method to print the matrix
     *
     * @param matrix : 2D Matrix to be printed
     */
    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void debugMsg(String s) {
        System.out.println(Sender.class.getSimpleName() + "-->" + s);
    }
}
