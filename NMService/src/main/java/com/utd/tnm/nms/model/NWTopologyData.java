package com.utd.tnm.nms.model;

import java.io.Serializable;
import java.util.List;

public class NWTopologyData implements Serializable {

	private static final long serialVersionUID = -7892818335846616934L;
	
	private PacketStaticData packetStaticData;
	private List<Integer> packetLossList;
	private List<SwitchData> switchList;
	private List<SwitchData> errorSwitchList;
	
	public List<SwitchData> getSwitchList() {
		return switchList;
	}

	public void setSwitchList(List<SwitchData> switchList) {
		this.switchList = switchList;
	}

	public List<Integer> getPacketLossList() {
		return packetLossList;
	}

	public void setPacketLossList(List<Integer> packetLossList) {
		this.packetLossList = packetLossList;
	}

	public PacketStaticData getPacketStaticData() {
		return packetStaticData;
	}

	public void setPacketStaticData(PacketStaticData packetStaticData) {
		this.packetStaticData = packetStaticData;
	}

	public List<SwitchData> getErrorSwitchList() {
		return errorSwitchList;
	}

	public void setErrorSwitchList(List<SwitchData> errorSwitchList) {
		this.errorSwitchList = errorSwitchList;
	}

	@Override
	public String toString() {
		return "NWTopologyData [packetStaticData=" + packetStaticData + ", packetLossList=" + packetLossList
				+ ", switchList=" + switchList + ", errorSwitchList=" + errorSwitchList + "]";
	}
	
	
	
	
}
