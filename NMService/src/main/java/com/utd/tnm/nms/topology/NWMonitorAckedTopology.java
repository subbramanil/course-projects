package com.utd.tnm.nms.topology;

import com.firebase.client.Firebase;
import com.utd.tnm.nms.bolts.GeneralNWReportBolt;
import com.utd.tnm.nms.bolts.NWReportBolt;
import com.utd.tnm.nms.bolts.NwMonitorBolt;
import com.utd.tnm.nms.bolts.ProduceNWDataBolt;
import com.utd.tnm.nms.model.NWTopologyData;
import com.utd.tnm.nms.spouts.ProcesInputSpout;
import com.utd.tnm.nms.util.CommonDataUtil;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

public class NWMonitorAckedTopology {

	private static final String PROCESS_SPOUT_ID = "process-input-spout";
	private static final String PRODUCE_BOLT_ID = "produce-nwdata-bolt";
	private static final String MONITOR_BOLT_ID = "monitor-bolt";
	private static final String REPORT_BOLT_ID = "report-bolt";
	private static final String GEN_REPORT_BOLT_ID = "gen-report-bolt";
	private static final String TOPOLOGY_NAME = "nw-monitor-topology";

	public static void main(String[] args) throws Exception {
//		startNMService();
	}
	
	public static void startNMService(){
		ProcesInputSpout spout = new ProcesInputSpout();
		
		ProduceNWDataBolt produceBolt = new ProduceNWDataBolt();
		
		NwMonitorBolt nwMonitorBolt = new NwMonitorBolt();
		NWReportBolt nwReportBolt = new NWReportBolt();
		GeneralNWReportBolt genReport = new GeneralNWReportBolt();

		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout(PROCESS_SPOUT_ID, spout);
		builder.setBolt(PRODUCE_BOLT_ID, produceBolt).shuffleGrouping(
				PROCESS_SPOUT_ID);
		builder.setBolt(MONITOR_BOLT_ID, nwMonitorBolt).fieldsGrouping(PRODUCE_BOLT_ID,
				new Fields("word"));
		builder.setBolt(REPORT_BOLT_ID, nwReportBolt).globalGrouping(
				MONITOR_BOLT_ID);
		builder.setBolt(GEN_REPORT_BOLT_ID, genReport).fieldsGrouping(PRODUCE_BOLT_ID,
				new Fields("word"));
		Config cfg = new Config();
		
		cfg.setDebug(false);

		/*
		 * boolean localMode =
		 * Boolean.parseBoolean(System.getProperty("localmode", "false"));
		 * 
		 * if (true) {
		 */
		LocalCluster cluster = new LocalCluster();
		
		cluster.submitTopology(TOPOLOGY_NAME, cfg, builder.createTopology());
		Utils.sleep(10 * 1000);
		cluster.killTopology(TOPOLOGY_NAME);
		
		
		System.out.println("#######################################Firebase");
		Firebase myFirebaseRef = new Firebase("https://sweltering-heat-1721.firebaseio.com/");
		for(NWTopologyData data:CommonDataUtil.totalNWDataList){
			System.err.println(data);
		}
		myFirebaseRef.child("TotalNWDataList").setValue(CommonDataUtil.totalNWDataList);
		myFirebaseRef.child("ErrorNWDataList").setValue(CommonDataUtil.errorNWDataList);		
		cluster.shutdown();
		/*
		 * } else { StormSubmitter.submitTopology(TOPOLOGY_NAME, cfg,
		 * builder.createTopology()); }
		 */
	}
}
