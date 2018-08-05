package com.boot.security.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.security.server.model.GridMapper;

@Mapper
public interface GridMapperDao {

	public void save(GridMapper gridMapper);

	public void changeData(@Param("indexx") int x, @Param("indexy") int y, @Param("id") String id);

	public List<String> findIdList();

	public List<GridMapper> find();
}
