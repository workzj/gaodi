package com.fy.database;

import java.sql.Timestamp;

/**
 * AbstractProjectFileFy entity provides the base persistence definition of the
 * ProjectFileFy entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractProjectFileFy implements java.io.Serializable {

	// Fields

	private Integer idprojectFileFy;
	private String filepath;
	private String name;
	private Integer type;
	private Long projectcode;
	private Integer filesize;
	private Integer userid;
	private Timestamp uploadTime;

	// Constructors

	/** default constructor */
	public AbstractProjectFileFy() {
	}

	/** full constructor */
	public AbstractProjectFileFy(String filepath, String name, Integer type,
			Long projectcode, Integer filesize, Integer userid,
			Timestamp uploadTime) {
		this.filepath = filepath;
		this.name = name;
		this.type = type;
		this.projectcode = projectcode;
		this.filesize = filesize;
		this.userid = userid;
		this.uploadTime = uploadTime;
	}

	// Property accessors

	public Integer getIdprojectFileFy() {
		return this.idprojectFileFy;
	}

	public void setIdprojectFileFy(Integer idprojectFileFy) {
		this.idprojectFileFy = idprojectFileFy;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getProjectcode() {
		return this.projectcode;
	}

	public void setProjectcode(Long projectcode) {
		this.projectcode = projectcode;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}