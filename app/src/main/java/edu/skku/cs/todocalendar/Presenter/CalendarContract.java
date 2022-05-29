package edu.skku.cs.todocalendar.Presenter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;

public interface CalendarContract {
    interface CalendarView{
        void showTodayTodoList(ArrayList<Plan> plans); // show listview
        void showTotalTodoList(ArrayList<Plan> plans); // GET plan table
    }
    interface CalendarPresenter{
        void onActivityStart(String uid); // get user id and get plan info of corresponding id
        void onDateClick(int year, int month, int day);
        // void onTodoChange(); // when delete/change/add in todolist occur -> reload todolist
    }
    interface TodoView{
        void showAddResult(Boolean success);
        void showChangeResult(Boolean success);
    }
    interface TodoPresenter{
        void onAddClick(String uid, String title, String memo, int year, int month, int day);
        void onChangeClick(int id, String title, String memo, int year, int month, int day);
        void onDeleteClick(int id);
        void onDoneClick(int id, Boolean done);
    }
    interface ItemTouchHelperListener{
        boolean onItemMove(int from_position, int to_position);
        void onItemSwipe(int position);
        void onLeftClick(int position, RecyclerView.ViewHolder viewHolder);
        void onRightClick(int position, RecyclerView.ViewHolder viewHolder);
    }
}
