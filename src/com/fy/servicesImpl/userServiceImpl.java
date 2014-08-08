package com.fy.servicesImpl;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;

import com.fy.database.ProjectFileFy;
import com.fy.database.UserFy;
import com.fy.database.UserFyDAO;
import com.fy.services.UserService;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class userServiceImpl implements UserService {

	private UserFyDAO userfyDao;
	private PlatformTransactionManager transactionManager;
	
	
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	public UserFyDAO getUserfyDao() {
		return userfyDao;
	}
	public void setUserfyDao(UserFyDAO userfyDao) {
		this.userfyDao = userfyDao;
	}
	
	public userServiceImpl(){
	}
	
	//�û���¼����
    public String login(String userName,String password){

    	String str = "failed";

    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = facesContext.getExternalContext();
		HttpSession session = (HttpSession) extContext.getSession(true);
    	
    	List ls = userfyDao.findByName(userName);
    	if(ls.size() == 1){
    		UserFy userfy = (UserFy)ls.get(0);
    		if(userfy.getPassword().equals(password)){
    			//�û���Ϣ����session
    			userfy.setPassword(""); //�������
	    		session.setAttribute("user", userfy);
    			str = userfy.getRolename();
    		}    		
    	}
    	
    	return str;
    }
    
    //�û���¼����
    public List GetRoleuser(String rolename){
    	return userfyDao.findByRolename(rolename);
    }
    //�õ������û�
    public List GetAlluser(){
    	return userfyDao.findAll();
    }
    //����û�
    public String AddUser(UserFy user){
    	String ret = "failed";
		try {
			/*
			List ls =userfyDao.findByName(user.getName());
			if(ls.size()>0){
				ret = "failed";
			}else{*/
			List ls = userfyDao.findByName(user.getName());
			if(ls.size() == 0){
				userfyDao.attachDirty(user);
				ret = "success";
			}
		} catch (Exception e) {
			String msg = "Could not Add User " + e.getMessage();
			ret = "failed";
		}
		return ret;
    }
    //ɾ���û�
    public String DelUser(Integer userId){
    	String ret = "success";
		try {
			UserFy user = new UserFy();
			user.setIduserFy(userId);
			userfyDao.getHibernateTemplate().delete(user);
		} catch (Exception e) {
			String msg = "Could not Delete user " + e.getMessage();
			ret = "failed";
		}
		
		return ret;
    }
    
    //�õ��û��ǳ�
    public String GetNikeName(String name){
    	String ret = "success";
		try {
			UserFy user = (UserFy)userfyDao.findByName(name).get(0);
			ret = user.getNikename();
		} catch (Exception e) {
			String msg = "Could not Get user NikeName " + e.getMessage();
			ret = "failed";
		}

		return ret;
    }
    
    public String GetUserName(Integer userId){
    	UserFy user = (UserFy)userfyDao.findById(userId);
    	return user.getName();
    }
}
