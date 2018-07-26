package com.hr.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.dao.PositionDao;
import com.hr.dao.impl.PositionDaoImpl;
import com.hr.entity.Dept;
import com.hr.entity.Position;

@WebServlet(urlPatterns="/position/position.do")
public class PositionServlet extends BaseServlet {
	private PositionDao positionDao = new PositionDaoImpl();
	/***
	 * ���ݲ��ű�Ż�ø�λ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryPositionByDept(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//��ò��ű��
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		List<Position> positions = positionDao.queryPositionByDeptId(deptId);
		if(positions==null || positions.size()==0){
			out.print("");
			return;
		}
		// ��positions����ת��Ϊһ��JSON����
				StringBuffer jsonPosition = new StringBuffer("[");
				for (Position position : positions) {
					jsonPosition.append("{");
					jsonPosition.append("position_id").append(":").append(position.getPosition_id())
							.append(",").append("position_name").append(":").append("\"")
							.append(position.getPosition_name()).append("\"");
					jsonPosition.append("},");
				}
				//�Ƴ����һ������
				jsonPosition.delete(jsonPosition.length()-1, jsonPosition.length());
				jsonPosition.append("]");
				//��JSON��ʽ�����鴫���ͻ���
				out.print(jsonPosition.toString());
	}
}
