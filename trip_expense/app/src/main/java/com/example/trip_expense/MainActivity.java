package com.example.trip_expense;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trip_expense.Adapter.AdapterTrip;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button, delete_button;
    ImageView empty_imageview;
    TextView no_data;

    DBHelper db;
    ArrayList<Integer> id;
    ArrayList<String> name, destination, date, risk, description, partner, duration;
    AdapterTrip customAdapter;
    EditText searchByName, searchByDestination;
    ImageButton search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("All Trips");


        searchByName = findViewById(R.id.search_name);
        searchByDestination = findViewById(R.id.search_destination);


        search_button= findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id.clear();
                name.clear();
                destination.clear();
                date.clear();
                risk.clear();
                description.clear();
                partner.clear();
                duration.clear();

                getSearch(searchByName.getText().toString(), searchByDestination.getText().toString());

                recyclerView.setAdapter(customAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        delete_button= findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(MainActivity.this);

                myDB.deleteAllTrip();

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

        recyclerView = findViewById(R.id.recyclerView);

        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTripActivity.class);
                startActivity(intent);
            }
        });


        db = new DBHelper(MainActivity.this);

        id = new ArrayList<>();
        name = new ArrayList<>();
        destination = new ArrayList<>();
        date = new ArrayList<>();
        risk = new ArrayList<>();
        description = new ArrayList<>();
        partner = new ArrayList<>();
        duration = new ArrayList<>();

        getSearch(searchByName.getText().toString(), searchByDestination.getText().toString());


        customAdapter = new AdapterTrip(MainActivity.this,this, id, name, destination,
                date, risk, description, partner, duration);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

//    void storeTripData(){
//        Cursor cursor = db.readAllData();
//        if(cursor.getCount()==0){
//            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            while (cursor.moveToNext()){
//                id.add(cursor.getInt(0));
//                name.add(cursor.getString(1));
//                destination.add(cursor.getString(2));
//                date.add(cursor.getString(3));
//                risk.add(cursor.getString(4));
//                description.add(cursor.getString(5));
//                partner.add(cursor.getString(6));
//                duration.add(cursor.getString(7));
//            }
//        }
//    }


    void getSearch(String searchName, String searchDestination){
        Cursor cursor = db.searchName(searchName, searchDestination);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){
                id.add(cursor.getInt(0));
                name.add(cursor.getString(1));
                destination.add(cursor.getString(2));
                date.add(cursor.getString(3));
                risk.add(cursor.getString(4));
                description.add(cursor.getString(5));
                partner.add(cursor.getString(6));
                duration.add(cursor.getString(7));
            }
        }
    }

//    void check(){
//        if (searchNew=="" ){
//            storeTripData();
//        }
//        else{
//            getSearch(searchNew);
//        }
//    }


}

