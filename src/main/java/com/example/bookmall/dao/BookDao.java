package com.example.bookmall.dao;

import com.example.bookmall.entity.Book;
import com.example.bookmall.util.DBUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    public Book findById(long id) throws SQLException {
        String sql = "SELECT id, category_id, name, price, stock FROM book WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        }
    }

    public List<Book> listAll() throws SQLException {
        String sql = "SELECT id, category_id, name, price, stock FROM book ORDER BY id";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        }

        return books;
    }

    public long insert(Book book) throws SQLException {
        String sql = "INSERT INTO book (category_id, name, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, book.getCategoryId());
            ps.setString(2, book.getName());
            ps.setBigDecimal(3, book.getPrice());
            ps.setInt(4, book.getStock());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return 0;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                return 0;
            }
        }
    }

    public int update(Book book) throws SQLException {
        String sql = "UPDATE book SET category_id = ?, name = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, book.getCategoryId());
            ps.setString(2, book.getName());
            ps.setBigDecimal(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setLong(5, book.getId());
            return ps.executeUpdate();
        }
    }

    public int deleteById(long id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }

    public List<Book> listByCategory(long categoryId) throws SQLException {
        String sql = "SELECT id, category_id, name, price, stock FROM book WHERE category_id = ? ORDER BY id";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapRow(rs));
                }
            }
        }

        return books;
    }

    public int countByCategory(long categoryId) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM book WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cnt");
                }
                return 0;
            }
        }
    }

    private Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setCategoryId(rs.getLong("category_id"));
        book.setName(rs.getString("name"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setStock(rs.getInt("stock"));
        return book;
    }
}
