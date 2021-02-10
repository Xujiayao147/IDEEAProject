package io.gitee.xujiayao147.ideeaProject.service;

import io.gitee.xujiayao147.ideeaProject.bean.User;

public interface UserService {

	boolean insert(User user);

//	boolean update(User user);
//
//	boolean delete(User user);
//
//	public List<User> query();

	User queryLogin(User user);

	User queryRegister(User user);
}
