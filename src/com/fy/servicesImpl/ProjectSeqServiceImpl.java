package com.fy.servicesImpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.transaction.PlatformTransactionManager;
import com.fy.services.ProjectSeqService;
import com.fy.database.ProjectSequenceDAO;
import com.fy.database.ProjectSequence;


public class ProjectSeqServiceImpl implements ProjectSeqService {

	private ProjectSequenceDAO projectSeqDao;
	private PlatformTransactionManager transactionManager;
	
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	public ProjectSequenceDAO getProjectSeqDao() {
		return projectSeqDao;
	}
	public void setProjectSeqDao(ProjectSequenceDAO projectSeqDao) {
		this.projectSeqDao = projectSeqDao;
	}

	//ÊµÏÖº¯Êý
	public Integer GetProjectSeq(){
		ProjectSequence ps =  new ProjectSequence();
		projectSeqDao.save(ps);
		return ps.getIdprojectSequence();
	}
}
