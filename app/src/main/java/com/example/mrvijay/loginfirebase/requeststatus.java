package com.example.mrvijay.loginfirebase;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class requeststatus extends AppCompatActivity {

    ImageView imageView;
    TextView fullName,hostelno,datetime,transid,reason,reqgranttimedate,resultreq,reasonlabel;

    String requestedtime,wardenresponsetime,wardendes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requeststatus);

        getSupportActionBar().setTitle("Request Status Page");

       imageView=findViewById(R.id.imageviewicon);
        fullName=findViewById(R.id.studentname);

        hostelno=findViewById(R.id.hostelno);
        datetime=findViewById(R.id.outtimeanddate);
        transid=findViewById(R.id.transactionid);
        reason=findViewById(R.id.reason);
        reasonlabel=findViewById(R.id.textView8);
        reqgranttimedate=findViewById(R.id.reqgranttimedate);
        resultreq=findViewById(R.id.resultreq);




        Intent intent=getIntent();

        Bundle bundle=intent.getExtras();

        boolean status=bundle.getBoolean("status");
        String username=bundle.get("username").toString();
        String purpose1=bundle.get("purpose").toString();
        String hostel1=bundle.get("hostelno").toString();
        String date1=bundle.get("date").toString();
        String time1=bundle.get("time").toString();
        String transactionid1=bundle.get("transactionid").toString();
        requestedtime=bundle.get("requestedtime").toString();
        wardenresponsetime=bundle.get("wardenresponsetime").toString();
        wardendes=bundle.get("wardendes").toString();










        if(status==true)
        {
            imageView.setImageResource(R.drawable.correct);

            fullName.setText(username);

            hostelno.setText(hostel1);

            datetime.setText(time1+"\n"+date1);
            transid.setText(transactionid1);

            reqgranttimedate.setText(requestedtime+"\n"+wardenresponsetime);

            reasonlabel.setVisibility(View.GONE);
            reason.setVisibility(View.GONE);
            resultreq.setText("ACCEPTED");
            resultreq.setTextColor(getResources().getColor(R.color.accept));

        }
        else if(status==false)
        {
            imageView.setImageResource(R.drawable.unlike);
            fullName.setText(username);

            hostelno.setText(hostel1);

            datetime.setText(time1+"\n"+date1);
            transid.setText(transactionid1);

            reqgranttimedate.setText(requestedtime+"\n"+wardenresponsetime);

            reason.setText(wardendes);

            resultreq.setText("DENIED");
            resultreq.setTextColor(getResources().getColor(R.color.deny));


        }






    }

    String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();



    }
}
