package test.joint;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.CustomerSearchDBAccess;
import model.Customer;

public class JoinTest_OrderControl02 {

    private CustomerSearchDBAccess dbAccess;

    @BeforeEach
    public void setUp() {
        dbAccess = new CustomerSearchDBAccess();
    }

    @Test
    @DisplayName("〔結合2-項番1〕電話番号検索とCustomerの連携確認")
    public void testJoin02_Tel() throws Exception {
        String tel = "09011112222";
        ArrayList<Customer> list = dbAccess.searchCustomerByTel(tel);

        assertNotNull(list, "リストが取得できること");
        assertFalse(list.isEmpty(), "1件以上取得できること");
        
        Customer c = list.get(0);
        assertEquals(tel, c.getTel(), "Customerインスタンスに正しい電話番号が保持されていること");
    }

    @Test
    @DisplayName("〔結合2-項番2〕カナ検索とCustomerの連携確認")
    public void testJoin02_Kana() throws Exception {
        String kana = "イトウ";
        ArrayList<Customer> list = dbAccess.searchCustomerByKana(kana);

        assertNotNull(list, "リストが取得できること");
        assertFalse(list.isEmpty(), "1件以上取得できること");
        assertTrue(list.get(0).getKana().contains(kana), "Customerインスタンスに正しいカナが保持されていること");
    }

    @Test
    @DisplayName("〔結合2-項番3〕複合検索とCustomerの連携確認")
    public void testJoin02_Both() throws Exception {
        String tel = "09011112222";
        String kana = "イトウ";
        ArrayList<Customer> list = dbAccess.searchCustomer(tel, kana);

        assertNotNull(list, "リストが取得できること");
        assertFalse(list.isEmpty(), "1件以上取得できること");
        assertEquals(tel, list.get(0).getTel());
        assertTrue(list.get(0).getKana().contains(kana));
    }
}
