package com.utd.aos.casualordering;

import com.utd.aos.casualordering.model.NodeDetails;
import com.utd.aos.casualordering.utils.AOSUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subbu on 10/1/15.
 */
public class Driver {


    private static List<NodeDetails> nodeList;

    public static List<NodeDetails> configureNodes(String fileName) {
        System.out.println("____________________________________________________");
        System.out.println("configureNodes() entry");
        String line;
        NodeDetails node = null;
        List<NodeDetails> nodes = new ArrayList<>();
        try {
            File output = new File(fileName);
            FileReader reader = new FileReader(output);
            BufferedReader br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                node = new NodeDetails(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
                System.out.println("Node:" + node);
                nodes.add(node);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Configuration file not found " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception while reading the configuration file" + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General Exception " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        System.out.println("Configuration of Nodes is completed. \n Number of nodes are "+nodes.size());
        System.out.println("configureNodes() exit");
        System.out.println("____________________________________________________");
        return nodes;
    }

    public static void main(String[] args) {
        System.out.println("____________________________________________________");
        System.out.println("Main() entry");
        String ipAddress = null;
        int port;

        if(args.length != 2){
            System.err.println("Not enough Parameters");
            System.err.println("Program <ipaddress> <port>");
            System.out.println("____________________________________________________");
            System.exit(1);
        }
        ipAddress = args[0];
        port = Integer.parseInt(args[1]);
        System.out.println("Inputs: " + ipAddress + ":" + port);

        NodeDetails nodeDetails = new NodeDetails(ipAddress, port);
        nodeDetails.setNodeWaitDuration(AOSUtils.randInt(20, 100));

        System.out.println("Current Node: "+nodeDetails);

        nodeList =  configureNodes("nodes.txt");

        Node node1 = new Node(nodeDetails, nodeList);
        node1.startServer();

        // Automatically shutdown in 1 minute
        try {
            System.out.println("Waiting...1");
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        node1.establishConnection();
        System.out.println("____________________________________________________");
/*
        // Automatically shutdown in 1 minute
        try {
            System.out.println("Waiting...2");
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        node1.stopServer();*/
    }
}
