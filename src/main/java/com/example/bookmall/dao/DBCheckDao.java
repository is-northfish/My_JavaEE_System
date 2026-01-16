package com.example.bookmall.dao;

import com.example.bookmall.entity.DBStatus;
import com.example.bookmall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCheckDao {
    public DBStatus fetchStatus() throws SQLException {
        String sql = "SELECT NOW() AS current_time_value, DATABASE() AS db_name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                DBStatus status = new DBStatus();
                status.setCurrentTime(rs.getString("current_time_value"));
                status.setDbName(rs.getString("db_name"));
                return status;
            }
            return null;
        }
    }
}
