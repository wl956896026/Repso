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
	 * 添加员工Servlet
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
	 * 查询员工Servlet
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

		// 将获得到的存有员工数据的list集合存入到request中
		request.setAttribute("emps", emps);
		// 请求转发到manager.jsp页面
		request.getRequestDispatcher("manager.jsp").forward(request, response);

	}

	/***
	 * 查询部门和职位Servlet
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
	 * 根据员工编号获得员工详情
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
	 * 检测登录名是否可用
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
		// 将获得的结果回传给客户端
		// 获得输出流
		PrintWriter writer = response.getWriter();
		// 输出给客户端
		writer.write(String.valueOf(boo));
	}

	protected void queryEmpName(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			PrintWriter out = response.getWriter();
			// 获得客户端提交的参数
			String nameStr = request.getParameter("empName");
			// 处理中文乱码

			String empName = new String(nameStr.getBytes("iso-8859-1"), "UTF-8");
			//调用底层处理获得数据,获得一个集合
		//	List<Emp> emps = empDao.queryEmpName(empName);
			//调用底层处理获得数据,获得一个包含名字的数组
			String[] empNames = empDao.queryEmpName(empName);
			//将一个Java集合对象转换为JSON数组
			//JSONArray empsJSON = (JSONArray)JSONSerializer.toJSON(emps);
			JSONArray empNameJSON = (JSONArray)JSONSerializer.toJSON(empNames);
			System.out.println(empNameJSON);
			if(empNameJSON.size()>0){
				out.print(empNameJSON);//回传到客户端
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
	 * 图片上传
	 * @param request
	 * @param response
	 */
	protected void photoUpload(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			//创建一个磁盘文件管理工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置一个上传文件的缓存大小,字节为单位
			factory.setSizeThreshold(1024*1024*2);
			//设置一个上传文件的临时路径
			//获得临时文件的路径,(根据项目路径获得绝对路径)
			String tempPath = this.getServletContext().getRealPath("/upload/temp");
			//设置上传文件的临时文件路径
			factory.setRepository(new File(tempPath));
			
			//创建一个上传文件的核心对象(ServletFileUpload),该类对象是实现上传功能的核心对象
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			//设置单个文件上传的大小
			//fileUpload.setFileSizeMax(1024*1024*2);
			//设置一次上传的总大小
			//fileUpload.setSizeMax(1024*1024*10);
			//检测表单类型是否为multipart/form-data
			if(fileUpload.isMultipartContent(request)){
				//解析request，并获得包含多个文件项的列表
				List<FileItem> fileItems = fileUpload.parseRequest(request);
				//遍历fileItems集合
				for(FileItem fileItem : fileItems){					
					
					if(!fileItem.isFormField()){//判断是否为普通的表单域,是返回true
						//获取文件的大小
						long size = fileItem.getSize();
						System.out.println(fileItem.getContentType());
						//设置只能上传图片文件
						if(!fileItem.getContentType().contains("image/")){
							throw new Exception();
						}
						
						//获得表单元素name属性的值
						String fieldName = fileItem.getFieldName();
						System.out.println("文件上传域:"+fieldName);
						//获得原文件名
						String oldFileName = fileItem.getName();
						//获得文件的扩展名
						String ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
						String newFileName = UUID.randomUUID().toString()+"."+ext;
						System.out.println("上传文件的文件名:"+oldFileName);
						//获取输入流对象
						InputStream input = fileItem.getInputStream();
						//获取上传文件的路径
						String uploadPath = this.getServletContext().getRealPath("/upload/images");
						//创建输出流对象
						OutputStream output = new FileOutputStream(new File(uploadPath+"/"+newFileName));
						//使用commons-io包中的IOUtils工具类进行文件的copy(上传)
						long copySize = IOUtils.copy(input, output);
						System.out.println(size);
						System.out.println(copySize);
						//关闭输入流,关闭输出流
						input.close();
						output.close();
						//删除临时文件
						fileItem.delete();
						//当文件大小等于上传的大小表示上传成功
						if(size==copySize){
							//将原文件名和新文件名拼接成一个字符串回传给客户端
							//String msg = newFileName+"_"+oldFileName;
							
							String jsonArr = "['"+newFileName+"','"+oldFileName+"']";
							//上传成功后将文件名回传到客户端
							response.getWriter().print(jsonArr);
							
						}
						
						
						
						
					}
					
				}
				
				
				
				
				
			}else{
				//throw new Exception("类型不正确");
				System.out.println("类型不正确");
			}
		} catch(SizeLimitExceededException e){
			System.out.println("单个文件上传大小不能超过2M");
		}catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("上传文件必须为图片文件...");
		}
	}
}
