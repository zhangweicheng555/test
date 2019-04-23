package com.boot.security.server.service;

import java.util.List;
import java.util.Map;

import com.boot.security.server.model.AnalysisModel;

public interface RegionService {

	public String queryMaxDate();

	public String queryMaxDateClus();

	public AnalysisModel queryGridWarnData(String region, String sdate);

	public AnalysisModel queryGridWarnDataCluster(String region, String sdate);

	public AnalysisModel queryGridWarnDataClusterNew(String region, String sdate);

	public List<Map<String, Object>> queryDateForMinute(String beginDate, String endDate, String region);

	public List<Map<String, Object>> queryDateForMinuteNew(String beginDate, String endDate, String region);

	public List<Map<String, Object>> queryDateForMinuteIndoor(String beginDate, String endDate);

	public List<Map<String, Object>> queryDateForMinuteAll(String beginDate, String endDate, String region);

	public void updateDate(String nowDate);

	public void insertNewData(String beforeDate);

	public Long querySwitchInfo(String maxDate);
}
