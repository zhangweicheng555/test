package com.boot.security.server.model;

/**
 * 根据移动用户占比计算总数量
 */
public class MobileUserCount {

	private double ratio;// 移动用户占比
	private double imsi_num;// 总用户数

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public double getImsi_num() {
		return imsi_num;
	}

	public void setImsi_num(double imsi_num) {
		this.imsi_num = imsi_num;
	}

	public MobileUserCount(double ratio, double imsi_num) {
		this.ratio = ratio;
		this.imsi_num = imsi_num;
	}

	public MobileUserCount() {
	}

}
