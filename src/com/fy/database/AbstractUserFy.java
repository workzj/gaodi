package com.fy.database;

/**
 * AbstractUserFy entity provides the base persistence definition of the UserFy
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserFy implements java.io.Serializable {

	// Fields

	private Integer iduserFy;
	private String name;
	private String password;
	private String nikename;
	private String rolename;

	// Constructors

	/** default constructor */
	public AbstractUserFy() {
	}

	/** full constructor */
	public AbstractUserFy(String name, String password, String nikename,
			String rolename) {
		this.name = name;
		this.password = password;
		this.nikename = nikename;
		this.rolename = rolename;
	}

	// Property accessors

	public Integer getIduserFy() {
		return this.iduserFy;
	}

	public void setIduserFy(Integer iduserFy) {
		this.iduserFy = iduserFy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNikename() {
		return this.nikename;
	}

	public void setNikename(String nikename) {
		this.nikename = nikename;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}