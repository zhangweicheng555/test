package com.boot.security.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.boot.security.server.model.GridData;

public interface GridDataService {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(String region,String maxDate);
	
	public List<Map<String, Object>> querySingleGridData();
	
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region);

	public Integer queryGridPeopleNumDataNew(String region,String maxDate);

	public List<Map<String, Object>> queryGridWarnData(int warnNum,String maxDate);
	
	public String queryMaxDate();
}
