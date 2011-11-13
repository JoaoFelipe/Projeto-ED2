package tests;

import dm.models.DoubleAttr;
import dm.models.CharAttr;
import dm.models.IntAttr;
import dm.models.Attribute;
import org.junit.Test;
import static org.junit.Assert.*;

public class AtributosTest {

    @Test
    public void criarAtributoInt() {
        Attribute atributo = Attribute.createAttribute("a:int");
        Attribute resultado = new IntAttr("a", false);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar10() {
        Attribute atributo = Attribute.createAttribute("a:char10");
        Attribute resultado = new CharAttr("a", false, 10);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar50() {
        Attribute atributo = Attribute.createAttribute("a:char50");
        Attribute resultado = new CharAttr("a", false, 50);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoChar1SemEspecificarTamanho() {
        Attribute atributo = Attribute.createAttribute("a:char");
        Attribute resultado = new CharAttr("a", false, 1);
        assertEquals(resultado, atributo);
    }


    @Test
    public void criarAtributoDouble() {
        Attribute atributo = Attribute.createAttribute("a:double");
        Attribute resultado = new DoubleAttr("a", false);
        assertEquals(resultado, atributo);
    }

    @Test
    public void criarAtributoIntPK() {
        Attribute atributo = Attribute.createAttribute("*b:int");
        Attribute resultado = new IntAttr("b", true);
        assertEquals(resultado, atributo);
    }

    @Test
    public void pegarNomeDoAtributo() {
        Attribute atributo = Attribute.createAttribute("*c:int");
        assertEquals("c", atributo.getName());
    }

    @Test
    public void pegarTipoDoAtributo() {
        Attribute atributo = Attribute.createAttribute("*c:int");
        assertEquals("int", atributo.getType());
    }

    @Test
    public void pegarPKDoAtributo() {
        Attribute atributo = Attribute.createAttribute("*c:int");
        assertEquals(true, atributo.isPK());
        atributo = Attribute.createAttribute("d:int");
        assertEquals(false, atributo.isPK());
    }

    @Test
    public void pegarReprDoAtributo() {
        Attribute atributo = Attribute.createAttribute("*c:int");
        assertEquals("*c:int", atributo.getRepr());
        atributo = new IntAttr("d", false);
        assertEquals("d:int", atributo.getRepr());
    }

    @Test
    public void pegar4comoTamanhoDoInt() {
        Attribute atributo = Attribute.createAttribute("*c:int");
        assertEquals(4, atributo.getSize());
    }

    @Test
    public void pegarTamanhoDoChar10como12() {
        Attribute atributo = Attribute.createAttribute("s:char10");
        assertEquals(12, atributo.getSize());
    }

    @Test
    public void pegar3ComoTamanhoDoChar1SemDefinirTamanho() {
        Attribute atributo = Attribute.createAttribute("s:char");
        assertEquals(3, atributo.getSize());
    }

    @Test
    public void pegar8comoTamanhoDoDouble() {
        Attribute atributo = Attribute.createAttribute("d:double");
        assertEquals(8, atributo.getSize());
    }

    @Test
    public void listaOpcoesAtributos() {
        //Testando int, double e char30
        int total = 0;
        for (String string : Attribute.allOptions()) {
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