package com.boot.security.server.service;

import java.util.List;
import java.util.Map;

import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;

public interface SysUserService {

	public List<SysUser> findTest(String beginDate,String endDate);
}
