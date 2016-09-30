package br.com.bigdatatec.pedrapapeltesoura;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by eduardo on 06/09/16.
 */
public class GerenciadorSom {

    private MediaPlayer win;
    private MediaPlayer loss;
    private MediaPlayer endGameWin;
    private boolean soundOn = true;


    public GerenciadorSom(boolean soundOn, Context context){
        setSoundOn(soundOn);
        win = MediaPlayer.create(context, R.raw.win);
        loss = MediaPlayer.create(context, R.raw.lose);
        endGameWin = MediaPlayer.create(context, R.raw.youwin);

    }

    public void stopSounds(){
        if(endGameWin.isPlaying()){
            endGameWin.stop();
        }
        if(win.isPlaying()){
            win.stop();
        }
        if(loss.isPlaying()){
            loss.stop();
        }
    }



    public void gameWin(){
        if(this.soundOn == true){
            endGameWin.start();
        }
    }

    public void winPlayer(){
        if(this.soundOn == true){
            win.start();
        }
    }

    public void lossPlayer(){
        if(this.soundOn == true){
            loss.start();
        }
    }

    public void setSoundOn(boolean soundOn){
        this.soundOn = soundOn;
    }
}
