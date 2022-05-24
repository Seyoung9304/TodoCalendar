package edu.skku.cs.todocalendar.Presenter;

import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;

public interface CalendarContract {
    interface View{
        void showTodayTodoList(ArrayList<Plan> plans); // show listview
        void showTotalTodoList(ArrayList<Plan> plans); // GET plan table
        //void showCalendar(); // show calendar with marker
    }
    interface Presenter{
        void onActivityStart(String uid); // get user id and get plan info of corresponding id
        void onDateClick(int year, int month, int day);
    }
}
