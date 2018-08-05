package com.boot.security.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.ImsiTrackDataDao;
import com.boot.security.server.model.ImsiTrackData;
import com.boot.security.server.model.TraceModel;
import com.boot.security.server.service.ImsiTrackDataService;

@Service
public class ImsiTrackDataServiceImpl implements ImsiTrackDataService {

	@Autowired
	private ImsiTrackDataDao imsiTrackDataDao;

	@Override
	public List<Map<String, Object>> queryDataByParam(String imsi, String beginDate, String endDate) {
		return imsiTrackDataDao.queryDataByParam(imsi, beginDate, endDate);
	}

	@Override
	public void save(ImsiTrackData imsiTrackData) {
		imsiTrackDataDao.save(imsiTrackData);
	}

	@Override
	public List<Map<String, Object>> userScatterPoint(TraceModel traceModel) {
		List<Double> users=traceModel.getTels();
		List<String> times=traceModel.getTimes();
		List<Map<String, Object>> all=new ArrayList<Map<String, Object>>();
		for (Double user : users) {
			Map<String, Object> mapM=new HashMap<>();
			List<Map<String, Object>> scatterPar=new ArrayList<Map<String, Object>>();
			for (String time : times) {
				Map<String, Object> map=new HashMap<String, Object>();
				Map<String, Object> mapOne=imsiTrackDataDao.userScatterPoint(user,time,traceModel.getRegion());
				if (mapOne != null && !mapOne.isEmpty()) {
					map.put("times", time);
					map.put("region", mapOne);
				}else {
					map.put("times", time);
					map.put("region", new HashMap<>());
				}
				scatterPar.add(map);
			}
			mapM.put("tels", user);
			mapM.put("scatterPar", scatterPar);
			all.add(mapM);
		}
		return all;
	}

}
