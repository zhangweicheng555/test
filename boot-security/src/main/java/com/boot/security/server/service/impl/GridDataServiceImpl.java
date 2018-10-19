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
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum, String minDate) {
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
	public Map<String, Object> queryGridDataAll(Date date, String region, String minDate) {
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
			Double total = gridDataDao.queryGridPeopleNumAll(dateNow, region, numPercent);
			if (total > 0) {
				map.put("date", dateNow);
				map.put("total", total);
				map.put("grids", new ArrayList<>());
				map.put("misc", new AnalysisModel());
				// 查询符合条件的数据
				List<CommonModel> list = gridDataDao.queryGridDataByTimeRegionAll(dateNow, region, numPercent);
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
				map.put("misc", regionService.queryGridWarnDataCluster("All", dateNow));
			} else {
				map = null;
			}
		} else {
			map = appController.getHiMapAll(region, numPercent, dateNow);
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
	
	
	public Map<String, Object> getHiMapAll(String region, Double numPercent, String dateNow) {
		Map<String, Object> map = new HashMap<>();
		// 查询这个馆这个时间的所有的人数的数量
		Double total = gridDataDao.queryHiGridPeopleNumAll(dateNow, region, numPercent);
		if (total > 0) {
			map.put("date", dateNow);
			map.put("total", total);
			map.put("grids", new ArrayList<>());
			map.put("misc", new AnalysisModel());
			// 查询符合条件的数据
			List<CommonModel> list = gridDataDao.queryHiGridDataByTimeRegionAll(dateNow, region, numPercent);
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
			map.put("misc", regionService.queryGridWarnDataCluster("All", dateNow));
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
	public List<Map<String, Object>> queryGridPeopleNumquick() {
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridPeopleNumquick(numPercent);
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(String warnNum, String maxDate) {
		String[] warnNums = warnNum.split(",");
		Double flag1 = Double.valueOf(warnNums[0]);
		Double flag2 = Double.valueOf(warnNums[1]);
		Double flag3 = Double.valueOf(warnNums[2]);
		Double flag4 = Double.valueOf(warnNums[3]);
		Double flag5 = Double.valueOf(warnNums[4]);
		Double flag6 = Double.valueOf(warnNums[5]);
		Double flag7 = Double.valueOf(warnNums[6]);
		Double flag8 = Double.valueOf(warnNums[7]);
		Double flag9 = Double.valueOf(warnNums[8]);
		Double flag10 = Double.valueOf(warnNums[9]);
		Double flag11 = Double.valueOf(warnNums[10]);
		Double flag12 = Double.valueOf(warnNums[11]);
		Double flag13 = Double.valueOf(warnNums[12]);
		Double flag14 = Double.valueOf(warnNums[13]);
		Double flag15 = Double.valueOf(warnNums[14]);
		Double flag16 = Double.valueOf(warnNums[15]);
		Double flag17 = Double.valueOf(warnNums[16]);
		Double flag18 = Double.valueOf(warnNums[17]);
		Double flag19 = Double.valueOf(warnNums[18]);
		Double flag20 = Double.valueOf(warnNums[19]);
		Double flag21 = Double.valueOf(warnNums[20]);
		Double flag22 = Double.valueOf(warnNums[21]);
		Double flag23 = Double.valueOf(warnNums[22]);
		Double flag24 = Double.valueOf(warnNums[23]);
		Double flag25 = Double.valueOf(warnNums[24]);
		Double flag26 = Double.valueOf(warnNums[25]);
		Double flag27 = Double.valueOf(warnNums[26]);
		Double flag28 = Double.valueOf(warnNums[27]);
		Double flag29 = Double.valueOf(warnNums[28]);
		Double flag30 = Double.valueOf(warnNums[29]);
		Double flag31 = Double.valueOf(warnNums[30]);
		Double flag32 = Double.valueOf(warnNums[31]);
		Double flag33 = Double.valueOf(warnNums[32]);
		Double flag34 = Double.valueOf(warnNums[33]);
		Double flag35 = Double.valueOf(warnNums[34]);
		Double flag36 = Double.valueOf(warnNums[35]);
		Double flag37 = Double.valueOf(warnNums[36]);
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridWarnData(flag1, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9, flag10,
				flag11, flag12, flag13, flag14, flag15, flag16, flag17, flag18, flag19, flag20, flag21, flag22, flag23,
				flag24, flag25, flag26, flag27, flag28, flag29, flag30, flag31, flag32, flag33, flag34, flag35, flag36,
				flag37, maxDate, numPercent);
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
		BootConstant.RECORD_TIME_GRID_5 = null;
		System.out.println("清除缓存5操作。。。。");
	}

	@Cacheable(value = "queryHiDate")
	@Override
	public List<String> queryHiDates(String beginDateStr, String endDateStr, String region) {

		return gridDataDao.queryHiDates(beginDateStr, endDateStr, region);
	}
}
