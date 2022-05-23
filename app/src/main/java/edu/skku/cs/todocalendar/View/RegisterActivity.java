package edu.skku.cs.todocalendar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.skku.cs.todocalendar.Presenter.RegisterContract;
import edu.skku.cs.todocalendar.Presenter.RegisterPresenter;
import edu.skku.cs.todocalendar.R;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View{

    EditText id;
    EditText pw;
    EditText pw_check;
    Button id_check;
    Button submit;

    RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);

        id = findViewById(R.id.edit_id);
        pw = findViewById(R.id.edit_pw);
        pw_check = findViewById(R.id.edit_pw_verify);
        id_check = findViewById(R.id.b_id_check);
        submit = findViewById(R.id.b_submit);

        Boolean id_valid = false;
        Boolean password_correct = false;

        // Check if ID already exists
        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    public void run(){
                        presenter.onIDCheckClick(id.getText().toString());
                    }
                }.start();
            }
        });

        // Post user info
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("submitclick", "done");
                new Thread(){
                    public void run(){
                        presenter.onRegisterClick(id.getText().toString(), pw.getText().toString(), pw_check.getText().toString());
                    }
                }.start();
            }
        });
    }

    @Override
    public void showIDCheckResult(Boolean success) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (success==true) {
                    Toast.makeText(getApplicationContext(), "This ID already exists. Try again.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Available ID!", Toast.LENGTH_LONG).show();
                }
            }
        }, 0);
    }

    @Override
    public void showRegisterResult(Boolean success) {
        if (success==true) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Register Success! Please Log in.", Toast.LENGTH_LONG).show();
                }
            }, 0);
            finish();
        }else{
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "ERROR - Did you checked ID? Did you checked password?", Toast.LENGTH_LONG).show();
                }
            }, 0);
        }
    }
}