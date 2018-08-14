package com.boot.security.server.startRun;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(value=1)
@Component
public class StartDealCls implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++++++++++++++++++++++程序启动完毕开始处理程序++++++++++++++++++");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		Date nowDate = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");//可以方便地修改日期格式 
		String nowDateStr = dateFormat.format(nowDate); 
		System.out.println(nowDateStr); 
	}
	public static void main(String[] args) {
		Date nowDate = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式 
		String nowDateTime = dateFormat.format(nowDate); 
		String nowDateStr="2018/7/27 "+nowDateTime;
		System.out.println(nowDateStr);
	}
}
