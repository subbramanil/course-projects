package com.utd.tnm.nms.spouts;

import java.util.List;
import java.util.Map;

import com.utd.tnm.nms.util.CommonDataUtil;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * Spout that reads the input file and provides the data line by line
 * 
 * @author Subbu
 *
 */
public class ProcesInputSpout extends BaseRichSpout {

	private static final long serialVersionUID = -462261441867084611L;

	private SpoutOutputCollector collector;

	private int idx = 0;

	private List<String> nwDataList;

	public ProcesInputSpout() {
		nwDataList = CommonDataUtil.readCSVFile("SDN_data_output.csv");
		System.out.println("Total NW data: "+nwDataList.size());
	}

	@Override
	public void open(@SuppressWarnings("rawtypes") Map cfg, TopologyContext topology,
			SpoutOutputCollector outCollector) {
		collector = outCollector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}

	@Override
	public void nextTuple() {
		if (idx < nwDataList.size()) {
			collector.emit(new Values(nwDataList.get(idx)));
		}
		idx++;
		Utils.sleep(1);
	}

/*	*//**
	 * Method to execute the python script
	 *//*
	private static void executePyScript() {
		System.out.println("executePyScript entry");
		String execCmd = "python " + NMUtil.getFilePath("sdn_file_reader.py") + " "
				+ NMUtil.getFilePath("SDN_input_IP.csv") + " " + NMUtil.getFilePath("SDN_topology_input.csv") + " 3";
		System.out.println("Executing command: " + execCmd);
		try {
			Runtime.getRuntime().exec(execCmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("executePyScript exit");
	}*/

}
