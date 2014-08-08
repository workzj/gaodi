package com.fy.viewer;

import java.text.SimpleDateFormat;
import com.fy.database.ProjectFy;

public class MCInstance {
	public MCInstance(String name,Integer price) {
		super();
		this.name = name;
		this.location = "-";
		this.sellDate = "100";
		this.sellStatus = "100";
		this.price = price;
		this.businessdegree = "100";
		this.landscape = "100";
		this.traffic = "100";
		this.environment = "100";
		this.base = "100";
		this.construction = "100";
		this.unit = "100";
		this.light = "100";
		this.decoration = "100";
		this.face = "100";
		this.buildDate = "100";
		this.community = "100";
		this.layer = "100";
		this.support = "100";
	}
	
	public MCInstance(String name,ProjectFy fy) {
		super();
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy��MM��");
		this.name = name;
		this.location = (fy.getWuyeAt()==null) ? "δ֪" : fy.getWuyeAt();
		this.sellDate = (fy.getSellDate() == null ) ? "δ֪" : fmtrq.format(fy.getSellDate());
		this.sellStatus = (fy.getSellMode() == 1) ? "�������г���" : "���������г���";
		this.price = (fy.getHouseFinalSingle() == null) ? 0 : fy.getHouseFinalSingle();
		this.businessdegree = "ͬһ����";
		this.landscape = "����۶�����ͬ";
		this.traffic = "����۶�����ͬ";
		this.environment = "����۶���һ��";
		this.base = "����۶���һ��";
		if(fy.getHouseConstruction() == null){
			this.construction += "δ֪";
		}else{
			switch(fy.getHouseConstruction()){
				case 1:
					this.construction = "�ֻ�ṹ";
					break;
				case 2:
					this.construction = "ש��ṹ";
					break;
				case 3:
					this.construction = "שľ�ṹ";
					break;
				case 4:
					this.construction = "�ֽṹ";
					break;
				default:
						break;
			}
		}
		this.unit = "����";
		this.light = "һ��";
		if(fy.getHouseDecoration() == null){
			this.decoration += "δ֪";
		}else{
			switch(fy.getHouseDecoration()){
				case 1:
					this.decoration = "��װ";
					break;
				case 2:
					this.decoration = "��װ";
					break;
				case 3:
					this.decoration = "ë��";
					break;
				case 4:
					this.decoration = "��װ";
					break;
				case 5:
					this.decoration = "����װ��";
					break;
				default:
						break;
			}
		}
		fmtrq.applyPattern("yyyy��");
		this.buildDate = (fy.getHouseDate() == null ) ? "δ֪" : fmtrq.format(fy.getHouseDate());
		if(fy.getHouseFace() == null){
			this.face = "δ֪";
		}else{
			switch(fy.getHouseFace()){
				case 1:
					this.face = "������";
					break;
				case 2:
					this.face = "�ϳ���";
					break;
				case 3:
					this.face = "������";
					break;
				case 4:
					this.face = "������";
					break;
				case 5:
					this.face = "���ϳ���";
					break;
				case 6:
					this.face = "��������";
					break;
				case 7:
					this.face = "���ϳ���";
					break;
				case 8:
					this.face = "��������";
					break;
				case 9:
					this.face = "�ϱ�����";
					break;
				case 10:
					this.face = "��������";
					break;
				default:
						break;
			}
		}
		
		this.community ="һ��";
		
		if(fy.getHouseLayers() == null || fy.getHouseAtLayer() == null){
			this.layer = "δ֪";
		}else{
			this.layer = fy.getHouseAtLayer().toString() + "�㡢��"+fy.getHouseLayers().toString()+"��";
		}
		this.support = "˫��";
		
	}//end of constration class fun

	private String name;		// �ɱ�ʵ��A;
	private String location;	//����;
	private String sellDate; 	//��������;
	private String sellStatus;	//�������;
	private Integer price;		//�ɽ�����;
	private String businessdegree;		//�̷�������;
	private String landscape;		//����;
	private String traffic;			//��ͨ;
	private String environment;		//���򻷾�;
	private String base;//������ʩ;
	private String construction;//�����ṹ;
	private String unit;//����;
	private String light;//�ɹ⡢ͨ��;
	private String decoration;//װ��;
	private String buildDate;//�������;
	private String community;//С������
	private String face;//����;
	private String layer;//¥��;
	private String support;//������ʩ��
	
	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSellDate() {
		return sellDate;
	}
	public void setSellDate(String sellDate) {
		this.sellDate = sellDate;
	}
	public String getSellStatus() {
		return sellStatus;
	}
	public void setSellStatus(String sellStatus) {
		this.sellStatus = sellStatus;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getBusinessdegree() {
		return businessdegree;
	}
	public void setBusinessdegree(String businessdegree) {
		this.businessdegree = businessdegree;
	}
	public String getLandscape() {
		return landscape;
	}
	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getConstruction() {
		return construction;
	}
	public void setConstruction(String construction) {
		this.construction = construction;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getDecoration() {
		return decoration;
	}
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}
	public String getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
}
