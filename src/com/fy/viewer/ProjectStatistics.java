package com.fy.viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.fy.database.ProjectFy;
import com.fy.database.UserFy;
import com.fy.services.ProjectService;
import com.fy.services.SearchProject;
import com.fy.services.UserService;
import com.fy.servicesImpl.JBPMUtil;
import javax.faces.event.ActionEvent;
import org.jbpm.api.history.*;
import com.fy.utils.xwpf.ExcelExportUtil;
//案件统计
public class ProjectStatistics {

	private ProjectService projectservice;
	private JBPMUtil jBPMUtil;
	private SearchProject searchPj;
	private UserService userservice;
	
	//与前端交互的数据
	//业务逻辑
	private Integer totalNum = 0;		//总受理案件数
	private Integer kanchaNum = 0;		//正在勘查
	private Integer yupingNum = 0;		//正在预评
	private Integer shenchaNum = 0;		//正在审查
	private Integer shenpiNum = 0;		//正在审批
	private Integer yupingOverNum = 0;		//已出具预评
	private Integer officialOverNum = 0;	//已出具正式报告
	private Integer endNum = 0;			//已归档
	private Integer chargeNum = 0;			//已收费
	private Integer sendNum = 0;			//已送达
	
	//收费统计
	List<ProjectSourceInfo> sourceInfo;
	List<ProjectTaskInfo> 	taskInfo;
	List<StatisticsTotalInfo> 	totalInfo;
	
	//新增收费来源过滤
	private String source;				//业务来源

	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public List<StatisticsTotalInfo> getTotalInfo() {
		return totalInfo;
	}


	public void setTotalInfo(List<StatisticsTotalInfo> totalInfo) {
		this.totalInfo = totalInfo;
	}


	public SearchProject getSearchPj() {
		if(searchPj == null){
			searchPj = new SearchProject();
		}
		return searchPj;
	}


	public List<ProjectTaskInfo> getTaskInfo() {
		return taskInfo;
	}


	public void setTaskInfo(List<ProjectTaskInfo> taskInfo) {
		this.taskInfo = taskInfo;
	}


	public void setSearchPj(SearchProject searchPj) {
		this.searchPj = searchPj;
	}

	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
	}
	
	public ProjectService getProjectService() {
		return projectservice;
	}

	public void setProjectService(ProjectService newValue) {
		this.projectservice = newValue;
		
	}
	
	public UserService getUserService() {
		return userservice;
	}

	public void setUserService(UserService newValue) {
		this.userservice = newValue;
	}

	public Integer getTotalNum() {
		return totalNum;
	}


	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}


	public Integer getKanchaNum() {
		return kanchaNum;
	}


	public void setKanchaNum(Integer kanchaNum) {
		this.kanchaNum = kanchaNum;
	}


	public Integer getYupingNum() {
		return yupingNum;
	}


	public void setYupingNum(Integer yupingNum) {
		this.yupingNum = yupingNum;
	}


	public Integer getShenchaNum() {
		return shenchaNum;
	}


	public void setShenchaNum(Integer shenchaNum) {
		this.shenchaNum = shenchaNum;
	}


	public Integer getShenpiNum() {
		return shenpiNum;
	}


	public void setShenpiNum(Integer shenpiNum) {
		this.shenpiNum = shenpiNum;
	}

	public Integer getYupingOverNum() {
		return yupingOverNum;
	}


	public void setYupingOverNum(Integer yupingOverNum) {
		this.yupingOverNum = yupingOverNum;
	}


	public Integer getOfficialOverNum() {
		return officialOverNum;
	}


	public void setOfficialOverNum(Integer officialOverNum) {
		this.officialOverNum = officialOverNum;
	}


	public Integer getSendNum() {
		return sendNum;
	}


	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}


	public Integer getEndNum() {
		return endNum;
	}


	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}


	public Integer getChargeNum() {
		return chargeNum;
	}


	public void setChargeNum(Integer chargeNum) {
		this.chargeNum = chargeNum;
	}


	public List<ProjectSourceInfo> getSourceInfo() {
		return sourceInfo;
	}


	public void setSourceInfo(List<ProjectSourceInfo> sourceInfo) {
		this.sourceInfo = sourceInfo;
	}


	//开始统计
	public void CountAction(ActionEvent actionEvent){
		totalNum = 0;		//总受理案件数
		kanchaNum = 0;		//正在勘查
		yupingNum = 0;		//正在预评
		shenchaNum = 0;		//正在审查
		shenpiNum = 0;		//正在审批
		yupingOverNum = 0;	//已出具预评
		officialOverNum = 0;//已出具正式报告
		endNum = 0;			//已归档
		chargeNum = 0;			//已收费
		sendNum = 0;		//已送达
		
		List<ProjectFy> ls = projectservice.SearchProject(searchPj);
		sourceInfo = new ArrayList<ProjectSourceInfo>();
		Map<String,Integer> pjSource = new HashMap();   //MAP存放了，业务来源和数组ID的对应关系
		
		totalInfo = new ArrayList<StatisticsTotalInfo>();
		Map<String,Integer> purPose = new HashMap();   //MAP存放了，估价目的和数组ID的对应关系
		
		for(ProjectFy obj:ls){
			if(source != null) {
				if(obj.getProjectSource() == null || obj.getProjectSource().indexOf(source)==-1){
					//如果没有开启业务来源过滤、或者开启了包含该关键字
					continue;
				}
			}
			//判断是否有该来源
			if( !pjSource.containsKey(obj.getProjectSource()) ){
				//如果没有
				if( sourceInfo.add(new ProjectSourceInfo(obj.getProjectSource(),0,0,0)) ){
					pjSource.put(obj.getProjectSource(),sourceInfo.size()-1 );
				}
			}
			//获取项目来源类
			ProjectSourceInfo source = sourceInfo.get(pjSource.get(obj.getProjectSource()));
	
			//判断是否有该估价目的
			String strPurpose = obj.getEvaluatePurpose();
			if(strPurpose == null){
				strPurpose = "null";
			}
			if( !purPose.containsKey(strPurpose) ){
				//如果没有
				if( totalInfo.add(new StatisticsTotalInfo(strPurpose))){
					purPose.put(strPurpose,totalInfo.size()-1 );
				}
			}
			//获取估价目的类
			StatisticsTotalInfo total = totalInfo.get(purPose.get(strPurpose));
			
			total.addNumber();
			if(obj.getHouseEvaluateTotal() != null ){
				total.addPrice(obj.getHouseEvaluateTotal());
			}
			if(obj.getHouseArea() != null){
				total.addArea(obj.getHouseArea());
			}

			//判断案件是否收费
			if(obj.getPingguMoney() != null && obj.getPingguMoney() > 0 ){
				chargeNum ++;
				source.setChargeNum(source.getChargeNum()+1);
				source.setTotalMoney(source.getTotalMoney() + obj.getPingguMoney());
			}else{
				source.setUnChargeNum(source.getUnChargeNum()+1);
			}
			
			//统计个数
			if(obj.getCurStatus() != null){
				if("已归档".equals(obj.getCurStatus()) ){
					sendNum ++;
					endNum ++;
					source.AddendNum();
				}else if("现场勘查".equals(obj.getCurStatus()) ){
					source.AddkanchaNum();
					kanchaNum ++;
				}else if("预评撰写".equals(obj.getCurStatus()) ){
					yupingNum ++;
					source.AddyupingNum();
				}else if("预评审查".equals(obj.getCurStatus()) ){
					shenchaNum ++;
					source.AddshenchaNum();
				}else if("预评审批".equals(obj.getCurStatus()) ){
					shenpiNum ++;
					source.AddshenpiNum();
				}else if("出具预评报告".equals(obj.getCurStatus()) ){
					
				}else if("出具正式报告".equals(obj.getCurStatus()) ){
					yupingOverNum ++;
					source.AddyupingOverNum();
				}else if("送达报告".equals(obj.getCurStatus()) ){
					officialOverNum ++;
					source.AddofficialOverNum();
				}else if("直接归档".equals(obj.getCurStatus()) ){
					endNum ++;
					source.AddendNum();
				}
			}
			totalNum ++;
		}
		
		/*
		sourceInfo = new ArrayList<ProjectSourceInfo>();
		
		Iterator iter = pjSource.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry<String,ProjectSourceInfo> entry = (Map.Entry<String,ProjectSourceInfo>) iter.next();
		    sourceInfo.add(entry.getValue()); 
		} // end of while
		*/
		//查询任务完成
		taskInfo = new ArrayList<ProjectTaskInfo>();
		List<UserFy> listuser = userservice.GetAlluser();
		
		for(UserFy usr:listuser){
			if("kancha".equals(usr.getRolename()) || "pinggu".equals(usr.getRolename()) ){
				//只有勘察员和评估员才统计
				List<String> pids = new ArrayList<String>();
				
				List<HistoryTask> ht = jBPMUtil.getHistoryService().createHistoryTaskQuery().
						startedBefore(searchPj.getShouliDateMax()).
						startedAfter(searchPj.getShouliDateMin()).
						assignee(usr.getName()).list();
					if(ht != null){
						ProjectTaskInfo ti = new ProjectTaskInfo(usr.getNikename(),0,0,(long)0,usr.getRolename());
						for(HistoryTask tmp:ht){
							if("to 现场勘查".equals(tmp.getOutcome())){
								continue;
							}
							if("completed".equals(tmp.getState())){
								ti.setCompletedNum(ti.getCompletedNum()+1);
								pids.add(tmp.getExecutionId());
							}else{
								ti.setProcessNum(ti.getProcessNum()+1);
							}
							ti.setTotalTime(ti.getTotalTime() + tmp.getDuration());
						}
						ti.setPids(pids);
						taskInfo.add(ti);
					}
			}
			
		}
	}// end of CountAction

	//导出工作人员 业务完成明细
	public void ExportWorker(ActionEvent event) {
		String assignee = (String)event.getComponent().getAttributes().get("assignee");
		if(assignee ==null)
			return;
		
		List<String> pids = null;
		for(ProjectTaskInfo tmp:taskInfo){
			if( tmp.getAssignee().equals(assignee) ){
				pids = tmp.getPids();
			}
		}
		
		final List<String[]> mce = new ArrayList<String[]>();
		String[] export = null;
		String[] headinfo = { "序号", "档案号", "坐落", "产权人", "建筑面积", "评估总价", "评估单价","支行名称","工作人员姓名"};
		
		try {
			searchPj.setPidlist(pids);
			List<ProjectFy> ls = projectservice.SearchProject(searchPj);
			Integer index = 1;
			for(ProjectFy obj:ls){
				export = new String[9];
				export[0] = index.toString();
				
				if(obj.getProjectCode() != null)
					export[1] = obj.getProjectCode();
				else
					export[1] = "";
				
				if(obj.getHouseXy() != null)
					export[2] = obj.getHouseXy();
				else
					export[2] = "";
				
				if(obj.getSellerName() != null)
					export[3] = obj.getSellerName();
				else
					export[3] = "";
				
				if(obj.getHouseArea() != null)
					export[4] = Float.toString(obj.getHouseArea());
				else
					export[4] = "";
				
				if(obj.getHouseEvaluateTotal() != null)
					export[5] = obj.getHouseEvaluateTotal().toString();
				else
					export[5] = "";
				
				if(obj.getHouseEvaluateSingle() != null)
					export[6] = obj.getHouseEvaluateSingle().toString();
				else
					export[6] = "";
				
				if(obj.getProjectSource() != null)
					export[7] = obj.getProjectSource();
				else
					export[7] = "";
				
				//工作人员名称
				export[8] = assignee;
				
				index ++;
				mce.add(export);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ExcelExportUtil ee = new ExcelExportUtil();
		String filename = "worker.xls";
		ee.outputExcel(filename, mce, headinfo);
		searchPj.setPidlist(null);
		
	}// end of ExportWorker
	
	public void ExportSource() {
		ExportSource(null);
	}
	
	public void ExportYPSource() {
		ExportSource("出具预评报告");
	}
	
	public void ExportZSSource() {
		ExportSource("出具正式报告");
	}
	
	//加上条件
	public void ExportSource(String curStatus) {
		final List<String[]> mce = new ArrayList<String[]>();
		String[] export = null;
		String[] headinfo = { "序号", "档案号", "坐落", "产权人", "建筑面积", "评估总价", "评估单价","支行名称","客户经理"};
		
		try {
			searchPj.setProjectSource(source);
			searchPj.setNode(curStatus);
			List<ProjectFy> ls = projectservice.SearchProject(searchPj);
			Integer index = 1;
			for(ProjectFy obj:ls){
				export = new String[9];
				export[0] = index.toString();
				
				if(obj.getProjectCode() != null)
					export[1] = obj.getProjectCode();
				else
					export[1] = "";
				
				if(obj.getHouseXy() != null)
					export[2] = obj.getHouseXy();
				else
					export[2] = "";
				
				if(obj.getSellerName() != null)
					export[3] = obj.getSellerName();
				else
					export[3] = "";
				
				if(obj.getHouseArea() != null)
					export[4] = Float.toString(obj.getHouseArea());
				else
					export[4] = "";
				
				if(obj.getHouseEvaluateTotal() != null)
					export[5] = obj.getHouseEvaluateTotal().toString();
				else
					export[5] = "";
				
				if(obj.getHouseEvaluateSingle() != null)
					export[6] = obj.getHouseEvaluateSingle().toString();
				else
					export[6] = "";
				
				if(obj.getProjectSource() != null)
					export[7] = obj.getProjectSource();
				else
					export[7] = "";
				
				//客户经理
				export[8] = "";
				
				index ++;
				mce.add(export);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ExcelExportUtil ee = new ExcelExportUtil();
		String filename = "banks.xls";
		ee.outputExcel(filename, mce, headinfo);
		searchPj.setProjectSource(null);
	}
}
