package com.example.trip_expense;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import kotlin.collections.FloatIterator;

public class CreateChart extends AppCompatActivity {

    ArrayList<String> amountFood, amountTrans, amountTravel, amountTicket, amountSouvenir;
    Float food, trans, travel, ticket, souvenir;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_trip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Expense Chart");

        BarChart chart = findViewById(R.id.barchart);

        amountFood = getIntent().getStringArrayListExtra("food");
        amountTrans = getIntent().getStringArrayListExtra("trans");
        amountTravel = getIntent().getStringArrayListExtra("travel");
        amountTicket = getIntent().getStringArrayListExtra("ticket");
        amountSouvenir = getIntent().getStringArrayListExtra("souvenir");

        ArrayList<BarEntry> entries = new ArrayList<>();

        food = sumAmountType(amountFood);
        trans = sumAmountType(amountTrans);
        travel = sumAmountType(amountTravel);
        ticket = sumAmountType(amountTicket);
        souvenir = sumAmountType(amountSouvenir);

        entries.add(new BarEntry(4f, food ));
        entries.add(new BarEntry(6f, trans));
        entries.add(new BarEntry(8f,  travel));
        entries.add(new BarEntry(10f, ticket));
        entries.add(new BarEntry(12f, souvenir));


        String totalExpense = String.valueOf(food + trans + travel + ticket + souvenir);

        BarDataSet dataSet = new BarDataSet(entries, "Total Expense of Trip: " + totalExpense);


        chart.animateY(2000);
        BarData data = new BarData(dataSet);

        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        XAxis xaxis = chart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setDrawLabels(false);
        xaxis.setDrawAxisLine(false);


        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setDrawGridLines(false);
//        yAxisLeft.setDrawAxisLine(false);
//        yAxisLeft.setEnabled(false);



        chart.setDrawBorders(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.getDescription().setEnabled(false);

        chart.setData(data);

        chart.setBackgroundColor(Color.green(20));
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setColors(Color.rgb(126,206,195));



    }

    private float sumAmountType(ArrayList<String> amount){
        float sumAmount = 0;
        for (int i = 0; i < amount.size(); i++) {
            sumAmount += Float.valueOf(amount.get(i));
        }
        return sumAmount;
    };


}


