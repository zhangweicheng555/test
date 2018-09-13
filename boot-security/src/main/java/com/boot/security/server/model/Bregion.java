package com.boot.security.server.model;

/**
 * b域数据实体类
 * 
 * @author weichengz
 * @date 2018年9月8日 上午8:36:34
 */
public class Bregion {

	private String sdate;// 时间
	private Long eci;// 小区id
	private String gender;// 性别 男 女
	private String age;// 年龄段 0-18、18-35、35-50、50-65、65-200、NA
	private Long sourcetype;// 来源地是否属于大陆 0-大陆，1-大陆之外的区域和国家
	private String source;// 省份或者直辖市或者国家地区
	private Long imei_num;// IMEI数量
	private String area_type;// 区域类型
	private Long floor_no;// 楼层号
	private String stadium_no;// 场馆编号

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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

	public Bregion(String sdate, Long eci, String gender, String age, Long sourcetype, String source, Long imei_num,
			String area_type, Long floor_no, String stadium_no) {
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

	public String getStadium_no() {
		return stadium_no;
	}

	public void setStadium_no(String stadium_no) {
		this.stadium_no = stadium_no;
	}

	public Bregion() {
		super();
	}

}
