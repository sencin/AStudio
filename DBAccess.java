package com.example.activity1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBAccess extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dtStudent";
    static SQLiteDatabase dbStudent;

    public DBAccess(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbStudent = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase dbStudent) {
        String sql;

        sql = "CREATE TABLE tblName (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "course TEXT," +
                "year TEXT)";
        dbStudent.execSQL(sql);

        sql = "CREATE TABLE tblSchoolYear (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "schoolYearStart TEXT," +
                "schoolYearEnd TEXT," +
                "semester TEXT," +
                "syId INTEGER)";
        dbStudent.execSQL(sql);

        sql = "CREATE TABLE tblGrades (" +
                "gradeId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "subjectCode TEXT, " +
                "subjectDescription TEXT, " + // corrected the column name
                "gradeValue TEXT, " +
                "syId INTEGER)";
        dbStudent.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbStudent, int oldVersion, int newVersion) {
        dbStudent.execSQL("DROP TABLE IF EXISTS tblName");
        dbStudent.execSQL("DROP TABLE IF EXISTS tblSchoolYear");
        dbStudent.execSQL("DROP TABLE IF EXISTS tblGrades");
    }

    public void deleteAllStudents() {
        dbStudent.delete("tblName", null, null);
    }

    public void deleteAllSchoolYears() {
        dbStudent.delete("tblSchoolYear", null, null);
    }

    public void deleteAllGrades() {
        dbStudent.delete("tblGrades", null, null);
    }

    public void addStudent(Student student) {
        // Implement addStudent method
    }

    public void addSchoolYear(SchoolYear schoolYear) {
        ContentValues values = new ContentValues();
        values.put("schoolYearStart", schoolYear.SchoolYearStart);
        values.put("schoolYearEnd", schoolYear.SchoolYearEnd);
        values.put("semester", schoolYear.Semester);
        values.put("syId", schoolYear.SchoolYearId);

        dbStudent.insert("tblSchoolYear", null, values);
    }

    public List<SchoolYear> getSchoolYears() {
        Cursor c = dbStudent.rawQuery("SELECT * FROM tblSchoolYear ORDER BY schoolYearStart, schoolYearEnd, semester, syId", null);
        c.moveToFirst();

        List<SchoolYear> schoolYearList = new ArrayList<>();
        while (!c.isAfterLast()) {
            SchoolYear schoolYear = new SchoolYear();
            schoolYear.SchoolYearStart = c.getString(1);
            schoolYear.SchoolYearEnd = c.getString(2);
            schoolYear.Semester = c.getString(3);
            schoolYear.SchoolYearId = c.getString(4);
            schoolYearList.add(schoolYear);
            c.moveToNext();
        }
        c.close();
        return schoolYearList;
    }

    public void addGrade(StudentGrade studentGrade) {
        ContentValues values = new ContentValues();
        values.put("subjectCode", studentGrade.SubjectCode);
        values.put("subjectDescription", studentGrade.SubjectDescription);
        values.put("gradeValue", studentGrade.GradeValue);
        values.put("syId", studentGrade.SyId);

        dbStudent.insert("tblGrades", null, values);
    }

    public List<StudentGrade> getGrades(int syId) {
        Cursor c = dbStudent.rawQuery("SELECT gradeId, subjectCode, subjectDescription, gradeValue, syId FROM tblGrades WHERE syId = ?",
                new String[]{String.valueOf(syId)});
        c.moveToFirst();

        List<StudentGrade> gradeList = new ArrayList<>();
        while (!c.isAfterLast()) {
            StudentGrade studentGrade = new StudentGrade();
            studentGrade.Id = c.getInt(0);
            studentGrade.SubjectCode = c.getString(1);
            studentGrade.SubjectDescription = c.getString(2);
            studentGrade.GradeValue = c.getString(3);
            studentGrade.SyId = c.getString(4);
            gradeList.add(studentGrade);
            c.moveToNext();
        }
        c.close();
        return gradeList;
    }
}
