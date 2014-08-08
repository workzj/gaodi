package com.fy.database;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ProjectFileFy entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.fy.database.ProjectFileFy
 * @author MyEclipse Persistence Tools
 */

public class ProjectFileFyDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(ProjectFileFyDAO.class);
	// property constants
	public static final String FILEPATH = "filepath";
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String PROJECTCODE = "projectcode";
	public static final String FILESIZE = "filesize";
	public static final String USERID = "userid";

	protected void initDao() {
		// do nothing
	}

	public void save(ProjectFileFy transientInstance) {
		log.debug("saving ProjectFileFy instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ProjectFileFy persistentInstance) {
		log.debug("deleting ProjectFileFy instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProjectFileFy findById(java.lang.Integer id) {
		log.debug("getting ProjectFileFy instance with id: " + id);
		try {
			ProjectFileFy instance = (ProjectFileFy) getHibernateTemplate()
					.get("com.fy.database.ProjectFileFy", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ProjectFileFy instance) {
		log.debug("finding ProjectFileFy instance by example");
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
		log.debug("finding ProjectFileFy instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ProjectFileFy as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFilepath(Object filepath) {
		return findByProperty(FILEPATH, filepath);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findByProjectcode(Object projectcode) {
		return findByProperty(PROJECTCODE, projectcode);
	}

	public List findByFilesize(Object filesize) {
		return findByProperty(FILESIZE, filesize);
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid);
	}

	public List findAll() {
		log.debug("finding all ProjectFileFy instances");
		try {
			String queryString = "from ProjectFileFy";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ProjectFileFy merge(ProjectFileFy detachedInstance) {
		log.debug("merging ProjectFileFy instance");
		try {
			ProjectFileFy result = (ProjectFileFy) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ProjectFileFy instance) {
		log.debug("attaching dirty ProjectFileFy instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProjectFileFy instance) {
		log.debug("attaching clean ProjectFileFy instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProjectFileFyDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ProjectFileFyDAO) ctx.getBean("ProjectFileFyDAO");
	}
}