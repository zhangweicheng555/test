package com.boot.security.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.common.BootConstant;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.CommonModel;
import com.boot.security.server.model.GridData;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.RegionService;

@Service
public class GridDataServiceImpl implements GridDataService {

	@Autowired
	private GridDataDao gridDataDao;
	@Autowired
	private RegionService regionService;

	@Override
	public void save(GridData gridData) {
		gridDataDao.save(gridData);
	}

	@Override
	public List<Map<String, Object>> queryGridDataByRegion(String[] regionArr, String maxDate) {

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridDataByRegion(maxDate, numPercent, regionArr);
	}

	@Override
	public List<Map<String, Object>> querySingleGridData() {
		return gridDataDao.querySingleGridData();
	}

	// 测试
	@Override
	public Map<String, Object> queryGridDataByTimeRegion(Date date, String region, Double warnNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> map = new HashMap<>();

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		String dateNow = sdf.format(date);
		// 查询这个馆这个时间的所有的人数的数量
		Double total = gridDataDao.queryGridPeopleNum(dateNow, region, numPercent);
		if (total > 0) {
			map.put("date", dateNow);
			map.put("total", total);
			map.put("grids", new ArrayList<>());
			map.put("misc", new AnalysisModel());
			// 查询符合条件的数据
			List<CommonModel> list = gridDataDao.queryGridDataByTimeRegion(dateNow, region, numPercent, warnNum);
			if (list != null && list.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();
				for (CommonModel commonModel : list) {
					Map<String, Object> mapM = new HashMap<>();
					mapM.put("userCount", commonModel.getUserCount());
					mapM.put("x", commonModel.getX());
					mapM.put("y", commonModel.getY());
					listMaps.add(mapM);
				}
				map.put("grids", listMaps);
			}
			
			//查询b域
			dateNow=dateNow.substring(0, 12);
			map.put("misc", regionService.queryGridWarnData(region, dateNow));
		} else {
			map = null;
		}
		return map;
	}
	
	
	
	@Override
	public Double queryGridPeopleNumDataNew(String region, String maxDate) {
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridPeopleNum(maxDate, region, numPercent);
	}

	@Override
	public List<Map<String, Object>> queryGridWarnData(Double warnNum, String maxDate, String region) {
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		return gridDataDao.queryGridWarnData(warnNum, maxDate, region, numPercent);
	}

	@Override
	public String queryMaxDate() {
		return gridDataDao.queryMaxDate();
	}

	@Override
	public String queryBeforeDate() {
		return gridDataDao.queryBeforeDate();
	}

	@Override
	public void insertBatch(String beforeDate) {
		gridDataDao.insertBatch(beforeDate);
	}

	@Override
	public void deleteBatch(String beforeDate) {
		gridDataDao.deleteBatch(beforeDate);
	}

	@Override
	public String queryMinDate() {
		return gridDataDao.queryMinDate();
	}

	@Override
	public void updateDate(String nowDate) {
		gridDataDao.updateDate(nowDate);
	}

	@Override
	public void insertNewData(String beforeDate) {
		gridDataDao.insertNewData(beforeDate);
	}

	@Override
	public List<Map<String, Object>> queryPeopleNumByTimeRange(List<String> listDates, String region) {

		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		List<Map<String, Object>> list=new ArrayList<>();
		if (StringUtils.isNoneBlank(region)) {
			String[] regionStr=region.trim().split(",");
			for (int i = 0; i < regionStr.length; i++) {
				Map<String, Object> map=new HashMap<>();
				map.put("name", regionStr[i]);
				List<Double> listDouble=new ArrayList<>();
				for (String date : listDates) {
					listDouble.add(gridDataDao.queryGridPeopleNum(date, region, numPercent));
				}
				map.put("item", listDouble);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public void deleteBatchT(String beforeDate) {
		gridDataDao.deleteBatchT(beforeDate);
	}


}
