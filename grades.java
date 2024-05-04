package com.example.activity1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class grades extends AppCompatActivity {

    List<SchoolYear> semester;
    RecyclerView rvGrades;
    List<StudentGrade> studentGradeList;
    DBAccess da;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        da = new DBAccess(this);

        // Populate semester list from database
        semester = da.getSchoolYears();

        // Populate student grades list
       studentGradeList = GetListofGrades();

        // Set up spinner adapter
        SchoolYearSpinnerAdapter spinnerAdapter = new SchoolYearSpinnerAdapter(this, R.layout.activity_school_year_row, semester);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Find spinner and set adapter
        Spinner spinner = findViewById(R.id.spn_school_calendar);
        spinner.setAdapter(spinnerAdapter);

        // Initialize RecyclerView and its adapter


        // Set listener for spinner item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SchoolYear selectedItem = semester.get(position);
                // Update UI based on selected school year
                //updateGrades(selectedItem.SchoolYearId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });
    }
    // Method to update grades based on selected school year
    private void updateGrades(String schoolYearId) {
        rvGrades = findViewById(R.id.rvGrades);
        GradeRecycleViewAdapter gradeAdapter = new GradeRecycleViewAdapter();
        rvGrades.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvGrades.setItemAnimator(new DefaultItemAnimator());
        rvGrades.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvGrades.setAdapter(gradeAdapter);
        // Clear existing grade list
        studentGradeList.clear();
        // Get new grade list from database based on selected school year
        gradeAdapter.grade_list =da.getGrades(Integer.parseInt(schoolYearId));
        // Notify adapter of data change
        rvGrades.setAdapter(gradeAdapter);
    }

    // Method to populate initial list of grades (dummy data)
    private List<StudentGrade> GetListofGrades() {
        return da.getGrades(1);
    }
}
