package com.boot.security.server.service;

import java.util.List;
import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.model.TestH;

public interface SysUserService {

	public List<SysUser> findTest(String beginDate,String endDate);

	public String testCache(Integer num1);

	public String clear();
	
	public List<GridData> findNewGridDateByDate(String mydate);

	public void clearTestGridData(String dealBDateScheduled, String dealEDateScheduled);
	
	public List<TestH> find1();
	public void find2(String x,String y);
}
