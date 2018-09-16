package com.wut.store.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wut.store.domain.Category;
import com.wut.store.domain.Product;
import com.wut.store.service.ProductService;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.UUIDUtils;

/**
 * Servlet implementation class AddProductServlet
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
		//����Product����
			Product product = new Product();
		// ���������ļ����
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// ���û�������С������ļ��Ĵ�С�����˻������Ĵ�С���ͻ������ʱ�ļ�
		diskFileItemFactory.setSizeThreshold(3 * 1024 * 1024);
		//������ʱ�ļ���ŵ�·��
		//diskFileItemFactory.setRepository(repository);
		// ��ú��Ľ����ࣺServletFileUpload
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		fileUpload.setHeaderEncoding("UTF-8");//�����ļ����ϴ�����
		//fileUpload.setFileSizeMax(fileSizeMax);//���õ����ļ���С
		//fileUpload.setSizeMax(sizeMax);//���ñ��������ļ����ܴ�С
		// ����request ����List����
		List<FileItem> list = fileUpload.parseRequest(request);
		//���ÿһ������
		//��������ֵ���뵽һ��Map������
		Map<String,String> map = new HashMap<String,String>();
		String fileName = null;
		for(FileItem fileItem:list){
			//�ж���ͨ����ļ��ϴ���
			if(fileItem.isFormField()){
				//��ͨ��
				String name = fileItem.getFieldName();
				String value = fileItem.getString("UTF-8");//�����ͨ�����������
				System.out.println(name+"   "+value);
				map.put(name, value);
			}else{
				//�ļ��ϴ���
				//����ļ�����
				fileName = fileItem.getName();
				System.out.println("�ļ�����"+fileName);
				//�����������
				InputStream is = fileItem.getInputStream();
				//����ļ�Ҫ�ϴ���·����
				String path = this.getServletContext().getRealPath("/products/1");
				OutputStream os = new FileOutputStream(path+"/"+fileName);
				int length = 0;
				byte[]b=new byte[1024];
				while((length = is.read(b))!=-1){
					os.write(b, 0, length);
				}
				is.close();
				os.close();
			}
		}
		//��װ����
		BeanUtils.populate(product, map);
		product.setPid(UUIDUtils.getUUID());
		product.setPdate(new Date());
		product.setPflag(0);
		product.setPimage("products/1/"+fileName);
		Category category = new Category();
		category.setCid(map.get("cid"));
		product.setCategory(category);
		//���뵽���ݿ⣺
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		productService.save(product);
		//ҳ����ת
		response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
