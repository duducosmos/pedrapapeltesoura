package br.com.bigdatatec.pedrapapeltesoura;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private Button play;
    private ImageButton somonoff;
    private RadioGroup escolha;
    private RadioButton radioButtonEscolha;
    private TextView placarJogador;
    private TextView placarComputador;
    private ImageView maoComputador;
    private ImageView maoJogador;
    private PPTEngine pptEngine;
    private Integer maxPonto = 10;
    private Context context;
    private GerenciadorSom som;
    private boolean somOn;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.jogar);
        somonoff = (ImageButton) findViewById(R.id.somonoff);
        escolha = (RadioGroup) findViewById(R.id.escolha);

        placarJogador = (TextView) findViewById(R.id.placarJog);
        placarComputador = (TextView) findViewById(R.id.placarComp);

        context = getApplicationContext();


        maoComputador = (ImageView) findViewById(R.id.computador);
        maoComputador.setRotation(180);
        maoComputador.setImageResource(R.drawable.pedra);
        maoJogador = (ImageView) findViewById(R.id.jogador);
        maoJogador.setImageResource(R.drawable.pedra);

        mShared = getSharedPreferences(getString(R.string.estadosom),0);

        somOn = mShared.getBoolean("som", true);

        if(somOn == false) {
            somonoff.setImageResource(R.drawable.somoff);
        }
        pptEngine = new PPTEngine();

        som = new GerenciadorSom(somOn, context);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("38CFBE22A69549342697AA9C2D44700E")
                .build();


        mAdView.loadAd(adRequest);


    }

    public void somonoff(View view){
        if(somOn == true) {
            somOn = false;

            somonoff.setImageResource(R.drawable.somoff);
        }
        else{
            somOn = true;
            somonoff.setImageResource(R.drawable.somon);
        }
        som.setSoundOn(somOn);

        mEdit = mShared.edit();
        mEdit.putBoolean("som", somOn);
        mEdit.commit();


    }

    private void setCompImage(int v){
        if(v == 0){
            maoComputador.setImageResource(R.drawable.tesoura);
        }
        else if(v == 1){
            maoComputador.setImageResource(R.drawable.papel);
        }
        else {
            maoComputador.setImageResource(R.drawable.pedra);
        }

    }

    public void play(View view){
        int selectedId = escolha.getCheckedRadioButtonId();


        radioButtonEscolha = (RadioButton) findViewById(selectedId);
        CharSequence escolhido = radioButtonEscolha.getText();

        if(radioButtonEscolha.getText() == getString(R.string.pedra)){
            maoJogador.setImageResource(R.drawable.pedra);
            setCompImage(pptEngine.play(2, som));
        }
        else if(escolhido == getString(R.string.papel)){
            maoJogador.setImageResource(R.drawable.papel);
            setCompImage(pptEngine.play(1, som));
        }
        else if(escolhido == getString(R.string.tesoura)){
            maoJogador.setImageResource(R.drawable.tesoura);
            setCompImage(pptEngine.play(0, som));
        }

        if(Integer.parseInt(pptEngine.getPlacarComputador()) == maxPonto ||
                Integer.parseInt(pptEngine.getPlacarJogador()) == maxPonto){

            //maoJogador.setImageResource(R.drawable.pedra);
            //maoComputador.setImageResource(R.drawable.pedra);
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("Jogador", pptEngine.getPlacarJogador());
            intent.putExtra("Computador", pptEngine.getPlacarComputador());
            intent.putExtra("Som", Boolean.toString(somOn));

            startActivity(intent);
            pptEngine.restart();
            finish();

        }

        placarJogador.setText(pptEngine.getPlacarJogador());
        placarComputador.setText(pptEngine.getPlacarComputador());




    }

 }
