package edu.skku.cs.todocalendar.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import edu.skku.cs.todocalendar.R;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    long selectedDate;

    AppCompatButton addplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarview);
        selectedDate = calendarView.getDate();

        addplan = findViewById(R.id.b_add_todo);

        addplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TodoActivity.class);
                startActivity(intent);
            }
        });
    }
}