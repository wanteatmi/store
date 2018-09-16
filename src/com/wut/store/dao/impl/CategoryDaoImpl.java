package com.wut.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wut.store.dao.CategoryDao;
import com.wut.store.domain.Category;
import com.wut.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql="select * from category ";
		List<Category> list=queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}

	@Override
	public void save(Category category) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values (?,?)";
		queryRunner.update(sql,category.getCid(),category.getCname());
	}

	@Override
	public Category findById(String cid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid =?";
		Category category = queryRunner.query(sql, new BeanHandler<Category>(Category.class),cid);
		return category;
	}

	@Override
	public void update(Category category) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname=? where cid= ?";
		queryRunner.update(sql,category.getCname(),category.getCid());
		
	}

	@Override
	public void delete(String cid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update product set cid =null where cid = ?";
		queryRunner.update(sql,cid);
		sql = "delete from category where cid =?";
		queryRunner.update(sql,cid);
	}

}
