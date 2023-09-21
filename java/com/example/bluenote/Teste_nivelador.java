package com.example.bluenote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Teste_nivelador extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    private MediaPlayer mediaPlayer4;

    private Boolean rodando = false;
    private Boolean rodando2 = false;
    private Boolean rodando3= false;
    private Boolean rodando4 = false;

    private TextView txtPergunta;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private SQLiteDatabase bd;
    private ImageView imgProximo;
    private double resultado;
    private ImageView imgPergunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_nivelador);
        ActionBar ab = getSupportActionBar();
        ab.hide();

        txtPergunta = findViewById(R.id.txtPergunta);
        btn1 = findViewById(R.id.btnOpc1);
        btn2 = findViewById(R.id.btnOpc2);
        btn3 = findViewById(R.id.btnOpc3);
        btn4 = findViewById(R.id.btnOpc4);
        imgProximo = findViewById(R.id.imageView);
        imgPergunta = findViewById(R.id.imgPergunta);

        bd = openOrCreateDatabase("bluenote", MODE_PRIVATE, null);
        String cmd;


        //criando tabelas
        cmd = "CREATE TABLE IF NOT EXISTS teste_nivelador(" +
                "id char(1) not null, " +
                "primary key(id))";
        bd.execSQL(cmd);

        cmd = "CREATE TABLE IF NOT EXISTS usu_nivel(" +
                "email varchar(255), " +
                "nome_usu varchar(255), " +
                "nivel integer, " +
                "foreign key(email, nome_usu) references usuario(email, nome_usuario), " +
                "foreign key(nivel) references nivel(dificuldade), " +
                "primary key(email, nome_usu, nivel))";
        bd.execSQL(cmd);

        cmd = "CREATE TABLE IF NOT EXISTS pergunta(" +
                "texto varchar(255) not null, " +
                "resposta varchar(255)  not null, " +
                "assunto varchar(255), " +
                "img varchar(255), " +
                "som varchar(255), " +
                "id_atv char(1), " +
                "id_teste char(1), " +
                "foreign key(assunto) references assunto(descricao), " +
                "foreign key(id_atv) references atividade(id), " +
                "foreign key(id_teste) references teste(id), " +
                "primary key(texto))";
        bd.execSQL(cmd);

        cmd = "CREATE TABLE IF NOT EXISTS opcao(" +
                "texto_pg varchar(255), " +
                "texto_opc varchar(255) not null, " +
                "img varchar(255), " +
                "som varchar(255), " +
                "foreign key(texto_pg) references pergunta(texto), " +
                "primary key(texto_pg, texto_opc))";
        bd.execSQL(cmd);

        //adicionando dados
        Cursor cursor = bd.rawQuery("SELECT count(*) FROM pergunta", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
           cmd = "INSERT INTO pergunta VALUES ('Os intervalos apresentados a seguir são respectivamente:', 'Uma 7 Menor e uma 5 Diminuta', 'Intervalos', '/raw/intervalo_1.png', null, 1, 1)";
           bd.execSQL(cmd);
           cmd = "INSERT INTO pergunta VALUES ('Dois instrumentos melódicos e dois harmônicos são respectivamente', 'Saxofone, violino, piano e guitarra', 'Harmonia', null, null, 2, 2)";
           bd.execSQL(cmd);
           cmd = "INSERT INTO pergunta VALUES ('O acorde tocado acima é denominado de:', 'D#m7M', 'Percepção', '/raw/percepcao_1.png', null, 3, 3)";
           bd.execSQL(cmd);
            cmd = "INSERT INTO pergunta VALUES ('escolha o elefante mais barulhento: ', 'elefante 2', 'teste', null, null, 4, 4)";
            bd.execSQL(cmd);
        }

        cursor = bd.rawQuery("SELECT count(*) FROM opcao", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
            cmd = "INSERT INTO opcao VALUES ('escolha o elefante mais barulhento: ', 'elefante 3', null, 'R.raw.imyoung')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('escolha o elefante mais barulhento: ', 'elefante 1', null, 'R.raw.valsinha')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('escolha o elefante mais barulhento: ', 'elefante 2', null, 'R.raw.forever')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('escolha o elefante mais barulhento: ', 'elefante 4', null, 'R.raw.attentionplease')";
            bd.execSQL(cmd);

            cmd = "INSERT INTO opcao VALUES ('Dois instrumentos melódicos e dois harmônicos são respectivamente', 'Saxofone, violino, piano e guitarra', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Dois instrumentos melódicos e dois harmônicos são respectivamente', 'Saxofone, flauta doce, piano e clarinete', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Dois instrumentos melódicos e dois harmônicos são respectivamente', 'Escaleta, baixo elétrico, trompete e trombone', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Dois instrumentos melódicos e dois harmônicos são respectivamente', 'flauta transversal, piano, guitarra e saxofone', null, null)";
            bd.execSQL(cmd);

            cmd = "INSERT INTO opcao VALUES ('Os intervalos apresentados a seguir são respectivamente:', 'Uma 7 Menor e uma 5 Diminuta', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Os intervalos apresentados a seguir são respectivamente:', 'Uma 7 Menor e uma 5 Aumentada', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Os intervalos apresentados a seguir são respectivamente:', 'Uma 7 Maior e uma 5 Diminuta', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('Os intervalos apresentados a seguir são respectivamente:', 'Uma 7 Maior e uma 5 Aumentada', null, null)";
            bd.execSQL(cmd);

            cmd = "INSERT INTO opcao VALUES ('O acorde tocado acima é denominado de:', 'D#m7M', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('O acorde tocado acima é denominado de:', 'D#7M', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('O acorde tocado acima é denominado de:', 'D#m7', null, null)";
            bd.execSQL(cmd);
            cmd = "INSERT INTO opcao VALUES ('O acorde tocado acima é denominado de:', 'D#7', null, null)";
            bd.execSQL(cmd);
        }
        cursor = bd.rawQuery("SELECT count(*) FROM assunto", null);
        cursor.moveToFirst();
        if (cursor.getInt(0) == 0){
            cmd = "INSERT INTO assunto VALUES('Intervalos')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO assunto VALUES('Harmonia')";
            bd.execSQL(cmd);
            cmd = "INSERT INTO assunto VALUES('Percepção')";
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
        }

        cursor = bd.rawQuery("SELECT p._rowid_ _id, p.texto, p.img, opc.som, opc.texto_opc FROM pergunta p, opcao opc WHERE p.texto=opc.texto_pg", null);
        cursor.moveToNext();
        txtPergunta.setText(cursor.getString(cursor.getColumnIndex("texto")));
        if (cursor.getString(cursor.getColumnIndex("img")) != null){
            //File imgFile = new File(cursor.getString(cursor.getColumnIndex("img")));
            //Bitmap mb = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //imgPergunta.setImageBitmap(BitmapFactory.decodeFile("file://" + cursor.getString(cursor.getColumnIndex("img"))));
            imgPergunta.setVisibility(View.VISIBLE);
        } else {
            imgPergunta.setVisibility(View.GONE);
        }
        btn1.setText(cursor.getString(cursor.getColumnIndex("texto_opc")));
        cursor.moveToNext();
        btn2.setText(cursor.getString(cursor.getColumnIndex("texto_opc")));
        cursor.moveToNext();
        btn3.setText(cursor.getString(cursor.getColumnIndex("texto_opc")));
        cursor.moveToNext();
        btn4.setText(cursor.getString(cursor.getColumnIndex("texto_opc")));

        /*
        mediaPlayer = MediaPlayer.create(this, R.raw.attentionplease);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.imyoung);
        mediaPlayer3 = MediaPlayer.create(this, R.raw.forever);
        mediaPlayer4 = MediaPlayer.create(this, R.raw.valsinha);
        */

        Cursor finalCursor = cursor;
        imgProximo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                String pergunta = (String) txtPergunta.getText();
                String resposta;
                if (btn1.getBackgroundTintList() == ColorStateList.valueOf(Color.parseColor("#E91E63"))) {
                    resposta = (String) btn1.getText();
                } else if (btn2.getBackgroundTintList() == ColorStateList.valueOf(Color.parseColor("#E91E63"))) {
                    resposta = (String) btn2.getText();
                } else if (btn3.getBackgroundTintList() == ColorStateList.valueOf(Color.parseColor("#E91E63"))) {
                    resposta = (String) btn3.getText();
                } else {
                    resposta = (String) btn4.getText();
                }

                String cmdfinal = "SELECT _rowid_ _id, resposta FROM pergunta WHERE texto like '";
                cmdfinal = cmdfinal+pergunta;
                cmdfinal = cmdfinal+"'";
                Cursor conferir = bd.rawQuery(cmdfinal, null);
                conferir.moveToNext();

                if (conferir.getString((conferir.getColumnIndex("resposta"))).equals(resposta)){
                    resultado = resultado+1;
                }

                if (finalCursor.moveToNext()){
                    txtPergunta.setText(finalCursor.getString(finalCursor.getColumnIndex("texto")));
                    if (finalCursor.getString(finalCursor.getColumnIndex("img")) != null){
                        //imgPergunta.setImageBitmap(BitmapFactory.decodeFile("file://" + finalCursor.getString(finalCursor.getColumnIndex("img"))));
                        imgPergunta.setVisibility(View.VISIBLE);
                    } else {
                        imgPergunta.setVisibility(View.GONE);
                    }
                    btn1.setText(finalCursor.getString(finalCursor.getColumnIndex("texto_opc")));
                    finalCursor.moveToNext();
                    btn2.setText(finalCursor.getString(finalCursor.getColumnIndex("texto_opc")));
                    finalCursor.moveToNext();
                    btn3.setText(finalCursor.getString(finalCursor.getColumnIndex("texto_opc")));
                    finalCursor.moveToNext();
                    btn4.setText(finalCursor.getString(finalCursor.getColumnIndex("texto_opc")));
                } else {
                    Intent i3 = new Intent(getApplicationContext(), Resultado_tn.class);
                    double porcentagem = (resultado/(finalCursor.getCount()/4))*100;
                    i3.putExtra("porcentagem", porcentagem);
                    startActivity(i3);
                    finish();
                }
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                /*
                if (rodando){
                    mediaPlayer.pause();
                    rodando = false;
                } else {
                    mediaPlayer.start();
                    rodando = true;
                }
                 */
                    }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                /*
                if (rodando2){
                    mediaPlayer2.pause();
                    rodando2 = false;
                } else {
                    mediaPlayer2.start();
                    rodando2 = true;
                }
                 */
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                /*
                if (rodando3){
                    mediaPlayer3.pause();
                    rodando3 = false;
                } else {
                    mediaPlayer3.start();
                    rodando3 = true;
                }
                 */
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                btn4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                btn1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                /*
                if (rodando4){
                    mediaPlayer4.pause();
                    rodando4 = false;
                } else {
                    mediaPlayer4.start();
                    rodando4 = true;
                }
                 */
            }
        });
    }
        /*
        if (som!=null && img!=null){
            //posicao do xml diferente para img e som e botao para player de musica
        } else if (som!= null) {
            //posicao do xml diferente e botao para player de musica
        } else if (img!= null) {
            //posicao do xml diferente
        } else {
            txtPergunta.setText(texto);
        }
         */
    }
        /*
        if (cont == 5){
            cont = 0;
        }
        if (som!=null && img!=null){
            //posicao do xml diferente para img e som em botao para musica
        } else if (som!= null) {
            //botao para player de musica
        } else if (img!= null) {
            //posicao do xml diferente
        } else {
            if (cont == 1) {
                btn1.setText(texto_opc);
            } else if (cont == 2) {
                btn2.setText(texto_opc);
            } else if (cont == 3) {
                btn3.setText(texto_opc);
            } else {
                btn4.setText(texto_opc);
            }
        }
         */

