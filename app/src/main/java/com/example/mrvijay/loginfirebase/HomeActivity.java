package com.example.mrvijay.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    ImageView openPermission,openMess,openEnquiry;

    String adminmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home Page");



        openPermission=findViewById(R.id.opnepermissionactivity);
        openMess=findViewById(R.id.openmessactivity);
        openEnquiry=findViewById(R.id.openenquiryactivity);

        RequestResultService mSensorService = new RequestResultService(this);
        Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("adminid").child("admin email1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adminmail=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        openPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,Logged.class);
                startActivity(intent);
            }
        });

        openMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,MessActivity.class);
                startActivity(intent);
            }
        });

        openEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent contact=new Intent(Intent.ACTION_SENDTO);
                contact.setData(Uri.parse("mailto:"+adminmail));

                try
                {
                    startActivity(contact);
                }
                catch(ActivityNotFoundException e){}
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logoutmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.logout12)
        {
            getSharedPreferences("status",MODE_PRIVATE).edit().putBoolean("onlineStatus",false).commit();
            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);

            startActivity(intent);
            finish();

        }
        else if(id==R.id.inbox)
        {
            Intent intent=new Intent(HomeActivity.this,BroadcastActivity.class);
            startActivity(intent);
        }


        return true;
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
}
