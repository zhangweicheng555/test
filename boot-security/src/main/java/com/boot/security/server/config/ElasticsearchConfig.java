package com.boot.security.server.config;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

	@Bean
	public TransportClient transportClient() throws UnknownHostException {
		// 如果有多个地址 一个个 add进去
		InetSocketTransportAddress node = new InetSocketTransportAddress(InetAddress.getByName("192.168.233.21"), 9300);
		
		Settings settings = Settings.builder().put("cluster.name", "zwc").build();
		
		TransportClient client=new PreBuiltTransportClient(settings);
		client.addTransportAddress(node);//有多个地址 生成多个就可以  然后多个添加
		
		return client;
	}
}
