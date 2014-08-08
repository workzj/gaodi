package com.fy.database;



/**
 * AbstractProjectSequence entity provides the base persistence definition of the ProjectSequence entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractProjectSequence  implements java.io.Serializable {


    // Fields    

     private Integer idprojectSequence;


    // Constructors

    /** default constructor */
    public AbstractProjectSequence() {
    }

    

   
    // Property accessors

    public Integer getIdprojectSequence() {
        return this.idprojectSequence;
    }
    
    public void setIdprojectSequence(Integer idprojectSequence) {
        this.idprojectSequence = idprojectSequence;
    }
   








}