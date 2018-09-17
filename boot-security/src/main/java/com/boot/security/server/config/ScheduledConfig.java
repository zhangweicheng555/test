package com.boot.security.server.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.boot.security.server.service.GridDataService;
/** 定时器使用 */
//@Configuration
//@EnableScheduling
public class ScheduledConfig {
	
	@Autowired
	private GridDataService gridDataService;

	/**
	 * 30分钟扫一下
	 */
	//@Scheduled(cron = "0 0/30 * * * ?")
	public void execByThirtyMin() {
		//查询表中前四小时的数据
		String beforeDate = gridDataService.queryBeforeDate();
		//根据这个时间查询出LTE_GRID_USER_NUM  超过前4小时的数据插入到历史表中
		gridDataService.insertBatch(beforeDate);
		//LTE_GRID_USER_NUM删除 小于等于这个日期的数据
		gridDataService.deleteBatch(beforeDate);
	}
	
	/**
	 * 下午2点到晚上8点 每5分钟执行一次
	 */
	//@Scheduled(cron = "0 0/5 17-20 * * ?")
	public void scheduledByFiveMinute() throws ParseException {
		
	}

	/**
	 * 每天21点执行
	 */
	//@Scheduled(cron = "0 0 21 * * ?")
	public void dateTask() {
	}
}
