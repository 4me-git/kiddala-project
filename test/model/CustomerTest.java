package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    @DisplayName("引数なしコンストラクタとsetter/getterの動作確認")
    public void testNoArgsConstructorAndSettersGetters() {
        // インスタンス作成
        Customer customer = new Customer();

        // setterで値を設定
        customer.setCustId(1);
        customer.setCustName("青木まゆみ");
        customer.setKana("アオキマユミ");
        customer.setTel("09012345678");
        customer.setAddress("東京都千代田区神田小川町1-1-1");

        // getterで想定通りの値が取得できるか検証
        assertEquals(1, customer.getCustId());
        assertEquals("青木まゆみ", customer.getCustName());
        assertEquals("アオキマユミ", customer.getKana());
        assertEquals("09012345678", customer.getTel());
        assertEquals("東京都千代田区神田小川町1-1-1", customer.getAddress());
    }

    @Test
    @DisplayName("全フィールド引数指定コンストラクタの動作確認")
    public void testAllArgsConstructor() {
        // 引数付きコンストラクタで初期化
        Customer customer = new Customer(
            2, 
            "伊藤華英", 
            "イトウハナエ", 
            "09087654321", 
            "東京都千代田区神田小川町2-1-1"
        );

        // 各フィールドの値が正しくセットされているか検証
        assertEquals(2, customer.getCustId());
        assertEquals("伊藤華英", customer.getCustName());
        assertEquals("イトウハナエ", customer.getKana());
        assertEquals("09087654321", customer.getTel());
        assertEquals("東京都千代田区神田小川町2-1-1", customer.getAddress());
    }
}
