package com.boot.security.server.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.BregionDao;
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
		// 处理省份
		handleSource(analysisModel, bregionDao.analysisBySource(region, sdate));
		return analysisModel;
	}

	private void handleSource(AnalysisModel analysisModel, List<BregionModel> analysisBySource) {
		if (analysisBySource != null && analysisBySource.size() > 0) {

			for (int i = 0; i < analysisBySource.size(); i++) {
				BregionModel bregionModel = analysisBySource.get(i);
				if (i == 0) {
					analysisModel.setSource1(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 1) {
					analysisModel.setSource2(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 2) {
					analysisModel.setSource3(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 3) {
					analysisModel.setSource4(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 4) {
					analysisModel.setSource5(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 5) {
					analysisModel.setSource6(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 6) {
					analysisModel.setSource7(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 7) {
					analysisModel.setSource8(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 8) {
					analysisModel.setSource9(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
				if (i == 9) {
					analysisModel.setSource10(bregionModel.getSnum() + "," + bregionModel.getSource());
				}
			}
		}
	}

	private void handleAge(AnalysisModel analysisModel, List<BregionModel> analysisByAge) {
		if (analysisByAge != null && analysisByAge.size() > 0) {
			for (BregionModel bregionModel : analysisByAge) {
				if (("0-18").equals(bregionModel.getAge())) {
					analysisModel.setAge1(Long.valueOf(bregionModel.getAnum()));
				}
				if (("18-35").equals(bregionModel.getAge())) {
					analysisModel.setAge1(Long.valueOf(bregionModel.getAnum()));
				}
				if (("35-50").equals(bregionModel.getAge())) {
					analysisModel.setAge3(Long.valueOf(bregionModel.getAnum()));
				}
				if (("50-60").equals(bregionModel.getAge())) {
					analysisModel.setAge4(Long.valueOf(bregionModel.getAnum()));
				}
				if (("65-200").equals(bregionModel.getAge())) {
					analysisModel.setAge5(Long.valueOf(bregionModel.getAnum()));
				}
			}
		}
	}

	private void handleSex(AnalysisModel analysisModel, List<BregionModel> analysisByGnder) {
		if (analysisByGnder != null && analysisByGnder.size() > 0) {
			for (BregionModel bregionModel : analysisByGnder) {
				if (("男").equals(bregionModel.getGender())) {
					analysisModel.setMale(bregionModel.getGnum());
				}
				if (("女").equals(bregionModel.getGender())) {
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
	}

}
