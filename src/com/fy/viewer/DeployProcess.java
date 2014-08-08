package com.fy.viewer;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.zip.ZipInputStream;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.fy.servicesImpl.JBPMUtil;

public class DeployProcess {

	private JBPMUtil jBPMUtil;	
	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
	}
	
	public DeployProcess(){
	}
	
	public void handleFileUpload(FileUploadEvent event) {  
		 
	}

	public String fileDeploy(FileUploadEvent event) throws IOException{
		
	//String realPath = request.getSession().getServletContext().getRealPath("")+"/WEB-INF/deploy/";
	  try {
		  //��ʼ����JBPM����
		  /**/
		  String deploymentid = jBPMUtil.getProcessEngine().getRepositoryService()
				  .createDeployment().addResourcesFromZipInputStream(new ZipInputStream(event.getFile().getInputstream())).deploy();
				  //addResourceFromInputStream(upFile.getName(), upFile.getInputStream())
				  //.deploy();
		System.out.println("File deploy Successful.");
		FacesMessage msg = new FacesMessage("�ɹ���", event.getFile().getFileName() + " �����ɹ���");  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
		  return "";
	  }
	  catch (Exception ioe) {
		  System.out.println("File deploy Unsuccessful.");
		  FacesMessage msg = new FacesMessage("ʧ�ܣ�", event.getFile().getFileName() + " ����ʧ�ܣ�");  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
		  return "";
	  }
	}
}
