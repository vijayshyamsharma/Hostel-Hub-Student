package com.example.mrvijay.loginfirebase;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ImageViewHolder> {

    static Context context22;


    static ArrayList<View> listpos=new ArrayList<>();

    static ArrayList<TextView> listtext=new ArrayList<>();

    Context context;
    ArrayList<MessageData> arrayList;

    public MessageListAdapter(Context context, ArrayList<MessageData> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
        context22=context;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.messagecustomlist,parent,false);

       ImageViewHolder imageViewHolder=new ImageViewHolder(view);

       return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {



        final MessageData messageData=arrayList.get(position);

        holder.message.setText(messageData.getMessage());
        holder.dateandtime.setText(messageData.getTime());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        TextView message,dateandtime;





        ConstraintLayout constraintLayout;

        public ImageViewHolder(@NonNull final View itemView) {
            super(itemView);

            message=itemView.findViewById(R.id.messagetext);
            dateandtime=itemView.findViewById(R.id.messagedatetime);

            constraintLayout=itemView.findViewById(R.id.messagecontainer);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    BroadcastActivity.textcpy.setVisible(true);
                    for(View view:listpos)
                    {
                        view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
                    }

                    listpos.clear();
                    listtext.clear();

                    itemView.setBackground(itemView.getResources().getDrawable(R.drawable.selectchatui));
                    listpos.add(itemView);

                    listtext.add((TextView) itemView.findViewById(R.id.messagetext));
                    BroadcastActivity.check=true;

                    return true;
                }
            });


            





        }
    }

    static void resetallandcopy(int val)
    {

        if(val==1)
        {
            for(View view:listpos)
            {

                view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
            }

            for(TextView textView:listtext)
            {
                ClipboardManager manager=(ClipboardManager) context22.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("message",textView.getText().toString());

                manager.setPrimaryClip(clipData);

                Toast.makeText(context22, "copied to clipboard", Toast.LENGTH_SHORT).show();
                BroadcastActivity.textcpy.setVisible(false);
                BroadcastActivity.check=false;
            }
        }
        else if(val==0)
        {
            for(View view:listpos)
            {

                view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
                BroadcastActivity.textcpy.setVisible(false);
            }

        }


    }




}
