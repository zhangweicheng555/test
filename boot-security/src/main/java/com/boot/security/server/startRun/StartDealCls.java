package com.boot.security.server.startRun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.boot.security.server.config.ScheduledConfig;
import com.boot.security.server.controller.AppController;
import com.boot.security.server.dao.GridDataDao;

@Order(value = 1)
@Component
public class StartDealCls implements ApplicationRunner {

	@Autowired
	private GridDataDao gridDataDao;
	@Autowired
	private AppController appController;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++程序启动完毕_weichengz++++++++++++++++++");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		String minDate=gridDataDao.queryMinDate();
		String beginDate=getDateBeforSevenDay(-10)+"000000";
		String endDate=dealMaxDate(minDate);
	
		List<String> regions=ScheduledConfig.numList1;
		for (String region : regions) {
			appController.queryGridDataByTimeRegionYh(beginDate, endDate, 5, 0.0, region);
		}
	}
	
	private String dealMaxDate(String beforeDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse(beforeDate);
		Date afterDate = new Date(date.getTime() - 300000);
		return sdf.format(afterDate);
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

}
