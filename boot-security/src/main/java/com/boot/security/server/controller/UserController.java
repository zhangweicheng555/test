package com.boot.security.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.service.SysUserService;

/**
 * app对接相关接口
 */

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(value = "/userList")
	@ResponseBody
	public List<SysUser> searchTest(@RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate) {
		return sysUserService.findTest(beginDate, endDate);
	}
	
	
	@RequestMapping(value = "/testCache")
	public void testCache(@Param("num1") Integer num1) {
		sysUserService.testCache(num1);
	}
	@RequestMapping(value = "/clear")
	@ResponseBody
	public String clear() {
		return sysUserService.clear();
	}
	
	
}
