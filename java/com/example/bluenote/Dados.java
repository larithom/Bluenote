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
import android.widget.Toast;

public class Dados extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtUsuario;
    private Button btnSenha;
    private Button btnSalvar;
    private Button btnNivel;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        txtEmail = findViewById(R.id.txtEmail);
        txtUsuario = findViewById(R.id.txtUsuario);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSenha = findViewById(R.id.btnSenha);
        btnNivel = findViewById(R.id.btnNivel);

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);

        SessionManagement sm = new SessionManagement(Dados.this);
        txtEmail.setText(sm.getSessionMeia().split(",")[0]);
        txtUsuario.setText(sm.getSessionMeia().split(",")[1]);
        btnNivel.setText("Nível 1");

    }

    private class EscutadorBotao implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(txtUsuario.getText().toString()) || TextUtils.isEmpty(txtEmail.getText().toString())) {
                Toast.makeText(Dados.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                boolean certo = true;
                String cmd = "SELECT _rowid_ _id, email, nome_usuario, senha FROM usuario WHERE nome_usuario like '";
                SessionManagement sm = new SessionManagement(Dados.this);
                cmd = cmd+sm.getSession();
                cmd = cmd+"'";
                Cursor cursor = bd.rawQuery(cmd, null);
                cursor.moveToNext();
                String senha = cursor.getString(cursor.getColumnIndex("senha"));
                String usu = txtUsuario.getText().toString();
                String email = txtEmail.getText().toString();
                cmd = "UPDATE usuario SET nome_usuario='";
                cmd = cmd+usu;
                cmd = cmd+"', email='";
                cmd = cmd+email;
                cmd = cmd+"' WHERE nome_usuario like '";
                cmd = cmd+sm.getSession();
                cmd = cmd+"'";
                try{
                    bd.execSQL(cmd);
                }catch (Exception e){
                    Toast.makeText(Dados.this, "Não foi possível alterar :(", Toast.LENGTH_SHORT).show();
                    certo = false;
                }
                if (certo) {
                    cmd = "SELECT * FROM usu_nivel WHERE nome_usu like '";
                    cmd = cmd + sm.getSession();
                    cmd = cmd +"' ORDER BY nivel";
                    cursor = bd.rawQuery(cmd, null);
                    cursor.moveToNext();
                    String nivel = cursor.getString(cursor.getColumnIndex("nivel"));
                    cmd = "DELETE usu_nivel WHERE nome_usu like '";
                    cmd = cmd+sm.getSession();
                    cmd = cmd+"'";
                    cmd = "INSERT INTO usu_nivel VALUES ('";
                    cmd = cmd + email;
                    cmd = cmd + "', '";
                    cmd = cmd + usu;
                    cmd = cmd + "', '";
                    cmd = cmd + nivel;
                    cmd = cmd + "')";
                    try {
                        bd.execSQL(cmd);
                    } catch (Exception e) {
                    }
                    try {
                        bd.execSQL(cmd);
                    } catch (Exception e) {
                        certo = false;
                        Toast.makeText(Dados.this, "Algo deu errado...", Toast.LENGTH_SHORT).show();
                    }
                    if (certo) {
                        cmd = "SELECT _rowid_ _id, email, nome_usuario, senha FROM usuario WHERE nome_usuario like '";
                        cmd = cmd+usu;
                        cmd = cmd+"'";
                        cursor = bd.rawQuery(cmd, null);
                        cursor.moveToNext();
                        Usuario u = new Usuario(senha, cursor.getString(cursor.getColumnIndex("email")), cursor.getString(cursor.getColumnIndex("nome_usuario")));
                        sm.saveSession(u);
                        Toast.makeText(Dados.this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}