package com.example.activity1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class SchoolYearSpinnerAdapter extends ArrayAdapter<SchoolYear> {
    private List<SchoolYear> schoolYearList;

    private Context context;

    public SchoolYearSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<SchoolYear> objects){
        super(context, resource, objects);

        this.schoolYearList = objects;
        this.context = context;
    }
    @Override
    public int getCount(){
        return schoolYearList.size();
    }

    @Override
    public SchoolYear getItem(int i){
        return schoolYearList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_school_year_row, viewGroup, false);
        }
        SchoolYear schoolYear = schoolYearList.get(i);

        TextView SchoolYearStart = view.findViewById(R.id.txtSchoolYearStart);
        TextView SchoolYearEnd = view.findViewById(R.id.txtSchoolYearEnd);
        TextView Semester = view.findViewById(R.id.txtSemester);

        SchoolYearStart.setText(schoolYear.SchoolYearStart);
        SchoolYearEnd.setText(schoolYear.SchoolYearEnd);
        Semester.setText(schoolYear.Semester);

        return view;

    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position, convertView, parent);
    }

}






