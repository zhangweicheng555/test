package com.boot.security.server.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.UserDao;
import com.boot.security.server.model.GridData;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.model.TestH;
import com.boot.security.server.service.SysUserService;
import com.boot.security.server.util.CacheUtil;
import com.boot.security.server.util.SpringUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<SysUser> findTest(String beginDate, String endDate) {
		return userDao.findTest(beginDate, endDate);
	}

	@Override
	public String testCache(Integer num) {
		if (num > 5) {
			Cache cache = CacheUtil.getCacheManager("queryPeopleNumByTimeRange");
			System.out.println("======================================");
			return "success";
		} else {
			System.out.println("没有数据啊");
			return null;
		}
	}

	@Override
	public String clear() {
		// 获取EhCacheCacheManager类
		EhCacheCacheManager cacheCacheManager = SpringUtil.getApplicationContext().getBean(EhCacheCacheManager.class);
		// 获取CacheManager类
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		// 获取Cache
		// Cache cache=cacheManager.getCache("userCache");
		// cache.put(new Element("Hello", "123"));
		// System.out.println(cache.get("Hello").getObjectValue());

		System.out.println(cacheManager.getCache("queryPeopleNumByTimeRange").toString());
		cacheManager.removeCache("queryPeopleNumByTimeRange");

		return "success";
	}

	@Override
	public List<GridData> findNewGridDateByDate(String mydate) {
		return userDao.findNewGridDateByDate(mydate);
	}

	@Override
	public void clearTestGridData(String dealBDateScheduled, String dealEDateScheduled) {
		userDao.clearTestGridData(dealBDateScheduled, dealEDateScheduled);
	}

	@Override
	public List<TestH> find1() {
		return userDao.find1();
	}

	@Override
	public void find2(String x, String y) {
		userDao.find2(x, y);
	}

}
