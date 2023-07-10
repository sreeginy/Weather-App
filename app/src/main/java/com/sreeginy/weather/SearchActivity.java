package com.sreeginy.weather;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sreeginy.weather.Adapter.TomorrowAdapter;
import com.sreeginy.weather.Model.Tomorrow;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterTomorrow;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = editText.getText().toString();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
                return false;
            }
        });

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<Tomorrow> items = new ArrayList<>();

        items.add(new Tomorrow("Sun", "cloudy", "Strom", 25, 10));
        items.add(new Tomorrow("Mon", "storm", "Rainny", 75, 30));
        items.add(new Tomorrow("Tue", "sun", "Cloudy", 55, 5));
        items.add(new Tomorrow("Wed", "wind", "Strom", 25, 10));
        items.add(new Tomorrow("Thu", "snowy", "Snow", 67, 2));
        items.add(new Tomorrow("Fri", "cloudy_3", "Sunny", 10, 5));
        items.add(new Tomorrow("Sat", "cloudy_sunny", "Storm", 25, 10));

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterTomorrow = new TomorrowAdapter(items);
        recyclerView.setAdapter(adapterTomorrow);
    }
}
