package edu.skku.cs.todocalendar.Presenter;

import edu.skku.cs.todocalendar.Model.Model;

public class Presenter implements Contract.Presenter {
    Contract.View view;
    Model mainModel;

    public Presenter(Contract.View view){
        this.view = view;
        mainModel = new Model(this);
    }

    @Override
    public void onLoginClick(String id, String pw) {
        view.showLoginResult(mainModel.checkIDPW(id, pw));
    }
}
