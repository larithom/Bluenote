package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {
    private Button btnCadastro;
    private EditText txtNome_usu;
    private EditText txtEmail;
    private EditText txtSenha;
    private SQLiteDatabase bd;
    public static final String SHARED_PREFS = "shade_prefs";
    public static final String CHAVE_EMAIL = "chave_email";
    public static final String CHAVE_USUARIO = "chave_usuario";
    public static final String SENHA = "senha";
    SharedPreferences sharedPreferences;

    String email, usuario, senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnCadastro = findViewById(R.id.btnCadastro);
        txtNome_usu = findViewById(R.id.txtNome_usu);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("CHAVE_USUARIO", null);
        email = sharedPreferences.getString("CHAVE_EMAIL", null);
        senha = sharedPreferences.getString("SENHA", null);

        btnCadastro.setOnClickListener(new EscutadorBotao());
        ActionBar ab = getSupportActionBar();
        ab.hide();

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);

        String cmd;
        cmd = "CREATE TABLE IF NOT EXISTS usuario ("+
                "nome_usuario VARCHAR(255) UNIQUE NOT NULL, " +
                "email VARCHAR(255) UNIQUE NOT NULL, " +
                "senha VARCHAR(255), PRIMARY KEY(nome_usuario, email))";
        bd.execSQL(cmd);
    }

    @Override
    protected void onStart(){
        super.onStart();
        SessionManagement sm = new SessionManagement(Cadastro.this);
        if (sm.getSession()!=null){
            Intent i1 = new Intent(getApplicationContext(), Inicio.class);
            i1.putExtra("nome", sm.getSession());
            startActivity(i1);
            finish();
        }
    }

    private class EscutadorBotao implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(txtNome_usu.getText().toString()) || TextUtils.isEmpty(txtSenha.getText().toString()) || TextUtils.isEmpty(txtEmail.getText().toString())) {
                Toast.makeText(Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                boolean certo = true;
                String senha2 = txtSenha.getText().toString();
                String email2 = txtEmail.getText().toString();
                String nome_usu = txtNome_usu.getText().toString();
                String cmd = "INSERT INTO usuario VALUES ('";
                cmd = cmd+nome_usu;
                cmd = cmd+"', '";
                cmd = cmd+email2;
                cmd = cmd+"', '";
                cmd = cmd+senha2;
                cmd = cmd+"')";
                try{
                    bd.execSQL(cmd);
                }catch (Exception e){
                    Toast.makeText(Cadastro.this, "Não foi possível adicionar :(", Toast.LENGTH_SHORT).show();
                    certo = false;
                }
                if (certo) {
                    Usuario u = new Usuario(senha2, email2, nome_usu);
                    SessionManagement sm = new SessionManagement(Cadastro.this);
                    sm.saveSession(u);
                    Toast.makeText(Cadastro.this, "Usuário adicionado :)", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(getApplicationContext(), Inicio.class);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i1.putExtra("nome", nome_usu);
                    startActivity(i1);
                    finish();
                }
            }
        }
    }
}