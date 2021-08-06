package com.bank.service;

import java.util.List;

import com.bank.dao.UserDAO;
import com.bank.model.User;

interface UserService {
	
	UserDAO getUserDAO(User user);
	UserDAO getUserDAOByName(String name);
	List<UserDAO> getAllUsers();

	void deleteUser(Long id);

}
