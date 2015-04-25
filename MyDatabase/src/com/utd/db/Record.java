package com.utd.db;

public class Record {
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
