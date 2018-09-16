package com.wut.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.wut.store.domain.Order;
import com.wut.store.domain.OrderItem;

public interface OrderDao {


	void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

	void saveOrder(Connection conn, Order order) throws SQLException;

	List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws SQLException, Exception;

	Integer findCountByUid(String uid) throws SQLException;

	Order findByOid(String oid) throws  Exception;

	void update(Order order) throws SQLException;

	List<Order> findAll() throws SQLException;

	List<Order> findByState(int pstate) throws SQLException;

	List<OrderItem> showDetail(String oid) throws SQLException, Exception;


}
