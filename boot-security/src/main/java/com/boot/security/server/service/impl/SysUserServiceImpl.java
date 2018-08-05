package com.boot.security.server.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.dao.UserDao;
import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<SysUser> findTest(String beginDate,String endDate) {
		// TODO Auto-generated method stub
		return userDao.findTest(beginDate,endDate);
	}

	

}
