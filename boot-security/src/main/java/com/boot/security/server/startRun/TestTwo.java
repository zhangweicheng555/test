package com.boot.security.server.startRun;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//@Order(value=1)
//@Component
public class TestTwo implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//System.out.println("---------------这是其次执行的方法--------------");
	}

}
