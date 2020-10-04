package com.example.mrvijay.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ImageViewHolder> {


    ArrayList<UserData> data;

    Context context;

    public RequestListAdapter(ArrayList<UserData> arrayList, Context context)
    {


        data=arrayList;


        this.context=context;

    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final UserData userData=data.get(position);

        holder.studentname.setText(userData.getFullName());
        holder.purpose.setText(userData.getPurpose());
        holder.hostelname.setText(userData.getHostelName());
        holder.outtime.setText(userData.getTime());
        holder.outdate.setText(userData.getDate());
        holder.useremail.setText(userData.getUsermail());
        holder.transid.setText(userData.getTransid());
        String status=userData.getStatus().toUpperCase();


            holder.requestTime.setText(userData.getRequestedtime());
            holder.respondTime.setText(userData.getWardenrespondedtime());

            holder.wardenresponse.setVisibility(View.VISIBLE);
            holder.wardenresponse.setText(status);

            if(status.equalsIgnoreCase("accepted"))
            {
                holder.wardenresponse.setTextColor(context.getResources().getColor(R.color.accept));

                holder.reqstatuspage.setVisibility(View.VISIBLE);

                holder.reqstatuspage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            Intent intent=new Intent(context,requeststatus.class);
                            intent.putExtra("email",userData.getUsermail());
                            intent.putExtra("hostelno",userData.getHostelName());
                            intent.putExtra("date",userData.getDate());
                            intent.putExtra("time",userData.getTime());
                            intent.putExtra("status",true);
                            intent.putExtra("username",userData.getFullName());
                            intent.putExtra("purpose",userData.getPurpose());
                            intent.putExtra("transactionid",userData.getTransid());
                        intent.putExtra("requestedtime",userData.getRequestedtime());
                        intent.putExtra("wardenresponsetime",userData.getWardenrespondedtime());
                        intent.putExtra("wardendes",userData.getIfdenytext());


                            context.startActivity(intent);

                    }
                });

            }
            else if (status.equalsIgnoreCase("denied"))
            {
                holder.wardenresponse.setTextColor(context.getResources().getColor(R.color.deny));

                holder.reqstatuspage.setVisibility(View.VISIBLE);

                holder.reqstatuspage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        Intent intent=new Intent(context,requeststatus.class);

                        intent.putExtra("email",userData.getUsermail());
                        intent.putExtra("hostelno",userData.getHostelName());
                        intent.putExtra("date",userData.getDate());
                        intent.putExtra("time",userData.getTime());
                        intent.putExtra("status",false);
                        intent.putExtra("username",userData.getFullName());
                        intent.putExtra("purpose",userData.getPurpose());
                        intent.putExtra("transactionid",userData.getTransid());
                        intent.putExtra("requestedtime",userData.getRequestedtime());
                        intent.putExtra("wardenresponsetime",userData.getWardenrespondedtime());
                        intent.putExtra("wardendes",userData.getIfdenytext());



                        context.startActivity(intent);
                    }
                });
            }







    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customrequestlist,parent,false);

        ImageViewHolder imageViewHolder=new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {

        TextView studentname,purpose,hostelname,outtime,outdate,useremail,transid,wardenresponse,reqstatuspage,requestTime,respondTime;



        ConstraintLayout constraintLayout;




        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            studentname=itemView.findViewById(R.id.studentname);
            purpose=itemView.findViewById(R.id.purposevalue);
            hostelname=itemView.findViewById(R.id.hostelvalue);
            outtime=itemView.findViewById(R.id.touttimevalue);
            outdate=itemView.findViewById(R.id.outdatevalue);
            useremail=itemView.findViewById(R.id.useremailvalue);
            transid=itemView.findViewById(R.id.transidvalue);

            reqstatuspage=itemView.findViewById(R.id.studentreqstatuspage);



            wardenresponse=itemView.findViewById(R.id.wardenresponse);

            requestTime=itemView.findViewById(R.id.requesttime);
            respondTime=itemView.findViewById(R.id.grantedtime);


        }
    }
}
