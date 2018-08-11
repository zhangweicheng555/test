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
			"5.1", "5.2", "6.1", "6.2", "7.1", "7.2", "8.1", "8.2", "F1", "F2", "F3"));

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
	 * 五、接口5 根据指定时间范围和场馆编号获取指定场馆的栅格数据。
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
			map.put("msg", "系统异常查询以下原因:1." + e.getLocalizedMessage() + "  " + "2.传入的日期格式要求为：yyyy-MM-dd HH:mm");
		}
		return map;
	}

	/**
	 * 接口8 获取当前所有场馆的告警信息。
	 */
	@RequestMapping(value = "/queryGridWarnData")
	public Map<String, Object> queryGridWarnData(@RequestParam(value = "warnNum", required = true) int warnNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("warningParameter", new ArrayList<>());
		try {
			List<Map<String, Object>> listDatas = gridDataService.queryGridWarnData(warnNum);
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
				List<Map<String, Long>> listMaps = new ArrayList<Map<String, Long>>();
				for (int j = 1; j < numList.size(); j++) {
					String key = numList.get(j);// region
					HiGridDataHour hiGridDataHour = hiGridDataHourService.queryHiGridDataHourLatest(key);
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

	private Map<String, Long> dealAllMap(List<Map<String, Long>> listMaps) {
		Long male = 0l;
		Long female = 0l;
		Long age1 = 0l;
		Long age2 = 0l;
		Long age3 = 0l;
		Long age4 = 0l;
		Long age5 = 0l;
		Long age6 = 0l;
		Long age7 = 0l;
		Long age8 = 0l;
		Long source1 = 0l;
		Long source2 = 0l;

		for (Map<String, Long> map : listMaps) {
			male += map.get("male");
			female += map.get("female");
			age1 += map.get("age1");
			age2 += map.get("age2");
			age3 += map.get("age3");
			age4 += map.get("age4");
			age5 += map.get("age5");
			age6 += map.get("age6");
			age7 += map.get("age7");
			age8 += map.get("age8");
			source1 += map.get("source1");
			source2 += map.get("source2");
		}
		Map<String, Long> map = new HashMap<>();
		map.put("male", male);
		map.put("female", female);
		map.put("age1", age1);
		map.put("age2", age2);
		map.put("age3", age3);
		map.put("age4", age4);
		map.put("age5", age5);
		map.put("age6", age6);
		map.put("age7", age7);
		map.put("age8", age8);
		map.put("source1", source1);
		map.put("source2", source2);
		return map;
	}

	private Map<String, Long> hiGridDataHourToMap(HiGridDataHour hiGridDataHour) {
		Map<String, Long> map = new HashMap<>();
		if (hiGridDataHour != null) {
			map.put("male", hiGridDataHour.getImsiMale());
			map.put("female", hiGridDataHour.getImsiFeMale());
			map.put("age1", hiGridDataHour.getImsiAge1());
			map.put("age2", hiGridDataHour.getImsiAge2());
			map.put("age3", hiGridDataHour.getImsiAge3());
			map.put("age4", hiGridDataHour.getImsiAge4());
			map.put("age5", hiGridDataHour.getImsiAge5());
			map.put("age6", hiGridDataHour.getImsiAge6());
			map.put("age7", hiGridDataHour.getImsiAge7());
			map.put("age8", hiGridDataHour.getImsiAge8());
			map.put("source1", hiGridDataHour.getImsiSource1());
			map.put("source2", hiGridDataHour.getImsiSource2());
			return map;
		} else {
			map.put("male", 0l);
			map.put("female", 0l);
			map.put("age1", 0l);
			map.put("age2", 0l);
			map.put("age3", 0l);
			map.put("age4", 0l);
			map.put("age5", 0l);
			map.put("age6", 0l);
			map.put("age7", 0l);
			map.put("age8", 0l);
			map.put("source1", 0l);
			map.put("source2", 0l);
		}
		return map;
	}

	/**
	 * 接口1 最新 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。
	 */
	@RequestMapping(value = "/queryGridPeopleNumData")
	public Map<String, Object> queryGridPeopleNumData() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			List<Integer> peopleParameterList = new ArrayList<Integer>();
			for (int j = 1; j < numList.size(); j++) {
				String key = numList.get(j);// region
				peopleParameterList.add(gridDataService.queryGridPeopleNumDataNew(key));
			}
			int countAll = 0;
			for (Integer num : peopleParameterList) {
				countAll += num;
			}
			peopleParameterList.add(0, countAll);
			map.put("peopleParameterList", peopleParameterList);

		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 接口2 根据指定时间范围获取所有场馆的各自在馆人数和所有场馆总人数。
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryPeopleNumByTimeRange")
	public Map<String, Object> queryPeopleNumByTimeRange(
			@RequestParam(value = "beginDateStr", required = true) String beginDateStr,
			@RequestParam(value = "endDateStr", required = true) String endDateStr,
			@RequestParam(value = "minute", required = true) int minute) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		try {
			List<Date> listDates = MyUtil.getDateList(beginDateStr, endDateStr, minute);
			if (listDates.size() > 0) {
				for (int j = 1; j < numList.size(); j++) {
					String key = numList.get(j);// region
					String itemName = "item" + (j + 1);
					List<Integer> myList = new ArrayList<Integer>();
					for (Date date : listDates) {
						myList.add(hiGridDataHourService.queryPeopleNumByTimeRange(date, key));
					}
					map.put(itemName, myList);
				}
				// 组装all
				List<Integer> all = new ArrayList<>();
				List<Integer> item2 = (List<Integer>) map.get("item2");
				List<Integer> item3 = (List<Integer>) map.get("item3");
				List<Integer> item4 = (List<Integer>) map.get("item4");
				List<Integer> item5 = (List<Integer>) map.get("item5");
				List<Integer> item6 = (List<Integer>) map.get("item6");
				List<Integer> item7 = (List<Integer>) map.get("item7");
				List<Integer> item8 = (List<Integer>) map.get("item8");
				List<Integer> item9 = (List<Integer>) map.get("item9");
				List<Integer> item10 = (List<Integer>) map.get("item10");
				List<Integer> item11 = (List<Integer>) map.get("item11");
				List<Integer> item12 = (List<Integer>) map.get("item12");
				List<Integer> item13 = (List<Integer>) map.get("item13");
				List<Integer> item14 = (List<Integer>) map.get("item14");
				List<Integer> item15 = (List<Integer>) map.get("item15");
				List<Integer> item16 = (List<Integer>) map.get("item16");
				List<Integer> item17 = (List<Integer>) map.get("item17");
				for (int i = 0; i < listDates.size(); i++) {
					all.add(item2.get(i) + item3.get(i) + item4.get(i) + item5.get(i) + item6.get(i) + item7.get(i)
							+ item8.get(i) + item9.get(i) + item10.get(i) + item11.get(i) + item12.get(i)
							+ item13.get(i) + item14.get(i) + item15.get(i) + item16.get(i) + item17.get(i));
				}
				map.put("item1", all);
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
	 * 接口1.获取所有场馆的各自在馆人数和所有场馆总人数 对接接口 这个废弃 这个是返回各管的所有数据 而不是最新管的数据
	 */
	@RequestMapping(value = "/queryGridPeopleNumDataOld")
	public Map<String, Object> queryGridPeopleNumDataOld() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("peopleParameterList", new ArrayList<>());
		try {
			List<Map<String, Object>> mapListData = gridDataService.querySingleGridData();
			if (mapListData != null && mapListData.size() > 0) {
				Long allNum = 0l;
				List<Long> list = new ArrayList<Long>();// 存储数字
				for (int i = 0; i < 17; i++) {
					list.add(0l);
				}
				for (int j = 0; j < numList.size(); j++) {
					String key = numList.get(j);
					allNum = getMapVal(mapListData, allNum, list, j, key);
				}
				list.set(0, allNum);
				map.put("peopleParameterList", list);
			} else {
				map.put("status", 1);
				map.put("msg", "没有对应条件的数据！");
			}
		} catch (Exception e) {
			map.put("status", 2);
			map.put("msg", "系统异常:" + e.getLocalizedMessage());
		}
		return map;
	}

	private Long getMapVal(List<Map<String, Object>> mapListData, Long allNum, List<Long> list, int j, String key) {
		for (int i = 0; i < mapListData.size(); i++) {
			Map<String, Object> mapEntity = mapListData.get(i);
			if (key.equals(mapEntity.get("region"))) {
				Long numM = Long.parseLong(mapEntity.get("userCount").toString());
				allNum += numM;
				list.set(j, numM);
				mapListData.remove(i);
				break;
			}
		}
		return allNum;
	}

	/**
	 * 用户散点图 接口 七、接口7 获取指定用户的散点图。
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
	 * 传入场馆编号，返回栅格集合 对接接口 接口4 获取指定场馆的栅格数据。
	 */
	@RequestMapping(value = "/queryGridDataByRegion")
	public Map<String, Object> queryGridDataByRegion(@RequestParam("region") String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 0);
		map.put("msg", "操作成功！");
		map.put("gridParameterList", new ArrayList<>());
		try {
			if (StringUtils.isNoneBlank(region)) {
				List<Map<String, Object>> list = gridDataService.queryGridDataByRegion(region);
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
		return map;
	}

	/**
	 * 用户轨迹热力图 对接接口 接口3 获取指定用户的轨迹数据。
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
