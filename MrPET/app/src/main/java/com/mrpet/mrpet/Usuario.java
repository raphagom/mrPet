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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Usuario extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSexo;
    private EditText etDtNascimento;
    private EditText etDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSexo = (EditText) findViewById(R.id.etSexo);
        etDtNascimento = (EditText) findViewById(R.id.etDtNascimento);
        etDevice = (EditText) findViewById(R.id.etDevice);

        UsuarioREST usuarioREST = new UsuarioREST();
        usuarioREST.execute("Id do usuario aqi");


    }

    public void voltar(View v)
    {
        finish();
    }

    private class UsuarioREST extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Usuario.this, "Aguarde", "Buscando dados no Servidor");
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL("http://ec2-54-197-172-138.compute-1.amazonaws.com/clientes/procurar?_id=41694328864");
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
                    String email = jsonResult.getString("email");
                    String sexo = jsonResult.getString("sexo");
                    String cpf = jsonResult.getString("cpf");
                    String dtNascimento = jsonResult.getString("dtNascimento");
                    Date data = new SimpleDateFormat("yyyy-mm-dd").parse(dtNascimento);
                    dtNascimento = new SimpleDateFormat("dd/mm/yyyy").format(data);
                    String logradouro = jsonResult.getString("logradouro");
                    String numero = jsonResult.getString("numero");
                    String complemento = jsonResult.getString("complemento");
                    String bairro = jsonResult.getString("bairro");
                    long cep = jsonResult.getLong("cep");
                    String cidade = jsonResult.getString("cidade");
                    String estado = jsonResult.getString("estado");
                    String device = jsonResult.getString("device");

                    etNome.setText(nome);
                    etEmail.setText(email);
                    etSexo.setText(sexo);
                    etDtNascimento.setText(dtNascimento);
                    etDevice.setText(device);
                }
                catch(JSONException ex)
                {
                    ex.printStackTrace();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }else{
                Toast.makeText(Usuario.this, "Erro ao Realizar Consulta", Toast.LENGTH_LONG).show();
            }
        }
    }

}
