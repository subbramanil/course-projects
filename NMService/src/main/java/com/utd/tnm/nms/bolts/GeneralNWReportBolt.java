package com.utd.tnm.nms.bolts;

import java.util.ArrayList;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.utd.tnm.nms.model.NWTopologyData;
import com.utd.tnm.nms.util.CommonDataUtil;

public class GeneralNWReportBolt extends BaseRichBolt {

	private static final long serialVersionUID = 6102304822420418016L;

	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector outCollector) {
		collector = outCollector;
		CommonDataUtil.totalNWDataList = new ArrayList<NWTopologyData>();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// terminal bolt = does not emit anything
	}

	@Override
	public void execute(Tuple tuple) {
		NWTopologyData data = (NWTopologyData) tuple.getValue(0);
		CommonDataUtil.totalNWDataList.add(data);
		collector.ack(tuple);
	}
	
	@Override
	public void cleanup() {
		System.out.println("--- FINAL COUNTS ---");
		System.out.println("Total Packets: "+CommonDataUtil.totalNWDataList.size());
		/*for(NWTopologyData data:totalNWDataList){
			System.out.println(data);
		}*/
		System.out.println("--------------");
	}
}
