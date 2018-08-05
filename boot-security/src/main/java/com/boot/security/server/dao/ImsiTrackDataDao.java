package com.boot.security.server.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.ImsiTrackData;

@Mapper
public interface ImsiTrackDataDao {

	public List<Map<String, Object>> queryDataByParam(@Param("imsi") String imsi, @Param("beginDate") String beginDate,
			@Param("endDate") String endDate);

	public void save(ImsiTrackData imsiTrackData);

	public Map<String, Object> userScatterPoint(@Param("user") Double user,@Param("time") String time,@Param("region") String region);
}
