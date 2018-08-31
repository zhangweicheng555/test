package com.boot.security.server.config;

import java.text.ParseException;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableScheduling
public class ScheduledConfig {
	
	/** 定时器使用 */


	

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
