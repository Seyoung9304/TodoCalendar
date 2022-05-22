package edu.skku.cs.todocalendar.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.skku.cs.todocalendar.R;

public class RegisterActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    EditText pw_check;
    Button id_check;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id = findViewById(R.id.edit_id);
        pw = findViewById(R.id.edit_pw);
        pw_check = findViewById(R.id.edit_pw_verify);
        id_check = findViewById(R.id.b_id_check);
        register = findViewById(R.id.b_register);

        Boolean id_valid = false;
        Boolean password_correct = false;

        // Check if ID already exists
        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Post user info
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}