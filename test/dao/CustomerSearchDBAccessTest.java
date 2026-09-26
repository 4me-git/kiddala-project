package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Customer;

public class CustomerSearchDBAccessTest {

    private CustomerSearchDBAccess dbAccess;

    @BeforeEach
    public void setUp() {
        dbAccess = new CustomerSearchDBAccess();
    }

    // -----------------------------------------------------------------
    // 1. DB接続確認テスト
    // -----------------------------------------------------------------
    @Test
    @DisplayName("DB接続テスト：createConnectionが正常にConnectionオブジェクトを返すか")
    public void testCreateConnection() {
        try (Connection con = dbAccess.createConnection()) {
            assertNotNull(con, "データベース接続オブジェクトがnullであってはなりません");
            assertFalse(con.isClosed(), "データベース接続が開いた状態である必要があります");
        } catch (Exception e) {
            fail("DB接続時に例外が発生しました: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------
    // 2. searchCustomerByTel の条件網羅テスト
    // -----------------------------------------------------------------
    @Test
    @DisplayName("電話番号検索：該当データあり（条件: True）")
    public void testSearchCustomerByTel_Hit() throws Exception {
        // DBに存在する電話番号を指定（環境に合わせて調整してください）
        String tel = "09011112222"; 
        ArrayList<Customer>  list = dbAccess.searchCustomerByTel(tel);

        assertNotNull(list);
        // 該当データが存在することを確認
        if (!list.isEmpty()) {
            assertEquals(tel, list.get(0).getTel());
        }
    }

    @Test
    @DisplayName("電話番号検索：該当データなし（条件: False）")
    public void testSearchCustomerByTel_NoHit() throws Exception {
        // DBに存在しない電話番号を指定
        String tel = "00000000000"; 
        ArrayList list = dbAccess.searchCustomerByTel(tel);

        assertNotNull(list);
        assertEquals(0, list.size(), "存在しない電話番号の場合は0件で返る必要があります");
    }

    // -----------------------------------------------------------------
    // 3. searchCustomerByKana の条件網羅テスト
    // -----------------------------------------------------------------
    @Test
    @DisplayName("カナ検索：該当データあり（条件: True）")
    public void testSearchCustomerByKana_Hit() throws Exception {
        // DBに存在するフリガナの一部（LIKE検索）を指定
        String kana = "ヤマダ"; 
        ArrayList<Customer> list = dbAccess.searchCustomerByKana(kana);

        assertNotNull(list);
        if (!list.isEmpty()) {
            assertTrue(list.get(0).getKana().contains(kana));
        }
    }

    @Test
    @DisplayName("カナ検索：該当データなし（条件: False）")
    public void testSearchCustomerByKana_NoHit() throws Exception {
        // DBに存在しないフリガナを指定
        String kana = "ンンンンン"; 
        ArrayList list = dbAccess.searchCustomerByKana(kana);

        assertNotNull(list);
        assertEquals(0, list.size(), "存在しないフリガナの場合は0件で返る必要があります");
    }

    // -----------------------------------------------------------------
    // 4. searchCustomer (tel, kana) の複数条件網羅テスト（MCC）
    // -----------------------------------------------------------------
    @Test
    @DisplayName("複合検索 [True, True]：tel一致 ＆ kana一致（両方ヒット）")
    public void testSearchCustomer_BothHit() throws Exception {
        String tel = "09011112222";
        String kana = "ヤマダ";
        ArrayList<Customer>  list = dbAccess.searchCustomer(tel, kana);

        assertNotNull(list);
        // 両方の条件を満たすデータのみが取得できているか確認
        for (Customer c : list) {
            assertEquals(tel, c.getTel());
            assertTrue(c.getKana().contains(kana));
        }
    }

    @Test
    @DisplayName("複合検索 [True, False]：tel一致 ＆ kana不一致（ヒットなし）")
    public void testSearchCustomer_TelHit_KanaNoHit() throws Exception {
        String tel = "09011112222";
        String kana = "存在しないカナ";
        ArrayList list = dbAccess.searchCustomer(tel, kana);

        assertNotNull(list);
        assertEquals(0, list.size(), "AND条件のため、片方が不一致なら0件になる必要があります");
    }

    @Test
    @DisplayName("複合検索 [False, True]：tel不一致 ＆ kana一致（ヒットなし）")
    public void testSearchCustomer_TelNoHit_KanaHit() throws Exception {
        String tel = "00000000000";
        String kana = "ヤマダ";
        ArrayList list = dbAccess.searchCustomer(tel, kana);

        assertNotNull(list);
        assertEquals(0, list.size(), "AND条件のため、片方が不一致なら0件になる必要があります");
    }

    @Test
    @DisplayName("複合検索 [False, False]：tel不一致 ＆ kana不一致（ヒットなし）")
    public void testSearchCustomer_BothNoHit() throws Exception {
        String tel = "00000000000";
        String kana = "存在しないカナ";
        ArrayList list = dbAccess.searchCustomer(tel, kana);

        assertNotNull(list);
        assertEquals(0, list.size());
    }
}
