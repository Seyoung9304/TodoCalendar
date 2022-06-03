package edu.skku.cs.todocalendar.Presenter;

public interface RegisterContract {
    interface View{
        void showIDCheckResult(Boolean success);
        void showRegisterResult(Boolean success);
    }
    interface Presenter{
        void onIDCheckClick(String id);
        void onRegisterClick(String id, String pw, String pw_verify);
    }
}
