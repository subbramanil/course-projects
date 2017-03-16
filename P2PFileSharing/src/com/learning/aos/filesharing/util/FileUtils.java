package com.learning.aos.filesharing.util;


import com.learning.aos.filesharing.model.FileInfo;
import com.learning.aos.filesharing.model.NodeDetails;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subbu on 11/8/15.
 */
public class FileUtils {

    public static NodeDetails readNodeInfo(String fileName) {
        JSONParser parser = new JSONParser();
        NodeDetails node = null;
        try {
            Object obj = parser.parse(new FileReader(fileName));

            JSONObject jsonObject = (JSONObject) obj;
            node = new NodeDetails();

            node.setNodeID(Integer.parseInt((String) jsonObject.get("nodeID")));
            node.setNodeAddress((String) jsonObject.get("ipAddress"));
            node.setNodePort(Integer.parseInt((String) jsonObject.get("port")));
            List<FileInfo> fileInfos = new ArrayList<>();
            for (JSONObject jsonObject1 : (Iterable<JSONObject>) jsonObject.get("files")) {
                fileInfos.add(new FileInfo(jsonObject1));
            }
            node.setFileInfoList(fileInfos);
            debugMsg("Current Node: " + node);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return node;
    }

    public static void writeNodeInfo(String fileName, NodeDetails currentNode) {
        JSONParser parser = new JSONParser();
        NodeDetails node = null;
        try {

            JSONObject obj = new JSONObject();
            obj.put("nodeID", "" + currentNode.getNodeID());
            obj.put("iPAddress", currentNode.getNodeAddress());
            obj.put("port", "" + currentNode.getNodePort());

            JSONArray fileArray = new JSONArray();

            for (FileInfo file : currentNode.getFileInfoList()) {
                JSONObject fileObj = new JSONObject();
                fileObj.put("fileName", file.getFileName());
                fileObj.put("keywords", file.getKeywords());
                fileArray.add(fileObj);
            }

            obj.put("files", fileArray);

            try (FileWriter file = new FileWriter(fileName)) {
                file.write(obj.toJSONString());
                debugMsg("Successfully Copied JSON Object to File...");
                debugMsg("JSON Array: " + obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<NodeDetails> readP2PSysInfo() {
        JSONParser parser = new JSONParser();
        List<NodeDetails> nodes = null;
        NodeDetails node = null;
        try {
            Object obj = parser.parse(new FileReader("p2pSysInfo.json"));
            nodes = new ArrayList<>();
            if (obj != null) {
                JSONArray jsonArray = (JSONArray) obj;
                for (Object aJsonArray : jsonArray) {
                    JSONObject jsonObject = (JSONObject) aJsonArray;
                    node = new NodeDetails();
                    node.setNodeID(((Long) jsonObject.get("ID")).intValue());
                    node.setNodeAddress((String) jsonObject.get("IPAddress"));
                    node.setNodePort(((Long) jsonObject.get("Port")).intValue());
                    debugMsg("Node: " + node);
                    nodes.add(node);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nodes;
    }

    public static void writeP2PSysInfo(List<NodeDetails> nodesList) {
        try {
            JSONArray objArray = new JSONArray();

            for (NodeDetails node : nodesList) {
                JSONObject obj = new JSONObject();
                obj.put("ID", node.getNodeID());
                obj.put("IPAddress", node.getNodeAddress());
                obj.put("Port", node.getNodePort());
                objArray.add(obj);
            }

            try (FileWriter file = new FileWriter("p2pSysInfo.json")) {
                file.write(objArray.toJSONString());
                debugMsg("Successfully Copied JSON Object to File...");
                debugMsg("JSON Array: " + objArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void debugMsg(String msg) {
        System.out.println(FileUtils.class.getSimpleName() + "-->" + msg);
    }

    private static void debugError(String msg) {
        System.err.println(FileUtils.class.getSimpleName() + "-->" + msg);
    }
}
