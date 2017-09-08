package com.mrpet.mrpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MrPET extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mr_pet);
    }

    public void exibirGerenciadorDieta(View v)
    {
        Intent intentDieta = new Intent(this, GerenciadorDieta.class);
        startActivity(intentDieta);
    }

    public void exibirGerenciadorPets(View v)
    {
        Intent intentPet = new Intent(this, GerenciadorPets.class);
        startActivity(intentPet);
    }

    public void exibirInfoUsuario(View v)
    {
        Intent intentUsuario = new Intent(this, Usuario.class);
        startActivity(intentUsuario);
    }

    public void exibirEntreContato(View v)
    {
        Intent intentContato = new Intent(this, EntreContato.class);
        startActivity(intentContato);
    }


}
