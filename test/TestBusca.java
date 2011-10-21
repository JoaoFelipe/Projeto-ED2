

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.models.arquivo.Valor;
import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Resultado;
import gd.models.ER.Entidade;
import gd.models.arquivo.Registro;
import gd.models.ER.ListaER;
import gd.models.ER.EntidadeRelacionamento;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.File;
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
public class TestBusca {

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
    public void buscarExistenteNaPosicao0Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 15)),
            new Registro(e1, Arrays.asList(1, "Bia", 15)),
            new Registro(e1, Arrays.asList(2, "Carol", 15)),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(0, true),arquivo.busca(new Valor(e1.getPk(), 0)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistenteNaPosicao1Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(1, "Bia", 15)),
            new Registro(e1, Arrays.asList(2, "Carol", 15)),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, true),arquivo.busca(new Valor(e1.getPk(), 1)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistenteNaPosicao2Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(1, "Bia", 15)),
            new Registro(e1, Arrays.asList(2, "Carol", 15)),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(2, true),arquivo.busca(new Valor(e1.getPk(), 2)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistenteNaPosicao3Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(3, "Daniela", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(3, true),arquivo.busca(new Valor(e1.getPk(), 3)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente4NaPosicao0Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliana", 15)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(0, true),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente5NaPosicao1Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, true),arquivo.busca(new Valor(e1.getPk(), 5)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente6NaPosicao2Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(6, "Giovana", 20)),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(2, true),arquivo.busca(new Valor(e1.getPk(), 6)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente7NaPosicao3Certa() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(6, "Giovana", 20)),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(3, true),arquivo.busca(new Valor(e1.getPk(), 7)));
        arquivo.fechar();
    }


    @Test
    public void buscarExistente4ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 15)),
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, true),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente8Com2Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 15)),
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "Iva", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(3, true),arquivo.busca(new Valor(e1.getPk(), 8)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente9ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 15)),
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1, Arrays.asList(9, "Joana", 20)),
            new Registro(e1, Arrays.asList(8, "Iva", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(2, true),arquivo.busca(new Valor(e1.getPk(), 9)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(0, false),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(2, false),arquivo.busca(new Valor(e1.getPk(), 2)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0NaPosicao3() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(3, false),arquivo.busca(new Valor(e1.getPk(), 7)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0ComConflitoPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliane", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(6, "Gabriela", 20)),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);

        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, false),arquivo.busca(new Valor(e1.getPk(), 0)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0Com2ConflitosPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliane", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(3, false),arquivo.busca(new Valor(e1.getPk(), 0)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente0Com3ConflitosPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliane", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1, Arrays.asList(6, "Gabriela", 20)),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(-1, false),arquivo.busca(new Valor(e1.getPk(), 0)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistente4ComConflitoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, true),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente4ComConflitoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(0, false),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarNaoExistente4Com2ConflitosRemovidos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1, Arrays.asList(7, "Hanna", 20))
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(0, false),arquivo.busca(new Valor(e1.getPk(), 4)));
        arquivo.fechar();
    }

    @Test
    public void buscarExistenteComRemovidoNoCaminho() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, 2),
            new Registro(e1)
        ));
        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(new Resultado(1, true),arquivo.busca(new Valor(e1.getPk(), 0)));
        arquivo.fechar();
    }

}