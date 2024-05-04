package com.example.activity1;



import android.os.AsyncTask;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebApi extends AsyncTask<String, String, String> {

    DBAccess dbStudent;
    String user;
    String pass;
    ProgressBar load;

    public WebApi(Context con, String user, String pass, ProgressBar load) {
        dbStudent = new DBAccess(con);
        this.user = user;
        this.pass = pass;
        //this.load = load;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //load.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        try {
            URL url;
            HttpURLConnection conn = null;

            try {
                //url = new URL("http://10.0.2.2:5100/getStudRecord?studentId=" + user + "&birthdate=" + pass);
                url = new URL("http://192.168.0.3:5100/getStudRecord?studentId=" + user + "&birthdate=" + pass);
                //to open a url
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                int data = isw.read();
                while (data != -1) {
                    result += (char) data;
                    data = isw.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        Student stud_info;
        SchoolYear sy_list;
        StudentGrade gr_list;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray;

            dbStudent.deleteAllStudents();
            dbStudent.deleteAllSchoolYears();
            dbStudent.deleteAllGrades();

            JSONObject en = jsonObject.getJSONObject("studentProfile");
            stud_info = new Student();
            stud_info.LastName = en.getString("lastName");
            stud_info.FirstName = en.getString("firstName");
            stud_info.Course = en.getString("course");
            stud_info.Year = en.getString("year");
            dbStudent.addStudent(stud_info);

            jsonArray = jsonObject.getJSONArray("schoolYears");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject e = jsonArray.getJSONObject(i);

                sy_list = new SchoolYear();
                sy_list.SchoolYearId = e.getString("syId");
                sy_list.Semester = e.getString("semester");
                //sy_list. = e.getInt("id");
                sy_list.SchoolYearStart= e.getString("schoolYearStart");
                sy_list.SchoolYearEnd = e.getString("schoolYearEnd");

                dbStudent.addSchoolYear(sy_list);
            }

            jsonArray = jsonObject.getJSONArray("grades");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject e = jsonArray.getJSONObject(i);

                gr_list = new StudentGrade();
                gr_list.SyId = e.getString("syId");
                gr_list.GradeValue = e.getString("gradeValue");
                gr_list.SubjectCode = e.getString("subjectCode");
                gr_list.SubjectDescription = e.getString("subjectDescription");

                dbStudent.addGrade(gr_list);
            }

            //load.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}