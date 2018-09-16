package com.wut.store.web.servlet;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wut.store.domain.Category;
import com.wut.store.service.CategoryService;
import com.wut.store.utils.BaseServlet;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.UUIDUtils;

/**
 * 后台分类管理的servlet
 */
public class adminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * 后台查询所有分类的方法：findAll
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 调用业务层
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			// 存入req域中，完成页面跳转
			req.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";
	}

	/**
	 * 跳转到添加分类的页面执行的方法：saveUI
	 */
	public String saveUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/admin/category/add.jsp";
	}

	/*
	 * 保存分类的执行的方法：save
	 */
	public String save(HttpServletRequest req, HttpServletResponse resp) {

		try {
			// 接收参数
			String cname = req.getParameter("cname");
			// 封装数据
			Category category = new Category();
			category.setCid(UUIDUtils.getUUID());
			category.setCname(cname);
			// 调用业务层
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			categoryService.save(category);
			resp.sendRedirect(req.getContextPath()
					+ "/adminCategoryServlet?method=findAll");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 编辑分类的执行方法：edit
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp) {

		try {
			// 接受参数
			String cid = req.getParameter("cid");
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			Category category = categoryService.findById(cid);
			req.setAttribute("category", category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/category/edit.jsp";
	}

	/*
	 * 修改分类的执行方法：update
	 */
	public String update(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 接收数据
			Map<String, String[]> map = req.getParameterMap();
			// 封装数据
			Category category = new Category();
			BeanUtils.populate(category, map);
			// 处理数据
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			categoryService.update(category);
			resp.sendRedirect(req.getContextPath()
					+ "/adminCategoryServlet?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 删除分类：delete
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String cid = req.getParameter("cid");
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			categoryService.delete(cid);
			resp.sendRedirect(req.getContextPath()
					+ "/adminCategoryServlet?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
