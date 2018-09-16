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
		//创建Product对象
			Product product = new Product();
		// 创建磁盘文件项工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 设置缓存区大小：如果文件的大小超过了缓冲区的大小，就会产生临时文件
		diskFileItemFactory.setSizeThreshold(3 * 1024 * 1024);
		//设置临时文件存放的路径
		//diskFileItemFactory.setRepository(repository);
		// 获得核心解析类：ServletFileUpload
		ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		fileUpload.setHeaderEncoding("UTF-8");//中文文件名上传乱码
		//fileUpload.setFileSizeMax(fileSizeMax);//设置单个文件大小
		//fileUpload.setSizeMax(sizeMax);//设置表单中所有文件的总大小
		// 解析request 返回List集合
		List<FileItem> list = fileUpload.parseRequest(request);
		//获得每一个部分
		//将遍历的值存入到一个Map集合中
		Map<String,String> map = new HashMap<String,String>();
		String fileName = null;
		for(FileItem fileItem:list){
			//判断普通箱和文件上传项
			if(fileItem.isFormField()){
				//普通项
				String name = fileItem.getFieldName();
				String value = fileItem.getString("UTF-8");//解决普通项的中文乱码
				System.out.println(name+"   "+value);
				map.put(name, value);
			}else{
				//文件上传项
				//获得文件名：
				fileName = fileItem.getName();
				System.out.println("文件名："+fileName);
				//获得输入流：
				InputStream is = fileItem.getInputStream();
				//获得文件要上传的路径：
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
		//封装数据
		BeanUtils.populate(product, map);
		product.setPid(UUIDUtils.getUUID());
		product.setPdate(new Date());
		product.setPflag(0);
		product.setPimage("products/1/"+fileName);
		Category category = new Category();
		category.setCid(map.get("cid"));
		product.setCategory(category);
		//存入到数据库：
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		productService.save(product);
		//页面跳转
		response.sendRedirect(request.getContextPath()+"/adminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
