package com.boot.security.server.service;
import java.util.List;
import java.util.Map;

import com.boot.security.server.model.TestGridData;

public interface TestGridDataService {

	public void save(TestGridData testGridData);

	public List<Map<String, Object>> queryTestGridDataByRegion();

}
