package edu.skku.cs.todocalendar.Presenter;

import edu.skku.cs.todocalendar.Model.CalendarModel;
import edu.skku.cs.todocalendar.Model.TodoModel;

public class TodoPresenter implements CalendarContract.TodoPresenter{
    CalendarContract.TodoView todoView;
    CalendarContract.CalendarView calendarView;
    CalendarContract.ItemTouchHelperListener adapter;
    TodoModel todoModel = new TodoModel(this);
    CalendarModel calendarModel = new CalendarModel(this);

    public TodoPresenter(CalendarContract.TodoView todoView){
        this.todoView = todoView;
    }

    public TodoPresenter(CalendarContract.CalendarView calendarView){
        this.calendarView = calendarView;
    }

    public TodoPresenter(CalendarContract.ItemTouchHelperListener adapter){
        this.adapter = adapter;
    }

    @Override
    public void onAddClick(String uid, String title, String memo, int year, int month, int day) {
        todoView.showAddResult(todoModel.addPlan(uid, title, memo, year, month, day), year, month, day);
    }

    @Override
    public void onChangeClick(int id, String title, String memo, int year, int month, int day) {
        todoView.showChangeResult(todoModel.changePlan(id, title, memo, year, month, day), year, month, day);
    }

    @Override
    public void onDeleteClick(int id) {
        todoModel.deletePlan(id);
    }

    @Override
    public void onDoneClick(int id, Boolean done) {
        todoModel.updatePlanStatus(id, done);
    }
}
