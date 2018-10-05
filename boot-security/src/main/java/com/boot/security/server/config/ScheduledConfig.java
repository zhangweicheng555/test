package com.boot.security.server.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.controller.AppController;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.util.MyUtil;

/** 定时器使用 */
@Configuration
@EnableScheduling
public class ScheduledConfig {

	@Autowired
	private GridDataDao gridDataDao;
	@Autowired
	private GridDataService gridDataService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 0点三分执行 确认入库的时间 正式服
	 */
	@Transactional
	@Scheduled(cron = "0 3 0 * * ?")
	public void execByThirtyMin() {
		// 查询表中最大时间
		String beforeDate = gridDataService.queryMaxDate();
		// 将小于这个时间的插入到历史表中
		gridDataService.insertBatch(beforeDate);
		// LTE_GRID_USER_NUM删除 小于这个日期的数据
		gridDataService.deleteBatch(beforeDate);
	}

	/**
	 * 下午2点到晚上8点 每5分钟执行一次
	 */
	// @Scheduled(cron = "0 0/5 17-20 * * ?")
	public void scheduledByFiveMinute() throws ParseException {

	}

	/**
	 * 每天21点执行
	 */
	// @Scheduled(cron = "0 0 21 * * ?")
	public void dateTask() {
	}

	@Autowired
	private AppController appController;

	// 初始化场馆编号
	public final static List<String> numList1 = new ArrayList<String>(
			Arrays.asList("1", "2", "3", "4.1", "4.2", "5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2",
					"NH", "EH", "WH", "V1_1", "V1_2", "V1_3", "V1_4", "V2_1", "V2_2", "V2_3", "V3_1", "V3_2", "V3_3",
					"V3_4", "V3_5", "V3_6", "V3_7", "V4_1", "V4_2", "V4_3", "V4_4", "V4_5", "V4_6", "V5"));

	/**
	 * 每10分钟执行一次 测试服 接口2 优化
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void execByFiveMinFor2() throws ParseException {
		String endDate = getNowDate();
		String beginDate = endDate.substring(0, 8) + "000000";
		String regionStrs = "";
		for (String region : numList1) {
			if (StringUtils.isNoneBlank(regionStrs)) {
				regionStrs = regionStrs + "," + region;
			} else {
				regionStrs = region;
			}
		}
		regionStrs = regionStrs.trim();
		appController.queryPeopleNumByTimeRangeNew(beginDate, endDate, 5, regionStrs);
	}

	/**
	 * 0点10分执行 清除接口2缓存
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void execClearCache() {
		appController.clearCache();
	}
	/**
	 * 0点10分执行 清除接口5缓存
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	public void execClearFiveCache() {
		appController.clearFiveCache();
	}

	public String getNowDate() {
		Calendar rightNow = Calendar.getInstance();
		int minute = rightNow.get(Calendar.MINUTE);
		minute = Math.round(minute / 5 * 5);// 计算5的整数分钟
		rightNow.set(Calendar.MINUTE, minute);
		rightNow.set(Calendar.SECOND, 0);
		return sdf.format(rightNow.getTime());
	}
	
	/**
	 * 20分钟 缓存接口5
	 * @throws Exception 
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void execSetCache() throws Exception {
		String beginDate=null;
		String endDate=null;
		if (StringUtils.isBlank(BootConstant.RECORD_TIME_GRID_5)) {
			String minDate=gridDataDao.queryMinDate();
			endDate=dealMaxDate(minDate);
		}else {
			endDate=BootConstant.RECORD_TIME_GRID_5;
		}
		String flagDate=MyUtil.getSomeDateByDay(sdf.format(new Date()),-10);
		if (endDate.compareTo(flagDate) >0) {
			beginDate=MyUtil.getSomeDateByDay(endDate, -1);
			BootConstant.RECORD_TIME_GRID_5=beginDate;
			
			List<String> regions=ScheduledConfig.numList1;
			for (String region : regions) {
				appController.queryGridDataByTimeRegionYh(beginDate, endDate, 5, 0.0, region);
			}
		}
		
	}
	/**
	 * 获取当前日期的前第几天的日期 格式为 yyyy-MM-dd 前一天 传入 -1 前七天 传入 -7 传入0代表当前的日期
	 */
	public static String getDateBeforSevenDay(int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, num);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}
	private String dealMaxDate(String beforeDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse(beforeDate);
		Date afterDate = new Date(date.getTime() - 300000);
		return sdf.format(afterDate);
	}
}
