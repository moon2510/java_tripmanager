package com.example.trip_expense.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_expense.R;
import com.example.trip_expense.ViewDetailTrip;

import java.util.ArrayList;

public class AdapterTrip extends RecyclerView.Adapter<AdapterTrip.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id, name, destination, date, risk, description, partner, duration;

    public AdapterTrip(Activity activity, Context context, ArrayList id, ArrayList name, ArrayList destination,
                       ArrayList date, ArrayList risk, ArrayList description, ArrayList partner, ArrayList duration){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.name = name;
        this.destination = destination ;
        this.date = date;
        this.risk = risk;
        this.description = description;
        this.partner = partner;
        this.duration = duration;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_item_trip, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.trip_id.setText(String.valueOf(id.get(position)));
        holder.trip_name.setText(String.valueOf(name.get(position)));
        holder.trip_destination.setText(String.valueOf(destination.get(position)));
        holder.trip_date.setText(String.valueOf(date.get(position)));
        holder.trip_risk.setText(String.valueOf(risk.get(position)));
        holder.trip_duration.setText(String.valueOf(duration.get(position)));
        //Recyclerview onClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewDetailTrip.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("destination", String.valueOf(destination.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("risk", String.valueOf(risk.get(position)));
                intent.putExtra("description", String.valueOf(description.get(position)));
                intent.putExtra("partner", String.valueOf(partner.get(position)));
                intent.putExtra("duration", String.valueOf(duration.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_id, trip_name, trip_destination, trip_date, trip_risk, trip_duration  ;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            trip_id = itemView.findViewById(R.id.trip_id);
            trip_name = itemView.findViewById(R.id.trip_name);
            trip_destination = itemView.findViewById(R.id.trip_destination);
            trip_date = itemView.findViewById(R.id.trip_date);
            trip_risk = itemView.findViewById(R.id.trip_risk);
            trip_duration = itemView.findViewById(R.id.trip_duration);

        }

    }



}
