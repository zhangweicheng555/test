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
import com.boot.security.server.model.CommonModel;
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
				if (double1 >0) {
					map.put("userCount", (int) Math.ceil(double1));
					map.remove("imsi");
					map.remove("radio");
					lisMaps.add(map);
				}
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
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Long warnNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();

		List<CommonModel> list = gridDataDao.queryGridDataByTimeRegion(sdf.format(date), region);

		Map<String, Object> mapModel = new HashMap<>();
		map.put("date", sdf.format(date));
		map.put("total", 0);
		map.put("grids", new ArrayList<>());
		
		
		
		if (list.size() > 0) {
			Double countNum = 0.0;
			List<Map<String, Object>> listMaps = new ArrayList<>();
			for (CommonModel entity : list) {

				Map<String, Object> mapM = new HashMap<>();

				Double imsi = entity.getImsi();
				Double radio = entity.getRadio();
				Double double1 = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					double1 = imsi / BootConstant.People_Num_Percent;
				} else {
					double1 = imsi / radio;
				}

				countNum += Math.ceil(double1);
				if (double1 > warnNum) {
					mapM.put("userCount", Math.ceil(double1));
					mapM.put("x", entity.getX());
					mapM.put("y", entity.getY());

					listMaps.add(mapM);
				}
			}
			map.put("total", countNum);
			map.put("grids", listMaps);
		}
		return map;
	}

	@Override
	public Double queryGridPeopleNumDataNew(String region, String maxDate) {
		Double num = 0.0;
		List<CommonModel> commonModels = gridDataDao.queryGridPeopleNumDataNew(region, maxDate);
		if (commonModels != null && commonModels.size() > 0) {
			for (CommonModel entity : commonModels) {

				Double imsi = entity.getImsi();
				Double radio = entity.getRadio();
				Double double1 = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					double1 = imsi / BootConstant.People_Num_Percent;
				} else {
					double1 = imsi / radio;
				}
				num += Math.ceil(double1);
			}
		}
		return num;
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(Double warnNum, String maxDate, String region) {
		/* return gridDataDao.queryGridWarnData(warnNum, maxDate, region); */
		List<Map<String, Object>> queryGridWarnData = gridDataDao.queryGridWarnData(warnNum, maxDate, region);
		List<Map<String, Object>> queryDataMaps = new ArrayList<>();
		if (queryGridWarnData != null && queryGridWarnData.size() > 0) {
			for (Map<String, Object> map : queryGridWarnData) {
				Double imsi = Double.valueOf(map.get("imsi").toString());
				Double radio = Double.valueOf(map.get("radio").toString());
				Double double1 = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					double1 = imsi / BootConstant.People_Num_Percent;
				} else {
					double1 = imsi / radio;
				}
				map.put("userCount", (int) Math.ceil(double1));
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
