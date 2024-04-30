package minesweeper.modelo;

import minesweeper.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean aberto = false;
    private boolean minado = false;
    private boolean bandeira = false;
    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }
    public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;
        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;
        boolean result = false;
        if(deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            result = true;
        }else if(deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            result = true;
        }
        return result;
    }
    public void alternarBandeira(){
        if(!aberto)
            bandeira = !bandeira;
    }
    boolean abrir(){
        if(!aberto && !bandeira){
            aberto = true;
            if(minado){
                throw new ExplosaoException();
            }
            if(vizinhancaSegura()){
                vizinhos.forEach(n -> n.abrir());
            }
            return true;
        }
        return false;
    }
    boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(n ->n.minado);
    }
    public boolean hasBandeira(){
        return bandeira;
    }
    public void AdicionarMina(){
        if(!minado)
            this.minado = true;
    }
    public boolean isAberto(){
        return aberto;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && bandeira;
        return desvendado || protegido;
    }
    long minasNaVizinhanca(){
        return vizinhos.stream().filter(n -> n.minado).count();
    }
    void reniciar(){
        aberto = false;
        minado = false;
        bandeira = false;
    }
    public String toString(){
        if(bandeira){
            return "X";
        }
        else if(minado && aberto){
            return "*";
        }
        else if(aberto && minasNaVizinhanca() > 0){
            return minasNaVizinhanca() + "";
        }
        else if(aberto){
            return " ";
        }
        else{
            return "?";
        }
    }

    public boolean isMinado() {
        return minado;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }
}
