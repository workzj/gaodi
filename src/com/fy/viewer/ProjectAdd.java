package com.fy.viewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.primefaces.event.FileUploadEvent;

import com.fy.database.ProjectFileFy;
import com.fy.database.ProjectFy;
import com.fy.database.UserFy;
import com.fy.database.MarketCompare;
import com.fy.services.ProjectFileService;
import com.fy.services.ProjectSeqService;
import com.fy.services.ProjectService;
import com.fy.services.UserService;
import com.fy.services.MarketCompareService;
import com.fy.servicesImpl.JBPMUtil;
import com.fy.utils.file.FileUtil;
import com.fy.utils.xwpf.POIWordDocument;
import com.fy.utils.Num2RMB;
import java.util.ArrayList;
import org.primefaces.event.CellEditEvent;

public class ProjectAdd {
	/*
	 * ��������
	 * */
	private ProjectFy project;
	private ProjectService projectservice;
	private boolean bEnd;			//�Ƿ�ֱ�ӹ鵵
	private JBPMUtil jBPMUtil;
	private int operatetype;		//�������͡�0,add;1,edit;2,kancha;3,pinggu;4,shencha;5,shenpi��6�������
	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	private UserService userservice;

	//��������б�
	private List<ProjectFileFy> listFiles;
	//ͼƬ�������
	private List<ProjectFileFy> listPics;

	//��Ŀ�ļ����ݿ�
	private ProjectFileService projectFileService;
	private String processOpinion;
	//��Ŀ��������
	private ProjectSeqService projectSeqService;
	//��Ŀ������ʩ���Ŵ�����Ҫ���⴦��
	private String[] projectSupport;
	private String[] projectWindow;
	private boolean  allSelectWindow;
	private boolean  allSelectSupport;
	
	//ֱ�ӹ鵵������ĵ������
	private String pcode;
	private String pletter;
	
	//�г��ȽϷ������б�
	private List<MCInstance> instances;		//�ɱȽ�ʵ���б�
	private List<MarketCompare> mcRecords;	//��Ҫ����ļ�¼�����ȷŵ�����б���
	private List<MCInstance> parameters;	//�г��ȽϷ��������
	private MarketCompareService marketCompareService;
	private Map<String,Object> ColumnParameter;
	
	private Double calcPrices;		//���۶����׼�۸�
	private String landLevel;		//���ؼ���
	private Double landPrices;		//���س��ý� ��ÿƽ�ף�
	private Double diyaMoney;		//��ѺȨ��ֵ
	
	/*
	 * GET/SET ����
	 * 
	 * */
	
	public String getPcode() {
		return pcode;
	}


	public String getPletter() {
		return pletter;
	}


	public void setPletter(String pletter) {
		this.pletter = pletter;
	}


	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	
	public Double getCalcPrices() {
		if(calcPrices == null){
			calcPrices = new Double(0);
		}
		return calcPrices;
	}


	public void setCalcPrices(Double calcPrices) {
		this.calcPrices = calcPrices;
	}


	public String getLandLevel() {
		if(landLevel == null){
			landLevel = "��";
		}
		return landLevel;
	}


	public void setLandLevel(String landLevel) {
		this.landLevel = landLevel;
	}


	public Double getLandPrices() {
		if(landPrices == null){
			landPrices = new Double(0);
		}
		return landPrices;
	}


	public void setLandPrices(Double landPrices) {
		this.landPrices = landPrices;
	}


	public Double getDiyaMoney() {
		if(diyaMoney == null){
			diyaMoney = new Double(0);
		}

		return diyaMoney;
	}


	public void setDiyaMoney(Double diyaMoney) {
		this.diyaMoney = diyaMoney;
	}


	public List<ProjectFileFy> getListPics() {
		/*if(listPics == null){
			listPics = new ArrayList<ProjectFileFy>();
		}*/
		return listPics;
	}


	public void setParameters(List<MCInstance> parameters) {
		this.parameters = parameters;
	}

	public MarketCompareService getMarketCompareService() {
		return marketCompareService;
	}

	public void setMarketCompareService(MarketCompareService marketCompareService) {
		this.marketCompareService = marketCompareService;
		if(project == null)
			return;
		//��ʼ��result����
		List<MarketCompare> rets = marketCompareService.GetMCParameter(project.getIdprojectFy(),(short)3);
		for (MarketCompare mc : rets) {
			switch(mc.getColumn()){
				case 1:
					landLevel = mc.getValue();
					break;
				case 2:
					landPrices = Double.parseDouble(mc.getValue());
					break;
				case 3:
					diyaMoney = Double.parseDouble(mc.getValue());
					break;
				default:
					break;
			}
		}
	}

	public void setInstances(List<MCInstance> instances) {
		this.instances = instances;
	}

	public boolean isAllSelectWindow() {
		return allSelectWindow;
	}

	public void setAllSelectWindow(boolean allSelectWindow) {
		this.allSelectWindow = allSelectWindow;
	}

	public boolean isAllSelectSupport() {
		return allSelectSupport;
	}

	public void setAllSelectSupport(boolean allSelectSupport) {
		this.allSelectSupport = allSelectSupport;
	}

	public void setListPics(List<ProjectFileFy> listPics) {
		this.listPics = listPics;
	}
	
	public String getProcessOpinion() {
		return processOpinion;
	}

	public String[] getProjectWindow() {
		if(project != null && project.getHouseWindow() != null){
			projectWindow = project.getHouseWindow().replaceAll("\\[", "")
					.replaceAll("\\]", "").split(", ");
		}
		return projectWindow;
	}

	public void setProjectWindow(String[] projectWindow) {
		if(project != null && projectWindow != null){
			String str = Arrays.toString(projectWindow);
			project.setHouseWindow(str);
		}
		this.projectWindow = projectWindow;
	}

	public String[] getProjectSupport() {
		if(project != null && project.getHouseSupport() != null){
			projectSupport = project.getHouseSupport().replaceAll("\\[", "")
					.replaceAll("\\]", "").split(", ");
		}
		return projectSupport;
	}

	public void setProjectSupport(String[] projectSupport) {
		this.projectSupport = projectSupport;
		if(project != null && projectSupport != null){
			String str = Arrays.toString(projectSupport);
			project.setHouseSupport(str);
		}
	}

	public ProjectSeqService getProjectSeqService() {
		return projectSeqService;
	}

	public void setProjectSeqService(ProjectSeqService projectSeqService) {
		this.projectSeqService = projectSeqService;
	}

	public void setProcessOpinion(String processOpinion) {
		this.processOpinion = processOpinion;
	}

	public List<ProjectFileFy> getListFiles() {
		return listFiles;
	}

	
	public void setListFiles(List<ProjectFileFy> listFiles) {
		this.listFiles = listFiles;
	}
	
	public UserService getUserService() {
		return userservice;
	}


	public ProjectFileService getProjectFileService() {
		return projectFileService;
	}

	public void setProjectFileService(ProjectFileService projectFileService) {
		this.projectFileService = projectFileService;
	}

	public void setUserService(UserService newValue) {
		this.userservice = newValue;
	}
	
	public int getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(int operatetype) {
		this.operatetype = operatetype;
	}
	
	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
	}
	
	public boolean isbEnd() {
		return bEnd;
	}

	public void setbEnd(boolean bEnd) {
		this.bEnd = bEnd;
	}

	public ProjectService getProjectService() {
		return projectservice;
	}

	public ProjectFy getProject() {
		if(project == null){
			project = new ProjectFy();
		}
		return project;
	}

	public void setProject(ProjectFy project) {
		this.project = project;
	}
	
	public void setProjectService(ProjectService newValue) {
		this.projectservice = newValue;

		//��Ĺ����ڷ���ע��֮ǰ������ע��֮�����ٲ�ѯ��
		
		Long pid = (Long)session.getAttribute("projectId");
			
		operatetype = 0;
		if( pid !=null ){
			//�������ĿID���������͵õ���Ŀ��
			ProjectFy project = projectservice.GetProjectByPId(pid);
			listFiles = projectFileService.GetProjectFilesByCode(pid);
			listPics = projectFileService.GetProjectPicsByCode(pid);
			setProject(project);
			session.removeAttribute("projectId");
			
			if ( project.getCurStatus() ==null ){
				operatetype = 0; //���
			}else if("�ֳ�����".equals(project.getCurStatus())){
				operatetype = 2;//����
			}else if("Ԥ��׫д".equals(project.getCurStatus())){
				operatetype = 3;//Ԥ��׫д
			}else if("Ԥ�����".equals(project.getCurStatus())){
				operatetype = 4;//���
			}else if("Ԥ������".equals(project.getCurStatus())){
				operatetype = 5;//����
			}else if("����Ԥ������".equals(project.getCurStatus())){
				operatetype = 7;//����Ԥ������
			}else if("������ʽ����".equals(project.getCurStatus())){
				operatetype = 8;
			}else if("�ʹﱨ��".equals(project.getCurStatus())){
				operatetype = 9;
			}else if("�鵵".equals(project.getCurStatus())){
				operatetype = 6;
			}
		}
	}
	
	/*
	 *
	 * ��Ӧ���������ܺ���
	 * 
	*/
	
	public ProjectAdd(){		
		//��ʼ�����ղ���
		ColumnParameter = new HashMap();
		ColumnParameter.put("location", 0);
		ColumnParameter.put("sellDate", 1);
		ColumnParameter.put("sellStatus", 2);
		ColumnParameter.put("price", 3);
		ColumnParameter.put("businessdegree", 4);
		ColumnParameter.put("landscape", 5);
		ColumnParameter.put("traffic", 6);
		ColumnParameter.put("environment", 7);
		ColumnParameter.put("base", 8);
		ColumnParameter.put("construction", 9);
		ColumnParameter.put("unit", 10);
		ColumnParameter.put("light", 11);
		ColumnParameter.put("decoration", 12);
		ColumnParameter.put("buildDate", 13);
		ColumnParameter.put("community", 14);
		ColumnParameter.put("face", 15);
		ColumnParameter.put("layer", 16);
		ColumnParameter.put("support", 17);
	}

	//��������
	private String StarProcess(){
		//�õ���ǰ�û�
		UserFy userfy = (UserFy)session.getAttribute("user");

		if(userfy == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"�½�����ʧ�ܣ���ǰ�û����Ϸ���", "ʧ�ܣ�"));
			return "error";
		}
		
		//��������ʱ�䣬��Ŀ��ʼʱ��
		project.setShouliDate(new java.util.Date());
		String ret = projectservice.SaveOrUpdateProject(project);
		
		if("failed".equals(ret)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"�½�����ʧ�ܣ���������ظ���", "ʧ�ܣ�"));
			return "error";
		}
		
		//����process�ı���
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("user", userfy.getName());

		//��������
		ProcessInstance processInstance = jBPMUtil.getExecutionService().
				startProcessInstanceByKey("test1",variables);
		
		jBPMUtil.getExecutionService()
			.setVariable(processInstance.getId(),"processInstanceId",processInstance.getId());
		jBPMUtil.getExecutionService()
			.createVariable(processInstance.getId(),"opinion","��������",true);
		
	
		//���̵�һ����ҵ������
		//�õ���ǰ����ID
		String taskid = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(processInstance.getId()).uniqueResult().getId();

		//�ĵ���������ʵ��ID
		project.setProcessInstanceId(processInstance.getId());
		//�����ĵ�
		project.setProjectComment(processOpinion);

		projectservice.SaveOrUpdateProject(project);
		
		project = null;
				
		//����������ת
		if(bEnd){
			//ֱ�ӽ������鵵
			jBPMUtil.getTaskService().addTaskComment(taskid, "��ֱ�ӹ鵵��");
			jBPMUtil.getTaskService().completeTask(taskid,"to �鵵");
		}else{
			jBPMUtil.getTaskService().addTaskComment(taskid, "���ύ�ֳ����顿");
			jBPMUtil.getTaskService().completeTask(taskid,"to �ֳ�����");
		}

		return ret;
	}
	
	//���水ť
	public String InsertProject(){
		if(operatetype == 0){
			//0������ҵ������ڵ�
			String prjcode;
			
			if(bEnd){
				//ֱ�ӹ鵵�Ļ���Ҫ������������
				prjcode = pcode;
				this.project.setCurStatus("ֱ�ӹ鵵");
			}else{
				if("".equals(pcode) || pcode==null ){
					//������Ŀ��� �� 2013 03 0000 A~G
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");  
					prjcode=sdf.format(new java.util.Date());
					Integer index = projectSeqService.GetProjectSeq();
					if(index <= 9999){
						String str = String.format("%04d", index);
						prjcode = prjcode + str + pletter;
					}else{
						prjcode = "over numbers";
					}
				}else{
					prjcode = pcode;//������ŵĻ�
				}
				this.project.setCurStatus("�ֳ�����");
			}
			if("over numbers".equals(prjcode)){
				//�������9999������Ϊ��ID����������ټ����ˡ�
				return "over numbers";
			}
			this.project.setProjectCode(prjcode);
			return StarProcess();
		}
		else{
			//������������̽ڵ㣬������Ǳ��水ť��
			String str = projectservice.SaveOrUpdateProject(project);
			UserFy userfy = (UserFy)session.getAttribute("user");
			if("kancha".equals(userfy.getRolename()) )
				return "Kancha";
			else if("pinggu".equals(userfy.getRolename()) )
				return "pinggu";
			else
				return str;
		}
	}
	
	//�������
	public String KanchaComplete(){
		String ret = "error";
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("�ֳ�����").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "��������ɡ�    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to Ԥ��׫д");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("Ԥ��׫д");
			projectservice.SaveOrUpdateProject(project);
			ret = "Kancha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//�������һ��
		//clear();

		return ret;
	}
	
	//�������
	public String PingguComplete(){
		String ret = "error";
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("Ԥ��׫д").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(taskid,"��������ɡ�    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to Ԥ�����");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("Ԥ�����");
			projectservice.SaveOrUpdateProject(project);
			ret = "pinggu";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//�������һ��
		//clear();

		return ret;
	}
	
	//���ͨ��
	public String ShenchaPass(){
		String ret = "error";
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("Ԥ�����").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "�����ͨ����    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to Ԥ������");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("Ԥ������");
			projectservice.SaveOrUpdateProject(project);
			ret = "shencha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//�������һ��
		//clear();

		return ret;
	}
	
	//��鲻ͨ��
	public String ShenchaUnpass(){
		String ret = "error";
		UserFy userfy = (UserFy)session.getAttribute("user");
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("Ԥ�����").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				//�Ƚ�������Ȼ����������ת
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "����鲻ͨ����    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to Ԥ��׫д");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("Ԥ��׫д");
			projectservice.SaveOrUpdateProject(project);
			ret = "shencha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//�������һ��
		//clear();

		return ret;
	}
	//����ͨ��
	public String ShenpiPass(){	
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("Ԥ������").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "������ͨ����    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to ����Ԥ������");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("����Ԥ������");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		return "shenpi";
	}
	//������ͨ��
	public String ShenpiUnpass(){
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("Ԥ������").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "��������ͨ����    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to Ԥ��׫д");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("Ԥ��׫д");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "shenpi";
	}
	
	//����ͼƬ�༭
	public String ExportPicWord(){
		POIWordDocument pdoc = new POIWordDocument();
		Map<String,String> var = new HashMap<String,String>();
		if(listPics != null){
			for(ProjectFileFy obj:listPics){
				var.put(obj.getName(),obj.getFilepath());
			}
		}

		try
		{
			ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
			String realPath = servletContext.getRealPath("") + "\\pics_out.docx";
			pdoc.ReplaceAll("\\doc\\pics.docx",realPath,var);
			FileUtil.downloadFile("pics_out.docx","pics_out.docx");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "export";
		
	}
	
	//���Ӻ��ʵķ���װ�����ζ��ʣ��齺�ᡢͿ���ࣺˢ����Ƭ������	PVC��ˮ����桢��Ϊ����ֽ������ש��ذ�֮�ࣺ�̣�
	//����Ĳ���ֻ����װ�޵Ĳ��ϡ�
	private String AddPerDecoration(String meterial){
		StringBuffer ret = new StringBuffer("");
		if("�ذ�ש".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("ľ�ذ�".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("�ذ��".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("ˮ��ɰ������".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("Ϳ��".equals(meterial)){
			ret.append("ˢ").append(meterial);
		}else if("�齺��".equals(meterial)){
			ret.append("ˢ").append(meterial);
		}else if("��ֽ".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("�ڲ�".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("ˮ��ɰ��".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("Ϳ���ְ�".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("����".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("�����ذ�ש".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("������".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("ˮ�����".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("��Ƭ".equals(meterial)){
			ret.append("��").append(meterial);
		}else if("PVC".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("ˮ��ɰ����ë".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("ˮ��ɰ��Ĩƽ".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}else if("ʯ���ְ�".equals(meterial)){
			ret.append("Ϊ").append(meterial);
		}
		
		return ret.toString();
	}
	
	//��ʽ��װ��״̬
	private String FormatDecorationStatus(){
		StringBuffer ret = new StringBuffer("");
		//[1, 2, 3, 4, 5, 6, 7, 8]
		for(int i=0;i<projectWindow.length;i++){
			if( "1".equals(projectWindow[i]) ){
				ret.append("�뻧����װ��������ţ�");
			}else if( "2".equals(projectWindow[i]) ){
				ret.append("�뻧����װ˫������ţ�");
			}else if( "3".equals(projectWindow[i]) ){
				ret.append("�뻧����װľ�ţ�");
			}else if( "4".equals(projectWindow[i]) ){
				ret.append("ľ����");
			}else if( "5".equals(projectWindow[i]) ){
				ret.append("�ִܸ���");
			}else if( "6".equals(projectWindow[i]) ){
				ret.append("���Ͻ𴰣�");
			}else if( "7".equals(projectWindow[i]) ){
				ret.append("�ִ���");
			}else if( "8".equals(projectWindow[i]) ){
				ret.append("��������");
			}
		}
		
		if (project.getHouseYangtai() == null){
			ret.append("����̨");
		}else if (project.getHouseYangtai() == 1){
			ret.append("��̨�ѷ�ա�");
		}else if (project.getHouseYangtai() == 2){
			ret.append("��̨δ��ա�");
		}else {
			ret.append("����̨��");
		}
		
		if(project.getHouseMaopiDimian() == null || "0".equals(project.getHouseMaopiDimian()) || 
				"δѡ��".equals(project.getHouseMaopiDimian()) || "".equals(project.getHouseMaopiDimian()) ){
			//�������ë����
			
			//����
			if(project.getHouseKetingDimian() != null){
				ret.append("���ڿ�������").append(AddPerDecoration(project.getHouseKetingDimian())).append("��ǽ��")
							.append(AddPerDecoration(project.getHouseKetingQiangmian()))
							.append("������").append(AddPerDecoration(project.getHouseKetingDingpeng())).append("��");
			}
			
			//����
			if(project.getHouseCantingDimian() != null){
				ret.append("���ڲ�������").append(AddPerDecoration(project.getHouseCantingDimian())).append("��ǽ��")
							.append(AddPerDecoration(project.getHouseCantingQiangmian()))
							.append("������").append(AddPerDecoration(project.getHouseCantingDingpeng())).append("��");
			}

			//����
			if(project.getHouseWoshiDimian() != null){
				ret.append("���ҵ���").append(AddPerDecoration(project.getHouseWoshiDimian())).append("��ǽ��")
							.append(AddPerDecoration(project.getHouseWoshiQiangmian()))
							.append("������").append(AddPerDecoration(project.getHouseWoshiDingpeng())).append("��");
			}
			
			//����
			if(project.getHouseChufangDimian() != null){
				ret.append("��������").append(AddPerDecoration(project.getHouseChufangDimian())).append("��ǽ��")
							.append(AddPerDecoration(project.getHouseChufangQiangmian()))
							.append("������").append(AddPerDecoration(project.getHouseChufangDingpeng())).append("��");
			}
			
			//������
			if(project.getHouseWeiDimain() != null){
				ret.append("���������").append(AddPerDecoration(project.getHouseWeiDimain())).append("��ǽ��")
							.append(AddPerDecoration(project.getHouseWeiQiangmian()))
							.append("������").append(AddPerDecoration(project.getHouseWeiDingpeng())).append("��");
			}
			
			ret.deleteCharAt(ret.length()-1);
			
		}else {
			//�����ë����
			ret.append("�÷�Ϊë����������").append(AddPerDecoration(project.getHouseMaopiDimian()))
			   .append("��ǽ��").append(AddPerDecoration(project.getHouseMaopiQiangmian()))
			   .append("������").append(AddPerDecoration(project.getHouseMaopiDingpeng()));
		}
		ret.append("��������ʩ��");
		for(int i=0;i<projectSupport.length;i++){
			if( "1".equals(projectSupport[i]) ){
				ret.append((i==0) ? "ˮ" : "��ˮ" );
			}else if( "2".equals(projectSupport[i]) ){
				ret.append((i==0) ? "��" : "����" );
			}else if( "3".equals(projectSupport[i]) ){
				ret.append((i==0) ? "��Ȼ��" : "����Ȼ��" );
			}else if( "4".equals(projectSupport[i]) ){
				ret.append((i==0) ? "�绰" : "���绰" );
			}else if( "5".equals(projectSupport[i]) ){
				ret.append((i==0) ? "��·" : "����·" );
			}else if( "6".equals(projectSupport[i]) ){
				ret.append((i==0) ? "���" : "�����" );
			}else if( "7".equals(projectSupport[i]) ){
				ret.append((i==0) ? "����" : "������" );
			}else if( "8".equals(projectSupport[i]) ){
				ret.append((i==0) ? "�Ž�ϵͳ" : "���Ž�ϵͳ" );
			}else if( "9".equals(projectSupport[i]) ){
				ret.append((i==0) ? "����յ�" : "������յ�" );
			}else if( "10".equals(projectSupport[i]) ){
				ret.append((i==0) ? "���ʽ�յ�" : "�����ʽ�յ�" );
			}else if( "11".equals(projectSupport[i]) ){
				ret.append((i==0) ? "���й�ů" : "�����й�ů" );
			}else if( "12".equals(projectSupport[i]) ){
				ret.append((i==0) ? "��ů" : "����ů" );
			}else if( "13".equals(projectSupport[i]) ){
				ret.append((i==0) ? "��ů" : "����ů" );
			}else if( "14".equals(projectSupport[i]) ){
				ret.append((i==0) ? "�ڹ�" : "���ڹ�" );
			}else if( "15".equals(projectSupport[i]) ){
				ret.append((i==0) ? "�Զ�����" : "���Զ�����" );
			}else if( "16".equals(projectSupport[i]) ){
				ret.append((i==0) ? "����˨" : "������˨" );
			}
		}
		ret.append("�ȡ�");
		return ret.toString();
	}
	
	//��ʽ�� ������״
	private String FormatKanchaCurrentstatus(){
		if(project == null){
			return "";
		}
		
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer("    1�����۶���λ��");
		ret.append(project.getHouseXy()).append("��Ϊ").append(project.getWuyeAt()).append("С����С����ģ");
				
		if(project.getXiaoquExtent() == null){
			ret.append("δ֪��");
		}else if(project.getXiaoquExtent() == 1){
			ret.append("��С��");
		}else if(project.getXiaoquExtent() == 2){
			ret.append("�еȣ�");
		}else if(project.getXiaoquExtent() == 3){
			ret.append("�ϴ�");
		}
		if(project.getWuyeManager() == null){
			ret.append("��ҵδ֪��");
		}else if(project.getWuyeManager() == 1){
			ret.append("��λ��ҵ����");
		}else if(project.getWuyeManager() == 2){
			ret.append("רҵ��ҵ����");
		}else {
			ret.append("����ҵ����");
		}
		
		if(project.getHouseLvhua() == null){
			ret.append("���̻���");
		}else if(project.getHouseLvhua() == 1){
			ret.append("���̻���");
		}else if(project.getHouseLvhua() == 2){
			ret.append("�̻�һ�㡣");
		}else if(project.getHouseLvhua() == 3){
			ret.append("�̻��Ϻá�");
		}else if(project.getHouseLvhua() == 4){
			ret.append("�̻����á�");
		}

		ret.append(newLine);
		
		ret.append("    2��������" + project.getHouseWaiqiang() + "�����۶�����Ϊ");
		if(project.getHouseFace() == null){
			ret.append("δ֪��");
		}else{
			switch(project.getHouseFace()){
				case 1:
					ret.append("������");
					break;
				case 2:
					ret.append("�ϳ���");
					break;
				case 3:
					ret.append("������");
					break;
				case 4:
					ret.append("������");
					break;
				case 5:
					ret.append("���ϳ���");
					break;
				case 6:
					ret.append("��������");
					break;
				case 7:
					ret.append("���ϳ���");
					break;
				case 8:
					ret.append("��������");
					break;
				case 9:
					ret.append("�ϱ�����");
					break;
				case 10:
					ret.append("��������");
					break;
				default:
						break;
			}
		}

		ret.append(newLine);
		
		if(project.getHouseAtLayer() != null && project.getHouseLayers()!=null ){
			ret.append("    3�����۶���λ�ڵ�").append(project.getHouseAtLayer().toString())
			   .append("�㣬�ܲ���").append(project.getHouseLayers().toString())
			   .append("�㣬������");
		}else{
			ret.append("    3�����۶���λ�ڵ�null�㣬�ܲ���null�㣬������");
		}
		
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy��");
		if(project.getHouseDate() != null){
			ret.append(fmtrq.format(project.getHouseDate()));
		}else{
			ret.append("δ֪");
		}
		
		if(project.getHouseConstruction() == null){
			ret.append("��δ֪");
		}else{
			switch(project.getHouseConstruction()){
				case 1:
					ret.append("���ֻ�ṹ��");
					break;
				case 2:
					ret.append("����Ͻṹ��");
					break;
				case 3:
					ret.append("��שľ�ṹ��");
					break;
				case 4:
					ret.append("���ֽṹ��");
					break;
				default:
						break;
			}
		}
		
		ret.append(FormatDecorationStatus());

		return ret.toString();
	}
	//��ʽ�� ���ݱ�����������
	private String FormatDiyatishi(){
		if(project == null){
			return "";
		}
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer("    1�����۶���״���ͷ��ز��г�״����ʱ��仯�Է��ز���Ѻ��ֵ���ܲ���Ӱ�죬�ڹ��۶���ʵ�Ｐ�������ز��������𺦣�������ά��ʹ�ã���δ���ӷ��������ܳ�����ز��г�û�д�Ĳ���������£�Ԥ�ƹ��۱���ʹ����Ч���ڣ����ز���Ѻ��ֵ���������ȶ��� ");
		ret.append(newLine);
		ret.append("    2���Ե�Ѻ�ڼ���ܲ����ķ��ز��Ŵ����գ����۱���ʹ����Ӧ�����ע: ���۶�����ܻ����ڷ�����״����������仯�Լ����ز��г��۸񲨶������ز�˰�ѵ�����ԭ���·��ز���Ѻ��ֵ����");
		ret.append(newLine);
		ret.append("    3�����۱���ʹ����Ӧ����ʹ��������ֵ,��ע���÷��ز�ʱ���ٱ��ּ����õ�Ӱ��,�����ز���Ѻ���۱�����ߺ�����Ѻ�Ǽ�֮��,�Ƿ����ַ��������ܳ�Ȩ����");
		ret.append(newLine);
		ret.append("    4�����ڻ����ڷ��ز��г��۸�仯�ȽϿ�ʱ�Է��ز���Ѻ��ֵ������������");
		ret.append(newLine);
		ret.append("    5�����۶���Ϊ���ز�ת�õ�Ѻ������ز�������ɺ󣬷������ʱ�Ϊ��Ʒ������Ӧ������̯�������ʼ�ת��Ϊ���г��á�");
		return ret.toString();
	}
	//��ʽ�� ���ݱ�����������
	private String FormatBianxian(){
		if(project == null){
			return "";
		}
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer();
		//String ret = "";
		switch(project.getHouseUsing()){
			case 0:
				ret.append("    1�����۶���ĺϷ���;δ֪������ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
			break;
			case 1:
				ret.append("    1�����۶���ĺϷ���;Ϊ����סլ������ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
				break;
			case 2:
				ret.append("    1�����۶���ĺϷ���;Ϊ��ҵ���񣬶���ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
				break;
			case 3:
				ret.append("    1�����۶���ĺϷ���;Ϊ�칫������ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
				break;
			case 4:
				ret.append("    1�����۶���ĺϷ���;Ϊ�ǳ���סլ������ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
				break;
			case 5:
				ret.append("    1�����۶���ĺϷ���;Ϊ�Ƶ�ʽ��Ԣ������ʹ���Խ�ǿ���޷��ָ�ת�ã����۶���������ҵ����");
				break;
			default:
					break;
		}
		
		if(project.getWuyeManager() == 0 || project.getWuyeManager() == 3){
			ret.append("һ�㣬");
		}else{
			ret.append("�Ϻã�");
		}
		
		if(project.getHouseArea() < 60){
			ret.append("�����С");
		}else if(project.getHouseArea() < 144){
			ret.append("�������");
		}else{
			ret.append("����ϴ�");
		}
		ret.append("�����ڱ�������һ�㡣");
		ret.append(newLine);
		ret.append("    2���ٶ��ڼ�ֵʱ���������߱������۶���ʱ������ڶ�����ǿ�ƴ��֡�Ǳ�ڹ���Ⱥ���ܵ����Ƽ������ų�����Ӱ�죬�����ʵ�ֵļ۸�һ��ȹ����г��۸�Ҫ�ͣ�������Ա����Ŀǰ���Ʒ��ز������۸��������г��۸�ı�����ϵ��ȷ�������ڹ���ʱ���������߱���ʱ�����ʵ�ֵļ۸����������г���ֵ�ı���Ϊ80%��");
		ret.append(newLine);
		ret.append("    3�����÷��ز�ʱ������ֵ�ʱ�䳤���Լ����á�˰������ࡢ������峥˳���봦�÷�ʽ��Ӫ�����Ե������йء�һ��˵������������ʽ���÷��ز�ʱ������ʱ��϶̣������ּ۸�һ��ϵͣ����ֳɱ��ϸߣ�Ҫ֧������Ӷ��Ӫҵ˰�����������ѡ�");
		ret.append(newLine);
		
		return ret.toString();
	}
	
	//�õ�����Ա NIKE NAME
	private String GetKanchaName(){
		List<HistoryActivityInstance> haInstance = jBPMUtil.getHistoryService()  
			    .createHistoryActivityInstanceQuery().processInstanceId(project.getProcessInstanceId()).list();
		List<HistoryTask> ht = jBPMUtil.getHistoryService()
			    .createHistoryTaskQuery().executionId(project.getProcessInstanceId()).list();
		
		for(int i=0;i<haInstance.size();i++){
			HistoryActivityInstanceImpl tes = (HistoryActivityInstanceImpl)haInstance.get(i);
			if("�ֳ�����".equals(tes.getActivityName())){
				String assignee = ht.get(i).getAssignee();
				return userservice.GetNikeName(assignee);
			}
		}

		return "";
	}
	
	//�õ�����Ա NIKE NAME
	private String GetPingguName(){
		List<HistoryActivityInstance> haInstance = jBPMUtil.getHistoryService()  
			    .createHistoryActivityInstanceQuery().processInstanceId(project.getProcessInstanceId()).list();
		List<HistoryTask> ht = jBPMUtil.getHistoryService()
			    .createHistoryTaskQuery().executionId(project.getProcessInstanceId()).list();
		
		for(int i=0;i<haInstance.size();i++){
			HistoryActivityInstanceImpl tes = (HistoryActivityInstanceImpl)haInstance.get(i);
			if("Ԥ��׫д".equals(tes.getActivityName())){
				String assignee = ht.get(i).getAssignee();
				return userservice.GetNikeName(assignee);
			}
		}

		return "";
	}
	
	//������ʽ�������
	public void ExportResult(){
		//String newLine = System.getProperty("line.separator");
		POIWordDocument pdoc = new POIWordDocument();
		Map<String,String> var = new HashMap<String,String>();
		
		var.put("projectCode","ԥ֣�ߵط�����[2014]"+project.getProjectCode().substring(project.getProjectCode().length()-7));
		var.put("sellerName", project.getSellerName());
		var.put("houseXy", project.getHouseXy());
		var.put("houseNumber",project.getHouseNumber());

		if(project.getHouseArea()!=null){
			var.put("houseArea",project.getHouseArea().toString());
		}
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy��MM��dd��");
		if(project.getKanchaDate() != null)
		{
			Date date = new Date();
			var.put("curDateex",fmtrq.format(project.getKanchaDate()));
			var.put("pingguriqi",fmtrq.format(project.getKanchaDate())+"��"+fmtrq.format(date));
		}
		
		if(project.getHouseEvaluateSingle() != null)
			var.put("houseEvaluateMoneyper",project.getHouseEvaluateSingle().toString());
		
		if(project.getHouseEvaluateTotal() != null)
		{
			float totalMoney = project.getHouseEvaluateTotal().floatValue() / 10000;
			totalMoney = (float)(Math.round(totalMoney*100))/100;
			var.put("houseEvaluateMoneyAll",String.valueOf(totalMoney));
		}
		
		if(listPics != null){
			for(ProjectFileFy obj:listPics){
				var.put(obj.getName(),obj.getFilepath()+obj.getName());
			}
		}

		try
		{
			//���ձ�������ĵ�����
			StringBuffer filename = new StringBuffer(project.getProjectCode());
			filename.append(".docx");
			
			ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
			String realPath = servletContext.getRealPath("") + "/result_out.docx";
			pdoc.ReplaceAll("/doc/result.docx",realPath,var);
			FileUtil.downloadFile("result_out.docx",filename.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//return userfy.getRolename();
	}
	
	//����Ԥ������
	public void ExportYPWord(){
		String newLine = System.getProperty("line.separator");
		POIWordDocument pdoc = new POIWordDocument();
		Map<String,String> var = new HashMap<String,String>();
		
		//����ǹ�����Ļ��������ʽ��Ҫ��������
		if("������".equals(project.getProjectSource())){
			var.put("cell01", "������");
			var.put("sellerTel", project.getBuyerName());
			
		}else{
			var.put("cell01", "��ϵ��ʽ");
			var.put("sellerTel", project.getSellerTel());
		}
		
		var.put("projectCode", project.getProjectCode().substring(project.getProjectCode().length()-7));
		var.put("sellerName", project.getSellerName());
		
		var.put("houseXy", project.getHouseXy());
		var.put("houseNumber",project.getHouseNumber());
		var.put("wuyeAt",project.getWuyeAt());
		var.put("ownerName", ( (project.getOwnerName() == null ) ? "" : project.getOwnerName()));
		var.put("ownerNumber", ( (project.getOwnerNumber() == null ) ? "" : project.getOwnerNumber()));
		//����
		StringBuffer houseUnit = new StringBuffer();
		houseUnit.append( (project.getHouseUnit1() == null ) ? "" : project.getHouseUnit1().toString()+"�� ");
		houseUnit.append( (project.getHouseUnit2() == null ) ? "" : project.getHouseUnit2().toString()+"�� ");
		houseUnit.append( (project.getHouseUnit3() == null ) ? "" : project.getHouseUnit3().toString()+"�� ");
		houseUnit.append( (project.getHouseUnit4() == null ) ? "" : project.getHouseUnit4().toString()+"�� ");
		var.put("houseUnit",houseUnit.toString());
		
		var.put("houseLayers", ( (project.getHouseLayers() == null ) ? "" : project.getHouseLayers().toString()+"��"));
		var.put("houseAtLayer", ( (project.getHouseAtLayer() == null ) ? "" : project.getHouseAtLayer().toString()+"��"));
		
		switch(project.getHouseConstruction()){
			case 1:
				var.put("houseConstruction","�ֻ�");
				break;
			case 2:
				var.put("houseConstruction","���");
				break;
			case 3:
				var.put("houseConstruction","שľ");
				break;
			case 4:
				var.put("houseConstruction","��");
				break;
			default:
					break;
		}
		if(project.getHouseArea()!=null){
			var.put("houseArea",project.getHouseArea().toString()+"ƽ����");
		}
		
		switch(project.getHouseUsing()){
		case 1:
			var.put("houseUsing","����סլ");
			break;
		case 2:
			var.put("houseUsing","��ҵ����");
			break;
		case 3:
			var.put("houseUsing","�칫");
			break;
		case 4:
			var.put("houseUsing","�ǳ���סլ");
			break;
		case 5:
			var.put("houseUsing","�Ƶ�ʽ��Ԣ");
			break;
		default:
				break;
		}
		
		SimpleDateFormat fmtrq1 = new SimpleDateFormat("yyyy��");
		if(project.getHouseDate() != null)
			var.put("houseDate",fmtrq1.format(project.getHouseDate()));
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy��MM��dd��");
		if(project.getProjectDate() != null)
			var.put("projectDate",fmtrq.format(project.getProjectDate()));
		
		//��������
		StringBuffer kanchaDate = new StringBuffer("����Ŀ�ģ�Ϊȷ�����ز���Ѻ�������ṩ�ο����ݶ��������ز���Ѻ��ֵ");
		//String kanchaDate = "����Ŀ�ģ�Ϊȷ�����ز���Ѻ�������ṩ�ο����ݶ��������ز���Ѻ��ֵ";
		kanchaDate.append(newLine);
		if(project.getKanchaDate() == null){
			kanchaDate.append("��ֵʱ�㣺 δ֪");
		}else{
			kanchaDate.append("��ֵʱ�㣺 ").append(fmtrq.format(project.getKanchaDate()));
		}
		
		kanchaDate.append(newLine);
		kanchaDate.append("���۷������ȽϷ�");
		
		var.put("curDate",kanchaDate.toString());
		
		//����ʦ������Աǩ��
		UserFy userfy = (UserFy)session.getAttribute("user");
		StringBuffer pgPerson = new StringBuffer("���������������ߵؾ��䷿�ز������������ι�˾����ʡ�ֹ�˾");
		pgPerson.append(newLine);
		pgPerson.append("����ʦ��  ��ï��   ��ͬ��");
		pgPerson.append(newLine);
		pgPerson.append("����Ա��  ");
		pgPerson.append(GetPingguName());
		pgPerson.append(newLine);
		pgPerson.append("������Ա��").append(GetKanchaName());
		var.put("curPerson",pgPerson.toString());
		
		switch(project.getHouseProperty()){
		case 1:
			var.put("houseProperty","��Ʒ��");
			break;
		case 2:
			var.put("houseProperty","���ʷ�");
			break;
		case 3:
			var.put("houseProperty","���ķ�");
			break;
		case 4:
			var.put("houseProperty","���ӹ���");
			break;

		default:
				break;
		}
		
		switch(project.getHouseLandProperty()){
		case 1:
			var.put("houseLandProperty","���г���");
			break;
		case 2:
			var.put("houseLandProperty","���л���");
			break;
		case 3:
			var.put("houseLandProperty","��������");
			break;
		default:
				break;
		}

		var.put("houseUse",project.getHouseUse());
		var.put("kanchaCurrentStatus",FormatKanchaCurrentstatus());
		
		StringBuffer kanchaMarketarea = new StringBuffer(houseMarketArea());
		kanchaMarketarea.append(newLine)
				.append("    ���۶��������������Ʒ��ز��ɽ�����").append(project.getReferMin()).append(" ~ ")
				.append(project.getReferMax()).append("Ԫ/ƽ����֮�䣬���ۺϷ�����Ƚϣ����۶�����ٱ��ֲο���Ϊ")
				.append(project.getReferTotal()).append("��Ԫ��");
		
		var.put("kanchaMarketarea",kanchaMarketarea.toString());
		
		if(project.getHouseEvaluateTotal()!=null && project.getHouseEvaluateSingle()!=null){
			float totalMoney = project.getHouseEvaluateTotal().floatValue() / 10000;
			StringBuffer housemoney = new StringBuffer();
			totalMoney = (float)(Math.round(totalMoney*100))/100;
			housemoney.append("������ֵ����").append(totalMoney).append("��Ԫ            ���ۣ���  ")
			          .append(project.getHouseEvaluateSingle()).append("Ԫ/�O");
			
			var.put("houseEvaluateMoney",housemoney.toString());
		}
		var.put("yupingBianxian",FormatBianxian());
		var.put("yupingDiyatishi",FormatDiyatishi());
		
		if(listPics != null){
			for(ProjectFileFy obj:listPics){
				var.put(obj.getName(),obj.getFilepath()+obj.getName());
			}
		}

		try
		{
			//���ձ�������ĵ�����
			StringBuffer filename = new StringBuffer(project.getProjectCode());
			filename.append(".docx");
			
			ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
			String realPath = servletContext.getRealPath("") + "/yuping_out.docx";
			pdoc.ReplaceAll("/doc/sample.docx",realPath,var);
			FileUtil.downloadFile("yuping_out.docx",filename.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//return userfy.getRolename();
	}
	
    private void createDirs(File dir) throws IOException {
        if (dir == null || dir.exists()) { return; }
        createDirs(dir.getParentFile());
        dir.mkdir();
    }
	
	//�����ļ���Դ洢·��
	private String GetFileStoragePath(Long projectCode,int nType) throws IOException  {
		//�ȵõ���Ŀ¼
		ServletContext servletContext = (ServletContext) FacesContext  
                .getCurrentInstance().getExternalContext().getContext();
		String fileDirs =servletContext.getRealPath("");
		//request.getSession().getServletContext().getRealPath("");
		String userDirs = "/data/"+projectCode.toString() + "/";
		if(nType == 0){
			userDirs += "files/";
		}else if(nType == 1){
			userDirs += "pictures/";
		}
		
		File saveFile = new File(fileDirs+userDirs); //�����ļ�
		// ����Ŀ¼
		if (!saveFile.exists()) {
			createDirs(saveFile); // �ݹ鴴��Ŀ¼
			//saveFile.createNewFile(); // ������ǰ�ļ�
    	  //saveFile.mkdirs();// Ŀ¼�����ڵ�����£�����Ŀ¼��    
		}
		return userDirs;
	}
	//�ϴ��ļ�
	public void UploadFile(FileUploadEvent event){
		if(project == null)
			return ;
		 try {
			 String suffix = FilenameUtils.getExtension(event.getFile().getFileName()).toLowerCase();

			 int fileType=0; //Ĭ��Ϊ�ļ�
			 if(	"jpg".equals(suffix) || 
					"jpeg".equals(suffix) || 
					"gif".equals(suffix) ||
					"bmp".equals(suffix) || 
					"png".equals(suffix) ){
				 fileType = 1;
			 }
			 /*  �����ϴ� */
			 String userPath = GetFileStoragePath(project.getIdprojectFy(),fileType);
			 ServletContext servletContext = (ServletContext) FacesContext  
		                .getCurrentInstance().getExternalContext().getContext();
			 String realPath = servletContext.getRealPath("") + userPath;
			 InputStream stream = event.getFile().getInputstream();
			 String fullPath = event.getFile().getFileName();
			 String fileName = fullPath.substring(fullPath.lastIndexOf("\\")+1);  
			 realPath = realPath + fileName;
			 File saveFile = new File(realPath);// �����ļ�
		     //�����ļ�
			 FileOutputStream fos = new FileOutputStream(saveFile);
			 byte[] b = new byte[1024];  
		     int i = 0;
		     while ((i = stream.read(b)) > 0) {  
	            fos.write(b, 0, i);  
		     }
			 
			 fos.flush();
			 fos.close();
			 stream.close();
			 
			 //д�����ݿ��¼
			 ProjectFileFy pgFile = new ProjectFileFy();
			 pgFile.setProjectcode(project.getIdprojectFy());
			 pgFile.setFilepath(userPath);
			 pgFile.setFilesize((int)event.getFile().getSize());
			 pgFile.setName(fileName);
			 pgFile.setType(fileType);
			 Date date = new Date();
			 pgFile.setUploadTime(new Timestamp(date.getTime()));
			 projectFileService.AddProjectFile(pgFile);

			 if(fileType == 0){
				 listFiles = projectFileService.GetProjectFilesByCode(pgFile.getProjectcode());
			 }else{
				 listPics = projectFileService.GetProjectPicsByCode(pgFile.getProjectcode());
			 }
			 
			 FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " �ϴ��ɹ���");  
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		  }
		  catch (Exception ioe) {
			  FacesMessage msg = new FacesMessage("Failed", event.getFile().getFileName() + " �ϴ�ʧ�ܣ�");  
		      FacesContext.getCurrentInstance().addMessage(null, msg);
		  }
	}

	//��������
	public void DownloadFileAction(ActionEvent event) {
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("file");

		String filePath = "/data/"+file.getProjectcode().toString() + "/files/";
		FileUtil.downloadFile(filePath+file.getName(),file.getName());

	}
	//����ɾ��
	public void DeleteFileAction(ActionEvent event) {
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("file");
		ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
		String realPath = servletContext.getRealPath("") + file.getFilepath();
		FileUtil.delete(realPath);
		projectFileService.DeleteFile(file.getIdprojectFileFy());
		listFiles = projectFileService.GetProjectFilesByCode(project.getIdprojectFy());
	}
	//ͼƬɾ��
	public void DeletePicAction(ActionEvent event){
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("image");
		ServletContext servletContext = (ServletContext) FacesContext  
                .getCurrentInstance().getExternalContext().getContext();
		String realPath = servletContext.getRealPath("") + file.getFilepath();// picData.getFilepath();
		FileUtil.delete(realPath);
		projectFileService.DeleteFile(file.getIdprojectFileFy());
		listPics = projectFileService.GetProjectPicsByCode(project.getIdprojectFy());

	}
	
	//ͼƬ������
	public void PicChangeName(ValueChangeEvent event){
		String oldName = (String)event.getOldValue();
	    String newName = (String)event.getNewValue();
	    if("success".equals(projectFileService.ChangeFileName(oldName,newName))){
	    }
	}
	
	public void selectWindow() {
		String tmp = allSelectWindow ? "[2, 6]" : "";
		project.setHouseWindow(tmp);
    }  
	public void selectSupport() {
		String tmp = allSelectSupport ? "[1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 16]" : "";
		project.setHouseSupport(tmp);
    }

	/*
	 * 
	 *		�г��ȽϷ�����
	 * 
	 * */
	
	//�г��ȽϷ����������ʼ��
	public List<MCInstance> getParameters() {
		if(project.getIdprojectFy() == null)
			return null;
		
		if(parameters == null){
			parameters = new ArrayList<MCInstance>();
			parameters.add(new MCInstance("�ɱ�ʵ��A",instances.get(0).getPrice()));
			parameters.add(new MCInstance("�ɱ�ʵ��B",instances.get(1).getPrice()));
			parameters.add(new MCInstance("�ɱ�ʵ��C",instances.get(2).getPrice()));
			parameters.add(new MCInstance("���۶���",0));
			
			List<MarketCompare> ls = marketCompareService.GetMCParameter(project.getIdprojectFy(),(short)2);
			for (MarketCompare obj : ls) {
				switch(obj.getColumn()){
					case 0:
						parameters.get(obj.getRow()).setLocation(obj.getValue());
						break;
					case 1:
						parameters.get(obj.getRow()).setSellDate(obj.getValue());
						break;
					case 2:
						parameters.get(obj.getRow()).setSellStatus(obj.getValue());
						break;
					/*	
					case 3:
						parameters.get(obj.getRow()).setPrice(Integer.parseInt(obj.getValue()));
						break;*/
					case 4:
						parameters.get(obj.getRow()).setBusinessdegree(obj.getValue());
						break;
					case 5:
						parameters.get(obj.getRow()).setLandscape(obj.getValue());
						break;
					case 6:
						parameters.get(obj.getRow()).setTraffic(obj.getValue());
						break;
					case 7:
						parameters.get(obj.getRow()).setEnvironment(obj.getValue());
						break;
					case 8:
						parameters.get(obj.getRow()).setBase(obj.getValue());
						break;
					case 9:
						parameters.get(obj.getRow()).setConstruction(obj.getValue());
						break;
					case 10:
						parameters.get(obj.getRow()).setUnit(obj.getValue());
						break;
					case 11:
						parameters.get(obj.getRow()).setLight(obj.getValue());
						break;
					case 12:
						parameters.get(obj.getRow()).setDecoration(obj.getValue());
						break;
					case 13:
						parameters.get(obj.getRow()).setBuildDate(obj.getValue());
						break;
					case 14:
						parameters.get(obj.getRow()).setCommunity(obj.getValue());
						break;
					case 15:
						parameters.get(obj.getRow()).setFace(obj.getValue());
						break;
					case 16:
						parameters.get(obj.getRow()).setLayer(obj.getValue());
						break;
					case 17:
						parameters.get(obj.getRow()).setSupport(obj.getValue());
						break;
					default:
							break;
				}
				
			}
			
		}
		
		
		return parameters;
	}
	
	//�г��ȽϷ�ʵ���Աȳ�ʼ��	
	public List<MCInstance> getInstances() {
		//������г�ʼ��
		if(project.getIdprojectFy() == null)
			return null;

		if(instances == null){
			instances = new ArrayList<MCInstance>();
			instances.add(new MCInstance("�ɱ�ʵ��A",project));
			instances.add(new MCInstance("�ɱ�ʵ��B",project));
			instances.add(new MCInstance("�ɱ�ʵ��C",project));
			instances.add(new MCInstance("���۶���",project));
			
			List<MarketCompare> ls = marketCompareService.GetMCParameter(project.getIdprojectFy(),(short)1);
			for (MarketCompare obj : ls) {
				switch(obj.getColumn()){
					case 0:
						instances.get(obj.getRow()).setLocation(obj.getValue());
						break;
					case 1:
						instances.get(obj.getRow()).setSellDate(obj.getValue());
						break;
					case 2:
						instances.get(obj.getRow()).setSellStatus(obj.getValue());
						break;
					case 3:
						instances.get(obj.getRow()).setPrice(Integer.parseInt(obj.getValue()));
						break;
					case 4:
						instances.get(obj.getRow()).setBusinessdegree(obj.getValue());
						break;
					case 5:
						instances.get(obj.getRow()).setLandscape(obj.getValue());
						break;
					case 6:
						instances.get(obj.getRow()).setTraffic(obj.getValue());
						break;
					case 7:
						instances.get(obj.getRow()).setEnvironment(obj.getValue());
						break;
					case 8:
						instances.get(obj.getRow()).setBase(obj.getValue());
						break;
					case 9:
						instances.get(obj.getRow()).setConstruction(obj.getValue());
						break;
					case 10:
						instances.get(obj.getRow()).setUnit(obj.getValue());
						break;
					case 11:
						instances.get(obj.getRow()).setLight(obj.getValue());
						break;
					case 12:
						instances.get(obj.getRow()).setDecoration(obj.getValue());
						break;
					case 13:
						instances.get(obj.getRow()).setBuildDate(obj.getValue());
						break;
					case 14:
						instances.get(obj.getRow()).setCommunity(obj.getValue());
						break;
					case 15:
						instances.get(obj.getRow()).setFace(obj.getValue());
						break;
					case 16:
						instances.get(obj.getRow()).setLayer(obj.getValue());
						break;
					case 17:
						instances.get(obj.getRow()).setSupport(obj.getValue());
						break;
					default:
							break;
				}
				
			}
		}
		return instances;
	}

	
	//�г��ȽϷ�ʵ���ı���
	public void MCInstanceSave(){
		//������Ҫ����ı�������
		for(MarketCompare mc:mcRecords){
			marketCompareService.AddMCParameter(mc);
		}
	}
	
	public void onInstanceCellEdit(CellEditEvent event) {
		//��¼�Ĳ�������
		if( mcRecords == null ){
			mcRecords = new ArrayList<MarketCompare>();
		}
		Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
        	String id = event.getColumn().getClientId();
        	id = id.substring(id.lastIndexOf(':')+1);
        	Integer nColumn = (Integer)ColumnParameter.get(id);
            Integer nRow = event.getRowIndex();
            if("price".equals(id)){
        		//����Ǽ۸�
        		parameters.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        		instances.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        	}
            //����ÿһ���༭����CELL
            MarketCompare mc = new MarketCompare(project.getIdprojectFy(),(short)(int)nColumn,
            		(short)(int)nRow,(short)1,newValue.toString());
            
            //����ɾ���ظ���
            for(MarketCompare obj:mcRecords){
            	if ( obj.getColumn() == mc.getColumn() && obj.getRow() == mc.getRow() && obj.getType() == mc.getType() ){
    				mcRecords.remove(obj);
    			}
    		}
            mcRecords.add(mc);
        }  
		
	}
	public static boolean isInteger(String str) {    
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(str).matches();    
	  }  
	
	public void onParameterCellEdit(CellEditEvent event) {
		//��¼�Ĳ�������
		if( mcRecords == null ){
			mcRecords = new ArrayList<MarketCompare>();
		}
		Object oldValue = event.getOldValue();  
        Object newValue = event.getNewValue();
        if (!isInteger(newValue.toString())){
        	return;
        }
        if(newValue != null && !newValue.equals(oldValue)) {
        	String id = event.getColumn().getClientId();
        	id = id.substring(id.lastIndexOf(':')+1);
        	Integer nColumn = (Integer)ColumnParameter.get(id);
            Integer nRow = event.getRowIndex();
            
        	if("price".equals(id)){
        		//����Ǽ۸�
        		parameters.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        		instances.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        	}
        	
            //����ÿһ���༭����CELL
            MarketCompare mc = new MarketCompare(project.getIdprojectFy(),(short)(int)nColumn,
            		(short)(int)nRow,(short)2,newValue.toString());
            
            //����ɾ���ظ���
            for(MarketCompare obj:mcRecords){
    			if ( obj.getColumn() == mc.getColumn() && obj.getRow() == mc.getRow() && obj.getType() == mc.getType() ){
    				mcRecords.remove(obj);
    			}
    		}
            mcRecords.add(mc);
        }  
		
	}
	
	//�г��ȽϷ�����
	private double CalcPrice(){

		double ret = 0;
		MCInstance A = parameters.get(0);
		MCInstance B = parameters.get(1);
		MCInstance C = parameters.get(2);
		MCInstance me = parameters.get(3);
		
		//���ڲ���
		double DateA = Double.parseDouble(A.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateB = Double.parseDouble(B.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateC = Double.parseDouble(C.getSellDate()) / Double.parseDouble(me.getSellDate());
		
		//�����������
		//BigDecimal b1 = new BigDecimal(A.getSellStatus());
		
		double StatusA = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(A.getSellStatus());
		double StatusB = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(B.getSellStatus());
		double StatusC = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(C.getSellStatus());
		//��������
		double AreaA = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
			* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
			/ ( Double.parseDouble(A.getBusinessdegree()) * Double.parseDouble(A.getLandscape()) 
			* Double.parseDouble(A.getEnvironment()) * Double.parseDouble(A.getTraffic()) * Double.parseDouble(A.getBase()) );
		
		double AreaB = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
				* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
				/ ( Double.parseDouble(B.getBusinessdegree()) * Double.parseDouble(B.getLandscape()) 
				* Double.parseDouble(B.getEnvironment()) * Double.parseDouble(B.getTraffic()) * Double.parseDouble(B.getBase()) );
		
		double AreaC = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
				* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
				/ ( Double.parseDouble(C.getBusinessdegree()) * Double.parseDouble(C.getLandscape()) 
				* Double.parseDouble(C.getEnvironment()) * Double.parseDouble(C.getTraffic()) * Double.parseDouble(C.getBase()) );
		//��������
		double HouseA = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(A.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(A.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(A.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(A.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(A.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(A.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(A.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(A.getSupport()) );
		
		double HouseB = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(B.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(B.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(B.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(B.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(B.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(B.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(B.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(B.getSupport()) );
		
		double HouseC = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(C.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(C.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(C.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(C.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(C.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(C.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(C.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(C.getSupport()) );
		
		double priceA = A.getPrice() * DateA * StatusA * HouseA * AreaA;
		double priceC = C.getPrice() * DateC * StatusC * HouseC * AreaC;
		double priceB = B.getPrice() * DateB * StatusB * HouseB * AreaB;
		
		ret = ( priceA + priceB + priceC ) / 3;
		return ret;
	}
	
	//�г��ȽϷ����沢��������Ľ��
	public void CalcResult(ActionEvent actionEvent){
		//�����޸���
		calcPrices = CalcPrice();
		double total = ( calcPrices - landPrices)  * (double)project.getHouseArea() - diyaMoney;
		BigDecimal b1 = new BigDecimal(calcPrices.toString());
		project.setHouseEvaluateSingle((int)Math.round( calcPrices - landPrices));
		project.setHouseEvaluateTotal((int)Math.round(total));
		return ;
	}
	
	//�г��ȽϷ����沢��������Ľ��
	public void SaveResult(ActionEvent actionEvent){
		//�����޸���
		//�������еļ�������Ҳ��Ҫ���棬SHIT����
		//column ֵ 0 ��1��2��3���ֱ�����ĸ���Ҫ����ı���
		if( mcRecords == null ){
			mcRecords = new ArrayList<MarketCompare>();
		}
		
		mcRecords.add(new MarketCompare(project.getIdprojectFy(),(short)1,(short)1,(short)3,landLevel));
		mcRecords.add(new MarketCompare(project.getIdprojectFy(),(short)2,(short)1,(short)3,landPrices.toString()));
		mcRecords.add(new MarketCompare(project.getIdprojectFy(),(short)3,(short)1,(short)3,diyaMoney.toString()));

		MCInstanceSave();
		project.setHouseEvaluateSingle((int)Math.round( calcPrices - landPrices));
		project.setHouseEvaluateTotal((int)Math.round(( calcPrices - landPrices)  * (double)project.getHouseArea() - diyaMoney));
		return ;
	}
	
	//Ԥ�������ѳ���
	public String CompletedYP(){
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("����Ԥ������").uniqueResult();
			
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "��Ԥ���ѳ��ߡ�    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to ������ʽ����");
			}
			
			project.setProjectComment(processOpinion);
			project.setCurStatus("������ʽ����");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "completedyp";
	}
	
	//��ʽ�����ѳ���
	public String CompletedOfficial(){
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("������ʽ����").uniqueResult();
			
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "����ʽ�����ѳ��ߡ�    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to �ʹﱨ��");
			}
			
			project.setProjectComment(processOpinion);
			project.setCurStatus("�ʹﱨ��");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "completedofficial";
	}
	
	//�����г���������������
	private String houseMarketArea(){
		StringBuffer marketArea = new StringBuffer("    ���۶���λ��");
		marketArea.append(project.getHouseXy()).append("������").append(project.getRoadEast())
				.append("������").append(project.getRoadSouth())
				.append("������").append(project.getRoadNorth())
				.append("������").append(project.getRoadWest())
				.append("��������Ҫ��").append(project.getAroundMarket()).append("����·ͨ���ͨ���")
				.append("��������").append(project.getAroundTraffic()).append("��");
		return marketArea.toString();
	}
	
	//����װ��ת�庯��
	private String transformHouseDecoration(Short houseDecoration){
		String ret = "δ֪";
		switch(houseDecoration){
			case 1:
				ret = "��װ";
				break;
			case 2:
				ret = "��װ";
				break;
			case 3:
				ret = "ë��";
				break;
			case 4:
				ret = "��װ";
				break;
			case 5:
				ret = "����װ��";
				break;
			default:
				break;
		}
		return ret;
	}
	//��������;ת�庯��
	private String transformHouseUsing(Short houseUsing){
		String ret = "δ֪";
		switch(houseUsing){
			case 1:
				ret = "����סլ";
				break;
			case 2:
				ret = "��ҵ����";
				break;
			case 3:
				ret = "�칫";
				break;
			case 4:
				ret = "�ǳ���סլ";
				break;
			case 5:
				ret = "�Ƶ�ʽ��Ԣ";
				break;
			default:
				break;
		}
		return ret;
	}
	//С����ģת�庯��
	private String transformXiaoquExtent(Short xiaoquExtent){
		String ret = "δ֪";
		switch(xiaoquExtent){
			case 1:
				ret = "��С";
				break;
			case 2:
				ret = "�е�";
				break;
			case 3:
				ret = "�ϴ�";
				break;
			default:
				break;
		}
		return ret;
	}
	
	//��ҵ����ת�庯��
	private String transformWuyeManager(Short wuyeManager){
		String ret = "δ֪";
		switch(wuyeManager){
			case 1:
				ret = "��λ��ҵ����";
				break;
			case 2:
				ret = "רҵ��ҵ����";
				break;
			case 3:
				ret = "��";
				break;
			default:
				break;
		}
		return ret;
	}
	//С���̻�ת�庯��
	private String transformHouseLvhua(Short houseLvhua){
		String ret = "δ֪";
		switch(houseLvhua){
			case 1:
				ret = "��";
				break;
			case 2:
				ret = "һ��";
				break;
			case 3:
				ret = "�Ϻ�";
				break;
			case 4:
				ret = "����";
				break;
			default:
				break;
		}
		return ret;
	}
	//���ݳ���ת�庯��
	private String transformHouseFace(Short houseFace){
		String ret = "δ֪";
		switch(houseFace){
			case 1:
				ret = "��";
				break;
			case 2:
				ret = "��";
				break;
			case 3:
				ret = "��";
				break;
			case 4:
				ret = "��";
				break;
			case 5:
				ret = "����";
				break;
			case 6:
				ret = "����";
				break;
			case 7:
				ret = "����";
				break;
			case 8:
				ret = "����";
				break;
			case 9:
				ret = "�ϱ�";
				break;
			case 10:
				ret = "����";
				break;
			default:
				break;
		}
		return ret;
	}
	//���ݽṹ ת�庯��
	private String transformHouseConstruction(Short houseConstruction){
		String ret = "δ֪";
		switch(houseConstruction){
			case 1:
				ret = "�ֻ�";
				break;
			case 2:
				ret = "���";
				break;
			case 3:
				ret = "שľ";
				break;
			case 4:
				ret = "��";
				break;
			default:
				break;
		}
		return ret;
	}	
	
	//�г��ȽϷ�����
	private void fillCalcPrice(Map<String,String> var){

		// 0 ==> A  ||  1 ==> B   ||  2 ==> C 
		MCInstance A = parameters.get(0);
		MCInstance B = parameters.get(1);
		MCInstance C = parameters.get(2);
		MCInstance me = parameters.get(3);
		
		//���ڲ���
		double DateA = Double.parseDouble(A.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateB = Double.parseDouble(B.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateC = Double.parseDouble(C.getSellDate()) / Double.parseDouble(me.getSellDate());
		
		var.put("031",new java.text.DecimalFormat("#0.0000").format(DateA));
		var.put("131",new java.text.DecimalFormat("#0.0000").format(DateB));
		var.put("231",new java.text.DecimalFormat("#0.0000").format(DateC));
		
		double StatusA = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(A.getSellStatus());
		double StatusB = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(B.getSellStatus());
		double StatusC = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(C.getSellStatus());
		
		var.put("032",new java.text.DecimalFormat("#0.0000").format(StatusA));
		var.put("132",new java.text.DecimalFormat("#0.0000").format(StatusB));
		var.put("232",new java.text.DecimalFormat("#0.0000").format(StatusC));
		
		//��������
		double AreaA = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
			* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
			/ ( Double.parseDouble(A.getBusinessdegree()) * Double.parseDouble(A.getLandscape()) 
			* Double.parseDouble(A.getEnvironment()) * Double.parseDouble(A.getTraffic()) * Double.parseDouble(A.getBase()) );
		
		double AreaB = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
				* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
				/ ( Double.parseDouble(B.getBusinessdegree()) * Double.parseDouble(B.getLandscape()) 
				* Double.parseDouble(B.getEnvironment()) * Double.parseDouble(B.getTraffic()) * Double.parseDouble(B.getBase()) );
		
		double AreaC = ( Double.parseDouble(me.getBusinessdegree()) * Double.parseDouble(me.getLandscape()) 
				* Double.parseDouble(me.getEnvironment()) * Double.parseDouble(me.getTraffic()) * Double.parseDouble(me.getBase()) )
				/ ( Double.parseDouble(C.getBusinessdegree()) * Double.parseDouble(C.getLandscape()) 
				* Double.parseDouble(C.getEnvironment()) * Double.parseDouble(C.getTraffic()) * Double.parseDouble(C.getBase()) );
		
		var.put("033",new java.text.DecimalFormat("#0.0000").format(AreaA));
		var.put("133",new java.text.DecimalFormat("#0.0000").format(AreaB));
		var.put("233",new java.text.DecimalFormat("#0.0000").format(AreaC));
		
		//��������
		double HouseA = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(A.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(A.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(A.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(A.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(A.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(A.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(A.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(A.getSupport()) );
		
		double HouseB = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(B.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(B.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(B.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(B.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(B.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(B.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(B.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(B.getSupport()) );
		
		double HouseC = ( Double.parseDouble(me.getConstruction()) / Double.parseDouble(C.getConstruction()) )
				* ( Double.parseDouble(me.getUnit()) / Double.parseDouble(C.getUnit()) )
				* ( Double.parseDouble(me.getLight()) / Double.parseDouble(C.getLight()) )
				* ( Double.parseDouble(me.getDecoration()) / Double.parseDouble(C.getDecoration()) )
				* ( Double.parseDouble(me.getBuildDate()) / Double.parseDouble(C.getBuildDate()) )
				* ( Double.parseDouble(me.getFace()) / Double.parseDouble(C.getFace()) )
				* ( Double.parseDouble(me.getLayer()) / Double.parseDouble(C.getLayer()) )
				* ( Double.parseDouble(me.getSupport()) / Double.parseDouble(C.getSupport()) );
		
		var.put("034",new java.text.DecimalFormat("#0.0000").format(HouseA));
		var.put("134",new java.text.DecimalFormat("#0.0000").format(HouseB));
		var.put("234",new java.text.DecimalFormat("#0.0000").format(HouseC));
		
		double priceA = A.getPrice() * DateA * StatusA * HouseA * AreaA;
		double priceC = C.getPrice() * DateC * StatusC * HouseC * AreaC;
		double priceB = B.getPrice() * DateB * StatusB * HouseB * AreaB;
		
		var.put("035",new java.text.DecimalFormat("#0.0000").format(priceA));
		var.put("135",new java.text.DecimalFormat("#0.0000").format(priceB));
		var.put("235",new java.text.DecimalFormat("#0.0000").format(priceC));
	}
	
	private void fillParametersMap(Map<String,String> var){
		
		//�����ǱȽ����ص�˵������������˵��
		String strName = "";
		for(Integer i=0;i<4;i++){
			strName = i.toString()+"10";
			var.put(strName,
				((instances.get(i).getLocation() == null )?"δ֪":instances.get(i).getLocation()));
			strName = i.toString()+"11";
			var.put(strName,
				((instances.get(i).getSellDate() == null )?"δ֪":instances.get(i).getSellDate()));
			strName = i.toString()+"12";
			var.put(strName,
				((instances.get(i).getSellStatus() == null )?"δ֪":instances.get(i).getSellStatus()));
			strName = i.toString()+"13";
			var.put(strName,
				((instances.get(i).getPrice() == null )?"δ֪":instances.get(i).getPrice().toString()));
			strName = i.toString()+"14";
			var.put(strName,
				((instances.get(i).getBusinessdegree() == null )?"δ֪":instances.get(i).getBusinessdegree()));
			strName = i.toString()+"15";
			var.put(strName,
				((instances.get(i).getLandscape() == null )?"δ֪":instances.get(i).getLandscape()));
			strName = i.toString()+"16";
			var.put(strName,
				((instances.get(i).getTraffic() == null )?"δ֪":instances.get(i).getTraffic()));
			strName = i.toString()+"17";
			var.put(strName,
				((instances.get(i).getEnvironment() == null )?"δ֪":instances.get(i).getEnvironment()));
			strName = i.toString()+"18";
			var.put(strName,
				((instances.get(i).getBase() == null )?"δ֪":instances.get(i).getBase()));
			strName = i.toString()+"19";
			var.put(strName,
				((instances.get(i).getConstruction() == null )?"δ֪":instances.get(i).getConstruction()));
			strName = i.toString()+"110";
			var.put(strName,
				((instances.get(i).getUnit() == null )?"δ֪":instances.get(i).getUnit()));
			strName = i.toString()+"111";
			var.put(strName,
				((instances.get(i).getLight() == null )?"δ֪":instances.get(i).getLight()));
			strName = i.toString()+"112";
			var.put(strName,
				((instances.get(i).getDecoration() == null )?"δ֪":instances.get(i).getDecoration()));
			strName = i.toString()+"113";
			var.put(strName,
				((instances.get(i).getBuildDate() == null )?"δ֪":instances.get(i).getBuildDate()));
			strName = i.toString()+"114";
			var.put(strName,"����۶���һ��");
			strName = i.toString()+"115";
			var.put(strName,
				((instances.get(i).getCommunity() == null )?"δ֪":instances.get(i).getCommunity()));
			strName = i.toString()+"116";
			var.put(strName,
				((instances.get(i).getFace() == null )?"δ֪":instances.get(i).getFace()));
			strName = i.toString()+"117";
			var.put(strName,
				((instances.get(i).getLayer() == null )?"δ֪":instances.get(i).getLayer()));
			strName = i.toString()+"118";
			var.put(strName,"��Ʒ��");
			strName = i.toString()+"119";
			var.put(strName,
				((instances.get(i).getSupport() == null )?"δ֪":instances.get(i).getSupport()));
		}
		
		//������������
		for(Integer i=0;i<4;i++){
			strName = i.toString()+"20";
			var.put(strName,
				((parameters.get(i).getPrice() == null )?"δ֪":parameters.get(i).getPrice().toString()));
			strName = i.toString()+"21";
			var.put(strName,
				((parameters.get(i).getSellDate() == null )?"δ֪":parameters.get(i).getSellDate()));
			strName = i.toString()+"22";
			var.put(strName,
				((parameters.get(i).getSellStatus() == null )?"δ֪":parameters.get(i).getSellStatus()));
			strName = i.toString()+"23";
			var.put(strName,
				((parameters.get(i).getBusinessdegree() == null )?"δ֪":parameters.get(i).getBusinessdegree()));
			strName = i.toString()+"24";
			var.put(strName,
				((parameters.get(i).getLandscape() == null )?"δ֪":parameters.get(i).getLandscape()));
			strName = i.toString()+"25";
			var.put(strName,
				((parameters.get(i).getTraffic() == null )?"δ֪":parameters.get(i).getTraffic()));
			strName = i.toString()+"26";
			var.put(strName,
				((parameters.get(i).getEnvironment() == null )?"δ֪":parameters.get(i).getEnvironment()));
			strName = i.toString()+"27";
			var.put(strName,
				((parameters.get(i).getBase() == null )?"δ֪":parameters.get(i).getBase()));
			strName = i.toString()+"28";
			var.put(strName,
				((parameters.get(i).getConstruction() == null )?"δ֪":parameters.get(i).getConstruction()));
			strName = i.toString()+"29";
			var.put(strName,
				((parameters.get(i).getUnit() == null )?"δ֪":parameters.get(i).getUnit()));
			strName = i.toString()+"210";
			var.put(strName,
				((parameters.get(i).getLight() == null )?"δ֪":parameters.get(i).getLight()));
			strName = i.toString()+"211";
			var.put(strName,
				((parameters.get(i).getDecoration() == null )?"δ֪":parameters.get(i).getDecoration()));
			strName = i.toString()+"212";
			var.put(strName,
				((parameters.get(i).getBuildDate() == null )?"δ֪":parameters.get(i).getBuildDate()));
			strName = i.toString()+"213";
			var.put(strName,"100");
			strName = i.toString()+"214";
			var.put(strName,
				((parameters.get(i).getCommunity() == null )?"δ֪":parameters.get(i).getCommunity()));
			strName = i.toString()+"215";
			var.put(strName,
				((parameters.get(i).getFace() == null )?"δ֪":parameters.get(i).getFace()));
			strName = i.toString()+"216";
			var.put(strName,
				((parameters.get(i).getLayer() == null )?"δ֪":parameters.get(i).getLayer()));
			strName = i.toString()+"217";
			var.put(strName,"100");
			strName = i.toString()+"218";
			var.put(strName,
				((instances.get(i).getSupport() == null )?"δ֪":parameters.get(i).getSupport()));
		}
		fillCalcPrice(var);
	}
	
	//������ʽ����
	//���˳��ʾ����{020}������ߵ���һ������λ���ֱ��Ӧ���ǣ�
	//	��һλ������0��ʾ��A��1��ʾ��B��2��ʾ��C��3���ɼ۶���
	//	�ڶ�λ������1��˵����0��������
	//	����λ������ÿһ�е���Ŵ�0��ʼ����17�����������~������ʩ
	public String ExportOfficialWord(){
		try
		{
			//���ձ�������ĵ�����
			POIWordDocument pdoc = new POIWordDocument();
			Map<String,String> var = new HashMap<String,String>();
			SimpleDateFormat fmYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat fmDate = new SimpleDateFormat("yyyy��MM��dd��");

			var.put("projectCode",
					((project.getProjectCode() == null )?"δ֪":project.getProjectCode().substring(4)));
			var.put("houseXy",
					((project.getHouseXy() == null )?"δ֪":project.getHouseXy()));
			var.put("houseArea",
					((project.getHouseArea() == null )?"δ֪":new java.text.DecimalFormat("#.00").format(project.getHouseArea())));
			var.put("houseHolds",
					((project.getHouseHolds() == null )?"δ֪":project.getHouseHolds().toString()));
			var.put("houseUnit1",
					((project.getHouseUnit1() == null )?"δ֪":project.getHouseUnit1().toString()));
			var.put("houseUnit2",
					((project.getHouseUnit2() == null )?"δ֪":project.getHouseUnit2().toString()));
			var.put("houseUnit3",
					((project.getHouseUnit3() == null )?"δ֪":project.getHouseUnit3().toString()));
			var.put("houseUnit4",
					((project.getHouseUnit4() == null )?"δ֪":project.getHouseUnit4().toString()));
			var.put("houseDecoration",
					((project.getHouseDecoration() == null )?"δ֪":transformHouseDecoration(project.getHouseDecoration())));
			var.put("houseLayers",
					((project.getHouseLayers() == null )?"δ֪":project.getHouseLayers().toString()));
			var.put("houseAtLayer",
					((project.getHouseAtLayer() == null )?"δ֪":project.getHouseAtLayer().toString()));
			var.put("houseUsing",
					((project.getHouseUsing() == null )?"δ֪":transformHouseUsing(project.getHouseUsing())));
			var.put("houseUse",
					((project.getHouseUse() == null )?"δ֪":project.getHouseUse()));
			var.put("houseDate",
					((project.getHouseDate() == null )?"δ֪":fmYear.format(project.getHouseDate())));
			var.put("buyerName",
					((project.getBuyerName() == null )?"δ֪":project.getBuyerName()));
			
			//�����֤���еĻ���������֤�ŵ�
			if(project.getHouseNewnumber() != null){
				var.put("houseNumber",project.getHouseNewnumber());
				if(project.getNewhouseDate() != null){
					Calendar c = new GregorianCalendar();  
					c.setTime(project.getNewhouseDate());
					var.put("houseNumberDate",fmDate.format(c.getTime()));
					c.add(Calendar.DAY_OF_MONTH, 1); 
					var.put("evaluateDate",fmDate.format(c.getTime()));
				}
			}else{
				var.put("houseNumber",
						((project.getHouseNumber() == null )?"δ֪":project.getHouseNumber()));
				if(project.getProjectDate() != null){
					Calendar c = new GregorianCalendar();  
					c.setTime(project.getProjectDate());
					var.put("houseNumberDate",fmDate.format(c.getTime()));
					c.add(Calendar.DAY_OF_MONTH, 1); 
					var.put("evaluateDate",fmDate.format(c.getTime()));
				}
			}

			//����ʱ��,�����Ҫ��һ��������̣��Ժ��޸�
			var.put("beginEvaluateDate",
					((project.getKanchaDate() == null )?"δ֪":fmDate.format(project.getKanchaDate())));
			var.put("endEvaluateDate",fmDate.format(new java.util.Date()));
			//��д�����
			var.put("houseEvaluateTotalRMB",
					((project.getHouseEvaluateTotal() == null )?"δ֪":Num2RMB.CNValueOf(project.getHouseEvaluateTotal().toString())));
			var.put("houseEvaluateTotal",
					((project.getHouseEvaluateTotal() == null )?"δ֪":(new java.text.DecimalFormat("#.00").format(project.getHouseEvaluateTotal().floatValue()/10000))));
			var.put("houseEvaluateSingle",
					((project.getHouseEvaluateSingle() == null )?"δ֪":project.getHouseEvaluateSingle().toString()));
			var.put("wuyeAt",
					((project.getWuyeAt() == null )?"δ֪":project.getWuyeAt()));
			var.put("xiaoquExtent",
					((project.getXiaoquExtent() == null )?"δ֪":transformXiaoquExtent(project.getXiaoquExtent())));
			var.put("wuyeManager",
					((project.getWuyeManager() == null )?"δ֪":transformWuyeManager(project.getWuyeManager())));
			var.put("houseLvhua",
					((project.getHouseLvhua() == null )?"δ֪":transformHouseLvhua(project.getHouseLvhua())));
			var.put("houseLayerHeight",
					((project.getHouseLayerHeight() == null )?"δ֪":project.getHouseLayerHeight().toString()));
			var.put("houseFace",
					((project.getHouseFace() == null )?"δ֪":transformHouseFace(project.getHouseFace())));
			var.put("houseConstruction",
					((project.getHouseConstruction() == null )?"δ֪":transformHouseConstruction(project.getHouseConstruction())));
			var.put("houseUnitNum",
					((project.getHouseUnitNum() == null )?"δ֪":project.getHouseUnitNum().toString()));
			var.put("houseHolds",
					((project.getHouseHolds() == null )?"δ֪":project.getHouseHolds().toString()));
			var.put("kanchaDate",
					((project.getKanchaDate() == null )?"δ֪":fmDate.format(project.getKanchaDate())));
			var.put("sellDate",
					((project.getSellDate() == null )?"δ֪":fmDate.format(project.getSellDate())));
			var.put("projectDate",
					((project.getProjectDate() == null )?"δ֪":fmDate.format(project.getProjectDate())));
			var.put("DecorationStatus",FormatDecorationStatus());
			var.put("houseMarketArea",houseMarketArea());
			fillParametersMap(var);
			
			if(listPics != null){
				for(ProjectFileFy obj:listPics){
					var.put(obj.getName(),obj.getFilepath()+obj.getName());
				}
			}
			
			//���������ʽ�ĵ�
			String filename = project.getProjectCode() +"_official.docx";
			
			ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
			String realPath = servletContext.getRealPath("") + "/" + filename;
			pdoc.ReplaceAll("/doc/official.docx",realPath,var);
			FileUtil.downloadFile(filename,filename);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "exportofficial";
	}
	
	/*          end   class           */
}
