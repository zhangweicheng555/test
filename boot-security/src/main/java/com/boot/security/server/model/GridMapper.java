package com.boot.security.server.model;

/**
 * 栅格用户数映射表
 */
public class GridMapper {

	private String id;
	private String entityHand;// 区域
	private double gridx;// 栅格坐标x
	private double gridy;// 栅格坐标y
	private int newidx;//
	private int newidy;//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityHand() {
		return entityHand;
	}

	public void setEntityHand(String entityHand) {
		this.entityHand = entityHand;
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

	public int getNewidx() {
		return newidx;
	}

	public void setNewidx(int newidx) {
		this.newidx = newidx;
	}

	public int getNewidy() {
		return newidy;
	}

	public void setNewidy(int newidy) {
		this.newidy = newidy;
	}

	public GridMapper(String id, String entityHand, double gridx, double gridy, int newidx, int newidy) {
		this.id = id;
		this.entityHand = entityHand;
		this.gridx = gridx;
		this.gridy = gridy;
		this.newidx = newidx;
		this.newidy = newidy;
	}

	public GridMapper() {
	}

}
