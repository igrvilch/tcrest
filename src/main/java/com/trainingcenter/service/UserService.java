package com.trainingcenter.service;

import com.trainingcenter.model.User;


public interface UserService {

    public User getUser(String userLogin);

    public User getUserById(int userId);

}
