package com.example.mrvijay.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessActivity extends AppCompatActivity {

    MenuMess menuMess;

    String weekday;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextInputEditText breakfastfood,lunchfood,dinnerfood;



    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        getSupportActionBar().setTitle("Mess Menu");

        breakfastfood=findViewById(R.id.breakfastfood);
        lunchfood=findViewById(R.id.lunchfood);
        dinnerfood=findViewById(R.id.dinnerfood);
        spinner=findViewById(R.id.spinnerweekdays);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                weekday=parent.getSelectedItem().toString();

                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();
    }

    void getData()
    {
        databaseReference=firebaseDatabase.getReference("messmenudetails").child(weekday);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                menuMess=dataSnapshot.getValue(MenuMess.class);

                if(menuMess==null)
                {
                    Toast.makeText(MessActivity.this, "warden haven't updated "+weekday+" menu", Toast.LENGTH_LONG).show();
                    breakfastfood.setText("");
                    lunchfood.setText("");
                    dinnerfood.setText("");

                }
                else {
                    breakfastfood.setText(menuMess.getBreakfastmenu());
                    lunchfood.setText(menuMess.getLunchmenu());
                    dinnerfood.setText(menuMess.getDinnermenu());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }
}
