package com.trainingcenter.dao;

import com.trainingcenter.model.Category;

import java.util.List;


public interface CategoryDao {

    public List<Category> getCategoriesFromDB();

    public Category getCategoryFromDB(String categoryName);
}
