package com.boot.security.server.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.BregionDao;
import com.boot.security.server.model.AnalysisCluster;
import com.boot.security.server.model.AnalysisModel;
import com.boot.security.server.model.BregionModel;
import com.boot.security.server.service.RegionService;

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
		AnalysisCluster cluster=null;
		if (region == null) {
			cluster = bregionDao.queryGridWarnDataClusterAll(region, sdate);
		}else {
			cluster = bregionDao.queryGridWarnDataCluster(region, sdate);
		}
		if (cluster != null) {
			analysisModel.setMale(cluster.getMale());
			analysisModel.setFemale(cluster.getFemale());

			analysisModel.setAge1(cluster.getAge1());
			analysisModel.setAge2(cluster.getAge2());
			analysisModel.setAge3(cluster.getAge3());
			analysisModel.setAge4(cluster.getAge4());
			analysisModel.setAge5(cluster.getAge5());

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
		}
		analysisModel.setTime(sdate);
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

}
