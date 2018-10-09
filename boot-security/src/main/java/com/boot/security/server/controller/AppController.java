package com.boot.security.server.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.config.ScheduledConfig;
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.RegionService;
import com.boot.security.server.util.MyUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * app对接相关接口 1
 */

@RestController
@EnableSwagger2
@RequestMapping("/app")
public class AppController {

	// 初始化场馆编号
	public final static List<String> numList = new ArrayList<String>(
			Arrays.asList("All", "1", "2", "3", "4.1", "4.2", "5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2",
					"NH", "EH", "WH", "V1_1", "V1_2", "V1_3", "V1_4", "V2_1", "V2_2", "V2_3", "V3_1", "V3_2", "V3_3",
					"V3_4", "V3_5", "V3_6", "V3_7", "V4_1", "V4_2", "V4_3", "V4_4", "V4_5", "V4_6", "V5"));

	public final static List<String> numListN = new ArrayList<String>(
			Arrays.asList("Indoor", "1", "2", "3", "4.1", "4.2", "5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2",
					"NH", "EH", "WH", "Outdoor", "V1_1", "V1_2", "V1_3", "V1_4", "V2_1", "V2_2", "V2_3", "V3_1", "V3_2",
					"V3_3", "V3_4", "V3_5", "V3_6", "V3_7", "V4_1", "V4_2", "V4_3", "V4_4", "V4_5", "V4_6", "V5"));

	@Autowired
	private GridDataService gridDataService;

	@Autowired
	private GridDataDao gridDataDao;

	/**
	 * 五、接口5 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割 warnNum ：废弃
	 */
	@ApiOperation(value = "接口5:指定时间范围和场馆编号获取指定场馆的栅格数据", notes = "指定时间范围和场馆编号/时间范围根据指定的时间粒度切割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDateStr", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDateStr", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "warnNum", value = "告警人数(废弃)", dataType = "int", required = false),
			@ApiImplicitParam(name = "region", value = "场馆编号", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataByTimeRegion", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataByTimeRegion(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "warnNum", required = false) Double warnNum,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridHistoryParameterList", new ArrayList<>());
		try {
			warnNum = 0.0;
			String minDate = gridDataDao.queryMinDate();
			List<Date> listDates = MyUtil.getDateList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();
				for (Date date : listDates) {
					Map<String, Object> map2 = gridDataService.queryGridDataByTimeRegion(date, region, warnNum,
							minDate);
					if (map2 != null) {
						listMaps.add(map2);
					}
				}
				map.put("gridHistoryParameterList", listMaps);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 五、定时优化使用： 接口5 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割
	 * warnNum ：废弃
	 */
	@ApiOperation(value = "接口5:指定时间范围和场馆编号获取指定场馆的栅格数据", notes = "指定时间范围和场馆编号/时间范围根据指定的时间粒度切割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDateStr", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDateStr", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "warnNum", value = "告警人数(废弃)", dataType = "int", required = false),
			@ApiImplicitParam(name = "region", value = "场馆编号", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataByTimeRegionYh", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataByTimeRegionYh(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "warnNum", required = false) Double warnNum,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridHistoryParameterList", new ArrayList<>());
		try {
			warnNum = 0.0;
			List<String> hiDates = gridDataService.queryHiDates(beginDateStr, endDateStr, region);
			List<Date> listDates = MyUtil.getDateList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();
				for (Date date : listDates) {
					Map<String, Object> map2 = gridDataService.queryGridDataByTimeRegionYh(date, region, warnNum,
							hiDates);
					if (map2 != null) {
						listMaps.add(map2);
					}
				}
				map.put("gridHistoryParameterList", listMaps);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 2、接口2根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。
	 */
	@ApiOperation(value = "接口2:根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。", notes = "据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDate", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDate", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "regionStr", value = "场馆编号(多个以逗号分隔)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryPeopleNumByTimeRangeNew", method = RequestMethod.GET)
	public Map<String, Object> queryPeopleNumByTimeRangeNew(
			@RequestParam(value = "beginDate", required = true) String beginDateStr,
			@RequestParam(value = "endDate", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "regionStr", required = true) String regionStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("item", new ArrayList<>());
		try {
			List<String> listDates = MyUtil.getDateStrList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();

				String beginDate = listDates.get(0);
				String endDate = listDates.get(listDates.size() - 1);

				Double numPercent = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					numPercent = BootConstant.People_Num_Percent;
				} else {
					numPercent = null;
				}
				if (StringUtils.isNoneBlank(regionStr)) {
					if(("all").equals(regionStr)) {
						String regionStrs = "";
						for (String region : ScheduledConfig.numList1) {
							if (StringUtils.isNoneBlank(regionStrs)) {
								regionStrs = regionStrs + "," + region;
							} else {
								regionStrs = region;
							}
						}
						regionStr=regionStrs;
					}
					String[] regionStrs = regionStr.trim().split(",");
					for (int i = 0; i < regionStrs.length; i++) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("name", regionStrs[i]);
						List<Double> listDouble = new ArrayList<>();

						/*** 查询这个场馆对应的所有的日期 */
						List<String> datesStr = gridDataDao.findDatesStr(beginDate, endDate, regionStrs[i]);
						for (String date : listDates) {
							Double doubleNum = findGridPeopleNumClusterNew(datesStr, date, regionStrs[i], numPercent);
							listDouble.add(doubleNum);
						}
						map1.put("item", listDouble);
						listMaps.add(map1);
					}
				}

				if (listMaps != null && listMaps.size() > 0) {
					// 处理总和
					handleListMap(listMaps);
					map.put("item", listMaps);
				}
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 2、接口2根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。
	 */
	@ApiOperation(value = "接口2:根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。", notes = "据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDate", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "regionStr", value = "场馆编号(多个以逗号分隔)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryPeopleNumByTimeRange", method = RequestMethod.GET)
	public Map<String, Object> queryPeopleNumByTimeRange(
			@RequestParam(value = "beginDate", required = true) String beginDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "regionStr", required = true) String regionStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("item", new ArrayList<>());
		try {
			String endDateStr = gridDataService.queryMaxDate();
			List<String> listDates = MyUtil.getDateStrList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();

				String beginDate = listDates.get(0);
				String endDate = listDates.get(listDates.size() - 1);

				Double numPercent = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					numPercent = BootConstant.People_Num_Percent;
				} else {
					numPercent = null;
				}
				if (StringUtils.isNoneBlank(regionStr)) {
					String[] regionStrs = regionStr.trim().split(",");
					for (int i = 0; i < regionStrs.length; i++) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("name", regionStrs[i]);
						List<Double> listDouble = new ArrayList<>();

						/*** 查询这个场馆对应的所有的日期 */
						List<String> datesStr = gridDataDao.findDatesStr(beginDate, endDate, regionStrs[i]);
						for (String date : listDates) {
							Double doubleNum = findGridPeopleNumClusterNew(datesStr, date, regionStrs[i], numPercent);
							listDouble.add(doubleNum);
						}
						map1.put("item", listDouble);
						listMaps.add(map1);
					}
				}

				if (listMaps != null && listMaps.size() > 0) {
					// 处理总和
					handleListMap(listMaps);
					map.put("item", listMaps);
				}
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	private Double findGridPeopleNumClusterNew(List<String> dates, String dateStr, String region, Double numPercent) {
		if (dates == null || dates.size() == 0) {
			return 0.0;
		} else {
			if (dates.contains(dateStr)) {
				return gridDataService.findNumByDate(dateStr, region, numPercent);
			} else {
				return 0.0;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void handleListMap(List<Map<String, Object>> listMaps) {
		
		Map<String, Object> mapOne = new HashMap<>();
		mapOne.put("name", "Indoor");
		List<Double> listNew = new ArrayList<Double>();

		for (int i = 0; i < 16; i++) {
			Map<String, Object> map = listMaps.get(i);
			List<Double> listM = (List<Double>) map.get("item");
			if (i == 0) {
				listNew = listM;
			} else {
				List<Double> list = new ArrayList<>();
				for (int j = 0; j < listNew.size(); j++) {
					list.add(j, listNew.get(j) + listM.get(j));
				}
				listNew = list;
			}
		}
		
		Map<String, Object> mapTwo = new HashMap<>();
		mapTwo.put("name", "Outdoor");
		List<Double> listTwo = new ArrayList<Double>();

		for (int i = 16; i < 37; i++) {
			Map<String, Object> map = listMaps.get(i);
			List<Double> listM = (List<Double>) map.get("item");
			if (i == 0) {
				listTwo = listM;
			} else {
				List<Double> list = new ArrayList<>();
				for (int j = 0; j < listTwo.size(); j++) {
					list.add(j, listTwo.get(j) + listM.get(j));
				}
				listTwo = list;
			}
		}
		
		mapOne.put("item", listNew);
		listMaps.add(0, mapOne);
		mapTwo.put("item", listTwo);
		listMaps.add(17, mapTwo);	
	}

	/**
	 * 接口8 获取当前所有场馆的告警信息。 根据传入的告警数量 获取所有的栅格数据 最大时间的
	 */
	@ApiOperation(value = "接口8:获取当前所有场馆的告警信息", notes = "根据传入的告警数量(37个, 获取对应栅格最大时间的数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "warnNum", value = "告警人数多个以逗号分割", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridWarnData", method = RequestMethod.GET)
	public Map<String, Object> queryGridWarnData(@RequestParam(value = "warnNum", required = true) String warnNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("warningParameter", new ArrayList<>());
		try {
			if (StringUtils.isBlank(warnNum)) {
				map.put("status", 2);
				map.put("msg", "未传入告警数量字符串warnNum");
			} else {
				String[] warnNums = warnNum.split(",");
				if (warnNums.length != 37) {
					map.put("status", 2);
					map.put("msg", "请传入37个场馆对应的告警数量");
				} else {
					List<Map<String, Object>> listDatas = new ArrayList<>();
					String maxDate = gridDataService.queryMaxDate();
					if (StringUtils.isBlank(maxDate)) {
						map.put("status", 2);
						map.put("msg", "数据库中日期不存在");
					} else {
						for (int i = 1; i < numList.size(); i++) {
							Double dVal = Double.valueOf(warnNums[i - 1]);
							if (dVal != -1) {
								List<Map<String, Object>> listRegionDatas = gridDataService.queryGridWarnData(dVal,
										maxDate, numList.get(i));
								if (listRegionDatas != null && listRegionDatas.size() > 0) {
									for (Map<String, Object> map2 : listRegionDatas) {
										listDatas.add(map2);
									}
								}
							}
						}
						if (listDatas.size() > 0) {
							map.put("warningParameter", listDatas);
						} else {
							map.put("status", 1);
							map.put("msg", "没有对应条件的数据！");
						}
					}
				}
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因: " + e.getLocalizedMessage());
		}
		return map;
	}

	@Autowired
	private RegionService regionService;

	/**
	 * 接口6 新 b域接口
	 */
	@ApiOperation(value = "接口6:获取所有场馆的的年龄段、来源地、男女比例", notes = "最后时间对应的所有场馆的的年龄段、来源地、男女比例")
	@RequestMapping(value = "/queryHiGridDataHourLatest", method = RequestMethod.GET)
	public Map<String, Object> queryHiGridDataHourLatest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			String maxDate = regionService.queryMaxDateClus();
			if (StringUtils.isBlank(maxDate)) {
				map.put("status", 2);
				map.put("msg", "数据库中日期不存在");
			} else {
				List<AnalysisModel> list = new ArrayList<AnalysisModel>();
				for (int j = 0; j < numList.size(); j++) {
					String key = numList.get(j);// region
					list.add(regionService.queryGridWarnDataCluster(key, maxDate));
				}
				map.put("miscParameter", list);
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 接口1 最新 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。 各个场馆最新的总人数 这里面有个问题 就是场馆的时间可能不一致
	 * 时间就是数据库的最大时间 ----- maxDate 是数据的最大时间 reqDate 没用 正式服
	 */
	@ApiOperation(value = "接口1:获取所有场馆的各自在馆人数和所有场馆总人数", notes = "所有场馆最后时间的各自在馆人数和所有场馆总人数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "reqDate", value = "时间(正式服无效(随意指定),但未必传参数)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridPeopleNumData", method = RequestMethod.GET)
	public Map<String, Object> queryGridPeopleNumData(
			@RequestParam(value = "reqDate", required = true) String reqDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("time", "");
		map.put("peopleParameterList", "");
		String maxDate = null;
		reqDate = gridDataService.queryMaxDate();
		try {
			if (StringUtils.isBlank(reqDate)) {
				map.put("status", 2);
				map.put("msg", "未传入请求的参数:reqDate:格式(20180824234600)");
			} else {
				maxDate = reqDate;
				if (StringUtils.isNoneBlank(maxDate)) {
					List<Double> peopleParameterList = new ArrayList<Double>();
					for (int j = 1; j < numList.size(); j++) {
						String key = numList.get(j);// region
						peopleParameterList.add(gridDataService.queryGridPeopleNumDataNew(key, maxDate));
					}
					Double countIn = 0.0;
					for (int i = 0; i < 16; i++) {
						countIn += peopleParameterList.get(i);
					}
					Double countOut = 0.0;
					for (int j = 16; j < 37; j++) {
						countOut += peopleParameterList.get(j);
					}

					peopleParameterList.add(0, countIn);
					peopleParameterList.add(17, countOut);
					map.put("peopleParameterList", peopleParameterList);
				} else {// fasle
					map.put("status", 2);
					map.put("msg", "传入请求的参数:reqDate格式不正确(20180824234600)");
				}
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		map.put("time", reqDate);
		return map;
	}

	/**
	 * 传入场馆编号，返回栅格集合 对接接口 接口4 获取指定场馆的栅格数据。 返回各个长场馆 最新的数据 x y 并且 用户大于0
	 */
	@ApiOperation(value = "接口4:获取指定场馆的栅格数据", notes = "获取指定场馆的栅格数据，多个以逗号分割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "region", value = "场馆编号(多个以逗号分隔)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataByRegion", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataByRegion(@RequestParam("region") String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("time", "");
		map.put("gridParameterList", new ArrayList<>());
		String maxDate = gridDataService.queryMaxDate();
		try {
			if (StringUtils.isBlank(maxDate)) {
				map.put("status", 2);
				map.put("msg", "数据库中日期不存在");
			} else {
				if (StringUtils.isNoneBlank(region)) {
					String[] regionArr = region.trim().split(",");
					List<Map<String, Object>> list = gridDataService.queryGridDataByRegion(regionArr, maxDate);
					if (list.size() > 0) {
						map.put("gridParameterList", list);
					} else {
						map.put("status", 1);
						map.put("msg", "没有对应条件的数据！");
					}
				} else {
					map.put("status", 2);
					map.put("msg", "未传入请求参数region");
				}
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		map.put("time", maxDate);
		return map;
	}

	/**
	 * 接口10 设置移动百分比
	 */
	@ApiOperation(value = "接口10:设置移动百分比", notes = "设置移动百分比,移动百分比 float类型 值范围 0.00 - 1.00")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userPercent", value = "移动百分比", dataType = "double", required = true) })
	@ResponseBody
	@RequestMapping(value = "/setUserPercent", method = RequestMethod.GET)
	public Map<String, Object> setUserPercent(
			@RequestParam(value = "userPercent", required = true) Double userPercent) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BootConstant.People_Num_Percent = userPercent;
			map.put("status", 0);
			map.put("msg", "设置移动百分比成功：" + BootConstant.People_Num_Percent + "！");
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常！");
		}
		return map;
	}

	/**
	 * 接口11 获取移动百分比
	 */
	@ApiOperation(value = "接口11:获取移动百分比", notes = "获取移动百分比")
	@ResponseBody
	@RequestMapping(value = "/getUserPercent", method = RequestMethod.GET)
	public Map<String, Object> getUserPercent() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Double userPercent = BootConstant.People_Num_Percent;
			if (userPercent != null && userPercent > 0) {
				map.put("status", 0);
				map.put("userPercent", userPercent);
				map.put("msg", "获取移动百分比成功：" + BootConstant.People_Num_Percent + "！");
			} else {
				map.put("status", 0);
				map.put("userPercent", 0);
				map.put("msg", "未设置移动百分比！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("userPercent", 0);
			map.put("msg", "系统异常！");
		}
		return map;
	}

	/**
	 * 接口9 强制设置后台所有接口数据变成更新状态。
	 */
	@ResponseBody
	@RequestMapping("/refreshAllData")
	public Map<String, Object> refreshAllData(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("status", 0);
			map.put("msg", "刷新成功！");
			if (session.getAttribute(BootConstant.LTE_Region_NUM_HOUR) != null) {
				session.removeAttribute(BootConstant.LTE_Region_NUM_HOUR);
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常！");
		}
		return map;
	}

	/**
	 * 14、接口14 根据指定时间范围和场馆编号获取指定场馆的人数数据。
	 */
	@RequestMapping(value = "/queryDateForMinute", method = RequestMethod.GET)
	public Map<String, Object> queryDateForMinute(@RequestParam(value = "beginDate", required = true) String beginDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("peopleHistoryParameterList", new ArrayList<>());
		try {
			List<Map<String, Object>> list = regionService.queryDateForMinute(beginDate, endDate, region);
			if (list.size() > 0) {
				map.put("peopleHistoryParameterList", list);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 清除接口2的缓存
	 */
	@RequestMapping(value = "/clearCache", method = RequestMethod.GET)
	public Map<String, Object> clearCache() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			gridDataService.clearCache();
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  ");
		}
		return map;
	}

	/**
	 * 
	 * @Description: 接口5 缓存使用
	 * @author weichengz
	 * @date 2018年10月3日 下午6:55:04
	 */
	public Map<String, Object> getHiMap(String region, Double warnNum, Double numPercent, String dateNow) {
		return gridDataService.getHiMap(region, warnNum, numPercent, dateNow);
	}

	/**
	 * 清除接口2的缓存
	 */
	@RequestMapping(value = "/clearFiveCache", method = RequestMethod.GET)
	public Map<String, Object> clearFiveCache() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			gridDataService.clearFiveCache();
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  ");
		}
		return map;
	}
}
