package com.hr.entity;

public class Experience {
	private int experience_id;
	private String start_time;
	private String end_time;
	private String company;
	private String position;
	private String job_content;
	public int getExperience_id() {
		return experience_id;
	}
	public void setExperience_id(int experience_id) {
		this.experience_id = experience_id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getJob_content() {
		return job_content;
	}
	public void setJob_content(String job_content) {
		this.job_content = job_content;
	}
	@Override
	public String toString() {
		return "Experience [experience_id=" + experience_id + ", start_time="
				+ start_time + ", end_time=" + end_time + ", company="
				+ company + ", position=" + position + ", job_content="
				+ job_content + "]";
	}
	

}
