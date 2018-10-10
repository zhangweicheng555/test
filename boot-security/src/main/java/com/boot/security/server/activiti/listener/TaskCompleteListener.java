package com.boot.security.server.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.springframework.stereotype.Component;

/**
 * 任务完成监听器
 * 
 */
@Component
public class TaskCompleteListener implements EventHandler {

	@Override
	public void handle(ActivitiEvent event) {
		System.out.println("任务完成了。。。。。。。。。。。");
	}

}