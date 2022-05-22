package edu.skku.cs.todocalendar.Presenter;

public interface MainContract {
    interface View{
        void showResult(Boolean success);
    }
    interface Presenter{
        void onLoginClick(String id, String pw);
    }
}
