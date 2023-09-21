package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);
        String cmd;

        //criando tabelas
        cmd = "CREATE TABLE IF NOT EXISTS assunto(" +
                "descricao varchar(255) NOT NULL, " +
                "PRIMARY KEY(descricao))";
        bd.execSQL(cmd);
        cmd = "CREATE TABLE IF NOT EXISTS nivel(" +
                "dificuldade integer NOT NULL, " +
                "assunto varchar(255), " +
                "FOREIGN KEY(assunto) REFERENCES assunto(descricao), " +
                "PRIMARY KEY(dificuldade))";
        bd.execSQL(cmd);
        cmd = "CREATE TABLE IF NOT EXISTS teste_nivelador(" +
                "id char(1) NOT NULL, " +
                "PRIMARY KEY(id))";
        bd.execSQL(cmd);

        //adicionando dados
        Cursor cursor = bd.rawQuery("SELECT count(*) FROM assunto", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
            cmd = "INSERT INTO assunto VALUES('Intervalos')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO assunto VALUES('Harmonia')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO assunto VALUES('Percepção')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO assunto VALUES('teste')";
            bd.execSQL(cmd);
        }

        cursor = bd.rawQuery("SELECT count(*) FROM nivel", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
            cmd = "INSERT INTO nivel VALUES(1 , 'Intervalos')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO nivel VALUES(2, 'Harmonia')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO nivel VALUES(3, 'Percepção')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO nivel VALUES(4, 'teste')";
        }

        cursor = bd.rawQuery("SELECT count(*) FROM teste_nivelador", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
            cmd = "INSERT INTO teste_nivelador VALUES(1)";
            bd.execSQL(cmd);
        }

        //transicao
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        }, 2500);
    }
}