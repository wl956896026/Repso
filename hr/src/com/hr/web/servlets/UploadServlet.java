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
	 * ʹ��commons-fileuploadʵ���ļ��ϴ�
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
					
					if(fileItem.isFormField()){//�ж��Ƿ�Ϊ��ͨ�ı���,�Ƿ���true
						//��ñ�Ԫ��name���Ե�ֵ
						String fileName = fileItem.getFieldName();
						//�����ͨ�����ֵ
						String username = fileItem.getString("UTF-8");
						System.out.println("��ͨ����:"+username);
					}else{
						System.out.println(fileItem.getContentType());
						//����ֻ���ϴ�ͼƬ�ļ�
						if(!fileItem.getContentType().contains("image/")){
							throw new Exception();
						}
						
						//��ñ�Ԫ��name���Ե�ֵ
						String fieldName = fileItem.getFieldName();
						System.out.println("�ļ��ϴ���:"+fieldName);
						String fileName = fileItem.getName();
						System.out.println("�ϴ��ļ����ļ���:"+fileName);
						//��ȡ����������
						InputStream input = fileItem.getInputStream();
						//��ȡ�ϴ��ļ���·��
						String uploadPath = this.getServletContext().getRealPath("/upload/images");
						//�������������
						OutputStream output = new FileOutputStream(new File(uploadPath+"/"+fileName));
						//ʹ��commons-io���е�IOUtils����������ļ���copy
						IOUtils.copy(input, output);
						//�ر�������,�ر������
						input.close();
						output.close();
						//ɾ����ʱ�ļ�
						fileItem.delete();
						
						
						
						
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
