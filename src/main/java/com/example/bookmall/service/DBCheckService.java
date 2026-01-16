package com.example.bookmall.service;

import com.example.bookmall.dao.DBCheckDao;
import com.example.bookmall.entity.DBStatus;

import java.sql.SQLException;

public class DBCheckService {
    private final DBCheckDao dbCheckDao = new DBCheckDao();

    public DBStatus getStatus() throws SQLException {
        return dbCheckDao.fetchStatus();
    }
}
