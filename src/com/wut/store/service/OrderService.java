package com.wut.store.service;

import java.sql.SQLException;
import java.util.List;

import com.wut.store.domain.Order;
import com.wut.store.domain.OrderItem;
import com.wut.store.domain.PageBean;

public interface OrderService {

	void save(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws SQLException, Exception;

	Order findByOid(String oid) throws SQLException, Exception;

	void update(Order order) throws SQLException;

	List<Order> findAll() throws SQLException;

	List<Order> findByState(int pstate) throws SQLException;

	List<OrderItem> showDetail(String oid) throws SQLException, Exception;

}
