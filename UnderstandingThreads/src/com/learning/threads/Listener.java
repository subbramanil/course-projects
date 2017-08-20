package com.learning.threads;

import com.learning.threads.model.MessageDetails;
import com.learning.threads.model.NodeDetails;
import com.learning.threads.model.SocketInfo;
import com.learning.threads.util.Statistics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Subbu on 10/8/15.
 */
public class Listener implements Runnable {

    private final NodeDetails currentNode;
    boolean isMulticast;
    private SocketInfo socketInfo;
    private double[] latencyArray;

    ObjectInputStream inputStream = null;
    private List<MessageDetails> bufferList;
    private int msgCount = 0;

    public Listener(SocketInfo info, NodeDetails currentNode, boolean isMulticast) {
        socketInfo = info;
        this.currentNode = currentNode;
        this.isMulticast = isMulticast;
        bufferList = new ArrayList<>();
        latencyArray = new double[100];
    }

    @Override
    public void run() {
        while (true) {
            try {
                inputStream = new ObjectInputStream(socketInfo.getSocket().getInputStream());

                int delay = ThreadLocalRandom.current().nextInt(50, 200);
                debugMsg("Delaying for " + delay + " ms before delivering");
                Thread.sleep(delay);

                MessageDetails msg = (MessageDetails) inputStream.readObject();

                System.out.println("--------> received Msg "+msg);

                latencyArray[msg.getNodeID()] = (new Date().getTime()) - msg.getTimeStamp();

                if (!isMulticast) {
                    debugMsg("Received msg:" + Arrays.toString(msg.getMsgVector()) + " from " + msg.getNodeID());
                    updateNodeVector(msg);
                } else {
                    debugMsg("Received msg from " + msg.getNodeID());
                    printMatrix(msg.getMsgMatrix());
                    updateNodeMatrix(msg);
                }

                debugMsg("Message count: " + (++msgCount));
                if (msgCount == 3) {
                    debugMsg("bring down the computation");
                    if (!isMulticast) {
                        debugMsg("Final Node Vector is " + Arrays.toString(currentNode.getNodeVector()));
                    } else {
                        debugMsg("Final Node Matrix");
                        printMatrix(currentNode.getNodeMatrix());
                    }
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Breaking the execution");
                System.exit(1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Statistics stats = new Statistics(latencyArray);
        debugMsg("For the node " + currentNode.getNodeID() + ", The latency: " + stats.getMean());
        debugMsg("For the node " + currentNode.getNodeID() + ", The standard deviation: " + stats.getStdDev());
    }

    /**
     * Synchronized method to update the Node matrix for multicasting
     *
     * @param msg : Message received
     */
    private synchronized void updateNodeMatrix(MessageDetails msg) {

        int nodeID = currentNode.getNodeID();
        debugMsg("Current Node ID: " + nodeID);
        debugMsg("Node Matrix: ");
        printMatrix(currentNode.getNodeMatrix());

        int msgNodeID = msg.getNodeID();
        debugMsg("Received Msg from " + msgNodeID);
        debugMsg("Message Matrix: ");
        printMatrix(msg.getMsgMatrix());

        debugMsg("Node: ");
        for (int n : currentNode.getNodeMatrix()[nodeID]) {
            System.out.print(n + " ");
        }

        debugMsg("Message: ");
        for (int n : msg.getMsgMatrix()[nodeID]) {
            System.out.print(n + " ");
        }

        int diff = 0;
        for (int i = 0; i < currentNode.getNodeMatrix().length; i++) {
            if (currentNode.getNodeMatrix()[nodeID][i] < msg.getMsgMatrix()[nodeID][i]) {
                diff++;
            }
        }

        if (diff > 1) {
            debugMsg("Buffer Msg " + msg + " for later delivery");
            bufferList.add(msg);
        } else {
            debugMsg("No need to buffer");
            debugMsg("Take component wise max and update matrix values");
            // Update other matrix
            for (int i = 0; i < currentNode.getNodeMatrix().length; i++) {
                for (int j = 0; j < currentNode.getNodeMatrix()[i].length; j++) {
                    int n1 = currentNode.getNodeMatrix()[i][j];
                    int n2 = msg.getMsgMatrix()[i][j];
                    if (n1 < n2) {
                        debugMsg("Updating node[" + i + "][" + j + "] value from " + n1 + " to " + n2);
                        currentNode.getNodeMatrix()[i][j] = msg.getMsgMatrix()[i][j];
                    }
                }
            }

            debugMsg("Checking Buffer to see if any message can be delivered...");
            checkBuffer(nodeID);
        }
    }

    /**
     * Synchronized Method to check th buffer to see if any messages can be delivered from buffer
     *
     * @param nodeID : ID of the node
     */
    private synchronized void checkBuffer(int nodeID) {
        int diff = 0;
        if (bufferList.size() != 0) {
            for (int x = 0; x < bufferList.size(); x++) {
                MessageDetails msg = bufferList.get(x);
                for (int i = 0; i < currentNode.getNodeMatrix().length; i++) {
                    if (currentNode.getNodeMatrix()[nodeID][i] < msg.getMsgMatrix()[nodeID][i]) {
                        diff++;
                    }
                }

                if (diff > 1) {
                    debugMsg("Do not remove from Buffer. Keep Msg " + msg + " for later delivery");
                } else {
                    debugMsg("Remove msg from buffer");
                    for (int i = 0; i < currentNode.getNodeMatrix().length; i++) {
                        currentNode.getNodeMatrix()[nodeID][i] = msg.getMsgMatrix()[nodeID][i];
                    }
                    bufferList.remove(x);
                }
            }
        } else {
            debugMsg("Empty buffer");
        }
    }


    /**
     * Utility Method to print the 2D Matrix
     *
     * @param matrix : 2D integer matrix
     */
    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Synchronized Method to update the Node vector for broadcasting
     *
     * @param msg : Message to be checked against node
     */
    private synchronized void updateNodeVector(MessageDetails msg) {

        int nodeID = msg.getNodeID();

        debugMsg("Current Node ID: "+currentNode.getNodeID()+" msg received from Node: "+nodeID);

        debugMsg("Comparing Node Vector: " + Arrays.toString(currentNode.getNodeVector()) + " with Node " + nodeID + " msg vector:" + Arrays.toString(msg.getMsgVector()));

        debugMsg("Updating : " + currentNode.getNodeVector()[nodeID] + " with " + msg.getMsgVector()[nodeID]);
        if(nodeID!=currentNode.getNodeID())
            currentNode.getNodeVector()[nodeID] = msg.getMsgVector()[nodeID];
    }

    public void debugMsg(String s){
        System.out.println(Listener.class.getSimpleName() + "-->" + s);
    }
}
