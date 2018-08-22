package com.boot.security.server.model;

/**
 * 根据移动用户占比计算总数量
 */
public class MobileUserCount {

	private double ratio;// 移动用户占比
	private double imsi;// 总用户数

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public MobileUserCount(double ratio, double imsi) {
		super();
		this.ratio = ratio;
		this.imsi = imsi;
	}

	public double getImsi() {
		return imsi;
	}

	public void setImsi(double imsi) {
		this.imsi = imsi;
	}

	public MobileUserCount() {
	}

}
