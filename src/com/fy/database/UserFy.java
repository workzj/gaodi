package com.fy.database;

/**
 * UserFy entity. @author MyEclipse Persistence Tools
 */
public class UserFy extends AbstractUserFy implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserFy() {
	}

	/** full constructor */
	public UserFy(String name, String password, String nikename, String rolename) {
		super(name, password, nikename, rolename);
	}

}
