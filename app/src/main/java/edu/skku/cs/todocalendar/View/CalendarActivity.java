package edu.skku.cs.todocalendar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.CalendarPresenter;
import edu.skku.cs.todocalendar.Presenter.MainPresenter;
import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity implements CalendarContract.View {
    CalendarView calendarView;
    AppCompatButton addplan;
    ListView listview;
    ListViewAdapter listViewAdapter;

    ArrayList<Plan> plans;
    ArrayList<Plan> todayplans;

    private String uid;
    long selectedDate;

    CalendarContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        presenter = new CalendarPresenter(this);

        uid = getIntent().getStringExtra("uid");

        new Thread(){
            public void run(){
                presenter.onActivityStart(uid);
            }
        }.start();

        calendarView = findViewById(R.id.calendarview);
        listview = findViewById(R.id.todo_list);
        addplan = findViewById(R.id.b_add_todo);

        selectedDate = calendarView.getDate();

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                presenter.onDateClick(year, month+1, day); //month: 0~11
            }
        });
    }

    @Override
    public void showTodayTodoList(ArrayList<Plan> today_plans) {
        listViewAdapter = new ListViewAdapter(getApplicationContext(), today_plans);
        listview.setAdapter(listViewAdapter);
    }

    @Override
    public void showTotalTodoList(ArrayList<Plan> plans) {
        // TODO
        return;
    }
}