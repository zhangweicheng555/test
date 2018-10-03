package com.boot.security.server.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.security.server.common.BootConstant;
import com.boot.security.server.dao.BregionDao;
import com.boot.security.server.model.AnalysisCluster;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.BregionModel;
import com.boot.security.server.service.RegionService;
import com.boot.security.server.util.MyUtil;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private BregionDao bregionDao;

	@Override
	public String queryMaxDate() {
		return bregionDao.queryMaxDate();
	}

	@Override
	public AnalysisModel queryGridWarnData(String region, String sdate) {
		AnalysisModel analysisModel = new AnalysisModel();
		// 先初始化
		InitEntity(analysisModel);
		analysisModel.setTime(sdate);
		// 处理性别
		handleSex(analysisModel, bregionDao.analysisByGnder(region, sdate));
		// 处理年龄
		handleAge(analysisModel, bregionDao.analysisByAge(region, sdate));
		// 处理省份 境内
		handleSource(analysisModel, bregionDao.analysisBySource(region, sdate, 0l));
		// 境外
		handleSourceOut(analysisModel, bregionDao.analysisBySource(region, sdate, 1l));

		return analysisModel;
	}

	@Override
	public AnalysisModel queryGridWarnDataCluster(String region, String sdate) {
		AnalysisModel analysisModel = new AnalysisModel();
		analysisModel.setTime(sdate);
		AnalysisCluster cluster=null;
		if (region == null) {
			//获取所有的性别年龄 数量
			cluster= bregionDao.queryGridWarnDataClusterAll(region, sdate);
			if (cluster == null) {
				return analysisModel;
			}
			//获取境内境外
			
			/*Map<String, Object> querySource1 = bregionDao.querySource1(sdate);
			if (querySource1 != null) {
				analysisModel.setSource1(querySource1.get("sourceNum")+","+querySource1.get("source"));
			}
			Map<String, Object> querySource2 = bregionDao.querySource2(sdate);
			if (querySource2 != null) {
				analysisModel.setSource2(querySource2.get("sourceNum")+","+querySource1.get("source"));
			}
			Map<String, Object> querySource3 = bregionDao.querySource3(sdate);
			if (querySource3 != null) {
				analysisModel.setSource3(querySource3.get("sourceNum")+","+querySource3.get("source"));
			}
			Map<String, Object> querySource4 = bregionDao.querySource4(sdate);
			if (querySource4 != null) {
				analysisModel.setSource4(querySource4.get("sourceNum")+","+querySource4.get("source"));
			}
			Map<String, Object> querySource5 = bregionDao.querySource5(sdate);
			if (querySource5 != null) {
				analysisModel.setSource5(querySource5.get("sourceNum")+","+querySource5.get("source"));
			}
			Map<String, Object> querySource6 = bregionDao.querySource6(sdate);
			if (querySource6 != null) {
				analysisModel.setSource6(querySource6.get("sourceNum")+","+querySource6.get("source"));
			}
			Map<String, Object> querySource7 = bregionDao.querySource7(sdate);
			if (querySource7 != null) {
				analysisModel.setSource7(querySource7.get("sourceNum")+","+querySource7.get("source"));
			}
			Map<String, Object> querySource8 = bregionDao.querySource8(sdate);
			if (querySource7 != null) {
				analysisModel.setSource8(querySource8.get("sourceNum")+","+querySource8.get("source"));
			}
			Map<String, Object> querySource9 = bregionDao.querySource9(sdate);
			if (querySource9 != null) {
				analysisModel.setSource9(querySource9.get("sourceNum")+","+querySource9.get("source"));
			}
			Map<String, Object> querySource10 = bregionDao.querySource10(sdate);
			if (querySource10 != null) {
				analysisModel.setSource10(querySource10.get("sourceNum")+","+querySource10.get("source"));
			}
			
			Map<String, Object> queryGlobal1 = bregionDao.gloal1(sdate);
			if (queryGlobal1 != null) {
				analysisModel.setGloal1(queryGlobal1.get("sourceNum")+","+queryGlobal1.get("source"));
			}
			Map<String, Object> queryGlobal2 = bregionDao.gloal2(sdate);
			if (queryGlobal2 != null) {
				analysisModel.setGloal2(queryGlobal2.get("sourceNum")+","+queryGlobal2.get("source"));
			}
			Map<String, Object> queryGlobal3 = bregionDao.gloal3(sdate);
			if (queryGlobal3 != null) {
				analysisModel.setGloal3(queryGlobal3.get("sourceNum")+","+queryGlobal3.get("source"));
			}
			Map<String, Object> queryGlobal4 = bregionDao.gloal4(sdate);
			if (queryGlobal4 != null) {
				analysisModel.setGloal4(queryGlobal4.get("sourceNum")+","+queryGlobal4.get("source"));
			}
			Map<String, Object> queryGlobal5 = bregionDao.gloal5(sdate);
			if (queryGlobal5 != null) {
				analysisModel.setGloal5(queryGlobal5.get("sourceNum")+","+queryGlobal5.get("source"));
			}
			Map<String, Object> queryGlobal6 = bregionDao.gloal6(sdate);
			if (queryGlobal6 != null) {
				analysisModel.setGloal6(queryGlobal6.get("sourceNum")+","+queryGlobal6.get("source"));
			}
			Map<String, Object> queryGlobal7 = bregionDao.gloal7(sdate);
			if (queryGlobal7 != null) {
				analysisModel.setGloal7(queryGlobal7.get("sourceNum")+","+queryGlobal7.get("source"));
			}
			Map<String, Object> queryGlobal8 = bregionDao.gloal8(sdate);
			if (queryGlobal8 != null) {
				analysisModel.setGloal8(queryGlobal8.get("sourceNum")+","+queryGlobal8.get("source"));
			}
			Map<String, Object> queryGlobal9 = bregionDao.gloal9(sdate);
			if (queryGlobal9 != null) {
				analysisModel.setGloal9(queryGlobal9.get("sourceNum")+","+queryGlobal9.get("source"));
			}
			Map<String, Object> queryGlobal10 = bregionDao.gloal10(sdate);
			if (queryGlobal10 != null) {
				analysisModel.setGloal10(queryGlobal10.get("sourceNum")+","+queryGlobal10.get("source"));
			}*/
		}else {
			cluster = bregionDao.queryGridWarnDataCluster(region, sdate);
			if (cluster == null) {
				return analysisModel;
			}
		}
		if (cluster != null) {
			analysisModel.setSource1(cluster.getSourceNum1() + "," + cluster.getSource1());
			analysisModel.setSource2(cluster.getSourceNum2() + "," + cluster.getSource2());
			analysisModel.setSource3(cluster.getSourceNum3() + "," + cluster.getSource3());
			analysisModel.setSource4(cluster.getSourceNum4() + "," + cluster.getSource4());
			analysisModel.setSource5(cluster.getSourceNum5() + "," + cluster.getSource5());
			analysisModel.setSource6(cluster.getSourceNum6() + "," + cluster.getSource6());
			analysisModel.setSource7(cluster.getSourceNum7() + "," + cluster.getSource7());
			analysisModel.setSource8(cluster.getSourceNum8() + "," + cluster.getSource8());
			analysisModel.setSource9(cluster.getSourceNum9() + "," + cluster.getSource9());
			analysisModel.setSource10(cluster.getSourceNum10() + "," + cluster.getSource10());

			analysisModel.setGloal1(cluster.getGloalNum1() + "," + cluster.getGloal1());
			analysisModel.setGloal2(cluster.getGloalNum2() + "," + cluster.getGloal2());
			analysisModel.setGloal3(cluster.getGloalNum3() + "," + cluster.getGloal3());
			analysisModel.setGloal4(cluster.getGloalNum4() + "," + cluster.getGloal4());
			analysisModel.setGloal5(cluster.getGloalNum5() + "," + cluster.getGloal5());
			analysisModel.setGloal6(cluster.getGloalNum6() + "," + cluster.getGloal6());
			analysisModel.setGloal7(cluster.getGloalNum7() + "," + cluster.getGloal7());
			analysisModel.setGloal8(cluster.getGloalNum8() + "," + cluster.getGloal8());
			analysisModel.setGloal9(cluster.getGloalNum9() + "," + cluster.getGloal9());
			analysisModel.setGloal10(cluster.getGloalNum10() + "," + cluster.getGloal10());
			analysisModel.setMale(cluster.getMale());
			analysisModel.setFemale(cluster.getFemale());
			analysisModel.setAge1(cluster.getAge1());
			analysisModel.setAge2(cluster.getAge2());
			analysisModel.setAge3(cluster.getAge3());
			analysisModel.setAge4(cluster.getAge4());
			analysisModel.setAge5(cluster.getAge5());
		}
		return analysisModel;
	}
	@Override
	public AnalysisModel queryGridWarnDataClusterNew(String region, String sdate) {
		AnalysisModel analysisModel = new AnalysisModel();
		analysisModel.setTime(sdate);
		AnalysisCluster cluster=null;
		
		//获取所有的性别年龄 数量
		cluster= bregionDao.queryGridWarnDataClusterAllNew(region, sdate);
		if (cluster == null) {
			return analysisModel;
		}
		
		if (cluster != null) {
			analysisModel.setSource1(cluster.getSourceNum1() + "," + cluster.getSource1());
			analysisModel.setSource2(cluster.getSourceNum2() + "," + cluster.getSource2());
			analysisModel.setSource3(cluster.getSourceNum3() + "," + cluster.getSource3());
			analysisModel.setSource4(cluster.getSourceNum4() + "," + cluster.getSource4());
			analysisModel.setSource5(cluster.getSourceNum5() + "," + cluster.getSource5());
			analysisModel.setSource6(cluster.getSourceNum6() + "," + cluster.getSource6());
			analysisModel.setSource7(cluster.getSourceNum7() + "," + cluster.getSource7());
			analysisModel.setSource8(cluster.getSourceNum8() + "," + cluster.getSource8());
			analysisModel.setSource9(cluster.getSourceNum9() + "," + cluster.getSource9());
			analysisModel.setSource10(cluster.getSourceNum10() + "," + cluster.getSource10());
			
			analysisModel.setGloal1(cluster.getGloalNum1() + "," + cluster.getGloal1());
			analysisModel.setGloal2(cluster.getGloalNum2() + "," + cluster.getGloal2());
			analysisModel.setGloal3(cluster.getGloalNum3() + "," + cluster.getGloal3());
			analysisModel.setGloal4(cluster.getGloalNum4() + "," + cluster.getGloal4());
			analysisModel.setGloal5(cluster.getGloalNum5() + "," + cluster.getGloal5());
			analysisModel.setGloal6(cluster.getGloalNum6() + "," + cluster.getGloal6());
			analysisModel.setGloal7(cluster.getGloalNum7() + "," + cluster.getGloal7());
			analysisModel.setGloal8(cluster.getGloalNum8() + "," + cluster.getGloal8());
			analysisModel.setGloal9(cluster.getGloalNum9() + "," + cluster.getGloal9());
			analysisModel.setGloal10(cluster.getGloalNum10() + "," + cluster.getGloal10());
			analysisModel.setMale(cluster.getMale());
			analysisModel.setFemale(cluster.getFemale());
			analysisModel.setAge1(cluster.getAge1());
			analysisModel.setAge2(cluster.getAge2());
			analysisModel.setAge3(cluster.getAge3());
			analysisModel.setAge4(cluster.getAge4());
			analysisModel.setAge5(cluster.getAge5());
		}
		return analysisModel;
	}

	private void handleSourceOut(AnalysisModel analysisModel, List<BregionModel> analysisBySource) {
		if (analysisBySource != null && analysisBySource.size() > 0) {

			for (int i = 0; i < analysisBySource.size(); i++) {
				BregionModel bregionModel = analysisBySource.get(i);
				String source = bregionModel.getSource();
				if (StringUtils.isBlank(source) || ("null").equals(source) || ("").equals(source)) {
					source = "未知";
				}
				if (i == 0) {
					analysisModel.setGloal1(bregionModel.getSnum() + "," + source);
				}
				if (i == 1) {
					analysisModel.setGloal2(bregionModel.getSnum() + "," + source);
				}
				if (i == 2) {
					analysisModel.setGloal3(bregionModel.getSnum() + "," + source);
				}
				if (i == 3) {
					analysisModel.setGloal4(bregionModel.getSnum() + "," + source);
				}
				if (i == 4) {
					analysisModel.setGloal5(bregionModel.getSnum() + "," + source);
				}
				if (i == 5) {
					analysisModel.setGloal6(bregionModel.getSnum() + "," + source);
				}
				if (i == 6) {
					analysisModel.setGloal7(bregionModel.getSnum() + "," + source);
				}
				if (i == 7) {
					analysisModel.setGloal8(bregionModel.getSnum() + "," + source);
				}
				if (i == 8) {
					analysisModel.setGloal9(bregionModel.getSnum() + "," + source);
				}
				if (i == 9) {
					analysisModel.setGloal10(bregionModel.getSnum() + "," + source);
				}
			}
		}
	}

	private void handleSource(AnalysisModel analysisModel, List<BregionModel> analysisBySource) {
		if (analysisBySource != null && analysisBySource.size() > 0) {

			for (int i = 0; i < analysisBySource.size(); i++) {
				BregionModel bregionModel = analysisBySource.get(i);
				String source = bregionModel.getSource();
				if (StringUtils.isBlank(source) || ("null").equals(source) || ("").equals(source)) {
					source = "未知";
				}
				if (i == 0) {
					analysisModel.setSource1(bregionModel.getSnum() + "," + source);
				}
				if (i == 1) {
					analysisModel.setSource2(bregionModel.getSnum() + "," + source);
				}
				if (i == 2) {
					analysisModel.setSource3(bregionModel.getSnum() + "," + source);
				}
				if (i == 3) {
					analysisModel.setSource4(bregionModel.getSnum() + "," + source);
				}
				if (i == 4) {
					analysisModel.setSource5(bregionModel.getSnum() + "," + source);
				}
				if (i == 5) {
					analysisModel.setSource6(bregionModel.getSnum() + "," + source);
				}
				if (i == 6) {
					analysisModel.setSource7(bregionModel.getSnum() + "," + source);
				}
				if (i == 7) {
					analysisModel.setSource8(bregionModel.getSnum() + "," + source);
				}
				if (i == 8) {
					analysisModel.setSource9(bregionModel.getSnum() + "," + source);
				}
				if (i == 9) {
					analysisModel.setSource10(bregionModel.getSnum() + "," + source);
				}
			}
		}
	}

	private void handleAge(AnalysisModel analysisModel, List<BregionModel> analysisByAge) {
		if (analysisByAge != null && analysisByAge.size() > 0) {
			for (BregionModel bregionModel : analysisByAge) {
				if (("age18").equals(bregionModel.getAge())) {
					analysisModel.setAge1(Long.valueOf(bregionModel.getAnum()));
				}
				if (("age35").equals(bregionModel.getAge())) {
					analysisModel.setAge2(Long.valueOf(bregionModel.getAnum()));
				}
				if (("age50").equals(bregionModel.getAge())) {
					analysisModel.setAge3(Long.valueOf(bregionModel.getAnum()));
				}
				if (("age65").equals(bregionModel.getAge())) {
					analysisModel.setAge4(Long.valueOf(bregionModel.getAnum()));
				}
				if (("age200").equals(bregionModel.getAge())) {
					analysisModel.setAge5(Long.valueOf(bregionModel.getAnum()));
				}
			}
		}
	}

	private void handleSex(AnalysisModel analysisModel, List<BregionModel> analysisByGnder) {
		if (analysisByGnder != null && analysisByGnder.size() > 0) {
			for (BregionModel bregionModel : analysisByGnder) {
				if (("1").equals(bregionModel.getGender())) {
					analysisModel.setMale(bregionModel.getGnum());
				}
				if (("2").equals(bregionModel.getGender())) {
					analysisModel.setFemale(bregionModel.getGnum());
				}
			}
		}
	}

	private void InitEntity(AnalysisModel analysisModel) {
		analysisModel.setMale(0l);
		analysisModel.setFemale(0l);
		analysisModel.setAge1(0l);
		analysisModel.setAge2(0l);
		analysisModel.setAge2(0l);
		analysisModel.setAge3(0l);
		analysisModel.setAge4(0l);
		analysisModel.setAge5(0l);
		analysisModel.setSource1("0,无");
		analysisModel.setSource2("0,无");
		analysisModel.setSource3("0,无");
		analysisModel.setSource4("0,无");
		analysisModel.setSource5("0,无");
		analysisModel.setSource6("0,无");
		analysisModel.setSource7("0,无");
		analysisModel.setSource8("0,无");
		analysisModel.setSource9("0,无");
		analysisModel.setSource10("0,无");

		analysisModel.setGloal1("0,无");
		analysisModel.setGloal2("0,无");
		analysisModel.setGloal3("0,无");
		analysisModel.setGloal4("0,无");
		analysisModel.setGloal5("0,无");
		analysisModel.setGloal6("0,无");
		analysisModel.setGloal7("0,无");
		analysisModel.setGloal8("0,无");
		analysisModel.setGloal9("0,无");
		analysisModel.setGloal10("0,无");
	}

	@Override
	public List<Map<String, Object>> queryDateForMinute(String beginDate, String endDate, String region)  {
		
		Double numPercent = 0.0;
		if (BootConstant.People_Num_Percent > 0) {
			numPercent = BootConstant.People_Num_Percent;
		} else {
			numPercent = null;
		}
		
		List<Map<String,Object>> datas = bregionDao.queryDateForMinute(beginDate,endDate,region,numPercent);
		
		List<Map<String, Object>> list=new ArrayList<>();
		
		
		
		if (datas != null && datas.size()>0) {
			Map<String, String> entity=handleDatas(datas);
			for (Map<String, Object> map : datas) {
				
				String date=(String) map.get("date");
				
				try {
					String before=MyUtil.getFiveDate(date,5);
					if (entity.containsKey(before)) {
						//list.add(model);
						Integer beforeNum=Integer.valueOf(entity.get(before).split("_")[1]);
						Integer nowNum=Integer.valueOf(entity.get(date).split("_")[1]);
						
						Integer cha=nowNum-beforeNum;
						Integer js=cha/5;
						Integer dan=cha-js*4;
						
						Random rand = new Random();
						Integer sj=rand.nextInt(5) + 1;
						
						Integer num1=0;
						Integer num2=0;
						Integer num3=0;
						Integer num4=0;
						
						Integer num=beforeNum+js-sj;
						//产生 没有前后的4个数字
						List<String> dates=MyUtil.getDateStrY(before, date, 1);
						if (num>0) {
							num1=num;
							num2=num+js;
							num3=num+js+js+sj;
							num4=num+js+js+dan;
						}else {
							num=beforeNum+js+sj;
							num1=num;
							num2=beforeNum+js;
							num3=beforeNum+js-sj;
							num4=dan;
						}
						for (int i = 0; i < 4; i++) {
							Map<String, Object> model=new HashMap<>();
							if (i==0) {
								model.put("date", dates.get(i));
								model.put("total",num1);
								list.add(model);
							}
							if (i==1) {
								model.put("date", dates.get(i));
								model.put("total",num2);
								list.add(model);
							}
							if (i==2) {
								model.put("date", dates.get(i));
								model.put("total",num3);
								list.add(model);
							}
							if (i==3) {
								model.put("date", dates.get(i));
								model.put("total",num4);
								list.add(model);
							}
						}
					}
					Map<String, Object> model=new HashMap<>();
					model.put("date", date);
					model.put("total", entity.get(date).split("_")[1]);
					list.add(model);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private Map<String, String> handleDatas(List<Map<String, Object>> datas) {
		Map<String, String> map=new HashMap<>();
		for (Map<String, Object> map1 : datas) {
			String date=(String) map1.get("date");
			String num=map1.get("num").toString();
			if (num.contains(".")) {
				map.put(date, date+"_"+num.substring(0, num.indexOf(".")));
			}else {
				map.put(date, date+"_"+num);
			}
		}
		return map;
	}

	@Override
	public String queryMaxDateClus() {
		return bregionDao.queryMaxDateClus();
	}
	
}
