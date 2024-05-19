package com.example.cis695_capstone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class settingsActivity extends AppCompatActivity {
    private int beginningWeight;
    private int goalWeight;
    private boolean gender;
    private int height;
    private String goalDate;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initDatePicker();
        renderView();
        dateButton = findViewById(R.id.datePickerButton2);
    }

    public void submitButton(View button){
        Intent i = new Intent(this, MainActivity.class);
        updateVariables();

        DatabaseHelper databaseHelper = new DatabaseHelper(settingsActivity.this);
        boolean success = databaseHelper.updateSettings(beginningWeight, goalWeight, gender, height, goalDate);

        startActivity(i);
    }

    public void cancelButton(View button){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void updateVariables(){
        this.beginningWeight = Integer.parseInt(((EditText)findViewById(R.id.beginningWeight)).getText().toString());
        this.goalWeight = Integer.parseInt(((EditText)findViewById(R.id.goalWeight)).getText().toString());
        if(((RadioButton)findViewById(R.id.maleRadioButton)).isChecked()) this.gender = true;
            else this.gender = false; // Assumed if male not checked then female is checked.
        this.height = Integer.parseInt(((EditText)findViewById(R.id.heightMetric)).getText().toString());
        this.goalDate = dateButton.getText().toString();
    }

    private void renderView(){
        DatabaseHelper databaseHelper = new DatabaseHelper(settingsActivity.this);

        this.beginningWeight = databaseHelper.getBegWeight();
        this.goalWeight = databaseHelper.getGoalWeight();
        this.gender = databaseHelper.getGender();
        this.height = databaseHelper.getHeight();
        this.goalDate = databaseHelper.getGoalDate();

        ((EditText)findViewById(R.id.beginningWeight)).setText(Integer.toString(beginningWeight));
        ((EditText)findViewById(R.id.goalWeight)).setText(Integer.toString(goalWeight));
        if(gender) ((RadioButton)findViewById(R.id.maleRadioButton)).setChecked(true);
        else ((RadioButton)findViewById(R.id.femaleRadioButton)).setChecked(true);
        ((EditText)findViewById(R.id.heightMetric)).setText(Integer.toString(height));
        ((Button)findViewById(R.id.datePickerButton2)).setText(goalDate);
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