package com.boot.security.server.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.CommonModel;
import com.boot.security.server.model.GridData;

@Mapper
public interface GridDataDao {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(@Param("maxDate") String maxDate,
			@Param("numPercent") Double numPercent, @Param("regionArr") String[] regionArr);

	public List<Map<String, Object>> querySingleGridData();

	public List<CommonModel> queryGridDataByTimeRegion(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent, @Param("warnNum") Double warnNum);

	public Double queryGridPeopleNum(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<CommonModel> queryGridPeopleNumDataNew(@Param("region") String region,
			@Param("maxDate") String maxDate);

	public String queryMaxDate();

	public List<Map<String, Object>> queryGridWarnData(@Param("warnNum") Double warnNum,
			@Param("maxDate") String maxDate, @Param("region") String region, @Param("numPercent") Double numPercent);

	// 0918
	public String queryBeforeDate();

	public String queryMinDate();

	public void insertBatch(@Param("beforeDate") String beforeDate);

	public void insertNewData(@Param("beforeDate") String beforeDate);

	public void deleteBatch(@Param("beforeDate") String beforeDate);

	public Double queryHiGridPeopleNum(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public Double queryGridPeopleNumCluster(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<CommonModel> queryHiGridDataByTimeRegion(@Param("dateStr") String dateStr,
			@Param("region") String region, @Param("numPercent") Double numPercent, @Param("warnNum") Double warnNum);

	public void updateDate(@Param("nowDate") String nowDate);

	public List<String> findDatesStr(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
			@Param("region") String region);

	// 接口2优化使用
	public Double queryGridPeopleNumClusterNew(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);
}
