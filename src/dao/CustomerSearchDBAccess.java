package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Customer;

public class CustomerSearchDBAccess {

    // 【修正点①】DB接続情報をクラスの「定数」として1箇所にまとめる（レビュー指摘：DRY違反の解消）
    private static final String URL = "jdbc:mysql://localhost:65534/KIDDA_LA"; // ポートを仕様通りの65543→65534に修正
    private static final String USER = "user1";
    private static final String PASSWORD = "pass1";

    /**
     * 【修正点①】データベースへの接続を確立するメソッド
     * 仕様に合わせて public（外部から呼べる形）にし、throws Exception に変更
     */
    public Connection createConnection() throws Exception {
        // JDBCドライバのロード（レビュー指摘：Class.forNameの追加）
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 【修正点①】データベースとの接続を閉じるメソッド
     */
    public void closeConnection(Connection con) throws Exception {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    /**
     * 電話番号を条件に顧客情報を検索
     */
    public ArrayList<Customer> searchCustomerByTel(String tel) throws Exception {
        ArrayList<Customer> list = new ArrayList<>();
        // ※列名は実DBの定義（大文字のCUSTID等）に合わせてエラーを防止
        String sql = "SELECT CUSTID, CUSTNAME, KANA, TEL, ADDRESS FROM CUSTOMER WHERE TEL = ?";

        // 接続の生成からクローズまでを確実に管理するため、メソッド内でcreateConnection()を呼ぶ設計に変更
        try (Connection connection = createConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, tel);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("CUSTID"),
                        rs.getString("CUSTNAME"),
                        rs.getString("KANA"),
                        rs.getString("TEL"),
                        rs.getString("ADDRESS")
                    );
                    list.add(customer);
                }
            }
        }
        return list;
    }

    /**
     * フリガナを条件に顧客情報を検索（部分一致）
     */
    public ArrayList<Customer> searchCustomerByKana(String kana) throws Exception {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT CUSTID, CUSTNAME, KANA, TEL, ADDRESS FROM CUSTOMER WHERE KANA LIKE ?";

        try (Connection connection = createConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            // 【修正点②】「前方一致(kana + "%")」から「部分一致("%" + kana + "%")」に修正
            pstmt.setString(1, "%" + kana + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("CUSTID"),
                        rs.getString("CUSTNAME"),
                        rs.getString("KANA"),
                        rs.getString("TEL"),
                        rs.getString("ADDRESS")
                    );
                    list.add(customer);
                }
            }
        }
        return list;
    }

    /**
     * 【修正点③】電話番号（完全一致）かつ フリガナ（部分一致）の両方で顧客情報を検索
     * 引数なしの全件検索から、仕様通りの絞り込み検索に作り直しました。
     */
    public ArrayList<Customer> searchCustomer(String tel, String kana) throws Exception {
        ArrayList<Customer> list = new ArrayList<>();
        // TELとKANAの両方をWHERE句の条件にする
        String sql = "SELECT CUSTID, CUSTNAME, KANA, TEL, ADDRESS FROM CUSTOMER WHERE TEL = ? AND KANA LIKE ?";

        try (Connection connection = createConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, tel);
            pstmt.setString(2, "%" + kana + "%"); // カナは部分一致
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("CUSTID"),
                        rs.getString("CUSTNAME"),
                        rs.getString("KANA"),
                        rs.getString("TEL"),
                        rs.getString("ADDRESS")
                    );
                    list.add(customer);
                }
            }
        }
        return list;
    }
}