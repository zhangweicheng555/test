package com.boot.security.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.GridData;

@Mapper
public interface GridDataDao {

	public void save(GridData gridData);

	public List<Map<String, Object>> queryGridDataByRegion(@Param("region") String region);

	public List<Map<String, Object>> querySingleGridData();

	public int queryPeopleNumByTimeRange(@Param("dateStr") String dateStr, @Param("region") String region);
}
