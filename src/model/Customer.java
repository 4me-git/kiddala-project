package model;

import java.io.Serializable;

public class Customer implements Serializable {
	// ==========================================
    // 属性（フィールド）の定義
    // ==========================================
    private int custId;
    private String custName;
    private String kana;
    private String tel;
    private String address;

    // ==========================================
    // コンストラクタ
    // ==========================================
    /**
     * 引数なしコンストラクタ
     */

    public Customer() {
    }

    /**
     * 引数ありのコンストラクタ
     *
     * @param custId    顧客ID
     * @param custName  顧客名
     * @param kana      顧客カナ
     * @param tel       顧客電話番号
     * @param address   顧客住所
     */
    public Customer(int custId, String custName, String kana, String tel, String address) {
        this.custId = custId;
        this.custName = custName;
        this.kana = kana;
        this.tel = tel;
        this.address = address;
    }

    // ==========================================
    // getter/setter
    // ==========================================

    // custId
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    // custName
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    // kana
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    // tel
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    // address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
