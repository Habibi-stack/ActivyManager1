package com.examplealan.vixi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    private PasswordDAO passwordDAO;
    private int passwordId;

    private TextView editName, editLogin, editPassword, editNotes;
    private String selectedCategory = "Alimentação"; // Categoria padrão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Spinner spinner = findViewById(R.id.spinner);

        // Dados do Spinner
        String[] options = {"Alimentação", "Transporte", "Lazer", "Saúde"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Listener para salvar a categoria selecionada
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = options[position]; // Atualiza a categoria selecionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nenhuma ação necessária
            }
        });

        // Inicialização dos campos de texto
        editName = findViewById(R.id.addName);
        editLogin = findViewById(R.id.addLogin);
        editPassword = findViewById(R.id.addPassword);
        editNotes = findViewById(R.id.addNotes);

        passwordDAO = new PasswordDAO(this);
        Intent intent = getIntent();
        passwordId = intent.getIntExtra("passwordId", -1);

        // Carrega os dados caso seja uma edição
        if (passwordId != -1) {
            Password password = passwordDAO.get(passwordId);
            editName.setText(password.getName());
            editLogin.setText(password.getLogin());
            editPassword.setText(password.getPassword());
            editNotes.setText(password.getNotes());
        }
    }

    /**
     * Método chamado ao clicar no botão de salvar.
     */
    public void salvarClicado(View view) {
        // Cria ou atualiza o objeto Password
        Password password = new Password(passwordId,
                editName.getText().toString(),
                editLogin.getText().toString(),
                editPassword.getText().toString(),
                editNotes.getText().toString());

        boolean result;
        if (passwordId == -1) {
            result = passwordDAO.add(password);
        } else {
            result = passwordDAO.update(password);
        }

        if (result) {
            // Envia a categoria selecionada de volta para a ListActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedCategory", selectedCategory);
            setResult(RESULT_OK, resultIntent);
            finish(); // Finaliza a atividade
        }
    }
}
