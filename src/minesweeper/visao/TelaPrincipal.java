package minesweeper.visao;

import minesweeper.modelo.Tabuleiro;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
        add(new PainelTabuleiro(tabuleiro));
        setVisible(true);
        setTitle("MineSweeper");
        setLocationRelativeTo(null);
        setSize(690,438);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public static void main(String[] args) {
        new TelaPrincipal();
    }
}