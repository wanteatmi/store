package com.wut.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.wut.store.dao.UserDao;
import com.wut.store.domain.User;
import com.wut.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByUsername(String username) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from user where username=?";
		User user=queryRunner.query(sql, new BeanHandler<User>(User.class), username);
		return user;
	}

	@Override
	public void save(User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="insert into user values (?,?,?,?,?,?,?,?,?,?) ";
		Object[]parms={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		queryRunner.update(sql,parms);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from user where code=?";
		User exitUser=queryRunner.query(sql, new BeanHandler<User>(User.class),code);
		return exitUser;
	}

	@Override
	public void update(User exitUser) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		Object[]parms={exitUser.getUsername(),exitUser.getPassword(),exitUser.getName(),exitUser.getEmail(),exitUser.getTelephone(),exitUser.getBirthday(),exitUser.getSex(),exitUser.getState(),exitUser.getCode(),exitUser.getUid()};
		queryRunner.update(sql, parms);
	}

	@Override
	public User login(User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from User where username=? and password=? and state=?";
		User exitUser=queryRunner.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword(),2);
		return exitUser;
	}

}
