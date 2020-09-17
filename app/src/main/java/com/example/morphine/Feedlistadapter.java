package com.example.morphine;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Feedlistadapter extends RecyclerView.Adapter<Feedlistadapter.FeedViewHolder> {
    private List<Feeds> data;
    Webconnectivity webster;
    public Feedlistadapter(List<Feeds> data,Webconnectivity wbs)
    {
        this.data=data;this.webster=wbs;
    }

    class FeedViewHolder extends RecyclerView.ViewHolder{
        TextView t1;
        Button res;
        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.feedname);
            res=itemView.findViewById(R.id.btnapp);

        }
    }


    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator= LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.feedcontainer,parent,false);

        return new FeedViewHolder(view);
    }
public static String getstatus(String st)
{
    if(Integer.parseInt(st)==1){return "ON";}
    else{return "OFF";}
}

    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, final int position) {
        final Feeds Desc=data.get(position);
        holder.t1.setText("Appliance :"+Desc.getFeed_Key());
        if(Integer.parseInt(Desc.getFeed_value())==1){holder.res.setBackgroundColor(Color.parseColor("#00FF80"));}
        else{holder.res.setBackgroundColor(Color.parseColor("#ffff33"));}
        holder.res.setText(getstatus(Desc.getFeed_value()));
        holder.res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.res.getText().toString().equalsIgnoreCase("ON")){
                    try {
                        holder.res.setText("OFF");
                        holder.res.setBackgroundColor(Color.parseColor("#ffff33"));
                        webster.senddata(Desc.getFeed_Key(),false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    try {
                        holder.res.setBackgroundColor(Color.parseColor("#00FF80"));
                        holder.res.setText("ON");
                        webster.senddata(Desc.getFeed_Key(),true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    public void removeAt(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

    @Override
    public int getItemCount() {
        try{return data.size();}catch(Exception e){
            data = new ArrayList<Feeds>();
            return data.size();
        }
    }




}

