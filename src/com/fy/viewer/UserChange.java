package com.fy.viewer;

 

import org.springframework.web.jsf.FacesContextUtils;

import com.fy.database.UserFy;
import com.fy.services.UserService;
import com.fy.servicesImpl.userServiceImpl;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

public class UserChange implements java.io.Serializable{

	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	//������֤����
    private String password2;
	private String password;
    private String oldpassword;
    //���û�
    private UserFy user;
	private UserService userservice;
	
    //get set function
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
    public UserService getUserService() {
		return userservice;
	}
    public void setUserService(UserService newValue) {
		this.userservice = newValue;
	}
	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public UserFy getUser() {
		if(user == null){
			user = new UserFy();
		}
		return user;
	}

	public void setUser(UserFy user) {
		this.user = user;
	}


	//���캯��
    public UserChange()  {
    	user = (UserFy)session.getAttribute("user");
    	
    }
    
    //�޸��û�
    public String ChangeUser(){
    	String ret = "failed";
    	if(oldpassword.equals(user.getPassword())){
    		//�����������ȷ��
    		if( password != null && password.equals(password2) ){
    			//���������Ͷ�����֤����һ��
    			user.setPassword(password);
    		}
        	ret = userservice.AddUser(user);
    	}
    	return ret;
    }

}
