package com.example.bluenote;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Perfil extends AppCompatActivity {
    private Button btnDados;
    private Button btnDeslogar;
    private Button btnExcluir;
    private SQLiteDatabase bd;
    String cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);
        btnDados = findViewById(R.id.btnDados);
        btnDeslogar = findViewById(R.id.btnDeslogar);
        btnExcluir = findViewById(R.id.btnExcluir);

        btnDeslogar.setOnClickListener(new EscutadorBotao());
        btnDados.setOnClickListener(new EscutadorBotao());
        btnExcluir.setOnClickListener(new EscutadorBotao());
    }

    private class EscutadorBotao implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Button b = (Button) view;
            switch ( b.getId() ) {
                case R.id.btnDados:
                    Intent i1 = new Intent(getApplicationContext(), Dados.class);
                    startActivity(i1);
                    break;
                case R.id.btnDeslogar:
                    SessionManagement sm = new SessionManagement(Perfil.this);
                    sm.removeSession();

                    Intent i2 = new Intent(getApplicationContext(), Login.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i2);
                    finish();
                case R.id.btnExcluir:
                    sm = new SessionManagement(Perfil.this);
                    cmd = "DELETE FROM usu_nivel WHERE nome_usu like ' ";
                    cmd = cmd+sm.getSession();
                    cmd = cmd+"'";
                    boolean certo = true;
                    try {
                        bd.execSQL(cmd);
                    } catch (Exception e) {
                        certo = false;
                        Toast.makeText(Perfil.this, "Algo deu errado...", Toast.LENGTH_SHORT).show();
                    }
                    if (certo) {
                        cmd = "DELETE FROM usuario WHERE nome_usuario like '";
                        cmd = cmd+sm.getSession();
                        cmd = cmd+"'";
                        try {
                            bd.execSQL(cmd);
                        } catch (Exception e) {
                            certo = false;
                            Toast.makeText(Perfil.this, "Algo deu errado...", Toast.LENGTH_SHORT).show();
                        }
                        if (certo) {
                            sm.removeSession();
                            Intent i3 = new Intent(getApplicationContext(), Login.class);
                            i3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i3);
                            finish();
                        }
                    }
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
    /*
    public static class Pergunta extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Deseja mesmo sair?").setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //desloga

                }
            }).setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ////faz nada e fecha o prompt

                }
            });
            return builder.create();
        }
    }
     */
}