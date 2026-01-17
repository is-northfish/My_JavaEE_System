package com.example.bookmall.service;

import com.example.bookmall.dao.UserDao;
import com.example.bookmall.entity.User;
import com.example.bookmall.util.PasswordUtil;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User register(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }

        String cleanUsername = username.trim();
        User existing = userDao.findByUsername(cleanUsername);
        if (existing != null) {
            return null;
        }

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashWithSalt(password, salt);

        User user = new User();
        user.setUsername(cleanUsername);
        user.setPasswordHash(hash);
        user.setSalt(salt);
        user.setRole("USER");
        user.setStatus(1);

        long id = userDao.insert(user);
        if (id <= 0) {
            return null;
        }
        user.setId(id);
        return user;
    }

    public User login(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (password == null || password.isEmpty()) {
            return null;
        }

        User user = userDao.findByUsername(username.trim());
        if (user == null) {
            return null;
        }
        if (user.getStatus() != 1) {
            return null;
        }

        String expected = PasswordUtil.hashWithSalt(password, user.getSalt());
        if (!expected.equals(user.getPasswordHash())) {
            return null;
        }

        return user;
    }

    public List<User> listAllUsers() throws SQLException {
        return userDao.listAll();
    }

    public String disableUser(long id) throws SQLException {
        if (id <= 0) {
            return "用户不存在";
        }
        int affected = userDao.updateStatus(id, 0);
        if (affected == 0) {
            return "禁用失败";
        }
        return null;
    }

    public User getUserById(long id) throws SQLException {
        return userDao.findById(id);
    }

    public String addUser(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }
        if (password == null || password.isEmpty()) {
            return "密码不能为空";
        }

        String cleanUsername = username.trim();
        User existing = userDao.findByUsername(cleanUsername);
        if (existing != null) {
            return "用户名已存在";
        }

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashWithSalt(password, salt);

        User user = new User();
        user.setUsername(cleanUsername);
        user.setPasswordHash(hash);
        user.setSalt(salt);
        user.setRole("USER");
        user.setStatus(1);

        long id = userDao.insert(user);
        if (id <= 0) {
            return "新增用户失败";
        }
        return null;
    }

    public String updateUser(long id, String username, String password) throws SQLException {
        if (id <= 0) {
            return "用户不存在";
        }
        if (username == null || username.trim().isEmpty()) {
            return "用户名不能为空";
        }
        if (password == null || password.isEmpty()) {
            return "密码不能为空";
        }

        User existing = userDao.findById(id);
        if (existing == null) {
            return "用户不存在";
        }

        String cleanUsername = username.trim();
        User conflict = userDao.findByUsername(cleanUsername);
        if (conflict != null && conflict.getId() != id) {
            return "用户名已存在";
        }

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashWithSalt(password, salt);

        int affected = userDao.updateCredentials(id, cleanUsername, hash, salt);
        if (affected == 0) {
            return "更新用户失败";
        }
        return null;
    }
}
