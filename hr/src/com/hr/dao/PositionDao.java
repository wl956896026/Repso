package com.hr.dao;

import java.util.List;

import com.hr.entity.Position;

public interface PositionDao {
	/***
	 * ����ְλ��Ż��ְλ����
	 * @param positionId
	 * @return
	 */
	public Position queryPositionById(int positionId);
	/***
	 * ��ȡ���и�λ��Ϣ
	 * @return
	 */
	public List<Position> queryPositions();
	/***
	 * ���ݲ��ű�Ż�øò��ŵ�ְλ
	 * @param deptId
	 * @return
	 */
	public List<Position> queryPositionByDeptId(int deptId);
}
