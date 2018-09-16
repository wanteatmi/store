package com.wut.store.service.impl;

import java.sql.SQLException;

import com.wut.store.dao.UserDao;
import com.wut.store.dao.impl.UserDaoImpl;
import com.wut.store.domain.User;
import com.wut.store.service.UserService;
import com.wut.store.utils.BeanFactory;
import com.wut.store.utils.MailUtils;
import com.wut.store.utils.UUIDUtils;

public class UserServiceImpl implements UserService {

	@Override
	public User findByUsername(String username) throws SQLException  {
		// TODO Auto-generated method stub
		//UserDao userDao=new UserDaoImpl();
		UserDao userDao=(UserDao) BeanFactory.getBean("userDao");
		
		return userDao.findByUsername(username);
	}

	@Override
	public void save(User user) throws SQLException {
		// TODO Auto-generated method stub
		String code=UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		user.setState(1);
		UserDao userDao=(UserDao) BeanFactory.getBean("userDao");
		userDao.save(user);
		MailUtils.sendMail(user.getEmail(), code);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		// TODO Auto-generated method stub
		UserDao userDao=(UserDao) BeanFactory.getBean("userDao");
		return userDao.findByCode(code);
	}

	@Override
	public void update(User exitUser) throws SQLException {
		// TODO Auto-generated method stub
		UserDao userDao=(UserDao) BeanFactory.getBean("userDao");
		userDao.update(exitUser);
	}

	@Override
	public User login(User user) throws SQLException {
		// TODO Auto-generated method stub
		UserDao userDao=(UserDao) BeanFactory.getBean("userDao");
		return userDao.login(user);
	}
	
}
