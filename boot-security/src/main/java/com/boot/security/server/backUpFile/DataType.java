package com.boot.security.server.backUpFile;

import java.util.HashMap;
import java.util.Map;

/***
 * 枚举使用举例、这里面有个静态变量初始化的例子
 */
public enum DataType {

	
	LANE(1l, "lane", "车道线"), GROUND_SYMBOL(2l, "groundSymbol", "地面标识"), POLE(3l, "pole", "灯杆"), TRAFFIC_SIGN(4l,
			"trafficSign", "路牌");

	private Long type;
	private String code;
	private String name;

	private static Map<String, String> map = new HashMap<String, String>();
	static {
		for (DataType s : DataType.values()) {
			map.put(s.code, s.name);
		}
	}

	private DataType(Long type, String code, String name) {
		this.type = type;
		this.code = code;
		this.name = name;
	}

	@Override
	public String toString() {
		return String.valueOf(this.code);
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public Long getType() {
		return this.type;
	}

	public static String nameOf(String code) {
		for (DataType s : DataType.values()) {
			if (String.valueOf(s.getCode()).equals(String.valueOf(code))) {
				return s.getName();
			}
		}
		return null;
	}

	public static Map<String, String> map() {
		return map;
	}

	public static DataType enumOf(String code) {
		for (DataType s : DataType.values()) {
			if (String.valueOf(s.getCode()).equals(String.valueOf(code))) {
				return s;
			}
		}
		return null;
	}

}