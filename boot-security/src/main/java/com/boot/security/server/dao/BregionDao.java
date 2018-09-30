package com.boot.security.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.AnalysisCluster;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.BregionModel;

@Mapper
public interface BregionDao {

	public List<BregionModel> analysisByGnder(@Param("region") String region, @Param("sdate") String sdate);

	public List<BregionModel> analysisByAge(@Param("region") String region, @Param("sdate") String sdate);

	public List<BregionModel> analysisBySource(@Param("region") String region, @Param("sdate") String sdate,
			@Param("sourcetype") Long sourcetype);

	public String queryMaxDate();
	public String queryMaxDateClus();

	public AnalysisCluster queryGridWarnDataCluster(@Param("region") String region, @Param("sdate") String sdate);

	public AnalysisCluster queryGridWarnDataClusterAll(@Param("region") String region, @Param("sdate") String sdate);

	public List<Map<String, Object>> queryDateForMinute(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
			@Param("region") String region);

	public Map<String, Object> querySource1(@Param("beginDate") String sdate);
	public Map<String, Object> querySource2(@Param("beginDate") String sdate);
	public Map<String, Object> querySource3(@Param("beginDate") String sdate);
	public Map<String, Object> querySource4(@Param("beginDate") String sdate);
	public Map<String, Object> querySource5(@Param("beginDate") String sdate);
	public Map<String, Object> querySource6(@Param("beginDate") String sdate);
	public Map<String, Object> querySource7(@Param("beginDate") String sdate);
	public Map<String, Object> querySource8(@Param("beginDate") String sdate);
	public Map<String, Object> querySource9(@Param("beginDate") String sdate);
	public Map<String, Object> querySource10(@Param("beginDate") String sdate);

	public Map<String, Object> gloal1(@Param("beginDate") String sdate);
	public Map<String, Object> gloal2(@Param("beginDate") String sdate);
	public Map<String, Object> gloal3(@Param("beginDate") String sdate);
	public Map<String, Object> gloal4(@Param("beginDate") String sdate);
	public Map<String, Object> gloal5(@Param("beginDate") String sdate);
	public Map<String, Object> gloal6(@Param("beginDate") String sdate);
	public Map<String, Object> gloal7(@Param("beginDate") String sdate);
	public Map<String, Object> gloal8(@Param("beginDate") String sdate);
	public Map<String, Object> gloal9(@Param("beginDate") String sdate);
	public Map<String, Object> gloal10(@Param("beginDate") String sdate);
}
