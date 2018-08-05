package com.boot.security.server.model;

import java.util.List;

public class TraceModel {

	private String region;
	private List<Double> tels;
	private List<String> times;
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public List<Double> getTels() {
		return tels;
	}
	public void setTels(List<Double> tels) {
		this.tels = tels;
	}
	public List<String> getTimes() {
		return times;
	}
	public void setTimes(List<String> times) {
		this.times = times;
	}
	public TraceModel(String region, List<Double> tels, List<String> times) {
		super();
		this.region = region;
		this.tels = tels;
		this.times = times;
	}
	public TraceModel() {
		super();
	}
	
}
