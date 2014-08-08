package com.fy.servicesImpl;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import com.fy.database.ProjectFy;
import com.fy.database.ProjectFyDAO;
import com.fy.services.ProjectService;
import com.fy.services.SearchProject;


public class ProjectServiceImpl implements ProjectService {

	private ProjectFyDAO projectDao;
	private PlatformTransactionManager transactionManager;
	
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public ProjectFyDAO getProjectDao() {
		return projectDao;
	}
	public void setProjectDao(ProjectFyDAO projectDao) {
		this.projectDao = projectDao;
	}
	//查询
	public List SearchProject(SearchProject searchPj){
		List ls = null;
		try {
			ls = projectDao.getHibernateTemplate().findByCriteria(searchPj.GenerateDC());
		} catch (Exception e) {
			String msg = "Could not SearchProject " + e.getMessage();
		}
		return ls;
	}
	//添加
	public String AddProject(ProjectFy project){
		String ret = "success";
		try {
			projectDao.save(project);
		} catch (Exception e) {
			String msg = "Could not AddProject " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
	}
	//删除
	public String DeleteProject(Long id){
		String ret = "success";
		try {
			projectDao.delete(projectDao.getHibernateTemplate().get(ProjectFy.class, id));
		} catch (Exception e) {
			String msg = "Could not DeleteProject " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
	}
	//修改
	public String SaveOrUpdateProject(ProjectFy project){
		String ret = "success";
		try {
			if(project.getPingguMoney() == null)
				project.setPingguMoney(0);
			projectDao.attachDirty(project);
		} catch (Exception e) {
			String msg = "Could not EditProject " + e.getMessage();
			ret = "failed";
		}
		return ret;
	}
	
	//通过流程ID得到产品
	public List GetProjectBySId(String processInstanceId){
		List ls = null;
		try {
			ls = projectDao.findByProcessInstanceId(processInstanceId);
		} catch (Exception e) {
			String msg = "Could not SearchProject " + e.getMessage();
		}
		return ls;
	}
	
	//通过项目ID得到产品
	public ProjectFy GetProjectByPId(Long projectId){
		ProjectFy p = null;
		try {
			p = projectDao.findById(projectId);
		} catch (Exception e) {
			String msg = "Could not SearchProject " + e.getMessage();
		}
		return p;
	}
}
