package com.example.bookmall.service;

import com.example.bookmall.dao.BookDao;
import com.example.bookmall.entity.Book;

import java.math.BigDecimal;
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

    public List<Book> listAll() throws SQLException {
        return bookDao.listAll();
    }

    public String addBook(long categoryId, String name, String priceText, String stockText) throws SQLException {
        if (categoryId <= 0) {
            return "分类不合法";
        }
        if (name == null || name.trim().isEmpty()) {
            return "书名不能为空";
        }
        if (priceText == null || priceText.trim().isEmpty()) {
            return "价格不能为空";
        }
        if (stockText == null || stockText.trim().isEmpty()) {
            return "库存不能为空";
        }

        BigDecimal price;
        int stock;
        try {
            price = new BigDecimal(priceText.trim());
            stock = Integer.parseInt(stockText.trim());
        } catch (NumberFormatException e) {
            return "价格或库存格式不正确";
        }
        if (stock < 0) {
            return "库存不能为负数";
        }

        Book book = new Book();
        book.setCategoryId(categoryId);
        book.setName(name.trim());
        book.setPrice(price);
        book.setStock(stock);

        long id = bookDao.insert(book);
        if (id <= 0) {
            return "新增图书失败";
        }
        return null;
    }

    public String updateBook(long id, long categoryId, String name, String priceText, String stockText) throws SQLException {
        if (id <= 0) {
            return "图书不存在";
        }
        if (categoryId <= 0) {
            return "分类不合法";
        }
        if (name == null || name.trim().isEmpty()) {
            return "书名不能为空";
        }
        if (priceText == null || priceText.trim().isEmpty()) {
            return "价格不能为空";
        }
        if (stockText == null || stockText.trim().isEmpty()) {
            return "库存不能为空";
        }

        BigDecimal price;
        int stock;
        try {
            price = new BigDecimal(priceText.trim());
            stock = Integer.parseInt(stockText.trim());
        } catch (NumberFormatException e) {
            return "价格或库存格式不正确";
        }
        if (stock < 0) {
            return "库存不能为负数";
        }

        Book book = new Book();
        book.setId(id);
        book.setCategoryId(categoryId);
        book.setName(name.trim());
        book.setPrice(price);
        book.setStock(stock);

        int affected = bookDao.update(book);
        if (affected == 0) {
            return "更新图书失败";
        }
        return null;
    }

    public String deleteBook(long id) throws SQLException {
        if (id <= 0) {
            return "图书不存在";
        }
        int affected = bookDao.deleteById(id);
        if (affected == 0) {
            return "删除失败";
        }
        return null;
    }
}
