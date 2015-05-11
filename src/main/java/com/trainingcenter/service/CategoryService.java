package com.trainingcenter.service;

import com.trainingcenter.model.Category;

import java.util.List;

public interface CategoryService {

    public List<String> getCategoriesNames();

    public Category getCategory(String courseCategory);
    
    public List<Category> getCategories();
}
