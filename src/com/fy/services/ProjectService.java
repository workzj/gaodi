package com.fy.services;

import java.util.List;

import com.fy.database.ProjectFy;

public interface ProjectService {
	public List SearchProject(SearchProject searchPj);
	public String AddProject(ProjectFy project);
	public String DeleteProject(Long pid);
	public String SaveOrUpdateProject(ProjectFy project);
	public List GetProjectBySId(String processInstanceId);
	public ProjectFy GetProjectByPId(Long processInstanceId);
}
