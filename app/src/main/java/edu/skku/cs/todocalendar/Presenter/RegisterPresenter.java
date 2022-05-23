package edu.skku.cs.todocalendar.Presenter;

import edu.skku.cs.todocalendar.Model.RegisterModel;

public class RegisterPresenter implements  RegisterContract.Presenter{
    RegisterContract.View view;
    RegisterModel registerModel;

    public RegisterPresenter(RegisterContract.View view){
        this.view = view;
        registerModel = new RegisterModel(this);
    }

    @Override
    public void onIDCheckClick(String id) {
        view.showIDCheckResult(registerModel.checkID(id));
    }

    @Override
    public void onRegisterClick(String id, String pw, String pw_verify) {
        view.showRegisterResult(registerModel.registerUser(id, pw, pw_verify));
    }
}
