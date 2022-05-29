package edu.skku.cs.todocalendar.Presenter;

public interface TodoContract {
    interface View{
        void showAddResult(Boolean success);
        void showChangeResult(Boolean success);
    }
    interface Presenter{
        void onAddClick(String uid, String title, String memo, int year, int month, int day);
        void onChangeClick(int id, String title, String memo, int year, int month, int day);
    }
}
