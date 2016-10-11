package com.sap.cloud.sample.persistence;

public class Project {
	private String id = "oneProjectId";
	private String name = "oneProjectName";
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}