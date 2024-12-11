package com.examplealan.vixi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PasswordsAdapter adapter;
    private PieChart pieChart;
    private TextView categoryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        pieChart = findViewById(R.id.piechart);
        categoryText = findViewById(R.id.categoryText);
        String category = getIntent().getStringExtra("category");
        int food = getIntent().getIntExtra("food",0);
        int transport = getIntent().getIntExtra("transport", 0);
        int leisure = getIntent().getIntExtra("leisure", 0);
        int health = getIntent().getIntExtra("health", 0);
        categoryText.setText("Categoria Selecionada: " + category);
        setdata(food, transport, leisure, health);

        recyclerView = findViewById(R.id.list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PasswordsAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private void setdata(int food, int transport, int leisure, int health) {
        pieChart.addPieSlice(new PieModel("Alimentação", food, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Transporte", transport, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Lazer", leisure, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Saúde", health, Color.parseColor("#29B6F6")));


        // Animar o gráfico
        pieChart.startAnimation();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
    public void buttonAddClick(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }



}