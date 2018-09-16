package com.wut.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.wut.store.dao.OrderDao;
import com.wut.store.domain.Order;
import com.wut.store.domain.OrderItem;
import com.wut.store.domain.PageBean;
import com.wut.store.service.OrderService;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) {
		// TODO Auto-generated method stub
		Connection conn=null;
		try{
			//��������
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			//ִ�б���
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
			orderDao.saveOrder(conn,order);
			//ѭ�����涩����
			for(OrderItem orderItem:order.getOrderItems()){
				orderDao.saveOrderItem(conn,orderItem);
			}
			
			//�ύ����
			DbUtils.commitAndCloseQuietly(conn);
		}catch(Exception e){
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
	}

	@Override
	public PageBean<Order> findByUid(String uid, Integer currPage) throws Exception {
		// TODO Auto-generated method stub
		PageBean<Order> pageBean = new PageBean<Order>();
		//���õ�ǰ��ҳ��
		pageBean.setCurrPage(currPage);
		//����ÿҳ��ʾ�ļ�¼��
		Integer pageSize = 5;
		pageBean.setPageSize(pageSize);
		//�����ܼ�¼����
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		Integer totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		//������ҳ��
		double tc = totalCount;
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//����ÿҳ��ʾ�����ݵļ���
		int begin = (currPage - 1)*pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.findByOid(oid);
	}

	@Override
	public void update(Order order) throws SQLException {
		// TODO Auto-generated method stub
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		orderDao.update(order);
	}

	@Override
	public List<Order> findAll() throws SQLException {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.findAll();
	}

	@Override
	public List<Order> findByState(int pstate) throws SQLException {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.findByState(pstate);
	}

	@Override
	public List<OrderItem> showDetail(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.showDetail(oid);
	}
	
}
