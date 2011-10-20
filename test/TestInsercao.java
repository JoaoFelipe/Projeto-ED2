

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import java.io.File;
import java.util.Arrays;
import utils.Cliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

/**
 *
 * @author Joao
 */
public class TestInsercao {

    String masterE1 = "test\\EmpergadoMaster.dat";
    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    Entidade e1 = null;
    Entidade e2 = null;

    @Before
    public void setUp() throws Exception {
        File file = new File(metaDadosPath);
        if (file.exists()) {
            file.delete();
        }
        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ListaER.getInstancia().add(e1);
        ListaER.getInstancia().add(e2);

    }

    @Test
    public void inserirElemento0NaPosicao0() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(0, "Ana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(0, "Ana", 20),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento4NaPosicao0() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(4, "Eliana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(4, "Eliana", 20),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento1NaPosicao1() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(1, "Beatriz", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(),
            new Cliente(1, "Beatriz", 20),
            new Cliente(),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento5NaPosicao1() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(5, "Fabiana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(),
            new Cliente(5, "Fabiana", 20),
            new Cliente(),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento3NaPosicao3() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(4, "Eliana", 20),
            new Cliente(5, "Fabiana", 20),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(3, "Carol", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(4, "Eliana", 20),
            new Cliente(5, "Fabiana", 20),
            new Cliente(),
            new Cliente(3, "Carol", 20)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento4NaPosicao1Com1ConflitoNaPosicao0() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(0, "Ana", 20),
            new Cliente(),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(4, "Eliana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(0, "Ana", 20, 1),
            new Cliente(4, "Eliana", 20),
            new Cliente(),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento4NaPosicao2Com1ConflitoNaPosicao0EElementoNaProxima() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(0, "Ana", 20),
            new Cliente(1, "Beatriz", 20),
            new Cliente(),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(4, "Eliana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(0, "Ana", 20, 2),
            new Cliente(1, "Beatriz", 20),
            new Cliente(4, "Eliana", 20),
            new Cliente()
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento8NaPosicao3Com2Conflitos() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(0, "Ana", 20, 2),
            new Cliente(1, "Beatriz", 20),
            new Cliente(4, "Eliana", 20),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(8, "Iva", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(0, "Ana", 20, 2),
            new Cliente(1, "Beatriz", 20),
            new Cliente(4, "Eliana", 20, 3),
            new Cliente(8, "Iva", 20)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento12NaPosicao3Com3Conflitos() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(0, "Ana", 20, 1),
            new Cliente(8, "Iva", 20, 2),
            new Cliente(4, "Eliana", 20),
            new Cliente()
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(12, "Mariana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(0, "Ana", 20, 1),
            new Cliente(8, "Iva", 20, 2),
            new Cliente(4, "Eliana", 20,3),
            new Cliente(12, "Mariana", 20)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento7NaPosicao0Com1Conflitos() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(),
            new Cliente(),
            new Cliente(),
            new Cliente(3, "Daniela", 20)
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(7, "Hanna", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElemento6NaPosicao2Com1Conflitos() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));

        Questao11.insere(nomeArquivo, 4, new Cliente(6, "Giovana", 20));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(6, "Giovana", 20),
            new Cliente(2, "Carol", 20, 1),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void falharAoTentarInserirElementoComCodigoRepetido() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));

        assertEquals(false, Questao11.insere(nomeArquivo, 4, new Cliente(2, "Carolina", 20)));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void falharAoTentarInserirElementoComCodigoRepetidoComConflito() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));

        assertEquals(false, Questao11.insere(nomeArquivo, 4, new Cliente(7, "Hanna", 20)));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void falharAoTentarInserirElementoEmArquivoCheio() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(1, "Beatriz", 20),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));

        assertEquals(false, Questao11.insere(nomeArquivo, 4, new Cliente(0, "Ana", 20)));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(1, "Beatriz", 20),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }

    @Test
    public void inserirElementoEmPosicaoErradaQuandoEstiverOcupada() throws Exception {
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Cliente(7, "Hanna", 20),
            new Cliente(),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));

        assertEquals(true, Questao11.insere(nomeArquivo, 4, new Cliente(0, "Ana", 20)));

        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Cliente(7, "Hanna", 20, 1),
            new Cliente(0, "Ana", 20),
            new Cliente(2, "Carol", 20),
            new Cliente(3, "Daniela", 20, 0)
        ));
        assertArrayEquals(lerArquivo(nomeArquivoMaster), lerArquivo(nomeArquivo));
    }



}