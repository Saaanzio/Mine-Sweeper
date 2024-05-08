package minesweeper.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador{
    private final int qntLinhas;
    private final int qntColunas;
    private final int minas;
    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
    public Tabuleiro(int qntLinhas, int qntColunas, int minas) {
        this.qntLinhas = qntLinhas;
        this.qntColunas = qntColunas;
        this.minas = minas;

        criarCampos();
        criarVizinhos();
        criarMinas();
    }
    public void paraCada(Consumer<Campo> funcao){
        campos.forEach(funcao);
    }
    public void registrarObservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }
    private void notificarObservador(Boolean resultado){
        observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public int getQntColunas() {
        return qntColunas;
    }

    public int getQntLinhas() {
        return qntLinhas;
    }

    public void abrir(int linha, int coluna){
            campos.stream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(Campo::abrir);

    }
    private void mostrarMinas(){
        campos.stream()
                .filter(c -> c.isMinado())
                .filter(c -> !c.hasBandeira())
                .forEach(c -> c.setAberto(true));

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
                Campo campo = new Campo(i,j);
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }
    }
    public boolean Vitoria(){
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }
    public void reniciar(){
        campos.stream().forEach(Campo::reniciar);
        criarMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if(evento == CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservador(false);
        }
        else if(Vitoria()){
            notificarObservador(true);
        }

    }

}
