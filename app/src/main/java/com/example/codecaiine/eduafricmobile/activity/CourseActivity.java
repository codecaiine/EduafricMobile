package com.example.codecaiine.eduafricmobile.activity;

/**
 * Created by codecaiine on 09/04/2018.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codecaiine.eduafricmobile.R;

import java.util.HashMap;


public class CourseActivity extends AppCompatActivity {

    EditText StudentName, StudentPhoneNumber, StudentClass;
    Button RegisterStudent, ShowCourses;
    String StudentNameHolder, StudentPhoneHolder, StudentClassHolder;
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "https://androidjsonblog.000webhostapp.com/Student/StudentRegister.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        StudentName = (EditText)findViewById(R.id.editName);
        StudentPhoneNumber = (EditText)findViewById(R.id.editPhoneNumber);
        StudentClass = (EditText)findViewById(R.id.editClass);

        RegisterStudent = (Button)findViewById(R.id.btnAdd);
        ShowCourses = (Button)findViewById(R.id.btnAdd);

        RegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    StudentRegistration(StudentNameHolder,StudentPhoneHolder, StudentClassHolder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(CourseActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

        ShowCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CourseActivity.this,ShowAllCourseActivity.class);
                startActivity(intent);

            }
        });


    }

    public void StudentRegistration(final String S_Name, final String S_Phone, final String S_Class){

        class StudentRegistrationClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(CourseActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(CourseActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("StudentName",params[0]);

                hashMap.put("StudentPhone",params[1]);

                hashMap.put("StudentClass",params[2]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRegistrationClass studentRegistrationClass = new StudentRegistrationClass();

        studentRegistrationClass.execute(S_Name,S_Phone,S_Class);
    }


    public void CheckEditTextIsEmptyOrNot(){

        StudentNameHolder = StudentName.getText().toString();
        StudentPhoneHolder = StudentPhoneNumber.getText().toString();
        StudentClassHolder = StudentClass.getText().toString();

        if(TextUtils.isEmpty(StudentNameHolder) || TextUtils.isEmpty(StudentPhoneHolder) || TextUtils.isEmpty(StudentClassHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
}
