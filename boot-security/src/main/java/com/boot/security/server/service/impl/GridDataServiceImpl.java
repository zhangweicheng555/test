package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.GridData;
import com.boot.security.server.model.MobileUserCount;
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
	public List<Map<String, Object>> queryGridDataByRegion(String region) {
		return gridDataDao.queryGridDataByRegion(region);
	}

	@Override
	public List<Map<String, Object>> querySingleGridData() {
		return gridDataDao.querySingleGridData();
	}

	@Override
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
	public Integer queryGridPeopleNumDataNew(String region) {
		Integer num=0;
		List<MobileUserCount> list=gridDataDao.queryGridPeopleNumDataNew(region);
		if (list !=null && list.size() >0) {
			if (BootConstant.People_Num_Percent >0) {
				for (MobileUserCount mobileUserCount : list) {
					Double double1=mobileUserCount.getImsi_num()/BootConstant.People_Num_Percent;
					num += (int)Math.floor(double1);
				}
			}else {
				for (MobileUserCount mobileUserCount : list) {
					Double double1=mobileUserCount.getImsi_num()/mobileUserCount.getRatio();
					num += (int)Math.floor(double1);
				}
			}
		}
		return num;
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(int warnNum) {
		return gridDataDao.queryGridWarnData(warnNum);
	}

}
