package com.hr.web.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import sun.org.mozilla.javascript.internal.json.JsonParser;

import com.hr.dao.DeptDao;
import com.hr.dao.EmpDao;
import com.hr.dao.PositionDao;
import com.hr.dao.impl.DeptDaoImpl;
import com.hr.dao.impl.EmpDaoImpl;
import com.hr.dao.impl.PositionDaoImpl;
import com.hr.entity.Dept;
import com.hr.entity.Emp;
import com.hr.entity.Experience;
import com.hr.entity.Position;

@WebServlet(urlPatterns = "/emp/emp.do")
public class EmpServlet extends BaseServlet {
	private EmpDao empDao = new EmpDaoImpl();

	/***
	 * ���Ա��Servlet
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addEmp(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("UTF-8");
		String emp_name = request.getParameter("emp_name");
		String emp_sex = request.getParameter("emp_sex");
		String login_name = request.getParameter("login_name");
		String emp_old_photo = request.getParameter("emp_old_photo");
		String emp_new_photo = request.getParameter("emp_new_photo");
		String emp_birthday = request.getParameter("emp_birthday");
		String emp_xueli = request.getParameter("emp_xueli");
		String emp_major = request.getParameter("emp_major");
		String emp_school = request.getParameter("emp_school");
		String emp_phone = request.getParameter("emp_phone");
		String emp_email = request.getParameter("emp_email");
		String emp_put_time = request.getParameter("emp_put_time");
		String position_id = request.getParameter("position_id");

		Emp emp = new Emp();
		emp.setEmp_birthday(emp_birthday);
		emp.setEmp_email(emp_email);
		emp.setEmp_major(emp_major);
		emp.setEmp_name(emp_name);
		emp.setEmp_phone(emp_phone);
		emp.setEmp_photo(emp_old_photo);
		emp.setEmp_new_photo(emp_new_photo);
		emp.setEmp_put_time(emp_put_time);
		emp.setEmp_school(emp_school);
		emp.setEmp_sex(Integer.parseInt(emp_sex));
		emp.setEmp_xueli(emp_xueli);
		emp.setLogin_name(login_name);
		emp.setPosition_id(Integer.parseInt(position_id));

		String company = request.getParameter("company");
		String position = request.getParameter("position");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String job_content = request.getParameter("job_content");

		Experience experience = new Experience();
		experience.setCompany(company);
		experience.setEnd_time(end_time);
		experience.setJob_content(job_content);
		experience.setPosition(position);
		experience.setStart_time(start_time);

		int i = empDao.addEmp(emp, experience);
		if (i > 0) {
			// response.sendRedirect("queryEmpServlet");
			this.queryEmp(request, response);
		} else {
			// response.sendRedirect("queryDeptPosition");
			this.queryDeptAndPosition(request, response);
		}

	}

	/**
	 * ��ѯԱ��Servlet
	 */
	protected void queryEmp(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String empName = request.getParameter("empName");
		List<Object[]> emps = null;
		if (empName != null && !empName.equals("")) {
			empName = new String(empName.getBytes("iso-8859-1"), "UTF-8");
			emps = empDao.queryEmp(empName);
		} else {
			emps = empDao.queryEmp();
		}

		// ����õ��Ĵ���Ա�����ݵ�list���ϴ��뵽request��
		request.setAttribute("emps", emps);
		// ����ת����manager.jspҳ��
		request.getRequestDispatcher("manager.jsp").forward(request, response);

	}

	/***
	 * ��ѯ���ź�ְλServlet
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryDeptAndPosition(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DeptDao deptDao = new DeptDaoImpl();
		PositionDao positionDao = new PositionDaoImpl();
		List<Dept> depts = deptDao.queryDept();
		List<Position> positions = positionDao.queryPositions();
		request.setAttribute("depts", depts);
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("addEmp.jsp").forward(request, response);
	}

	/***
	 * ����Ա����Ż��Ա������
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void empDesc(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int empId = Integer.parseInt(request.getParameter("emp_id"));
		String[] empInfo = empDao.queryEmpById(empId);
		request.setAttribute("empInfo", empInfo);
		request.getRequestDispatcher("/emp/empDesc.jsp").forward(request,
				response);
	}

	/***
	 * ����¼���Ƿ����
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void checkLoginName(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String loginName = request.getParameter("loginName");
		System.out.println(loginName);
		boolean boo = empDao.checkLoginName(loginName);
		// ����õĽ���ش����ͻ���
		// ��������
		PrintWriter writer = response.getWriter();
		// ������ͻ���
		writer.write(String.valueOf(boo));
	}

	protected void queryEmpName(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			PrintWriter out = response.getWriter();
			// ��ÿͻ����ύ�Ĳ���
			String nameStr = request.getParameter("empName");
			// ������������

			String empName = new String(nameStr.getBytes("iso-8859-1"), "UTF-8");
			//���õײ㴦��������,���һ������
		//	List<Emp> emps = empDao.queryEmpName(empName);
			//���õײ㴦��������,���һ���������ֵ�����
			String[] empNames = empDao.queryEmpName(empName);
			//��һ��Java���϶���ת��ΪJSON����
			//JSONArray empsJSON = (JSONArray)JSONSerializer.toJSON(emps);
			JSONArray empNameJSON = (JSONArray)JSONSerializer.toJSON(empNames);
			System.out.println(empNameJSON);
			if(empNameJSON.size()>0){
				out.print(empNameJSON);//�ش����ͻ���
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/***
	 * ͼƬ�ϴ�
	 * @param request
	 * @param response
	 */
	protected void photoUpload(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//����һ�������ļ�������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//����һ���ϴ��ļ��Ļ����С,�ֽ�Ϊ��λ
			factory.setSizeThreshold(1024*1024*2);
			//����һ���ϴ��ļ�����ʱ·��
			//�����ʱ�ļ���·��,(������Ŀ·����þ���·��)
			String tempPath = this.getServletContext().getRealPath("/upload/temp");
			//�����ϴ��ļ�����ʱ�ļ�·��
			factory.setRepository(new File(tempPath));
			
			//����һ���ϴ��ļ��ĺ��Ķ���(ServletFileUpload),���������ʵ���ϴ����ܵĺ��Ķ���
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			//���õ����ļ��ϴ��Ĵ�С
			//fileUpload.setFileSizeMax(1024*1024*2);
			//����һ���ϴ����ܴ�С
			//fileUpload.setSizeMax(1024*1024*10);
			//���������Ƿ�Ϊmultipart/form-data
			if(fileUpload.isMultipartContent(request)){
				//����request������ð�������ļ�����б�
				List<FileItem> fileItems = fileUpload.parseRequest(request);
				//����fileItems����
				for(FileItem fileItem : fileItems){					
					
					if(!fileItem.isFormField()){//�ж��Ƿ�Ϊ��ͨ�ı���,�Ƿ���true
						//��ȡ�ļ��Ĵ�С
						long size = fileItem.getSize();
						System.out.println(fileItem.getContentType());
						//����ֻ���ϴ�ͼƬ�ļ�
						if(!fileItem.getContentType().contains("image/")){
							throw new Exception();
						}
						
						//��ñ�Ԫ��name���Ե�ֵ
						String fieldName = fileItem.getFieldName();
						System.out.println("�ļ��ϴ���:"+fieldName);
						//���ԭ�ļ���
						String oldFileName = fileItem.getName();
						//����ļ�����չ��
						String ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
						String newFileName = UUID.randomUUID().toString()+"."+ext;
						System.out.println("�ϴ��ļ����ļ���:"+oldFileName);
						//��ȡ����������
						InputStream input = fileItem.getInputStream();
						//��ȡ�ϴ��ļ���·��
						String uploadPath = this.getServletContext().getRealPath("/upload/images");
						//�������������
						OutputStream output = new FileOutputStream(new File(uploadPath+"/"+newFileName));
						//ʹ��commons-io���е�IOUtils����������ļ���copy(�ϴ�)
						long copySize = IOUtils.copy(input, output);
						System.out.println(size);
						System.out.println(copySize);
						//�ر�������,�ر������
						input.close();
						output.close();
						//ɾ����ʱ�ļ�
						fileItem.delete();
						//���ļ���С�����ϴ��Ĵ�С��ʾ�ϴ��ɹ�
						if(size==copySize){
							//��ԭ�ļ��������ļ���ƴ�ӳ�һ���ַ����ش����ͻ���
							//String msg = newFileName+"_"+oldFileName;
							
							String jsonArr = "['"+newFileName+"','"+oldFileName+"']";
							//�ϴ��ɹ����ļ����ش����ͻ���
							response.getWriter().print(jsonArr);
							
						}
						
						
						
						
					}
					
				}
				
				
				
				
				
			}else{
				//throw new Exception("���Ͳ���ȷ");
				System.out.println("���Ͳ���ȷ");
			}
		} catch(SizeLimitExceededException e){
			System.out.println("�����ļ��ϴ���С���ܳ���2M");
		}catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("�ϴ��ļ�����ΪͼƬ�ļ�...");
		}
	}
}
