/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.models.atributos.DoubleAttr;
import gd.models.atributos.CharAttr;
import gd.models.atributos.IntAttr;
import gd.models.atributos.Atributo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class TestAtributos {

    public TestAtributos() {
    }

    @Test
    public void criarAtributoInt() {
        Atributo atributo = Atributo.criarAtributo("a:int");
        Atributo resultado = new IntAttr("a", false);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar10() {
        Atributo atributo = Atributo.criarAtributo("a:char10");
        Atributo resultado = new CharAttr("a", false, 10);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar50() {
        Atributo atributo = Atributo.criarAtributo("a:char50");
        Atributo resultado = new CharAttr("a", false, 50);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar1SemEspecificarTamanho() {
        Atributo atributo = Atributo.criarAtributo("a:char");
        Atributo resultado = new CharAttr("a", false, 1);
        assertEquals(resultado, atributo);
    }


    @Test
    public void criarAtributoDouble() {
        Atributo atributo = Atributo.criarAtributo("a:double");
        Atributo resultado = new DoubleAttr("a", false);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoIntPK() {
        Atributo atributo = Atributo.criarAtributo("*b:int");
        Atributo resultado = new IntAttr("b", true);
        assertEquals(resultado, atributo);
    }

    @Test
    public void pegarNomeDoAtributo() {
        Atributo atributo = Atributo.criarAtributo("*c:int");
        assertEquals("c", atributo.getNome());
    }

    @Test
    public void pegarTipoDoAtributo() {
        Atributo atributo = Atributo.criarAtributo("*c:int");
        assertEquals("int", atributo.getTipo());
    }

    @Test
    public void pegarPKDoAtributo() {
        Atributo atributo = Atributo.criarAtributo("*c:int");
        assertEquals(true, atributo.getPK());
        atributo = Atributo.criarAtributo("d:int");
        assertEquals(false, atributo.getPK());
    }

    @Test
    public void pegarReprDoAtributo() {
        Atributo atributo = Atributo.criarAtributo("*c:int");
        assertEquals("*c:int", atributo.getRepr());
        atributo = new IntAttr("d", false);
        assertEquals("d:int", atributo.getRepr());
    }

    @Test
    public void pegar4comoTamanhoDoInt() {
        Atributo atributo = Atributo.criarAtributo("*c:int");
        assertEquals(4, atributo.getTamanho());
    }

    @Test
    public void pegarTamanhoDoChar10() {
        Atributo atributo = Atributo.criarAtributo("s:char10");
        assertEquals(10, atributo.getTamanho());
    }

    @Test
    public void pegar1ComoTamanhoDoCharSemDefinirTamanho() {
        Atributo atributo = Atributo.criarAtributo("s:char");
        assertEquals(1, atributo.getTamanho());
    }

    @Test
    public void pegar8comoTamanhoDoDouble() {
        Atributo atributo = Atributo.criarAtributo("d:double");
        assertEquals(8, atributo.getTamanho());
    }

    @Test
    public void listaOpcoesAtributos() {
        //Testando int, double e char30
        int total = 0;
        for (String string : Atributo.todasOpcoes()) {
            if (string.equals("int"))
                total++;
            if (string.equals("double"))
                total++;
            if (string.equals("char30"))
                total++;
        }
        assertEquals(3, total);

    }

}