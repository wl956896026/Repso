package com.hr.dao;

import java.util.List;

import com.hr.entity.Position;

public interface PositionDao {
	/***
	 * 根据职位编号获得职位对象
	 * @param positionId
	 * @return
	 */
	public Position queryPositionById(int positionId);
	/***
	 * 获取所有岗位信息
	 * @return
	 */
	public List<Position> queryPositions();
	/***
	 * 根据部门编号获得该部门的职位
	 * @param deptId
	 * @return
	 */
	public List<Position> queryPositionByDeptId(int deptId);
}
