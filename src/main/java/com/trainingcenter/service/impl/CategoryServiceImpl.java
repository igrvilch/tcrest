package com.trainingcenter.service.impl;

import com.trainingcenter.dao.CategoryDao;
import com.trainingcenter.model.Category;
import com.trainingcenter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    @Transactional
    public List<String> getCategoriesNames() {
        List<Category> categories = categoryDao.getCategoriesFromDB();
        List<String> categoriesNames = new ArrayList<String>();
        for (Category category : categories) {
            categoriesNames.add(category.getCategoryName());
        }
        return categoriesNames;
    }

    @Override
    @Transactional
    public Category getCategory(String categoryName) {
        Category category = categoryDao.getCategoryFromDB(categoryName);
        return category;
    }

	@Override
	@Transactional
	public List<Category> getCategories() {
		List<Category> categories = categoryDao.getCategoriesFromDB();
		return categories;
	}
}
