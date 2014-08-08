package com.fy.database;

import java.sql.Timestamp;

/**
 * ProjectFileFy entity. @author MyEclipse Persistence Tools
 */
public class ProjectFileFy extends AbstractProjectFileFy implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ProjectFileFy() {
	}

	/** full constructor */
	public ProjectFileFy(String filepath, String name, Integer type,
			Long projectcode, Integer filesize, Integer userid,
			Timestamp uploadTime) {
		super(filepath, name, type, projectcode, filesize, userid, uploadTime);
	}

}
