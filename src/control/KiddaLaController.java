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
                
                // Action（司令塔）に渡すために配列の形にまとめる
                String[] searchData = { tel, kana };
                
                // 顧客検索の司令塔（Action）を呼び出す
                CustomerSearchAction action = new CustomerSearchAction();
                String[][] resultTable = action.execute(searchData);
                
                // 実行して戻ってきた検索結果（2次元配列）をセッション（荷物置き場）に保存する
                // これにより、次に開くJSP画面でこの結果を取り出して表にできます
                session.setAttribute("customerData", resultTable);
                
                // 検索が終わったら、また検索画面（結果付き）に戻る
                forwardPage = "customerSearch.jsp";
            }
            
        } catch (Exception e) {
            // 万が一エラーが起きた場合はスタックトレースをログに出力
            e.printStackTrace();
            // 今回は割愛しますが、実務ではエラー画面等へ案内します
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