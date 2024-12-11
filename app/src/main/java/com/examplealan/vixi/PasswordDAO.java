package com.examplealan.vixi;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class PasswordDAO {
    private Context context;
    private SQLiteDatabase database;
    public PasswordDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    /*
    versão sem banco de dados para salvar

    public static ArrayList<Password> passwordsList = new ArrayList<>();
    public PasswordDAO(Context context) {
        this.context = context;
    }
    public ArrayList<Password> getList() {
        if (passwordsList.size() == 0) {
            passwordsList.add(new Password(0, "Facebook", "dovahkiin@gmail.com",
                    "FusRoDah123", ""));
            passwordsList.add(new Password(1, "GMail", "dovahkiin",
                    "Teste123", ""));
            passwordsList.add(new Password(2, "IComp", "dfrd@icomp.ufam.edu.br",
                    "Java4242", "Mudar a senha!"));
            passwordsList.add(new Password(3, "Steam", "dovahkiin",
                    "FusRoDah123", "Conta do Brasil"));
        }*/
    public ArrayList<Password> getList() {
        ArrayList<Password> result = new ArrayList<Password>();
        String sql = "SELECT * FROM passwords ORDER BY name";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String login = cursor.getString(2);
            String password = cursor.getString(3);
            String notes = cursor.getString(4);
            result.add(new Password(id, name, login, password, notes));
        }
        return result;
    }

    public boolean add(Password password) {
        String sql = "INSERT INTO passwords VALUES (NULL, "
                + "'" + password.getName() + "', "
                + "'" + password.getLogin() + "', "
                + "'" + password.getPassword() + "', "
                + "'" + password.getNotes() + "')";
        try {
            database.execSQL(sql);
            Toast.makeText(context, "dados salvos!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean update(Password password) {
        String sql = "UPDATE passwords SET "
                + "name='" + password.getName() + "', "
                + "login='" + password.getLogin() + "', "
                + "password='" + password.getPassword() + "', "
                + "notes='" + password.getNotes() + "' "
                + "WHERE id=" + password.getId();
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Dados atualizados!", Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Password get(int id) {
        String sql = "SELECT * FROM passwords WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String login = cursor.getString(2);
            String password = cursor.getString(3);
            String notes = cursor.getString(4);
            return new Password(id, name, login, password, notes);
        }
        return null;
    }
    public boolean re(Password password) {

        String sql = "INSERT INTO passwords VALUES (NULL, "
                + "'" + password.getName() + "', "
                + "'" + password.getLogin() +"', "
                + "'" + password.getPassword() + "', "
                + "'" + password.getNotes() + "')";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "dados salvos!", Toast.LENGTH_SHORT).show();
            return true;

        } catch (SQLException e) {
            Toast.makeText(context, "Erro! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

       /* return passwordsList;
    }public boolean add(Password password) {
        password.setId(passwordsList.size());
        passwordsList.add(password);
        Toast.makeText(context, "Senha salva!", Toast.LENGTH_SHORT).show();
        return true;
    }public boolean update(Password password) {
        passwordsList.set(password.getId(), password);
        Toast.makeText(context, "Senha atualizada!", Toast.LENGTH_SHORT).show();
        return true;
    }
    public Password get(int id) {
        return passwordsList.get(id);
    }

}
*/