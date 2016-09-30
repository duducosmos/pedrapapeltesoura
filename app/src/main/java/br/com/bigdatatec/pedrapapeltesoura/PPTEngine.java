package br.com.bigdatatec.pedrapapeltesoura;

import java.util.Hashtable;
import java.util.Random;

/**
 * Created by eduardo on 06/09/16.
 */
public class PPTEngine {
    private int[] a = {1, 1, 1};
    private final int memoryLoss = 4;
    private final int memoryMax = 8;
    private int placarJogador= 0;
    private int placarComputador = 0;
    private Hashtable<Integer, Integer> saidas = new Hashtable<>();


    private Hashtable<Integer, Hashtable> estados = new Hashtable<>();

    public PPTEngine(){
        setEstados();
        setSaidas();
    }

    private void setSaidas(){
        saidas.put(0,2);
        saidas.put(1,0);
        saidas.put(2,1);
    }
    public String getPlacarJogador(){
        return Integer.toString(this.placarJogador);
    }

    public String getPlacarComputador(){
        return Integer.toString(this.placarComputador);
    }

    public void restart(){
        for(int i = 0; i < a.length; i++){
            a[i] = 1;
        }

        this.placarJogador = 0;
        this.placarComputador = 0;
    }

    public int play(int jogador, GerenciadorSom som){
        int computador = ia(jogador);
        if(computador == jogador){

        }
        else if(deltafunc(computador, jogador) == true){
            this.placarComputador += 1;
            som.lossPlayer();
        }
        else{
            this.placarJogador += 1;
            som.winPlayer();
        }

        return computador;
    }

    public int ia(int v){
        a[v] += 1;
        int comput;

        for(int i=0; i < a.length; i++) {


            if (a[i] >= memoryMax) {
                for (int j = 0; j < a.length; j++) {
                    a[j] = (int) (((float) a[j]) / memoryLoss);
                }
            }

            float sum = 0;
            for(int k = 0; k < a.length; k++){
                sum += a[k];
            }

            Random r = new Random(System.currentTimeMillis());

            float t = ((float) a[i]) / sum;

            if(r.nextFloat() <= t){
                comput = saidas.get(i);
                return comput;
            }

        }

        Random r = new Random(System.currentTimeMillis());

        return r.nextInt(2);
    }

    public boolean deltafunc(int comp, int jog){
        Hashtable d = this.estados.get(comp);
        Boolean res = (Boolean) d.get(jog);
        return res;
    }


    private Hashtable transicoes(Integer[] est, boolean[] passagens){
        Hashtable<Integer, Boolean> trans = new Hashtable<>();
        for(int i=0; i < est.length; i++){
            trans.put(est[i], passagens[i]);
        }
        return trans;
    }

    public void setEstados(){
        this.estados = delta();
    }


    private Hashtable delta(){

        /*
        Função de Transição para Tesoura, Papel e Pedra
        Estados | Tesoura (0) | Papel (1) | Pedra (2) |
        Tesoura |     False   | True      | False     |
        Papel   |     False   | False     | True      |
        Pedra   |     True    | False     | False     |
        * */

        Hashtable<Integer, Hashtable> estados = new Hashtable<Integer, Hashtable>();

        Integer[] est = {0, 1, 2};
        boolean[] passagens = {false, true, false};


        estados.put(0,transicoes(est,passagens));

        passagens[1] = false;
        passagens[2] = true;
        estados.put(1,transicoes(est,passagens));

        passagens[0] = true;
        passagens[1] = false;
        passagens[2] = false;
        estados.put(2,transicoes(est,passagens));
        return estados;

    }



}
