package com.driver.services.impl;

import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) throws Exception {
        User user;
        try {
            user = userRepository4.findById(userId).get();
        }
        catch (Exception e){
            throw new Exception("user id is not valid");
        }
        userRepository4.delete(user);
    }

    @Override
    public User updatePassword(Integer userId, String password) throws Exception {
        User user;
        try {
            user = userRepository4.findById(userId).get();
        }
        catch (Exception e){
            throw new Exception("user id is not valid");
        }
        user.setPassword(password);
        userRepository4.save(user);
        return user;
    }

    @Override
    public void register(String name, String phoneNumber, String password) {
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        userRepository4.save(user);
    }
}