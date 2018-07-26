package com.hr.web.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
@WebServlet(urlPatterns="/emp/upload.do")
public class UploadServlet extends HttpServlet {
	/**8
	 * 使用commons-fileupload实现文件上传
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
					
					if(fileItem.isFormField()){//判断是否为普通的表单域,是返回true
						//获得表单元素name属性的值
						String fileName = fileItem.getFieldName();
						//获得普通表单域的值
						String username = fileItem.getString("UTF-8");
						System.out.println("普通表单域:"+username);
					}else{
						System.out.println(fileItem.getContentType());
						//设置只能上传图片文件
						if(!fileItem.getContentType().contains("image/")){
							throw new Exception();
						}
						
						//获得表单元素name属性的值
						String fieldName = fileItem.getFieldName();
						System.out.println("文件上传域:"+fieldName);
						String fileName = fileItem.getName();
						System.out.println("上传文件的文件名:"+fileName);
						//获取输入流对象
						InputStream input = fileItem.getInputStream();
						//获取上传文件的路径
						String uploadPath = this.getServletContext().getRealPath("/upload/images");
						//创建输出流对象
						OutputStream output = new FileOutputStream(new File(uploadPath+"/"+fileName));
						//使用commons-io包中的IOUtils工具类进行文件的copy
						IOUtils.copy(input, output);
						//关闭输入流,关闭输出流
						input.close();
						output.close();
						//删除临时文件
						fileItem.delete();
						
						
						
						
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
