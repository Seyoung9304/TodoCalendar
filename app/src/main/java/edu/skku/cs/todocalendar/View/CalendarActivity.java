package edu.skku.cs.todocalendar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarview);
    }
}