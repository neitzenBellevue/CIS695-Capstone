package com.example.cis695_capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class weightHistoryActivity extends AppCompatActivity {
    private int beginningWeight;
    private int goalWeight;
    private boolean gender;
    private int height;
    private String goalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_history);
        updateVariables();
        renderHistory();
    }

    private void renderHistory(){
        DatabaseHelper databaseHelper = new DatabaseHelper(weightHistoryActivity.this);
        List<weightEntry> history = databaseHelper.getAllEntries();
        ListView myListView = (ListView) findViewById(R.id.listView);
        EntryListAdapter adapter = new EntryListAdapter(this,
                R.layout.dynamic_listview_adapter, (ArrayList<weightEntry>)history);
        myListView.setAdapter(adapter);
    }
    private void updateVariables(){
        DatabaseHelper databaseHelper = new DatabaseHelper(weightHistoryActivity.this);
        this.beginningWeight = databaseHelper.getBegWeight();
        this.goalWeight = databaseHelper.getGoalWeight();
        this.gender = databaseHelper.getGender();
        this.height = databaseHelper.getHeight();
        this.goalDate = databaseHelper.getGoalDate();
    }

    public void returnToSummary(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    static class EntryListAdapter extends ArrayAdapter<weightEntry> {
        private static final String TAG = "EntryListAdapter";
        private Context context;
        int resource;
        public EntryListAdapter(Context context, int resource, ArrayList<weightEntry> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            int weight = getItem(position).getWeight();
            String date = getItem(position).getDate();
            weightEntry newEntry = new weightEntry(weight, date, "");
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView dateSet = (TextView) convertView.findViewById(R.id.dateGenerated);
            TextView weightSet = (TextView) convertView.findViewById(R.id.weightGenerated);

            dateSet.setText(date);
            weightSet.setText(Integer.toString(weight));
            //Todo: Set image in future assignments
            //Todo: Link Edit Button

            return convertView;
        }
    }
}