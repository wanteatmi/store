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
 * ��̨��������servlet
 */
public class adminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * ��̨��ѯ���з���ķ�����findAll
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// ����ҵ���
			CategoryService categoryService = (CategoryService) BeanFactory
					.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			// ����req���У����ҳ����ת
			req.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";
	}

	/**
	 * ��ת����ӷ����ҳ��ִ�еķ�����saveUI
	 */
	public String saveUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/admin/category/add.jsp";
	}

	/*
	 * ��������ִ�еķ�����save
	 */
	public String save(HttpServletRequest req, HttpServletResponse resp) {

		try {
			// ���ղ���
			String cname = req.getParameter("cname");
			// ��װ����
			Category category = new Category();
			category.setCid(UUIDUtils.getUUID());
			category.setCname(cname);
			// ����ҵ���
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
	 * �༭�����ִ�з�����edit
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp) {

		try {
			// ���ܲ���
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
	 * �޸ķ����ִ�з�����update
	 */
	public String update(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// ��������
			Map<String, String[]> map = req.getParameterMap();
			// ��װ����
			Category category = new Category();
			BeanUtils.populate(category, map);
			// ��������
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
	 * ɾ�����ࣺdelete
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
