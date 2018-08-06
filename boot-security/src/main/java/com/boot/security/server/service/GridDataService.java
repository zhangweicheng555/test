package com.boot.security.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.GridData;

public interface GridDataService {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(String region);
	
	public List<Map<String, Object>> querySingleGridData();
	
	public int queryPeopleNumByTimeRange(Date dateStr,String region);
}
