package com.example.cis695_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

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
                break;
            case "weightHistoryButton":
                Log.d("history button","pressed weight history button");
                i = new Intent(this, weightHistoryActivity.class);
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                // TODO
                break;
            case "appSettingsButton":
                Log.d("settings button","pressed settings menu button");
                i = new Intent(this, editSettingsActivity.class);
                break;
        }
        startActivity(i);
    }

    private void renderGraph(){
        // Todo: Read database and render graph based on entries
    }

    private void renderLatestWeight(){
        // Todo: Read database and render latest weight to @+id/currentWeightTitleText
    }

    private void renderLastFiveWeight(){
        // Todo: Read database and render the last five weights to @+di/weightHistoryDataText
    }

}