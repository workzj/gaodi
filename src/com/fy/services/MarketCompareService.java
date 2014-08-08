package com.fy.services;

import java.util.List;
import com.fy.database.MarketCompare;

public interface MarketCompareService {

	//获得市场比较法的参数列表
	public List GetMCParameter(Long pID,short type);
	public String AddMCParameter(MarketCompare param);
}
