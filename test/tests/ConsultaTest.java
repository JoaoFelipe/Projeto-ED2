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


public class ConsultaTest {

    String metaDadosPath = "test\\metadados-teste.dat";
    String arqE2 = "test\\Empregado.dat";
    String prefix = "test\\";
    Entity e1 = null;
    Attribute cod;
    Attribute nome;
    Attribute idade;
    Attribute salario;

    public void deletarArquivos() {
        for (String string : Arrays.asList(metaDadosPath, arqE2)) {
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

        e1 = (Entity) EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "salario:double"));

        ERList.getInstance().add(e1);

        gerarArquivo(prefix+e1.getName()+".dat", Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20, 1500.0)),
            new Tuple(e1, Arrays.asList(1, "Bia", 15, 1000.50)),
            new Tuple(e1, Arrays.asList(2, "Clara", 30, 1500.0)),
            new Tuple(e1, Arrays.asList(3, "Dani", 20, 5500.0))
        ));

        cod = e1.getPk();
        nome = e1.findAttribute("nome");
        idade = e1.findAttribute("idade");
        salario = e1.findAttribute("salario");
    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void TestComparaInt() {
        Attribute atributo = e1.findAttribute("cod");
        assertEquals(true, atributo.compare("=", new Value(atributo, 1), 1));
        assertEquals(false, atributo.compare("=", new Value(atributo, 1), 2));

        assertEquals(true, atributo.compare("!=", new Value(atributo, 1), 2));
        assertEquals(false, atributo.compare("!=", new Value(atributo, 1), 1));

        assertEquals(true, atributo.compare(">", new Value(atributo, 1), 0));
        assertEquals(false, atributo.compare(">", new Value(atributo, 1), 2));

        assertEquals(true, atributo.compare(">=", new Value(atributo, 1), 0));
        assertEquals(true, atributo.compare(">=", new Value(atributo, 1), 1));
        assertEquals(false, atributo.compare(">=", new Value(atributo, 1), 2));

        assertEquals(true, atributo.compare("<", new Value(atributo, 2), 3));
        assertEquals(false, atributo.compare("<", new Value(atributo, 2), 1));

        assertEquals(true, atributo.compare("<=", new Value(atributo, 2), 3));
        assertEquals(true, atributo.compare("<=", new Value(atributo, 2), 2));
        assertEquals(false, atributo.compare("<=", new Value(atributo, 2), 1));
    }

    @Test
    public void TestComparaDouble() {
        Attribute atributo = e1.findAttribute("salario");
        assertEquals(true, atributo.compare("=", new Value(atributo, 1.0), 1.0));
        assertEquals(false, atributo.compare("=", new Value(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compare("!=", new Value(atributo, 1.0), 2.0));
        assertEquals(false, atributo.compare("!=", new Value(atributo, 1.0), 1.0));

        assertEquals(true, atributo.compare(">", new Value(atributo, 1.0), 0.0));
        assertEquals(false, atributo.compare(">", new Value(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compare(">=", new Value(atributo, 1.0), 0.0));
        assertEquals(true, atributo.compare(">=", new Value(atributo, 1.0), 1.0));
        assertEquals(false, atributo.compare(">=", new Value(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compare("<", new Value(atributo, 2.0), 3.0));
        assertEquals(false, atributo.compare("<", new Value(atributo, 2.0), 1.0));

        assertEquals(true, atributo.compare("<=", new Value(atributo, 2.0), 3.0));
        assertEquals(true, atributo.compare("<=", new Value(atributo, 2.0), 2.0));
        assertEquals(false, atributo.compare("<=", new Value(atributo, 2.0), 1.0));
    }

    @Test
    public void TestComparaChar() {
        Attribute atributo = e1.findAttribute("nome");
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "teste"), "teste"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "teste"), "%est%"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "teste"), "t%e"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "teste"), "te_te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te%ste"), "te\\%ste"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te\\ste"), "te\\\\ste"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te\\\\ste"), "te\\\\\\\\ste")); 
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te+ste"), "te+ste")); 
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te(s)te"), "te(s)te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes.te"), "tes.te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te[s]te"), "te[s]te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "te{s}te"), "te{s}te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes,te"), "tes,te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes|te"), "tes|te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes^te"), "tes^te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes&te"), "tes&te"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "teste?"), "teste?"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "tes//ate"), "tes//ate"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "*teste"), "*teste"));
        assertEquals(true, atributo.compare("LIKE", new Value(atributo, "*test_"), "*test\\_"));
        assertEquals(false, atributo.compare("LIKE", new Value(atributo, "teste"), "testes"));
        assertEquals(false, atributo.compare("LIKE", new Value(atributo, "teste"), "test"));
        assertEquals(false, atributo.compare("LIKE", new Value(atributo, "teste"), "test\\%"));
        assertEquals(false, atributo.compare("LIKE", new Value(atributo, "teste"), "test\\e"));
        assertEquals(false, atributo.compare("LIKE", new Value(atributo, "teste"), "test\\_"));

        assertEquals(true, atributo.compare("=", new Value(atributo, "teste"), "teste"));
        assertEquals(false, atributo.compare("=", new Value(atributo, "teste"), "testes"));

        assertEquals(true, atributo.compare("!=", new Value(atributo, "teste"), "testes"));
        assertEquals(false, atributo.compare("!=", new Value(atributo, "teste"), "teste"));

        assertEquals(true, atributo.compare(">", new Value(atributo, "b"), "a"));
        assertEquals(false, atributo.compare(">", new Value(atributo, "b"), "c"));

        assertEquals(true, atributo.compare(">=", new Value(atributo, "b"), "a"));
        assertEquals(true, atributo.compare(">=", new Value(atributo, "b"), "b"));
        assertEquals(false, atributo.compare(">=", new Value(atributo, "b"), "c"));

        assertEquals(true, atributo.compare("<", new Value(atributo, "b"), "c"));
        assertEquals(false, atributo.compare("<", new Value(atributo, "b"), "a"));

        assertEquals(true, atributo.compare("<=", new Value(atributo, "b"), "c"));
        assertEquals(true, atributo.compare("<=", new Value(atributo, "b"), "b"));
        assertEquals(false, atributo.compare("<=", new Value(atributo, "b"), "a"));
    }

    @Test
    public void TestConsultaUmaCondicao() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(new Value(cod, 0)).toArray();
        Object[] codigos = consulta.search(nome, "=", "Ana").compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultaDuasCondicoes() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Value(cod, 0),
                new Value(cod, 2)
        ).toArray();
        Object[] codigos = consulta.
                search(salario, ">", 1000.50).
                search(salario, "<", 3000.0).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultaDuasCondicoesEmCamposDiferente() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Value(cod, 0)
        ).toArray();
        Object[] codigos = consulta.
                search(idade, ">=", 20).
                search(cod, "<", 2).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultasConsecutivas() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Value(cod, 0),
                new Value(cod, 1),
                new Value(cod, 2)
        ).toArray();
        Object[] codigos = consulta.
                search(salario, ">", 1000.0).
                search(salario, "<", 3000.0).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);

        esperado = Arrays.asList(
                new Value(cod, 0),
                new Value(cod, 2)
        ).toArray();
        codigos = consulta.
                search(salario, ">", 1000.50).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);
    }
    
    @Test
    public void TestConsultaIgualdadeCod() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Value(cod, 0)
        ).toArray();
        Object[] codigos = consulta.
                search(cod, "=", 0).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);

    }
    
    @Test
    public void TestConsultaIgualdade2Cods() throws Exception {
        HashFile arquivo = new HashFile(e1);
        Search consulta = new Search(arquivo, null);

        Object[] esperado = new Object[0];
        Object[] codigos = consulta.
                search(cod, "=", 0).
                search(cod, "=", 1).
                compile(prefix).getPKs().toArray();

        assertArrayEquals(esperado, codigos);

    }

}