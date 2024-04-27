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
    private List<entry> history = new ArrayList<entry>();
    private int beginningWeight = 200;
    private int goalWeight = 180;
    private boolean gender = true; // True = Male, False = Female.
    private int height = 180;
    private String goalDate = "Jan 27 2050";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateVariables();
        updateHistory(getIntent().getStringExtra("date"), getIntent().getIntExtra("weight", -1));
        renderBMI();
        renderToDate();
        renderLatestWeight();
    }

    private void updateVariables(){
        if(getIntent().getBundleExtra("history") != null){
            Bundle args = getIntent().getBundleExtra("history");
            history = (ArrayList) args.getSerializable("history");
        } else {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String monthStr = numToMonth(month);
            StringBuilder build = new StringBuilder();
            build.append(monthStr+ " ");
            build.append(day + " ");
            build.append(year + " ");
            history.add(0, new entry(beginningWeight, build.toString()));
        }
        this.beginningWeight = getIntent().getIntExtra("beginningWeight", beginningWeight);
        this.goalWeight = getIntent().getIntExtra("goalWeight", goalWeight);
        this.gender = getIntent().getBooleanExtra("gender", gender);
        this.height = getIntent().getIntExtra("height", height);
    }
    // The method will act as the logic performed when buttons on the Summary Screen are pressed.
    public void navButtons(View button){
        Intent i = new Intent(this, MainActivity.class);
        switch(getResources().getResourceEntryName(button.getId())){
            case "addWeightButton":
                Log.d("weight button","pressed add weight button");
                i = new Intent(this, weightEntryActivity.class);
                if(!history.isEmpty()) {
                    i.putExtra("lastWeight", history.get(history.size() - 1).getWeight());
                    Bundle args = new Bundle();
                    args.putSerializable("history", (Serializable)history);
                    i.putExtra("history", args);
                }
                startActivity(i);
                break;
            case "weightHistoryButton":
                Log.d("history button","pressed weight history button");
                if(!history.isEmpty()) {
                    i.putExtra("lastWeight", history.get(history.size() - 1).getWeight());
                    Bundle args = new Bundle();
                    args.putSerializable("history", (Serializable)history);
                    i.putExtra("history", args);
                }
                startActivity(i);
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                // TODO: Future assignment
                break;
            case "appSettingsButton":
                Log.d("settings button","pressed settings menu button");
                i = new Intent(this, settingsActivity.class);
                if(!history.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putSerializable("history", (Serializable)history);
                    i.putExtra("history", args);
                }
                i.putExtra("beginningWeight", beginningWeight);
                i.putExtra("goalWeight", goalWeight);
                i.putExtra("gender", gender);
                i.putExtra("height", height);
                i.putExtra("goalDate", goalDate);
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
    private void updateHistory(String date, int weight){
        if(weight == -1) return;
        else{
            entry temp = new entry(weight, date);
            history.add(temp);
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
class entry implements Serializable {
    private int weight;
    private String date;
    public entry(int weight, String date){
        this.weight = weight;
        this.date = date;
    }
    public int getWeight(){
        return weight;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
}