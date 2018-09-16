package com.wut.store.dao;

import java.sql.SQLException;

import com.wut.store.domain.User;

public interface UserDao {

	User findByUsername(String username) throws SQLException;

	void save(User user) throws SQLException;

	User findByCode(String code) throws SQLException;

	void update(User exitUser) throws SQLException;

	User login(User user) throws SQLException;
	
}
