package com.boot.security.server.model;

/**
 * 场馆历史数据查询（数据源为IMSI记录表） LTE_Region_NUM_HOUR
 */
public class HiGridDataHour {

	private String sdate;// 日期
	private String regionType;// 区域
	private String region;// 场馆编号
	private long imsiCum;// 每小时移动用户
	private long imsiMale;// 当前移动用户男
	private long imsiFeMale;// 女
	private long imsiAge1;// 当前移动用户年龄1
	private long imsiAge2;
	private long imsiAge3;
	private long imsiAge4;
	private long imsiAge5;
	private long imsiSource1;// 当前移动用户 省内
	private long imsiSource2;// 当前移动用户 省外

	
	
	
	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
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

	public long getImsiCum() {
		return imsiCum;
	}

	public void setImsiCum(long imsiCum) {
		this.imsiCum = imsiCum;
	}

	public long getImsiMale() {
		return imsiMale;
	}

	public void setImsiMale(long imsiMale) {
		this.imsiMale = imsiMale;
	}

	public long getImsiFeMale() {
		return imsiFeMale;
	}

	public void setImsiFeMale(long imsiFeMale) {
		this.imsiFeMale = imsiFeMale;
	}

	public long getImsiAge1() {
		return imsiAge1;
	}

	public void setImsiAge1(long imsiAge1) {
		this.imsiAge1 = imsiAge1;
	}

	public long getImsiAge2() {
		return imsiAge2;
	}

	public void setImsiAge2(long imsiAge2) {
		this.imsiAge2 = imsiAge2;
	}

	public long getImsiAge3() {
		return imsiAge3;
	}

	public void setImsiAge3(long imsiAge3) {
		this.imsiAge3 = imsiAge3;
	}

	public long getImsiAge4() {
		return imsiAge4;
	}

	public void setImsiAge4(long imsiAge4) {
		this.imsiAge4 = imsiAge4;
	}

	public long getImsiAge5() {
		return imsiAge5;
	}

	public void setImsiAge5(long imsiAge5) {
		this.imsiAge5 = imsiAge5;
	}


	public long getImsiSource1() {
		return imsiSource1;
	}

	public void setImsiSource1(long imsiSource1) {
		this.imsiSource1 = imsiSource1;
	}

	public long getImsiSource2() {
		return imsiSource2;
	}

	public void setImsiSource2(long imsiSource2) {
		this.imsiSource2 = imsiSource2;
	}

	public HiGridDataHour(String sdate, String regionType, String region, long imsiCum, long imsiMale, long imsiFeMale,
			long imsiAge1, long imsiAge2, long imsiAge3, long imsiAge4, long imsiAge5, long imsiSource1,
			long imsiSource2) {
		super();
		this.sdate = sdate;
		this.regionType = regionType;
		this.region = region;
		this.imsiCum = imsiCum;
		this.imsiMale = imsiMale;
		this.imsiFeMale = imsiFeMale;
		this.imsiAge1 = imsiAge1;
		this.imsiAge2 = imsiAge2;
		this.imsiAge3 = imsiAge3;
		this.imsiAge4 = imsiAge4;
		this.imsiAge5 = imsiAge5;
		this.imsiSource1 = imsiSource1;
		this.imsiSource2 = imsiSource2;
	}

	public HiGridDataHour() {
	}

}
