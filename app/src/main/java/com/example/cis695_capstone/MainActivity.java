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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<entry> history = new ArrayList<entry>();
    private int beginningWeight = 0;
    private int goalWeight = 0;
    private boolean gender = true;
    private int height = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getBundleExtra("history") != null){
            Bundle args = getIntent().getBundleExtra("history");
            history = (ArrayList) args.getSerializable("history");
        }
        updateHistory(getIntent().getStringExtra("date"), getIntent().getIntExtra("weight", -1));
        renderBMI();
        renderToDate();
        renderLatestWeight();
        renderLastFiveWeight();
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
                i = new Intent(this, weightHistoryActivity.class);
                startActivity(i);
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                // TODO: Future assignmen
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

    }

    private void renderToDate(){
        if(!history.isEmpty()){
            int highest = 0;
            for(entry i : history){
                if(i.getWeight() > highest) highest = i.getWeight();
            }
            ((TextView)findViewById(R.id.toDateGenerated)).setText("You've lost " + Integer.toString(highest-beginningWeight) + "pounds!");
        } else ((TextView)findViewById(R.id.toDateGenerated)).setText("You haven't started yet. Good Luck!");
    }

    private void renderLatestWeight(){
        if(!history.isEmpty()){
            ((TextView)findViewById(R.id.currentWeightDataText)).setText(
                    history.get(history.size() - 1).toString()
            );
        }
    }

    private void renderLastFiveWeight() {
        StringBuilder temp = new StringBuilder();
        if (!history.isEmpty()) {
            if(history.size() > 5){
                for (int x = history.size(); x > history.size() - 5; x--) {
                    temp.append(history.get(x - 1).toString()).append("\n");
                }
            } else
                for (int x = history.size(); x > 0; x--) {
                    temp.append(history.get(x - 1).toString()).append("\n");
                }
            ((TextView) findViewById(R.id.weightHistoryDataText)).setText(temp.toString());
        }
    }

    private void updateHistory(String date, int weight){
        if(weight == -1) return;
        else{
            entry temp = new entry(weight, date);
            history.add(temp);
        }
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
    @Override
    public String toString(){
        return weight + ", " + date;
    }
}