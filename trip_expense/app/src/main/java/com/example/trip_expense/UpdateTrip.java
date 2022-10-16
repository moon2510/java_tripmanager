package com.example.trip_expense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateTrip extends AppCompatActivity {

    TextView trip_name, trip_destination, trip_date, trip_description, trip_partner, trip_duration;
    String trip_id;
    EditText tripname, tripdestination, tripdate, tripdescription, trippartner, tripduration;

    Button update_button;
    Spinner  triprisk;

    private final String[] riskOption = {
            "Yes",
            "No",
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_trip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update Trip");

        trip_id = getIntent().getStringExtra("id");

        trip_name = findViewById(R.id.nameUpdate);
        trip_name.setText(getIntent().getStringExtra("name"));

        trip_destination = findViewById(R.id.destinationUpdate);
        trip_destination.setText(getIntent().getStringExtra("destination"));

        trip_date = findViewById(R.id.dateUpdate);
        trip_date.setText(getIntent().getStringExtra("date"));

        triprisk = (Spinner) findViewById(R.id.spinnerRiskUpdate);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, riskOption);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        triprisk.setAdapter((dataAdapter));

        trip_description = findViewById(R.id.descriptionUpdate);
        trip_description.setText(getIntent().getStringExtra("description"));

        trip_partner = findViewById(R.id.partnerUpdate);
        trip_partner.setText(getIntent().getStringExtra("partner"));

        trip_duration = findViewById(R.id.durationUpdate);
        trip_duration.setText(getIntent().getStringExtra("duration"));

        tripname = findViewById(R.id.nameUpdate);
        tripdestination = findViewById(R.id.destinationUpdate);
        tripdate = findViewById(R.id.dateUpdate);
        triprisk = findViewById(R.id.spinnerRiskUpdate);
        tripdescription = findViewById(R.id.descriptionUpdate);
        trippartner = findViewById(R.id.partnerUpdate);
        tripduration = findViewById(R.id.durationUpdate);


        update_button = findViewById(R.id.updateTrip);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(UpdateTrip.this);

                myDB.updateTrip(
                        trip_id,
                        tripname.getText().toString(),
                        tripdestination.getText().toString(),
                        tripdate.getText().toString(),
                        triprisk.getSelectedItem().toString(),
                        tripdescription.getText().toString(),
                        trippartner.getText().toString(),
                        tripduration.getText().toString()

                );

                Intent intent = new Intent(UpdateTrip.this, MainActivity.class);

                startActivity(intent);
            }
        });


    }
}
