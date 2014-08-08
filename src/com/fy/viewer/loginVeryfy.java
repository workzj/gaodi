package com.fy.viewer;

 
import java.util.List;

import org.springframework.web.jsf.FacesContextUtils;

import com.fy.database.UserFy;
import com.fy.services.UserService;
import com.fy.servicesImpl.userServiceImpl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class loginVeryfy implements java.io.Serializable{


	//���ڵ�¼���û���	
    private String name;
    //���ڵ�¼������
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

    
	//���캯��
    public loginVeryfy()  {
    	
    }
    
    //�û���¼����
    public String loginAction(){
    	String ret = userservice.login(name, password);
    	if("failed".equals(ret)){
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ʧ��", "�û������������"));
    	}else{
    		//�õ��û��ǳ�
    		this.name = userservice.GetNikeName(name);
    		//�õ��û���ɫ
    		if( "manager".equals(ret) ){
    			setPassword("���Ա");
			}else if( "shouli".equals(ret) ){
				setPassword("����Ա");
			}else if( "kancha".equals(ret) ){
				setPassword("����Ա");
			}else if( "pinggu".equals(ret) ){
				setPassword("����Ա");
			}else if( "shenpi".equals(ret) ){
				setPassword("����Ա");
			}else if( "worker".equals(ret) ){
				setPassword("�ʹ�Ա");
			}
    	}
    	return ret;
    }
}
