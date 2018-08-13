package com.boot.security.server.config;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
//@EnableScheduling
public class ScheduledConfig {

	 @Scheduled(cron="*/5 * * * * ?")
	public void dateTask() {
		System.out.println("這是每個5秒鐘打印一次。。。。。。");
	}
}
