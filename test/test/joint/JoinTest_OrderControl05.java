package test.joint;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Customer;
// OrderControlUtilityクラスがどのパッケージにあるか確認・importしてください
// 例: import util.OrderControlUtility; または import model.OrderControlUtility;
import model.OrderControlUtility; 

public class JoinTest_OrderControl05 {

    @Test
    @DisplayName("〔結合5-項番1,2〕customerToArray()によるCustomerリストから2次元配列への変換動作確認")
    public void testJoin05_CustomerToArray() {
        ArrayList<Customer> list = new ArrayList<>();
        list.add(new Customer(1, "青木まゆみ", "アオキマユミ", "09012345678", "東京都千代田区神田小川町1-1-1"));

        // OrderControlUtilityの変換メソッド呼び出し
        String[][] result = OrderControlUtility.customerToArray(list);

        assertNotNull(result, "配列が返ってくること");
        assertEquals(1, result.length, "1件分の配列になっていること");
        assertEquals("1", result[0][0]);
        assertEquals("青木まゆみ", result[0][1]);
        assertEquals("アオキマユミ", result[0][2]);
        assertEquals("09012345678", result[0][3]);
        assertEquals("東京都千代田区神田小川町1-1-1", result[0][4]);
    }
}
