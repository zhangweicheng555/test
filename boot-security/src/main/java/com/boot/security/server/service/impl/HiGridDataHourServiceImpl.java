package com.boot.security.server.service.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.common.BootConstant;
import com.boot.security.server.dao.HiGridDataHourDao;
import com.boot.security.server.model.HiGridDataHour;
import com.boot.security.server.service.HiGridDataHourService;

@Service
public class HiGridDataHourServiceImpl implements HiGridDataHourService {

	@Autowired
	private HiGridDataHourDao hiGridDataHourDao;
	
	/**
	 * Cacheable/CachePut/CacheEvit 都有value属性，制定的是要使用的缓存的名称，
	 * 就是缓存xml里面的name  
	 * key是要缓存的内容  在缓存中的键值
	 */
	//@Cacheable(value="queryPeopleNumByTimeRange")
	@Override
	public Integer queryPeopleNumByTimeRange(Date dateStr, String region) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return hiGridDataHourDao.queryPeopleNumByTimeRange(sdf.format(dateStr), region);
	}

	@Override
	public HiGridDataHour queryHiGridDataHourLatest(String region) {
		return hiGridDataHourDao.queryHiGridDataHourLatest(region);
	}

	@Override
	public long queryCount(HttpSession session) {
		long countNum=hiGridDataHourDao.queryCount();
		if (session.getAttribute(BootConstant.LTE_Region_NUM_HOUR) != null) {
			Long beforeNum=(Long) session.getAttribute(BootConstant.LTE_Region_NUM_HOUR);
			if (countNum > beforeNum) {
				session.removeAttribute(BootConstant.LTE_Region_NUM_HOUR);
				session.setAttribute(BootConstant.LTE_Region_NUM_HOUR, countNum);
				return 1l;
			}else {
				return 0l;
			}
		}else{
			session.setAttribute(BootConstant.LTE_Region_NUM_HOUR, countNum);
		}
		return 1l;
	}

	@Override
	public List<Integer> queryPeopleNumByTimeRangeNew(List<String> listDates, String key) {
		return hiGridDataHourDao.queryPeopleNumByTimeRangeNew(listDates,key);
	}
}
