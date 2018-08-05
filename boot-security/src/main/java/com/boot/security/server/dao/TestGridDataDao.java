package com.boot.security.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.boot.security.server.model.TestGridData;

@Mapper
public interface TestGridDataDao {

	public void save(TestGridData testGridData);

	public List<Map<String, Object>> queryTestGridDataByRegion();
}
