package com.boot.security.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.boot.security.server.model.GridData;

public interface GridDataService {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(String[] regionArr, String maxDate);

	public List<Map<String, Object>> querySingleGridData();

	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum,String minDate);
	public Map<String, Object> queryGridDataAll(Date date, String region, String minDate);

	public Map<String, Object> queryGridDataByTimeRegionYh(Date date, String region, Double warnNum,
			List<String> dates);

	public Double queryGridPeopleNumDataNew(String region, String maxDate);
	
	public List<Map<String, Object>> queryGridPeopleNumquick(String[] regionArr,String maxDate);

	public List<Map<String, Object>> queryGridWarnData(String warnNum,String maxDate);

	public String queryMaxDate();

	// 0918
	public String queryBeforeDate();

	public void insertBatch(String beforeDate);

	public void deleteBatch(String beforeDate);

	public String queryMinDate();

	// 更新测试服所有日期
	public void updateDate(String nowDate);

	public void insertNewData(String beforeDate);

	public Double findNumByDate(String dateStr, String region, Double numPercent);

	public void clearCache();

	public void clearFiveCache();

	public Map<String, Object> getHiMap(String region, Double warnNum, Double numPercent, String dateNow);
	public Map<String, Object> getHiMapAll(String region, Double numPercent, String dateNow);

	public List<String> queryHiDates(String beginDateStr, String endDateStr, String region);

	
	/**
	* @Description: 接口2最新方法
	* @author weichengz
	* @date 2019年4月19日 上午10:01:30
	 */
	public List<Map<String, Object>> queryGridNumBetData(String[] split, String beginDate,String endDate,Double numPercent);
	
	public List<String> testQueryDbTime();
}
