package com.fy.database;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserFy entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.fy.database.UserFy
 * @author MyEclipse Persistence Tools
 */

public class UserFyDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(UserFyDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String NIKENAME = "nikename";
	public static final String ROLENAME = "rolename";

	protected void initDao() {
		// do nothing
	}

	public void save(UserFy transientInstance) {
		log.debug("saving UserFy instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserFy persistentInstance) {
		log.debug("deleting UserFy instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserFy findById(java.lang.Integer id) {
		log.debug("getting UserFy instance with id: " + id);
		try {
			UserFy instance = (UserFy) getHibernateTemplate().get(
					"com.fy.database.UserFy", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserFy instance) {
		log.debug("finding UserFy instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserFy instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserFy as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByNikename(Object nikename) {
		return findByProperty(NIKENAME, nikename);
	}

	public List findByRolename(Object rolename) {
		return findByProperty(ROLENAME, rolename);
	}

	public List findAll() {
		log.debug("finding all UserFy instances");
		try {
			String queryString = "from UserFy";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserFy merge(UserFy detachedInstance) {
		log.debug("merging UserFy instance");
		try {
			UserFy result = (UserFy) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserFy instance) {
		log.debug("attaching dirty UserFy instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserFy instance) {
		log.debug("attaching clean UserFy instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserFyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UserFyDAO) ctx.getBean("UserFyDAO");
	}
}