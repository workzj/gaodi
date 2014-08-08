package com.fy.database;

/**
 * AbstractRoleFy entity provides the base persistence definition of the RoleFy
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleFy implements java.io.Serializable {

	// Fields

	private Integer idroleFy;
	private String name;
	private String remark;

	// Constructors

	/** default constructor */
	public AbstractRoleFy() {
	}

	/** full constructor */
	public AbstractRoleFy(String name, String remark) {
		this.name = name;
		this.remark = remark;
	}

	// Property accessors

	public Integer getIdroleFy() {
		return this.idroleFy;
	}

	public void setIdroleFy(Integer idroleFy) {
		this.idroleFy = idroleFy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}