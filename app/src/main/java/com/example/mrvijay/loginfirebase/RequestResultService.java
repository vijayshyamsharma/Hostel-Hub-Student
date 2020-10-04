package com.example.mrvijay.loginfirebase;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RequestResultService extends Service {

    String usermail;

    UserData userData;

    String reqtransid;

    boolean check=false;

    String CHANNEL_ID = "my_channel_01";
    int notifyID = 1;

    Notification notification;
    NotificationManager mNotificationManager;


    Context context;

    public RequestResultService(Context applicationContext) {
        super();
        context = applicationContext;
        Log.i("HERE", "here service created!");
    }

    public RequestResultService() {
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("HERE", "here service created!");

        final String emailid=getSharedPreferences("status",MODE_PRIVATE).getString("emailid",null);

        usermail=emailid.substring(0,emailid.indexOf('@'));


       Log.d("usermail",usermail);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("requeststatus").child(usermail);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                reqtransid=dataSnapshot.getKey();

                fetchAllDetails();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        checkBroadcast();





        return START_STICKY;
    }


    void checkBroadcast()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("allbroadcasts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long localcount=getSharedPreferences("status",MODE_PRIVATE).getLong("childcount",0);

                long onlinecount=dataSnapshot.getChildrenCount();

                if(onlinecount>localcount)
                {
                    createNotification1();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);



    }


    void fetchAllDetails()
    {


        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users").child(usermail).child("requests").child(reqtransid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userData=dataSnapshot.getValue(UserData.class);

                createNotification(userData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();




        Intent iHeartBeatService = new Intent(RequestResultService.this,
                RequestResultService.class);
        PendingIntent piHeartBeatService = PendingIntent.getService(this, 0,
                iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piHeartBeatService);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), 1000, piHeartBeatService);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();






    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void createNotification(UserData userData1) {

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        Intent intent = new Intent(getBaseContext(), requeststatus.class);

        intent.putExtra("email",userData.getUsermail());
        intent.putExtra("hostelno",userData.getHostelName());
        intent.putExtra("date",userData.getDate());
        intent.putExtra("time",userData.getTime());

        if(userData.getStatus().equalsIgnoreCase("accepted"))
        {
            intent.putExtra("status",true);
        }
        else if(userData.getStatus().equalsIgnoreCase("denied"))
        {
            intent.putExtra("status",false);
        }

        intent.putExtra("username",userData.getFullName());
        intent.putExtra("purpose",userData.getPurpose());
        intent.putExtra("transactionid",userData.getTransid());
        intent.putExtra("requestedtime",userData.getRequestedtime());
        intent.putExtra("wardenresponsetime",userData.getWardenrespondedtime());
        intent.putExtra("wardendes",userData.getIfdenytext());


        TaskStackBuilder stackBuilder=TaskStackBuilder.create(getBaseContext());

        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);




        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setAutoCancel(true).setSmallIcon(R.drawable.hostel)


                .setContentText("Warden has responded to your request")
                .setContentTitle("Request Response")
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setChannelId(CHANNEL_ID);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            CharSequence name = "Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableLights(true);

            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{150, 100, 150, 100});

            mChannel.setShowBadge(true);


            mNotificationManager.createNotificationChannel(mChannel);


        }

        notification = builder.build();


        mNotificationManager.notify(notifyID, notification);

    }


    void createNotification1()
    {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        Intent intent = new Intent(getBaseContext(), BroadcastActivity.class);

        TaskStackBuilder stackBuilder=TaskStackBuilder.create(getBaseContext());

        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setAutoCancel(true).setSmallIcon(R.drawable.hostel)


                .setContentText("Warden broadcasted a message")
                .setContentTitle("Announcement")
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setChannelId(CHANNEL_ID);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            CharSequence name = "Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableLights(true);

            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{150, 100, 150, 100});

            mChannel.setShowBadge(true);


            mNotificationManager.createNotificationChannel(mChannel);


        }

        notification = builder.build();


        mNotificationManager.notify(notifyID, notification);
    }
}
