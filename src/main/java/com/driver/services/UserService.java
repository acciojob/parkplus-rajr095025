package com.driver.services;


import com.driver.model.User;

public interface UserService {

	void deleteUser(Integer userId) throws Exception;
	User updatePassword(Integer userId, String password) throws Exception;
    void register(String name, String phoneNumber, String password);
}
