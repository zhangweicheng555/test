package com.boot.security.server.startRun;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
//@Order(value=12)
//@Component
public class TestOne implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("-------------------------------------");
		System.out.println("---------------这是首先执行的方法--------------");
		System.out.println("-------------------------------------");
	}
}
