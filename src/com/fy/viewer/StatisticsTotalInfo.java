package com.fy.viewer;

public class StatisticsTotalInfo {
	private String 	purpose;		//����Ŀ��
	private Integer number;			//�ܼ���
	private Float 	area;		//�������
	private Long 	price;		//�ܼ�ֵ
	
	public StatisticsTotalInfo(String purpose, Integer number,
			Float area, Long price) {
		super();
 
		this.purpose = purpose;
		this.number = number;
		this.area = area;
		this.price = price;
	}
	
	public StatisticsTotalInfo(String purpose) {
		super();
 
		this.purpose = purpose;
		this.number = 0;
		this.area = (float)0;
		this.price = (long)0;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	//�Զ��庯��
	public void addNumber() {
		this.number ++;
	}
	
	public void addPrice(Integer price) {
		this.price += price;
	}
	
	public void addArea(Float area) {
		this.area += area;
	}
}
