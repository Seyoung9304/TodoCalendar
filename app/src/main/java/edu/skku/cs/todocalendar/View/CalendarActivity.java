package edu.skku.cs.todocalendar.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.skku.cs.todocalendar.Classes.Plan;
import edu.skku.cs.todocalendar.Presenter.CalendarContract;
import edu.skku.cs.todocalendar.Presenter.CalendarPresenter;
import edu.skku.cs.todocalendar.Presenter.ItemTouchHelperCallback;
import edu.skku.cs.todocalendar.Presenter.TodoPresenter;
import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity implements CalendarContract.CalendarView, CalendarAdapter.OnItemListener {

    int SUCCESS_CODE = 0;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private int selectedDay = 0;

    AppCompatButton addplan;
    TextView dateTodo;

    RecyclerView listview;
    ListViewAdapter listViewAdapter;
    ItemTouchHelper helper;

    ArrayList<Plan> ACTIVITY_plans;
    ArrayList<Plan> ACTIVITY_todayplans;

    private String uid;

    CalendarContract.CalendarPresenter presenter;
    CalendarContract.TodoPresenter todoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        presenter = new CalendarPresenter(this);
        todoPresenter = new TodoPresenter(this);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        uid = getIntent().getStringExtra("uid");

        Log.e("calendaractivity", "oncreate");

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
        dateTodo = findViewById(R.id.selected_date);

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDay!=0){
                    Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                    intent.putExtra("menu", "add");
                    intent.putExtra("uid", uid);
                    intent.putExtra("y", selectedDate.getYear());
                    intent.putExtra("m", selectedDate.getMonthValue());
                    intent.putExtra("d", selectedDay);
                    startActivityForResult(intent, SUCCESS_CODE);
                }else{
                    Toast.makeText(getApplicationContext(), "Select the date first.", Toast.LENGTH_LONG).show();
                }
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
        selectedDay = 0;
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view){
        selectedDay = 0;
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(!dayText.equals("")) {
            selectedDay = Integer.parseInt(dayText);

            new Thread(){
                public void run(){
                    presenter.onDateClick(selectedDate.getYear(), selectedDate.getMonthValue(), Integer.parseInt(dayText)); //month: 0~11
                }
            }.start();

            String showtext = selectedDate.getYear() + ". " + selectedDate.getMonthValue() + ". " + dayText;
            dateTodo.setText(showtext);
        }
    }


    @Override
    public void showTodayTodoList(ArrayList<Plan> today_plans) {
        ACTIVITY_todayplans = today_plans;
        Log.e("calendaractivity", String.valueOf(today_plans.size()));
        Log.e("calendaractivity", "showtodaytodolist");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.setItems(ACTIVITY_todayplans);
            }
        }, 0);
    }

    @Override
    public void showTotalTodoList(ArrayList<Plan> plans) {
        ACTIVITY_plans = plans;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("backed on CalenderActivity", "onactivityresult");
        Log.e("resultcode", String.valueOf(resultCode));
        if(resultCode == SUCCESS_CODE){
            Log.e("onactivityresult", "before");
            new Thread(){
                public void run(){
                    presenter.onDateClick(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay); //month: 0~11
                }
            }.start();
            Log.e("onactivityresult", "after");
        }
    }

    public void runTodoActivity(Intent intent){
        startActivityForResult(intent, SUCCESS_CODE);
    }
}