package com.trainingcenter.service.impl;

import com.trainingcenter.dao.UserDao;
import com.trainingcenter.model.User;
import com.trainingcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional
    public User getUser(String userLogin) {
        return userDao.getUserFromDB(userLogin);
    }

    @Override
    @Transactional
    public User getUserById(int userId) {
        return userDao.getUserFromDBById(userId);
    }
}