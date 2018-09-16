package com.wut.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.wut.store.dao.CategoryDao;
import com.wut.store.dao.impl.CategoryDaoImpl;
import com.wut.store.domain.Category;
import com.wut.store.service.CategoryService;
import com.wut.store.utils.BeanFactory;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() throws SQLException {
		// TODO Auto-generated method stub
		/*CategoryDao categoryDao=new CategoryDaoImpl();
		return categoryDao.findAll();*/
		CacheManager cacheManager=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//
		Cache cache=cacheManager.getCache("categoryCache");
		//判断
		Element element=cache.get("list");
		if(element==null){
			//System.out.println("缓存中没有数据，不查询数据库");
		//	CategoryDao categoryDao=new CategoryDaoImpl();
			CategoryDao categoryDao=(CategoryDao) BeanFactory.getBean("categoryDao");
			List<Category> list=categoryDao.findAll();
			element=new Element("list",list);
			cache.put(element);
			return list;
		}else{
			//System.out.println("缓存中没有数据，查询数据库");
			List<Category> list=(List<Category>) element.getObjectValue();
			return list;
		}
		
	}

	@Override
	public void save(Category category) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.save(category);
		//清空缓存
		CacheManager cacheManager=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//从配置文件中获取名称为categoryCache缓存区
		Cache cache=cacheManager.getCache("categoryCache");
		//从缓存区移除
		cache.remove("list");
	}

	@Override
	public Category findById(String cid) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		return categoryDao.findById(cid);
	}

	@Override
	public void update(Category category) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.update(category);
		//清空缓存
				CacheManager cacheManager=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				//从配置文件中获取名称为categoryCache缓存区
				Cache cache=cacheManager.getCache("categoryCache");
				//从缓存区移除
				cache.remove("list");
	}

	@Override
	public void delete(String cid) throws SQLException {
		// TODO Auto-generated method stub
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.delete(cid);
		//清空缓存
				CacheManager cacheManager=CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				//从配置文件中获取名称为categoryCache缓存区
				Cache cache=cacheManager.getCache("categoryCache");
				//从缓存区移除
				cache.remove("list");
	}

}
