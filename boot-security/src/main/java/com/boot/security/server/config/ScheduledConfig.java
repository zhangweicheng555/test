package com.boot.security.server.config;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import com.boot.security.server.service.GridDataService;

/** 定时器使用 */
//@Configuration
//@EnableScheduling
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
