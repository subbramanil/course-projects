package com.learning.aos.filesharing;

import com.learning.aos.filesharing.model.FileInfo;
import com.learning.aos.filesharing.model.SearchRequest;

import java.util.Scanner;

/**
 * Created by Subbu on 11/8/15.
 */
public class SearchHelperThread implements Runnable {

    private P2PNode p2PNode;

    public SearchHelperThread() {
        p2PNode = P2PNode.getSingetonNodeInstance();
    }

    @Override
    public void run() {
        boolean isExiting = false;
        boolean isValid = true;
        int ch;
        int reqID = 0;
        do {
            debugMsg("Make a search request: ");
            debugMsg("Choose \n 1.FileName" +
                    "\n 2.Keyword" +
                    "\n 3.Exit");
            Scanner in = new Scanner(System.in);
            ch = in.nextInt();
            SearchRequest request = new SearchRequest();
            switch (ch) {
                case 1:
                    debugMsg("Enter the filename: ");
                    request.setFileName(in.next());
                    break;
                case 2:
                    debugMsg("Enter the keyword: ");
                    request.setKeyword(in.next());
                    break;
                case 3:
                    debugMsg("Exit from the system");
                    isExiting = true;
                    break;
                default:
                    debugMsg("InCorrect choice");
            }

            if (!isExiting) {
                request.setReqID(reqID++);
                if (!checkinSameNode(request)) {
                    debugSplMsg("Sending request: " + request);
                    new Thread(new SearchReqSender(request)).start();
                } else {
                    debugMsg("File exists in the same node.. No need to send request");
                }
            } else {
                p2PNode.exitGracefully();
            }
        } while (ch != 3);
    }

    private boolean checkinSameNode(SearchRequest request) {
        boolean isFileFound = false;
        if (request.getFileName() != null) {
            for (FileInfo file : p2PNode.currentNode.getFileInfoList()) {
                if (file.getFileName().equals(request.getFileName())) {
                    isFileFound = true;
                    break;
                }
            }
        }
        return isFileFound;
    }

    private void debugMsg(String msg) {
        System.out.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
    }

    private void debugError(String msg) {
        System.err.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
    }

    private void debugSplMsg(String msg) {
        System.out.println("________________________________________________________");
        System.out.println(SearchHelperThread.class.getSimpleName() + "-->" + msg);
        System.out.println("________________________________________________________");
    }
}
