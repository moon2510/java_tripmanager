package com.example.trip_expense;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateTripActivity extends AppCompatActivity {
    EditText trip_name, trip_destination, trip_date, trip_description, trip_partner, trip_duration;
    Button create_button;

    DatePickerDialog picker;

    private Context context;

    private final String[] riskOption = {
            "Yes",
            "No",
    };

    private Spinner trip_risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_trip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Trip");

        //Enter Date
        trip_date=(EditText) findViewById(R.id.dateText);
        trip_date.setInputType(InputType.TYPE_NULL);

        trip_risk = (Spinner) findViewById(R.id.spinnerRisk);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, riskOption);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trip_risk.setAdapter((dataAdapter));

        trip_name = findViewById(R.id.nameText);
        trip_destination = findViewById(R.id.destinationText);
        trip_risk = findViewById(R.id.spinnerRisk);
        trip_description = findViewById(R.id.descriptionText);
        trip_partner = findViewById(R.id.partnerText);
        trip_duration = findViewById(R.id.durationText);

        create_button = findViewById(R.id.createTrip);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveTrip(trip_name.getText().toString(),
                        trip_destination.getText().toString(),
                        trip_date.getText().toString(),
                        trip_risk.getSelectedItem().toString(),
                        trip_description.getText().toString(),
                        trip_partner.getText().toString(),
                        trip_duration.getText().toString()
                );

            }
        });

        trip_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                trip_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }

    private boolean checkFieldRequire(String name, String destination, String date, String duration){
        if (name.equals("") || destination.equals("") || date.equals("") || duration.equals("")){
            Toast.makeText(this, "Please fill all require fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private  void saveTrip(String name, String destination, String date, String risk, String description, String partner, String duration){
        if (checkFieldRequire(name, destination, date, duration)){
            new AlertDialog.Builder(this).setTitle("Confirm Detail Trip").setMessage(
                    "\nName of Trip: " + name +
                    "\nDestination: " + destination +
                    "\nDate: " + date +
                    "\nRisk Assessment: " + risk +
                    "\nDescription: " + description +
                    "\nPartner: " + partner +
                    "\nDuration: " + duration + "days"
            ).setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DBHelper myDB = new DBHelper(CreateTripActivity.this);

                    myDB.insertTrip(name, destination, date, risk, description, partner, duration);

                    Intent intent = new Intent(CreateTripActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }).show();
        }
    }

}
