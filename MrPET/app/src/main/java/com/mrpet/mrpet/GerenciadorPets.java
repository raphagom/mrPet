package com.mrpet.mrpet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GerenciadorPets extends AppCompatActivity {

    private EditText etNome;
    private EditText etRaca;
    private EditText etPorte;
    private EditText etPedigree;
    private EditText etEspecie;
    private EditText etIdade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciador_pets);

        etNome = (EditText) findViewById(R.id.etNome);
        etRaca = (EditText) findViewById(R.id.etRaca);
        etPorte = (EditText) findViewById(R.id.etPorte);
        etPedigree = (EditText) findViewById(R.id.etPedigree);
        etEspecie = (EditText) findViewById(R.id.etEspecie);
        etIdade = (EditText) findViewById(R.id.etIdade);

        GerenciadorPetREST gerenciadorPetREST = new GerenciadorPetREST();
        gerenciadorPetREST.execute("Id do usuario aqi");
    }

    public void voltar(View v)
    {
        finish();
    }


    private class GerenciadorPetREST extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(GerenciadorPets.this, "Aguarde", "Buscando dados no Servidor");
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://ec2-54-197-172-138.compute-1.amazonaws.com/pets/procurar?_id=41694328864");
                HttpURLConnection connection  = (HttpURLConnection)  url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if(connection.getResponseCode() == 200)
                {
                    BufferedReader stream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String linha = "";
                    StringBuilder resposta = new StringBuilder();
                    while ((linha = stream.readLine()) != null){
                        resposta.append(linha);
                    }
                    connection.disconnect();
                    return  resposta.toString();
                }

            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progress.dismiss();

            if(s!= null)
            {
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsonResult = jsonObject.getJSONObject("data");

                    String id = jsonResult.getString("_id");
                    String nome = jsonResult.getString("nome");
                    String raca = jsonResult.getString("raca");
                    String porte = jsonResult.getString("porte");
                    String pedigree = jsonResult.getString("pedigree");
                    String especie = jsonResult.getString("especie");
                    String idade = jsonResult.getString("idade");
                    String device = jsonResult.getString("device");
                    String dono = jsonResult.getString("dono");

                    etNome.setText(nome);
                    etRaca.setText(raca);
                    etPorte.setText(porte);
                    etPedigree.setText(pedigree);
                    etEspecie.setText(especie);
                    etIdade.setText(idade);
                }
                catch(JSONException ex)
                {
                    ex.printStackTrace();
                }
            }else{
                Toast.makeText(GerenciadorPets.this, "Erro ao Realizar Consulta", Toast.LENGTH_LONG).show();
            }
        }
    }

}
