package com.boot.security.server.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;

public interface SysUserService {

	public List<SysUser> findTest(String beginDate,String endDate);

	public String testCache(Integer num1);

	public String clear();
	
	public List<GridData> findNewGridDateByDate(String mydate);

	public void clearTestGridData(String dealBDateScheduled, String dealEDateScheduled);
}
