package com.trainingcenter.dao.impl;

import com.trainingcenter.dao.UserDao;
import com.trainingcenter.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUserFromDB(String userLogin) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(session + "session111");
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("userLogin", userLogin)).uniqueResult();
        return user;
    }

    @Override
    public User getUserFromDBById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(session + "session111");
        Criteria criteria = session.createCriteria(User.class);
        User user = (User) criteria.add(Restrictions.eq("userId", userId)).uniqueResult();
        return user;
    }


}
