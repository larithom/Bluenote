package com.example.bluenote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Resultado_tn extends AppCompatActivity {
    private TextView txtMensagem;
    private Button btnPorcentagem;
    private Button btnNivel;
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_tn);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        String cmd;
        txtMensagem = findViewById(R.id.txtMensagem);
        btnPorcentagem = findViewById(R.id.btnPorcentagem);
        btnNivel = findViewById(R.id.btnNivel);

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);

        SessionManagement sm = new SessionManagement(Resultado_tn.this);
        String email = sm.getSessionMeia().split(",")[0];
        String nome_usu = sm.getSessionMeia().split(",")[1];

        Intent i = getIntent();
        Double porcentagem = i.getDoubleExtra("porcentagem", 0);
        btnPorcentagem.setText(porcentagem+"%");

        if (porcentagem < 25){
            txtMensagem.setText("Bora aprender!");
            cmd = "INSERT INTO usu_nivel VALUES ('";
            cmd = cmd + email;
            cmd = cmd + "', '";
            cmd = cmd + nome_usu;
            cmd = cmd + "', 1)";
            try {
                bd.execSQL(cmd);
            } catch (Exception e) {
            }
        } else if (porcentagem < 50) {
            txtMensagem.setText("Vamos aprender um pouco +");
            cmd = "INSERT INTO usu_nivel VALUES ('";
            cmd = cmd + email;
            cmd = cmd + "', '";
            cmd = cmd + nome_usu;
            cmd = cmd + "', 2)";
            try {
                bd.execSQL(cmd);
            } catch (Exception e) {
            }
        } else if (porcentagem < 75) {
            txtMensagem.setText("Vamos relembrar!");
            cmd = "INSERT INTO usu_nivel VALUES ('";
            cmd = cmd + email;
            cmd = cmd + "', '";
            cmd = cmd + nome_usu;
            cmd = cmd + "', 3)";
            try {
                bd.execSQL(cmd);
            } catch (Exception e) {
            }
        } else {
            txtMensagem.setText("Gostaria de me ensinar?");
            cmd = "INSERT INTO usu_nivel VALUES ('";
            cmd = cmd + email;
            cmd = cmd + "', '";
            cmd = cmd + nome_usu;
            cmd = cmd + "', 4)";
            try {
                bd.execSQL(cmd);
            } catch (Exception e) {
            }
        }

        btnNivel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(getApplicationContext(), Niveis.class);
                startActivity(i3);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i3 = new Intent(getApplicationContext(), Inicio.class);
        startActivity(i3);
        finish();
        super.onBackPressed();
    }
}