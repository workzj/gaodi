package com.fy.viewer;

 
import java.util.List;
import com.fy.database.UserFy;
import com.fy.services.UserService;
import com.fy.servicesImpl.JBPMUtil;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

public class UserManager implements java.io.Serializable{

	private JBPMUtil jBPMUtil;	
	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
	}

	//������֤����
    private String password2;
    //���û�
    private UserFy user;

	private UserService userservice;
    private List<UserFy> listuser;
	
    //get set function
    public UserService getUserService() {
		return userservice;
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

	public List<UserFy> getListuser() {
		return listuser;
	}

	public void setListuser(List<UserFy> listuser) {
		this.listuser = listuser;
	}

	private void GetAllUser(){
		this.listuser = userservice.GetAlluser();
		for(int i=0;i<listuser.size();i++){
			if( "admin".equals(listuser.get(i).getRolename()) ){
				listuser.remove(i);
				i--;
			}else if( "manager".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("���Ա");
			}else if( "shouli".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("����Ա");
			}else if( "kancha".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("����Ա");
			}else if( "pinggu".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("����Ա");
			}else if( "shenpi".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("����Ա");
			}else if( "worker".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("�ʹ�Ա");
			}
		}
	}
	public void setUserService(UserService newValue) {
		this.userservice = newValue;
		GetAllUser();
	}

	//���캯��
    public UserManager()  {
    	
    }
    
    //����û�
    public String AddUser(){
    	String ret = "";
    	if(password2.equals(user.getPassword())){
        	ret = userservice.AddUser(user);
        	if("success".equals(ret)){
        		//����û���ӳɹ����ʹ���JBPM���û���ϵ
        		if(jBPMUtil.getIdentityService().findGroupById(user.getRolename()) ==  null){
        			jBPMUtil.getIdentityService().createGroup(user.getRolename());
        		}
        		jBPMUtil.getIdentityService().createUser(user.getName(), user.getNikename(), user.getNikename());
        		jBPMUtil.getIdentityService().createMembership(user.getName(), user.getRolename());
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"�û����", "�ɹ���")); 
        	}else{
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"�û����", "ʧ�ܣ�"));
        	}
        	GetAllUser();
        	user = null;
    	}
    	return ret;
    }
    
    //ɾ���û�
    public void DeleteUserAction(ActionEvent event) {
    	Integer userid = (Integer)event.getComponent().getAttributes().get("userid");
    	if(userid != null){
    		jBPMUtil.getIdentityService().deleteUser(userservice.GetUserName(userid));
    		userservice.DelUser(userid);
    		GetAllUser();
    	}
    }

}
