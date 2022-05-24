package edu.skku.cs.todocalendar.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.skku.cs.todocalendar.R;

public class TodoActivity extends Activity {

    EditText e_title;
    EditText e_memo;
    TextView tv_date;
    ImageButton b_check;
    Button b_cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        e_title = findViewById(R.id.e_title);
        e_memo = findViewById(R.id.e_memo);
        tv_date = findViewById(R.id.date);
        b_check = findViewById(R.id.b_check);
        b_cancel = findViewById(R.id.b_cancel);
        
        //intent로 받을거: 선택된 날짜, 메뉴, update인 경우 기존데이터

        b_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent "menu" == update인 경우
                // TODO - POST deletePlan()
                // Intent "menu" == new인 경우
                // TODO - POST addPlan()
                finish();
            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent "menu" == update인 경우 삭제
                // TODO - POST deletePlan()
                finish();
            }
        });
    }
}
