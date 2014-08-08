package com.fy.database;



/**
 * AbstractMarketCompare entity provides the base persistence definition of the MarketCompare entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractMarketCompare  implements java.io.Serializable {


    // Fields    

     private Long idmarketCompare;
     private Long projectId;
     private Short column;
     private Short row;
     private Short type;
     private String value;


    // Constructors

    /** default constructor */
    public AbstractMarketCompare() {
    }

	/** minimal constructor */
    public AbstractMarketCompare(Long projectId) {
        this.projectId = projectId;
    }
    
    /** full constructor */
    public AbstractMarketCompare(Long projectId, Short column, Short row, Short type, String value) {
        this.projectId = projectId;
        this.column = column;
        this.row = row;
        this.type = type;
        this.value = value;
    }

   
    // Property accessors

    public Long getIdmarketCompare() {
        return this.idmarketCompare;
    }
    
    public void setIdmarketCompare(Long idmarketCompare) {
        this.idmarketCompare = idmarketCompare;
    }

    public Long getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Short getColumn() {
        return this.column;
    }
    
    public void setColumn(Short column) {
        this.column = column;
    }

    public Short getRow() {
        return this.row;
    }
    
    public void setRow(Short row) {
        this.row = row;
    }

    public Short getType() {
        return this.type;
    }
    
    public void setType(Short type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
   








}