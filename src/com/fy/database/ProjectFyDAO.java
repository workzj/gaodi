package com.fy.database;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ProjectFy entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.fy.database.ProjectFy
 * @author MyEclipse Persistence Tools
 */

public class ProjectFyDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(ProjectFyDAO.class);
	// property constants
	public static final String PROJECT_CODE = "projectCode";
	public static final String SELLER_NAME = "sellerName";
	public static final String BUYER_NAME = "buyerName";
	public static final String HOUSE_NUMBER = "houseNumber";
	public static final String HOUSE_XY = "houseXy";
	public static final String HOUSE_LAYERS = "houseLayers";
	public static final String HOUSE_AT_LAYER = "houseAtLayer";
	public static final String HOUSE_AREA = "houseArea";
	public static final String HOUSE_CONSTRUCTION = "houseConstruction";
	public static final String HOUSE_DECORATION = "houseDecoration";
	public static final String HOUSE_SUPPORT = "houseSupport";
	public static final String HOUSE_USE = "houseUse";
	public static final String HOUSE_FINAL_TOTAL = "houseFinalTotal";
	public static final String HOUSE_EVALUATE_TOTAL = "houseEvaluateTotal";
	public static final String HOUSE_FINAL_SINGLE = "houseFinalSingle";
	public static final String HOUSE_EVALUATE_SINGLE = "houseEvaluateSingle";
	public static final String PROJECT_COMMENT = "projectComment";
	public static final String PROCESS_INSTANCE_ID = "processInstanceId";
	public static final String WUYE_NAME = "wuyeName";
	public static final String WUYE_EXTENT = "wuyeExtent";
	public static final String HOUSE_HOLDS = "houseHolds";
	public static final String HOUSE_USING = "houseUsing";
	public static final String HOUSE_STYLE = "houseStyle";
	public static final String HOUSE_FACE = "houseFace";
	public static final String HOUSE_PROPERTY = "houseProperty";
	public static final String HOUSE_LAND_PROPERTY = "houseLandProperty";
	public static final String SELL_MODE = "sellMode";
	public static final String TAX_MODE = "taxMode";
	public static final String HOUSE_ROAD = "houseRoad";
	public static final String HOUSE_ENVIRONMENT = "houseEnvironment";
	public static final String KANCHA_CURRENTSTATUS = "kanchaCurrentstatus";
	public static final String KANCHA_MARKETAREA = "kanchaMarketarea";
	public static final String YUPING_BIANXIAN = "yupingBianxian";
	public static final String YUPING_DIYATISHI = "yupingDiyatishi";
	public static final String PROJECT_SOURCE = "projectSource";
	public static final String WUYE_AT = "wuyeAt";
	public static final String HOUSE_NEWNUMBER = "houseNewnumber";
	public static final String SIMILARHOUSE_TOTAL = "similarhouseTotal";
	public static final String SIMILARHOUSE_SINGLE = "similarhouseSingle";
	public static final String HOUSE_NUMBER_HAS = "houseNumberHas";
	public static final String HOUSE_UNIT1 = "houseUnit1";
	public static final String HOUSE_UNIT2 = "houseUnit2";
	public static final String HOUSE_UNIT3 = "houseUnit3";
	public static final String HOUSE_UNIT4 = "houseUnit4";
	public static final String HOUSE_UNIT = "houseUnit";
	public static final String HOUSE_WAIQIANG = "houseWaiqiang";
	public static final String HOUSE_YANGTAI = "houseYangtai";
	public static final String HOUSE_KETING_DIMIAN = "houseKetingDimian";
	public static final String HOUSE_KETING_QIANGMIAN = "houseKetingQiangmian";
	public static final String HOUSE_KETING_DINGPENG = "houseKetingDingpeng";
	public static final String HOUSE_CANTING_DIMIAN = "houseCantingDimian";
	public static final String HOUSE_CANTING_QIANGMIAN = "houseCantingQiangmian";
	public static final String HOUSE_CANTING_DINGPENG = "houseCantingDingpeng";
	public static final String HOUSE_WOSHI_DIMIAN = "houseWoshiDimian";
	public static final String HOUSE_WOSHI_QIANGMIAN = "houseWoshiQiangmian";
	public static final String HOUSE_WOSHI_DINGPENG = "houseWoshiDingpeng";
	public static final String HOUSE_CHUFANG_DIMIAN = "houseChufangDimian";
	public static final String HOUSE_CHUFANG_QIANGMIAN = "houseChufangQiangmian";
	public static final String HOUSE_CHUFANG_DINGPENG = "houseChufangDingpeng";
	public static final String HOUSE_WEI_DIMAIN = "houseWeiDimain";
	public static final String HOUSE_WEI_QIANGMIAN = "houseWeiQiangmian";
	public static final String HOUSE_WEI_DINGPENG = "houseWeiDingpeng";
	public static final String HOUSE_MAOPI_DIMIAN = "houseMaopiDimian";
	public static final String HOUSE_MAOPI_QIANGMIAN = "houseMaopiQiangmian";
	public static final String HOUSE_WINDOW = "houseWindow";
	public static final String WUYE_MANAGER = "wuyeManager";
	public static final String HOUSE_WILL_REMOVE = "houseWillRemove";
	public static final String HOUSE_LVHUA = "houseLvhua";
	public static final String PINGGU_MONEY = "pingguMoney";
	public static final String SELLER_TEL = "sellerTel";
	public static final String BUYER_TEL = "buyerTel";
	public static final String HOUSE_LAYER_HEIGHT = "houseLayerHeight";
	public static final String HOUSE_BASEMENT_AREA = "houseBasementArea";
	public static final String HOUSE_UNIT_NUM = "houseUnitNum";
	public static final String HOUSE_MAOPI_DINGPENG = "houseMaopiDingpeng";
	public static final String XIAOQU_EXTENT = "xiaoquExtent";
	public static final String CUR_STATUS = "curStatus";
	public static final String OWNER_NAME = "ownerName";
	public static final String OWNER_NUMBER = "ownerNumber";
	public static final String ROAD_EAST = "roadEast";
	public static final String ROAD_WEST = "roadWest";
	public static final String ROAD_SOUTH = "roadSouth";
	public static final String ROAD_NORTH = "roadNorth";
	public static final String AROUND_MARKET = "aroundMarket";
	public static final String AROUND_TRAFFIC = "aroundTraffic";
	public static final String REFER_MIN = "referMin";
	public static final String REFER_MAX = "referMax";
	public static final String REFER_TOTAL = "referTotal";
	public static final String EVALUATE_PURPOSE = "evaluatePurpose";

	protected void initDao() {
		// do nothing
	}

	public void save(ProjectFy transientInstance) {
		log.debug("saving ProjectFy instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ProjectFy persistentInstance) {
		log.debug("deleting ProjectFy instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ProjectFy findById(java.lang.Long id) {
		log.debug("getting ProjectFy instance with id: " + id);
		try {
			ProjectFy instance = (ProjectFy) getHibernateTemplate().get(
					"com.fy.database.ProjectFy", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ProjectFy instance) {
		log.debug("finding ProjectFy instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ProjectFy instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ProjectFy as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProjectCode(Object projectCode) {
		return findByProperty(PROJECT_CODE, projectCode);
	}

	public List findBySellerName(Object sellerName) {
		return findByProperty(SELLER_NAME, sellerName);
	}

	public List findByBuyerName(Object buyerName) {
		return findByProperty(BUYER_NAME, buyerName);
	}

	public List findByHouseNumber(Object houseNumber) {
		return findByProperty(HOUSE_NUMBER, houseNumber);
	}

	public List findByHouseXy(Object houseXy) {
		return findByProperty(HOUSE_XY, houseXy);
	}

	public List findByHouseLayers(Object houseLayers) {
		return findByProperty(HOUSE_LAYERS, houseLayers);
	}

	public List findByHouseAtLayer(Object houseAtLayer) {
		return findByProperty(HOUSE_AT_LAYER, houseAtLayer);
	}

	public List findByHouseArea(Object houseArea) {
		return findByProperty(HOUSE_AREA, houseArea);
	}

	public List findByHouseConstruction(Object houseConstruction) {
		return findByProperty(HOUSE_CONSTRUCTION, houseConstruction);
	}

	public List findByHouseDecoration(Object houseDecoration) {
		return findByProperty(HOUSE_DECORATION, houseDecoration);
	}

	public List findByHouseSupport(Object houseSupport) {
		return findByProperty(HOUSE_SUPPORT, houseSupport);
	}

	public List findByHouseUse(Object houseUse) {
		return findByProperty(HOUSE_USE, houseUse);
	}

	public List findByHouseFinalTotal(Object houseFinalTotal) {
		return findByProperty(HOUSE_FINAL_TOTAL, houseFinalTotal);
	}

	public List findByHouseEvaluateTotal(Object houseEvaluateTotal) {
		return findByProperty(HOUSE_EVALUATE_TOTAL, houseEvaluateTotal);
	}

	public List findByHouseFinalSingle(Object houseFinalSingle) {
		return findByProperty(HOUSE_FINAL_SINGLE, houseFinalSingle);
	}

	public List findByHouseEvaluateSingle(Object houseEvaluateSingle) {
		return findByProperty(HOUSE_EVALUATE_SINGLE, houseEvaluateSingle);
	}

	public List findByProjectComment(Object projectComment) {
		return findByProperty(PROJECT_COMMENT, projectComment);
	}

	public List findByProcessInstanceId(Object processInstanceId) {
		return findByProperty(PROCESS_INSTANCE_ID, processInstanceId);
	}

	public List findByWuyeName(Object wuyeName) {
		return findByProperty(WUYE_NAME, wuyeName);
	}

	public List findByWuyeExtent(Object wuyeExtent) {
		return findByProperty(WUYE_EXTENT, wuyeExtent);
	}

	public List findByHouseHolds(Object houseHolds) {
		return findByProperty(HOUSE_HOLDS, houseHolds);
	}

	public List findByHouseUsing(Object houseUsing) {
		return findByProperty(HOUSE_USING, houseUsing);
	}

	public List findByHouseStyle(Object houseStyle) {
		return findByProperty(HOUSE_STYLE, houseStyle);
	}

	public List findByHouseFace(Object houseFace) {
		return findByProperty(HOUSE_FACE, houseFace);
	}

	public List findByHouseProperty(Object houseProperty) {
		return findByProperty(HOUSE_PROPERTY, houseProperty);
	}

	public List findByHouseLandProperty(Object houseLandProperty) {
		return findByProperty(HOUSE_LAND_PROPERTY, houseLandProperty);
	}

	public List findBySellMode(Object sellMode) {
		return findByProperty(SELL_MODE, sellMode);
	}

	public List findByTaxMode(Object taxMode) {
		return findByProperty(TAX_MODE, taxMode);
	}

	public List findByHouseRoad(Object houseRoad) {
		return findByProperty(HOUSE_ROAD, houseRoad);
	}

	public List findByHouseEnvironment(Object houseEnvironment) {
		return findByProperty(HOUSE_ENVIRONMENT, houseEnvironment);
	}

	public List findByKanchaCurrentstatus(Object kanchaCurrentstatus) {
		return findByProperty(KANCHA_CURRENTSTATUS, kanchaCurrentstatus);
	}

	public List findByKanchaMarketarea(Object kanchaMarketarea) {
		return findByProperty(KANCHA_MARKETAREA, kanchaMarketarea);
	}

	public List findByYupingBianxian(Object yupingBianxian) {
		return findByProperty(YUPING_BIANXIAN, yupingBianxian);
	}

	public List findByYupingDiyatishi(Object yupingDiyatishi) {
		return findByProperty(YUPING_DIYATISHI, yupingDiyatishi);
	}

	public List findByProjectSource(Object projectSource) {
		return findByProperty(PROJECT_SOURCE, projectSource);
	}

	public List findByWuyeAt(Object wuyeAt) {
		return findByProperty(WUYE_AT, wuyeAt);
	}

	public List findByHouseNewnumber(Object houseNewnumber) {
		return findByProperty(HOUSE_NEWNUMBER, houseNewnumber);
	}

	public List findBySimilarhouseTotal(Object similarhouseTotal) {
		return findByProperty(SIMILARHOUSE_TOTAL, similarhouseTotal);
	}

	public List findBySimilarhouseSingle(Object similarhouseSingle) {
		return findByProperty(SIMILARHOUSE_SINGLE, similarhouseSingle);
	}

	public List findByHouseNumberHas(Object houseNumberHas) {
		return findByProperty(HOUSE_NUMBER_HAS, houseNumberHas);
	}

	public List findByHouseUnit1(Object houseUnit1) {
		return findByProperty(HOUSE_UNIT1, houseUnit1);
	}

	public List findByHouseUnit2(Object houseUnit2) {
		return findByProperty(HOUSE_UNIT2, houseUnit2);
	}

	public List findByHouseUnit3(Object houseUnit3) {
		return findByProperty(HOUSE_UNIT3, houseUnit3);
	}

	public List findByHouseUnit4(Object houseUnit4) {
		return findByProperty(HOUSE_UNIT4, houseUnit4);
	}

	public List findByHouseUnit(Object houseUnit) {
		return findByProperty(HOUSE_UNIT, houseUnit);
	}

	public List findByHouseWaiqiang(Object houseWaiqiang) {
		return findByProperty(HOUSE_WAIQIANG, houseWaiqiang);
	}

	public List findByHouseYangtai(Object houseYangtai) {
		return findByProperty(HOUSE_YANGTAI, houseYangtai);
	}

	public List findByHouseKetingDimian(Object houseKetingDimian) {
		return findByProperty(HOUSE_KETING_DIMIAN, houseKetingDimian);
	}

	public List findByHouseKetingQiangmian(Object houseKetingQiangmian) {
		return findByProperty(HOUSE_KETING_QIANGMIAN, houseKetingQiangmian);
	}

	public List findByHouseKetingDingpeng(Object houseKetingDingpeng) {
		return findByProperty(HOUSE_KETING_DINGPENG, houseKetingDingpeng);
	}

	public List findByHouseCantingDimian(Object houseCantingDimian) {
		return findByProperty(HOUSE_CANTING_DIMIAN, houseCantingDimian);
	}

	public List findByHouseCantingQiangmian(Object houseCantingQiangmian) {
		return findByProperty(HOUSE_CANTING_QIANGMIAN, houseCantingQiangmian);
	}

	public List findByHouseCantingDingpeng(Object houseCantingDingpeng) {
		return findByProperty(HOUSE_CANTING_DINGPENG, houseCantingDingpeng);
	}

	public List findByHouseWoshiDimian(Object houseWoshiDimian) {
		return findByProperty(HOUSE_WOSHI_DIMIAN, houseWoshiDimian);
	}

	public List findByHouseWoshiQiangmian(Object houseWoshiQiangmian) {
		return findByProperty(HOUSE_WOSHI_QIANGMIAN, houseWoshiQiangmian);
	}

	public List findByHouseWoshiDingpeng(Object houseWoshiDingpeng) {
		return findByProperty(HOUSE_WOSHI_DINGPENG, houseWoshiDingpeng);
	}

	public List findByHouseChufangDimian(Object houseChufangDimian) {
		return findByProperty(HOUSE_CHUFANG_DIMIAN, houseChufangDimian);
	}

	public List findByHouseChufangQiangmian(Object houseChufangQiangmian) {
		return findByProperty(HOUSE_CHUFANG_QIANGMIAN, houseChufangQiangmian);
	}

	public List findByHouseChufangDingpeng(Object houseChufangDingpeng) {
		return findByProperty(HOUSE_CHUFANG_DINGPENG, houseChufangDingpeng);
	}

	public List findByHouseWeiDimain(Object houseWeiDimain) {
		return findByProperty(HOUSE_WEI_DIMAIN, houseWeiDimain);
	}

	public List findByHouseWeiQiangmian(Object houseWeiQiangmian) {
		return findByProperty(HOUSE_WEI_QIANGMIAN, houseWeiQiangmian);
	}

	public List findByHouseWeiDingpeng(Object houseWeiDingpeng) {
		return findByProperty(HOUSE_WEI_DINGPENG, houseWeiDingpeng);
	}

	public List findByHouseMaopiDimian(Object houseMaopiDimian) {
		return findByProperty(HOUSE_MAOPI_DIMIAN, houseMaopiDimian);
	}

	public List findByHouseMaopiQiangmian(Object houseMaopiQiangmian) {
		return findByProperty(HOUSE_MAOPI_QIANGMIAN, houseMaopiQiangmian);
	}

	public List findByHouseWindow(Object houseWindow) {
		return findByProperty(HOUSE_WINDOW, houseWindow);
	}

	public List findByWuyeManager(Object wuyeManager) {
		return findByProperty(WUYE_MANAGER, wuyeManager);
	}

	public List findByHouseWillRemove(Object houseWillRemove) {
		return findByProperty(HOUSE_WILL_REMOVE, houseWillRemove);
	}

	public List findByHouseLvhua(Object houseLvhua) {
		return findByProperty(HOUSE_LVHUA, houseLvhua);
	}

	public List findByPingguMoney(Object pingguMoney) {
		return findByProperty(PINGGU_MONEY, pingguMoney);
	}

	public List findBySellerTel(Object sellerTel) {
		return findByProperty(SELLER_TEL, sellerTel);
	}

	public List findByBuyerTel(Object buyerTel) {
		return findByProperty(BUYER_TEL, buyerTel);
	}

	public List findByHouseLayerHeight(Object houseLayerHeight) {
		return findByProperty(HOUSE_LAYER_HEIGHT, houseLayerHeight);
	}

	public List findByHouseBasementArea(Object houseBasementArea) {
		return findByProperty(HOUSE_BASEMENT_AREA, houseBasementArea);
	}

	public List findByHouseUnitNum(Object houseUnitNum) {
		return findByProperty(HOUSE_UNIT_NUM, houseUnitNum);
	}

	public List findByHouseMaopiDingpeng(Object houseMaopiDingpeng) {
		return findByProperty(HOUSE_MAOPI_DINGPENG, houseMaopiDingpeng);
	}

	public List findByXiaoquExtent(Object xiaoquExtent) {
		return findByProperty(XIAOQU_EXTENT, xiaoquExtent);
	}

	public List findByCurStatus(Object curStatus) {
		return findByProperty(CUR_STATUS, curStatus);
	}

	public List findByOwnerName(Object ownerName) {
		return findByProperty(OWNER_NAME, ownerName);
	}

	public List findByOwnerNumber(Object ownerNumber) {
		return findByProperty(OWNER_NUMBER, ownerNumber);
	}

	public List findByRoadEast(Object roadEast) {
		return findByProperty(ROAD_EAST, roadEast);
	}

	public List findByRoadWest(Object roadWest) {
		return findByProperty(ROAD_WEST, roadWest);
	}

	public List findByRoadSouth(Object roadSouth) {
		return findByProperty(ROAD_SOUTH, roadSouth);
	}

	public List findByRoadNorth(Object roadNorth) {
		return findByProperty(ROAD_NORTH, roadNorth);
	}

	public List findByAroundMarket(Object aroundMarket) {
		return findByProperty(AROUND_MARKET, aroundMarket);
	}

	public List findByAroundTraffic(Object aroundTraffic) {
		return findByProperty(AROUND_TRAFFIC, aroundTraffic);
	}

	public List findByReferMin(Object referMin) {
		return findByProperty(REFER_MIN, referMin);
	}

	public List findByReferMax(Object referMax) {
		return findByProperty(REFER_MAX, referMax);
	}

	public List findByReferTotal(Object referTotal) {
		return findByProperty(REFER_TOTAL, referTotal);
	}

	public List findByEvaluatePurpose(Object evaluatePurpose) {
		return findByProperty(EVALUATE_PURPOSE, evaluatePurpose);
	}

	public List findAll() {
		log.debug("finding all ProjectFy instances");
		try {
			String queryString = "from ProjectFy";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ProjectFy merge(ProjectFy detachedInstance) {
		log.debug("merging ProjectFy instance");
		try {
			ProjectFy result = (ProjectFy) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ProjectFy instance) {
		log.debug("attaching dirty ProjectFy instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ProjectFy instance) {
		log.debug("attaching clean ProjectFy instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ProjectFyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ProjectFyDAO) ctx.getBean("ProjectFyDAO");
	}
}