package com.boot.security.server.service;

import com.boot.security.server.model.AnalysisModel;

public interface RegionService {

	public String queryMaxDate();
	
	public AnalysisModel queryGridWarnData(String region,String sdate);
	
	public void updateDate(String nowDate);
	public void insertNewData(String beforeDate);
}
