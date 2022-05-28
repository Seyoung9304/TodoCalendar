package edu.skku.cs.todocalendar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.CalendarPresenter;
import edu.skku.cs.todocalendar.Presenter.MainPresenter;
import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity implements CalendarContract.View, CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    AppCompatButton addplan;
    ListView listview;
    ListViewAdapter listViewAdapter;

    ArrayList<Plan> plans;
    ArrayList<Plan> todayplans;

    private String uid;
    // long selectedDate;

    CalendarContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        presenter = new CalendarPresenter(this);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        uid = getIntent().getStringExtra("uid");

        new Thread(){
            public void run(){
                presenter.onActivityStart(uid);
            }
        }.start();

        // calendarView = findViewById(R.id.calendarview);
        listview = findViewById(R.id.todo_list);
        addplan = findViewById(R.id.b_add_todo);

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                //intent.putExtra("*/-+")
                startActivity(intent);
            }
        });
        /*
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

            }
        });*/
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view){
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view){
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")) {
            presenter.onDateClick(selectedDate.getYear(), selectedDate.getMonthValue()+1, Integer.parseInt(dayText)); //month: 0~11
            // String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
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