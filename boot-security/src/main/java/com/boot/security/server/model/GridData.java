package com.boot.security.server.model;

/**
 * 栅格用户数 LTE_GRID_IMSI_NUM
 */
public class GridData {

	private double longitude;// 经度
	private double latitude;// 纬度
	private String regiontype;// 区域 Indoor Outdoor
	private String floorno;//
	private String region;// 场馆编号
	private double imsi_num;// 该栅格内终端IMEI号个数
	private double ratio;// 移动用户占比
	private double totalusers;// 总用户数
	private String sdate;// 日期
	private double gridx;// 栅格坐标x
	private double gridy;// 栅格坐标y
	private String scenario;//
	private String city;//

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getRegiontype() {
		return regiontype;
	}

	public void setRegiontype(String regiontype) {
		this.regiontype = regiontype;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public double getImsi_num() {
		return imsi_num;
	}

	public void setImsi_num(double imsi_num) {
		this.imsi_num = imsi_num;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public double getTotalusers() {
		return totalusers;
	}

	public void setTotalusers(double totalusers) {
		this.totalusers = totalusers;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFloorno() {
		return floorno;
	}

	public void setFloorno(String floorno) {
		this.floorno = floorno;
	}

	public GridData(double longitude, double latitude, String regiontype, String floorno, String region,
			double imsi_num, double ratio, double totalusers, String sdate, double gridx, double gridy, String scenario,
			String city) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.regiontype = regiontype;
		this.floorno = floorno;
		this.region = region;
		this.imsi_num = imsi_num;
		this.ratio = ratio;
		this.totalusers = totalusers;
		this.sdate = sdate;
		this.gridx = gridx;
		this.gridy = gridy;
		this.scenario = scenario;
		this.city = city;
	}

	public GridData() {
		super();
	}

}
