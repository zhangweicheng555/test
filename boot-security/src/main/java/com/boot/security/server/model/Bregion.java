package com.boot.security.server.model;

/**
 * b域数据实体类
 * 
 * @author weichengz
 * @date 2018年9月8日 上午8:36:34
 */
public class Bregion {

	private String sdate;
	private Long eci;
	private String gender;
	private Long age;
	private Long sourcetype;
	private String source;
	private Long imei_num;
	private String area_type;
	private Long floor_no;
	private Long stadium_no;

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public Long getEci() {
		return eci;
	}

	public void setEci(Long eci) {
		this.eci = eci;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(Long sourcetype) {
		this.sourcetype = sourcetype;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getImei_num() {
		return imei_num;
	}

	public void setImei_num(Long imei_num) {
		this.imei_num = imei_num;
	}

	public String getArea_type() {
		return area_type;
	}

	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}

	public Long getFloor_no() {
		return floor_no;
	}

	public void setFloor_no(Long floor_no) {
		this.floor_no = floor_no;
	}

	public Long getStadium_no() {
		return stadium_no;
	}

	public void setStadium_no(Long stadium_no) {
		this.stadium_no = stadium_no;
	}

	public Bregion(String sdate, Long eci, String gender, Long age, Long sourcetype, String source, Long imei_num,
			String area_type, Long floor_no, Long stadium_no) {
		super();
		this.sdate = sdate;
		this.eci = eci;
		this.gender = gender;
		this.age = age;
		this.sourcetype = sourcetype;
		this.source = source;
		this.imei_num = imei_num;
		this.area_type = area_type;
		this.floor_no = floor_no;
		this.stadium_no = stadium_no;
	}

	public Bregion() {
		super();
	}

}
