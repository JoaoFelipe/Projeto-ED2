package tests;

import dm.models.Value;
import dm.models.HashFile;
import dm.models.Tuple;
import dm.models.EntityRelationship;
import dm.models.Entity;
import dm.models.ERList;
import dm.models.ConsistencyStrategy;
import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

public class TestRemocao {

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
    public void removerElemento0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void removerElemento1NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 1)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void removerElemento2NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(2, "Clara", 20)),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 2)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, 2),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void removerElemento3NaPosicao3() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(3, "Daniela", 20))
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 3)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void removerElemento0NaPosicao1ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void removerElemento0NaPosicao1ComRegistroRemovido() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, 2),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void falhaAoRemoverRegistroInexistente() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(false, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }


    @Test
    public void removerDependente() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "A", 5, 0)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));

        HashFile arquivo = new HashFile(e2);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e2.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2, 2),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }

    @Test
    public void restringirDeletar() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "A", 5, 0)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        arquivo.open(prefix);
        assertEquals(false, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "A", 5, 0)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }
    
    @Test
    public void cascataDeletar() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "A", 5, 0)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.setStrategy(ConsistencyStrategy.CASCADE);
        arquivo.open(prefix);
        assertEquals(true, arquivo.remove(new Value(e1.getPk(), 0)));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2, 2),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }


}
