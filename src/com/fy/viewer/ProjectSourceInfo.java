package com.fy.viewer;

import java.util.Date;

public class ProjectSourceInfo {

		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getChargeNum() {
		return chargeNum;
	}
	public void setChargeNum(Integer chargeNum) {
		this.chargeNum = chargeNum;
	}
	public Integer getUnChargeNum() {
		return unChargeNum;
	}
	public void setUnChargeNum(Integer unChargeNum) {
		this.unChargeNum = unChargeNum;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	public void AddendNum(){
		this.endNum ++;
	}
	
	public void AddkanchaNum(){
		this.kanchaNum++;
	}
	
	public void AddyupingNum(){
		this.yupingNum++;
	}
	
	public void AddshenchaNum(){
		this.shenchaNum++;
	}
	
	public void AddshenpiNum(){
		this.shenpiNum++;
	}
	
	public void AddyupingOverNum(){
		this.yupingOverNum++;
	}
	
	public void AddofficialOverNum(){
		this.officialOverNum++;
	}
	
	public Integer getEndNum() {
		return endNum;
	}
	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
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
	public ProjectSourceInfo(String name, Integer chargeNum,
			Integer unChargeNum, Integer totalMoney) {
		super();
		this.name = name;
		this.chargeNum = chargeNum;
		this.unChargeNum = unChargeNum;
		this.totalMoney = totalMoney;
		
		this.endNum = 0;
		this.kanchaNum = 0;
		this.yupingNum = 0;
		this.shenchaNum = 0;
		this.shenpiNum = 0;
		this.yupingOverNum = 0;
		this.officialOverNum = 0;
	}
	
	private String name;
	private Integer chargeNum;		//������Ŀ
	private Integer unChargeNum;	//δ������Ŀ
	private Integer totalMoney;		//���շѽ��
	
	private Integer endNum;			//�����
	private Integer kanchaNum;		//���ڿ���
	private Integer yupingNum;		//����Ԥ��
	private Integer shenchaNum;		//�������
	private Integer shenpiNum;		//��������
	private Integer yupingOverNum;	//�ѳ���Ԥ��
	private Integer officialOverNum;	//�ѳ�����ʽ
}
