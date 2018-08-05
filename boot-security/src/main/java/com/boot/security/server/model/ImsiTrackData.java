package com.boot.security.server.model;

/**
 * 用户轨迹 LTE_IMSI_TRACE_DATA
 */
public class ImsiTrackData {

	private String sdate;
	private double imsi;
	private double cellId;
	private String regionType;
	private String region;
	private double gridx;
	private double gridy;
	private double longitude;
	private double latitude;
	private double confidence;
	private String remark;

	public String getSdate() {
		return sdate;
	}

	public void setImsi(Long imsi) {
		this.imsi = imsi;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public double getImsi() {
		return imsi;
	}

	

	public double getCellId() {
		return cellId;
	}

	public void setCellId(double cellId) {
		this.cellId = cellId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public ImsiTrackData(String sdate, double imsi, double cellId, String regionType, String region, double gridx,
			double gridy, double longitude, double latitude, double confidence, String remark) {
		super();
		this.sdate = sdate;
		this.imsi = imsi;
		this.cellId = cellId;
		this.regionType = regionType;
		this.region = region;
		this.gridx = gridx;
		this.gridy = gridy;
		this.longitude = longitude;
		this.latitude = latitude;
		this.confidence = confidence;
		this.remark = remark;
	}

	public void setImsi(double imsi) {
		this.imsi = imsi;
	}

	public double getGridx() {
		return gridx;
	}

	public void setGridx(double gridx) {
		this.gridx = gridx;
	}

	public double getGridy() {
		return gridy;
	}

	public void setGridy(double gridy) {
		this.gridy = gridy;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	

	public ImsiTrackData() {
		super();
	}

}
