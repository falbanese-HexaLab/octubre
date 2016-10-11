package com.sap.cloud.sample.persistence;

import java.util.Date;

public class Job {
	private Person worker = new Person();
	private Project project = new Project();
	private Float hours = (float) 0;
	private String date = "11/11/2000";
	
	public void init(Person worker, Project project, Float hours, String date) {
		this.worker = worker;
		this.project = project;
		this.hours = hours;
		this.date = date;
	}
	
	public void setWorker(Person worker) {
		this.worker = worker;
	}
	public Person getWorker() {
		return worker;
	}
	
	public void setProject(Project newProject) {
		this.project = newProject;
	}
	public Project getProject() {
		return project;
	}
	
	public void setHours(Float hours) {
		this.hours = hours;
	}
	public Float getHours() {
		return hours;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
}