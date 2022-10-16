package com.example.trip_expense;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trip_expense.Adapter.AdapterExpense;
import com.example.trip_expense.Adapter.AdapterTrip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewExpense extends AppCompatActivity {

    DBHelper db;

    FloatingActionButton add_expense, chart_expense, home_button;
    String trip_id_FK, trip_name;

    RecyclerView recyclerView;
    ImageView empty_imageview;
    TextView no_data;

    ArrayList<String> id, type, amount, time, comment;
    AdapterExpense customAdapter;

    ArrayList<String> amountFood, amountTrans, amountTravel, amountTicket, amountSouvenir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_expense);

        trip_id_FK = getIntent().getStringExtra("trip_id_FK");
        trip_name = getIntent().getStringExtra("trip_name");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Expenses - " + trip_name);

        recyclerView = findViewById(R.id.recyclerViewEX);

        empty_imageview = findViewById(R.id.empty_imageviewEX);
        no_data = findViewById(R.id.no_data);


        add_expense = findViewById(R.id.add_expense);
        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewExpense.this, CreateExpense.class);

                intent.putExtra("trip_id_FK", trip_id_FK);
                intent.putExtra("trip_name", trip_name);

                startActivity(intent);
            }
        });

        chart_expense = findViewById(R.id.chart_button);
        chart_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewExpense.this, CreateChart.class);

                intent.putExtra("food", amountFood);
                intent.putExtra("trans", amountTrans);
                intent.putExtra("travel", amountTravel);
                intent.putExtra("ticket", amountTicket);
                intent.putExtra("souvenir", amountSouvenir);

                startActivity(intent);
            }
        });

        home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewExpense.this, MainActivity.class);

                startActivity(intent);
            }
        });


        db = new DBHelper(ViewExpense.this);

        id = new ArrayList<>();
        type = new ArrayList<>();
        amount = new ArrayList<>();
        time = new ArrayList<>();
        comment = new ArrayList<>();

        amountFood = new ArrayList<>();
        amountTrans = new ArrayList<>();
        amountTravel = new ArrayList<>();
        amountTicket = new ArrayList<>();
        amountSouvenir = new ArrayList<>();

        storeExpenseData();
        storeAmountFood();
        storeAmountTrans();
        storeAmountTravel();
        storeAmountTicket();
        storeAmountSouvenir();

        customAdapter = new AdapterExpense(ViewExpense.this,this, id, type, amount, time, comment);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewExpense.this));

    }

    void storeExpenseData(){
        Cursor cursor = db.readAllDataExpense(trip_id_FK);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                type.add(cursor.getString(2));
                amount.add(cursor.getString(3));
                time.add(cursor.getString(4));
                comment.add(cursor.getString(5));
            }
        }
    }

    void storeAmountFood(){
        Cursor cursor = db.readTypeExpense(trip_id_FK,"Food");
        while (cursor.moveToNext()){
            amountFood.add(cursor.getString(0));
        }
    }

    void storeAmountTrans(){
        Cursor cursor = db.readTypeExpense(trip_id_FK,"Transport");
        while (cursor.moveToNext()){
            amountTrans.add(cursor.getString(0));
        }
    }

    void storeAmountTravel(){
        Cursor cursor = db.readTypeExpense(trip_id_FK,"Travel");
        while (cursor.moveToNext()){
            amountTravel.add(cursor.getString(0));
        }
    }

    void storeAmountTicket(){
        Cursor cursor = db.readTypeExpense(trip_id_FK,"Ticket");
        while (cursor.moveToNext()){
            amountTicket.add(cursor.getString(0));
        }
    }

    void storeAmountSouvenir(){
        Cursor cursor = db.readTypeExpense(trip_id_FK,"Souvenir");

        while (cursor.moveToNext()){
            amountSouvenir.add(cursor.getString(0));
        }

    }


}
