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
		SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy年MM月");
		this.name = name;
		this.location = (fy.getWuyeAt()==null) ? "未知" : fy.getWuyeAt();
		this.sellDate = (fy.getSellDate() == null ) ? "未知" : fmtrq.format(fy.getSellDate());
		this.sellStatus = (fy.getSellMode() == 1) ? "正常、市场价" : "不正常、市场价";
		this.price = (fy.getHouseFinalSingle() == null) ? 0 : fy.getHouseFinalSingle();
		this.businessdegree = "同一区域";
		this.landscape = "与估价对象相同";
		this.traffic = "与估价对象相同";
		this.environment = "与估价对象一致";
		this.base = "与估价对象一致";
		if(fy.getHouseConstruction() == null){
			this.construction += "未知";
		}else{
			switch(fy.getHouseConstruction()){
				case 1:
					this.construction = "钢混结构";
					break;
				case 2:
					this.construction = "砖混结构";
					break;
				case 3:
					this.construction = "砖木结构";
					break;
				case 4:
					this.construction = "钢结构";
					break;
				default:
						break;
			}
		}
		this.unit = "合理";
		this.light = "一般";
		if(fy.getHouseDecoration() == null){
			this.decoration += "未知";
		}else{
			switch(fy.getHouseDecoration()){
				case 1:
					this.decoration = "简装";
					break;
				case 2:
					this.decoration = "精装";
					break;
				case 3:
					this.decoration = "毛坯";
					break;
				case 4:
					this.decoration = "中装";
					break;
				case 5:
					this.decoration = "豪华装修";
					break;
				default:
						break;
			}
		}
		fmtrq.applyPattern("yyyy年");
		this.buildDate = (fy.getHouseDate() == null ) ? "未知" : fmtrq.format(fy.getHouseDate());
		if(fy.getHouseFace() == null){
			this.face = "未知";
		}else{
			switch(fy.getHouseFace()){
				case 1:
					this.face = "东朝向";
					break;
				case 2:
					this.face = "南朝向";
					break;
				case 3:
					this.face = "西朝向";
					break;
				case 4:
					this.face = "北朝向";
					break;
				case 5:
					this.face = "东南朝向";
					break;
				case 6:
					this.face = "东北朝向";
					break;
				case 7:
					this.face = "西南朝向";
					break;
				case 8:
					this.face = "西北朝向";
					break;
				case 9:
					this.face = "南北朝向";
					break;
				case 10:
					this.face = "东西朝向";
					break;
				default:
						break;
			}
		}
		
		this.community ="一般";
		
		if(fy.getHouseLayers() == null || fy.getHouseAtLayer() == null){
			this.layer = "未知";
		}else{
			this.layer = fy.getHouseAtLayer().toString() + "层、共"+fy.getHouseLayers().toString()+"层";
		}
		this.support = "双气";
		
	}//end of constration class fun

	private String name;		// 可比实例A;
	private String location;	//坐落;
	private String sellDate; 	//交易日期;
	private String sellStatus;	//交易情况;
	private Integer price;		//成交单价;
	private String businessdegree;		//商服繁华度;
	private String landscape;		//景观;
	private String traffic;			//交通;
	private String environment;		//区域环境;
	private String base;//基础设施;
	private String construction;//建筑结构;
	private String unit;//户型;
	private String light;//采光、通风;
	private String decoration;//装修;
	private String buildDate;//建成年代;
	private String community;//小区环境
	private String face;//朝向;
	private String layer;//楼层;
	private String support;//配套设施；
	
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
