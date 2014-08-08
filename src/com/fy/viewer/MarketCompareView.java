package com.fy.viewer;

import com.fy.database.MarketCompare;
import com.fy.services.MarketCompareService;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

public class MarketCompareView {
	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	//¹¹Ôìº¯Êý
    public MarketCompareView()  {
    	Integer pid = (Integer)session.getAttribute("projectID");
    	session.removeAttribute("projectID");

    }
}
