package com.example.trip_expense;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TABLE_TRIP = "trips_table";

    public static final String ID_COLUMN = "trip_id";
    public static final String NAME_COLUMN = "name";
    public static final String DESTINATION_COLUMN = "destination";
    public static final String DATE_COLUMN = "date";
    public static final String RISK_COLUMN = "risk";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PARTNER_COLUMN = "partner";
    public static final String DURATION_COLUMN = "duration";

    private static final String TABLE_EXPENSE = "expense";

    public static final String ID_EXPENSE = "expense_id";
    public static final String TRIP_ID_EXPENSE = "tripId";
    public static final String TYPE_EXPENSE = "type";
    public static final String AMOUNT_EXPENSE = "amount";
    public static final String TIME_EXPENSE = "time";
    public static final String COMMENT_EXPENSE = "comment";



    private SQLiteDatabase database;
    private Context context;

    private static final String TRIP_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT , " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER)",
            TABLE_TRIP, ID_COLUMN, NAME_COLUMN,
            DESTINATION_COLUMN, DATE_COLUMN, RISK_COLUMN, DESCRIPTION_COLUMN, PARTNER_COLUMN, DURATION_COLUMN
    );

    private static final String EXPENSE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "FOREIGN KEY (tripId) REFERENCES trips_table(trip_id))",
            TABLE_EXPENSE, ID_EXPENSE, TRIP_ID_EXPENSE,
            TYPE_EXPENSE, AMOUNT_EXPENSE, TIME_EXPENSE, COMMENT_EXPENSE
    );

    public DBHelper(Context context){
        super(context, TABLE_TRIP, null, 1);
        database = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRIP_CREATE);
        db.execSQL(EXPENSE_CREATE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);

        Log.v(this.getClass().getName(), TABLE_TRIP +
                "database upgrade to version" + newVersion + " - old data lost"
        );

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);

        Log.v(this.getClass().getName(), TABLE_EXPENSE +
                "database upgrade to version" + newVersion + " - old data lost"
        );
        onCreate(db);
    }

    public void insertTrip(String name, String destination, String date, String risk, String description, String partner, String duration){
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, name);
        rowValues.put(DESTINATION_COLUMN, destination);
        rowValues.put(DATE_COLUMN, date);
        rowValues.put(RISK_COLUMN, risk);
        rowValues.put(DESCRIPTION_COLUMN, description);
        rowValues.put(PARTNER_COLUMN,partner);
        rowValues.put(DURATION_COLUMN, duration);

        long result =  database.insertOrThrow(TABLE_TRIP, null, rowValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_TRIP;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDataExpense(String tripId){
        String query = "SELECT * FROM expense WHERE tripId = " + tripId;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateTrip(String trip_id, String name, String destination, String date, String risk, String description, String partner, String duration){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(NAME_COLUMN, name);
        row.put(DESTINATION_COLUMN, destination);
        row.put(DATE_COLUMN, date);
        row.put(RISK_COLUMN, risk);
        row.put(DESCRIPTION_COLUMN, description);
        row.put(PARTNER_COLUMN, partner);
        row.put(DURATION_COLUMN, duration);

        long result = db.update(TABLE_TRIP, row, "trip_id=?", new String[]{trip_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneTrip(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TRIP, "trip_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllTrip(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TRIP);
    }

    //Expense DB
    public void insertExpense(Integer tripId, String type, String amount, String time, String comment){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TRIP_ID_EXPENSE, tripId);
        rowValues.put(TYPE_EXPENSE, type);
        rowValues.put(AMOUNT_EXPENSE, amount);
        rowValues.put(TIME_EXPENSE, time);
        rowValues.put(COMMENT_EXPENSE, comment);

        long result =  database.insertOrThrow(TABLE_EXPENSE, null, rowValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readTypeExpense(String tripId, String type){
        String query = "SELECT amount FROM expense WHERE tripId = " + tripId + " AND type = '" + type +"' " ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    //Search
    Cursor searchName(String name, String destination){
        String query = "SELECT * FROM trips_table " +
                        "WHERE name LIKE '%" + name + "%' " +
                        "AND destination LIKE '%" + destination + "%'" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


}
