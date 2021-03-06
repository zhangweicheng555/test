package com.boot.security.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boot.security.server.model.GridData;

public interface GridDataService {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(String region, String maxDate);

	public List<Map<String, Object>> querySingleGridData();

	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum);

	public Double queryGridPeopleNumDataNew(String region, String maxDate);

	public List<Map<String, Object>> queryGridWarnData(Double warnNum, String maxDate, String region);

	public String queryMaxDate();
	
	//0918
	public String queryBeforeDate();

	public void insertBatch(String beforeDate);

	public void deleteBatch(String beforeDate);
	
	public String queryMinDate();
}
