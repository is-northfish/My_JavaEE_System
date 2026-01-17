package com.example.bookmall.service;

import com.example.bookmall.dao.BookDao;
import com.example.bookmall.dao.CategoryDao;
import com.example.bookmall.entity.Category;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();
    private final BookDao bookDao = new BookDao();

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

    public String deleteCategory(long id) throws SQLException {
        int count = bookDao.countByCategory(id);
        if (count > 0) {
            return "分类下存在图书，无法删除";
        }
        int affected = categoryDao.deleteById(id);
        if (affected == 0) {
            return "删除失败";
        }
        return null;
    }

    public Category getCategory(long id) throws SQLException {
        return categoryDao.findById(id);
    }

    public String updateCategory(long id, String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            return "分类名称不能为空";
        }
        Category category = new Category();
        category.setId(id);
        category.setName(name.trim());
        int affected = categoryDao.update(category);
        if (affected == 0) {
            return "更新失败";
        }
        return null;
    }
}
