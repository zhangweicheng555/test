package com.boot.security.server.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.boot.security.server.model.HiGridDataHour;

public interface HiGridDataHourService {

	public Integer queryPeopleNumByTimeRange(Date dateStr, String region);

	public HiGridDataHour queryHiGridDataHourLatest(String region);

	public long queryCount(HttpSession session);

}
