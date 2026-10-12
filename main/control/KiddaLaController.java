package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.CustomerSearchAction;

/**
 * 全体の「総合受付・案内係」となるコントローラーサーブレットです。
 * @WebServlet("/KiddaLaController") という記述で、ブラウザからの窓口になります。
 */
@WebServlet("/KiddaLaController")
public class KiddaLaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 画面からデータが送られてきたとき（POST）に動くメソッド
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 文字化けを防ぐため、受け取る文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");
        
        // 2. セッション（ブラウザごとの荷物置き場）を準備
        HttpSession session = request.getSession();
        
        // 3. 画面から送られてきた「command（何の仕事をしたいか）」の指示を読み取る
        String command = request.getParameter("command");
        
        // 次に進むべき画面（JSP）のファイルを覚えておく変数
        String forwardPage = "mainMenu.jsp"; // 基本はメインメニューに設定

        try {
            // 指示（command）の内容によって、案内するルートを分岐させる
            if (command == null || command.equals("")) {
                // 指示がなければ、そのままメインメニュー画面へ
                forwardPage = "mainMenu.jsp";

            } else if (command.equals("CustomerSearchDisplay")) {
                // 「検索画面を開いて！」という指示の場合
                forwardPage = "customerSearch.jsp";

            } else if (command.equals("CustomerSearch")) {
                // 「検索を実行して！」という指示の場合
                
                // 画面の入力欄から「電話番号」と「フリガナ」の文字を回収
                String tel = request.getParameter("tel");
                String kana = request.getParameter("kana");
                
                // 両方未入力の場合はエラーコード「011」を設定して画面に戻す
                if ((tel == null || tel.trim().isEmpty()) && (kana == null || kana.trim().isEmpty())) {
                    request.setAttribute("errorCode", "011");
                    forwardPage = "customerSearch.jsp";
                } else {
                    // 片方でも入力されている場合は検索処理を実行
                    String[] searchData = { tel, kana };
                    
                    CustomerSearchAction action = new CustomerSearchAction();
                    String[][] resultTable = action.execute(searchData);
                    
                    session.setAttribute("customerData", resultTable);
                    forwardPage = "customerSearch.jsp";
                }
                
            } else if (command.equals("OrderControlDisplay")) {
                // 「注文/配達確認/顧客情報変更」画面を開く指示の場合
                String custId = request.getParameter("custId");
                
                // 選択された顧客IDをリクエスト属性に保持
                request.setAttribute("custId", custId);
                
                // 注文管理画面（orderControl.jsp）へ遷移
                forwardPage = "orderControl.jsp";
            }
            
        } catch (Exception e) {// 例外発生時はスタックトレースを出力
            e.printStackTrace();
            
            // DB接続エラー等の場合、エラーコード「010」を設定して検索画面に戻す
            request.setAttribute("errorCode", "010");
            forwardPage = "customerSearch.jsp";
        }

        // 4. 指定された次のJSP画面へ、ユーザーを「案内（フォワード）」する
        RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
        rd.forward(request, response);
    }

    /**
     * ブラウザのアドレス欄に直接URLを叩いた時（GET）などに動くメソッド
     * 今回はすべての処理を doPost に丸投げする設計にします。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}