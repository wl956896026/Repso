package com.hr.entity;

public class Position {
	private int position_id;
	private String position_name;
	private float position_price;
	private String position_desc;
	private int dept_id;
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public float getPosition_price() {
		return position_price;
	}
	public void setPosition_price(float position_price) {
		this.position_price = position_price;
	}
	public String getPosition_desc() {
		return position_desc;
	}
	public void setPosition_desc(String position_desc) {
		this.position_desc = position_desc;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	

}
