package tests;

import java.util.ArrayList;
import dm.models.Search;
import dm.models.HashFile;
import dm.models.Tuple;
import dm.models.Value;
import dm.models.Attribute;
import dm.models.EntityRelationship;
import java.util.Arrays;
import dm.models.ERList;
import java.io.File;
import dm.models.Entity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;


public class ConsultaRelacionamentosTest {

    String metaDadosPath = "test\\metadados-teste.dat";
    String arqE1 = "test\\Empregado.dat";
    String arqE2 = "test\\Dependente.dat";
    String prefix = "test\\";
    Entity e1 = null;
    Entity e2 = null;
    Attribute cod;

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

        EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

        
        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 15)),
            new Tuple(e1, Arrays.asList(2, "Clara", 30)),
            new Tuple(e1, Arrays.asList(3, "Dani", 20))
        ));
        
        gerarArquivo(prefix+e2.getName()+".dat", Arrays.asList(
            new Tuple(e2, Arrays.asList(0, "Eliana", 20, 0)),
            new Tuple(e2, Arrays.asList(1, "Fabiana", 15, 0)),
            new Tuple(e2, Arrays.asList(2, "Giovanna", 30, 1)),
            new Tuple(e2, Arrays.asList(3, "Hanna", 20, 2))
        ));
        
        cod = e1.getPk();

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void TestConsultaIgualdade() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(new Value(cod, 0)).toArray();
        Object[] codigos = consulta.search("#Dependente", "=", 2).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaIgualdadeTuplasDiferentes() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
            new Value(cod, 1),
            new Value(cod, 2)
        ).toArray();
        Object[] codigos = consulta.search("#Dependente", "=", 1).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaIgualdadeSemDependentes() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
            new Value(cod, 3)
        ).toArray();
        Object[] codigos = consulta.search("#Dependente", "=", 0).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaIgualdadeNumeroNaoEncontrado() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList().toArray();
        Object[] codigos = consulta.search("#Dependente", "=", 3).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaMairQue0() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
            new Value(cod, 0),
            new Value(cod, 1),
            new Value(cod, 2)
        ).toArray();
        Object[] codigos = consulta.search("#Dependente", ">", 0).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaMenorQue2() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
            new Value(cod, 1),
            new Value(cod, 2),
            new Value(cod, 3)
        ).toArray();
        Object[] codigos = consulta.search("#Dependente", "<", 2).compile(prefix).getPKs().toArray();
        assertArrayEquals(esperado, codigos);
    }


}