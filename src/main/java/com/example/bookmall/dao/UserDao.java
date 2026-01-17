package com.example.bookmall.dao;

import com.example.bookmall.entity.User;
import com.example.bookmall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password_hash, salt, role, status, created_at FROM `user` WHERE username = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        }
    }

    public User findById(long id) throws SQLException {
        String sql = "SELECT id, username, password_hash, salt, role, status, created_at FROM `user` WHERE id = ?";

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

    public long insert(User user) throws SQLException {
        String sql = "INSERT INTO `user` (username, password_hash, salt, role, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getSalt());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getStatus());

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

    public List<User> listAll() throws SQLException {
        String sql = "SELECT id, username, role, status, created_at FROM `user` ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        }

        return users;
    }

    public int updateStatus(long id, int status) throws SQLException {
        String sql = "UPDATE `user` SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setLong(2, id);
            return ps.executeUpdate();
        }
    }

    public int updateCredentials(long id, String username, String passwordHash, String salt) throws SQLException {
        String sql = "UPDATE `user` SET username = ?, password_hash = ?, salt = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, salt);
            ps.setLong(4, id);
            return ps.executeUpdate();
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setSalt(rs.getString("salt"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getInt("status"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}
