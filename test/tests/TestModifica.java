package tests;

import java.util.List;
import java.util.ArrayList;
import dm.models.file.Value;
import dm.models.file.HashFile;
import dm.models.file.Tuple;
import dm.models.ER.EntityRelationship;
import dm.models.ER.Entity;
import dm.models.ER.ERList;
import dm.models.file.ConsistencyStrategy;
import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

public class TestModifica {

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
    public void modificarElemento0NaPosicao0TrocaNome() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("nome"), "Bia"));
        assertEquals(true, arquivo.modify(new Value(e1.getPk(), 0), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void modificarElementoTrocando2Atributos() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("nome"), "Ana"));
        mudancas.add(new Value(e1.findAttribute("idade"), 30));
        assertEquals(true, arquivo.modify(new Value(e1.getPk(), 1), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1, Arrays.asList(1, "Ana", 30)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void modificarElemento0NaPosicao1ComConflitoTrocarNome() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("nome"), "Livia Lutterback Dias"));
        assertEquals(true, arquivo.modify(new Value(e1.getPk(), 0), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(4, "Eliana", 20)),
            new Tuple(e1, Arrays.asList(0, "Livia Lutterback Dias", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
    }

    @Test
    public void falhaAoModificarRegistroInexistente() throws Exception {
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        HashFile arquivo = new HashFile(e1);
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.RESTRICT);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("nome"), "Lívia"));
        assertEquals(false, arquivo.modify(new Value(e1.getPk(), 0), mudancas));
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
    public void modificarDependenteTrocaNome() throws Exception {
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
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.CASCADE);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e2.findAttribute("nome"), "B"));
        assertEquals(true, arquivo.modify(new Value(e2.getPk(), 0), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "B", 5, 0)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }

    @Test
    public void modificarMestreEmCascataTrocaNome() throws Exception {
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
        arquivo.open(prefix);
        arquivo.setStrategy(ConsistencyStrategy.CASCADE);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("nome"), "Bia"));
        assertEquals(true, arquivo.modify(new Value(e2.getPk(), 5), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(5, "Bia", 20)),
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
    public void modificarDependenteTrocaCod() throws Exception {
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
        arquivo.open(prefix);
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("cod"), "1"));
        arquivo.setStrategy(ConsistencyStrategy.CASCADE);
        assertEquals(true, arquivo.modify(new Value(e1.getPk(), 0), mudancas));
        arquivo.close();

        gerarArquivo(masterE1, Arrays.asList(
            new Tuple(e1, 2),
            new Tuple(e1, Arrays.asList(5, "Fabiana", 20)),
            new Tuple(e1, Arrays.asList(1, "Ana", 20)),
            new Tuple(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "A", 5, 1)),
            new Tuple(e2),
            new Tuple(e2),
            new Tuple(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getName()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getName()+".dat"));
    }
    
    @Test
    public void restringirModificarDependenteCod() throws Exception {
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
        List<Value> mudancas = new ArrayList<Value>();
        mudancas.add(new Value(e1.findAttribute("cod"), "1"));
        assertEquals(false, arquivo.modify(new Value(e1.getPk(), 0), mudancas));
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

   
}
