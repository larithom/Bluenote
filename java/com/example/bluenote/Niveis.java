package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Niveis extends AppCompatActivity {
    private Button btnNivel1;
    private Button btnNivel2;
    private Button btnNivel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveis);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        btnNivel1 = findViewById(R.id.btnNivel1);
        btnNivel2 = findViewById(R.id.btnNivel2);
        btnNivel3 = findViewById(R.id.btnNivel3);

        btnNivel1.setOnClickListener(new EscutadorBotao());
    }

    private class EscutadorBotao implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            switch ( b.getId() ) {
                case R.id.btnNivel1:
                    Intent i1 = new Intent(getApplicationContext(), Nivel1.class);
                    startActivity(i1);
                    break;
                case R.id.btnNivel2:
                    break;
                case R.id.btnNivel3:
                    break;
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent i3 = new Intent(getApplicationContext(), Inicio.class);
        startActivity(i3);
        finish();
        super.onBackPressed();
    }
}