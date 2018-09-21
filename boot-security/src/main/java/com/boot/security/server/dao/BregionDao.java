package com.boot.security.server.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.boot.security.server.model.BregionModel;

@Mapper
public interface BregionDao {

	public List<BregionModel> analysisByGnder(@Param("region") String region, @Param("sdate") String sdate);

	public List<BregionModel> analysisByAge(@Param("region") String region, @Param("sdate") String sdate);

	public List<BregionModel> analysisBySource(@Param("region") String region, @Param("sdate") String sdate);

	public String queryMaxDate();
	
	public void updateDate();
	public void insertNewData();
}
