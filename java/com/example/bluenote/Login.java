package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private TextView link1;
    private TextView link2;
    private EditText txtUsuario;
    private EditText txtSenha;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        btnLogin = findViewById(R.id.btnLogin);
        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtSenha = findViewById(R.id.txtSenha);
        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);

        btnLogin.setOnClickListener(new EscutadorBotao());
        link1.setOnClickListener(new EscutadorText());
    }

    @Override
    protected void onStart(){
        super.onStart();
        SessionManagement sm = new SessionManagement(Login.this);
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
            String cmd;
                if (TextUtils.isEmpty(txtUsuario.getText().toString()) || TextUtils.isEmpty(txtSenha.getText().toString())) {
                    Toast.makeText(Login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean certo = true;
                    String senha = txtSenha.getText().toString();
                    String usuario = txtUsuario.getText().toString();
                    cmd = "SELECT _rowid_ _id, email, nome_usuario, senha FROM usuario WHERE nome_usuario like '";
                    cmd = cmd+usuario;
                    cmd = cmd+"' or email like '";
                    cmd = cmd+usuario;
                    cmd = cmd+"' and senha like '";
                    cmd = cmd+senha;
                    cmd = cmd+"'";
                    Cursor cursor = bd.rawQuery(cmd, null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        Usuario u = new Usuario(cursor.getString(cursor.getColumnIndex("senha")), cursor.getString(cursor.getColumnIndex("email")), cursor.getString(cursor.getColumnIndex("nome_usuario")));
                        SessionManagement sm = new SessionManagement(Login.this);
                        sm.saveSession(u);
                        Intent i1 = new Intent(getApplicationContext(), Inicio.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.putExtra("nome", cursor.getString(cursor.getColumnIndex("nome_usuario")));
                        startActivity(i1);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    private class EscutadorText implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView tv = (TextView) view;
            switch (tv.getId()) {
                case R.id.link1:
                    Intent i2 = new Intent(getApplicationContext(), Cadastro.class);
                    startActivity(i2);
                    break;
            }
        }
    }
}