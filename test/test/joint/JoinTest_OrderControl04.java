package test.joint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import action.CustomerSearchAction;

public class JoinTest_OrderControl04 {

    private CustomerSearchAction action;

    @BeforeEach
    public void setUp() {
        action = new CustomerSearchAction();
    }

    @Test
    @DisplayName("〔結合4-項番1〕ActionがCustomerオブジェクトの各属性値を正しく参照できていること")
    public void testJoin04_CustomerAttributes() throws Exception {
        // 伊藤英恵さん (ID:12, KANA:イトウハナエ, TEL:09011112222, ADDRESS:東京都千代田区神田小川町1-4-1)
        String[] input = { "09011112222", "" };
        String[][] result = action.execute(input);

        assertNotNull(result, "検索結果が取得できること");
        // [0]ID, [1]氏名, [2]カナ, [3]TEL, [4]住所
        assertEquals("12", result[0][0], "顧客IDが正しく変換・格納されていること");
        assertEquals("伊藤英恵", result[0][1], "顧客名が正しく変換・格納されていること");
        assertEquals("イトウハナエ", result[0][2], "カナが正しく変換・格納されていること");
        assertEquals("09011112222", result[0][3], "TELが正しく変換・格納されていること");
        assertEquals("東京都千代田区神田小川町1-4-1", result[0][4], "住所が正しく変換・格納されていること");
    }
}