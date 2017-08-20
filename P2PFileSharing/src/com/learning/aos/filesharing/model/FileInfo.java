package com.learning.aos.filesharing.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Subbu on 11/8/15.
 */
public class FileInfo implements Serializable{

    private String fileName;

    private List<String> keywords;

    public FileInfo() {
    }

    public FileInfo(JSONObject jsonObject) {
        this.fileName = (String) jsonObject.get("fileName");
        this.keywords = new ArrayList<>();
        Iterator<String> iterator = ((JSONArray) jsonObject.get("keywords")).iterator();
        while (iterator.hasNext()) {
            this.keywords.add(iterator.next());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}
