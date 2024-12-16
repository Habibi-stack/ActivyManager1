package com.examplealan.vixi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_EDIT = 1; // Código de solicitação para EditActivity

    private RecyclerView recyclerView;
    private PasswordsAdapter adapter;
    private PieChart pieChart;

    private Map<String, Integer> categoryCounts = new HashMap<>();
    private int totalItems = 0;

    private TextView categoryText;

    private static final String PREFS_NAME = "CategoryData"; // Nome do SharedPreferences
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inicializa o SharedPreferences
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Inicializa os contadores das categorias
        categoryCounts.put("Alimentação", preferences.getInt("foodCount", 0));
        categoryCounts.put("Transporte", preferences.getInt("transportCount", 0));
        categoryCounts.put("Lazer", preferences.getInt("leisureCount", 0));
        categoryCounts.put("Saúde", preferences.getInt("healthCount", 0));
        totalItems = preferences.getInt("totalItems", 0);

        // Inicializa o RecyclerView
        recyclerView = findViewById(R.id.list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PasswordsAdapter(this);
        recyclerView.setAdapter(adapter);

        // Inicializa o gráfico de pizza
        pieChart = findViewById(R.id.piechart);
        updatePieChart(
                calculatePercentage("Alimentação"),
                calculatePercentage("Transporte"),
                calculatePercentage("Lazer"),
                calculatePercentage("Saúde")
        );

        // Campo de texto para exibir informações adicionais
        categoryText = findViewById(R.id.categoryText);
        categoryText.setText("Itens totais: " + totalItems);
    }

    /**
     * Atualiza o gráfico de pizza com os valores fornecidos.
     *
     * @param food      Percentual de Alimentação
     * @param transport Percentual de Transporte
     * @param leisure   Percentual de Lazer
     * @param health    Percentual de Saúde
     */
    private void updatePieChart(int food, int transport, int leisure, int health) {
        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Alimentação", food, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("Transporte", transport, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("Lazer", leisure, Color.parseColor("#EF5350")));
        pieChart.addPieSlice(new PieModel("Saúde", health, Color.parseColor("#29B6F6")));
        pieChart.startAnimation();
    }

    /**
     * Calcula o percentual de uma categoria com base na quantidade total de itens.
     *
     * @param category Nome da categoria (ex: "Alimentação", "Transporte")
     * @return Percentual da categoria
     */
    private int calculatePercentage(String category) {
        int count = categoryCounts.getOrDefault(category, 0);
        return totalItems > 0 ? (int) ((count / (double) totalItems) * 100) : 0;
    }

    /**
     * Callback chamado ao retornar da EditActivity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            String category = data.getStringExtra("selectedCategory");
            if (category != null) {
                // Incrementa a contagem da categoria correspondente
                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
                totalItems++;

                // Recalcula os percentuais e atualiza o gráfico
                updatePieChart(
                        calculatePercentage("Alimentação"),
                        calculatePercentage("Transporte"),
                        calculatePercentage("Lazer"),
                        calculatePercentage("Saúde")
                );

                // Atualiza o texto de informações
                categoryText.setText("Itens totais: " + totalItems);

                // Salva os dados no SharedPreferences
                saveDataToPreferences();
            }
        }
    }

    /**
     * Salva os dados no SharedPreferences.
     */
    private void saveDataToPreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("foodCount", categoryCounts.getOrDefault("Alimentação", 0));
        editor.putInt("transportCount", categoryCounts.getOrDefault("Transporte", 0));
        editor.putInt("leisureCount", categoryCounts.getOrDefault("Lazer", 0));
        editor.putInt("healthCount", categoryCounts.getOrDefault("Saúde", 0));
        editor.putInt("totalItems", totalItems);
        editor.apply();
    }

    /**
     * Método chamado ao clicar no botão de adicionar.
     */
    public void buttonAddClick(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
}
