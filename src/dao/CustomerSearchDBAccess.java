package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Customer;

public class CustomerSearchDBAccess {
    private Connection connection;

    /**
     * コンストラクタ
     * @param connection データベース接続オブジェクト
     */
    public CustomerSearchDBAccess(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * 「createConnection()」
     */
    private Connection createConnection() throws Exception {
        // 仕様書の概要に書かれている接続情報を設定
        String url = "jdbc:mysql://localhost:65543/KIDDA_LA";
        String user = "user1";
        String password = "pass1";
        
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 「closeConnection()」
     */
    private void closeConnection(Connection con) throws Exception {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }

    /**
     * 電話番号を条件に顧客情報を検索
     *
     * @param tel 検索する電話番号
     * @return 検索結果のCustomerオブジェクトのリスト
     * @throws SQLException データベースエラーが発生した場合
     */
    public ArrayList<Customer> searchCustomerByTel(String tel) throws SQLException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT cust_id, cust_name, kana, tel, address FROM CUSTOMER WHERE tel = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tel);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("cust_id"),
                        rs.getString("cust_name"),
                        rs.getString("kana"),
                        rs.getString("tel"),
                        rs.getString("address")
                    );
                    list.add(customer);
                }
            }
        }
        return list;
    }

    /**
     * フリガナを条件に顧客情報を検索（前方一致）
     *
     * @param kana 検索するフリガナ
     * @return 検索結果のCustomerオブジェクトのリスト
     * @throws SQLException データベースエラーが発生した場合
     */
    public ArrayList<Customer> searchCustomerByKana(String kana) throws SQLException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT cust_id, cust_name, kana, tel, address FROM CUSTOMER WHERE kana LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // 前方一致にするため、後ろに % を結合します
            pstmt.setString(1, kana + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("cust_id"),
                        rs.getString("cust_name"),
                        rs.getString("kana"),
                        rs.getString("tel"),
                        rs.getString("address")
                    );
                    list.add(customer);
                }
            }
        }
        return list;
    }

    /**
     * 顧客情報を全件検索
     *
     * @return すべての顧客情報のCustomerオブジェクトのリスト
     * @throws SQLException データベースエラーが発生した場合
     */
    public ArrayList<Customer> searchCustomer() throws SQLException {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "SELECT cust_id, cust_name, kana, tel, address FROM CUSTOMER";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getInt("cust_id"),
                    rs.getString("cust_name"),
                    rs.getString("kana"),
                    rs.getString("tel"),
                    rs.getString("address")
                );
                list.add(customer);
            }
        }
        return list;
    }
}
