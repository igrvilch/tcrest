package com.trainingcenter.dao;

import com.trainingcenter.model.User;

public interface UserDao {

    public User getUserFromDB(String userLogin);

    public User getUserFromDBById(int userId);
}
