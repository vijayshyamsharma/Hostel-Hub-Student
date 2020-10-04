package com.example.mrvijay.loginfirebase;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Mr.vijay on 28-10-2018.
 */

public class MyAdapter extends ArrayAdapter {

    Context context;




     public MyAdapter(Context context)
    {
        super(context,R.layout.chatlist);

        this.context=context;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


         View view=null;

         try {
             LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

             view = inflater.inflate(R.layout.chatlist, null);

             TextView textView = view.findViewById(R.id.chatname);


             User user = (User) this.getItem(position);

             String text = user.name;

             textView.setText(text);

         }
         catch (InflateException e){

             Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
         }






        return view;
    }
}
