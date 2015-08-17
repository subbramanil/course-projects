package com.utd.tnm.nms.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.utd.tnm.nms.model.NWTopologyData;

public class CommonDataUtil {
	public static NWTopologyData nwData = null;
	public static String contextPath = null;
	public static ServletContext context;
	public static List<NWTopologyData> totalNWDataList;
	public static List<NWTopologyData> errorNWDataList;
	
	/**
	 * Method to read the CSV file and produce list of Strings that contain the
	 * network data
	 */
	public static List<String> readCSVFile(String fileName) {
		List<String> strDataList = new ArrayList<String>();
		System.out.println(CommonDataUtil.context.getContextPath());
		BufferedReader br = null;
		String line = "";
		try {
			InputStream io = context.getResourceAsStream("/WEB-INF/"+fileName);
			br = new BufferedReader(new InputStreamReader(io));
			br.readLine();
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				strDataList.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("No of records processed: " + strDataList.size());
		System.out.println("Done");
		return strDataList;
	}
}
