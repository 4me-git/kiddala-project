<%--
  ファイル名：orderControl.jsp
  概要    ：「注文／配達確認／顧客情報変更」画面（スタブ）
            UC01「顧客情報を検索する」の適格性確認テスト（顧客選択後の画面遷移）用。
            選択した顧客情報をセッションスコープのcustomerDataから取得して表示する。
            注文登録・配達確認・顧客情報変更（UC02～UC05）の実装時に置き換える。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    // HTMLの特殊文字をエスケープする
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    String custId = request.getParameter("custId");
    String[][] customerData = (String[][]) session.getAttribute("customerData");

    // 検索結果の中から選択された顧客情報を探す
    String[] customer = null;
    if (customerData != null && custId != null) {
        for (int i = 0; i < customerData.length; i++) {
            if (custId.equals(customerData[i][0])) {
                customer = customerData[i];
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>《注文／配達確認／顧客情報変更》 KIDDA-LA 業務システム</title>
<link rel="stylesheet" href="css/kiddala.css">
</head>
<body>
<div class="screen">
    <div class="header">
        <p class="system-name">KIDDA-LA 業務システム</p>
        <p class="screen-name">《注文／配達確認／顧客情報変更》</p>
    </div>

    <div class="content">
        <% if (customer != null) { %>
        <table class="customer-info">
            <tr><th>ID</th><td><%= escape(customer[0]) %></td></tr>
            <tr><th>氏名</th><td><%= escape(customer[1]) %></td></tr>
            <tr><th>カナ</th><td><%= escape(customer[2]) %></td></tr>
            <tr><th>電話番号</th><td><%= escape(customer[3]) %></td></tr>
            <tr><th>住所</th><td><%= escape(customer[4]) %></td></tr>
        </table>
        <% } else { %>
        <p>選択された顧客情報が見つかりません。</p>
        <% } %>

        <form action="KiddaLaController" method="post">
            <input type="hidden" name="command" value="CustomerSearchDisplay">
            <button type="submit" class="btn btn-sub">検索に戻る</button>
        </form>

        <p class="note">※本画面はスタブです。注文登録・配達確認・顧客情報変更は未実装です。</p>
    </div>

    <div class="footer">&copy;Infotech Serve Inc.</div>
</div>
</body>
</html>
