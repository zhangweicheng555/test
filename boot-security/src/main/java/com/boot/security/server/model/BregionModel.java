package com.boot.security.server.model;

import java.io.Serializable;

/**
 * b域数据实体类
 * 
 * @author weichengz
 * @date 2018年9月8日 上午8:36:34
 */
public class BregionModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String gender;
	private Long gnum;
	private String age;
	private Long anum;
	private String source;
	private Long snum;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getGnum() {
		return gnum;
	}

	public void setGnum(Long gnum) {
		this.gnum = gnum;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getAnum() {
		return anum;
	}

	public void setAnum(Long anum) {
		this.anum = anum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getSnum() {
		return snum;
	}

	public void setSnum(Long snum) {
		this.snum = snum;
	}

}
