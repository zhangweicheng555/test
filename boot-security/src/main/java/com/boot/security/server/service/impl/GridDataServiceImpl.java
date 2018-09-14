package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.security.server.common.BootConstant;
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
	public List<Map<String, Object>> queryGridDataByRegion(String region, String maxDate) {
		List<Map<String, Object>> queryGridDataByRegion = gridDataDao.queryGridDataByRegion(region, maxDate);
		List<Map<String, Object>> lisMaps = new ArrayList<Map<String, Object>>();
		if (queryGridDataByRegion != null && queryGridDataByRegion.size() > 0) {
			for (Map<String, Object> map : queryGridDataByRegion) {
				Double imsi = Double.valueOf(map.get("imsi").toString());
				Double radio = Double.valueOf(map.get("radio").toString());
				Double double1 = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					double1 = imsi / BootConstant.People_Num_Percent;
				} else {
					double1 = imsi / radio;
				}
				map.put("userCount", (int) Math.floor(double1));
				map.remove("imsi");
				map.remove("radio");
				lisMaps.add(map);
			}
		}
		return lisMaps;
		/* return gridDataDao.queryGridDataByRegion(region,maxDate); */
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
			/*
			 * map.put("date", sdf.format(date)); map.put("grids", list); return map;
			 */
			map.put("date", sdf.format(date));
			List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map2 : list) {
				Double imsi = Double.valueOf(map2.get("imsi").toString());
				Double radio = Double.valueOf(map2.get("radio").toString());
				Double double1 = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					double1 = imsi / BootConstant.People_Num_Percent;
				} else {
					double1 = imsi / radio;
				}
				map2.put("userCount", (int) Math.floor(double1));
				map2.remove("imsi");
				map2.remove("radio");
				listMaps.add(map2);
			}
			map.put("grids", listMaps);
			return map;
		}
		return null;
	}

	@Override
	public Integer queryGridPeopleNumDataNew(String region, String maxDate) {

		return gridDataDao.queryGridPeopleNumDataNew(region, maxDate);
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(Double warnNum, String maxDate, String region) {
		/*return gridDataDao.queryGridWarnData(warnNum, maxDate, region);*/
		List<Map<String, Object>> queryGridWarnData = gridDataDao.queryGridWarnData(warnNum, maxDate, region);
		List<Map<String, Object>> queryDataMaps=new ArrayList<>();
		if (queryGridWarnData != null && queryGridWarnData.size() >0) {
			for (Map<String, Object> map : queryGridWarnData) {
				Double imsi=Double.valueOf(map.get("imsi").toString());
				Double radio=Double.valueOf(map.get("radio").toString());
				Double double1=0.0;
				if (BootConstant.People_Num_Percent >0) {
					double1=imsi/BootConstant.People_Num_Percent;
				}else {
					double1=imsi/radio;
				}
				map.put("userCount", (int)Math.floor(double1));
				map.remove("imsi");
				map.remove("radio");
				queryDataMaps.add(map);
			}
		}
		return queryDataMaps;
	}

	@Override
	public String queryMaxDate() {
		return gridDataDao.queryMaxDate();
	}

}
