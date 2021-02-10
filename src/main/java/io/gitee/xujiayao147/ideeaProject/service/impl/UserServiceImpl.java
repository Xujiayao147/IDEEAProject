package io.gitee.xujiayao147.ideeaProject.service.impl;

import io.gitee.xujiayao147.ideeaProject.bean.User;
import io.gitee.xujiayao147.ideeaProject.dao.UserDao;
import io.gitee.xujiayao147.ideeaProject.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao userDao = new UserDao();

	@Override
	public boolean insert(User user) {
		return userDao.insert(user);
	}

//	@Override
//	public boolean delete(User user) {
//		return userDao.delete(user);
//	}
//
//	@Override
//	public boolean update(User user) {
//		return userDao.update(user);
//	}
//
//	@Override
//	public List<User> query() {
//		return userDao.query();
//	}

	@Override
	public User queryLogin(User user) {
		return userDao.queryLogin(user);
	}

	@Override
	public User queryRegister(User user) {
		return userDao.queryRegister(user);
	}
}
