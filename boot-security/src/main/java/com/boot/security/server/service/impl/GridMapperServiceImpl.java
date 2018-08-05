package com.boot.security.server.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.boot.security.server.dao.GridMapperDao;
import com.boot.security.server.model.GridMapper;
import com.boot.security.server.service.GridMapperService;

@Service
public class GridMapperServiceImpl implements GridMapperService {

	@Autowired
	private GridMapperDao gridMapperDao;

	@Override
	public void save(GridMapper gridMapper) {
		gridMapperDao.save(gridMapper);
	}

	@Override
	public void changeData(int x, int y, String id) {
		gridMapperDao.changeData(x, y, id);
	}

	@Override
	public List<String> findIdList() {
		return gridMapperDao.findIdList();
	}

	@Override
	public List<GridMapper> find() {
		return gridMapperDao.find();
	}

}
