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
	 * 变量声明
	 * */
	private ProjectFy project;
	private ProjectService projectservice;
	private boolean bEnd;			//是否直接归档
	private JBPMUtil jBPMUtil;
	private int operatetype;		//操作类型。0,add;1,edit;2,kancha;3,pinggu;4,shencha;5,shenpi；6，已完成
	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	private UserService userservice;

	//附件浏览列表
	private List<ProjectFileFy> listFiles;
	//图片浏览参数
	private List<ProjectFileFy> listPics;

	//项目文件数据库
	private ProjectFileService projectFileService;
	private String processOpinion;
	//项目序列数据
	private ProjectSeqService projectSeqService;
	//项目配套设施、门窗，需要特殊处理
	private String[] projectSupport;
	private String[] projectWindow;
	private boolean  allSelectWindow;
	private boolean  allSelectSupport;
	
	//直接归档，输入的档案编号
	private String pcode;
	private String pletter;
	
	//市场比较法变量列表
	private List<MCInstance> instances;		//可比较实例列表
	private List<MarketCompare> mcRecords;	//需要保存的记录，都先放到这个列表里
	private List<MCInstance> parameters;	//市场比较法计算参数
	private MarketCompareService marketCompareService;
	private Map<String,Object> ColumnParameter;
	
	private Double calcPrices;		//估价对象比准价格
	private String landLevel;		//土地级别
	private Double landPrices;		//土地出让金 （每平米）
	private Double diyaMoney;		//抵押权价值
	
	/*
	 * GET/SET 函数
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
			landLevel = "级";
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
		//初始化result参数
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

		//类的构造在服务注入之前，所以注入之后我再查询，
		
		Long pid = (Long)session.getAttribute("projectId");
			
		operatetype = 0;
		if( pid !=null ){
			//如果有项目ID传进来，就得到项目。
			ProjectFy project = projectservice.GetProjectByPId(pid);
			listFiles = projectFileService.GetProjectFilesByCode(pid);
			listPics = projectFileService.GetProjectPicsByCode(pid);
			setProject(project);
			session.removeAttribute("projectId");
			
			if ( project.getCurStatus() ==null ){
				operatetype = 0; //添加
			}else if("现场勘查".equals(project.getCurStatus())){
				operatetype = 2;//勘查
			}else if("预评撰写".equals(project.getCurStatus())){
				operatetype = 3;//预评撰写
			}else if("预评审查".equals(project.getCurStatus())){
				operatetype = 4;//审查
			}else if("预评审批".equals(project.getCurStatus())){
				operatetype = 5;//审批
			}else if("出具预评报告".equals(project.getCurStatus())){
				operatetype = 7;//出具预评报告
			}else if("出具正式报告".equals(project.getCurStatus())){
				operatetype = 8;
			}else if("送达报告".equals(project.getCurStatus())){
				operatetype = 9;
			}else if("归档".equals(project.getCurStatus())){
				operatetype = 6;
			}
		}
	}
	
	/*
	 *
	 * 响应函数，功能函数
	 * 
	*/
	
	public ProjectAdd(){		
		//初始化对照参数
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

	//启动流程
	private String StarProcess(){
		//得到当前用户
		UserFy userfy = (UserFy)session.getAttribute("user");

		if(userfy == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"新建案件失败！当前用户不合法！", "失败！"));
			return "error";
		}
		
		//设置受理时间，项目开始时间
		project.setShouliDate(new java.util.Date());
		String ret = projectservice.SaveOrUpdateProject(project);
		
		if("failed".equals(ret)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"新建案件失败！档案编号重复。", "失败！"));
			return "error";
		}
		
		//设置process的变量
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("user", userfy.getName());

		//启动流程
		ProcessInstance processInstance = jBPMUtil.getExecutionService().
				startProcessInstanceByKey("test1",variables);
		
		jBPMUtil.getExecutionService()
			.setVariable(processInstance.getId(),"processInstanceId",processInstance.getId());
		jBPMUtil.getExecutionService()
			.createVariable(processInstance.getId(),"opinion","案件受理",true);
		
	
		//流程第一步：业务受理
		//得到当前任务ID
		String taskid = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(processInstance.getId()).uniqueResult().getId();

		//文档关联流程实例ID
		project.setProcessInstanceId(processInstance.getId());
		//保存文档
		project.setProjectComment(processOpinion);

		projectservice.SaveOrUpdateProject(project);
		
		project = null;
				
		//流程向下流转
		if(bEnd){
			//直接结束，归档
			jBPMUtil.getTaskService().addTaskComment(taskid, "【直接归档】");
			jBPMUtil.getTaskService().completeTask(taskid,"to 归档");
		}else{
			jBPMUtil.getTaskService().addTaskComment(taskid, "【提交现场勘查】");
			jBPMUtil.getTaskService().completeTask(taskid,"to 现场勘查");
		}

		return ret;
	}
	
	//保存按钮
	public String InsertProject(){
		if(operatetype == 0){
			//0，代表业务受理节点
			String prjcode;
			
			if(bEnd){
				//直接归档的话不要进行自增计算
				prjcode = pcode;
				this.project.setCurStatus("直接归档");
			}else{
				if("".equals(pcode) || pcode==null ){
					//计算项目编号 ： 2013 03 0000 A~G
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
					prjcode = pcode;//如果填编号的话
				}
				this.project.setCurStatus("现场勘查");
			}
			if("over numbers".equals(prjcode)){
				//如果大于9999，就认为是ID溢出，不能再继续了。
				return "over numbers";
			}
			this.project.setProjectCode(prjcode);
			return StarProcess();
		}
		else{
			//如果是其他流程节点，这个就是保存按钮。
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
	
	//勘查完成
	public String KanchaComplete(){
		String ret = "error";
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("现场勘查").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【勘查完成】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 预评撰写");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("预评撰写");
			projectservice.SaveOrUpdateProject(project);
			ret = "Kancha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//最后清理一下
		//clear();

		return ret;
	}
	
	//评估完成
	public String PingguComplete(){
		String ret = "error";
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("预评撰写").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(taskid,"【评估完成】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 预评审查");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("预评审查");
			projectservice.SaveOrUpdateProject(project);
			ret = "pinggu";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//最后清理一下
		//clear();

		return ret;
	}
	
	//审查通过
	public String ShenchaPass(){
		String ret = "error";
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("预评审查").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【审查通过】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 预评审批");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("预评审批");
			projectservice.SaveOrUpdateProject(project);
			ret = "shencha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//最后清理一下
		//clear();

		return ret;
	}
	
	//审查不通过
	public String ShenchaUnpass(){
		String ret = "error";
		UserFy userfy = (UserFy)session.getAttribute("user");
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("预评审查").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				//先接受任务，然后再往下流转
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【审查不通过】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 预评撰写");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("预评撰写");
			projectservice.SaveOrUpdateProject(project);
			ret = "shencha";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//最后清理一下
		//clear();

		return ret;
	}
	//审批通过
	public String ShenpiPass(){	
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("预评审批").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【审批通过】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 出具预评报告");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("出具预评报告");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		return "shenpi";
	}
	//审批不通过
	public String ShenpiUnpass(){
		try
		{
			UserFy userfy = (UserFy)session.getAttribute("user");
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("预评审批").uniqueResult();
			
			System.out.println("activityName:"+task.getActivityName());
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【审批不通过】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 预评撰写");
			}
			project.setProjectComment(processOpinion);
			project.setCurStatus("预评撰写");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "shenpi";
	}
	
	//导出图片编辑
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
	
	//增加合适的房屋装修修饰动词，乳胶漆、涂料类：刷；瓷片：贴；	PVC，水泥地面、：为；壁纸：贴；砖或地板之类：铺；
	//输入的参数只能是装修的材料。
	private String AddPerDecoration(String meterial){
		StringBuffer ret = new StringBuffer("");
		if("地板砖".equals(meterial)){
			ret.append("铺").append(meterial);
		}else if("木地板".equals(meterial)){
			ret.append("铺").append(meterial);
		}else if("地板革".equals(meterial)){
			ret.append("铺").append(meterial);
		}else if("水泥砂浆地面".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("涂料".equals(meterial)){
			ret.append("刷").append(meterial);
		}else if("乳胶漆".equals(meterial)){
			ret.append("刷").append(meterial);
		}else if("壁纸".equals(meterial)){
			ret.append("贴").append(meterial);
		}else if("壁布".equals(meterial)){
			ret.append("贴").append(meterial);
		}else if("水泥砂浆".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("涂料罩白".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("吊顶".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("防滑地板砖".equals(meterial)){
			ret.append("铺").append(meterial);
		}else if("马赛克".equals(meterial)){
			ret.append("贴").append(meterial);
		}else if("水泥地面".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("瓷片".equals(meterial)){
			ret.append("贴").append(meterial);
		}else if("PVC".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("水泥砂浆拉毛".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("水泥砂浆抹平".equals(meterial)){
			ret.append("为").append(meterial);
		}else if("石灰罩白".equals(meterial)){
			ret.append("为").append(meterial);
		}
		
		return ret.toString();
	}
	
	//格式化装修状态
	private String FormatDecorationStatus(){
		StringBuffer ret = new StringBuffer("");
		//[1, 2, 3, 4, 5, 6, 7, 8]
		for(int i=0;i<projectWindow.length;i++){
			if( "1".equals(projectWindow[i]) ){
				ret.append("入户处安装单层防盗门，");
			}else if( "2".equals(projectWindow[i]) ){
				ret.append("入户处安装双层防盗门，");
			}else if( "3".equals(projectWindow[i]) ){
				ret.append("入户处安装木门，");
			}else if( "4".equals(projectWindow[i]) ){
				ret.append("木窗，");
			}else if( "5".equals(projectWindow[i]) ){
				ret.append("塑钢窗，");
			}else if( "6".equals(projectWindow[i]) ){
				ret.append("铝合金窗，");
			}else if( "7".equals(projectWindow[i]) ){
				ret.append("钢窗，");
			}else if( "8".equals(projectWindow[i]) ){
				ret.append("彩铝窗，");
			}
		}
		
		if (project.getHouseYangtai() == null){
			ret.append("无阳台");
		}else if (project.getHouseYangtai() == 1){
			ret.append("阳台已封闭。");
		}else if (project.getHouseYangtai() == 2){
			ret.append("阳台未封闭。");
		}else {
			ret.append("无阳台。");
		}
		
		if(project.getHouseMaopiDimian() == null || "0".equals(project.getHouseMaopiDimian()) || 
				"未选择".equals(project.getHouseMaopiDimian()) || "".equals(project.getHouseMaopiDimian()) ){
			//如果不是毛坯房
			
			//客厅
			if(project.getHouseKetingDimian() != null){
				ret.append("室内客厅地面").append(AddPerDecoration(project.getHouseKetingDimian())).append("，墙面")
							.append(AddPerDecoration(project.getHouseKetingQiangmian()))
							.append("，顶棚").append(AddPerDecoration(project.getHouseKetingDingpeng())).append("；");
			}
			
			//餐厅
			if(project.getHouseCantingDimian() != null){
				ret.append("室内餐厅地面").append(AddPerDecoration(project.getHouseCantingDimian())).append("，墙面")
							.append(AddPerDecoration(project.getHouseCantingQiangmian()))
							.append("，顶棚").append(AddPerDecoration(project.getHouseCantingDingpeng())).append("；");
			}

			//卧室
			if(project.getHouseWoshiDimian() != null){
				ret.append("卧室地面").append(AddPerDecoration(project.getHouseWoshiDimian())).append("，墙面")
							.append(AddPerDecoration(project.getHouseWoshiQiangmian()))
							.append("，顶棚").append(AddPerDecoration(project.getHouseWoshiDingpeng())).append("；");
			}
			
			//厨房
			if(project.getHouseChufangDimian() != null){
				ret.append("厨房地面").append(AddPerDecoration(project.getHouseChufangDimian())).append("，墙面")
							.append(AddPerDecoration(project.getHouseChufangQiangmian()))
							.append("，顶棚").append(AddPerDecoration(project.getHouseChufangDingpeng())).append("；");
			}
			
			//卫生间
			if(project.getHouseWeiDimain() != null){
				ret.append("卫生间地面").append(AddPerDecoration(project.getHouseWeiDimain())).append("，墙面")
							.append(AddPerDecoration(project.getHouseWeiQiangmian()))
							.append("，顶棚").append(AddPerDecoration(project.getHouseWeiDingpeng())).append("；");
			}
			
			ret.deleteCharAt(ret.length()-1);
			
		}else {
			//如果是毛坯房
			ret.append("该房为毛坯房，地面").append(AddPerDecoration(project.getHouseMaopiDimian()))
			   .append("，墙面").append(AddPerDecoration(project.getHouseMaopiQiangmian()))
			   .append("，顶棚").append(AddPerDecoration(project.getHouseMaopiDingpeng()));
		}
		ret.append("。配套设施有");
		for(int i=0;i<projectSupport.length;i++){
			if( "1".equals(projectSupport[i]) ){
				ret.append((i==0) ? "水" : "、水" );
			}else if( "2".equals(projectSupport[i]) ){
				ret.append((i==0) ? "电" : "、电" );
			}else if( "3".equals(projectSupport[i]) ){
				ret.append((i==0) ? "天然气" : "、天然气" );
			}else if( "4".equals(projectSupport[i]) ){
				ret.append((i==0) ? "电话" : "、电话" );
			}else if( "5".equals(projectSupport[i]) ){
				ret.append((i==0) ? "闭路" : "、闭路" );
			}else if( "6".equals(projectSupport[i]) ){
				ret.append((i==0) ? "宽带" : "、宽带" );
			}else if( "7".equals(projectSupport[i]) ){
				ret.append((i==0) ? "电梯" : "、电梯" );
			}else if( "8".equals(projectSupport[i]) ){
				ret.append((i==0) ? "门禁系统" : "、门禁系统" );
			}else if( "9".equals(projectSupport[i]) ){
				ret.append((i==0) ? "中央空调" : "、中央空调" );
			}else if( "10".equals(projectSupport[i]) ){
				ret.append((i==0) ? "柜挂式空调" : "、柜挂式空调" );
			}else if( "11".equals(projectSupport[i]) ){
				ret.append((i==0) ? "集中供暖" : "、集中供暖" );
			}else if( "12".equals(projectSupport[i]) ){
				ret.append((i==0) ? "土暖" : "、土暖" );
			}else if( "13".equals(projectSupport[i]) ){
				ret.append((i==0) ? "地暖" : "、地暖" );
			}else if( "14".equals(projectSupport[i]) ){
				ret.append((i==0) ? "壁挂" : "、壁挂" );
			}else if( "15".equals(projectSupport[i]) ){
				ret.append((i==0) ? "自动消防" : "、自动消防" );
			}else if( "16".equals(projectSupport[i]) ){
				ret.append((i==0) ? "消防栓" : "、消防栓" );
			}
		}
		ret.append("等。");
		return ret.toString();
	}
	
	//格式化 房屋现状
	private String FormatKanchaCurrentstatus(){
		if(project == null){
			return "";
		}
		
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer("    1、估价对象位于");
		ret.append(project.getHouseXy()).append("，为").append(project.getWuyeAt()).append("小区，小区规模");
				
		if(project.getXiaoquExtent() == null){
			ret.append("未知，");
		}else if(project.getXiaoquExtent() == 1){
			ret.append("较小，");
		}else if(project.getXiaoquExtent() == 2){
			ret.append("中等，");
		}else if(project.getXiaoquExtent() == 3){
			ret.append("较大，");
		}
		if(project.getWuyeManager() == null){
			ret.append("物业未知，");
		}else if(project.getWuyeManager() == 1){
			ret.append("单位物业管理，");
		}else if(project.getWuyeManager() == 2){
			ret.append("专业物业管理，");
		}else {
			ret.append("无物业管理，");
		}
		
		if(project.getHouseLvhua() == null){
			ret.append("无绿化。");
		}else if(project.getHouseLvhua() == 1){
			ret.append("无绿化。");
		}else if(project.getHouseLvhua() == 2){
			ret.append("绿化一般。");
		}else if(project.getHouseLvhua() == 3){
			ret.append("绿化较好。");
		}else if(project.getHouseLvhua() == 4){
			ret.append("绿化良好。");
		}

		ret.append(newLine);
		
		ret.append("    2、外立面" + project.getHouseWaiqiang() + "，估价对象朝向为");
		if(project.getHouseFace() == null){
			ret.append("未知。");
		}else{
			switch(project.getHouseFace()){
				case 1:
					ret.append("东朝向。");
					break;
				case 2:
					ret.append("南朝向。");
					break;
				case 3:
					ret.append("西朝向。");
					break;
				case 4:
					ret.append("北朝向。");
					break;
				case 5:
					ret.append("东南朝向。");
					break;
				case 6:
					ret.append("东北朝向。");
					break;
				case 7:
					ret.append("西南朝向。");
					break;
				case 8:
					ret.append("西北朝向。");
					break;
				case 9:
					ret.append("南北朝向。");
					break;
				case 10:
					ret.append("东西朝向。");
					break;
				default:
						break;
			}
		}

		ret.append(newLine);
		
		if(project.getHouseAtLayer() != null && project.getHouseLayers()!=null ){
			ret.append("    3、估价对象位于第").append(project.getHouseAtLayer().toString())
			   .append("层，总层数").append(project.getHouseLayers().toString())
			   .append("层，建成于");
		}else{
			ret.append("    3、估价对象位于第null层，总层数null层，建成于");
		}
		
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy年");
		if(project.getHouseDate() != null){
			ret.append(fmtrq.format(project.getHouseDate()));
		}else{
			ret.append("未知");
		}
		
		if(project.getHouseConstruction() == null){
			ret.append("，未知");
		}else{
			switch(project.getHouseConstruction()){
				case 1:
					ret.append("，钢混结构。");
					break;
				case 2:
					ret.append("，混合结构。");
					break;
				case 3:
					ret.append("，砖木结构。");
					break;
				case 4:
					ret.append("，钢结构。");
					break;
				default:
						break;
			}
		}
		
		ret.append(FormatDecorationStatus());

		return ret.toString();
	}
	//格式化 房屋变现能力分析
	private String FormatDiyatishi(){
		if(project == null){
			return "";
		}
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer("    1、估价对象状况和房地产市场状况因时间变化对房地产抵押价值可能产生影响，在估价对象实物及区域因素不受意外损害，能正常维护使用，且未增加法定优先受偿款，房地产市场没有大的波动的情况下，预计估价报告使用有效期内，房地产抵押价值基本保持稳定。 ");
		ret.append(newLine);
		ret.append("    2、对抵押期间可能产生的房地产信贷风险，估价报告使用者应给予关注: 估价对象可能会由于房屋现状变更、环境变化以及房地产市场价格波动、房地产税费调整等原因导致房地产抵押价值减损。");
		ret.append(newLine);
		ret.append("    3、估价报告使用者应合理使用评估价值,关注处置房地产时快速变现及费用的影响,当房地产抵押估价报告出具后至抵押登记之间,是否会出现法定优先受偿权利。");
		ret.append(newLine);
		ret.append("    4、定期或者在房地产市场价格变化比较快时对房地产抵押价值进行再评估。");
		ret.append(newLine);
		ret.append("    5、估价对象为房地产转让抵押贷款，房地产交易完成后，房产性质变为商品房，相应其所分摊土地性质即转变为国有出让。");
		return ret.toString();
	}
	//格式化 房屋变现能力分析
	private String FormatBianxian(){
		if(project == null){
			return "";
		}
		String newLine = System.getProperty("line.separator");
		StringBuffer ret = new StringBuffer();
		//String ret = "";
		switch(project.getHouseUsing()){
			case 0:
				ret.append("    1、估价对象的合法用途未知，独立使用性较强，无法分割转让，估价对象所处物业管理");
			break;
			case 1:
				ret.append("    1、估价对象的合法用途为成套住宅，独立使用性较强，无法分割转让，估价对象所处物业管理");
				break;
			case 2:
				ret.append("    1、估价对象的合法用途为商业服务，独立使用性较强，无法分割转让，估价对象所处物业管理");
				break;
			case 3:
				ret.append("    1、估价对象的合法用途为办公，独立使用性较强，无法分割转让，估价对象所处物业管理");
				break;
			case 4:
				ret.append("    1、估价对象的合法用途为非成套住宅，独立使用性较强，无法分割转让，估价对象所处物业管理");
				break;
			case 5:
				ret.append("    1、估价对象的合法用途为酒店式公寓，独立使用性较强，无法分割转让，估价对象所处物业管理");
				break;
			default:
					break;
		}
		
		if(project.getWuyeManager() == 0 || project.getWuyeManager() == 3){
			ret.append("一般，");
		}else{
			ret.append("较好，");
		}
		
		if(project.getHouseArea() < 60){
			ret.append("面积较小");
		}else if(project.getHouseArea() < 144){
			ret.append("面积适中");
		}else{
			ret.append("面积较大");
		}
		ret.append("，短期变现能力一般。");
		ret.append(newLine);
		ret.append("    2、假定在价值时点拍卖或者变卖估价对象时，因存在短期内强制处分、潜在购买群体受到限制及心理排斥因素影响，最可能实现的价格一般比公开市场价格要低，估价人员根据目前类似房地产拍卖价格与正常市场价格的比例关系，确定假设在估价时点拍卖或者变卖时最可能实现的价格与评估的市场价值的比率为80%。");
		ret.append(newLine);
		ret.append("    3、处置房地产时，其变现的时间长短以及费用、税金的种类、数额和清偿顺序与处置方式和营销策略等因素有关。一般说来，以拍卖方式处置房地产时，变现时间较短，但变现价格一般较低，变现成本较高，要支付拍卖佣金、营业税及部分手续费。");
		ret.append(newLine);
		
		return ret.toString();
	}
	
	//得到勘察员 NIKE NAME
	private String GetKanchaName(){
		List<HistoryActivityInstance> haInstance = jBPMUtil.getHistoryService()  
			    .createHistoryActivityInstanceQuery().processInstanceId(project.getProcessInstanceId()).list();
		List<HistoryTask> ht = jBPMUtil.getHistoryService()
			    .createHistoryTaskQuery().executionId(project.getProcessInstanceId()).list();
		
		for(int i=0;i<haInstance.size();i++){
			HistoryActivityInstanceImpl tes = (HistoryActivityInstanceImpl)haInstance.get(i);
			if("现场勘查".equals(tes.getActivityName())){
				String assignee = ht.get(i).getAssignee();
				return userservice.GetNikeName(assignee);
			}
		}

		return "";
	}
	
	//得到评估员 NIKE NAME
	private String GetPingguName(){
		List<HistoryActivityInstance> haInstance = jBPMUtil.getHistoryService()  
			    .createHistoryActivityInstanceQuery().processInstanceId(project.getProcessInstanceId()).list();
		List<HistoryTask> ht = jBPMUtil.getHistoryService()
			    .createHistoryTaskQuery().executionId(project.getProcessInstanceId()).list();
		
		for(int i=0;i<haInstance.size();i++){
			HistoryActivityInstanceImpl tes = (HistoryActivityInstanceImpl)haInstance.get(i);
			if("预评撰写".equals(tes.getActivityName())){
				String assignee = ht.get(i).getAssignee();
				return userservice.GetNikeName(assignee);
			}
		}

		return "";
	}
	
	//出具正式结果报告
	public void ExportResult(){
		//String newLine = System.getProperty("line.separator");
		POIWordDocument pdoc = new POIWordDocument();
		Map<String,String> var = new HashMap<String,String>();
		
		var.put("projectCode","豫郑高地分评字[2014]"+project.getProjectCode().substring(project.getProjectCode().length()-7));
		var.put("sellerName", project.getSellerName());
		var.put("houseXy", project.getHouseXy());
		var.put("houseNumber",project.getHouseNumber());

		if(project.getHouseArea()!=null){
			var.put("houseArea",project.getHouseArea().toString());
		}
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy年MM月dd日");
		if(project.getKanchaDate() != null)
		{
			Date date = new Date();
			var.put("curDateex",fmtrq.format(project.getKanchaDate()));
			var.put("pingguriqi",fmtrq.format(project.getKanchaDate())+"至"+fmtrq.format(date));
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
			//按照编号命名文档名称
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
	
	//出具预评报告
	public void ExportYPWord(){
		String newLine = System.getProperty("line.separator");
		POIWordDocument pdoc = new POIWordDocument();
		Map<String,String> var = new HashMap<String,String>();
		
		//如果是公积金的话，输出格式需要有所调整
		if("公积金".equals(project.getProjectSource())){
			var.put("cell01", "买方姓名");
			var.put("sellerTel", project.getBuyerName());
			
		}else{
			var.put("cell01", "联系方式");
			var.put("sellerTel", project.getSellerTel());
		}
		
		var.put("projectCode", project.getProjectCode().substring(project.getProjectCode().length()-7));
		var.put("sellerName", project.getSellerName());
		
		var.put("houseXy", project.getHouseXy());
		var.put("houseNumber",project.getHouseNumber());
		var.put("wuyeAt",project.getWuyeAt());
		var.put("ownerName", ( (project.getOwnerName() == null ) ? "" : project.getOwnerName()));
		var.put("ownerNumber", ( (project.getOwnerNumber() == null ) ? "" : project.getOwnerNumber()));
		//户型
		StringBuffer houseUnit = new StringBuffer();
		houseUnit.append( (project.getHouseUnit1() == null ) ? "" : project.getHouseUnit1().toString()+"室 ");
		houseUnit.append( (project.getHouseUnit2() == null ) ? "" : project.getHouseUnit2().toString()+"厅 ");
		houseUnit.append( (project.getHouseUnit3() == null ) ? "" : project.getHouseUnit3().toString()+"厨 ");
		houseUnit.append( (project.getHouseUnit4() == null ) ? "" : project.getHouseUnit4().toString()+"卫 ");
		var.put("houseUnit",houseUnit.toString());
		
		var.put("houseLayers", ( (project.getHouseLayers() == null ) ? "" : project.getHouseLayers().toString()+"层"));
		var.put("houseAtLayer", ( (project.getHouseAtLayer() == null ) ? "" : project.getHouseAtLayer().toString()+"层"));
		
		switch(project.getHouseConstruction()){
			case 1:
				var.put("houseConstruction","钢混");
				break;
			case 2:
				var.put("houseConstruction","混合");
				break;
			case 3:
				var.put("houseConstruction","砖木");
				break;
			case 4:
				var.put("houseConstruction","钢");
				break;
			default:
					break;
		}
		if(project.getHouseArea()!=null){
			var.put("houseArea",project.getHouseArea().toString()+"平方米");
		}
		
		switch(project.getHouseUsing()){
		case 1:
			var.put("houseUsing","成套住宅");
			break;
		case 2:
			var.put("houseUsing","商业服务");
			break;
		case 3:
			var.put("houseUsing","办公");
			break;
		case 4:
			var.put("houseUsing","非成套住宅");
			break;
		case 5:
			var.put("houseUsing","酒店式公寓");
			break;
		default:
				break;
		}
		
		SimpleDateFormat fmtrq1 = new SimpleDateFormat("yyyy年");
		if(project.getHouseDate() != null)
			var.put("houseDate",fmtrq1.format(project.getHouseDate()));
		
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy年MM月dd日");
		if(project.getProjectDate() != null)
			var.put("projectDate",fmtrq.format(project.getProjectDate()));
		
		//勘查日期
		StringBuffer kanchaDate = new StringBuffer("估价目的：为确定房地产抵押贷款额度提供参考依据而评估房地产抵押价值");
		//String kanchaDate = "估价目的：为确定房地产抵押贷款额度提供参考依据而评估房地产抵押价值";
		kanchaDate.append(newLine);
		if(project.getKanchaDate() == null){
			kanchaDate.append("价值时点： 未知");
		}else{
			kanchaDate.append("价值时点： ").append(fmtrq.format(project.getKanchaDate()));
		}
		
		kanchaDate.append(newLine);
		kanchaDate.append("估价方法：比较法");
		
		var.put("curDate",kanchaDate.toString());
		
		//评估师、评估员签名
		UserFy userfy = (UserFy)session.getAttribute("user");
		StringBuffer pgPerson = new StringBuffer("评估机构：北京高地经典房地产评估有限责任公司河南省分公司");
		pgPerson.append(newLine);
		pgPerson.append("评估师：  李茂敬   陈同文");
		pgPerson.append(newLine);
		pgPerson.append("评估员：  ");
		pgPerson.append(GetPingguName());
		pgPerson.append(newLine);
		pgPerson.append("勘察人员：").append(GetKanchaName());
		var.put("curPerson",pgPerson.toString());
		
		switch(project.getHouseProperty()){
		case 1:
			var.put("houseProperty","商品房");
			break;
		case 2:
			var.put("houseProperty","经适房");
			break;
		case 3:
			var.put("houseProperty","房改房");
			break;
		case 4:
			var.put("houseProperty","安居工程");
			break;

		default:
				break;
		}
		
		switch(project.getHouseLandProperty()){
		case 1:
			var.put("houseLandProperty","国有出让");
			break;
		case 2:
			var.put("houseLandProperty","国有划拨");
			break;
		case 3:
			var.put("houseLandProperty","集体所有");
			break;
		default:
				break;
		}

		var.put("houseUse",project.getHouseUse());
		var.put("kanchaCurrentStatus",FormatKanchaCurrentstatus());
		
		StringBuffer kanchaMarketarea = new StringBuffer(houseMarketArea());
		kanchaMarketarea.append(newLine)
				.append("    估价对象所处区域类似房地产成交价在").append(project.getReferMin()).append(" ~ ")
				.append(project.getReferMax()).append("元/平方米之间，经综合分析与比较，估价对象快速变现参考价为")
				.append(project.getReferTotal()).append("万元。");
		
		var.put("kanchaMarketarea",kanchaMarketarea.toString());
		
		if(project.getHouseEvaluateTotal()!=null && project.getHouseEvaluateSingle()!=null){
			float totalMoney = project.getHouseEvaluateTotal().floatValue() / 10000;
			StringBuffer housemoney = new StringBuffer();
			totalMoney = (float)(Math.round(totalMoney*100))/100;
			housemoney.append("评估价值：￥").append(totalMoney).append("万元            单价：￥  ")
			          .append(project.getHouseEvaluateSingle()).append("元/㎡");
			
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
			//按照编号命名文档名称
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
	
	//返回文件相对存储路径
	private String GetFileStoragePath(Long projectCode,int nType) throws IOException  {
		//先得到根目录
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
		
		File saveFile = new File(fileDirs+userDirs); //创建文件
		// 创建目录
		if (!saveFile.exists()) {
			createDirs(saveFile); // 递归创建目录
			//saveFile.createNewFile(); // 创建当前文件
    	  //saveFile.mkdirs();// 目录不存在的情况下，创建目录。    
		}
		return userDirs;
	}
	//上传文件
	public void UploadFile(FileUploadEvent event){
		if(project == null)
			return ;
		 try {
			 String suffix = FilenameUtils.getExtension(event.getFile().getFileName()).toLowerCase();

			 int fileType=0; //默认为文件
			 if(	"jpg".equals(suffix) || 
					"jpeg".equals(suffix) || 
					"gif".equals(suffix) ||
					"bmp".equals(suffix) || 
					"png".equals(suffix) ){
				 fileType = 1;
			 }
			 /*  附件上传 */
			 String userPath = GetFileStoragePath(project.getIdprojectFy(),fileType);
			 ServletContext servletContext = (ServletContext) FacesContext  
		                .getCurrentInstance().getExternalContext().getContext();
			 String realPath = servletContext.getRealPath("") + userPath;
			 InputStream stream = event.getFile().getInputstream();
			 String fullPath = event.getFile().getFileName();
			 String fileName = fullPath.substring(fullPath.lastIndexOf("\\")+1);  
			 realPath = realPath + fileName;
			 File saveFile = new File(realPath);// 创建文件
		     //保存文件
			 FileOutputStream fos = new FileOutputStream(saveFile);
			 byte[] b = new byte[1024];  
		     int i = 0;
		     while ((i = stream.read(b)) > 0) {  
	            fos.write(b, 0, i);  
		     }
			 
			 fos.flush();
			 fos.close();
			 stream.close();
			 
			 //写入数据库记录
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
			 
			 FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " 上传成功！");  
		     FacesContext.getCurrentInstance().addMessage(null, msg);
		  }
		  catch (Exception ioe) {
			  FacesMessage msg = new FacesMessage("Failed", event.getFile().getFileName() + " 上传失败！");  
		      FacesContext.getCurrentInstance().addMessage(null, msg);
		  }
	}

	//附件下载
	public void DownloadFileAction(ActionEvent event) {
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("file");

		String filePath = "/data/"+file.getProjectcode().toString() + "/files/";
		FileUtil.downloadFile(filePath+file.getName(),file.getName());

	}
	//附件删除
	public void DeleteFileAction(ActionEvent event) {
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("file");
		ServletContext servletContext = (ServletContext) FacesContext  
	                .getCurrentInstance().getExternalContext().getContext();
		String realPath = servletContext.getRealPath("") + file.getFilepath();
		FileUtil.delete(realPath);
		projectFileService.DeleteFile(file.getIdprojectFileFy());
		listFiles = projectFileService.GetProjectFilesByCode(project.getIdprojectFy());
	}
	//图片删除
	public void DeletePicAction(ActionEvent event){
		ProjectFileFy file = (ProjectFileFy)event.getComponent().getAttributes().get("image");
		ServletContext servletContext = (ServletContext) FacesContext  
                .getCurrentInstance().getExternalContext().getContext();
		String realPath = servletContext.getRealPath("") + file.getFilepath();// picData.getFilepath();
		FileUtil.delete(realPath);
		projectFileService.DeleteFile(file.getIdprojectFileFy());
		listPics = projectFileService.GetProjectPicsByCode(project.getIdprojectFy());

	}
	
	//图片重命名
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
	 *		市场比较法函数
	 * 
	 * */
	
	//市场比较法计算参数初始化
	public List<MCInstance> getParameters() {
		if(project.getIdprojectFy() == null)
			return null;
		
		if(parameters == null){
			parameters = new ArrayList<MCInstance>();
			parameters.add(new MCInstance("可比实例A",instances.get(0).getPrice()));
			parameters.add(new MCInstance("可比实例B",instances.get(1).getPrice()));
			parameters.add(new MCInstance("可比实例C",instances.get(2).getPrice()));
			parameters.add(new MCInstance("估价对象",0));
			
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
	
	//市场比较法实例对比初始化	
	public List<MCInstance> getInstances() {
		//这里进行初始化
		if(project.getIdprojectFy() == null)
			return null;

		if(instances == null){
			instances = new ArrayList<MCInstance>();
			instances.add(new MCInstance("可比实例A",project));
			instances.add(new MCInstance("可比实例B",project));
			instances.add(new MCInstance("可比实例C",project));
			instances.add(new MCInstance("估价对象",project));
			
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

	
	//市场比较法实例的保存
	public void MCInstanceSave(){
		//遍历需要保存的变量保存
		for(MarketCompare mc:mcRecords){
			marketCompareService.AddMCParameter(mc);
		}
	}
	
	public void onInstanceCellEdit(CellEditEvent event) {
		//记录的参数变量
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
        		//如果是价格
        		parameters.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        		instances.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        	}
            //保存每一个编辑过的CELL
            MarketCompare mc = new MarketCompare(project.getIdprojectFy(),(short)(int)nColumn,
            		(short)(int)nRow,(short)1,newValue.toString());
            
            //遍历删除重复项
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
		//记录的参数变量
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
        		//如果是价格
        		parameters.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        		instances.get(nRow).setPrice(Integer.parseInt(newValue.toString()));
        	}
        	
            //保存每一个编辑过的CELL
            MarketCompare mc = new MarketCompare(project.getIdprojectFy(),(short)(int)nColumn,
            		(short)(int)nRow,(short)2,newValue.toString());
            
            //遍历删除重复项
            for(MarketCompare obj:mcRecords){
    			if ( obj.getColumn() == mc.getColumn() && obj.getRow() == mc.getRow() && obj.getType() == mc.getType() ){
    				mcRecords.remove(obj);
    			}
    		}
            mcRecords.add(mc);
        }  
		
	}
	
	//市场比较法计算
	private double CalcPrice(){

		double ret = 0;
		MCInstance A = parameters.get(0);
		MCInstance B = parameters.get(1);
		MCInstance C = parameters.get(2);
		MCInstance me = parameters.get(3);
		
		//日期参数
		double DateA = Double.parseDouble(A.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateB = Double.parseDouble(B.getSellDate()) / Double.parseDouble(me.getSellDate());
		double DateC = Double.parseDouble(C.getSellDate()) / Double.parseDouble(me.getSellDate());
		
		//交易情况参数
		//BigDecimal b1 = new BigDecimal(A.getSellStatus());
		
		double StatusA = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(A.getSellStatus());
		double StatusB = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(B.getSellStatus());
		double StatusC = Double.parseDouble(me.getSellStatus()) / Double.parseDouble(C.getSellStatus());
		//区域因素
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
		//个别因素
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
	
	//市场比较法保存并计算出最后的结果
	public void CalcResult(ActionEvent actionEvent){
		//保存修改项
		calcPrices = CalcPrice();
		double total = ( calcPrices - landPrices)  * (double)project.getHouseArea() - diyaMoney;
		BigDecimal b1 = new BigDecimal(calcPrices.toString());
		project.setHouseEvaluateSingle((int)Math.round( calcPrices - landPrices));
		project.setHouseEvaluateTotal((int)Math.round(total));
		return ;
	}
	
	//市场比较法保存并计算出最后的结果
	public void SaveResult(ActionEvent actionEvent){
		//保存修改项
		//计算结果中的几个参数也需要保存，SHIT～！
		//column 值 0 ，1，2，3，分别代表四个需要保存的变量
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
	
	//预评报告已出具
	public String CompletedYP(){
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("出具预评报告").uniqueResult();
			
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【预评已出具】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 出具正式报告");
			}
			
			project.setProjectComment(processOpinion);
			project.setCurStatus("出具正式报告");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "completedyp";
	}
	
	//正式报告已出具
	public String CompletedOfficial(){
		try
		{
			Task task = jBPMUtil.getTaskService().createTaskQuery().
					processInstanceId(project.getProcessInstanceId()).activityName("出具正式报告").uniqueResult();
			
			String taskid = task.getId();
			if(taskid != null){
				jBPMUtil.getTaskService().addTaskComment(task.getId(), "【正式报告已出具】    "+processOpinion);
				jBPMUtil.getTaskService().completeTask(taskid,"to 送达报告");
			}
			
			project.setProjectComment(processOpinion);
			project.setCurStatus("送达报告");
			projectservice.SaveOrUpdateProject(project);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "completedofficial";
	}
	
	//房屋市场区域分析文字输出
	private String houseMarketArea(){
		StringBuffer marketArea = new StringBuffer("    估价对象位于");
		marketArea.append(project.getHouseXy()).append("，东有").append(project.getRoadEast())
				.append("，南有").append(project.getRoadSouth())
				.append("，北有").append(project.getRoadNorth())
				.append("，西有").append(project.getRoadWest())
				.append("，四周主要有").append(project.getAroundMarket()).append("，道路通达，交通便捷")
				.append("，附近有").append(project.getAroundTraffic()).append("。");
		return marketArea.toString();
	}
	
	//房屋装修转义函数
	private String transformHouseDecoration(Short houseDecoration){
		String ret = "未知";
		switch(houseDecoration){
			case 1:
				ret = "简装";
				break;
			case 2:
				ret = "精装";
				break;
			case 3:
				ret = "毛坯";
				break;
			case 4:
				ret = "中装";
				break;
			case 5:
				ret = "豪华装修";
				break;
			default:
				break;
		}
		return ret;
	}
	//房屋现用途转义函数
	private String transformHouseUsing(Short houseUsing){
		String ret = "未知";
		switch(houseUsing){
			case 1:
				ret = "成套住宅";
				break;
			case 2:
				ret = "商业服务";
				break;
			case 3:
				ret = "办公";
				break;
			case 4:
				ret = "非成套住宅";
				break;
			case 5:
				ret = "酒店式公寓";
				break;
			default:
				break;
		}
		return ret;
	}
	//小区规模转义函数
	private String transformXiaoquExtent(Short xiaoquExtent){
		String ret = "未知";
		switch(xiaoquExtent){
			case 1:
				ret = "较小";
				break;
			case 2:
				ret = "中等";
				break;
			case 3:
				ret = "较大";
				break;
			default:
				break;
		}
		return ret;
	}
	
	//物业管理转义函数
	private String transformWuyeManager(Short wuyeManager){
		String ret = "未知";
		switch(wuyeManager){
			case 1:
				ret = "单位物业管理";
				break;
			case 2:
				ret = "专业物业管理";
				break;
			case 3:
				ret = "无";
				break;
			default:
				break;
		}
		return ret;
	}
	//小区绿化转义函数
	private String transformHouseLvhua(Short houseLvhua){
		String ret = "未知";
		switch(houseLvhua){
			case 1:
				ret = "无";
				break;
			case 2:
				ret = "一般";
				break;
			case 3:
				ret = "较好";
				break;
			case 4:
				ret = "良好";
				break;
			default:
				break;
		}
		return ret;
	}
	//房屋朝向转义函数
	private String transformHouseFace(Short houseFace){
		String ret = "未知";
		switch(houseFace){
			case 1:
				ret = "东";
				break;
			case 2:
				ret = "南";
				break;
			case 3:
				ret = "西";
				break;
			case 4:
				ret = "北";
				break;
			case 5:
				ret = "东南";
				break;
			case 6:
				ret = "东北";
				break;
			case 7:
				ret = "西南";
				break;
			case 8:
				ret = "西北";
				break;
			case 9:
				ret = "南北";
				break;
			case 10:
				ret = "东西";
				break;
			default:
				break;
		}
		return ret;
	}
	//房屋结构 转义函数
	private String transformHouseConstruction(Short houseConstruction){
		String ret = "未知";
		switch(houseConstruction){
			case 1:
				ret = "钢混";
				break;
			case 2:
				ret = "混合";
				break;
			case 3:
				ret = "砖木";
				break;
			case 4:
				ret = "钢";
				break;
			default:
				break;
		}
		return ret;
	}	
	
	//市场比较法计算
	private void fillCalcPrice(Map<String,String> var){

		// 0 ==> A  ||  1 ==> B   ||  2 ==> C 
		MCInstance A = parameters.get(0);
		MCInstance B = parameters.get(1);
		MCInstance C = parameters.get(2);
		MCInstance me = parameters.get(3);
		
		//日期参数
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
		
		//区域因素
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
		
		//个别因素
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
		
		//首先是比较因素的说明表，都是文字说明
		String strName = "";
		for(Integer i=0;i<4;i++){
			strName = i.toString()+"10";
			var.put(strName,
				((instances.get(i).getLocation() == null )?"未知":instances.get(i).getLocation()));
			strName = i.toString()+"11";
			var.put(strName,
				((instances.get(i).getSellDate() == null )?"未知":instances.get(i).getSellDate()));
			strName = i.toString()+"12";
			var.put(strName,
				((instances.get(i).getSellStatus() == null )?"未知":instances.get(i).getSellStatus()));
			strName = i.toString()+"13";
			var.put(strName,
				((instances.get(i).getPrice() == null )?"未知":instances.get(i).getPrice().toString()));
			strName = i.toString()+"14";
			var.put(strName,
				((instances.get(i).getBusinessdegree() == null )?"未知":instances.get(i).getBusinessdegree()));
			strName = i.toString()+"15";
			var.put(strName,
				((instances.get(i).getLandscape() == null )?"未知":instances.get(i).getLandscape()));
			strName = i.toString()+"16";
			var.put(strName,
				((instances.get(i).getTraffic() == null )?"未知":instances.get(i).getTraffic()));
			strName = i.toString()+"17";
			var.put(strName,
				((instances.get(i).getEnvironment() == null )?"未知":instances.get(i).getEnvironment()));
			strName = i.toString()+"18";
			var.put(strName,
				((instances.get(i).getBase() == null )?"未知":instances.get(i).getBase()));
			strName = i.toString()+"19";
			var.put(strName,
				((instances.get(i).getConstruction() == null )?"未知":instances.get(i).getConstruction()));
			strName = i.toString()+"110";
			var.put(strName,
				((instances.get(i).getUnit() == null )?"未知":instances.get(i).getUnit()));
			strName = i.toString()+"111";
			var.put(strName,
				((instances.get(i).getLight() == null )?"未知":instances.get(i).getLight()));
			strName = i.toString()+"112";
			var.put(strName,
				((instances.get(i).getDecoration() == null )?"未知":instances.get(i).getDecoration()));
			strName = i.toString()+"113";
			var.put(strName,
				((instances.get(i).getBuildDate() == null )?"未知":instances.get(i).getBuildDate()));
			strName = i.toString()+"114";
			var.put(strName,"与估价对象一致");
			strName = i.toString()+"115";
			var.put(strName,
				((instances.get(i).getCommunity() == null )?"未知":instances.get(i).getCommunity()));
			strName = i.toString()+"116";
			var.put(strName,
				((instances.get(i).getFace() == null )?"未知":instances.get(i).getFace()));
			strName = i.toString()+"117";
			var.put(strName,
				((instances.get(i).getLayer() == null )?"未知":instances.get(i).getLayer()));
			strName = i.toString()+"118";
			var.put(strName,"商品房");
			strName = i.toString()+"119";
			var.put(strName,
				((instances.get(i).getSupport() == null )?"未知":instances.get(i).getSupport()));
		}
		
		//计算参数表填充
		for(Integer i=0;i<4;i++){
			strName = i.toString()+"20";
			var.put(strName,
				((parameters.get(i).getPrice() == null )?"未知":parameters.get(i).getPrice().toString()));
			strName = i.toString()+"21";
			var.put(strName,
				((parameters.get(i).getSellDate() == null )?"未知":parameters.get(i).getSellDate()));
			strName = i.toString()+"22";
			var.put(strName,
				((parameters.get(i).getSellStatus() == null )?"未知":parameters.get(i).getSellStatus()));
			strName = i.toString()+"23";
			var.put(strName,
				((parameters.get(i).getBusinessdegree() == null )?"未知":parameters.get(i).getBusinessdegree()));
			strName = i.toString()+"24";
			var.put(strName,
				((parameters.get(i).getLandscape() == null )?"未知":parameters.get(i).getLandscape()));
			strName = i.toString()+"25";
			var.put(strName,
				((parameters.get(i).getTraffic() == null )?"未知":parameters.get(i).getTraffic()));
			strName = i.toString()+"26";
			var.put(strName,
				((parameters.get(i).getEnvironment() == null )?"未知":parameters.get(i).getEnvironment()));
			strName = i.toString()+"27";
			var.put(strName,
				((parameters.get(i).getBase() == null )?"未知":parameters.get(i).getBase()));
			strName = i.toString()+"28";
			var.put(strName,
				((parameters.get(i).getConstruction() == null )?"未知":parameters.get(i).getConstruction()));
			strName = i.toString()+"29";
			var.put(strName,
				((parameters.get(i).getUnit() == null )?"未知":parameters.get(i).getUnit()));
			strName = i.toString()+"210";
			var.put(strName,
				((parameters.get(i).getLight() == null )?"未知":parameters.get(i).getLight()));
			strName = i.toString()+"211";
			var.put(strName,
				((parameters.get(i).getDecoration() == null )?"未知":parameters.get(i).getDecoration()));
			strName = i.toString()+"212";
			var.put(strName,
				((parameters.get(i).getBuildDate() == null )?"未知":parameters.get(i).getBuildDate()));
			strName = i.toString()+"213";
			var.put(strName,"100");
			strName = i.toString()+"214";
			var.put(strName,
				((parameters.get(i).getCommunity() == null )?"未知":parameters.get(i).getCommunity()));
			strName = i.toString()+"215";
			var.put(strName,
				((parameters.get(i).getFace() == null )?"未知":parameters.get(i).getFace()));
			strName = i.toString()+"216";
			var.put(strName,
				((parameters.get(i).getLayer() == null )?"未知":parameters.get(i).getLayer()));
			strName = i.toString()+"217";
			var.put(strName,"100");
			strName = i.toString()+"218";
			var.put(strName,
				((instances.get(i).getSupport() == null )?"未知":parameters.get(i).getSupport()));
		}
		fillCalcPrice(var);
	}
	
	//导出正式报告
	//编号顺序：示例：{020}，从左边到右一共有三位，分别对应的是：
	//	第一位整数：0，示例A；1，示例B；2，示例C；3，股价对象；
	//	第二位整数：1，说明表；0，参数表；
	//	第三位整数：每一行的序号从0开始，到17，代表从坐落~配套设施
	public String ExportOfficialWord(){
		try
		{
			//按照编号命名文档名称
			POIWordDocument pdoc = new POIWordDocument();
			Map<String,String> var = new HashMap<String,String>();
			SimpleDateFormat fmYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat fmDate = new SimpleDateFormat("yyyy年MM月dd日");

			var.put("projectCode",
					((project.getProjectCode() == null )?"未知":project.getProjectCode().substring(4)));
			var.put("houseXy",
					((project.getHouseXy() == null )?"未知":project.getHouseXy()));
			var.put("houseArea",
					((project.getHouseArea() == null )?"未知":new java.text.DecimalFormat("#.00").format(project.getHouseArea())));
			var.put("houseHolds",
					((project.getHouseHolds() == null )?"未知":project.getHouseHolds().toString()));
			var.put("houseUnit1",
					((project.getHouseUnit1() == null )?"未知":project.getHouseUnit1().toString()));
			var.put("houseUnit2",
					((project.getHouseUnit2() == null )?"未知":project.getHouseUnit2().toString()));
			var.put("houseUnit3",
					((project.getHouseUnit3() == null )?"未知":project.getHouseUnit3().toString()));
			var.put("houseUnit4",
					((project.getHouseUnit4() == null )?"未知":project.getHouseUnit4().toString()));
			var.put("houseDecoration",
					((project.getHouseDecoration() == null )?"未知":transformHouseDecoration(project.getHouseDecoration())));
			var.put("houseLayers",
					((project.getHouseLayers() == null )?"未知":project.getHouseLayers().toString()));
			var.put("houseAtLayer",
					((project.getHouseAtLayer() == null )?"未知":project.getHouseAtLayer().toString()));
			var.put("houseUsing",
					((project.getHouseUsing() == null )?"未知":transformHouseUsing(project.getHouseUsing())));
			var.put("houseUse",
					((project.getHouseUse() == null )?"未知":project.getHouseUse()));
			var.put("houseDate",
					((project.getHouseDate() == null )?"未知":fmYear.format(project.getHouseDate())));
			var.put("buyerName",
					((project.getBuyerName() == null )?"未知":project.getBuyerName()));
			
			//如果新证号有的话，就用新证号的
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
						((project.getHouseNumber() == null )?"未知":project.getHouseNumber()));
				if(project.getProjectDate() != null){
					Calendar c = new GregorianCalendar();  
					c.setTime(project.getProjectDate());
					var.put("houseNumberDate",fmDate.format(c.getTime()));
					c.add(Calendar.DAY_OF_MONTH, 1); 
					var.put("evaluateDate",fmDate.format(c.getTime()));
				}
			}

			//估价时点,这个需要有一个推算过程，稍后修改
			var.put("beginEvaluateDate",
					((project.getKanchaDate() == null )?"未知":fmDate.format(project.getKanchaDate())));
			var.put("endEvaluateDate",fmDate.format(new java.util.Date()));
			//大写人民币
			var.put("houseEvaluateTotalRMB",
					((project.getHouseEvaluateTotal() == null )?"未知":Num2RMB.CNValueOf(project.getHouseEvaluateTotal().toString())));
			var.put("houseEvaluateTotal",
					((project.getHouseEvaluateTotal() == null )?"未知":(new java.text.DecimalFormat("#.00").format(project.getHouseEvaluateTotal().floatValue()/10000))));
			var.put("houseEvaluateSingle",
					((project.getHouseEvaluateSingle() == null )?"未知":project.getHouseEvaluateSingle().toString()));
			var.put("wuyeAt",
					((project.getWuyeAt() == null )?"未知":project.getWuyeAt()));
			var.put("xiaoquExtent",
					((project.getXiaoquExtent() == null )?"未知":transformXiaoquExtent(project.getXiaoquExtent())));
			var.put("wuyeManager",
					((project.getWuyeManager() == null )?"未知":transformWuyeManager(project.getWuyeManager())));
			var.put("houseLvhua",
					((project.getHouseLvhua() == null )?"未知":transformHouseLvhua(project.getHouseLvhua())));
			var.put("houseLayerHeight",
					((project.getHouseLayerHeight() == null )?"未知":project.getHouseLayerHeight().toString()));
			var.put("houseFace",
					((project.getHouseFace() == null )?"未知":transformHouseFace(project.getHouseFace())));
			var.put("houseConstruction",
					((project.getHouseConstruction() == null )?"未知":transformHouseConstruction(project.getHouseConstruction())));
			var.put("houseUnitNum",
					((project.getHouseUnitNum() == null )?"未知":project.getHouseUnitNum().toString()));
			var.put("houseHolds",
					((project.getHouseHolds() == null )?"未知":project.getHouseHolds().toString()));
			var.put("kanchaDate",
					((project.getKanchaDate() == null )?"未知":fmDate.format(project.getKanchaDate())));
			var.put("sellDate",
					((project.getSellDate() == null )?"未知":fmDate.format(project.getSellDate())));
			var.put("projectDate",
					((project.getProjectDate() == null )?"未知":fmDate.format(project.getProjectDate())));
			var.put("DecorationStatus",FormatDecorationStatus());
			var.put("houseMarketArea",houseMarketArea());
			fillParametersMap(var);
			
			if(listPics != null){
				for(ProjectFileFy obj:listPics){
					var.put(obj.getName(),obj.getFilepath()+obj.getName());
				}
			}
			
			//输出下载正式文档
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
