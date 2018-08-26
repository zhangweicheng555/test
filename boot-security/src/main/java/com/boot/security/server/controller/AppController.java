package com.boot.security.server.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.model.GridData;
import com.boot.security.server.model.GridMapper;
import com.boot.security.server.model.HiGridDataHour;
import com.boot.security.server.model.ImsiTrackData;
import com.boot.security.server.model.TestGridData;
import com.boot.security.server.model.TraceModel;
import com.boot.security.server.service.GridDataService;
import com.boot.security.server.service.GridMapperService;
import com.boot.security.server.service.HiGridDataHourService;
import com.boot.security.server.service.ImsiTrackDataService;
import com.boot.security.server.service.TestGridDataService;
import com.boot.security.server.util.JsonMsgUtil;
import com.boot.security.server.util.MyUtil;
import com.google.gson.Gson;
import com.opencsv.CSVReader;

/**
 * app对接相关接口
 */

@RestController
@RequestMapping("/app")
public class AppController {

	// 初始化场馆编号
	public final static List<String> numList = new ArrayList<String>(Arrays.asList("All", "1", "2", "3", "4.1", "4.2",
			"5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2", "NH", "EH", "WH"));

	@Autowired
	private GridDataService gridDataService;
	@Autowired
	private HiGridDataHourService hiGridDataHourService;
	@Autowired
	private GridMapperService gridMapperService;
	@Autowired
	private TestGridDataService testGridDataService;
	@Autowired
	private ImsiTrackDataService imsiTrackDataService;

	/**
	 * 五、接口5 根据指定时间范围和场馆编号获取指定场馆的栅格数据。 这个就是返回 指定场馆 某个日期的所有数据 有日期范围 切割
	 */
	@RequestMapping(value = "/queryGridDataByTimeRegion")
	public Map<String, Object> queryGridDataByTimeRegion(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute,
			@RequestParam(value = "region", required = true) String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridHistoryParameterList", new ArrayList<>());
		try {
			List<Date> listDates = MyUtil.getDateList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> listMaps = new ArrayList<>();
				for (Date date : listDates) {
					Map<String, Object> map2 = gridDataService.queryGridDataByTimeRegion(date, region);
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
	 * 接口8 获取当前所有场馆的告警信息。 根据传入的告警数量 获取所有的栅格数据 最大时间的
	 */
	@RequestMapping(value = "/queryGridWarnData")
	public Map<String, Object> queryGridWarnData(@RequestParam(value = "warnNum", required = true) int warnNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("warningParameter", new ArrayList<>());
		try {
			String maxDate = gridDataService.queryMaxDate();
			List<Map<String, Object>> listDatas = gridDataService.queryGridWarnData(warnNum, maxDate);
			if (listDatas.size() > 0) {
				map.put("warningParameter", listDatas);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因: " + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 接口6 获取所有场馆的的年龄段、来源地、男女比例接口。
	 */
	@RequestMapping(value = "/queryHiGridDataHourLatest")
	public Map<String, Object> queryHiGridDataHourLatest(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			// 先判断数量是不是存在最新的数据
			long num = hiGridDataHourService.queryCount(session);
			if (num > 0) {
				String maxDate=hiGridDataHourService.queryMaxDate();
				List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
				for (int j = 1; j < numList.size(); j++) {
					String key = numList.get(j);// region
					HiGridDataHour hiGridDataHour = hiGridDataHourService.queryHiGridDataHourLatest(key,maxDate);
					listMaps.add(hiGridDataHourToMap(hiGridDataHour));
				}
				listMaps.add(0, dealAllMap(listMaps));
				map.put("miscParameter", listMaps);
			} else {
				map.put("status", 3);
				map.put("msg", "数据未更新");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyy-MM-dd HH:mm");
		}
		return map;
	}

	private Map<String, Object> dealAllMap(List<Map<String, Object>> listMaps) {
		Long male = 0l;
		Long female = 0l;
		Long age1 = 0l;
		Long age2 = 0l;
		Long age3 = 0l;
		Long age4 = 0l;
		Long age5 = 0l;
		Long source1 = 0l;
		Long source2 = 0l;

		for (Map<String, Object> map : listMaps) {
			male += (long) map.get("male");
			female += (long) map.get("female");
			age1 += (long) map.get("age1");
			age2 += (long) map.get("age2");
			age3 += (long) map.get("age3");
			age4 += (long) map.get("age4");
			age5 += (long) map.get("age5");
			source1 += (long) map.get("source1");
			source2 += (long) map.get("source2");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("male", male);
		map.put("female", female);
		map.put("age1", age1);
		map.put("age2", age2);
		map.put("age3", age3);
		map.put("age4", age4);
		map.put("age5", age5);
		map.put("source1", source1);
		map.put("source2", source2);
		return map;
	}

	private Map<String, Object> hiGridDataHourToMap(HiGridDataHour hiGridDataHour) {
		Map<String, Object> map = new HashMap<>();
		if (hiGridDataHour != null) {
			map.put("time", hiGridDataHour.getSdate());
			map.put("male", hiGridDataHour.getImsiMale());
			map.put("female", hiGridDataHour.getImsiFeMale());
			map.put("age1", hiGridDataHour.getImsiAge1());
			map.put("age2", hiGridDataHour.getImsiAge2());
			map.put("age3", hiGridDataHour.getImsiAge3());
			map.put("age4", hiGridDataHour.getImsiAge4());
			map.put("age5", hiGridDataHour.getImsiAge5());
			map.put("source1", hiGridDataHour.getImsiSource1());
			map.put("source2", hiGridDataHour.getImsiSource2());
			return map;
		} else {
			map.put("time", null);
			map.put("male", 0l);
			map.put("female", 0l);
			map.put("age1", 0l);
			map.put("age2", 0l);
			map.put("age3", 0l);
			map.put("age4", 0l);
			map.put("age5", 0l);
			map.put("source1", 0l);
			map.put("source2", 0l);
		}
		return map;
	}

	/**
	 * 接口1 最新 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。 
	 * 各个场馆最新的总人数 这里面有个问题 就是场馆的时间可能不一致
	 * 时间就是数据库的最大时间   -----
	 * maxDate 是数据的最大时间    reqDate 没用
	 */
	@RequestMapping(value = "/queryGridPeopleNumData")
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
//				maxDate = checkReqDate(reqDate);
				maxDate = reqDate;
				if (StringUtils.isNoneBlank(maxDate)) {
					List<Integer> peopleParameterList = new ArrayList<Integer>();
					for (int j = 1; j < numList.size(); j++) {
						String key = numList.get(j);// region
						peopleParameterList.add(gridDataService.queryGridPeopleNumDataNew(key, maxDate));
					}
					int countAll = 0;
					for (Integer num : peopleParameterList) {
						countAll += num;
					}
					peopleParameterList.add(0, countAll);
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
	 * 校验日期
	 */
	private String checkReqDate(String reqDate) {
		String flag = null;
		if (StringUtils.isNoneBlank(reqDate)) {
			try {
				if (reqDate.trim().length() == 14) {
					String begin = reqDate.substring(0, 11);// 20180824234
					String middle = reqDate.substring(11, 12);// 6
					int middleInt = Integer.valueOf(middle);
					if (middleInt < 5) {
						flag = begin + "0" + "00";
					} else if (middleInt >= 5) {
						flag = begin + "5" + "00";
					} else {
						flag = null;
					}
				}
			} catch (Exception e) {
				flag = null;
			}
		}
		return flag;
	}

	/**
	 * 最新   废弃 不用了  这个
	 * 接口2 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。 查询历史表
	 * http://localhost:8989/app/queryPeopleNumByTimeRange?beginDateStr=2019-7-26
	 * 11:55&endDateStr=2019-7-26 12:30&minute=5 这个就是开始时间 结束结束范围 然后跟几分钟切割
	 * 计算每个场馆各个时刻的值
	 * 
	 * 这个返回本馆当天的
	 */

	@RequestMapping(value = "/queryPeopleNumByTimeRange")
	public Map<String, Object> queryPeopleNumByTimeRange(
			@RequestParam(value = "beginDate", required = true) String beginDate,
			@RequestParam(value = "minute", required = true) int minute) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("time", "");
		map.put("item", new ArrayList<>());
		try {
			String maxDate = gridDataService.queryMaxDate();
			List<Date> listDates = MyUtil.getDateListN(beginDate, maxDate, minute);
			if (listDates.size() > 0) {
				List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
				for (String key : numList) {
					Map<String, Object> map2 = new HashMap<>();
					map2.put("name", key);
					List<Integer> lInteger = new ArrayList<>();
					for (Date date : listDates) {
						Integer integerNum = hiGridDataHourService.queryPeopleNumByTimeRange(date, key);
						if (integerNum != null && integerNum > 0) {
							lInteger.add(integerNum);
						} else {
							lInteger.add(0);
						}
					}
					map2.put("item", lInteger);
					myList.add(map2);
				}
				map.put("item", myList);
				map.put("time", maxDate);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyy-MM-dd HH:mm");
		}
		return map;
	}


	/**
	 * 用户散点图 接口 七、8 获取指定用户的散点图。 返回各个用户在各个时间点的 数量 返回xy
	 */
	@RequestMapping(value = "/userScatterPoint")
	public Map<String, Object> userScatterPoint(@RequestParam("data") String data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("scatterParameter", new ArrayList<>());
		try {
			Gson gson = new Gson();
			TraceModel traceModel = gson.fromJson(data, TraceModel.class);
			if (traceModel == null || traceModel.getTels().size() <= 0 || traceModel.getTimes().size() <= 0) {
				map.put("status", 1);
				map.put("msg", "没有传入tels/times参数！");
			} else {
				List<Map<String, Object>> list = imsiTrackDataService.userScatterPoint(traceModel);
				if (list.size() > 0) {
					map.put("scatterParameter", list);
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 传入场馆编号，返回栅格集合 对接接口 接口4 获取指定场馆的栅格数据。 返回各个长场馆 最新的数据 x y 并且 用户大于0
	 */
	@RequestMapping(value = "/queryGridDataByRegion")
	public Map<String, Object> queryGridDataByRegion(@RequestParam("region") String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("time", "");
		map.put("gridParameterList", new ArrayList<>());
		String maxDate = gridDataService.queryMaxDate();
		try {
			if (StringUtils.isNoneBlank(region)) {
				List<Map<String, Object>> list = gridDataService.queryGridDataByRegion(region, maxDate);
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
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		map.put("time", maxDate);
		return map;
	}

	/**
	 * 用户轨迹热力图 对接接口 接口3 获取指定用户的轨迹数据。 返回用户指定范围的数据 格式是x y类型的
	 */
	@RequestMapping(value = "/queryImsiTrackDataByParam")
	public Map<String, Object> queryImsiTrackDataByParam(@RequestParam("imsi") String imsi,
			@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("coordParameterList", new ArrayList<>());
		try {
			if (imsi != null && StringUtils.isNoneBlank(beginDate) && StringUtils.isNoneBlank(endDate)) {
				List<Map<String, Object>> list = imsiTrackDataService.queryDataByParam(imsi, beginDate, endDate);
				if (list.size() > 0) {
					map.put("coordParameterList", list);
				} else {
					map.put("status", 1);
					map.put("msg", "没有对应条件的数据！");
				}
			} else {
				map.put("status", 2);
				map.put("msg", "务必传入请求参数：imsi,beginDate,endDate");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 上传栅格数据 测试数据新的
	 */
	@RequestMapping(value = "/upTestNewGridData")
	public JsonMsgUtil upTestNewGridData() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入text文件成功", "");
		InputStreamReader read = null;
		try {

			String encoding = "GBK";
			File file = ResourceUtils.getFile("classpath:static/GridentityTest.txt");
			if (file.isFile() && file.exists()) {
				read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] attayStr = lineTxt.trim().split(",");
					TestGridData testGridData = new TestGridData(attayStr[10], attayStr[0], attayStr[1], attayStr[2],
							attayStr[3], attayStr[4], attayStr[5], attayStr[6], attayStr[7],
							Double.valueOf(attayStr[8]), Double.valueOf(attayStr[9]));
					testGridDataService.save(testGridData);
				}
				read.close();
			}
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
			if (read != null) {
				try {
					read.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return j;
	}

	public String cellToString(Cell cell) {
		if (cell == null) {
			return "";
		}
		// cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}

	@RequestMapping(value = "/MyRecorder")
	public void MyRecorder() {
		List<String> list = gridMapperService.findIdList();
		int x = 0;
		int y = 0;
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i);
			x = i % 230;
			if (i > 0 && x == 0) {
				y++;
			}
			changeData(x, y, id);
		}
	}

	private void changeData(int x, int y, String id) {
		gridMapperService.changeData(x, y, id);
	}

	@RequestMapping(value = "/upLoadUserTrackXml")
	public JsonMsgUtil upLoadUserTrackXml(@RequestParam("path") String path) {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入excel文件成功", "");
		List<ImsiTrackData> listGridMappers = new ArrayList<ImsiTrackData>();

		try {
			InputStream in = new FileInputStream(ResourceUtils.getFile("classpath:static/" + path + ".xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(in);
			// 读取第一个sheet
			Sheet sheet = wb.getSheetAt(0);
			int dom = 0;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// 获取第二行
				Row row = sheet.getRow(i);
				Date d = row.getCell(0).getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = formater.format(d);
				if (StringUtils.isBlank(date)) {
					break;
				}
				String region = new java.text.DecimalFormat("#.0")
						.format(Double.valueOf(cellToString(row.getCell(1)).trim()));
				String regionType = cellToString(row.getCell(2));
				double cellId = Double.valueOf(cellToString(row.getCell(3)));
				String num = cellToString(row.getCell(4));
				double imsiimsi = Double.valueOf(num);
				double longitude = Double.valueOf(cellToString(row.getCell(5)));
				double latitude = Double.valueOf(cellToString(row.getCell(6)));
				double gridx = Double.valueOf(cellToString(row.getCell(7)));
				double gridy = Double.valueOf(cellToString(row.getCell(8)));
				double confidence = 0;
				String remark = "";

				ImsiTrackData imsiTrackData = new ImsiTrackData(date, imsiimsi, cellId, regionType, region, gridx,
						gridy, longitude, latitude, confidence, remark);
				listGridMappers.add(imsiTrackData);
				dom++;
			}
			if (listGridMappers.size() > 0) {
				for (ImsiTrackData gridMapper : listGridMappers) {
					imsiTrackDataService.save(gridMapper);
				}
			} else {
				j = new JsonMsgUtil(true, "不存在上传的数据", "");
			}
			wb.close();
			in.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
		}
		return j;
	}

	public String idea(String t) {
		String s = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.YEAR, 1900);
		cal1.set(Calendar.DAY_OF_MONTH, 1);
		cal1.set(Calendar.DAY_OF_YEAR, 1);
		cal1.set(Calendar.HOUR_OF_DAY, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		if (t == null || ("").equals(t)) {
			// cal1.add(Calendar.DAY_OF_YEAR,43549-2);
		} else {
			cal1.add(Calendar.DAY_OF_YEAR, Integer.parseInt(t) - 2);
			Date date = cal1.getTime();
			s = sdf.format(date);
		}
		return s;

	}

	/**
	 * 上传用户轨迹csv文件 自己用
	 */
	@RequestMapping(value = "/upLoadUserTrack")
	public JsonMsgUtil upLoadUserTrack() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入csv文件成功", "");
		CSVReader reader = null;
		try {
			File csv = ResourceUtils.getFile("classpath:static/userTrackM.csv");
			reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"), ',');
			reader.readNext(); // 读标题了
			List<String[]> list = reader.readAll(); // 此时读取的已经是第二行了
			List<Date> list2 = MyUtil.getMyTestDate();
			for (int i = 0; i < list.size(); i++) {
				String[] strArray = list.get(i);
				ImsiTrackData imsiTrackData = new ImsiTrackData();
				imsiTrackData.setSdate(strArray[4].trim());
				imsiTrackData.setImsi(Long.valueOf(strArray[0].trim()));
				imsiTrackData.setCellId(Long.valueOf(strArray[1].trim()));
				imsiTrackData.setGridx(Long.valueOf(strArray[2].trim()));
				imsiTrackData.setGridy(Long.valueOf(strArray[3].trim()));
				imsiTrackData.setRegionType(strArray[5].trim());
				imsiTrackData.setRegion(strArray[6].trim());
				imsiTrackData.setLongitude(Double.valueOf(strArray[7].trim()));
				imsiTrackData.setLatitude(Double.valueOf(strArray[8].trim()));
				imsiTrackData.setConfidence(Long.valueOf(strArray[9].trim()));
				imsiTrackDataService.save(imsiTrackData);
			}
			reader.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 上传测试网格csv文件 自己用
	 */
	@RequestMapping(value = "/upLoadGridData")
	public JsonMsgUtil upLoadGridData() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入csv文件成功", "");
		CSVReader reader = null;
		try {
			File csv = ResourceUtils.getFile("classpath:static/GridSamplnew.csv");
			System.out.println(csv.exists());
			reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"), ',');
			reader.readNext(); // 读标题了
			List<String[]> list = reader.readAll(); // 此时读取的已经是第二行了
			for (Iterator<String[]> iterator = list.iterator(); iterator.hasNext();) {
				String[] strArray = (String[]) iterator.next();
				if (strArray != null && strArray.length > 0) {
					GridData gridData = new GridData();
					gridData.setSdate(strArray[0]);
					gridData.setRegiontype(strArray[1]);
					gridData.setRegion(strArray[2]);
					gridData.setGridx(Double.valueOf(strArray[3]));
					gridData.setGridy(Double.valueOf(strArray[4]));
					gridData.setLongitude(Double.valueOf(strArray[5]));
					gridData.setLatitude(Double.valueOf(strArray[6]));
					gridData.setImsi_num(Double.valueOf(strArray[7]));
					gridData.setRatio(Double.valueOf(strArray[8]));
					gridData.setTotalusers(Double.valueOf(strArray[9]));
					gridDataService.save(gridData);
				}
			}
			reader.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 上传GridMapper csv文件 自己用
	 */
	@RequestMapping(value = "/upLoadGridMapperCsv")
	public JsonMsgUtil upLoadGridMapperCsv(@RequestParam("path") String path) {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入csv文件成功", "");
		CSVReader reader = null;
		List<GridMapper> listGridMappers = new ArrayList<GridMapper>();
		try {
			File csv = ResourceUtils.getFile("classpath:static/zll.csv");
			reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"), ',');
			List<String[]> list = reader.readAll();
			int indexY = 265;
			for (int i = 1; i < list.size(); i++) {
				String[] strArray = list.get(i);
				String entityHand = strArray[2];
				if (StringUtils.isBlank(entityHand)) {
					break;
				}
				double gridx = Double.valueOf(strArray[3]);
				double gridy = Double.valueOf(strArray[4]);
				String id = strArray[1];
				int index = Integer.valueOf(strArray[0]);
				int indexX = index % 230;
				if (index > 0 && indexX == 0) {
					indexY++;
				}
				GridMapper gridMapper = new GridMapper(id, entityHand, gridx, gridy, indexX, indexY);
				listGridMappers.add(gridMapper);
			}
			if (listGridMappers.size() > 0) {
				for (GridMapper gridMapper : listGridMappers) {
					gridMapperService.save(gridMapper);
				}
			} else {
				j = new JsonMsgUtil(true, "不存在上传的数据", "");
			}
			j.setObject(list.size());
			reader.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 上传映射实体类xlsx文件 自己用
	 */
	@RequestMapping(value = "/upLoadGridMapper")
	public JsonMsgUtil upLoadGridMapper() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入excel文件成功", "");
		List<GridMapper> listGridMappers = new ArrayList<GridMapper>();

		try {
			InputStream in = new FileInputStream(ResourceUtils.getFile("classpath:static/zll.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(in);
			// 读取第一个sheet
			Sheet sheet = wb.getSheetAt(0);
			int indexX = 0;
			int indexY = 0;
			int dom = 0;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// 获取第二行
				Row row = sheet.getRow(i);
				String entityHand = cellToString(row.getCell(0));
				if (StringUtils.isBlank(entityHand)) {
					break;
				}
				double gridx = Double.valueOf(cellToString(row.getCell(1)));
				double gridy = Double.valueOf(cellToString(row.getCell(2)));
				String id = cellToString(row.getCell(3));
				indexX = dom % 230;
				if (dom > 0 && indexX == 0) {
					indexY++;
				}
				GridMapper gridMapper = new GridMapper(id, entityHand, gridx, gridy, indexX, indexY);
				listGridMappers.add(gridMapper);
				dom++;
			}
			if (listGridMappers.size() > 0) {
				for (GridMapper gridMapper : listGridMappers) {
					gridMapperService.save(gridMapper);
				}
			} else {
				j = new JsonMsgUtil(true, "不存在上传的数据", "");
			}
			wb.close();
			in.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
		}
		return j;
	}

	/**
	 * 上传映射实体类xlsx文件 自己用
	 */
	@RequestMapping(value = "/upLoadGridMapperN")
	public JsonMsgUtil upLoadGridMapperN() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入excel文件成功", "");
		List<GridMapper> listGridMappers = new ArrayList<GridMapper>();

		try {
			InputStream in = this.getClass().getResourceAsStream("/static/zll.xlsx"); // 对应resources下的文件
			XSSFWorkbook wb = new XSSFWorkbook(in);
			// 读取第一个sheet
			Sheet sheet = wb.getSheetAt(0);
			int indexX = 0;
			int indexY = 0;
			int dom = 0;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				// 获取第二行
				Row row = sheet.getRow(i);
				String entityHand = cellToString(row.getCell(0));
				if (StringUtils.isBlank(entityHand)) {
					break;
				}
				double gridx = Double.valueOf(cellToString(row.getCell(1)));
				double gridy = Double.valueOf(cellToString(row.getCell(2)));
				String id = cellToString(row.getCell(3));
				indexX = dom % 230;
				if (dom > 0 && indexX == 0) {
					indexY++;
				}
				GridMapper gridMapper = new GridMapper(id, entityHand, gridx, gridy, indexX, indexY);
				listGridMappers.add(gridMapper);
				dom++;
			}
			if (listGridMappers.size() > 0) {
				for (GridMapper gridMapper : listGridMappers) {
					gridMapperService.save(gridMapper);
				}
			} else {
				j = new JsonMsgUtil(true, "不存在上传的数据", "");
			}
			wb.close();
			in.close();
		} catch (Exception e) {
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
		}
		return j;
	}

	/**
	 * 上传栅格实体类xlsx文件 自己用
	 */
	@RequestMapping(value = "/upLoadGridDataModel")
	public JsonMsgUtil upLoadGridDataModel() {
		JsonMsgUtil j = new JsonMsgUtil(true, "导入excel文件成功", "");
		List<GridData> listGridMappers = new ArrayList<GridData>();

		try {
			InputStream in = new FileInputStream(ResourceUtils.getFile("classpath:static/gridDataNewMore.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(in);
			// 读取第一个sheet
			Sheet sheet = wb.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				GridData gridData = new GridData();
				// 获取第二行
				Row row = sheet.getRow(i);
				String regiontype = cellToString(row.getCell(1));
				gridData.setRegiontype(regiontype);
				gridData.setSdate("2018-07-26 11:00");
				// gridData.setSdate(MyUtil.stringToDate(cellToString(row.getCell(0))));
				if (StringUtils.isBlank(regiontype)) {
					break;
				}
				gridData.setRegion(
						new java.text.DecimalFormat("#.0").format(Double.valueOf(cellToString(row.getCell(2)).trim())));
				gridData.setGridx(Double.valueOf(cellToString(row.getCell(3))));
				gridData.setGridy(Double.valueOf(cellToString(row.getCell(4))));
				gridData.setLongitude(Double.valueOf(cellToString(row.getCell(6))));
				gridData.setLatitude(Double.valueOf(cellToString(row.getCell(5))));
				gridData.setImsi_num(Double.valueOf(cellToString(row.getCell(7))));
				gridData.setRatio(Double.valueOf(cellToString(row.getCell(8))));
				gridData.setTotalusers(Double.valueOf(cellToString(row.getCell(9))));

				listGridMappers.add(gridData);
			}
			if (listGridMappers.size() > 0) {
				for (GridData gridMapper : listGridMappers) {
					gridDataService.save(gridMapper);
				}
			} else {
				j = new JsonMsgUtil(true, "不存在上传的数据", "");
			}
			wb.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			j = new JsonMsgUtil(false, "由于系统原因操作失败", e.getMessage());
		}
		return j;
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

}
