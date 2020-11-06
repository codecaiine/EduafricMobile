package com.example.codecaiine.eduafricmobile.activity;

/**
 * Created by codecaiine on 09/04/2018.
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import com.example.codecaiine.eduafricmobile.R;

public class UpdateActivity extends AppCompatActivity {

    String HttpURL = "https://androidjsonblog.000webhostapp.com/Student/UpdateStudent.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText StudentName, StudentPhoneNumber, StudentClass;
    Button UpdateStudent;
    String IdHolder, StudentNameHolder, StudentPhoneHolder, StudentClassHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        StudentName = (EditText)findViewById(R.id.editName);
        StudentPhoneNumber = (EditText)findViewById(R.id.editPhoneNumber);
        StudentClass = (EditText)findViewById(R.id.editClass);

        UpdateStudent = (Button)findViewById(R.id.UpdateButton);

        // Receive Student ID, Name , Phone Number , Class Send by previous ShowSingleRecordActivity.
        IdHolder = getIntent().getStringExtra("Id");
        StudentNameHolder = getIntent().getStringExtra("Name");
        StudentPhoneHolder = getIntent().getStringExtra("Number");
        StudentClassHolder = getIntent().getStringExtra("Class");

        // Setting Received Student Name, Phone Number, Class into EditText.
        StudentName.setText(StudentNameHolder);
        StudentPhoneNumber.setText(StudentPhoneHolder);
        StudentClass.setText(StudentClassHolder);

        // Adding click listener to update button .
        UpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Student Name, Phone Number, Class to method to update on server.
                StudentRecordUpdate(IdHolder,StudentNameHolder,StudentPhoneHolder, StudentClassHolder);

            }
        });


    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText(){

        StudentNameHolder = StudentName.getText().toString();
        StudentPhoneHolder = StudentPhoneNumber.getText().toString();
        StudentClassHolder = StudentClass.getText().toString();

    }

    // Method to Update Student Record.
    public void StudentRecordUpdate(final String ID, final String S_Name, final String S_Phone, final String S_Class){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("StudentID",params[0]);

                hashMap.put("StudentName",params[1]);

                hashMap.put("StudentPhone",params[2]);

                hashMap.put("StudentClass",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();

        studentRecordUpdateClass.execute(ID,S_Name,S_Phone,S_Class);
    }
}
