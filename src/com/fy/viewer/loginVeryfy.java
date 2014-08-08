package com.fy.viewer;

 
import java.util.List;

import org.springframework.web.jsf.FacesContextUtils;

import com.fy.database.UserFy;
import com.fy.services.UserService;
import com.fy.servicesImpl.userServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class loginVeryfy implements java.io.Serializable{


	//用于登录的用户名	
    private String name;
    //用于登录的密码
    private String password;

	private UserService userservice;
    
	
    public UserService getUserService() {
		return userservice;
	}

	public void setUserService(UserService newValue) {
		this.userservice = newValue;
	}

	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    
	//构造函数
    public loginVeryfy()  {
    	
    }
    
    //用户登录动作
    public String loginAction(){
    	String ret = userservice.login(name, password);
    	if("failed".equals(ret)){
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"失败", "用户名或密码错误！"));
    	}else{
    		//得到用户昵称
    		this.name = userservice.GetNikeName(name);
    		//得到用户角色
    		if( "manager".equals(ret) ){
    			setPassword("审核员");
			}else if( "shouli".equals(ret) ){
				setPassword("受理员");
			}else if( "kancha".equals(ret) ){
				setPassword("勘查员");
			}else if( "pinggu".equals(ret) ){
				setPassword("评估员");
			}else if( "shenpi".equals(ret) ){
				setPassword("审批员");
			}else if( "worker".equals(ret) ){
				setPassword("送达员");
			}
    	}
    	return ret;
    }
}
