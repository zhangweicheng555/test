package com.boot.security.server.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;

@Mapper
public interface UserDao {

	@Select("select * from T_USER t where t.ID = #{id}")
	SysUser getById(Long id);

	public List<SysUser> findTest(@Param("beginDate") String beginDate,@Param("endDate")  String endDate);
	
	public List<GridData> findNewGridDateByDate(@Param("mydate") String mydate);

	public void clearTestGridData(@Param("dealBDateScheduled") String dealBDateScheduled,@Param("dealEDateScheduled") String dealEDateScheduled);
}
