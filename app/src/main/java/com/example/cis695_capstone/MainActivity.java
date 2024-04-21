package com.example.cis695_capstone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navButtons(View button){
        switch(getResources().getResourceEntryName(button.getId())){
            case "addWeightButton":
                Log.d("weight button","pressed add weight button");
                break;
            case "weightHistoryButton":
                Log.d("history button","pressed weight history button");
                break;
            case "progressPicButton":
                Log.d("picture button","pressed add progress picture button");
                break;
            case "appSettingsButton":
                Log.d("settings button","pressed settings menu button");
                break;
        }
    }
}