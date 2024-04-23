package com.example.cis695_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<entry> history = new ArrayList<entry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renderGraph();
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
                startActivity(i);
                break;
            case "weightHistoryButton":
                Log.d("history button","pressed weight history button");
                i = new Intent(this, weightHistoryActivity.class);
                startActivity(i);
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                // TODO
                break;
            case "appSettingsButton":
                Log.d("settings button","pressed settings menu button");
                i = new Intent(this, settingsActivity.class);
                startActivity(i);
                break;
        }
        startActivity(i);
    }

    private void renderGraph(){
        // Todo: Read database and render graph based on entries
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
            for (int x = history.size(); x > 0; x--) {
                temp.append(history.get(x - 1).toString()).append("/n");
            }
            ((TextView) findViewById(R.id.weightHistoryDataText)).setText(temp.toString());
        }
    }
}
class entry {
    private double weight;
    private Date date;
    public entry(double weight, Date date){
        this.weight = weight;
        this.date = date;
    }
    public double getWeight(){
        return weight;
    }
    public Date getDate(){
        return this.date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setWeight(double weight){
        this.weight = weight;
    }
    @Override
    public String toString(){
        return weight + ", " + date.toString();
    }
}