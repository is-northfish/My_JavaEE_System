package com.example.bookmall.service;

import com.example.bookmall.dao.CategoryDao;
import com.example.bookmall.entity.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    public List<Category> listAll() throws SQLException {
        return categoryDao.listAll();
    }

    public long addCategory(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            return 0;
        }
        Category category = new Category();
        category.setName(name.trim());
        return categoryDao.insert(category);
    }
}
