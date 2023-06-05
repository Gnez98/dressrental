package com.tan.domain;


import java.io.Serializable;

public class LeaseR implements Serializable {
    private Integer id;        //礼服租赁id
    private String dressname;   //租赁的礼服名称
    private String dressisbn;   //租赁的礼服的ISBN编号
    private String borrower;   //礼服租赁人
    private String borrowTime; //礼服租赁时间
    private String remandTime; //礼服归还时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDressname() {
        return dressname;
    }

    public void setDressname(String dressname) {
        this.dressname = dressname;
    }

    public String getDressisbn() {
        return dressisbn;
    }

    public void setDressisbn(String dressisbn) {
        this.dressisbn = dressisbn;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getRemandTime() {
        return remandTime;
    }

    public void setRemandTime(String remandTime) {
        this.remandTime = remandTime;
    }
}
