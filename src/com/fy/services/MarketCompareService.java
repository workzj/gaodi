package com.fy.services;

import java.util.List;
import com.fy.database.MarketCompare;

public interface MarketCompareService {

	//����г��ȽϷ��Ĳ����б�
	public List GetMCParameter(Long pID,short type);
	public String AddMCParameter(MarketCompare param);
}
