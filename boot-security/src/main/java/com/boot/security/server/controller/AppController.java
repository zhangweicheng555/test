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
import com.boot.security.server.dao.GridDataDao;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.CommonModel;
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

	// ALL ZGG_B1,ZGG_1F,ZGG_2F 都是indoor

	// 初始化场馆编号
	public final static List<String> numList = new ArrayList<String>(
			Arrays.asList("All", "1", "2", "3", "4.1", "4.2", "5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2",
					"NH", "EH", "WH", "V1_1", "V1_2", "V1_3", "V1_4", "V2_1", "V2_2", "V2_3", "V3_1", "V3_2", "V3_3",
					"V3_4", "V3_5", "V3_6", "V3_7", "V4_1", "V4_2", "V4_3", "V4_4", "V4_5", "V4_6", "V5"));

	public final static List<String> numListN = new ArrayList<String>(
			Arrays.asList("Indoor", "1", "2", "3", "4.1", "4.2", "5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2",
					"NH", "EH", "WH", "Outdoor", "V1_1", "V1_2", "V1_3", "V1_4", "V2_1", "V2_2", "V2_3", "V3_1", "V3_2",
					"V3_3", "V3_4", "V3_5", "V3_6", "V3_7", "V4_1", "V4_2", "V4_3", "V4_4", "V4_5", "V4_6", "V5"));

	public final static List<String> numListNew = new ArrayList<String>(Arrays.asList("ZGG_2F", "ZGG_1F", "ZGG_B1"));

	@Autowired
	private GridDataService gridDataService;

	@Autowired
	private GridDataDao gridDataDao;

	/**
	 * 五、接口5 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割 warnNum
	 */
	@ApiOperation(value = "接口5:指定时间范围和场馆编号获取指定场馆的栅格数据", notes = "指定时间范围和场馆编号/时间范围根据指定的时间粒度切割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDateStr", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDateStr", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "warnNum", value = "告警人数(废弃)", dataType = "int", required = false),
			@ApiImplicitParam(name = "region", value = "场馆编号", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataByTimeRegionNew", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataByTimeRegionNew(
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
	 * 五、接口5 最新 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割 warnNum
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
			List<String> listDates = MyUtil.getDateStrList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> datas = gridDataService.queryGridDataByTimeRegionNew(beginDateStr, endDateStr,
						region, warnNum);
				Map<String, List<Map<String, Object>>> beginMaps = new HashMap<>();
				if (datas != null && datas.size() > 0) {
					for (Map<String, Object> model : datas) {
						String dateModel = model.get("date").toString();
						if (beginMaps.containsKey(dateModel)) {
							List<Map<String, Object>> dataList = beginMaps.get(dateModel);
							model.remove("date");
							dataList.add(model);
						} else {
							List<Map<String, Object>> dataList = new ArrayList<>();
							model.remove("date");
							dataList.add(model);
							beginMaps.put(dateModel, dataList);
						}
					}
					List<Map<String, Object>> finalMap = new ArrayList<>();
					// 填充不存在的日期
					for (String ergodicDate : listDates) {
						Map<String, Object> mapData = new HashMap<>();
						if (beginMaps.containsKey(ergodicDate)) {
							List<Map<String, Object>> list = beginMaps.get(ergodicDate);
							mapData.put("date", ergodicDate);
							mapData.put("total", list.size());
							mapData.put("grids", list);
						} else {
							mapData.put("date", ergodicDate);
							mapData.put("total", 0);
							mapData.put("grids", new ArrayList<>());
						}
						finalMap.add(mapData);
					}
					map.put("gridHistoryParameterList", finalMap);
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
				// map.put("gridHistoryParameterList", listMaps);
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
	 * 五、接口16 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割 warnNum ：废弃
	 */
	@ApiOperation(value = "接口16:指定时间范围和场馆编号获取指定场馆的栅格数据", notes = "指定时间范围和场馆编号/时间范围根据指定的时间粒度切割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDateStr", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDateStr", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "region", value = "场馆编号indoor outdoor", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataAllOld", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataAllOld(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridHistoryParameterList", new ArrayList<>());
		try {
			if (("Indoor").equals(region) || ("Outdoor").equals(region)) {
				String minDate = gridDataDao.queryMinDate();
				List<Date> listDates = MyUtil.getDateList(beginDateStr, endDateStr, minute);
				if (listDates.size() > 0) {
					List<Map<String, Object>> listMaps = new ArrayList<>();
					for (Date date : listDates) {
						Map<String, Object> map2 = gridDataService.queryGridDataAll(date, region, minDate);
						if (map2 != null) {
							listMaps.add(map2);
						}
					}
					map.put("gridHistoryParameterList", listMaps);
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			} else {
				map.put("status", 2);
				map.put("msg", "region值未Indoor 或  Outdoor");
			}

		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 五、接口16 最新 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割 warnNum
	 */
	@ApiOperation(value = "接口16:指定时间范围和场馆编号获取指定场馆的栅格数据", notes = "指定时间范围和场馆编号/时间范围根据指定的时间粒度切割")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDateStr", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "endDateStr", value = "结束时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "region", value = "场馆编号indoor outdoor", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridDataAll", method = RequestMethod.GET)
	public Map<String, Object> queryGridDataAll(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridHistoryParameterList", new ArrayList<>());
		try {
			if (("Indoor").equals(region) || ("Outdoor").equals(region)) {
				List<String> listDates = MyUtil.getDateStrList(beginDateStr, endDateStr, minute);
				Map<String, List<Map<String, Object>>> kdatas = new HashMap<>();
				if (listDates.size() > 0) {
					List<Map<String, Object>> datas = gridDataService.queryGridDataAllNew(beginDateStr, endDateStr,
							region);
					if (datas != null && datas.size() > 0) {
						for (Map<String, Object> modelMap : datas) {
							String date = modelMap.get("date").toString();
							if (kdatas.containsKey(date)) {
								List<Map<String, Object>> maps = kdatas.get(date);
								modelMap.remove("date");
								maps.add(modelMap);
							} else {
								List<Map<String, Object>> noMaps=new ArrayList<>();
								modelMap.remove("date");
								noMaps.add(modelMap);
								kdatas.put(date, noMaps);
							}
						}
						if (kdatas.size() > 0) {
							List<Map<String, Object>> finalDatas = new ArrayList<>();
							for (String keyDate : listDates) {
								Map<String, Object> kvMap = new HashMap<>();
								if (kdatas.containsKey(keyDate)) {
									List<Map<String, Object>> datasChil = kdatas.get(keyDate);
									kvMap.clear();
									kvMap.put("date", keyDate);
									kvMap.put("total", datasChil.size());
									kvMap.put("grids", datasChil);
									finalDatas.add(kvMap);
								} else {
									kvMap.clear();
									kvMap.put("date", keyDate);
									kvMap.put("total", 0);
									kvMap.put("grids", new ArrayList<>());
									finalDatas.add(kvMap);
								}
							}
							map.put("gridHistoryParameterList", finalDatas);
						} else {
							map.put("gridHistoryParameterList", new ArrayList<>());
						}
					} else {
						map.put("status", 1);
						map.put("msg", "没有对应条件的数据！");
					}
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			} else {
				map.put("status", 2);
				map.put("msg", "region值为Indoor 或  Outdoor");
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
	 * 先不使用这个 2、优化使用 接口2根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。
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
	 * 使用这个 2、接口2根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。 结束时间是数据库的最大时间
	 */
	@ApiOperation(value = "接口2:根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。", notes = "据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "beginDate", value = "开始时间(格式20180909121212)", dataType = "string", required = true),
			@ApiImplicitParam(name = "minute", value = "时间颗粒", dataType = "int", required = true),
			@ApiImplicitParam(name = "regionStr", value = "场馆编号(多个以逗号分隔)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryPeopleNumByTimeRange", method = RequestMethod.GET)
	public Map<String, Object> queryPeopleNumByTimeRange(
			@RequestParam(value = "beginDate", required = true) String beginDate,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "regionStr", required = true) String regionStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("item", new ArrayList<>());
		try {
			if (StringUtils.isBlank(endDate)) {
				endDate = gridDataService.queryMaxDate();
			}
			List<String> listDates = MyUtil.getDateStrList(beginDate, endDate, minute);

			if (listDates.size() > 0) {
				Double numPercent = 0.0;
				if (BootConstant.People_Num_Percent > 0) {
					numPercent = BootConstant.People_Num_Percent;
				} else {
					numPercent = null;
				}
				List<Map<String, Object>> listMaps = gridDataService.queryGridNumBetData(regionStr.split(","),
						beginDate, endDate, numPercent);
				if (listMaps != null && listMaps.size() > 0) {
					// ZGG_B1,ZGG_1F,ZGG_2F
					Map<String, Integer> zgb1Map = new HashMap<>();
					Map<String, Integer> zg1fMap = new HashMap<>();
					Map<String, Integer> zg2fMap = new HashMap<>();// 将相同场馆号的数据 化为一个map

					for (Map<String, Object> modelMap : listMaps) {
						String region = modelMap.get("region").toString();
						String date = modelMap.get("date").toString();
						Integer userCount = Integer.valueOf(modelMap.get("userCount").toString());
						if (("ZGG_B1").equals(region)) {
							zgb1Map.put(date, userCount);
						}
						if (("ZGG_1F").equals(region)) {
							zg1fMap.put(date, userCount);
						}
						if (("ZGG_2F").equals(region)) {
							zg2fMap.put(date, userCount);
						}
					}

					List<Integer> zbOne = new ArrayList<>();
					List<Integer> zfOne = new ArrayList<>();
					List<Integer> zfTwo = new ArrayList<>();
					List<Integer> indoorAll = new ArrayList<>();

					// 每个场馆处理json格式
					for (String dateKey : listDates) {
						int numAll = 0;
						if (!zgb1Map.containsKey(dateKey)) {
							zbOne.add(0);
						} else {
							zbOne.add(zgb1Map.get(dateKey));
							numAll += zgb1Map.get(dateKey);
						}

						if (!zg1fMap.containsKey(dateKey)) {
							zfOne.add(0);
						} else {
							zfOne.add(zg1fMap.get(dateKey));
							numAll += zg1fMap.get(dateKey);
						}

						if (!zg2fMap.containsKey(dateKey)) {
							zfTwo.add(0);
						} else {
							zfTwo.add(zg2fMap.get(dateKey));
							numAll += zg2fMap.get(dateKey);
						}
						indoorAll.add(numAll);
					}

					listMaps.clear();
					Map<String, Object> jsonMap1 = new HashMap<>();
					jsonMap1.put("name", "Indoor");
					jsonMap1.put("item", indoorAll);
					listMaps.add(jsonMap1);

					Map<String, Object> jsonMap2 = new HashMap<>();
					jsonMap2.put("name", "ZGG_B1");
					jsonMap2.put("item", zbOne);
					listMaps.add(jsonMap2);

					Map<String, Object> jsonMap3 = new HashMap<>();
					jsonMap3.put("name", "ZGG_1F");
					jsonMap3.put("item", zfOne);
					listMaps.add(jsonMap3);

					Map<String, Object> jsonMap4 = new HashMap<>();
					jsonMap4.put("name", "ZGG_2F");
					jsonMap4.put("item", zfTwo);
					listMaps.add(jsonMap4);
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
		List<Integer> listNew = new ArrayList<Integer>();

		for (int i = 0; i < 16; i++) {
			Map<String, Object> map = listMaps.get(i);
			List<Integer> listM = (List<Integer>) map.get("userCount");
			if (i == 0) {
				listNew = listM;
			} else {
				List<Integer> list = new ArrayList<>();
				for (int j = 0; j < listNew.size(); j++) {
					list.add(j, listNew.get(j) + listM.get(j));
				}
				listNew = list;
			}
		}
		mapOne.put("item", listNew);
		listMaps.add(0, mapOne);
	}

	/**
	 * 接口8 获取当前所有场馆的告警信息。 根据传入的告警数量 获取所有的栅格数据 最大时间的
	 */
	@ApiOperation(value = "接口8:获取当前所有场馆的告警信息", notes = "根据传入的告警数量(3个, 获取对应栅格最大时间的数据")
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
				if (warnNums.length != 3) {
					map.put("status", 2);
					map.put("msg", "请传入3个场馆对应的告警数量");
				} else {
					String maxDate = gridDataService.queryMaxDate();
					if (StringUtils.isBlank(maxDate)) {
						map.put("status", 2);
						map.put("msg", "数据库中日期不存在");
					} else {
						List<Map<String, Object>> listDatas = gridDataService.queryGridWarnData(warnNum, maxDate);
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
	 * 接口1 最新 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数 时间就是数据库的最大时间 ----- maxDate 是数据的最大时间
	 * reqDate 最大时间可传入可不传入 默认是最大时间
	 */
	@ApiOperation(value = "接口1:获取所有场馆的各自在馆人数和所有场馆总人数", notes = "所有场馆最后时间的各自在馆人数和所有场馆总人数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "reqDate", value = "时间(非必填，默认最大时间)", dataType = "string", required = true) })
	@RequestMapping(value = "/queryGridPeopleNumData", method = RequestMethod.GET)
	public Map<String, Object> queryGridPeopleNumData(
			@RequestParam(value = "reqDate", required = true) String reqDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("time", "");
		map.put("peopleParameterList", "");
		try {
			if (StringUtils.isBlank(reqDate)) {
				map.put("status", 2);
				map.put("msg", "未传入日期参数：reqDate");
			}
			if (("00000000000000").equals(reqDate)) {
				reqDate = gridDataService.queryMaxDate();
			}
			List<Integer> peopleParameterList = new ArrayList<Integer>();
			List<Map<String, Object>> gridMap = gridDataService
					.queryGridPeopleNumquick(numListNew.toArray(new String[numListNew.size()]), reqDate);
			List<String> list = AppController.numListNew;
			if (gridMap != null && gridMap.size() > 0) {
				Map<String, Object> map3 = gridMap.get(0);
				reqDate = map3.get("times").toString();

				boolean flag = true;
				for (String region : list) {
					for (Map<String, Object> map2 : gridMap) {
						String key = map2.get("region").toString();
						if (region.equals(key)) {
							Integer num = Integer.valueOf(map2.get("num").toString());
							peopleParameterList.add(num);
							flag = false;
							break;
						}
					}
					if (flag) {
						peopleParameterList.add(0);
						flag = false;
					}
				}

				Integer countAll = 0;
				for (Integer eveNum : peopleParameterList) {
					countAll += eveNum;
				}
				peopleParameterList.add(0, countAll);
			} else {// 全部0
				for (String region : list) {
					peopleParameterList.add(0);
				}
				peopleParameterList.add(0);
			}
			map.put("peopleParameterList", peopleParameterList);
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
			List<String> listDates = MyUtil.getDateStrList(beginDate, endDate, 1);
			List<Map<String, Object>> list = regionService.queryDateForMinuteNew(beginDate, endDate, region);
			List<Map<String, Object>> finalDatas = new ArrayList<>();
			if (list != null && list.size() > 0) {
				if (listDates.size() == list.size()) {
					map.put("peopleHistoryParameterList", list);
				} else {
					for (String dateKey : listDates) {
						boolean flag = true;
						for (Map<String, Object> dataMap : list) {
							String date = dataMap.get("date").toString();
							if (dateKey.equals(date)) {
								finalDatas.add(dataMap);
								flag = false;
								break;
							}
						}
						if (flag) {
							Map<String, Object> zeroMap = new HashMap<>();
							zeroMap.put("date", dateKey);
							zeroMap.put("total", 0);
							finalDatas.add(zeroMap);
						}
					}
					map.put("peopleHistoryParameterList", finalDatas);
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
	 * 17最新、接口17 根据指定时间范围和场馆编号获取指定场馆的人数数据。
	 */
	@RequestMapping(value = "/queryDateForMinuteAll", method = RequestMethod.GET)
	public Map<String, Object> queryDateForMinuteAll(
			@RequestParam(value = "beginDate", required = true) String beginDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("peopleHistoryParameterList", new ArrayList<>());
		try {

			if (("Indoor").equals(region)) {
				List<String> listDates = MyUtil.getDateStrList(beginDate, endDate, 1);
				List<Map<String, Object>> list = regionService.queryDateForMinuteIndoor(beginDate, endDate);
				List<Map<String, Object>> finalDatas = new ArrayList<>();
				if (list != null && list.size() > 0) {
					if (listDates.size() == list.size()) {
						map.put("peopleHistoryParameterList", list);
					} else {
						for (String dateKey : listDates) {
							boolean flag = true;
							for (Map<String, Object> dataMap : list) {
								String date = dataMap.get("date").toString();
								if (dateKey.equals(date)) {
									finalDatas.add(dataMap);
									flag = false;
									break;
								}
							}
							if (flag) {
								Map<String, Object> zeroMap = new HashMap<>();
								zeroMap.put("date", dateKey);
								zeroMap.put("total", 0);
								finalDatas.add(zeroMap);
							}
						}
						map.put("peopleHistoryParameterList", finalDatas);
					}
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			} else {
				map.put("status", 2);
				map.put("msg", "场馆编号只能为Indoor");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyyMMddHHmmss");
		}
		return map;
	}

	/**
	 * 17、接口17根据指定时间范围和场馆编号获取指定场馆的人数数据。 废弃
	 */
	@RequestMapping(value = "/queryDateForMinuteAllOld", method = RequestMethod.GET)
	public Map<String, Object> queryDateForMinuteAllOld(
			@RequestParam(value = "beginDate", required = true) String beginDate,
			@RequestParam(value = "endDate", required = true) String endDate,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("peopleHistoryParameterList", new ArrayList<>());
		try {
			if (("Indoor").equals(region)) {
				List<Map<String, Object>> list = regionService.queryDateForMinuteAll(beginDate, endDate, region);
				if (list.size() > 0) {
					map.put("peopleHistoryParameterList", list);
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			} else {
				map.put("status", 2);
				map.put("msg", "场馆编号只能为Indoor");
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
	 * 
	 * @Description: 接口16 缓存使用
	 * @author weichengz
	 * @date 2018年10月3日 下午6:55:04
	 */
	public Map<String, Object> getHiMapAll(String region, Double numPercent, String dateNow) {
		return gridDataService.getHiMapAll(region, numPercent, dateNow);
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

	/**
	 * 
	 * 设置管外的所有暗门
	 * 
	 */
	@RequestMapping(value = "/querySwitchInfo", method = RequestMethod.GET)
	public Map<String, Object> querySwitchInf() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("count", 0);
		try {
			String maxDate = regionService.queryMaxDate();
			Long num = regionService.querySwitchInfo(maxDate);
			if (num != null && num > 0) {
				map.put("count", num);
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  ");
		}
		return map;
	}

	/**
	 * 
	 * 实测查询数据库的所有时间
	 * 
	 */
	@RequestMapping(value = "/testQueryDbTime", method = RequestMethod.GET)
	public List<String> testQueryDbTime() {
		return gridDataService.testQueryDbTime();
	}
}
