package com.boot.security.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.HiGridDataHour;

@Mapper
public interface HiGridDataHourDao {

	public Integer queryPeopleNumByTimeRange(@Param("dateStr") String dateStr, @Param("region") String region);

	public HiGridDataHour queryHiGridDataHourLatest(@Param("region") String region);

	public long queryCount();
}
