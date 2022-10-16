package com.example.trip_expense.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_expense.R;


import java.util.ArrayList;

public class AdapterExpense extends RecyclerView.Adapter<AdapterExpense.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList id, type, amount, time, comment;

    public AdapterExpense(Activity activity, Context context, ArrayList id, ArrayList type,
                       ArrayList amount, ArrayList time, ArrayList comment){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.comment = comment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_item_expense, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.ex_type.setText(String.valueOf(type.get(position)));
        holder.ex_amount.setText(String.valueOf(amount.get(position)));
        holder.ex_time.setText(String.valueOf(time.get(position)));
        holder.ex_comment.setText(String.valueOf(comment.get(position)));

    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, ex_type, ex_amount, ex_time, ex_comment ;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ex_type = itemView.findViewById(R.id.expense_type);
            ex_amount = itemView.findViewById(R.id.expense_amount);
            ex_time = itemView.findViewById(R.id.expense_time);
            ex_comment = itemView.findViewById(R.id.expense_comment);
        }

    }



}

