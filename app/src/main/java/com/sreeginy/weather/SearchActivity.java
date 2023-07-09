package com.sreeginy.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sreeginy.weather.Adapter.TommorowAdapter;
import com.sreeginy.weather.Model.Tommorow;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterTommorow;
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


        initRecyclerView();
    }

    private void initRecyclerView() {

        ArrayList<Tommorow> items = new ArrayList<>();

        items.add(new Tommorow("Sun","cloudy", "Strom",25, 10));
        items.add(new Tommorow("Mon","storm", "Rainny",75, 30));
        items.add(new Tommorow("Tue","sun", "Cludy",55, 05));
        items.add(new Tommorow("Wed","win", "Strom",25, 10));
        items.add(new Tommorow("Thu","snowy", "Snow",67, 02));
        items.add(new Tommorow("Fri","cloudy_3", "Sunny",10, 5));
        items.add(new Tommorow("Sat","cloudy_sunny", "storm",25, 10));

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapterTommorow = new TommorowAdapter(items);
        recyclerView.setAdapter(adapterTommorow);
    }
}


