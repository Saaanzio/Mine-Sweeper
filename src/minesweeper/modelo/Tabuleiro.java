package minesweeper.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int qntLinhas;
    private int qntColunas;
    private int minas;
    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int qntLinhas, int qntColunas, int minas) {
        this.qntLinhas = qntLinhas;
        this.qntColunas = qntColunas;
        this.minas = minas;

        criarCampos();
        criarVizinhos();
        criarMinas();
    }

    private void criarMinas() {
        long minasAtuais = 0;

        do{
            minasAtuais = campos.stream().filter(Campo::isMinado).count();
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).AdicionarMina();
        }while(minasAtuais < minas);
    }

    private void criarVizinhos() {
        for(Campo c:campos){
            for(Campo c1: campos){
                c.adicionarVizinho(c1);
            }
        }
    }

    private void criarCampos() {
        for(int i = 0; i<qntLinhas; i++) {
            for(int j = 0; j<qntColunas; j++) {
                campos.add(new Campo(i, j));
            }
        }
    }
    public boolean Vitoria(){
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }
    public void reniciar(){
        campos.stream().forEach(Campo::reniciar);
    }
    public String toString(){
        return "";
    }
}
