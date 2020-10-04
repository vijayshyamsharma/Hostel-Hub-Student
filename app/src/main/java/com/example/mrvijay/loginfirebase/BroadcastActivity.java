package com.example.mrvijay.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BroadcastActivity extends AppCompatActivity {


    static boolean check=false;

    static MenuItem textcpy;




    String uniqueone;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    MessageListAdapter messageListAdapter;

    String texttosend;

    ArrayList<MessageData> arrayList;

    String emailid,refinedemail;


    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);




        getSupportActionBar().setTitle("Inbox");
        arrayList=new ArrayList<>();
         emailid=getSharedPreferences("status",MODE_PRIVATE).getString("emailid",null);
         refinedemail=emailid.substring(0,emailid.indexOf('@'));


        recyclerView=findViewById(R.id.messagelist);
        progressBar=findViewById(R.id.progressBarMessageMenu);

        messageListAdapter=new MessageListAdapter(this,arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(messageListAdapter);




        firebaseDatabase=FirebaseDatabase.getInstance();





        getData();



    }

    void markseen()
    {

        databaseReference=firebaseDatabase.getReference("messageviews");




                long localcount=getSharedPreferences("status",MODE_PRIVATE).getLong(refinedemail,0);

                long onlinecount=arrayList.size();

                int val=(int)localcount;



                    for(int i=val;i<arrayList.size();i++)
                    {
                       /* String uniquekey=databaseReference.child(arrayList.get(i).getId()).push().getKey();
                        databaseReference.child(arrayList.get(i).getId()).child(uniquekey);
                        databaseReference.setValue(new SeenDetails(emailid,getDate(System.currentTimeMillis())));*/


                       databaseReference.child(arrayList.get(i).getId()).child(refinedemail).setValue(getDate(System.currentTimeMillis()));


                    }





    }

    void getData()
    {
        databaseReference=firebaseDatabase.getReference("allbroadcasts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                arrayList.clear();

                Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();

                for(DataSnapshot snapshot:iterable)
                {
                    arrayList.add(snapshot.getValue(MessageData.class));




                }

                messageListAdapter.notifyDataSetChanged();

                recyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());

                progressBar.setVisibility(View.GONE);


                markseen();

                getSharedPreferences("status",MODE_PRIVATE).edit().putLong("childcount",arrayList.size()).commit();
                getSharedPreferences("status",MODE_PRIVATE).edit().putLong(refinedemail,arrayList.size()).commit();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem=menu.findItem(R.id.textcopy);
        menuItem.setVisible(false);

        textcpy=menuItem;

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.textcopy,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.textcopy)
        {
            MessageListAdapter.resetallandcopy(1);


        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if(check==true)
        {
            check=false;
            MessageListAdapter.resetallandcopy(0);
        }
        else {
            super.onBackPressed();
        }

    }

    String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }




}
