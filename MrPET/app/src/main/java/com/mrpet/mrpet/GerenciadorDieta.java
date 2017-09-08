package com.mrpet.mrpet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GerenciadorDieta extends AppCompatActivity {

    private EditText etNome;
    private EditText etFrequenciaDiaria;
    private EditText etDataInicio;
    private EditText etDataFinal;
    private EditText etQtdRacao;
    private EditText etHorarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciador_dieta);

        etNome = (EditText) findViewById(R.id.etNome);
        etFrequenciaDiaria = (EditText) findViewById(R.id.etFrequencia);
        etDataInicio = (EditText) findViewById(R.id.etDtInicio);
        etDataFinal = (EditText) findViewById(R.id.etDtTermino);
        etQtdRacao = (EditText) findViewById(R.id.etQtdRacao);
        etHorarios = (EditText) findViewById(R.id.etHorarios);

        GerenciadorDietaREST rest = new GerenciadorDietaREST();
        rest.execute("aqui vai o id do usuario");
    }

    public void voltar(View v){
        finish();
    }

    private class GerenciadorDietaREST extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(GerenciadorDieta.this, "Aguarde", "Buscando dados no Servidor");
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://ec2-54-197-172-138.compute-1.amazonaws.com/dietas/procurar?device_id=13579b");
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
                    JSONArray arrayHorarios = jsonResult.getJSONArray("horarios");
                    String id = jsonResult.getString("_id");
                    String descricao = jsonResult.getString("descricao");
                    int frequencia_diaria = jsonResult.getInt("frequencia_diaria");
                    String data_inicio = jsonResult.getString("data_inicio");
                    Date dtini = new SimpleDateFormat("yyyy-mm-dd").parse(data_inicio);
                    data_inicio = new SimpleDateFormat("dd/mm/yyyy").format(dtini);
                    String data_fim = jsonResult.getString("data_fim");
                    Date dtfim = new SimpleDateFormat("yyyy-mm-dd").parse(data_fim);
                    data_fim = new SimpleDateFormat("dd/mm/yyyy").format(dtfim);
                    int qtde_racao = jsonResult.getInt("qtde_racao");
                    String ativa = jsonResult.getString("ativa");
                    String idDevice = jsonResult.getString("device");
                    String horarios = "";

                    for(int i = 0; i < arrayHorarios.length(); i++){
                        if(i+1 == arrayHorarios.length()){
                            horarios = horarios + arrayHorarios.getString(i);

                        }else{
                            horarios = horarios + arrayHorarios.getString(i) + ",";
                        }
                    }
                    etNome.setText(descricao);
                    etFrequenciaDiaria.setText(String.valueOf(frequencia_diaria));
                    etDataInicio.setText(data_inicio);
                    etDataFinal.setText(data_fim);
                    etQtdRacao.setText(String.valueOf(qtde_racao));
                    etHorarios.setText(horarios);


                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }else{
                Toast.makeText(GerenciadorDieta.this, "Erro ao Realizar Consulta", Toast.LENGTH_LONG).show();
            }
        }
    }
}

