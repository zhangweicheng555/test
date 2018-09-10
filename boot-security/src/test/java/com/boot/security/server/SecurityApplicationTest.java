package com.boot.security.server;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.security.server.model.GridMapper;
import com.boot.security.server.service.GridMapperService;

import oracle.net.aso.g;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityApplicationTest {

	@Autowired
	private GridMapperService gridMapperService;
	@Test
	public void password() {
		
	}
	
}
