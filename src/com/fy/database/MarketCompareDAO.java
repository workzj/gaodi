package com.fy.database;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 	* A data access object (DAO) providing persistence and search support for MarketCompare entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.fy.database.MarketCompare
  * @author MyEclipse Persistence Tools 
 */

public class MarketCompareDAO extends HibernateDaoSupport  {
	     private static final Logger log = LoggerFactory.getLogger(MarketCompareDAO.class);
		//property constants
	public static final String PROJECT_ID = "projectId";
	public static final String COLUMN = "column";
	public static final String ROW = "row";
	public static final String TYPE = "type";
	public static final String VALUE = "value";



	protected void initDao() {
		//do nothing
	}
    
    public void save(MarketCompare transientInstance) {
        log.debug("saving MarketCompare instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MarketCompare persistentInstance) {
        log.debug("deleting MarketCompare instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MarketCompare findById( java.lang.Long id) {
        log.debug("getting MarketCompare instance with id: " + id);
        try {
            MarketCompare instance = (MarketCompare) getHibernateTemplate()
                    .get("com.fy.database.MarketCompare", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MarketCompare instance) {
        log.debug("finding MarketCompare instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding MarketCompare instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MarketCompare as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByProjectId(Object projectId
	) {
		return findByProperty(PROJECT_ID, projectId
		);
	}
	
	public List findByColumn(Object column
	) {
		return findByProperty(COLUMN, column
		);
	}
	
	public List findByRow(Object row
	) {
		return findByProperty(ROW, row
		);
	}
	
	public List findByType(Object type
	) {
		return findByProperty(TYPE, type
		);
	}
	
	public List findByValue(Object value
	) {
		return findByProperty(VALUE, value
		);
	}
	

	public List findAll() {
		log.debug("finding all MarketCompare instances");
		try {
			String queryString = "from MarketCompare";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public MarketCompare merge(MarketCompare detachedInstance) {
        log.debug("merging MarketCompare instance");
        try {
            MarketCompare result = (MarketCompare) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MarketCompare instance) {
        log.debug("attaching dirty MarketCompare instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MarketCompare instance) {
        log.debug("attaching clean MarketCompare instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static MarketCompareDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (MarketCompareDAO) ctx.getBean("MarketCompareDAO");
	}
}