package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class OrderControlUtility {/**
     * データベースへの接続を確立します。
     */
    public Connection createConnection() throws Exception {
        String url = "jdbc:mysql://localhost:65543/KIDDA_LA";
        String user = "user1";
        String password = "pass1";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * データベースとの接続を閉じます。
     */
    public void closeConnection(Connection con) throws Exception {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

}
