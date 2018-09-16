package com.wut.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.wut.store.dao.ProductDao;
import com.wut.store.dao.impl.ProductDaoImpl;
import com.wut.store.domain.PageBean;
import com.wut.store.domain.Product;
import com.wut.store.service.ProductService;
import com.wut.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findByNew() throws SQLException {
	//	ProductDao productDao=new ProductDaoImpl();
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		
		return productDao.findByNew();
	}

	@Override
	public List<Product> findByHot() throws SQLException {
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByHot();
	}

	@Override
	public PageBean<Product> findByPageCid(String cid, Integer currPage) throws SQLException {
		// TODO Auto-generated method stub
		PageBean<Product> pageBean=new PageBean<Product>();
		//���ò���
		//���õ�ǰҳ��
		pageBean.setCurrPage(currPage);
		//����ÿҳ��ʾ�ļ�¼��
		Integer pageSize=12;
		pageBean.setPageSize(pageSize);
		//�����ܼ�¼��
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		Integer totalCount=productDao.findCountByCid(cid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//����ÿҳ��ʾ�����ݵļ���
		int begin=(currPage-1)*pageSize;
		List<Product> list=productDao.findPageByCid(cid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByPid(pid);
	}

	@Override
	public PageBean<Product> findByPage(Integer currPage) throws SQLException {
		// TODO Auto-generated method stub
		PageBean<Product> pageBean=new PageBean<Product>();
		//���ò���
		//���õ�ǰҳ��
		pageBean.setCurrPage(currPage);
		//����ÿҳ��ʾ�ļ�¼��
		Integer pageSize=9;
		pageBean.setPageSize(pageSize);
		//�����ܼ�¼��
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		Integer totalCount=productDao.findCount();
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//����ÿҳ��ʾ�����ݵļ���
		int begin=(currPage-1)*pageSize;
		List<Product> list=productDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
		
		
	}

	@Override
	public void save(Product product) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		productDao.save(product);
	}

	@Override
	public void update(Product product) throws SQLException {
		// TODO Auto-generated method stub
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		productDao.update(product);
	}

	@Override
	public List<Product> findByPushDown() throws SQLException {
		ProductDao productDao=(ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByPushDown();
	}

}
