package com.boot.security.server.service;

import java.util.List;
import java.util.Map;

import com.boot.security.server.model.ImsiTrackData;
import com.boot.security.server.model.TraceModel;

public interface ImsiTrackDataService {

	public List<Map<String, Object>> queryDataByParam(String imsi, String beginDate, String endDate);

	public void save(ImsiTrackData imsiTrackData);

	public List<Map<String, Object>> userScatterPoint(TraceModel traceModel);
}
