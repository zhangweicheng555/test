package com.boot.security.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.activiti.spring.boot.ActivitiProperties;
import org.activiti.spring.boot.RestApiAutoConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityDataConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.boot.security.server.activiti.listener.GlobalEventListener;

/**
 * 启动类
 */
@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration(exclude = { org.activiti.spring.boot.RestApiAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class
		})
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	/**
	 * 配置流程监听器
	 * 
	 * @author a-zhangweicheng
	 */
	@Configuration
	@EnableConfigurationProperties(ActivitiProperties.class)
	public static class DataSourceProcessEngineConfiguration extends AbstractProcessEngineAutoConfiguration {

		@Autowired
		public ActivitiEventListener globalEventListener() {
			GlobalEventListener globalEventListener = new GlobalEventListener();
			Map<String, String> handlers = globalEventListener.getHandlers();
			handlers.put("TASK_CREATED", "taskCreateListener");
			handlers.put("TASK_COMPLETED", "taskCompleteListener");
			handlers.put("PROCESS_COMPLETED", "processCompletedListener");
			handlers.put("PROCESS_CANCELLED", "processCancelledListener");
			return globalEventListener;
		}

		/**
		 * 解决流程图乱码
		 */
		@Bean
		@ConditionalOnMissingBean
		public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource,
				PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor)
				throws IOException {

			SpringProcessEngineConfiguration config = this.baseSpringProcessEngineConfiguration(dataSource,
					transactionManager, springAsyncExecutor);

			// 解决流程图中文乱码
			config.setActivityFontName("宋体");
			config.setLabelFontName("宋体");
			config.setAnnotationFontName("宋体");

			List<ActivitiEventListener> eventListeners = new ArrayList<ActivitiEventListener>();
			eventListeners.add(globalEventListener());
			config.setEventListeners(eventListeners);

			return config;
		}
	}

}
