package com.team.project.dib.dao;

import java.util.List;

import com.team.project.dib.dto.DibDto;

public interface DibDao {
	public void insert(DibDto dto);
	public void delete(int dibson_num);
	public List<DibDto> getData(DibDto dto);
	public DibDto getDetailData(DibDto dto);
	public int getCount();
}
