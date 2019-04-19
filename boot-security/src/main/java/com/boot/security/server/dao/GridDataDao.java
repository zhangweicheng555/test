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

	public List<CommonModel> queryGridDataByTimeRegionAll(@Param("dateStr") String dateStr,
			@Param("region") String region, @Param("numPercent") Double numPercent);

	public Double queryGridPeopleNum(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<Map<String, Object>> queryGridPeopleNumquick(@Param("numPercent") Double numPercent, @Param("regionArr") String[] regionArr,@Param("maxDate") String maxDate);

	public Double queryGridPeopleNumAll(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<CommonModel> queryGridPeopleNumDataNew(@Param("region") String region,
			@Param("maxDate") String maxDate);

	public String queryMaxDate();

	public List<Map<String, Object>> queryGridWarnData(@Param("flag1") Double flag1, @Param("flag2") Double flag2,
			@Param("flag3") Double flag3, @Param("flag4") Double flag4, @Param("flag5") Double flag5,
			@Param("flag6") Double flag6, @Param("flag7") Double flag7, @Param("flag8") Double flag8,
			@Param("flag9") Double flag9, @Param("flag10") Double flag10, @Param("flag11") Double flag11,
			@Param("flag12") Double flag12, @Param("flag13") Double flag13, @Param("flag14") Double flag14,
			@Param("flag15") Double flag15, @Param("flag16") Double flag16, @Param("flag17") Double flag17,
			@Param("flag18") Double flag18, @Param("flag19") Double flag19, @Param("flag20") Double flag20,
			@Param("flag21") Double flag21, @Param("flag22") Double flag22, @Param("flag23") Double flag23,
			@Param("flag24") Double flag24, @Param("flag25") Double flag25, @Param("flag26") Double flag26,
			@Param("flag27") Double flag27, @Param("flag28") Double flag28, @Param("flag29") Double flag29,
			@Param("flag30") Double flag30, @Param("flag31") Double flag31, @Param("flag32") Double flag32,
			@Param("flag33") Double flag33, @Param("flag34") Double flag34, @Param("flag35") Double flag35,
			@Param("flag36") Double flag36, @Param("flag37") Double flag37, @Param("maxDate") String maxDate,
			@Param("numPercent") Double numPercent);

	// 0918
	public String queryBeforeDate();

	public String queryMinDate();

	public void insertBatch(@Param("beforeDate") String beforeDate);

	public void insertNewData(@Param("beforeDate") String beforeDate);

	public void deleteBatch(@Param("beforeDate") String beforeDate);

	public Double queryHiGridPeopleNum(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public Double queryHiGridPeopleNumAll(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public Double queryGridPeopleNumCluster(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<CommonModel> queryHiGridDataByTimeRegion(@Param("dateStr") String dateStr,
			@Param("region") String region, @Param("numPercent") Double numPercent, @Param("warnNum") Double warnNum);

	public List<CommonModel> queryHiGridDataByTimeRegionAll(@Param("dateStr") String dateStr,
			@Param("region") String region, @Param("numPercent") Double numPercent);

	public void updateDate(@Param("nowDate") String nowDate);

	public List<String> findDatesStr(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
			@Param("region") String region);

	// 接口2优化使用
	public Double queryGridPeopleNumClusterNew(@Param("dateStr") String dateStr, @Param("region") String region,
			@Param("numPercent") Double numPercent);

	public List<String> queryHiDates(@Param("beginDate") String beginDateStr, @Param("endDate") String endDateStr,
			@Param("region") String region);

	public List<Map<String, Object>> queryGridNumBetData(@Param("regionArr") String[] regionArr,@Param("beginDate")  String beginDate,@Param("endDate") String endDate,@Param("numPercent")   Double numPercent);

}
