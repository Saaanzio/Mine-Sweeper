import minesweeper.modelo.Tabuleiro;
import minesweeper.visao.TabuleiroConsole;

public class Main {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(6,6,8);
        new TabuleiroConsole(tabuleiro);

    }
}