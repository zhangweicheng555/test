package com.boot.security.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.model.TestH;
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
		List<TestH> lMaps = sysUserService.find1();
		Set<String> set = new HashSet<>();
		for (TestH testH : lMaps) {
			int x = testH.getX();
			int y = testH.getY();

			int beginX = (x - 100) + 1;
			int endX = (x + 100) - 1;

			int beginY = (y - 100) + 1;
			int endY = (y + 100) - 1;

			for (int i = beginX; i <= endX; i++) {
				for (int j = beginY; j <= endY; j++) {
					set.add(String.valueOf(i) + "_" + String.valueOf(j));
				}
			}
		}
		System.out.println(set.size() + "===========================");
		for (String key : set) {
			String[] arrayStr = key.split("_");
			sysUserService.find2(arrayStr[0], arrayStr[1]);
		}
		return "succeess";
	}
}
