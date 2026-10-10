package test.joint;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Customer;
import model.OrderControlUtility;

public class JointTest_OrderControl01 {

    @Test
    @DisplayName("〔結合1-項番1〜5〕OrderControlUtilityとCustomerのメソッド連携確認")
    public void testJoin01_CustomerToArrayList() {
        // テスト用の Customer オブジェクトを作成
        Customer customer = new Customer(1, "青木まゆみ", "アオキマユミ", "09012345678", "東京都千代田区神田小川町1-1-1");
        
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        // OrderControlUtility#customerToArray の実行（内部で Customer の getter群が呼ばれる）
        String[][] resultTable = OrderControlUtility.customerToArray(customerList);

        // 検証
        assertNotNull(resultTable, "2次元配列が生成されること");
        assertEquals(1, resultTable.length, "配列の行数が1であること");

        // 各属性値（getCustId, getCustName, getKana, getAddress）が正しく取得されて配列に格納されているか確認
        assertEquals("1", resultTable[0][0], "〔項番2〕getCustId()が正しく使用できること");
        assertEquals("青木まゆみ", resultTable[0][1], "〔項番3〕getCustName()が正しく使用できること");
        assertEquals("アオキマユミ", resultTable[0][2], "〔項番4〕getKana()が正しく使用できること");
        assertEquals("09012345678", resultTable[0][3], "電話番号が正しく格納されていること");
        assertEquals("東京都千代田区神田小川町1-1-1", resultTable[0][4], "〔項番5〕getAddress()が正しく使用できること");
    }
}
