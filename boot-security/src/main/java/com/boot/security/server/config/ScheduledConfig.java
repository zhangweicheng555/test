package com.boot.security.server.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.boot.security.server.service.GridDataService;

/** 定时器使用 */
@Configuration
@EnableScheduling
public class ScheduledConfig {

	@Autowired
	private GridDataService gridDataService;

	/**
	 * 0点三分执行
	 * 确认入库的时间  正式服
	 */
	//@Transactional
	//@Scheduled(cron = "0 3 0 * * ?")
	public void execByThirtyMin() {
		// 查询表中最大时间
		String beforeDate = gridDataService.queryMaxDate();
		// 将小于这个时间的插入到历史表中
		gridDataService.insertBatch(beforeDate);
		// LTE_GRID_USER_NUM删除 小于这个日期的数据
		gridDataService.deleteBatch(beforeDate);
	}
	/**
	 * 每五分钟执行一次
	 * 测试服
	 * @throws ParseException 
	 */
	@Transactional
	@Scheduled(cron = "0 0/5 * * * ?")
	public void execByFiveMin() throws ParseException {
		System.out.println("开始定时器-------------");
		// 查询表中最大时间
		String beforeDate = gridDataService.queryMaxDate();
		//加5分钟
		String nowDate=dealMaxDate(beforeDate);
		// 更新表中的所有时间和所有人数
		gridDataService.updateDate(nowDate);
		//更新表中的  select ceil(dbms_random.value(1,42)) from dual;
		//插入到新表中 
		gridDataService.insertNewData(nowDate);
	}

	private  String dealMaxDate(String beforeDate) throws ParseException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 Date date = sdf.parse(beforeDate);
		 Date afterDate = new Date(date.getTime() + 300000);
		return sdf.format(afterDate);
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
}
