package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.GridData;
import com.boot.security.server.service.GridDataService;

@Service
public class GridDataServiceImpl implements GridDataService {

	@Autowired
	private GridDataDao gridDataDao;

	@Override
	public void save(GridData gridData) {
		gridDataDao.save(gridData);
	}

	@Override
	public List<Map<String, Object>> queryGridDataByRegion(String region,String maxDate) {
		return gridDataDao.queryGridDataByRegion(region,maxDate);
	}

	@Override
	public List<Map<String, Object>> querySingleGridData() {
		return gridDataDao.querySingleGridData();
	}

	@Override
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = gridDataDao.queryGridDataByTimeRegion(sdf.format(date), region);
		if (list.size() > 0) {
			map.put("date", sdf.format(date));
			map.put("grids", list);
			return map;
		}
		return null;
	}

	@Override
	public Integer queryGridPeopleNumDataNew(String region,String maxDate) {
		
		return gridDataDao.queryGridPeopleNumDataNew(region,maxDate);
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(int warnNum,String maxDate) {
		return gridDataDao.queryGridWarnData(warnNum,maxDate);
	}

	@Override
	public String queryMaxDate() {
		return gridDataDao.queryMaxDate();
	}

}
