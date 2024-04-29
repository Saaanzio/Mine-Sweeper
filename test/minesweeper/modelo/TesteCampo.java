package minesweeper.modelo;

import minesweeper.excecao.ExplosaoException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TesteCampo {
    private Campo campo;
    @BeforeEach
    public void iniciarCampo(){
        campo = new Campo(3,3);
    }
    @Test
    void testeAdicionarVizinho(){
        Campo vizinho = new Campo(3,2);
        boolean result = campo.adicionarVizinho(vizinho);
        assertTrue(result);
    }
    @Test
    void testeAdicionarVizinhoErrado(){
        Campo vizinho = new Campo(1,4);
        boolean result = campo.adicionarVizinho(vizinho);
        assertFalse(result);
    }
    @Test
    void TestBandeira(){
        assertFalse(campo.hasBandeira());
    }
    @Test
    void TestBandeiraAlternar(){
        campo.alternarBandeira();
        assertTrue(campo.hasBandeira());
    }
    @Test
    void TestBandeiraAlternarDuasVezes(){
        campo.alternarBandeira();
        campo.alternarBandeira();
        assertFalse(campo.hasBandeira());
    }
    @Test
    void AbrirCampoNormal(){
        assertTrue(campo.abrir());
    }
    @Test
    void AbrirCampoBandeira(){
        campo.alternarBandeira();
        assertFalse(campo.abrir());
    }
    @Test
    void AbrirCampoMinado(){
        campo.AdicionarMina();
        assertThrows(ExplosaoException.class, () ->{
            campo.abrir();
        });

    }
    @Test
    void AbrirCampoMinadoComBandeira(){
        campo.AdicionarMina();
        campo.alternarBandeira();
        assertFalse(campo.abrir());
    }


}
