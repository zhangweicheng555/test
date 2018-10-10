package com.boot.security.server.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * Activiti的事件处理器
 *
 */
public interface EventHandler {
	/**
	 * 事件处理器
	 * 
	 * @param event
	 */
	public void handle(ActivitiEvent event);
}