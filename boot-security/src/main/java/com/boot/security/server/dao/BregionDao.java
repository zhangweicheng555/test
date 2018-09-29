package com.boot.security.server.dao;

import java.util.List;
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

	public AnalysisCluster queryGridWarnDataCluster(@Param("region") String region,@Param("sdate")  String sdate);

	public AnalysisCluster queryGridWarnDataClusterAll(@Param("region") String region,@Param("sdate")  String sdate);
}
