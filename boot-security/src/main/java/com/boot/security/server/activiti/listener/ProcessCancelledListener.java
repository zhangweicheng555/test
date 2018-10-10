package com.boot.security.server.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.springframework.stereotype.Component;

/**
 * 流程删除监听器 主要用来监听流程实例删除事件
 * 调用  RuntimeService.deleteProcessInstance 引起的
 */
@Component
public class ProcessCancelledListener implements EventHandler {


	@Override
	public void handle(ActivitiEvent event) {
		/** 流程实例删除时将队列中的任务移除 */
	//	HistoryService historyService = event.getEngineServices().getHistoryService();

	//	HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
			//	.includeProcessVariables().processInstanceId(event.getProcessInstanceId()).singleResult();

		//Long taskId = Long.valueOf(String.valueOf(processInstance.getProcessVariables().get("taskId")));
		

	}

}