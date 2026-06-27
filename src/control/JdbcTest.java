package control;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {
    public static void main(String[] args) {
        // 接続先情報
        String url = "jdbc:mysql://localhost:3306/KIDDA_LA";
        String user = "root";
        String pass = "mysql818"; 

        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("★接続成功しました！★");
            con.close();
        } catch (Exception e) {
            System.out.println("接続に失敗しました...");
            e.printStackTrace();
        }
    }
}