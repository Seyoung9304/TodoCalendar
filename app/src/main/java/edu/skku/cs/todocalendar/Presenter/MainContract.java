package edu.skku.cs.todocalendar.Presenter;

public interface MainContract {
    interface View{
        void showLoginResult(Boolean success);
        //void showRegisterResult(Boolean success);
        //void showIDCheckResult(Boolean success);
    }
    interface Presenter{
        void onLoginClick(String id, String pw);
        //void onRegisterClick(String id, String pw);
    }
}
