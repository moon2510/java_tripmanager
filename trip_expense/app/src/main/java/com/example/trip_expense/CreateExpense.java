package com.example.trip_expense;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CreateExpense extends AppCompatActivity {

    EditText amount_expense, time_expense, comment;
    Button create_expense;
    String trip_id_FK, trip_name;

    DatePickerDialog picker;
    String amountType;

    private final String[] typeArray = {
            "Food",
            "Transport",
            "Travel",
            "Ticket",
            "Souvenir"
    };

    private Spinner spinnerTypeExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_expense);


        trip_id_FK = getIntent().getStringExtra("trip_id_FK");
        trip_name = getIntent().getStringExtra("trip_name");

        spinnerTypeExpense = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArray);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeExpense.setAdapter((dataAdapter));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Expense");

        spinnerTypeExpense = findViewById(R.id.spinnerType);
        amount_expense = findViewById(R.id.amountText);
        time_expense = findViewById(R.id.timeText);
        comment = findViewById(R.id.commentText);

        create_expense = findViewById(R.id.createExpense);
        create_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(CreateExpense.this);

                if (checkFieldRequire(amount_expense.getText().toString(), time_expense.getText().toString())){
                    myDB.insertExpense(Integer.valueOf(trip_id_FK),
                            spinnerTypeExpense.getSelectedItem().toString(),
                            amount_expense.getText().toString(),
                            time_expense.getText().toString(),
                            comment.getText().toString()
                    );

                    Intent intent = new Intent(CreateExpense.this, ViewExpense.class);
                    intent.putExtra("trip_id_FK", trip_id_FK);
                    intent.putExtra("trip_name", trip_name);

                    startActivity(intent);
                }

            }
        });

        time_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateExpense.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                time_expense.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });






    }

    private boolean checkFieldRequire(String amount, String time){
        if (amount.equals("") || time.equals("")){
            Toast.makeText(this, "Please fill all require fields", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }




}
