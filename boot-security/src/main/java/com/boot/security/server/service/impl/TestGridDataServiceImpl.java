package com.boot.security.server.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.TestGridDataDao;
import com.boot.security.server.model.TestGridData;
import com.boot.security.server.service.TestGridDataService;

@Service
public class TestGridDataServiceImpl implements TestGridDataService {

	@Autowired
	private TestGridDataDao testGridDataDao;

	@Override
	public void save(TestGridData testGridData) {
		testGridDataDao.save(testGridData);
	}

	@Override
	public List<Map<String, Object>> queryTestGridDataByRegion() {
		return testGridDataDao.queryTestGridDataByRegion();
	}

}
