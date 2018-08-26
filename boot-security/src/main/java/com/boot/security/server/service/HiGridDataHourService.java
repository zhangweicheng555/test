package com.boot.security.server.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.boot.security.server.model.HiGridDataHour;

public interface HiGridDataHourService {

	public Integer queryPeopleNumByTimeRange(Date dateStr, String region);

	public HiGridDataHour queryHiGridDataHourLatest(String region,String maxDate);

	public long queryCount(HttpSession session);
	
	public String queryMaxDate();

	public List<Integer> queryPeopleNumByTimeRangeNew(List<String> listDates, String key);


}
