package edu.skku.cs.todocalendar.Presenter;

import edu.skku.cs.todocalendar.Model.MainModel;

public class MainPresenter implements MainContract.Presenter {
    MainContract.View view;
    MainModel mainModel;

    public MainPresenter(MainContract.View view){
        this.view = view;
        mainModel = new MainModel(this);
    }

    @Override
    public void onLoginClick(String id, String pw) {
        view.showResult(mainModel.checkIDPW(id, pw));
    }
}
