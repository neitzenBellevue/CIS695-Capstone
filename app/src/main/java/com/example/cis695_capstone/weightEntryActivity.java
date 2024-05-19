package com.example.cis695_capstone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class weightEntryActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TextView weight;
    DatabaseHelper databaseHelper = new DatabaseHelper(weightEntryActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_entry);
        initDatePicker();

        List<weightEntry> history = databaseHelper.getAllEntries();
        String lastWeight = "150";
        if(!history.isEmpty()) lastWeight = Integer.toString(history.get(history.size() -1).getWeight());

        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate()); // Sets default day to today.
        weight = findViewById(R.id.weightEntry);
        weight.setText(lastWeight); // Sets default weight to last known or 150LBS);
    }

    public void submitButton(View button){
        if(!weight.getText().toString().isEmpty() && Integer.parseInt(weight.getText().toString()) > 0 &&
                !dateButton.getText().toString().isEmpty()){ // Verifying that weight and date exists;
            Intent i = new Intent(this, MainActivity.class);

            weightEntry newEntry = new weightEntry(Integer.parseInt(weight.getText().toString()),
                    dateButton.getText().toString(), "");

            databaseHelper.addEntry(newEntry);

            startActivity(i);
        } else findViewById(R.id.errorText).setVisibility(View.VISIBLE);
    }

    public void cancelButton(View button){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_POSITIVE;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month){
        if(month == 1){
           return "JAN";
        }
        if(month == 2){
            return "FEB";
        }
        if(month == 3){
            return "MAR";
        }
        if(month == 4){
            return "APR";
        }
        if(month == 5){
            return "MAY";
        }
        if(month == 6){
            return "JUN";
        }
        if(month == 7){
            return "JUL";
        }
        if(month ==8){
            return "AUG";
        }
        if(month == 9){
            return "SEP";
        }
        if(month == 10){
            return "OCT";
        }
        if(month == 11){
            return "NOV";
        }
        if(month == 12){
            return "DEC";
        }
        return "Error";
    }

    private String getTodaysDate(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return makeDateString(day, month, year);
    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}