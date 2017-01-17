package com.hanson.elasticsearch.data;

public class Project {
	private int projectId;
	private String projectName;
	private String projectContent;
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	public Project(int projectId, String projectName, String projectContent) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectContent = projectContent;
	}
	
	
}
