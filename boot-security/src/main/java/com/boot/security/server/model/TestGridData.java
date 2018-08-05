package com.boot.security.server.model;
/**
 * 测试栅格用户数
 */
public class TestGridData {

	private String id;
	private String fid;
	private String layer;
	private String subcls;
	private String extendeden;
	private String linetype;
	private String entityHand;
	private String textstr;
	private String entityHandOne;
	private double gridx;
	private double gridy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getSubcls() {
		return subcls;
	}

	public void setSubcls(String subcls) {
		this.subcls = subcls;
	}

	public String getExtendeden() {
		return extendeden;
	}

	public void setExtendeden(String extendeden) {
		this.extendeden = extendeden;
	}

	public String getLinetype() {
		return linetype;
	}

	public void setLinetype(String linetype) {
		this.linetype = linetype;
	}

	public String getEntityHand() {
		return entityHand;
	}

	public void setEntityHand(String entityHand) {
		this.entityHand = entityHand;
	}

	public String getTextstr() {
		return textstr;
	}

	public void setTextstr(String textstr) {
		this.textstr = textstr;
	}

	public String getEntityHandOne() {
		return entityHandOne;
	}

	public void setEntityHandOne(String entityHandOne) {
		this.entityHandOne = entityHandOne;
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

	public TestGridData(String id, String fid, String layer, String subcls, String extendeden, String linetype,
			String entityHand, String textstr, String entityHandOne, double gridx, double gridy) {
		this.id = id;
		this.fid = fid;
		this.layer = layer;
		this.subcls = subcls;
		this.extendeden = extendeden;
		this.linetype = linetype;
		this.entityHand = entityHand;
		this.textstr = textstr;
		this.entityHandOne = entityHandOne;
		this.gridx = gridx;
		this.gridy = gridy;
	}

	public TestGridData() {
	}

}
