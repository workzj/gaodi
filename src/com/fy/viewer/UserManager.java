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

	//二次验证密码
    private String password2;
    //新用户
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
				listuser.get(i).setRolename("审核员");
			}else if( "shouli".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("受理员");
			}else if( "kancha".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("勘查员");
			}else if( "pinggu".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("评估员");
			}else if( "shenpi".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("审批员");
			}else if( "worker".equals(listuser.get(i).getRolename()) ){
				listuser.get(i).setRolename("送达员");
			}
		}
	}
	public void setUserService(UserService newValue) {
		this.userservice = newValue;
		GetAllUser();
	}

	//构造函数
    public UserManager()  {
    	
    }
    
    //添加用户
    public String AddUser(){
    	String ret = "";
    	if(password2.equals(user.getPassword())){
        	ret = userservice.AddUser(user);
        	if("success".equals(ret)){
        		//如果用户添加成功，就创建JBPM的用户关系
        		if(jBPMUtil.getIdentityService().findGroupById(user.getRolename()) ==  null){
        			jBPMUtil.getIdentityService().createGroup(user.getRolename());
        		}
        		jBPMUtil.getIdentityService().createUser(user.getName(), user.getNikename(), user.getNikename());
        		jBPMUtil.getIdentityService().createMembership(user.getName(), user.getRolename());
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"用户添加", "成功！")); 
        	}else{
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"用户添加", "失败！"));
        	}
        	GetAllUser();
        	user = null;
    	}
    	return ret;
    }
    
    //删除用户
    public void DeleteUserAction(ActionEvent event) {
    	Integer userid = (Integer)event.getComponent().getAttributes().get("userid");
    	if(userid != null){
    		jBPMUtil.getIdentityService().deleteUser(userservice.GetUserName(userid));
    		userservice.DelUser(userid);
    		GetAllUser();
    	}
    }

}
