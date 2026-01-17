package com.example.bookmall.service;

import com.example.bookmall.dao.BookDao;
import com.example.bookmall.entity.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDao bookDao = new BookDao();

    public List<Book> listByCategory(long categoryId) throws SQLException {
        return bookDao.listByCategory(categoryId);
    }

    public Book findById(long id) throws SQLException {
        return bookDao.findById(id);
    }
}
