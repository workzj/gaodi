package com.fy.database;

import java.util.Date;

/**
 * AbstractProjectFy entity provides the base persistence definition of the
 * ProjectFy entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractProjectFy implements java.io.Serializable {

	// Fields

	private Long idprojectFy;
	private String projectCode;
	private String sellerName;
	private String buyerName;
	private String houseNumber;
	private String houseXy;
	private Short houseLayers;
	private Short houseAtLayer;
	private Float houseArea;
	private Short houseConstruction;
	private Date houseDate;
	private Short houseDecoration;
	private String houseSupport;
	private String houseUse;
	private Integer houseFinalTotal;
	private Integer houseEvaluateTotal;
	private Integer houseFinalSingle;
	private Integer houseEvaluateSingle;
	private Date projectDate;
	private Date sellDate;
	private String projectComment;
	private String processInstanceId;
	private String wuyeName;
	private Short wuyeExtent;
	private Short houseHolds;
	private Short houseUsing;
	private Short houseStyle;
	private Short houseFace;
	private Short houseProperty;
	private Short houseLandProperty;
	private Short sellMode;
	private Short taxMode;
	private String houseRoad;
	private String houseEnvironment;
	private String kanchaCurrentstatus;
	private String kanchaMarketarea;
	private String yupingBianxian;
	private String yupingDiyatishi;
	private String projectSource;
	private String wuyeAt;
	private String houseNewnumber;
	private Integer similarhouseTotal;
	private Integer similarhouseSingle;
	private Short houseNumberHas;
	private Short houseUnit1;
	private Short houseUnit2;
	private Short houseUnit3;
	private Short houseUnit4;
	private Short houseUnit;
	private String houseWaiqiang;
	private Short houseYangtai;
	private String houseKetingDimian;
	private String houseKetingQiangmian;
	private String houseKetingDingpeng;
	private String houseCantingDimian;
	private String houseCantingQiangmian;
	private String houseCantingDingpeng;
	private String houseWoshiDimian;
	private String houseWoshiQiangmian;
	private String houseWoshiDingpeng;
	private String houseChufangDimian;
	private String houseChufangQiangmian;
	private String houseChufangDingpeng;
	private String houseWeiDimain;
	private String houseWeiQiangmian;
	private String houseWeiDingpeng;
	private String houseMaopiDimian;
	private String houseMaopiQiangmian;
	private String houseWindow;
	private Short wuyeManager;
	private Short houseWillRemove;
	private Short houseLvhua;
	private Integer pingguMoney;
	private String sellerTel;
	private String buyerTel;
	private Float houseLayerHeight;
	private Float houseBasementArea;
	private Short houseUnitNum;
	private String houseMaopiDingpeng;
	private Date kanchaDate;
	private Short xiaoquExtent;
	private String curStatus;
	private String ownerName;
	private String ownerNumber;
	private Date shouliDate;
	private String roadEast;
	private String roadWest;
	private String roadSouth;
	private String roadNorth;
	private String aroundMarket;
	private String aroundTraffic;
	private Integer referMin;
	private Integer referMax;
	private Float referTotal;
	private Date newhouseDate;
	private String evaluatePurpose;

	// Constructors

	/** default constructor */
	public AbstractProjectFy() {
	}

	/** full constructor */
	public AbstractProjectFy(String projectCode, String sellerName,
			String buyerName, String houseNumber, String houseXy,
			Short houseLayers, Short houseAtLayer, Float houseArea,
			Short houseConstruction, Date houseDate, Short houseDecoration,
			String houseSupport, String houseUse, Integer houseFinalTotal,
			Integer houseEvaluateTotal, Integer houseFinalSingle,
			Integer houseEvaluateSingle, Date projectDate, Date sellDate,
			String projectComment, String processInstanceId, String wuyeName,
			Short wuyeExtent, Short houseHolds, Short houseUsing,
			Short houseStyle, Short houseFace, Short houseProperty,
			Short houseLandProperty, Short sellMode, Short taxMode,
			String houseRoad, String houseEnvironment,
			String kanchaCurrentstatus, String kanchaMarketarea,
			String yupingBianxian, String yupingDiyatishi,
			String projectSource, String wuyeAt, String houseNewnumber,
			Integer similarhouseTotal, Integer similarhouseSingle,
			Short houseNumberHas, Short houseUnit1, Short houseUnit2,
			Short houseUnit3, Short houseUnit4, Short houseUnit,
			String houseWaiqiang, Short houseYangtai, String houseKetingDimian,
			String houseKetingQiangmian, String houseKetingDingpeng,
			String houseCantingDimian, String houseCantingQiangmian,
			String houseCantingDingpeng, String houseWoshiDimian,
			String houseWoshiQiangmian, String houseWoshiDingpeng,
			String houseChufangDimian, String houseChufangQiangmian,
			String houseChufangDingpeng, String houseWeiDimain,
			String houseWeiQiangmian, String houseWeiDingpeng,
			String houseMaopiDimian, String houseMaopiQiangmian,
			String houseWindow, Short wuyeManager, Short houseWillRemove,
			Short houseLvhua, Integer pingguMoney, String sellerTel,
			String buyerTel, Float houseLayerHeight, Float houseBasementArea,
			Short houseUnitNum, String houseMaopiDingpeng, Date kanchaDate,
			Short xiaoquExtent, String curStatus, String ownerName,
			String ownerNumber, Date shouliDate, String roadEast,
			String roadWest, String roadSouth, String roadNorth,
			String aroundMarket, String aroundTraffic, Integer referMin,
			Integer referMax, Float referTotal, Date newhouseDate,
			String evaluatePurpose) {
		this.projectCode = projectCode;
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		this.houseNumber = houseNumber;
		this.houseXy = houseXy;
		this.houseLayers = houseLayers;
		this.houseAtLayer = houseAtLayer;
		this.houseArea = houseArea;
		this.houseConstruction = houseConstruction;
		this.houseDate = houseDate;
		this.houseDecoration = houseDecoration;
		this.houseSupport = houseSupport;
		this.houseUse = houseUse;
		this.houseFinalTotal = houseFinalTotal;
		this.houseEvaluateTotal = houseEvaluateTotal;
		this.houseFinalSingle = houseFinalSingle;
		this.houseEvaluateSingle = houseEvaluateSingle;
		this.projectDate = projectDate;
		this.sellDate = sellDate;
		this.projectComment = projectComment;
		this.processInstanceId = processInstanceId;
		this.wuyeName = wuyeName;
		this.wuyeExtent = wuyeExtent;
		this.houseHolds = houseHolds;
		this.houseUsing = houseUsing;
		this.houseStyle = houseStyle;
		this.houseFace = houseFace;
		this.houseProperty = houseProperty;
		this.houseLandProperty = houseLandProperty;
		this.sellMode = sellMode;
		this.taxMode = taxMode;
		this.houseRoad = houseRoad;
		this.houseEnvironment = houseEnvironment;
		this.kanchaCurrentstatus = kanchaCurrentstatus;
		this.kanchaMarketarea = kanchaMarketarea;
		this.yupingBianxian = yupingBianxian;
		this.yupingDiyatishi = yupingDiyatishi;
		this.projectSource = projectSource;
		this.wuyeAt = wuyeAt;
		this.houseNewnumber = houseNewnumber;
		this.similarhouseTotal = similarhouseTotal;
		this.similarhouseSingle = similarhouseSingle;
		this.houseNumberHas = houseNumberHas;
		this.houseUnit1 = houseUnit1;
		this.houseUnit2 = houseUnit2;
		this.houseUnit3 = houseUnit3;
		this.houseUnit4 = houseUnit4;
		this.houseUnit = houseUnit;
		this.houseWaiqiang = houseWaiqiang;
		this.houseYangtai = houseYangtai;
		this.houseKetingDimian = houseKetingDimian;
		this.houseKetingQiangmian = houseKetingQiangmian;
		this.houseKetingDingpeng = houseKetingDingpeng;
		this.houseCantingDimian = houseCantingDimian;
		this.houseCantingQiangmian = houseCantingQiangmian;
		this.houseCantingDingpeng = houseCantingDingpeng;
		this.houseWoshiDimian = houseWoshiDimian;
		this.houseWoshiQiangmian = houseWoshiQiangmian;
		this.houseWoshiDingpeng = houseWoshiDingpeng;
		this.houseChufangDimian = houseChufangDimian;
		this.houseChufangQiangmian = houseChufangQiangmian;
		this.houseChufangDingpeng = houseChufangDingpeng;
		this.houseWeiDimain = houseWeiDimain;
		this.houseWeiQiangmian = houseWeiQiangmian;
		this.houseWeiDingpeng = houseWeiDingpeng;
		this.houseMaopiDimian = houseMaopiDimian;
		this.houseMaopiQiangmian = houseMaopiQiangmian;
		this.houseWindow = houseWindow;
		this.wuyeManager = wuyeManager;
		this.houseWillRemove = houseWillRemove;
		this.houseLvhua = houseLvhua;
		this.pingguMoney = pingguMoney;
		this.sellerTel = sellerTel;
		this.buyerTel = buyerTel;
		this.houseLayerHeight = houseLayerHeight;
		this.houseBasementArea = houseBasementArea;
		this.houseUnitNum = houseUnitNum;
		this.houseMaopiDingpeng = houseMaopiDingpeng;
		this.kanchaDate = kanchaDate;
		this.xiaoquExtent = xiaoquExtent;
		this.curStatus = curStatus;
		this.ownerName = ownerName;
		this.ownerNumber = ownerNumber;
		this.shouliDate = shouliDate;
		this.roadEast = roadEast;
		this.roadWest = roadWest;
		this.roadSouth = roadSouth;
		this.roadNorth = roadNorth;
		this.aroundMarket = aroundMarket;
		this.aroundTraffic = aroundTraffic;
		this.referMin = referMin;
		this.referMax = referMax;
		this.referTotal = referTotal;
		this.newhouseDate = newhouseDate;
		this.evaluatePurpose = evaluatePurpose;
	}

	// Property accessors

	public Long getIdprojectFy() {
		return this.idprojectFy;
	}

	public void setIdprojectFy(Long idprojectFy) {
		this.idprojectFy = idprojectFy;
	}

	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return this.buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getHouseXy() {
		return this.houseXy;
	}

	public void setHouseXy(String houseXy) {
		this.houseXy = houseXy;
	}

	public Short getHouseLayers() {
		return this.houseLayers;
	}

	public void setHouseLayers(Short houseLayers) {
		this.houseLayers = houseLayers;
	}

	public Short getHouseAtLayer() {
		return this.houseAtLayer;
	}

	public void setHouseAtLayer(Short houseAtLayer) {
		this.houseAtLayer = houseAtLayer;
	}

	public Float getHouseArea() {
		return this.houseArea;
	}

	public void setHouseArea(Float houseArea) {
		this.houseArea = houseArea;
	}

	public Short getHouseConstruction() {
		return this.houseConstruction;
	}

	public void setHouseConstruction(Short houseConstruction) {
		this.houseConstruction = houseConstruction;
	}

	public Date getHouseDate() {
		return this.houseDate;
	}

	public void setHouseDate(Date houseDate) {
		this.houseDate = houseDate;
	}

	public Short getHouseDecoration() {
		return this.houseDecoration;
	}

	public void setHouseDecoration(Short houseDecoration) {
		this.houseDecoration = houseDecoration;
	}

	public String getHouseSupport() {
		return this.houseSupport;
	}

	public void setHouseSupport(String houseSupport) {
		this.houseSupport = houseSupport;
	}

	public String getHouseUse() {
		return this.houseUse;
	}

	public void setHouseUse(String houseUse) {
		this.houseUse = houseUse;
	}

	public Integer getHouseFinalTotal() {
		return this.houseFinalTotal;
	}

	public void setHouseFinalTotal(Integer houseFinalTotal) {
		this.houseFinalTotal = houseFinalTotal;
	}

	public Integer getHouseEvaluateTotal() {
		return this.houseEvaluateTotal;
	}

	public void setHouseEvaluateTotal(Integer houseEvaluateTotal) {
		this.houseEvaluateTotal = houseEvaluateTotal;
	}

	public Integer getHouseFinalSingle() {
		return this.houseFinalSingle;
	}

	public void setHouseFinalSingle(Integer houseFinalSingle) {
		this.houseFinalSingle = houseFinalSingle;
	}

	public Integer getHouseEvaluateSingle() {
		return this.houseEvaluateSingle;
	}

	public void setHouseEvaluateSingle(Integer houseEvaluateSingle) {
		this.houseEvaluateSingle = houseEvaluateSingle;
	}

	public Date getProjectDate() {
		return this.projectDate;
	}

	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}

	public Date getSellDate() {
		return this.sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public String getProjectComment() {
		return this.projectComment;
	}

	public void setProjectComment(String projectComment) {
		this.projectComment = projectComment;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getWuyeName() {
		return this.wuyeName;
	}

	public void setWuyeName(String wuyeName) {
		this.wuyeName = wuyeName;
	}

	public Short getWuyeExtent() {
		return this.wuyeExtent;
	}

	public void setWuyeExtent(Short wuyeExtent) {
		this.wuyeExtent = wuyeExtent;
	}

	public Short getHouseHolds() {
		return this.houseHolds;
	}

	public void setHouseHolds(Short houseHolds) {
		this.houseHolds = houseHolds;
	}

	public Short getHouseUsing() {
		return this.houseUsing;
	}

	public void setHouseUsing(Short houseUsing) {
		this.houseUsing = houseUsing;
	}

	public Short getHouseStyle() {
		return this.houseStyle;
	}

	public void setHouseStyle(Short houseStyle) {
		this.houseStyle = houseStyle;
	}

	public Short getHouseFace() {
		return this.houseFace;
	}

	public void setHouseFace(Short houseFace) {
		this.houseFace = houseFace;
	}

	public Short getHouseProperty() {
		return this.houseProperty;
	}

	public void setHouseProperty(Short houseProperty) {
		this.houseProperty = houseProperty;
	}

	public Short getHouseLandProperty() {
		return this.houseLandProperty;
	}

	public void setHouseLandProperty(Short houseLandProperty) {
		this.houseLandProperty = houseLandProperty;
	}

	public Short getSellMode() {
		return this.sellMode;
	}

	public void setSellMode(Short sellMode) {
		this.sellMode = sellMode;
	}

	public Short getTaxMode() {
		return this.taxMode;
	}

	public void setTaxMode(Short taxMode) {
		this.taxMode = taxMode;
	}

	public String getHouseRoad() {
		return this.houseRoad;
	}

	public void setHouseRoad(String houseRoad) {
		this.houseRoad = houseRoad;
	}

	public String getHouseEnvironment() {
		return this.houseEnvironment;
	}

	public void setHouseEnvironment(String houseEnvironment) {
		this.houseEnvironment = houseEnvironment;
	}

	public String getKanchaCurrentstatus() {
		return this.kanchaCurrentstatus;
	}

	public void setKanchaCurrentstatus(String kanchaCurrentstatus) {
		this.kanchaCurrentstatus = kanchaCurrentstatus;
	}

	public String getKanchaMarketarea() {
		return this.kanchaMarketarea;
	}

	public void setKanchaMarketarea(String kanchaMarketarea) {
		this.kanchaMarketarea = kanchaMarketarea;
	}

	public String getYupingBianxian() {
		return this.yupingBianxian;
	}

	public void setYupingBianxian(String yupingBianxian) {
		this.yupingBianxian = yupingBianxian;
	}

	public String getYupingDiyatishi() {
		return this.yupingDiyatishi;
	}

	public void setYupingDiyatishi(String yupingDiyatishi) {
		this.yupingDiyatishi = yupingDiyatishi;
	}

	public String getProjectSource() {
		return this.projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	public String getWuyeAt() {
		return this.wuyeAt;
	}

	public void setWuyeAt(String wuyeAt) {
		this.wuyeAt = wuyeAt;
	}

	public String getHouseNewnumber() {
		return this.houseNewnumber;
	}

	public void setHouseNewnumber(String houseNewnumber) {
		this.houseNewnumber = houseNewnumber;
	}

	public Integer getSimilarhouseTotal() {
		return this.similarhouseTotal;
	}

	public void setSimilarhouseTotal(Integer similarhouseTotal) {
		this.similarhouseTotal = similarhouseTotal;
	}

	public Integer getSimilarhouseSingle() {
		return this.similarhouseSingle;
	}

	public void setSimilarhouseSingle(Integer similarhouseSingle) {
		this.similarhouseSingle = similarhouseSingle;
	}

	public Short getHouseNumberHas() {
		return this.houseNumberHas;
	}

	public void setHouseNumberHas(Short houseNumberHas) {
		this.houseNumberHas = houseNumberHas;
	}

	public Short getHouseUnit1() {
		return this.houseUnit1;
	}

	public void setHouseUnit1(Short houseUnit1) {
		this.houseUnit1 = houseUnit1;
	}

	public Short getHouseUnit2() {
		return this.houseUnit2;
	}

	public void setHouseUnit2(Short houseUnit2) {
		this.houseUnit2 = houseUnit2;
	}

	public Short getHouseUnit3() {
		return this.houseUnit3;
	}

	public void setHouseUnit3(Short houseUnit3) {
		this.houseUnit3 = houseUnit3;
	}

	public Short getHouseUnit4() {
		return this.houseUnit4;
	}

	public void setHouseUnit4(Short houseUnit4) {
		this.houseUnit4 = houseUnit4;
	}

	public Short getHouseUnit() {
		return this.houseUnit;
	}

	public void setHouseUnit(Short houseUnit) {
		this.houseUnit = houseUnit;
	}

	public String getHouseWaiqiang() {
		return this.houseWaiqiang;
	}

	public void setHouseWaiqiang(String houseWaiqiang) {
		this.houseWaiqiang = houseWaiqiang;
	}

	public Short getHouseYangtai() {
		return this.houseYangtai;
	}

	public void setHouseYangtai(Short houseYangtai) {
		this.houseYangtai = houseYangtai;
	}

	public String getHouseKetingDimian() {
		return this.houseKetingDimian;
	}

	public void setHouseKetingDimian(String houseKetingDimian) {
		this.houseKetingDimian = houseKetingDimian;
	}

	public String getHouseKetingQiangmian() {
		return this.houseKetingQiangmian;
	}

	public void setHouseKetingQiangmian(String houseKetingQiangmian) {
		this.houseKetingQiangmian = houseKetingQiangmian;
	}

	public String getHouseKetingDingpeng() {
		return this.houseKetingDingpeng;
	}

	public void setHouseKetingDingpeng(String houseKetingDingpeng) {
		this.houseKetingDingpeng = houseKetingDingpeng;
	}

	public String getHouseCantingDimian() {
		return this.houseCantingDimian;
	}

	public void setHouseCantingDimian(String houseCantingDimian) {
		this.houseCantingDimian = houseCantingDimian;
	}

	public String getHouseCantingQiangmian() {
		return this.houseCantingQiangmian;
	}

	public void setHouseCantingQiangmian(String houseCantingQiangmian) {
		this.houseCantingQiangmian = houseCantingQiangmian;
	}

	public String getHouseCantingDingpeng() {
		return this.houseCantingDingpeng;
	}

	public void setHouseCantingDingpeng(String houseCantingDingpeng) {
		this.houseCantingDingpeng = houseCantingDingpeng;
	}

	public String getHouseWoshiDimian() {
		return this.houseWoshiDimian;
	}

	public void setHouseWoshiDimian(String houseWoshiDimian) {
		this.houseWoshiDimian = houseWoshiDimian;
	}

	public String getHouseWoshiQiangmian() {
		return this.houseWoshiQiangmian;
	}

	public void setHouseWoshiQiangmian(String houseWoshiQiangmian) {
		this.houseWoshiQiangmian = houseWoshiQiangmian;
	}

	public String getHouseWoshiDingpeng() {
		return this.houseWoshiDingpeng;
	}

	public void setHouseWoshiDingpeng(String houseWoshiDingpeng) {
		this.houseWoshiDingpeng = houseWoshiDingpeng;
	}

	public String getHouseChufangDimian() {
		return this.houseChufangDimian;
	}

	public void setHouseChufangDimian(String houseChufangDimian) {
		this.houseChufangDimian = houseChufangDimian;
	}

	public String getHouseChufangQiangmian() {
		return this.houseChufangQiangmian;
	}

	public void setHouseChufangQiangmian(String houseChufangQiangmian) {
		this.houseChufangQiangmian = houseChufangQiangmian;
	}

	public String getHouseChufangDingpeng() {
		return this.houseChufangDingpeng;
	}

	public void setHouseChufangDingpeng(String houseChufangDingpeng) {
		this.houseChufangDingpeng = houseChufangDingpeng;
	}

	public String getHouseWeiDimain() {
		return this.houseWeiDimain;
	}

	public void setHouseWeiDimain(String houseWeiDimain) {
		this.houseWeiDimain = houseWeiDimain;
	}

	public String getHouseWeiQiangmian() {
		return this.houseWeiQiangmian;
	}

	public void setHouseWeiQiangmian(String houseWeiQiangmian) {
		this.houseWeiQiangmian = houseWeiQiangmian;
	}

	public String getHouseWeiDingpeng() {
		return this.houseWeiDingpeng;
	}

	public void setHouseWeiDingpeng(String houseWeiDingpeng) {
		this.houseWeiDingpeng = houseWeiDingpeng;
	}

	public String getHouseMaopiDimian() {
		return this.houseMaopiDimian;
	}

	public void setHouseMaopiDimian(String houseMaopiDimian) {
		this.houseMaopiDimian = houseMaopiDimian;
	}

	public String getHouseMaopiQiangmian() {
		return this.houseMaopiQiangmian;
	}

	public void setHouseMaopiQiangmian(String houseMaopiQiangmian) {
		this.houseMaopiQiangmian = houseMaopiQiangmian;
	}

	public String getHouseWindow() {
		return this.houseWindow;
	}

	public void setHouseWindow(String houseWindow) {
		this.houseWindow = houseWindow;
	}

	public Short getWuyeManager() {
		return this.wuyeManager;
	}

	public void setWuyeManager(Short wuyeManager) {
		this.wuyeManager = wuyeManager;
	}

	public Short getHouseWillRemove() {
		return this.houseWillRemove;
	}

	public void setHouseWillRemove(Short houseWillRemove) {
		this.houseWillRemove = houseWillRemove;
	}

	public Short getHouseLvhua() {
		return this.houseLvhua;
	}

	public void setHouseLvhua(Short houseLvhua) {
		this.houseLvhua = houseLvhua;
	}

	public Integer getPingguMoney() {
		return this.pingguMoney;
	}

	public void setPingguMoney(Integer pingguMoney) {
		this.pingguMoney = pingguMoney;
	}

	public String getSellerTel() {
		return this.sellerTel;
	}

	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}

	public String getBuyerTel() {
		return this.buyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}

	public Float getHouseLayerHeight() {
		return this.houseLayerHeight;
	}

	public void setHouseLayerHeight(Float houseLayerHeight) {
		this.houseLayerHeight = houseLayerHeight;
	}

	public Float getHouseBasementArea() {
		return this.houseBasementArea;
	}

	public void setHouseBasementArea(Float houseBasementArea) {
		this.houseBasementArea = houseBasementArea;
	}

	public Short getHouseUnitNum() {
		return this.houseUnitNum;
	}

	public void setHouseUnitNum(Short houseUnitNum) {
		this.houseUnitNum = houseUnitNum;
	}

	public String getHouseMaopiDingpeng() {
		return this.houseMaopiDingpeng;
	}

	public void setHouseMaopiDingpeng(String houseMaopiDingpeng) {
		this.houseMaopiDingpeng = houseMaopiDingpeng;
	}

	public Date getKanchaDate() {
		return this.kanchaDate;
	}

	public void setKanchaDate(Date kanchaDate) {
		this.kanchaDate = kanchaDate;
	}

	public Short getXiaoquExtent() {
		return this.xiaoquExtent;
	}

	public void setXiaoquExtent(Short xiaoquExtent) {
		this.xiaoquExtent = xiaoquExtent;
	}

	public String getCurStatus() {
		return this.curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerNumber() {
		return this.ownerNumber;
	}

	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}

	public Date getShouliDate() {
		return this.shouliDate;
	}

	public void setShouliDate(Date shouliDate) {
		this.shouliDate = shouliDate;
	}

	public String getRoadEast() {
		return this.roadEast;
	}

	public void setRoadEast(String roadEast) {
		this.roadEast = roadEast;
	}

	public String getRoadWest() {
		return this.roadWest;
	}

	public void setRoadWest(String roadWest) {
		this.roadWest = roadWest;
	}

	public String getRoadSouth() {
		return this.roadSouth;
	}

	public void setRoadSouth(String roadSouth) {
		this.roadSouth = roadSouth;
	}

	public String getRoadNorth() {
		return this.roadNorth;
	}

	public void setRoadNorth(String roadNorth) {
		this.roadNorth = roadNorth;
	}

	public String getAroundMarket() {
		return this.aroundMarket;
	}

	public void setAroundMarket(String aroundMarket) {
		this.aroundMarket = aroundMarket;
	}

	public String getAroundTraffic() {
		return this.aroundTraffic;
	}

	public void setAroundTraffic(String aroundTraffic) {
		this.aroundTraffic = aroundTraffic;
	}

	public Integer getReferMin() {
		return this.referMin;
	}

	public void setReferMin(Integer referMin) {
		this.referMin = referMin;
	}

	public Integer getReferMax() {
		return this.referMax;
	}

	public void setReferMax(Integer referMax) {
		this.referMax = referMax;
	}

	public Float getReferTotal() {
		return this.referTotal;
	}

	public void setReferTotal(Float referTotal) {
		this.referTotal = referTotal;
	}

	public Date getNewhouseDate() {
		return this.newhouseDate;
	}

	public void setNewhouseDate(Date newhouseDate) {
		this.newhouseDate = newhouseDate;
	}

	public String getEvaluatePurpose() {
		return this.evaluatePurpose;
	}

	public void setEvaluatePurpose(String evaluatePurpose) {
		this.evaluatePurpose = evaluatePurpose;
	}

}