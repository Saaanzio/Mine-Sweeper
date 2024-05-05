package minesweeper.visao;

import minesweeper.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {
    public PainelTabuleiro(Tabuleiro tabuleiro){
        setLayout(new GridLayout(tabuleiro.getQntLinhas(), tabuleiro.getQntColunas()));

        tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {

            SwingUtilities.invokeLater(()->{
                if(e.Vitoria()){
                    JOptionPane.showMessageDialog(this, "Vitoria");
                }else{
                    JOptionPane.showMessageDialog(this, "Derrota");
                }
                tabuleiro.reniciar();
            });
        });

    }
}
