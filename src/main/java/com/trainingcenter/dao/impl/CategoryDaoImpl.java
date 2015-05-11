package com.trainingcenter.dao.impl;

import com.trainingcenter.dao.CategoryDao;
import com.trainingcenter.model.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> getCategoriesFromDB() {
        Session session = sessionFactory.getCurrentSession();
        List<Category> categories = session.createCriteria(Category.class).list();
        return categories;
    }

    @Override
    public Category getCategoryFromDB(String categoryName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Category.class);
        Category category = (Category) criteria.add(Restrictions.eq("categoryName", categoryName)).uniqueResult();
        return category;
    }
}
