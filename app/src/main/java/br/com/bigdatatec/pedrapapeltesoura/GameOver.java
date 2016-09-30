package br.com.bigdatatec.pedrapapeltesoura;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    private TextView gameover;
    private Context context;
    private int jogador;
    private int computador;

    private TextView placarJogador;
    private TextView placarComputador;
    private TextView fim;
    private TextView jogadorTexto;
    private TextView computadorTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        gameover = (TextView) findViewById(R.id.gameover);
        placarComputador = (TextView) findViewById(R.id.placarComputador2);
        placarJogador = (TextView) findViewById(R.id.placarJogador2);
        fim = (TextView) findViewById(R.id.fimDeJogo);
        jogadorTexto = (TextView) findViewById(R.id.jogadorTexto);
        computadorTexto = (TextView) findViewById(R.id.computadorTexto);

        context = getApplicationContext();

        final Typeface font = Typeface.createFromAsset(context.getAssets(),
                "fonts/kenpixel_blocks.ttf");

        final Typeface fontFuture = Typeface.createFromAsset(context.getAssets(),
                "fonts/kenpixel_mini_square.ttf");

        gameover.setTypeface(font);
        gameover.setTextSize(42);
        fim.setTypeface(fontFuture);
        computadorTexto.setTypeface(fontFuture);
        jogadorTexto.setTypeface(fontFuture);




        Intent intent = getIntent();
        if(intent.hasExtra("Jogador")){
            jogador = Integer.parseInt(intent.getStringExtra("Jogador"));
            computador = Integer.parseInt(intent.getStringExtra("Computador"));
            if(jogador > computador){
                fim.setText(getText(R.string.ganhou));
                fim.setTextColor(Color.BLUE);
                placarComputador.setTextColor(Color.RED);
                boolean somOn = Boolean.parseBoolean(intent.getStringExtra("Som"));
                if(somOn == true) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.youwin);
                    mediaPlayer.start();
                }
            }
            else {
                fim.setText(getText(R.string.perdeu));
                fim.setTextColor(Color.RED);
                placarJogador.setTextColor(Color.RED);

            }
            placarComputador.setText(intent.getStringExtra("Computador"));
            placarJogador.setText(intent.getStringExtra("Jogador"));
        }
    }

    public void playAgain(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


}
