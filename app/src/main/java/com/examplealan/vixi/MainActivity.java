package com.examplealan.vixi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listactivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;

    }
    public void entrarClicado(View view) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String prefLogin = sharedPreferences.getString("login", "");
        String prefPass = sharedPreferences.getString("password", "");
        String editLogin = ((EditText) findViewById(R.id.idlogin)).getText().toString();
        String editPass = ((EditText) findViewById(R.id.idsenha)).getText().toString();
        if (editLogin.equals(prefLogin) && editPass.equals(prefPass)) {
            Intent intent = new Intent(this, ListActivity.class);
            EditText inputLogin = findViewById(R.id.idlogin);
            intent.putExtra("login", inputLogin.getText().toString());
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Login/senha inválidos!", Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Active Manager é um aplicativo voltado para o controle de gastos e gestão financeira. O app foi desenvolvido para que os usuários tenham seu organizador financeiro pessoal e controlador de despesas digital.")
                    .setNeutralButton("Ok", null)
                    .show();
            return true;

        }
        if(item.getItemId()== R.id.configs){
            Intent intentConfig = new Intent(this, PreferencesActivity.class);
            startActivity(intentConfig);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


