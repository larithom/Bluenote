package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Teoria1 extends AppCompatActivity {
    private Button btnVoltar;
    private ImageView imgProxima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teoria1);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        btnVoltar = findViewById(R.id.btnVoltar);
        imgProxima = findViewById(R.id.imgProxima);

        btnVoltar.setOnClickListener(new EscutadorBotao());
    }

    private class EscutadorBotao implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            switch ( b.getId() ) {
                case R.id.btnVoltar:
                    finish();
                    break;
            }
        }
    }


}