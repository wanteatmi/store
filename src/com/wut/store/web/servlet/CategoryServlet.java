package com.wut.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.wut.store.domain.Category;
import com.wut.store.service.CategoryService;
import com.wut.store.service.impl.CategoryServiceImpl;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ѯ���з����Servlet������findAll
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// ����ҵ��㣺
			// CategoryService categoryService=new CategoryServiceImpl();
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			// ��listתΪJSON
			JSONArray jsonArray = JSONArray.fromObject(list);
			System.out.println(jsonArray.toString());
			resp.getWriter().println(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
