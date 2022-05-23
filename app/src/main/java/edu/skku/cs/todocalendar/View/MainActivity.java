package edu.skku.cs.todocalendar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.skku.cs.todocalendar.Presenter.MainContract;
import edu.skku.cs.todocalendar.Presenter.MainPresenter;
import edu.skku.cs.todocalendar.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    Button b_login;
    Button b_register;
    EditText e_id;
    EditText e_pw;

    MainContract.Presenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        b_login = findViewById(R.id.b_login);
        b_register = findViewById(R.id.b_register);
        e_id = findViewById(R.id.edit_id);
        e_pw = findViewById(R.id.edit_pw);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    public void run(){
                        presenter.onLoginClick(e_id.getText().toString(), e_pw.getText().toString());
                    }
                }.start();
            }
        });
    }

    @Override
    public void showLoginResult(Boolean success) {
        if (success==true) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            finish();
        }else{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Check ID or password and try again.", Toast.LENGTH_LONG).show();
                }
            }, 0);
        }
    }
}