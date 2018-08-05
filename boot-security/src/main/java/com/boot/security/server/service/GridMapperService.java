package com.boot.security.server.service;

import java.util.List;

import com.boot.security.server.model.GridMapper;

public interface GridMapperService {

	public void save(GridMapper gridMapper);

	public void changeData(int x, int y, String id);

	public List<String> findIdList();

	public List<GridMapper> find();
}
