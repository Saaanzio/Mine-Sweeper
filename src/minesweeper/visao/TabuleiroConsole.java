package minesweeper.visao;
import minesweeper.excecao.ExplosaoException;
import minesweeper.excecao.SairException;
import minesweeper.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);
    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        jogar();
    }

    private void jogar() {
        try{
            boolean continuar = true;

            while(continuar){
                cicloDoJogo();
                System.out.println("Jogar? (S/n)");
                String resposta = entrada.nextLine();
                if("n".equalsIgnoreCase(resposta)){{
                continuar = false;}
                }
                else{
                    tabuleiro.reniciar();
                }
            }
        }catch(SairException e){
            System.out.println("Fim de Jogo!");
        }
        finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try{
            while(!tabuleiro.Vitoria()){
                System.out.println(tabuleiro);
                String digitado = pegarValor("Digite as coordenadas (x,y): ");
                Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(Integer::parseInt).iterator();
                digitado = pegarValor("1 - Abrir / 2 - Colocar Bandeira");
                if("1".equals(digitado)){
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if("2".equals(digitado)){
                    tabuleiro.colocarBandeira(xy.next(), xy.next());
                }
            }
            System.out.println("Você ganhou!");
        }catch(ExplosaoException e){
            System.out.println(tabuleiro);
            System.out.println("Fim de Jogo!");
        }
    }
    private String pegarValor(String texto){
        System.out.print(texto);
        String digitado = entrada.nextLine();
        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }
        return digitado;
    }
}
