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
	
	private Date 	sellDateMin;			//�ɽ�������С
	private Date 	sellDateMax;			//�ɽ��������
	private Date 	buildDateMin;			//����������С
	private Date 	buildDateMax;			//�����������
	private Float 	houseAearMin;			//���������С
	private Float 	houseAearMax;			//����������
	private Integer 	prePriceMin;		//�����ܼ���С
	private Integer 	prePriceMax;		//�����ܼ����
	private Integer 	sellPriceMin;		//�ɽ��ܼ���С
	private Integer 	sellPriceMax;		//�ɽ��ܼ����
	private String		node;		//���̽ڵ�
	private List<String> pidlist;	//����ʵ��ID�б�
	
	private Date 	shouliDateMin;			//����������С
	private Date 	shouliDateMax;			//�����������

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

	//���ݲ�������QBL
	public DetachedCriteria GenerateDC(){
		
		DetachedCriteria detachedCrit = DetachedCriteria.forClass(AbstractProjectFy.class);
		//��ַ
		if( (this.getHouseXy() != null) && !"".equals(this.getHouseXy()) ){
			detachedCrit.add( Property.forName("houseXy").like("%" +this.getHouseXy()+"%") );
		}
		//����ʱ��
		if(sellDateMin != null){
			detachedCrit.add( Property.forName("sellDate").ge(sellDateMin) );
		}
		
		if(sellDateMax != null){
			detachedCrit.add( Property.forName("sellDate").le(sellDateMax) );
		}
		//����ʱ��
		if(buildDateMin != null){
			detachedCrit.add( Property.forName("houseDate").ge(buildDateMin) );
		}
		
		if(buildDateMax != null){
			detachedCrit.add( Property.forName("houseDate").le(buildDateMax) );
		}
		//���
		if(houseAearMin != null){
			detachedCrit.add( Property.forName("houseArea").ge(houseAearMin) );
		}
		
		if(houseAearMax != null){
			detachedCrit.add( Property.forName("houseArea").le(houseAearMax) );
		}

		//�ṹ
		if(this.getHouseConstruction() != null){
			detachedCrit.add( Property.forName("houseConstruction").eq(this.getHouseConstruction()) );
		}
		
		//װ��
		if(this.getHouseDecoration() != null){
			detachedCrit.add( Property.forName("houseDecoration").eq(this.getHouseDecoration()) );
		}
		
		//�����ܼ�
		if(prePriceMin != null){
			detachedCrit.add( Property.forName("houseEvaluateTotal").ge(prePriceMin) );
		}
		
		if(prePriceMax != null){
			detachedCrit.add( Property.forName("houseEvaluateTotal").le(prePriceMax) );
		}
		
		//�ɽ��ܼ�
		if(sellPriceMin != null){
			detachedCrit.add( Property.forName("houseFinalTotal").ge(sellPriceMin) );
		}
		
		if(sellPriceMax != null){
			detachedCrit.add( Property.forName("houseFinalTotal").le(sellPriceMax) );
		}
		
		//�Ƿ��շ�
		if(this.getPingguMoney() != null){
			if(this.getPingguMoney() == 0)
				detachedCrit.add( Property.forName("pingguMoney").eq(0) );
			else
				detachedCrit.add( Property.forName("pingguMoney").gt(0) );
		}
		//����ʵ���б�Ϊ��
		if(pidlist != null){
			detachedCrit.add( Property.forName("processInstanceId").in(pidlist) );
		}
		
		//��Ų�ѯ
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
		
		//��������
		if(shouliDateMin != null){
			detachedCrit.add( Property.forName("shouliDate").ge(shouliDateMin) );
		}
		
		if(shouliDateMax != null){
			detachedCrit.add( Property.forName("shouliDate").le(shouliDateMax) );
			
		}
		
		//������Դ
		if(this.getProjectSource() != null){
			detachedCrit.add( Property.forName("projectSource").like("%" +this.getProjectSource()+"%") );
		}
		
		detachedCrit.addOrder(Property.forName("idprojectFy").desc());
		return detachedCrit;
	}
	public SearchProject(){
	}
	
	
}
