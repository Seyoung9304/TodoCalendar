package edu.skku.cs.todocalendar.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.skku.cs.todocalendar.Presenter.TodoContract;
import edu.skku.cs.todocalendar.Presenter.TodoPresenter;
import edu.skku.cs.todocalendar.R;

public class TodoActivity extends Activity implements TodoContract.View {

    EditText e_title;
    EditText e_memo;
    TextView tv_date;
    ImageButton b_check;
    ImageButton b_cancel;

    Intent intent;
    String menu;
    int year;
    int month;
    int day;
    private String uid;

    TodoContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        intent = getIntent();
        menu = intent.getStringExtra("menu");
        uid = intent.getStringExtra("uid");
        year = intent.getIntExtra("y", 0);
        month = intent.getIntExtra("m", 0);
        day = intent.getIntExtra("d", 0);

        String today = Integer.toString(year) + ". " + Integer.toString(month) + ". " + Integer.toString(day);

        presenter = new TodoPresenter(this);

        e_title = findViewById(R.id.e_title);
        e_memo = findViewById(R.id.e_memo);
        tv_date = findViewById(R.id.date);
        b_check = findViewById(R.id.b_check);
        b_cancel = findViewById(R.id.b_cancel);

        tv_date.setText(today);

        //intent로 받을거: 선택된 날짜, 메뉴, update인 경우 기존데이터
        b_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu.compareTo("add")==0){
                    // Intent "menu" == new인 경우
                    String title = e_title.getText().toString();
                    String memo = e_memo.getText().toString();
                    new Thread(){
                        public void run(){
                            presenter.onAddClick(uid, title, memo, year, month, day);
                        }
                    }.start();
                }else{
                    // Intent "menu" == update인 경우
                    int id = intent.getIntExtra("id", -1);
                    String title = e_title.getText().toString();
                    String memo = e_memo.getText().toString();
                    new Thread(){
                        public void run(){
                            presenter.onChangeClick(id, title, memo, year, month, day);
                        }
                    }.start();
                }
                finish();
            }
        });

        b_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showAddResult(Boolean success) {
        if(success==true){
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Error: Try Again.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showChangeResult(Boolean success) {
        if(success==true){
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Error: Try Again.", Toast.LENGTH_LONG).show();
        }
    }
}
