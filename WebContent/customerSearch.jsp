<%--
  ファイル名：customerSearch.jsp
  概要    ：「顧客情報検索」画面（画面ID：KO-02-1）
            ・電話番号または氏名カナを入力して顧客情報を検索する。
            ・セッションスコープのcustomerDataを取得して顧客情報一覧を表示する。
            ・顧客情報一覧の顧客情報をクリックすると
              「注文／配達確認／顧客情報変更」画面へ遷移する。
            ・エラーメッセージ（コード：010～013）をメッセージダイアログで表示する。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%!
    // エラーメッセージ表（コード：メッセージ）
    private static final Map<String, String> MESSAGES = new HashMap<String, String>();

    static {
        MESSAGES.put("010", "【エラー】\\nDB接続処理に失敗しました！\\n管理者に連絡してください。");
        MESSAGES.put("011", "【エラー】\\n電話番号と氏名カナのいずれか、または両方を入力してください。");
        MESSAGES.put("012", "【確認】\\n一致する情報は見つかりませんでした。");
        MESSAGES.put("013", "【エラー】\\n顧客情報検索処理に失敗しました！\\n管理者に連絡してください。");
    }

    // HTMLの特殊文字をエスケープする
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    // 半角スペースと全角スペースを取り除いた結果が""と等しいかを判定する
    // （CustomerSearchAction#execute()の前処理と同じ判定）
    private boolean isBlank(String value) {
        return value == null || value.replace(" ", "").replace("　", "").equals("");
    }
%>
<%
    String command = request.getParameter("command");
    String tel = request.getParameter("tel");
    String kana = request.getParameter("kana");

    // コントローラで例外が発生した場合は，コントローラがエラーコード（010／013）を設定する
    String errorCode = (String) request.getAttribute("errorCode");
    String[][] customerData = null;

    // 検索を実行した場合だけ検索結果を表示する（画面を開いただけのときは前回の結果を表示しない）
    if ("CustomerSearch".equals(command) && errorCode == null) {
        if (isBlank(tel) && isBlank(kana)) {
            errorCode = "011";
        } else {
            customerData = (String[][]) session.getAttribute("customerData");
            if (customerData == null) {
                errorCode = "012";
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>《顧客情報検索》 KIDDA-LA 業務システム</title>
<link rel="stylesheet" href="css/kiddala.css">
<script>
    // 「入力消去」ボタン：テキストフィールドの入力情報を消去する
    function clearInput() {
        document.getElementById("tel").value = "";
        document.getElementById("kana").value = "";
    }
<% if (errorCode != null) { %>
    // エラーメッセージをメッセージダイアログで表示する（コード：<%= errorCode %>）
    window.onload = function () {
        alert("<%= MESSAGES.get(errorCode) %>");
    };
<% } %>
</script>
</head>
<body>
<div class="screen">
    <div class="header">
        <p class="system-name">KIDDA-LA 業務システム</p>
        <p class="screen-name">《顧客情報検索》</p>
    </div>

    <div class="content search-area">
        <%-- 顧客情報検索フォーム --%>
        <div class="search-form">
            <form action="KiddaLaController" method="post">
                <input type="hidden" name="command" value="CustomerSearch">
                <div class="field">
                    <label for="tel">電話番号（ハイフンなし）</label>
                    <input type="text" id="tel" name="tel" value="<%= escape(tel) %>"
                        placeholder="例：09012345678">
                </div>
                <div class="field">
                    <label for="kana">氏名カナ（全角カタカナ）</label>
                    <input type="text" id="kana" name="kana" value="<%= escape(kana) %>"
                        placeholder="例：ヤマダタロウ">
                </div>
                <button type="submit" class="btn btn-search">検索</button>
            </form>
            <div class="sub-buttons">
                <button type="button" class="btn btn-sub" onclick="clearInput();">入力消去</button>
                <button type="submit" class="btn btn-sub" form="backForm">戻る</button>
            </div>
            <%-- 「戻る」ボタン：commandを送信しないことで「メインメニュー」画面へ遷移する --%>
            <form id="backForm" action="KiddaLaController" method="post"></form>
        </div>

        <%-- 顧客情報一覧（検索結果） --%>
        <% if (customerData != null) { %>
        <div class="result">
            <div class="result-title">検索結果</div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>氏名</th>
                        <th>カナ</th>
                        <th>住所</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    // customerData[i]：[0]顧客ID，[1]顧客名，[2]カナ，[3]電話番号，[4]住所
                    for (int i = 0; i < customerData.length; i++) {
                        String link = "KiddaLaController?command=OrderControlDisplay&amp;custId="
                                + escape(customerData[i][0]);
                %>
                    <tr>
                        <td class="id"><a href="<%= link %>"><%= escape(customerData[i][0]) %></a></td>
                        <td><a href="<%= link %>"><%= escape(customerData[i][1]) %></a></td>
                        <td><a href="<%= link %>"><%= escape(customerData[i][2]) %></a></td>
                        <td><a href="<%= link %>"><%= escape(customerData[i][4]) %></a></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <% } %>
    </div>

    <div class="footer">&copy;Infotech Serve Inc.</div>
</div>
</body>
</html>
