package com.example.trip_expense;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ViewDetailTrip extends AppCompatActivity {

    Button delete_button, update_button, expense_button ;
    TextView trip_name, trip_destination, trip_date, trip_risk, trip_description, trip_partner, trip_duration;
    String trip_id, tripname, tripdestination, tripdate, triprisk, tripdescription, trippartner, tripduration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_detail_trip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Detail Trip");

        trip_id = getIntent().getStringExtra("id");

        tripname = getIntent().getStringExtra("name");
        trip_name = findViewById(R.id.nameText);
        trip_name.setText(tripname);

        tripdestination = getIntent().getStringExtra("destination");
        trip_destination = findViewById(R.id.destinationText);
        trip_destination.setText(tripdestination);

        tripdate =getIntent().getStringExtra("date");
        trip_date = findViewById(R.id.dateText);
        trip_date.setText(getIntent().getStringExtra("date"));

        triprisk = getIntent().getStringExtra("risk");
        trip_risk = findViewById(R.id.riskText);
        trip_risk.setText(getIntent().getStringExtra("risk"));

        tripdescription = getIntent().getStringExtra("description");
        trip_description = findViewById(R.id.descriptionText);
        trip_description.setText(getIntent().getStringExtra("description"));

        trippartner = getIntent().getStringExtra("partner");
        trip_partner = findViewById(R.id.partnerText);
        trip_partner.setText(getIntent().getStringExtra("partner"));

        tripduration = getIntent().getStringExtra("duration");
        trip_duration = findViewById(R.id.durationText);
        trip_duration.setText(getIntent().getStringExtra("duration"));

        delete_button = findViewById(R.id.deleteTrip);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(ViewDetailTrip.this);

                myDB.deleteOneTrip(trip_id);

                Intent intent = new Intent(ViewDetailTrip.this, MainActivity.class);
                startActivity(intent);
            }
        });

        update_button = findViewById(R.id.updateTrip);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDetailTrip.this, UpdateTrip.class);

                intent.putExtra("id",trip_id);
                intent.putExtra("name",tripname);
                intent.putExtra("destination",tripdestination);
                intent.putExtra("date",tripdate);
                intent.putExtra("risk",triprisk);
                intent.putExtra("description",tripdescription);
                intent.putExtra("partner",trippartner);
                intent.putExtra("duration",tripduration);

                startActivity(intent);
            }
        });

        expense_button = findViewById(R.id.expenseTrip);
        expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDetailTrip.this, ViewExpense.class);

                intent.putExtra("trip_id_FK", trip_id);
                intent.putExtra("trip_name", tripname);

                startActivity(intent);
            }
        });

    }

}
