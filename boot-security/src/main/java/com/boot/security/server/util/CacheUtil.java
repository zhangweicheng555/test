package com.boot.security.server.util;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheUtil {
	
	public static Cache getCache(String cacheName) {
		//获取EhCacheCacheManager类
		EhCacheCacheManager cacheCacheManager=SpringUtil.getApplicationContext().getBean(EhCacheCacheManager.class);
		//获取CacheManager类
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		//获取Cache
		Cache cache = cacheManager.getCache(cacheName);
	   // cache.put(new Element("Hello", "123"));
		//System.out.println(cache.get("Hello").getObjectValue());
		return cache;
	}
	public static void clearCache(String cacheName) {
		EhCacheCacheManager cacheCacheManager=SpringUtil.getApplicationContext().getBean(EhCacheCacheManager.class);
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		cacheManager.removeCache(cacheName);
	}

}
