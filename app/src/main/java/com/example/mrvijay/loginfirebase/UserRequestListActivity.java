package com.example.mrvijay.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UserRequestListActivity extends AppCompatActivity {

    TextView totalcont;
    RecyclerView recyclerView;
    RequestListAdapter requestListAdapter;

    ProgressBar progressBar;

    String refinedemail;

    ArrayList<UserData> dataArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_list);

        getSupportActionBar().setTitle("All Requests");

        final String emailid=getSharedPreferences("status",MODE_PRIVATE).getString("emailid",null);

         refinedemail=emailid.substring(0,emailid.indexOf('@'));

        dataArrayList=new ArrayList<>();

        totalcont=findViewById(R.id.totalcont);
        recyclerView=findViewById(R.id.contitems);
        progressBar=findViewById(R.id.progressBar1);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestListAdapter=new RequestListAdapter(dataArrayList,this);

        recyclerView.setAdapter(requestListAdapter);

        getData();
    }

    void getData()
    {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users").child(refinedemail).child("requests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dataArrayList.clear();



                Iterable<DataSnapshot> dataSnapshotIterable=dataSnapshot.getChildren();

                for(DataSnapshot snapshot:dataSnapshotIterable)
                {

                    dataArrayList.add(snapshot.getValue(UserData.class));


                }

                Collections.reverse(dataArrayList);

                totalcont.setText(dataArrayList.size()+" requests");

                requestListAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
