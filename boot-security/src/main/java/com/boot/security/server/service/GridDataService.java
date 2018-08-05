package com.boot.security.server.service;

import java.util.List;
import java.util.Map;

import com.boot.security.server.model.GridData;

public interface GridDataService {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(String region);
	
	public List<Map<String, Object>> querySingleGridData();
}
