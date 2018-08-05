package com.boot.security.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.security.server.model.SysUser;

@Mapper
public interface UserDao {

	@Select("select * from T_USER t where t.ID = #{id}")
	SysUser getById(Long id);

	public List<SysUser> findTest(@Param("beginDate") String beginDate,@Param("endDate")  String endDate);
}
