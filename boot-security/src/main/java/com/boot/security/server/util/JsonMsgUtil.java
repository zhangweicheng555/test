package com.boot.security.server.util;

public class JsonMsgUtil {

	private boolean success = false;
	private String msg;
	private Object object = null;

	public JsonMsgUtil(boolean success, String msg, Object object) {
		super();
		this.success = success;
		this.msg = msg;
		this.object = object;
	}

	public JsonMsgUtil() {
		super();
	}

	public JsonMsgUtil(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
