package com.boot.security.server.service;

import java.util.List;
import java.util.Map;

import com.boot.security.server.model.AnalysisModel;

public interface RegionService {

	public String queryMaxDate();
	
	public AnalysisModel queryGridWarnData(String region,String sdate);
	
	public List<Map<String, Object>> queryDateForMinute(String beginDate, String endDate, String region);
	
	public void updateDate(String nowDate);
	public void insertNewData(String beforeDate);
	public AnalysisModel queryGridWarnDataCluster(String region,String sdate);
}
