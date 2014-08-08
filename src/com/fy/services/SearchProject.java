package com.fy.services;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.fy.database.AbstractProjectFy;

public class SearchProject extends AbstractProjectFy implements 
java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8016784201523291979L;
	
	private Date 	sellDateMin;			//成交日期最小
	private Date 	sellDateMax;			//成交日期最大
	private Date 	buildDateMin;			//建成日期最小
	private Date 	buildDateMax;			//建成日期最大
	private Float 	houseAearMin;			//建筑面积最小
	private Float 	houseAearMax;			//建筑面积最大
	private Integer 	prePriceMin;		//评估总价最小
	private Integer 	prePriceMax;		//评估总价最大
	private Integer 	sellPriceMin;		//成交总价最小
	private Integer 	sellPriceMax;		//成交总价最大
	private String		node;		//流程节点
	private List<String> pidlist;	//流程实例ID列表
	
	private Date 	shouliDateMin;			//受理日期最小
	private Date 	shouliDateMax;			//受理日期最大

	public Date getShouliDateMin() {
		return shouliDateMin;
	}

	public void setShouliDateMin(Date shouliDateMin) {
		this.shouliDateMin = shouliDateMin;
	}

	public Date getShouliDateMax() {
		return shouliDateMax;
	}

	public void setShouliDateMax(Date shouliDateMax) {
		this.shouliDateMax = shouliDateMax;
	}

	public List<String> getPidlist() {
		return pidlist;
	}

	public void setPidlist(List<String> pidlist) {
		this.pidlist = pidlist;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Integer getPrePriceMin() {
		return prePriceMin;
	}

	public void setPrePriceMin(Integer prePriceMin) {
		this.prePriceMin = prePriceMin;
	}

	public Integer getPrePriceMax() {
		return prePriceMax;
	}

	public void setPrePriceMax(Integer prePriceMax) {
		this.prePriceMax = prePriceMax;
	}

	public Integer getSellPriceMin() {
		return sellPriceMin;
	}

	public Date getSellDateMin() {
		return sellDateMin;
	}

	public void setSellDateMin(Date sellDateMin) {
		this.sellDateMin = sellDateMin;
	}

	public Date getSellDateMax() {
		return sellDateMax;
	}

	public void setSellDateMax(Date sellDateMax) {
		this.sellDateMax = sellDateMax;
	}

	public void setSellPriceMin(Integer sellPriceMin) {
		this.sellPriceMin = sellPriceMin;
	}

	public Integer getSellPriceMax() {
		return sellPriceMax;
	}

	public void setSellPriceMax(Integer sellPriceMax) {
		this.sellPriceMax = sellPriceMax;
	}
	
	public Date getBuildDateMin() {
		return buildDateMin;
	}

	public void setBuildDateMin(Date buildDateMin) {
		this.buildDateMin = buildDateMin;
	}

	public Date getBuildDateMax() {
		return buildDateMax;
	}

	public void setBuildDateMax(Date buildDateMax) {
		this.buildDateMax = buildDateMax;
	}

	public Float getHouseAearMin() {
		return houseAearMin;
	}

	public void setHouseAearMin(Float houseAearMin) {
		this.houseAearMin = houseAearMin;
	}

	public Float getHouseAearMax() {
		return houseAearMax;
	}

	public void setHouseAearMax(Float houseAearMax) {
		this.houseAearMax = houseAearMax;
	}

	//根据参数生成QBL
	public DetachedCriteria GenerateDC(){
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(AbstractProjectFy.class);
		//地址
		if( (this.getHouseXy() != null) && !"".equals(this.getHouseXy()) ){
			detachedCrit.add( Property.forName("houseXy").like("%" +this.getHouseXy()+"%") );
		}
		//交易时间
		if(sellDateMin != null){
			detachedCrit.add( Property.forName("sellDate").ge(sellDateMin) );
		}
		
		if(sellDateMax != null){
			detachedCrit.add( Property.forName("sellDate").le(sellDateMax) );
		}
		//建成时间
		if(buildDateMin != null){
			detachedCrit.add( Property.forName("houseDate").ge(buildDateMin) );
		}
		
		if(buildDateMax != null){
			detachedCrit.add( Property.forName("houseDate").le(buildDateMax) );
		}
		//面积
		if(houseAearMin != null){
			detachedCrit.add( Property.forName("houseArea").ge(houseAearMin) );
		}
		
		if(houseAearMax != null){
			detachedCrit.add( Property.forName("houseArea").le(houseAearMax) );
		}

		//结构
		if(this.getHouseConstruction() != null){
			detachedCrit.add( Property.forName("houseConstruction").eq(this.getHouseConstruction()) );
		}
		
		//装修
		if(this.getHouseDecoration() != null){
			detachedCrit.add( Property.forName("houseDecoration").eq(this.getHouseDecoration()) );
		}
		
		//评估总价
		if(prePriceMin != null){
			detachedCrit.add( Property.forName("houseEvaluateTotal").ge(prePriceMin) );
		}
		
		if(prePriceMax != null){
			detachedCrit.add( Property.forName("houseEvaluateTotal").le(prePriceMax) );
		}
		
		//成交总价
		if(sellPriceMin != null){
			detachedCrit.add( Property.forName("houseFinalTotal").ge(sellPriceMin) );
		}
		
		if(sellPriceMax != null){
			detachedCrit.add( Property.forName("houseFinalTotal").le(sellPriceMax) );
		}
		
		//是否收费
		if(this.getPingguMoney() != null){
			if(this.getPingguMoney() == 0)
				detachedCrit.add( Property.forName("pingguMoney").eq(0) );
			else
				detachedCrit.add( Property.forName("pingguMoney").gt(0) );
		}
		//流程实例列表不为空
		if(pidlist != null){
			detachedCrit.add( Property.forName("processInstanceId").in(pidlist) );
		}
		
		//编号查询
		if(this.getProjectCode() != null){
			detachedCrit.add( Property.forName("projectCode").like("%" +this.getProjectCode()+"%") );
		}
		
		//
		if(this.getBuyerName() != null){
			detachedCrit.add( Property.forName("buyerName").like("%" +this.getBuyerName()+"%") );
		}
		
		//
		if(this.getSellerName() != null){
			detachedCrit.add( Property.forName("sellerName").like("%" +this.getSellerName()+"%") );
		}
		
		//
		if(this.getHouseNumber() != null){
			detachedCrit.add( Property.forName("houseNumber").like("%" +this.getHouseNumber()+"%") );
		}
		
		//
		if(this.getNode() != null){
			detachedCrit.add( Property.forName("curStatus").eq(this.getNode()) );
		}
		
		//受理日期
		if(shouliDateMin != null){
			detachedCrit.add( Property.forName("shouliDate").ge(shouliDateMin) );
		}
		
		if(shouliDateMax != null){
			detachedCrit.add( Property.forName("shouliDate").le(shouliDateMax) );
			
		}
		
		//案件来源
		if(this.getProjectSource() != null){
			detachedCrit.add( Property.forName("projectSource").like("%" +this.getProjectSource()+"%") );
		}
		
		detachedCrit.addOrder(Property.forName("idprojectFy").desc());
		return detachedCrit;
	}
	public SearchProject(){
	}
	
	
}
