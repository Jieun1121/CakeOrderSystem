package cakeapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/CakeOrderDB"; 
    private static final String USER = "root";
    private static final String PASSWORD = ""; // 본인이 설정한 비밀번호 입력

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
