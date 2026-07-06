package action;

import java.util.ArrayList;

import dao.CustomerSearchDBAccess;
import model.Customer;
import model.OrderControlUtility;

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

        // 【修正点①】引数なしでDAOを生成（DB接続はDAOが行うため）
        CustomerSearchDBAccess dbAccess = new CustomerSearchDBAccess();
        ArrayList<Customer> customerList = new ArrayList<>();

        // 2. ①、②、③の順に条件分岐を行う
        if (!tel.equals("") && kana.equals("")) {
            // ① 電話番号のみ入力されている場合
            customerList = dbAccess.searchCustomerByTel(tel);

        } else if (tel.equals("") && !kana.equals("")) {
            // ② カナのみ入力されている場合
            customerList = dbAccess.searchCustomerByKana(kana);

        } else if (!tel.equals("") && !kana.equals("")) {
            // ③ 【修正点③】両方入力されている場合
            // 引数に「tel」と「kana」を渡して絞り込むメソッドを呼び出す
            customerList = dbAccess.searchCustomer(tel, kana); 
        }

        // 【修正点②】データ変換用のUtilityを呼び出す
        OrderControlUtility utility = new OrderControlUtility();
        
        // utilityのcustomerToArrayメソッドにリストを渡して、2次元配列（String[][]）に変換して画面に返す
        // ※データが0件のときは、utility側で自動的に null が返る仕様
        return utility.customerToArray(customerList);
    }
    
}