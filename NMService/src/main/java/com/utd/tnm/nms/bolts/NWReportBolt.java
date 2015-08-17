package com.utd.tnm.nms.bolts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.utd.tnm.nms.model.NWTopologyData;
import com.utd.tnm.nms.util.CommonDataUtil;

public class NWReportBolt extends BaseRichBolt {

	private static final long serialVersionUID = 6102304822420418016L;

	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector outCollector) {
		collector = outCollector;
		CommonDataUtil.errorNWDataList = new ArrayList<NWTopologyData>();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// terminal bolt = does not emit anything
	}

	@Override
	public void execute(Tuple tuple) {
		NWTopologyData data = (NWTopologyData) tuple.getValue(0);
//		String p = getPriority(data.getPacketStaticData().getSrcIP());
//		System.out.println("----------------->"+p);
//		System.out.println("Before------------>"+data);
////		data.getPacketStaticData().setPriority(p);
//		System.out.println("After------------->"+data);
		CommonDataUtil.errorNWDataList.add(data);
		collector.ack(tuple);
	}
	
	private String getPriority(String ip) {
		String p = "high";
		int val = Integer.parseInt(ip.split("\\.")[2]);
		System.out.println(val);
		if(val>3)
			p = "low";
		else if(val>1)
			p="medium";
	
		return p;
	}
	
	@Override
	public void cleanup() {
		System.out.println("--- FINAL COUNTS ---");
		System.out.println("Erroneous Packets: "+CommonDataUtil.errorNWDataList.size());
		for(NWTopologyData data:CommonDataUtil.errorNWDataList){
			System.out.println(data);
		}
		System.out.println("--------------");
	}
}
