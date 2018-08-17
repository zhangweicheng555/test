package com.boot.security.server.service;

import java.util.List;
import com.boot.security.server.model.SysUser;

public interface SysUserService {

	public List<SysUser> findTest(String beginDate,String endDate);

	public String testCache(Integer num1);

	public String clear();
}
