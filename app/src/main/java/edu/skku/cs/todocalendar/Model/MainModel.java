package edu.skku.cs.todocalendar.Model;

import edu.skku.cs.todocalendar.Presenter.MainContract;

public class MainModel {
    MainContract.Presenter presenter;
    public MainModel(MainContract.Presenter presenter){
        this.presenter = presenter;
    }
    public Boolean checkIDPW(String id, String pw){
        // get request and check if id, pw is correct
        // API 통신
        return true;
    }
}
