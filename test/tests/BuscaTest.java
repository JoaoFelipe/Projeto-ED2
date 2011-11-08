package tests;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import dm.models.Value;
import dm.models.HashFile;
import dm.models.Result;
import dm.models.Entity;
import dm.models.Tuple;
import dm.models.ERList;
import dm.models.EntityRelationship;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

public class BuscaTest {

    String metaDadosPath = "test\\metadados-teste.dat";
    String arqE1 = "test\\Dependente.dat";
    String arqE2 = "test\\Empregado.dat";
    String prefix = "test\\";
    Entity e1 = null;
    Entity e2 = null;

    public void deletarArquivos() {
        for (String string : Arrays.asList(metaDadosPath, arqE1, arqE2)) {
            File file = new File(string);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        deletarArquivos();
        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entity) EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = (Entity) EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ERList.getInstance().add(e1);
        ERList.getInstance().add(e2);

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void buscarExistenteNaPosicao0Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 15)),
            new Tuple(e1, Arrays.asList(1, "Bia", 15)),
            new Tuple(e1, Arrays.asList(2, "Carol", 15)),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(0, true),arquivo.find(new Value(e1.getPk(), 0)));
        arquivo.close();
    }

    @Test
    public void buscarExistenteNaPosicao1Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Bia", 15)),
            new Tuple(e1, Arrays.asList(2, "Carol", 15)),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(1, true),arquivo.find(new Value(e1.getPk(), 1)));
        arquivo.close();
    }

    @Test
    public void buscarExistenteNaPosicao2Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Bia", 15)),
            new Tuple(e1, Arrays.asList(2, "Carol", 15)),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(2, true),arquivo.find(new Value(e1.getPk(), 2)));
        arquivo.close();
    }

    @Test
    public void buscarExistenteNaPosicao3Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(3, "Daniela", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(3, true),arquivo.find(new Value(e1.getPk(), 3)));
        arquivo.close();
    }

    @Test
    public void buscarExistente4NaPosicao0Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 15)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(0, true),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarExistente5NaPosicao1Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(1, true),arquivo.find(new Value(e1.getPk(), 5)));
        arquivo.close();
    }

    @Test
    public void buscarExistente6NaPosicao2Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20)),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(2, true),arquivo.find(new Value(e1.getPk(), 6)));
        arquivo.close();
    }

    @Test
    public void buscarExistente7NaPosicao3Certa() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20)),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(3, true),arquivo.find(new Value(e1.getPk(), 7)));
        arquivo.close();
    }


    @Test
    public void buscarExistente4ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 15)),
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(1, true),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarExistente8Com2Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 15)),
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "Iva", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(3, true),arquivo.find(new Value(e1.getPk(), 8)));
        arquivo.close();
    }

    @Test
    public void buscarExistente9ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 15)),
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1, Arrays.asList(9, "Joana", 20)),
            new Tuple(e1, Arrays.asList(8, "Iva", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(2, true),arquivo.find(new Value(e1.getPk(), 9)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(0, false),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(2, false),arquivo.find(new Value(e1.getPk(), 2)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0NaPosicao3() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(3, false),arquivo.find(new Value(e1.getPk(), 7)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0ComConflitoPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliane", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(6, "Gabriela", 20)),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);

        arquivo.open(prefix);
        assertEquals(new Result(1, false),arquivo.find(new Value(e1.getPk(), 0)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0Com2ConflitosPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliane", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(3, false),arquivo.find(new Value(e1.getPk(), 0)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente0Com3ConflitosPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliane", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1, Arrays.asList(6, "Gabriela", 20)),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(-1, false),arquivo.find(new Value(e1.getPk(), 0)));
        arquivo.close();
    }

    @Test
    public void buscarExistente4ComConflitoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(1, true),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente4ComConflitoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(0, false),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarNaoExistente4Com2ConflitosRemovidos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(7, "Hanna", 20))
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(0, false),arquivo.find(new Value(e1.getPk(), 4)));
        arquivo.close();
    }

    @Test
    public void buscarExistenteComRemovidoNoCaminho() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, 2),
            new Tuple(e1)
        ));
        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(new Result(1, true),arquivo.find(new Value(e1.getPk(), 0)));
        arquivo.close();
    }

}