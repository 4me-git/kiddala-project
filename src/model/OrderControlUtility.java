package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderControlUtility {

    /**
     * 【ステップ1】顧客情報リスト(ArrayList)を、検索結果表示用データ(String[][])に変換
     * 
     * @param list 顧客情報のリスト
     * @return 2次元配列の表示データ
     */
    public String[][] customerToArray(ArrayList<Customer> list) {
        // リストが空っぽ、または件数が0件の場合は、nullを返す（※仕様のルール）
        if (list == null || list.isEmpty()) {
            return null;
        }

        // リストの件数分の「行」と、5つの属性（ID, 名前, カナ, 電話, 住所）の「列」で2次元配列を準備
        String[][] tableData = new String[list.size()][5];

        // リストから1件ずつデータを取り出して、配列に詰め替えるループ処理
        for (int i = 0; i < list.size(); i++) {
            Customer c = list.get(i);
            tableData[i][0] = String.valueOf(c.getCustId()); // 顧客ID (int型をString型に変換)
            tableData[i][1] = c.getCustName();              // 顧客名
            tableData[i][2] = c.getKana();                  // 顧客カナ
            tableData[i][3] = c.getTel();                   // 顧客電話番号
            tableData[i][4] = c.getAddress();               // 顧客住所
        }

        return tableData;
    }

    /**
     * 【ステップ2】現在のシステム日付を "yyyy-MM-dd" 形式の文字列で返却します。
     * 
     * @return 日付文字列 (例: "2026-07-06")
     */
    public String getDate() {
        // カレンダーから現在の日時を取得
        Calendar cal = Calendar.getInstance();
        // 日付のフォーマット（形）を指定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 指定した形で文字列に変換して返す
        return sdf.format(cal.getTime());
    }
}