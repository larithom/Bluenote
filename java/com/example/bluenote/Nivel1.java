package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Nivel1 extends AppCompatActivity {
    private Button btnTeoria;
    private Button btnPratica;
    private Button btnInicio;
    private Button btnTesteF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel1);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        btnTeoria = findViewById(R.id.btnTeoria);
        btnPratica = findViewById(R.id.btnPratica);
        btnInicio = findViewById(R.id.btnInicio);
        btnTesteF = findViewById(R.id.btnTesteF);

        btnTeoria.setOnClickListener(new EscutadorBotao());
        btnInicio.setOnClickListener(new EscutadorBotao());
    }

    private class EscutadorBotao implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            switch (b.getId()) {
                case R.id.btnTeoria:
                    Intent i1 = new Intent(getApplicationContext(), Teoria1.class);
                    startActivity(i1);
                    break;
                case R.id.btnPratica:
                    break;
                case R.id.btnInicio:
                    Intent i3 = new Intent(getApplicationContext(), Inicio.class);
                    startActivityForResult(i3, 1);
                    finish();
                    break;
                case R.id.btnTesteF:
                    break;
            }
        }
    }
}