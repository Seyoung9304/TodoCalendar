package edu.skku.cs.todocalendar.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.CalendarPresenter;
import edu.skku.cs.todocalendar.Presenter.ItemTouchHelperCallback;
import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity implements CalendarContract.View, CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    AppCompatButton addplan;

    RecyclerView listview;
    ListViewAdapter listViewAdapter;
    ItemTouchHelper helper;

    ArrayList<Plan> plans;
    ArrayList<Plan> todayplans;

    private String uid;

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

        // todolist recycler view
        listview = findViewById(R.id.todo_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(manager);
        //set adapter
        listViewAdapter = new ListViewAdapter(this);
        listview.setAdapter(listViewAdapter);
        //helper
        helper = new ItemTouchHelper(new ItemTouchHelperCallback((listViewAdapter)));
        helper.attachToRecyclerView(listview);



        addplan = findViewById(R.id.b_add_todo);

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                //intent.putExtra("*/-+")
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView(){
        listview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                helper.onDraw(c, parent, state);
            }
        });
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
            presenter.onDateClick(selectedDate.getYear(), selectedDate.getMonthValue(), Integer.parseInt(dayText)); //month: 0~11
        }
    }


    @Override
    public void showTodayTodoList(ArrayList<Plan> today_plans) {
        // listViewAdapter = new ListViewAdapter(getApplicationContext(), today_plans);
        // listview.setAdapter(listViewAdapter);
        Log.e("calendaractivity", String.valueOf(today_plans.size()));
        Log.e("calendaractivity", "showtodaytodolist");
        listViewAdapter.setItems(today_plans);
    }

    @Override
    public void showTotalTodoList(ArrayList<Plan> plans) {
        // TODO
        return;
    }
}