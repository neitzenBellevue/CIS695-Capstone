package com.example.cis695_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<weightEntry> history = new ArrayList<weightEntry>();
    private int beginningWeight;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateVariables();
        renderBMI();
        renderToDate();
        renderLatestWeight();
    }

    // Updated to take information from database.
    private void updateVariables(){
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        this.history = databaseHelper.getAllEntries();
        this.beginningWeight = databaseHelper.getBegWeight();
        this.height = databaseHelper.getHeight();
    }
    // The method will act as the logic performed when buttons on the Summary Screen are pressed.
    public void navButtons(View button){
        Intent i = new Intent(this, MainActivity.class);
        switch(getResources().getResourceEntryName(button.getId())){
            case "addWeightButton":
                Log.d("weight button","pressed add weight button");
                i = new Intent(this, weightEntryActivity.class);
                startActivity(i);
                break;
            case "weightHistoryButton":
                Log.d("history button","pressed weight history button");
                i = new Intent(this, weightHistoryActivity.class);
                startActivity(i);
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                // TODO: Future assignment
                break;
            case "appSettingsButton":
                Log.d("settings button","pressed settings menu button");
                i = new Intent(this, settingsActivity.class);
                startActivity(i);
                break;
        }
        startActivity(i);
    }
    private void renderBMI(){
        if(!history.isEmpty()){
            int currentWeight = history.get(history.size() - 1).getWeight();
            double weightConvert = currentWeight * 0.453592;
            double heightConvert = height * .01;
            double bmi = weightConvert / (heightConvert * heightConvert);
            ((TextView)findViewById(R.id.bmiGenerated)).setText(String.format("%.2f", bmi));
        } else ((TextView)findViewById(R.id.bmiGenerated)).setText(Double.toString(beginningWeight / (height * height)));
    }
    private void renderToDate(){
        if(!history.isEmpty()){
            int weightLost = beginningWeight - history.get(history.size() - 1).getWeight();
            if(weightLost > 0)((TextView)findViewById(R.id.toDateGenerated)).setText("You've lost " + weightLost + " pounds!");
             else ((TextView)findViewById(R.id.toDateGenerated)).setText("You've gained " + weightLost*-1 + " pounds!");
        } else ((TextView)findViewById(R.id.toDateGenerated)).setText("You haven't started yet.");
    }
    private void renderLatestWeight(){
        if(!history.isEmpty()){
            int curWeight = history.get(history.size() - 1).getWeight();
            ((TextView)findViewById(R.id.currentWeightDataText)).setText(Integer.toString(curWeight) + " LBS");
        }
    }
    private String numToMonth(int month){
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
    }