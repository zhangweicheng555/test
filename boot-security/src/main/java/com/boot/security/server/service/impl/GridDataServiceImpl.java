package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
	public List<Map<String, Object>> queryGridDataByRegion(String[] regionArr, String maxDate) {

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridDataByRegion(maxDate, numPercent, regionArr);
	}

	@Override
	public List<Map<String, Object>> querySingleGridData() {
		return gridDataDao.querySingleGridData();
	}

	@Override
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		String dateNow = sdf.format(date);
		String minDate = gridDataDao.queryMinDate();
		if (minDate.compareTo(dateNow) <= 0) {
			// 查询这个馆这个时间的所有的人数的数量
			Double total = gridDataDao.queryGridPeopleNum(dateNow, region, numPercent);
			if (total > 0) {
				map.put("date", dateNow);
				map.put("total", total);
				map.put("grids", new ArrayList<>());
				// 查询符合条件的数据
				List<CommonModel> list = gridDataDao.queryGridDataByTimeRegion(dateNow, region, numPercent, warnNum);
				if (list != null && list.size() > 0) {
					List<Map<String, Object>> listMaps = new ArrayList<>();
					for (CommonModel commonModel : list) {
						Map<String, Object> mapM = new HashMap<>();
						mapM.put("userCount", commonModel.getUserCount());
						mapM.put("x", commonModel.getX());
						mapM.put("y", commonModel.getY());
						listMaps.add(mapM);
					}
					map.put("grids", listMaps);
				}
			} else {
				map = null;
			}
		} else {
			// 查询这个馆这个时间的所有的人数的数量
			Double total = gridDataDao.queryHiGridPeopleNum(dateNow, region, numPercent);
			if (total > 0) {
				map.put("date", dateNow);
				map.put("total", total);
				map.put("grids", new ArrayList<>());
				// 查询符合条件的数据
				List<CommonModel> list = gridDataDao.queryHiGridDataByTimeRegion(dateNow, region, numPercent, warnNum);
				if (list != null && list.size() > 0) {
					List<Map<String, Object>> listMaps = new ArrayList<>();
					for (CommonModel commonModel : list) {
						Map<String, Object> mapM = new HashMap<>();
						mapM.put("userCount", commonModel.getUserCount());
						mapM.put("x", commonModel.getX());
						mapM.put("y", commonModel.getY());
						listMaps.add(mapM);
					}
					map.put("grids", listMaps);
				}
			} else {
				map = null;
			}
		}
		return map;
	}

	@Override
	public Double queryGridPeopleNumDataNew(String region, String maxDate) {
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridPeopleNum(maxDate, region, numPercent);
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(Double warnNum, String maxDate, String region) {
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridWarnData(warnNum, maxDate, region, numPercent);
	}

	@Override
	public String queryMaxDate() {
		return gridDataDao.queryMaxDate();
	}

	@Override
	public String queryBeforeDate() {
		return gridDataDao.queryBeforeDate();
	}

	@Override
	public void insertBatch(String beforeDate) {
		gridDataDao.insertBatch(beforeDate);
	}

	@Override
	public void deleteBatch(String beforeDate) {
		gridDataDao.deleteBatch(beforeDate);
	}

	@Override
	public String queryMinDate() {
		return gridDataDao.queryMinDate();
	}

}
