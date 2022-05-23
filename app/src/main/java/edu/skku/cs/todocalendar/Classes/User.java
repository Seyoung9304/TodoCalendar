package edu.skku.cs.todocalendar.Classes;

public class User {
    String uid;
    String pw;

    public User(String uid, String pw){
        this.uid = uid;
        this.pw = pw;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
