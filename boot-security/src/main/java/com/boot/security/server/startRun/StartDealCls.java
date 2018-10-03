package com.boot.security.server.startRun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.boot.security.server.config.ScheduledConfig;
import com.boot.security.server.dao.GridDataDao;

@Order(value = 1)
@Component
public class StartDealCls implements ApplicationRunner {

	@Autowired
	private GridDataDao gridDataDao;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++程序启动完毕_weichengz++++++++++++++++++");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String minDate=gridDataDao.queryMinDate();
		
		List<String> regions=ScheduledConfig.numList1;
		for (String region : regions) {
			
		}
	}

}
