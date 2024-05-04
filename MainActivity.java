package com.example.activity1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGrades = findViewById(R.id.btnGrades);

        // Set onClickListeners for buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Menu.class));
            }
        });

        btnGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, grades.class));
            }
        });

        // Sample student data
        String studentId = "";
        String birthdate = "";

        // Execute WebApi to fetch student data
         WebApi webApi = new WebApi(this, studentId, birthdate, new ProgressBar(this));
         webApi.execute();
    }
}
