package com.utd.tnm.nms.bolts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.utd.tnm.nms.model.NWTopologyData;
import com.utd.tnm.nms.model.SwitchData;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class NwMonitorBolt extends BaseRichBolt {

	private static final long serialVersionUID = 6843364678084556655L;

	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector outCollector) {
		collector = outCollector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	@Override
	public void execute(Tuple tuple) {
		NWTopologyData nwData = (NWTopologyData) tuple.getValue(0);
		List<Integer> packetLostList = nwData.getPacketLossList();
		List<SwitchData> switchList = nwData.getSwitchList();
		List<SwitchData> errorSwitchList = nwData.getErrorSwitchList();
		if(errorSwitchList==null)
			errorSwitchList = new ArrayList<>();
		for(int i=0, n=packetLostList.size();i<n;i++){
			System.out.println(packetLostList.get(i));
			if(packetLostList.get(i)>0){
				System.out.println("------------------->");
				System.out.println(switchList.get(i-1));
				System.out.println(switchList.get(i));
				errorSwitchList.add(switchList.get(i-1));
				errorSwitchList.add(switchList.get(i));
				nwData.setErrorSwitchList(errorSwitchList);
				collector.emit(new Values(nwData));
				System.out.println("------------------->");
			}
		}
		
		collector.ack(tuple);
	}
}
