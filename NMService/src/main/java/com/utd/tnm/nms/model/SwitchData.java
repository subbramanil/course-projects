package com.utd.tnm.nms.model;

public class SwitchData {
	private String switchName;
	private String switchPort;

	public String getSwitchName() {
		return switchName;
	}

	public void setSwitchName(String switchName) {
		this.switchName = switchName;
	}

	public String getSwitchPort() {
		return switchPort;
	}

	public void setSwitchPort(String switchPort) {
		this.switchPort = switchPort;
	}

	public SwitchData() {
		super();
	}

	public SwitchData(String switchName, String switchPort) {
		super();
		this.switchName = switchName;
		this.switchPort = switchPort;
	}

	@Override
	public String toString() {
		return "SwitchData [switchName=" + switchName + ", switchPort="
				+ switchPort + "]";
	}

}
