package com.boot.security.server.controller;

import java.util.List;

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
	public List<SysUser> searchTest(@RequestParam("beginDate") String beginDate,@RequestParam("endDate") String endDate) {
		return sysUserService.findTest(beginDate,endDate);
	}

}