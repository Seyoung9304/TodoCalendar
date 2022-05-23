package edu.skku.cs.todocalendar.Presenter;

public interface CalendarContract {
    interface View{
        void showTodayTodoList(); // show listview
        //void showCalendar(); // show calendar with marker
    }
    interface Presenter{
        void onActivityStart(String id); // get user id and get plan info of corresponding id
    }
}
