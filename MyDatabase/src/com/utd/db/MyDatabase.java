package com.utd.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class MyDatabase {
	static Logger LOG = Logger.getLogger(MyDatabase.class);

	final static byte double_blind_mask      = 8;    // binary 0000 1000
	final static byte controlled_study_mask  = 4;    // binary 0000 0100
	final static byte govt_funded_mask       = 2;    // binary 0000 0010
	final static byte fda_approved_mask      = 1;    // binary 0000 0001
	
	static Map<Integer, Long> idMap = new HashMap<Integer, Long>();
	
	static ArrayList<Long> recList = null;
	static Map<String, ArrayList<Long>> nameMap = new HashMap<String, ArrayList<Long>>();
	static Map<String, ArrayList<Long>> drugMap = new HashMap<String, ArrayList<Long>>();
	static Map<Short, ArrayList<Long>> trialsMap = new HashMap<Short, ArrayList<Long>>();
	static Map<Short, ArrayList<Long>> patientsMap = new HashMap<Short, ArrayList<Long>>();
	static Map<Short, ArrayList<Long>> doseMap = new HashMap<Short, ArrayList<Long>>();
	static Map<Float, ArrayList<Long>> readingMap = new HashMap<Float, ArrayList<Long>>();
	static Map<Boolean, ArrayList<Long>> dblBlindMap = new HashMap<Boolean, ArrayList<Long>>();
	static Map<Boolean, ArrayList<Long>> studyMap = new HashMap<Boolean, ArrayList<Long>>();
	static Map<Boolean, ArrayList<Long>> govtFundMap = new HashMap<Boolean, ArrayList<Long>>();
	static Map<Boolean, ArrayList<Long>> fdaMap = new HashMap<Boolean, ArrayList<Long>>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Boolean isIndexReady = false;
	
	public static void main(String[] args) {
		LOG.debug("main() entry");
		List<Record> results = null;
		int ch;
		int compID;
		String compName;
		String drugID;
		short inShortVal;
		float reading;
		boolean inFlag;
		try(
				RandomAccessFile inputFile = new RandomAccessFile("PHARMA_TRIALS_1000B.csv", "r");
			) {
			do{
				System.out.println("Enter your choice:\n 1.Create Data.db\t 2. Query Database");
				ch = Integer.parseInt(br.readLine());
				if(ch==1){
					parseInputFile(inputFile);
					createIndexFile("id.idx");
					createIndexFile("name.idx", nameMap);
					createIndexFile("drug.idx", drugMap);
					createIndexFile("trials.idx", trialsMap);
					createIndexFile("patients.idx", patientsMap);
					createIndexFile("dose.idx", doseMap);
					createIndexFile("reading.idx", readingMap);
					createIndexFile("dblBlind.idx", dblBlindMap);
					createIndexFile("conStudy.idx", studyMap);
					createIndexFile("govt.idx", govtFundMap);
					createIndexFile("fda.idx", fdaMap);
					isIndexReady = true;
				}else{
					if(!isIndexReady)
						System.out.println("Data.db is not created yet!!");
				}
			}while(!isIndexReady);
			System.out.println("Data.db file is created.. Ready to query database!!");
			do{
			System.out.println("Enter your choice:\n"
					+ "1. ID\t2. Name\t3. Drug ID\t4. trials\t5. patients\t6. dosage_mg\t7. reading\t8. double_blind\t9. controlled_study\t10. govt_funded\t11. fda_approved\n Enter 0 to exit");
				ch = Integer.parseInt(br.readLine());
				switch(ch){
					case 1:
						System.out.println("Enter company ID:");
						compID = Integer.parseInt(br.readLine());
						recList = findRecordsUsingIndexFile("id.idx", compID);
						if(recList!=null){
							LOG.debug("No. of records with company Name: "+compID+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the company Name"+compID);
						}
						break;
					case 2:
						System.out.println("Enter company Name:");
						compName = br.readLine();
						recList = findRecordsUsingIndexFile("name.idx", compName);
						if(recList!=null){
							LOG.debug("No. of records with company Name: "+compName+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the company Name"+compName);
						}
						break;
					case 3:
						System.out.println("Enter Drug ID:");
						drugID = br.readLine();
						recList = findRecordsUsingIndexFile("drug.idx", drugID);
						if(recList!=null){
							LOG.debug("No. of records with Drug ID: "+drugID+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the Drug ID "+drugID);
						}
						break;
					case 4:
						System.out.println("Enter trials:");
						inShortVal = Short.parseShort(br.readLine());
						recList = trialsMap.get(inShortVal);
						recList = findRecordsUsingIndexFile("trials.idx", inShortVal);
						if(recList!=null){
							LOG.debug("No. of records with Trials: "+inShortVal+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the trials "+inShortVal);
						}
						break;
					case 5:
						System.out.println("Enter patients:");
						inShortVal = Short.parseShort(br.readLine());
						recList = findRecordsUsingIndexFile("patients.idx", inShortVal);
						if(recList!=null){
							LOG.debug("No. of records with patients: "+inShortVal+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the patients "+inShortVal);
						}
						break;
					case 6:
						System.out.println("Enter dosage:");
						inShortVal = Short.parseShort(br.readLine());
						recList = findRecordsUsingIndexFile("dose.idx", inShortVal);
						if(recList!=null){
							LOG.debug("No. of records with dosage: "+inShortVal+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the dosage "+inShortVal);
						}
						break;
					case 7:
						System.out.println("Enter reading:");
						reading = Float.parseFloat(br.readLine());
						recList = findRecordsUsingIndexFile("reading.idx", reading);
						if(recList!=null){
							LOG.debug("No. of records with reading: "+reading+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the reading "+reading);
						}
						break;
					case 8:
						System.out.println("Enter double_blind:");
						inFlag = Boolean.parseBoolean(br.readLine());
						recList = findRecordsUsingIndexFile("dblBlind.idx", inFlag);
						if(recList!=null){
							LOG.debug("No. of records with double Blind: "+inFlag+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the double Blind "+inFlag);
						}
						break;
					case 9:
						System.out.println("Enter controlled study:");
						inFlag = Boolean.parseBoolean(br.readLine());
						recList = findRecordsUsingIndexFile("conStudy.idx", inFlag);
						if(recList!=null){
							LOG.debug("No. of records with patients: "+inFlag+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the patients"+inFlag);
						}
						break;
					case 10:
						System.out.println("Enter govt_funded:");
						inFlag = Boolean.parseBoolean(br.readLine());
						recList = findRecordsUsingIndexFile("govt.idx", inFlag);
						if(recList!=null){
							LOG.debug("No. of records with govt_funded: "+inFlag+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the govt_funded"+inFlag);
						}
						break;
					case 11:
						System.out.println("Enter fda_approved:");
						inFlag = Boolean.parseBoolean(br.readLine());
						recList = findRecordsUsingIndexFile("fda.idx", inFlag);
						if(recList!=null){
							LOG.debug("No. of records with fda_approved: "+inFlag+" are "+ recList.size());
							results = new ArrayList<Record>();
							for(Long loc:recList){
								LOG.debug("Record  Position: "+loc);
								if(loc!=null)
									results.add(findRecord(loc));
							}
							printResults(results);
						}else{
							LOG.warn("No records found with the fda_approved"+inFlag);
						}
						break;
					default:
						System.out.println("Enter valid choice!!");
				}
			}while(ch!=0);

		} catch (FileNotFoundException e) {
			LOG.error("File not found");
			e.printStackTrace();
		} catch (IOException e1) {
			LOG.error("IO Exception");
			e1.printStackTrace();
		}
		LOG.debug("main() exit");
	}
	
	private static void printResults(List<Record> records){
		LOG.debug("printResults() entry");
		LOG.debug("Results:");
		for(Record rec:records){
			System.out.println(rec);
		}
		LOG.debug("printResults() exit");
	}
	
	private static Record createRecord(String line){
		Record rec = new Record();
		String[] colValues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		rec.setCompID(Integer.parseInt(colValues[0]));
		if(colValues[1].startsWith("\"")){
			rec.setCompName(colValues[1].substring(1, colValues[1].length()-1));
		}else{
			rec.setCompName(colValues[1]);
		}
		
		rec.setDrugID(colValues[2]);
		rec.setTrials(Short.parseShort(colValues[3]));
		rec.setPatients(Short.parseShort(colValues[4]));
		rec.setDosage_mg(Short.parseShort(colValues[5]));
		rec.setReading(Float.parseFloat(colValues[6]));
		
		byte commonByte = 0x00;
		if(Boolean.parseBoolean(colValues[7])){
			rec.setDoubleBlind(true);
			commonByte = (byte)(commonByte | double_blind_mask);
		}
		if(Boolean.parseBoolean(colValues[8])){
			rec.setControlledStudy(true);
			commonByte = (byte)(commonByte | controlled_study_mask);
		}
		if(Boolean.parseBoolean(colValues[9])){
			rec.setGovtFunded(true);
			commonByte = (byte)(commonByte | govt_funded_mask);
		}
		if(Boolean.parseBoolean(colValues[10])){
			rec.setFdaApproved(true);
			commonByte = (byte)(commonByte | fda_approved_mask);
		}
		rec.setFlags(commonByte);
		
		return rec;
	}

	private static void parseInputFile(RandomAccessFile inputFile) throws IOException {
		LOG.debug("parseInputFile() entry");
		Record rec = null;
		long recLoc;
		String line = inputFile.readLine();
		LOG.debug("First line skipped: "+line);
		line = inputFile.readLine();
		try(RandomAccessFile dataFile = new RandomAccessFile("data.db", "rw")) {
			do{
				rec = createRecord(line);
				recLoc = dataFile.getFilePointer();
				LOG.debug("Comp ID. "+ rec.getCompID() + " File Pointer Position:"+dataFile.getFilePointer());
				createKeyMaps(rec, recLoc);
				// Begin Writing to file
				dataFile.writeInt(rec.getCompID());
				LOG.debug("Length of Company: "+rec.getCompName()+" is "+ rec.getCompName().length());
				dataFile.write(rec.getCompName().length());
				dataFile.writeBytes(rec.getCompName());
				dataFile.writeBytes(rec.getDrugID());
				dataFile.writeShort(rec.getTrials());
				dataFile.writeShort(rec.getPatients());
				dataFile.writeShort(rec.getDosage_mg());
				dataFile.writeFloat(rec.getReading());
				dataFile.writeByte(rec.getFlags());
				// End writing to file
				
				// read next line
				line = inputFile.readLine();
			}while(line!=null);
		} catch (FileNotFoundException e) {
			LOG.error("File not found");
			e.printStackTrace();
		} catch (IOException e1) {
			LOG.error("IO Exception");
			e1.printStackTrace();
		}
		
		LOG.debug("parseInputFile() exit");
	}
	
	public static void createKeyMaps(Record rec, long recLoc){
		LOG.debug("createKeyMaps() entry");
		// ID Map
		idMap.put(rec.getCompID(), recLoc);
		
		// Name Map
		if(nameMap.containsKey(rec.getCompName())){
			recList = nameMap.get(rec.getCompName());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		nameMap.put(rec.getCompName(), recList);
		
		// Drug Map
		if(drugMap.containsKey(rec.getDrugID())){
			recList = drugMap.get(rec.getDrugID());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		drugMap.put(rec.getDrugID(), recList);
		
		// Trials Map
		if(trialsMap.containsKey(rec.getTrials())){
			recList = trialsMap.get(rec.getTrials());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		trialsMap.put(rec.getTrials(), recList);
		
		// Patients Map
		if(patientsMap.containsKey(rec.getPatients())){
			recList = patientsMap.get(rec.getPatients());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		patientsMap.put(rec.getPatients(), recList);
		
		// Dose map
		if(doseMap.containsKey(rec.getDosage_mg())){
			recList = doseMap.get(rec.getDosage_mg());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		doseMap.put(rec.getDosage_mg(), recList);
		
		// Reading map
		if(readingMap.containsKey(rec.getReading())){
			recList = readingMap.get(rec.getReading());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		readingMap.put(rec.getReading(), recList);
		
		// Double Blind map
		if(dblBlindMap.containsKey(rec.isDoubleBlind())){
			recList = dblBlindMap.get(rec.isDoubleBlind());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		dblBlindMap.put(rec.isDoubleBlind(), recList);

		// Controlled Study map
		if(studyMap.containsKey(rec.isControlledStudy())){
			recList = studyMap.get(rec.isControlledStudy());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		studyMap.put(rec.isControlledStudy(), recList);
		
		// Govt. Funded map
		if(govtFundMap.containsKey(rec.isGovtFunded())){
			recList = dblBlindMap.get(rec.isGovtFunded());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		govtFundMap.put(rec.isGovtFunded(), recList);
		
		//FDA. Approved Map
		if(fdaMap.containsKey(rec.isFdaApproved())){
			recList = dblBlindMap.get(rec.isFdaApproved());
		}else{
			recList = new ArrayList<Long>();
		}
		recList.add(recLoc);
		fdaMap.put(rec.isFdaApproved(), recList);
		LOG.debug("createKeyMaps() exit");
	}
	
	public static void createIndexFile(String fileName){
		LOG.debug("No of Entries: "+idMap.values().size());
		File file = null;
		PrintWriter writer = null;
		try{
			file = new File(fileName);
			writer = new PrintWriter(file);
			for(Map.Entry<Integer, Long> entry:idMap.entrySet()){
				String line = entry.getKey()+":"+entry.getValue()+"\n";
				writer.write(line);
				writer.flush();
			}
		}catch (Exception e) {
			LOG.error("Exception in creating "+fileName+" index file");
			e.printStackTrace();
		}finally{
			writer.close();
		}
		LOG.debug("createIndexFile() exit");
	}
	
	private static <T> void createIndexFile(String fileName, Map<T, ArrayList<Long>> keyMap){
		LOG.debug("createIndexFile("+fileName+") entry");
		File file = null;
		PrintWriter writer = null;
		try{
			file = new File(fileName);
			writer = new PrintWriter(file);
			for(Map.Entry<T, ArrayList<Long>> entry:keyMap.entrySet()){
				if(entry.getKey() instanceof Integer){
					writer.write((Integer) entry.getKey());
				}else if(entry.getKey() instanceof String){
					writer.write((String) entry.getKey());
				}else{
					writer.write(entry.getKey().toString());
				}
				writer.write(":");
				for(long loc:entry.getValue()){
					writer.write(String.valueOf(loc)+",");
				}
				writer.write('\n');
				writer.flush();
			}
		}catch (Exception e) {
			LOG.error("Exception in creating "+fileName+" index file");
			e.printStackTrace();
		}finally{
			writer.close();
		}
		LOG.debug("createIndexFile() exit");
	}
	
	private static <T> ArrayList<Long> findRecordsUsingIndexFile(String fileName, T param){
		LOG.debug("findRecordsUsingIndexFile() entry");
		String line = null;
		String[] tokens = null;
		ArrayList<Long> locList = null;
		int opr;
		try(RandomAccessFile dataFile = new RandomAccessFile(fileName, "r");){
			if(param instanceof Integer){
				System.out.println("Choose the comparison operator:\n 1.=\t2.!=\t3.<\t4.>\t5.<=\t6.>=");
				opr = Integer.parseInt(br.readLine());
				switch(opr){
					case 1:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) == (Integer)param){
								locList.add(Long.parseLong(tokens[1]));
								break;
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 2:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) != (Integer)param)
								locList.add(Long.parseLong(tokens[1]));
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 3:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) < (Integer)param){
								locList.add(Long.parseLong(tokens[1]));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 4:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) > (Integer)param){
								locList.add(Long.parseLong(tokens[1]));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 5:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) <= (Integer)param){
								locList.add(Long.parseLong(tokens[1]));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 6:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Integer.parseInt(tokens[0]) >= (Integer)param){
								locList.add(Long.parseLong(tokens[1]));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
				}
			}else if(param instanceof String){
				line = dataFile.readLine();
				do{
					locList = new ArrayList<Long>();
					tokens = line.split(":");
					if(tokens[0].equals(param)){
						for(String s:tokens[1].split(",")){
							locList.add(Long.parseLong(s));
						}
						LOG.debug("Record found & number of matching records are "+locList.size());
						break;
					}
					line = dataFile.readLine();
				}while(line!=null);
			}else if(param instanceof Short){
				System.out.println("Choose the comparison operator:\n 1.=\t2.!=\t3.<\t4.>\t5.<=\t6.>=");
				opr = Integer.parseInt(br.readLine());
				switch(opr){
					case 1:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) == (Short)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
								break;
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 2:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) != (Short)param)
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 3:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) < (Short)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 4:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) > (Short)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 5:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) <= (Short)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 6:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Short.parseShort(tokens[0]) >= (Short)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
				}
			}else if(param instanceof Float){
				System.out.println("Choose the comparison operator:\n 1.=\t2.!=\t3.<\t4.>\t5.<=\t6.>=");
				opr = Integer.parseInt(br.readLine());
				switch(opr){
					case 1:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) == (Float)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
								break;
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 2:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) != (Float)param)
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 3:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) < (Float)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 4:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) > (Float)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 5:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) <= (Float)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
					case 6:
						locList = new ArrayList<Long>();
						line = dataFile.readLine();
						do{
							tokens = line.split(":");
							if(Float.parseFloat(tokens[0]) >= (Float)param){
								for(String str:tokens[1].split(","))
									locList.add(Long.parseLong(str));
							}
							line = dataFile.readLine();
						}while(line!=null);
						break;
				}
			}else if(param instanceof Boolean){
				line = dataFile.readLine();
				do{
					locList = new ArrayList<Long>();
					tokens = line.split(":");
					if(Boolean.parseBoolean(tokens[0]) == (Boolean)param){
						for(String s:tokens[1].split(",")){
							locList.add(Long.parseLong(s));
						}
						LOG.debug("Record found & number of matching records are "+locList.size());
						break;
					}
					line = dataFile.readLine();
				}while(line!=null);
			}
		}catch (Exception e) {
			LOG.error(e.getMessage()+" Exception in findRecordsUsingIndexFile() method");
			e.printStackTrace();
		}
		LOG.debug("findRecordsUsingIndexFile() exit");
		return locList;
	}
	
	private static Record findRecord(Long recPtr) {
		LOG.debug("parseInputFile() entry");
		StringBuffer buf = null;
		Record record = new Record();
		try(RandomAccessFile dataFile = new RandomAccessFile("data.db", "r")){
			dataFile.seek(recPtr);
			int compID = dataFile.readInt();
			LOG.debug(""+compID);
			record.setCompID(compID);
			int len = dataFile.read();
			buf = new StringBuffer();
			while(len>0){
				buf.append((char)dataFile.read());
				len--;
			}
			record.setCompName(buf.toString());
			
			int i=0;
			buf = new StringBuffer();
			while(i<6){
				buf.append((char)dataFile.read());
				i++;
			}
			record.setDrugID(buf.toString());
			
			short trials = dataFile.readShort();
			record.setTrials(trials);
			
			short patients = dataFile.readShort();
			record.setPatients(patients);
			
			short dosage = dataFile.readShort();
			record.setDosage_mg(dosage);
			
			float reading = dataFile.readFloat();
			record.setReading(reading);
			
			byte flags = dataFile.readByte();
			LOG.debug("Flags:"+BitwiseTutorial.byte2bits(flags));
			
			record.setDoubleBlind(((double_blind_mask&flags)==8)?true:false);
			record.setControlledStudy(((controlled_study_mask&flags)==4)?true:false);
			record.setGovtFunded(((govt_funded_mask&flags)==2)?true:false);
			record.setFdaApproved(((fda_approved_mask&flags)==1)?true:false);
			
			LOG.debug("Record :"+record);
			
		}catch (Exception e) {
			LOG.error("Exception in findRecord() method");
			e.printStackTrace();
		}
		LOG.debug("findRecord() exit");
		return record;
	}
	
	private static class Record {
		int compID;
		String compName;
		String drugID;
		short trials;
		short patients;
		short dosage_mg;
		float reading;
		byte flags;
		boolean doubleBlind;
		boolean controlledStudy;
		boolean govtFunded;
		boolean fdaApproved;
		
		public int getCompID() {
			return compID;
		}
		public void setCompID(int compID) {
			this.compID = compID;
		}
		public String getCompName() {
			return compName;
		}
		public void setCompName(String compName) {
			this.compName = compName;
		}
		public String getDrugID() {
			return drugID;
		}
		public void setDrugID(String drugID) {
			this.drugID = drugID;
		}
		public short getTrials() {
			return trials;
		}
		public void setTrials(short trials) {
			this.trials = trials;
		}
		public short getPatients() {
			return patients;
		}
		public void setPatients(short patients) {
			this.patients = patients;
		}
		public short getDosage_mg() {
			return dosage_mg;
		}
		public void setDosage_mg(short dosage_mg) {
			this.dosage_mg = dosage_mg;
		}
		public float getReading() {
			return reading;
		}
		public void setReading(float reading) {
			this.reading = reading;
		}
		public boolean isDoubleBlind() {
			return doubleBlind;
		}
		public void setDoubleBlind(boolean doubleBlind) {
			this.doubleBlind = doubleBlind;
		}
		public boolean isControlledStudy() {
			return controlledStudy;
		}
		public void setControlledStudy(boolean controlledStudy) {
			this.controlledStudy = controlledStudy;
		}
		public boolean isGovtFunded() {
			return govtFunded;
		}
		public void setGovtFunded(boolean govtFunded) {
			this.govtFunded = govtFunded;
		}
		public boolean isFdaApproved() {
			return fdaApproved;
		}
		public void setFdaApproved(boolean fdaApproved) {
			this.fdaApproved = fdaApproved;
		}
		@Override
		public String toString() {
			return "Record [compID=" + compID + ", compName=" + compName
					+ ", drugID=" + drugID + ", trials=" + trials + ", patients="
					+ patients + ", dosage_mg=" + dosage_mg + ", reading="
					+ reading + ", doubleBlind=" + doubleBlind
					+ ", controlledStudy=" + controlledStudy + ", govtFunded="
					+ govtFunded + ", fdaApproved=" + fdaApproved + "]";
		}
		public byte getFlags() {
			return flags;
		}
		public void setFlags(byte flags) {
			this.flags = flags;
		}
	}
}
