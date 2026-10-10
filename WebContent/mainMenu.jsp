<%--
  ファイル名：mainMenu.jsp
  概要    ：「メインメニュー」画面（画面ID：KO-01-1）
            「01 注文管理」ボタンで「顧客情報検索」画面へ遷移する。
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>《メインメニュー》 KIDDA-LA 業務システム</title>
<link rel="stylesheet" href="css/kiddala.css">
</head>
<body>
<div class="screen">
    <div class="header">
        <p class="system-name">KIDDA-LA 業務システム</p>
        <p class="screen-name">《メインメニュー》</p>
    </div>

    <div class="content menu">
        <form action="KiddaLaController" method="post">
            <input type="hidden" name="command" value="CustomerSearchDisplay">
            <button type="submit" class="btn btn-menu">01　注文管理</button>
        </form>
    </div>

    <div class="footer">&copy;Infotech Serve Inc.</div>
</div>
</body>
</html>
