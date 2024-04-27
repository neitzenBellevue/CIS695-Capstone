package com.example.cis695_capstone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        Bundle args = getIntent().getBundleExtra("history");
        ArrayList<entry> history = (ArrayList<entry>) args.getSerializable("history");
        ListView myListView = (ListView) findViewById(R.id.listView);
        EntryListAdapter adapter = new EntryListAdapter(this, R.layout.dynamic_listview_adapter, history);
        myListView.setAdapter(adapter);
    }
    private void updateVariables(){
        this.beginningWeight = getIntent().getIntExtra("beginningWeight", 200);
        this.goalWeight = getIntent().getIntExtra("goalWeight", 180);
        this.gender = getIntent().getBooleanExtra("gender", true);
        this.height = getIntent().getIntExtra("height", 180);
        this.goalDate = getIntent().getStringExtra("goalDate");
    }

    public void returnToSummary(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("history", getIntent().getBundleExtra("history"));
        i.putExtra("beginningWeight", beginningWeight);
        i.putExtra("goalWeight", goalWeight);
        i.putExtra("gender", gender);
        i.putExtra("height", height);
        i.putExtra("goalDate", goalDate);
        startActivity(i);
    }

    static class EntryListAdapter extends ArrayAdapter<entry> {
        private static final String TAG = "EntryListAdapter";
        private Context context;
        int resource;
        public EntryListAdapter(Context context, int resource, ArrayList<entry> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            int weight = getItem(position).getWeight();
            String date = getItem(position).getDate();
            entry newEntry = new entry(weight, date);
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