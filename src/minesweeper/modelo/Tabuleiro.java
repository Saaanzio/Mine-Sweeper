package minesweeper.modelo;

import minesweeper.excecao.ExplosaoException;

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
    public void abrir(int linha, int coluna){
        try{
            campos.stream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(Campo::abrir);
        }catch(ExplosaoException e){
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }

    }
    public void colocarBandeira(int linha, int coluna){
        campos.stream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::alternarBandeira);
    }
    private void criarMinas() {
        long minasAtuais = 0;

        do{
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).AdicionarMina();
            minasAtuais = campos.stream().filter(Campo::isMinado).count();
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
        StringBuilder sb = new StringBuilder();
            sb.append(" ");
            sb.append(" ");
        for(int j = 0; j < qntColunas; j++){
            sb.append(" ");
            sb.append(j);
            sb.append(" ");
        }
        sb.append("\n");
        int k = 0;
        for(int i = 0; i < qntLinhas; i++){
            sb.append(i);
            sb.append(" ");
            for(int j = 0; j < qntColunas; j++){
                sb.append(" ");
                sb.append(campos.get(k));
                sb.append(" ");
                k++;

            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
