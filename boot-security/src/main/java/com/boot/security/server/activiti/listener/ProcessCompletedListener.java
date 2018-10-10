package com.boot.security.server.activiti.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 流程完成监听器 主要用来执行事件执行等
 * 
 */
@Component
public class ProcessCompletedListener implements EventHandler {

	@Override
	public void handle(ActivitiEvent event) {
		HistoryService historyService = event.getEngineServices().getHistoryService();
		RepositoryService repositoryService = event.getEngineServices().getRepositoryService();

		String processInstanceId = event.getProcessInstanceId();
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).includeProcessVariables().singleResult();
		// Long taskId = (Long) processInstance.getProcessVariables().get("taskId");

	}

}