package com.example.mrvijay.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Logged extends AppCompatActivity {


    String stname,outpurpose,outtime,outdate,hostelno;

        EditText fullname,purpose;
        EditText time,date;
        RadioGroup hostelsel;

        Button req;

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        String uniqueone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        getSupportActionBar().setTitle("Request Page");

        final String emailid=getSharedPreferences("status",MODE_PRIVATE).getString("emailid",null);

        final String refinedemail=emailid.substring(0,emailid.indexOf('@'));









        fullname=findViewById(R.id.editText3);
        purpose=findViewById(R.id.editText4);
        hostelsel=findViewById(R.id.hostelgroup);
        req=findViewById(R.id.button3);
        time=findViewById(R.id.textView5);
        date=findViewById(R.id.date);





        final ProgressDialog progressDialog=new ProgressDialog(Logged.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("waiting for admin response...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);


        firebaseDatabase=FirebaseDatabase.getInstance();


                req.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        progressDialog.show();

                         stname = fullname.getText().toString();
                         outpurpose = purpose.getText().toString();
                         outtime = time.getText().toString();
                         outdate=date.getText().toString();
                        int id = hostelsel.getCheckedRadioButtonId();


                        if (stname.length() != 0 & outpurpose.length() != 0 & outtime.length() != 0 & id != -1 & outdate.length()!=0) {
                            RadioButton radioButton = findViewById(id);

                             hostelno = radioButton.getText().toString();


                           DatabaseReference databaseReference1=firebaseDatabase.getReference("requeststatus").child(refinedemail);

                            String unique = databaseReference1.push().getKey();
                            uniqueone=unique;
                            databaseReference1.child(unique).setValue("status");

                            databaseReference = firebaseDatabase.getReference("all requests").child(unique);
                            DatabaseReference userrequestref=FirebaseDatabase.getInstance().getReference("users").child(refinedemail).child("requests").child(unique);



                            UserData userData=new UserData(outdate,stname,hostelno,"none",outpurpose,getDate(System.currentTimeMillis()),"waiting",outtime,unique,emailid,"none");

                            userrequestref.setValue(userData);
                            databaseReference.setValue(userData);






                            progressDialog.cancel();

                            Toast.makeText(Logged.this, "wait for warden response", Toast.LENGTH_SHORT).show();

                            fullname.setText("");
                            purpose.setText("");
                            date.setText("");
                            time.setText("");
                            hostelsel.clearCheck();




                        } else {
                            progressDialog.cancel();

                            Toast.makeText(Logged.this, "fill all details...", Toast.LENGTH_SHORT).show();
                        }


                    }


                });












    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.loggedmenu,menu);

       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent=new Intent(Logged.this,UserRequestListActivity.class);

        startActivity(intent);





        return true;
    }

  String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }


}


