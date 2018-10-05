package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.boot.security.server.common.BootConstant;
import com.boot.security.server.controller.AppController;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.CommonModel;
import com.boot.security.server.model.GridData;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.RegionService;

@Service
public class GridDataServiceImpl implements GridDataService {

	@Autowired
	private GridDataDao gridDataDao;
	@Autowired
	private RegionService regionService;

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

	/**
	 * 正式
	 */
	@Override
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum,String minDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		String dateNow = sdf.format(date);
		if (minDate.compareTo(dateNow) <= 0) {
			// 查询这个馆这个时间的所有的人数的数量
			Double total = gridDataDao.queryGridPeopleNum(dateNow, region, numPercent);
			if (total > 0) {
				map.put("date", dateNow);
				map.put("total", total);
				map.put("grids", new ArrayList<>());
				map.put("misc", new AnalysisModel());
				// 查询符合条件的数据
				List<CommonModel> list = gridDataDao.queryGridDataByTimeRegion(dateNow, region, numPercent, warnNum);
				if (list != null && list.size() > 0) {
					List<Map<String, Object>> listMaps = new ArrayList<>();
					for (CommonModel commonModel : list) {
						Map<String, Object> mapM = new HashMap<>();
						mapM.put("userCount", commonModel.getUserCount());
						mapM.put("x", commonModel.getX());
						mapM.put("y", commonModel.getY());
						mapM.put("region", commonModel.getRegion());
						listMaps.add(mapM);
					}
					map.put("grids", listMaps);
				}
				// 查询b域
				// dateNow=dateNow.substring(0, 12);
				map.put("misc", regionService.queryGridWarnDataCluster(region, dateNow));
			} else {
				map = null;
			}
		} else {
			map = appController.getHiMap(region, warnNum, numPercent, dateNow);
		}
		return map;
	}

	/**
	 * 正式
	 */
	@Override
	public Map<String, Object> queryGridDataByTimeRegionYh(Date date, String region, Double warnNum,
			List<String> dates) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}

		String dateNow = sdf.format(date);
		if (dates != null && dates.size() > 0) {
			if (dates.contains(dateNow)) {
				map = appController.getHiMap(region, warnNum, numPercent, dateNow);
			} else {
				return null;
			}
		} else {
			return null;
		}
		return map;
	}

	@Autowired
	private AppController appController;

	@Cacheable(value = "queryGridHiData")
	public Map<String, Object> getHiMap(String region, Double warnNum, Double numPercent, String dateNow) {
		Map<String, Object> map = new HashMap<>();
		// 查询这个馆这个时间的所有的人数的数量
		Double total = gridDataDao.queryHiGridPeopleNum(dateNow, region, numPercent);
		if (total > 0) {
			map.put("date", dateNow);
			map.put("total", total);
			map.put("grids", new ArrayList<>());
			map.put("misc", new AnalysisModel());
			// 查询符合条件的数据
			List<CommonModel> list = gridDataDao.queryHiGridDataByTimeRegion(dateNow, region, numPercent, warnNum);
			if (list != null && list.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();
				for (CommonModel commonModel : list) {
					Map<String, Object> mapM = new HashMap<>();
					mapM.put("userCount", commonModel.getUserCount());
					mapM.put("x", commonModel.getX());
					mapM.put("y", commonModel.getY());
					mapM.put("region", commonModel.getRegion());
					listMaps.add(mapM);
				}
				map.put("grids", listMaps);
			}
			// 查询b域
			// dateNow=dateNow.substring(0, 12);
			map.put("misc", regionService.queryGridWarnDataCluster(region, dateNow));
		} else {
			map = null;
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

	@Override
	public void updateDate(String nowDate) {
		gridDataDao.updateDate(nowDate);
	}

	@Override
	public void insertNewData(String beforeDate) {
		gridDataDao.insertNewData(beforeDate);
	}

	@Cacheable(value = "queryPeopleNumByTimeRange")
	public Double findNumByDate(String dateStr, String region, Double numPercent) {
		return gridDataDao.queryGridPeopleNumClusterNew(dateStr, region, numPercent);
	}

	@CacheEvict(value = "queryPeopleNumByTimeRange", allEntries = true)
	@Override
	public void clearCache() {
		System.out.println("清除缓存2操作。。。。");
	}

	@CacheEvict(value = "queryGridHiData", allEntries = true)
	@Override
	public void clearFiveCache() {
		System.out.println("清除缓存5操作。。。。");
	}

	@Cacheable(value = "queryHiDate")
	@Override
	public List<String> queryHiDates(String beginDateStr, String endDateStr, String region) {

		return gridDataDao.queryHiDates(beginDateStr, endDateStr, region);
	}
}
