package com.hr.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hr.dao.DBUtil;
import com.hr.dao.PositionDao;
import com.hr.entity.Position;

public class PositionDaoImpl extends DBUtil implements PositionDao {

	@Override
	public Position queryPositionById(int positionId) {
		String sql = "select * from tbl_position where position_id=?";
		List<Position> positions = super.executeQuery(sql, Position.class, positionId);
		if(positions!=null && positions.size()!=0){
			return positions.get(0);
		}
		return null;
	}

	@Override
	public List<Position> queryPositions() {
		String sql = "select * from tbl_position";
		return super.executeQuery(sql, Position.class);
	}

	@Override
	public List<Position> queryPositionByDeptId(int deptId) {
		String sql = "select * from tbl_position where dept_id=?";
		return super.executeQuery(sql, Position.class, deptId);
		
	}

}
