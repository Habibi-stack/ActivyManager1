package com.examplealan.vixi;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class PasswordsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Context context;
    public TextView login, name;
    public Spinner categorySpinner;
    public int id;

    public PasswordsViewHolder(ConstraintLayout v, Context context) {
        super(v);
        this.context = context;

        name = v.findViewById(R.id.itemName);
        login = v.findViewById(R.id.itemLogin);
        categorySpinner = v.findViewById(R.id.spinner); // Adicione um Spinner ao layout

        // Configurar o Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                new String[]{"Alimentação", "Transporte", "Lazer", "Saúde"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //categorySpinner.setAdapter(adapter);isso ta dando erro e não sei o pq


        // Configuração de clique no item
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("passwordId", this.id);
        context.startActivity(intent);
    }
}
