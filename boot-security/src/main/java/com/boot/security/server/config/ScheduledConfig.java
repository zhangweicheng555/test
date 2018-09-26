package com.boot.security.server.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.RegionService;

/** 定时器使用 */
@Configuration
@EnableScheduling
public class ScheduledConfig {

	@Autowired
	private GridDataService gridDataService;
	@Autowired
	private RegionService regionService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 0点三分执行 确认入库的时间 正式服
	 */
	// @Transactional
	// @Scheduled(cron = "0 3 0 * * ?")
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

	/**
	 * 每五分钟执行一次 测试服 栅格定时
	 */
	@Transactional
	@Scheduled(cron = "0 0/5 * * * ?")
	public void execByFiveMin() throws ParseException {
		String setTime = BootConstant.Back_Send_Time;
		String nowDate = null;
		if (("0").equals(setTime)) {
			nowDate = getNowDate();
		} else {
			nowDate = setTime;
		}
		// 更新表中的所有时间和所有人数
		gridDataService.updateDate(nowDate);
		// 插入到新表中
		gridDataService.insertNewData(nowDate);
		// b域导入
		// 处理时间
		String bdate = nowDate.substring(0, 12);
		regionService.updateDate(bdate);
		regionService.insertNewData(bdate);
		if (!("0").equals(setTime)) {
			BootConstant.Back_Send_Time = dealMaxDate(setTime);
		}
	}

	/**
	 * 每五分钟执行一次 测试服 B域定时
	 */
	 @Transactional
	 @Scheduled(cron = "0 0/5 * * * ?")
	public void execByBFiveMin() throws ParseException {
		String nowDate = getNowDate();
		// 处理时间
		String bdate = nowDate.substring(0, 12);
		regionService.updateDate(bdate);
		regionService.insertNewData(bdate);
	}

	private String dealMaxDate(String beforeDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse(beforeDate);
		Date afterDate = new Date(date.getTime() + 300000);
		return sdf.format(afterDate);
	}

	public String getNowDate() {
		Calendar rightNow = Calendar.getInstance();
		int minute = rightNow.get(Calendar.MINUTE);
		minute = Math.round(minute / 5 * 5);// 计算5的整数分钟
		rightNow.set(Calendar.MINUTE, minute);
		rightNow.set(Calendar.SECOND, 0);
		return sdf.format(rightNow.getTime());
	}
}
