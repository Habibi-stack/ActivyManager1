package com.examplealan.vixi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de layout para suportar system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listactivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }

    /**
     * Método chamado ao clicar no botão "Entrar".
     */
    public void entrarClicado(View view) {
        // Recupera as preferências salvas
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String prefLogin = sharedPreferences.getString("login", "");
        String prefPass = sharedPreferences.getString("password", "");

        // Obtém os valores inseridos pelo usuário
        String editLogin = ((EditText) findViewById(R.id.idlogin)).getText().toString();
        String editPass = ((EditText) findViewById(R.id.idsenha)).getText().toString();

        // Verifica se o login e senha estão corretos
        if (editLogin.equals(prefLogin) && editPass.equals(prefPass)) {
            // Abre a ListActivity
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        } else {
            // Exibe uma mensagem de erro
            Toast.makeText(this, "Login/senha inválidos!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            // Exibe a mensagem "Sobre"
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Active Manager é um aplicativo voltado para o controle de gastos e gestão financeira. O app foi desenvolvido para que os usuários tenham seu organizador financeiro pessoal e controlador de despesas digital.")
                    .setNeutralButton("Ok", null)
                    .show();
            return true;
        }

        if (item.getItemId() == R.id.configs) {
            // Abre a tela de configurações
            Intent intentConfig = new Intent(this, PreferencesActivity.class);
            startActivity(intentConfig);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
