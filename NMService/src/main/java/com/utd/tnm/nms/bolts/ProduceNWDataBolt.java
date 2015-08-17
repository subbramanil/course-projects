package com.utd.tnm.nms.bolts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.utd.tnm.nms.model.NWTopologyData;
import com.utd.tnm.nms.model.PacketStaticData;
import com.utd.tnm.nms.model.SwitchData;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class ProduceNWDataBolt extends BaseRichBolt implements Serializable{

	private static final long serialVersionUID = 3092938699134129356L;
	
	private static NWTopologyData nwData = new NWTopologyData();
	private static List<Integer> packetLossData = new ArrayList<Integer>();
	private static List<SwitchData> switchList = new ArrayList<SwitchData>();
	private static Integer lastPacketSize = null;

	private OutputCollector collector;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map cfg, TopologyContext topologyCtx,
			OutputCollector outCollector) {
		collector = outCollector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

	@Override
	public void execute(Tuple tuple) {
		
		System.out.println("Tuple: "+tuple);
		Object value = tuple.getValue(0);
		System.out.println("Value: "+value);
		String data = null;
		if (value instanceof String) {
			data = (String) value;
		}
		String[] inputs = data.split(",");
		
		PacketStaticData packetData = new PacketStaticData();
		
		packetData.setTimeStamp(inputs[0]);
		packetData.setSrcIP(inputs[1]);
		packetData.setPriority(getPriority(inputs[1]));
		packetData.setSrcPort(inputs[2]);
		packetData.setDestIP(inputs[3]);
		packetData.setDestPort(inputs[4]);
		packetData.setDirection(Integer.parseInt(inputs[5]));
		
		System.out.println("Network data"+nwData.getPacketStaticData());
		if(nwData.getPacketStaticData()!=null){
			System.out.println("Packet Data:"+packetData);
			if(!nwData.getPacketStaticData().equals(packetData)){ // New Source and Destination
				// collect last packet info
				nwData.setSwitchList(switchList);
				nwData.setPacketLossList(packetLossData);
				System.out.println("******************************************");
				System.out.println(nwData);
				System.out.println("******************************************");
				collector.emit(tuple, new Values(nwData));	// Emit
				//Create new resources
				nwData = new NWTopologyData();
				switchList = new ArrayList<SwitchData>();
				packetLossData = new ArrayList<Integer>();
				lastPacketSize = null;
			}
		}
		
		nwData.setPacketStaticData(packetData);
		
		int currentPacketSize = Integer.parseInt(inputs[6]);
		if(lastPacketSize==null){
			packetLossData.add(0);
			lastPacketSize = currentPacketSize;
		}else{
			int packetLoss = lastPacketSize-currentPacketSize;
			System.out.println("Packet loss: "+packetLoss);			
			packetLossData.add(packetLoss);
			lastPacketSize = currentPacketSize;
		}
		
		switchList.add(new SwitchData(inputs[8], inputs[9]));
		
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

}
