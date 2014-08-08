package com.fy.servicesImpl;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.transaction.PlatformTransactionManager;
import com.fy.services.ProjectFileService;
import com.fy.database.AbstractProjectFy;
import com.fy.database.ProjectFileFy;
import com.fy.database.ProjectFileFyDAO;
import com.fy.services.ProjectFileService;
import com.fy.utils.file.FileUtil;



public class ProjectFileServiceImpl implements ProjectFileService {

	private ProjectFileFyDAO projectFileDao;
	private PlatformTransactionManager transactionManager;
	
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	public ProjectFileFyDAO getProjectFileDao() {
		return projectFileDao;
	}
	public void setProjectFileDao(ProjectFileFyDAO projectFileDao) {
		this.projectFileDao = projectFileDao;
	}
	//���һ����Ŀ�ļ�
	public String AddProjectFile(ProjectFileFy pgFile){
		String ret = "success";
		try {
			projectFileDao.getHibernateTemplate().deleteAll(projectFileDao.findByName(pgFile.getName()));
			projectFileDao.save(pgFile);
			projectFileDao.getHibernateTemplate().flush();
		} catch (Exception e) {
			String msg = "Could not AddProjectFile " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
	}
	//ɾ��һ����Ŀ�ļ�
	public String DeleteProjectFile(Long pid){
		String ret = "success";
		try {
			ProjectFileFy fy = new ProjectFileFy();
			fy.setProjectcode(pid);
			projectFileDao.getHibernateTemplate().delete(fy);
		} catch (Exception e) {
			String msg = "Could not DeleteProjectFile " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
	}
	
	//ɾ��һ���ļ�
	public String DeleteFile(Integer fid){
		String ret = "success";
		try {
			ProjectFileFy fy = new ProjectFileFy();
			fy.setIdprojectFileFy(fid);
			projectFileDao.getHibernateTemplate().delete(fy);
		} catch (Exception e) {
			String msg = "Could not DeleteProjectFile " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
	}
	
	//�����ļ���
	public String ChangeFileName(String oldName,String newName){
		String ret = "success";
		try {
			if(projectFileDao.findByName(newName).size() == 0){
				//���������û�м�¼���Ϳ��Ը����ˡ�
				ProjectFileFy fy = (ProjectFileFy)projectFileDao.findByName(oldName).get(0);
				fy.setName(newName);
				ret = SaveOrUpdateProjectFile(fy);
				
				ServletContext servletContext = (ServletContext) FacesContext  
		                .getCurrentInstance().getExternalContext().getContext();
				String realPath = servletContext.getRealPath("");
				String userDirs = "/data/"+fy.getProjectcode().toString() + "/";
				if(fy.getType() == 0){
					userDirs += "files/";
				}else if(fy.getType() == 1){
					userDirs += "pictures/";
				}
				String file = realPath + userDirs + oldName;
				String tofile = realPath + userDirs + newName;
				FileUtil.renameFile(file,tofile);
			}
		} catch (Exception e) {
			String msg = "Could not Change File Name " + e.getMessage();
			ret = "failed";
		}
		return ret;
	}
	
	//�޸�
	public String SaveOrUpdateProjectFile(ProjectFileFy pgFile){
		String ret = "success";
		try {
			projectFileDao.attachDirty(pgFile);
		} catch (Exception e) {
			String msg = "Could not EditProjectFile " + e.getMessage();
			ret = "failed";
		}
		return ret;
	}
	
	//���Ҷ�Ӧ��projectcode ���еĸ����б�
	public List<ProjectFileFy> GetProjectFilesByCode(Long projectcode){
		List<ProjectFileFy> ls = null;
		try {
			DetachedCriteria detachedCrit = DetachedCriteria.forClass(ProjectFileFy.class);
			//�����ǣ�
			detachedCrit.add( Property.forName("projectcode").eq(projectcode) );
			detachedCrit.add( Property.forName("type").eq(0) );//type = 0 �����Ǹ���
			ls = projectFileDao.getHibernateTemplate().findByCriteria(detachedCrit);
		} catch (Exception e) {
			String msg = "Could not SearchProjectFile " + e.getMessage();
		}
		return ls;
	}
	
	//���Ҷ�Ӧ��projectcode ���е�ͼƬ�б�
	public List<ProjectFileFy> GetProjectPicsByCode(Long projectcode){
		List<ProjectFileFy> ls = null;
		try {
			DetachedCriteria detachedCrit = DetachedCriteria.forClass(ProjectFileFy.class);
			//�����ǣ�
			detachedCrit.add( Property.forName("projectcode").eq(projectcode) );
			detachedCrit.add( Property.forName("type").eq(1) );//type = 1 ������ͼƬ
			ls = projectFileDao.getHibernateTemplate().findByCriteria(detachedCrit);
		} catch (Exception e) {
			String msg = "Could not SearchProjectFile " + e.getMessage();
		}
		return ls;
	}

	
}
