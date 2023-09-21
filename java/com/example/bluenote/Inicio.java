package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {
    private Button btnTeste;
    private Button btnPerfil;
    private Button btnNiveis;
    private TextView txtEntrada;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        String cmd;
        btnTeste = findViewById(R.id.btnTeste);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnNiveis = findViewById(R.id.btnNiveis);
        txtEntrada = findViewById(R.id.txtEntrada);

        Intent i = getIntent();
        SessionManagement sm = new SessionManagement(Inicio.this);
        txtEntrada.setText("Bem-vind@, "+sm.getSession());

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);

        cmd = "CREATE TABLE IF NOT EXISTS usu_nivel(" +
                "email VARCHAR(255), " +
                "nome_usu VARCHAR(255), " +
                "nivel integer, " +
                "foreign key(email, nome_usu) references usuario(email, nome_usuario), " +
                "foreign key(nivel) references nivel(dificuldade), " +
                "primary key(email, nome_usu, nivel))";
        bd.execSQL(cmd);

        btnTeste.setOnClickListener(new EscutadorBotao());
        btnPerfil.setOnClickListener(new EscutadorBotao());
        btnNiveis.setOnClickListener(new EscutadorBotao());

        cmd = "SELECT * FROM usu_nivel WHERE nome_usu like ' ";
        cmd = cmd+sm.getSession();
        cmd = cmd+"'";
        Cursor cursor = bd.rawQuery(cmd, null);
        if (cursor.getCount() == 0){
            btnPerfil.setClickable(false);
            btnPerfil.setAlpha(.5f);
            btnNiveis.setClickable(false);
            btnNiveis.setAlpha(.5f);
        } else {
            btnPerfil.setClickable(true);
            btnPerfil.setAlpha(1);
            btnNiveis.setClickable(true);
            btnNiveis.setAlpha(1);
            btnTeste.setVisibility(View.GONE);
        }
    }

    private class EscutadorBotao implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            switch ( b.getId() ) {
                case R.id.btnTeste:
                    Intent i1 = new Intent(getApplicationContext(), Teste_nivelador.class);
                    startActivity(i1);
                    finish();
                    break;
                case R.id.btnPerfil:
                    Intent i2 = new Intent(getApplicationContext(), Perfil.class);
                    startActivity(i2);
                    break;
                case R.id.btnNiveis:
                    Intent i3 = new Intent(getApplicationContext(), Niveis.class);
                    startActivity(i3);
                    finish();
                    break;
            }
        }
    }
}
    
