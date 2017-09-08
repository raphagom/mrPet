package com.mrpet.mrpet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EntreContato extends AppCompatActivity {

    Button btEnviar;
    EditText edtAssunto;
    EditText edtPara;
    EditText edtMensagem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entre_contato);

        btEnviar = (Button) findViewById(R.id.btEnviar);
        edtAssunto = (EditText) findViewById(R.id.edtAssunto);
        edtPara = (EditText) findViewById(R.id.edtPara);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String para = edtPara.getText().toString();
                String assunto = edtAssunto.getText().toString();
                String mensagem = edtMensagem.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ para});
                email.putExtra(Intent.EXTRA_SUBJECT, assunto);
                email.putExtra(Intent.EXTRA_TEXT, mensagem);

                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Escolha o E-mail de sua preferência: "));

            }
        });









    }

    public void voltar(View v)
    {
        finish();
    }
}
