package com.examplealan.vixi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditActivity extends AppCompatActivity {
    private PasswordDAO passwordDAO;
    private int passwordId;
    private TextView editName, editLogin, editPassword, editNotes;


    public void salvarClicado(View view) {
        Password password = new Password(passwordId, editName.getText().toString(),
                editLogin.getText().toString(), editPassword.getText().toString(),
                editNotes.getText().toString());
        boolean result;
        if (passwordId == -1) result = passwordDAO.add(password);
        else result = passwordDAO.update(password);
        if (result) finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Spinner spinner = findViewById(R.id.spinner);

        // Dados do Spinner
        String[] options = {"Alimentação", "Transporte", "Lazer","Saúde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Envia a seleção para a PieChartActivity
                Intent intent = new Intent(EditActivity.this, ListActivity.class);
                // Envia a posição selecionada

                switch (position) {
                    case 0: // Alimentação
                        intent.putExtra("category", "Alimentação");
                        intent.putExtra("food", 50);
                        intent.putExtra("transport", 20);
                        intent.putExtra("leisure", 15);
                        intent.putExtra("health", 15);
                        break;

                    case 1: // Transporte
                        intent.putExtra("category", "Transporte");
                        intent.putExtra("food", 20);
                        intent.putExtra("transport", 50);
                        intent.putExtra("leisure", 15);
                        intent.putExtra("health", 15);
                        break;

                    case 2: // Lazer
                        intent.putExtra("category", "Lazer");
                        intent.putExtra("food", 15);
                        intent.putExtra("transport", 15);
                        intent.putExtra("leisure", 50);
                        intent.putExtra("health", 20);
                        break;

                    case 3: // Saúde
                        intent.putExtra("category", "Saúde");
                        intent.putExtra("food", 10);
                        intent.putExtra("transport", 20);
                        intent.putExtra("leisure", 20);
                        intent.putExtra("health", 50);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não faz nada
            }
        });
        editName = findViewById(R.id.addName);
        editLogin = findViewById(R.id.addLogin);
        editPassword = findViewById(R.id.addPassword);
        editNotes = findViewById(R.id.addNotes);
        passwordDAO = new PasswordDAO(this);
        Intent intent = getIntent();
        passwordId = intent.getIntExtra("passwordId", -1);
        // Verifica se uma senha foi passada como parâmetro
        if (passwordId != -1) {
            Password password = passwordDAO.get(passwordId);
            editName.setText(password.getName());
            editLogin.setText(password.getLogin());
            editPassword.setText(password.getPassword());
            editNotes.setText(password.getNotes());
        }

    }
}
