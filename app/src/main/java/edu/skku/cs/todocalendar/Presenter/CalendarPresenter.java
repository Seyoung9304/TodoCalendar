package edu.skku.cs.todocalendar.Presenter;


import edu.skku.cs.todocalendar.Model.CalendarModel;

public class CalendarPresenter implements CalendarContract.Presenter {
    CalendarContract.View view;
    CalendarModel calendarModel;

    public CalendarPresenter(CalendarContract.View view){
        this.view = view;
        calendarModel = new CalendarModel(this);
    }

    @Override
    public void onActivityStart(String uid) {
        view.showTotalTodoList(calendarModel.getPlans(uid));
    }

    public void onDateClick(int year, int date, int day){
        view.showTodayTodoList(calendarModel.getTodayPlans(year, date, day));
    }
}
