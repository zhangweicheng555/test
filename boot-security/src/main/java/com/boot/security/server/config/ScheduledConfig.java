package com.boot.security.server.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.boot.security.server.model.GridData;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.SysUserService;
import com.boot.security.server.util.SpringUtil;

@Configuration
@EnableScheduling
public class ScheduledConfig {
	/** 定时器使用 */
	private static String beginDateScheduled = "2018/7/27 14:05";

	/** 清理数据使用 */
	private static String dealBDateScheduled = "2018/7/27 14:05:00";
	private static String dealEDateScheduled = "2018/7/27 20:05:00";

	

	/**
	 * 下午2点到晚上8点 每5分钟执行一次
	 */
	@Scheduled(cron = "0 0/5 17-20 * * ?")
	public void scheduledByFiveMinute() throws ParseException {
		SysUserService sysUserService = SpringUtil.getApplicationContext().getBean(SysUserService.class);
		GridDataService gridDataService = SpringUtil.getApplicationContext().getBean(GridDataService.class);
		int num=0;
		/**查询临时表的数据*/
		List<GridData> listGridDatas=sysUserService.findNewGridDateByDate(beginDateScheduled);
		if (listGridDatas !=null && listGridDatas.size() >0) {
			for (GridData gridData : listGridDatas) {
				gridDataService.save(gridData);
			}
			num=listGridDatas.size();
		}
		/**临时表的数据入栅格表*/
		
		//时间加五分钟
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/dd HH:mm");
		Date afterDate = new Date(sdf.parse(beginDateScheduled).getTime() + 300000);
		beginDateScheduled=sdf.format(afterDate);
		
		System.out.println("开始每五分钟 导入一次：  " + beginDateScheduled  +"本次导入数据量:"+num);
	}

	/**
	 * 每天21点执行
	 */
	@Scheduled(cron = "0 0 21 * * ?")
	public void dateTask() {
		SysUserService sysUserService = SpringUtil.getApplicationContext().getBean(SysUserService.class);
		sysUserService.clearTestGridData(dealBDateScheduled,dealEDateScheduled);
		System.out.println("晚上21点开始处理数据。。。。。。");
	}
}
