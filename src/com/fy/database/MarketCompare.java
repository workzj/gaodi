package com.fy.database;



/**
 * MarketCompare entity. @author MyEclipse Persistence Tools
 */
public class MarketCompare extends AbstractMarketCompare implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public MarketCompare() {
    }

	/** minimal constructor */
    public MarketCompare(Long projectId) {
        super(projectId);        
    }
    
    /** full constructor */
    public MarketCompare(Long projectId, Short column, Short row, Short type, String value) {
        super(projectId, column, row, type, value);        
    }
   
}
