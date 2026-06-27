package action;

import java.sql.Connection;
import java.util.ArrayList;

import dao.CustomerSearchDBAccess;
import model.Customer;

public class CustomerSearchAction {
	
	/**
     * 顧客検索処理を実行
     *
     * @param data 入力情報配列 (data[0]:電話番号, data[1]:カナ)
     * @return 検索結果表示用データ (String[][])
     * @throws Exception データベースアクセスエラー等が発生した場合
     */
    public String[][] execute(String[] data) throws Exception {
        // 1. data[0]の値とdata[1]の値の半角スペースと全角スペースを取り除く
        if (data != null) {
            if (data.length > 0 && data[0] != null) {
                data[0] = data[0].replace(" ", "").replace(" ", "");
            }
            if (data.length > 1 && data[1] != null) {
                data[1] = data[1].replace(" ", "").replace(" ", "");
            }
        }

        // 引数の安全なローカル変数化（Null防止）
        String tel = (data != null && data.length > 0) ? data[0] : "";
        String kana = (data != null && data.length > 1) ? data[1] : "";

        if (tel == null) {
            tel = "";
        }
        if (kana == null) {
            kana = "";
        }

        // データベース接続の確立（OrderControlUtilityや共通処理を利用することを想定）
        // ※ 実際の環境に合わせて Connection の取得ロジックを調整してください。
        Connection connection = null; 
        
        CustomerSearchDBAccess dbAccess = new CustomerSearchDBAccess(connection);
        ArrayList<Customer> customerList = new ArrayList<>();

        // 2. ①、②、③の順に条件分岐を行う
        if (!tel.equals("") && kana.equals("")) {
            // ① data[0]の値が「""」と等しくなく かつ data[1]の値が「""」と等しい場合
            // ⇒ data[0]に一致する顧客情報リストを顧客情報検索DAOに問い合わせる
            customerList = dbAccess.searchCustomerByTel(tel);

        } else if (tel.equals("") && !kana.equals("")) {
            // ② data[0]の値が「""」と等しく かつ data[1]の値が「""」と等しくない場合
            // ⇒ data[1]を含む顧客情報リストを顧客情報検索DAOに問い合わせる
            customerList = dbAccess.searchCustomerByKana(kana);

        } else if (!tel.equals("") && !kana.equals("")) {
            // ③ data[0]の値が「""」と等しくなく かつ data[1]の値が「""」と等しくない場合
            // ⇒ data[0]に一致し かつ data[1]を含む顧客情報リストを顧客情報検索DAOに問い合わせる
            customerList = dbAccess.searchCustomer(); 
        }

        // 3. 顧客情報リストが取得できた場合は、顧客情報リストを検索結果表示用データに変換
        return convertToTableData(customerList);
    }

    /**
     * 💡 戻り値の変換ロジック
     * ArrayList<Customer> を 検索結果表示用データ（String[][]）に変換します。
     *
     * @param list 顧客情報のリスト
     * @return 2次元配列の表示データ
     */
    private String[][] convertToTableData(ArrayList<Customer> list) {
        if (list == null || list.isEmpty()) {
            return new String[0][0];
        }

        // リストの件数分の行数、5つの属性（ID, 名前, カナ, 電話, 住所）の列数で配列を初期化
        String[][] tableData = new String[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            Customer c = list.get(i);
            tableData[i][0] = String.valueOf(c.getCustId()); // 顧客ID
            tableData[i][1] = c.getCustName();              // 顧客名
            tableData[i][2] = c.getKana();                  // 顧客カナ
            tableData[i][3] = c.getTel();                   // 顧客電話番号
            tableData[i][4] = c.getAddress();               // 顧客住所
        }

        return tableData;
    }


    /**
     * customerToArray()
     * 戻り値を Customer[] 配列の形式で要求されるパターン対応
     */
    private Customer[] customerToArray(ArrayList<Customer> list) {
        if (list == null) {
            return new Customer[0];
        }
        return list.toArray(new Customer[0]);
    }
}


