package com.boot.security.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	
	public static void main(String[] args) throws ParseException {
		String begin="2018/7/7 12:00";
		String end="2018/7/7 13:10";
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date beginData=sdf.parse(begin);
		Date endData=sdf.parse(end);
		List<Date> listDates=new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			Date afterDate = new Date(beginData.getTime() + 5*60*1000);
			if (afterDate.compareTo(endData) > 0) {
				break;
			}
			listDates.add(afterDate);
		}
		for (Date date : listDates) {
			System.out.println(sdf.format(date));
		}
	}
}
