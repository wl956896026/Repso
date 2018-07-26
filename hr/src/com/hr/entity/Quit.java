package com.hr.entity;

public class Quit {
	private int quit_id;
	private int emp_id;
	private int quit_type;
	private String quit_time;
	private String quit_reason;
	public int getQuit_id() {
		return quit_id;
	}
	public void setQuit_id(int quit_id) {
		this.quit_id = quit_id;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public int getQuit_type() {
		return quit_type;
	}
	public void setQuit_type(int quit_type) {
		this.quit_type = quit_type;
	}
	public String getQuit_time() {
		return quit_time;
	}
	public void setQuit_time(String quit_time) {
		this.quit_time = quit_time;
	}
	public String getQuit_reason() {
		return quit_reason;
	}
	public void setQuit_reason(String quit_reason) {
		this.quit_reason = quit_reason;
	}
	
	
}
