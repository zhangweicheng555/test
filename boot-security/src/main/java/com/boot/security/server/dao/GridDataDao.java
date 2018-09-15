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

	public List<Map<String, Object>> queryGridDataByRegion(@Param("region") String region,@Param("maxDate") String maxDate);

	public List<Map<String, Object>> querySingleGridData();


	public List<CommonModel> queryGridDataByTimeRegion(@Param("dateStr") String dateStr,
			@Param("region") String region);

	public List<CommonModel> queryGridPeopleNumDataNew(@Param("region") String region,@Param("maxDate") String maxDate);
	
	public String queryMaxDate();

	public List<Map<String, Object>> queryGridWarnData(@Param("warnNum") Double warnNum,@Param("maxDate") String maxDate,@Param("region") String region);
}
