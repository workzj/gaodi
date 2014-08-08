package com.fy.services;

import java.util.List;

import com.fy.database.ProjectFileFy;

public interface ProjectFileService {
	public String AddProjectFile(ProjectFileFy pgFile);
	public String DeleteProjectFile(Long id);
	public String DeleteFile(Integer fid);
	public String SaveOrUpdateProjectFile(ProjectFileFy pgFile);
	public List<ProjectFileFy> GetProjectFilesByCode(Long projectcode);
	public List<ProjectFileFy> GetProjectPicsByCode(Long projectcode);
	public String ChangeFileName(String oldName,String newName);
}
