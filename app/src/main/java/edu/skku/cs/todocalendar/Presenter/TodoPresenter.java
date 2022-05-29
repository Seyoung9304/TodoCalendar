package edu.skku.cs.todocalendar.Presenter;

import edu.skku.cs.todocalendar.Model.TodoModel;

public class TodoPresenter implements TodoContract.Presenter{
    TodoContract.View view;
    TodoModel todoModel;

    public TodoPresenter(TodoContract.View view){
        this.view = view;
        todoModel = new TodoModel(this);
    }

    @Override
    public void onAddClick(String uid, String title, String memo, int year, int month, int day) {
        view.showAddResult(todoModel.addPlan(uid, title, memo, year, month, day));
    }

    @Override
    public void onChangeClick(int id, String title, String memo, int year, int month, int day) {
        view.showChangeResult(todoModel.changePlan(id, title, memo, year, month, day));
    }
}
