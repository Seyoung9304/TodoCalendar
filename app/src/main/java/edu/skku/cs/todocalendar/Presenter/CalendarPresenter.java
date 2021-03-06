package edu.skku.cs.todocalendar.Presenter;


import edu.skku.cs.todocalendar.Model.CalendarModel;

public class CalendarPresenter implements CalendarContract.CalendarPresenter {
    CalendarContract.CalendarView view;
    CalendarModel calendarModel;
    CalendarContract.TodoView todoView;

    public CalendarPresenter(CalendarContract.CalendarView view){
        this.view = view;
        calendarModel = new CalendarModel(this);
    }

    public CalendarPresenter(CalendarContract.TodoView view){
        this.todoView = view;
        calendarModel = new CalendarModel(this);
    }

    @Override
    public void onActivityStart(String uid) {
        view.showTotalTodoList(calendarModel.getPlans(uid));
    }

    public void onDateClick(int year, int date, int day){
        view.showTodayTodoList(calendarModel.getTodayPlans(year, date, day));
    }

    @Override
    public void onDataChange(int year, int month, int day) {
        view.showTodayTodoList(calendarModel.getTodayPlans(year, month, day));
    }
}
