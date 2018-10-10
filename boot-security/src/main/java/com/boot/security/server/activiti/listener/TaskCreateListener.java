package com.boot.security.server.activiti.listener;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新的实体已经创建。该实体包含在本事件里
 * 
 */
@Component
public class TaskCreateListener implements EventHandler {

	@Override
	public void handle(ActivitiEvent event) {
		System.out.println("任务创建了。。。。。。");
		//ActivitiEntityEventImpl eventImpl = (ActivitiEntityEventImpl) event;
	   //TaskEntity taskEntity = (TaskEntity) eventImpl.getEntity();
		
	}

}