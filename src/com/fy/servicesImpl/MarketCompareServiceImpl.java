package com.fy.servicesImpl;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import com.fy.database.MarketCompare;
import com.fy.database.MarketCompareDAO;
import com.fy.services.MarketCompareService;


public class MarketCompareServiceImpl implements MarketCompareService {

	private MarketCompareDAO marketCompareDAO;
	
	private PlatformTransactionManager transactionManager;
	
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	
	public MarketCompareServiceImpl(){
	}
	
	public List GetMCParameter(Long pID,short type){
		MarketCompare instance = new MarketCompare();
		instance.setType(type);
		instance.setProjectId(pID);
		return marketCompareDAO.findByExample(instance);
	}
	
	public MarketCompareDAO getMarketCompareDAO() {
		return marketCompareDAO;
	}
	public void setMarketCompareDAO(MarketCompareDAO marketCompareDAO) {
		this.marketCompareDAO = marketCompareDAO;
	}
	public String AddMCParameter(MarketCompare param){
		String ret = "success";
		try {
			MarketCompare mc = new MarketCompare(param.getProjectId(),param.getColumn(),param.getRow(),param.getType(),null);
			
			marketCompareDAO.getHibernateTemplate().deleteAll(marketCompareDAO.findByExample(mc));
			marketCompareDAO.getHibernateTemplate().saveOrUpdate(param);
			//marketCompareDAO.attachDirty(param);
			//marketCompareDAO.save(param);
		} catch (Exception e) {
			String msg = "Could not Add marketCompareDAO " + e.getMessage();
			ret = "failed";
		}
		return ret;
	}
}
