import gd.models.arquivo.HashFile;
import gd.models.arquivo.Tuple;
import gd.models.ER.EntityRelationship;
import gd.models.ER.Entity;
import gd.models.ER.ERList;
import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

public class TestInsercao {

    String masterE1 = "test\\EmpregadoMaster.dat";
    String masterE2 = "test\\DependenteMaster.dat";
    String arqE1 = "test\\Dependente.dat";
    String arqE2 = "test\\Empregado.dat";
    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    Entity e1 = null;
    Entity e2 = null;

    public void deletarArquivos() {
        for (String string : Arrays.asList(metaDadosPath, arqE1, arqE2, masterE1, masterE2)) {
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
        
        EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void inserirElemento0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento4NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(4, "Eliana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento1NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(1, "Bia", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento5NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(5, "Fabiana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento6NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(6, "Giovana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20)),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento4NaPosicao1ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(4, "Eliana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void expandirArquivoQuandoPassarDeDoisTercos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(2, "Carol", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void inserirElemento4NaPosicao4PoisTamanhoMudou() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(4, "D", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento8NaPosicao3Com2Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(8, "8", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "8", 20)),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento16NaPosicao6Com3Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "8", 20)),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(16, "16", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "8", 20)),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(16, "16", 20)),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void inserirElemento7NaPosicao0Com1Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(3, "Daniela", 20))
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(7, "Hanna", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(7, "Hanna", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(3, "Daniela", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElemento6NaPosicao3Com1Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(6, "Giovana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

     @Test
    public void falharAoTentarInserirElementoComCodigoRepetido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(false, arquivo.insert(new Tuple(e1, Arrays.asList(2, "Carolina", 20))));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void falharAoTentarInserirElementoComCodigoRepetidoComConflito() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20))
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(false, arquivo.insert(new Tuple(e1, Arrays.asList(6, "Gisele", 20))));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1, Arrays.asList(6, "Giovana", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void duasExpansoes() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "8", 20)),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(16, "16", 20)),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(17, "17", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1, Arrays.asList(17, "17", 20)),
            new Tuple(e1, Arrays.asList(16, "16", 20)),
            new Tuple(e1, Arrays.asList(4, "D", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(8, "8", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void naoInserirQuandoNaoExistirReferenciado() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));

        HashFile arquivo = new HashFile(e2);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e2, Arrays.asList(6, "Giovana", 5, 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2),
//            new Registro(e1, Arrays.asList(6, "Giovana", 5)),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }

    @Test
    public void inserirQuandoExistirReferenciado() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));

        HashFile arquivo = new HashFile(e2);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e2, Arrays.asList(6, "Giovana", 5, 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2, Arrays.asList(6, "Giovana", 5, 0)),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }

    @Test
    public void inserirElementoEmPosicaoRemovida() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElementoEmPosicaoRemovida0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(4, "Ana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void inserirElementoEmArquivoSoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, 2),
            new Tuple(e1, 2),
            new Tuple(e1, 2)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.insert(new Tuple(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, 2),
            new Tuple(e1, 2),
            new Tuple(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void naoInserirRegistroRepetidoEmArquivoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, 2),
            new Tuple(e1, 2)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        assertEquals(false, arquivo.insert(new Tuple(e1, Arrays.asList(0, "Ana", 20))));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, 2),
            new Tuple(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


}