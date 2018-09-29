package com.boot.security.server.model;

import java.io.Serializable;

/**
 * 返回类型通用的设置model
 * 
 * @author weichengz
 * @date 2018年9月15日 上午10:04:57
 */
public class CommonModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Double x;
	private Double y;
	private Double imsi;
	private Double radio;
	private Double allNum;
	private Double userCount;

	private String region;

	public Double getX() {
		return x;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getUserCount() {
		return userCount;
	}

	public void setUserCount(Double userCount) {
		this.userCount = userCount;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getImsi() {
		return imsi;
	}

	public void setImsi(Double imsi) {
		this.imsi = imsi;
	}

	public Double getRadio() {
		return radio;
	}

	public void setRadio(Double radio) {
		this.radio = radio;
	}

	public Double getAllNum() {
		return allNum;
	}

	public void setAllNum(Double allNum) {
		this.allNum = allNum;
	}

}
