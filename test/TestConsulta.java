import gd.models.arquivo.Consulta;
import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Registro;
import gd.models.arquivo.Valor;
import gd.models.atributos.Atributo;
import gd.models.ER.EntidadeRelacionamento;
import java.util.Arrays;
import gd.models.ER.ListaER;
import java.io.File;
import gd.models.ER.Entidade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;


public class TestConsulta {

    String metaDadosPath = "test\\metadados-teste.dat";
    String arqE2 = "test\\Empregado.dat";
    String prefix = "test\\";
    Entidade e1 = null;
    Atributo cod;
    Atributo nome;
    Atributo idade;
    Atributo salario;

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
        
        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "salario:double"));

        ListaER.getInstancia().add(e1);

        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20, 1500.0)),
            new Registro(e1, Arrays.asList(1, "Bia", 15, 1000.50)),
            new Registro(e1, Arrays.asList(2, "Clara", 30, 1500.0)),
            new Registro(e1, Arrays.asList(3, "Dani", 20, 5500.0))
        ));

        cod = e1.getPk();
        nome = e1.buscarAtributo("nome");
        idade = e1.buscarAtributo("idade");
        salario = e1.buscarAtributo("salario");
    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void TestComparaInt() {
        Atributo atributo = e1.buscarAtributo("cod");
        assertEquals(true, atributo.compara("=", new Valor(atributo, 1), 1));
        assertEquals(false, atributo.compara("=", new Valor(atributo, 1), 2));

        assertEquals(true, atributo.compara("!=", new Valor(atributo, 1), 2));
        assertEquals(false, atributo.compara("!=", new Valor(atributo, 1), 1));

        assertEquals(true, atributo.compara(">", new Valor(atributo, 1), 0));
        assertEquals(false, atributo.compara(">", new Valor(atributo, 1), 2));

        assertEquals(true, atributo.compara(">=", new Valor(atributo, 1), 0));
        assertEquals(true, atributo.compara(">=", new Valor(atributo, 1), 1));
        assertEquals(false, atributo.compara(">=", new Valor(atributo, 1), 2));

        assertEquals(true, atributo.compara("<", new Valor(atributo, 2), 3));
        assertEquals(false, atributo.compara("<", new Valor(atributo, 2), 1));

        assertEquals(true, atributo.compara("<=", new Valor(atributo, 2), 3));
        assertEquals(true, atributo.compara("<=", new Valor(atributo, 2), 2));
        assertEquals(false, atributo.compara("<=", new Valor(atributo, 2), 1));
    }

    @Test
    public void TestComparaDouble() {
        Atributo atributo = e1.buscarAtributo("salario");
        assertEquals(true, atributo.compara("=", new Valor(atributo, 1.0), 1.0));
        assertEquals(false, atributo.compara("=", new Valor(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compara("!=", new Valor(atributo, 1.0), 2.0));
        assertEquals(false, atributo.compara("!=", new Valor(atributo, 1.0), 1.0));

        assertEquals(true, atributo.compara(">", new Valor(atributo, 1.0), 0.0));
        assertEquals(false, atributo.compara(">", new Valor(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compara(">=", new Valor(atributo, 1.0), 0.0));
        assertEquals(true, atributo.compara(">=", new Valor(atributo, 1.0), 1.0));
        assertEquals(false, atributo.compara(">=", new Valor(atributo, 1.0), 2.0));

        assertEquals(true, atributo.compara("<", new Valor(atributo, 2.0), 3.0));
        assertEquals(false, atributo.compara("<", new Valor(atributo, 2.0), 1.0));

        assertEquals(true, atributo.compara("<=", new Valor(atributo, 2.0), 3.0));
        assertEquals(true, atributo.compara("<=", new Valor(atributo, 2.0), 2.0));
        assertEquals(false, atributo.compara("<=", new Valor(atributo, 2.0), 1.0));
    }

    @Test
    public void TestComparaChar() {
        Atributo atributo = e1.buscarAtributo("nome");
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "teste"), "teste"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "teste"), "%est%"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "teste"), "t%e"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "teste"), "te_te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te%ste"), "te\\%ste"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te\\ste"), "te\\\\ste"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te\\\\ste"), "te\\\\\\\\ste")); 
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te+ste"), "te+ste")); 
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te(s)te"), "te(s)te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes.te"), "tes.te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te[s]te"), "te[s]te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "te{s}te"), "te{s}te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes,te"), "tes,te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes|te"), "tes|te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes^te"), "tes^te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes&te"), "tes&te"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "teste?"), "teste?"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "tes//ate"), "tes//ate"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "*teste"), "*teste"));
        assertEquals(true, atributo.compara("LIKE", new Valor(atributo, "*test_"), "*test\\_"));
        assertEquals(false, atributo.compara("LIKE", new Valor(atributo, "teste"), "testes"));
        assertEquals(false, atributo.compara("LIKE", new Valor(atributo, "teste"), "test"));
        assertEquals(false, atributo.compara("LIKE", new Valor(atributo, "teste"), "test\\%"));
        assertEquals(false, atributo.compara("LIKE", new Valor(atributo, "teste"), "test\\e"));
        assertEquals(false, atributo.compara("LIKE", new Valor(atributo, "teste"), "test\\_"));

        assertEquals(true, atributo.compara("=", new Valor(atributo, "teste"), "teste"));
        assertEquals(false, atributo.compara("=", new Valor(atributo, "teste"), "testes"));

        assertEquals(true, atributo.compara("!=", new Valor(atributo, "teste"), "testes"));
        assertEquals(false, atributo.compara("!=", new Valor(atributo, "teste"), "teste"));

        assertEquals(true, atributo.compara(">", new Valor(atributo, "b"), "a"));
        assertEquals(false, atributo.compara(">", new Valor(atributo, "b"), "c"));

        assertEquals(true, atributo.compara(">=", new Valor(atributo, "b"), "a"));
        assertEquals(true, atributo.compara(">=", new Valor(atributo, "b"), "b"));
        assertEquals(false, atributo.compara(">=", new Valor(atributo, "b"), "c"));

        assertEquals(true, atributo.compara("<", new Valor(atributo, "b"), "c"));
        assertEquals(false, atributo.compara("<", new Valor(atributo, "b"), "a"));

        assertEquals(true, atributo.compara("<=", new Valor(atributo, "b"), "c"));
        assertEquals(true, atributo.compara("<=", new Valor(atributo, "b"), "b"));
        assertEquals(false, atributo.compara("<=", new Valor(atributo, "b"), "a"));
    }

    @Test
    public void TestConsultaUmaCondicao() throws Exception {
        Arquivo arquivo = new Arquivo(e1);
        Consulta consulta = new Consulta(arquivo, null);

        Object[] esperado = Arrays.asList(new Valor(cod, 0)).toArray();
        Object[] codigos = consulta.busca(nome, "=", "Ana").compila(prefix).getCodigos().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultaDuasCondicoes() throws Exception {
        Arquivo arquivo = new Arquivo(e1);
        Consulta consulta = new Consulta(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Valor(cod, 0),
                new Valor(cod, 2)
        ).toArray();
        Object[] codigos = consulta.
                busca(salario, ">", 1000.50).
                busca(salario, "<", 3000.0).
                compila(prefix).getCodigos().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultaDuasCondicoesEmCamposDiferente() throws Exception {
        Arquivo arquivo = new Arquivo(e1);
        Consulta consulta = new Consulta(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Valor(cod, 0)
        ).toArray();
        Object[] codigos = consulta.
                busca(idade, ">=", 20).
                busca(cod, "<", 2).
                compila(prefix).getCodigos().toArray();

        assertArrayEquals(esperado, codigos);
    }

    @Test
    public void TestConsultasConsecutivas() throws Exception {
        Arquivo arquivo = new Arquivo(e1);
        Consulta consulta = new Consulta(arquivo, null);

        Object[] esperado = Arrays.asList(
                new Valor(cod, 0),
                new Valor(cod, 1),
                new Valor(cod, 2)
        ).toArray();
        Object[] codigos = consulta.
                busca(salario, ">", 1000.0).
                busca(salario, "<", 3000.0).
                compila(prefix).getCodigos().toArray();

        assertArrayEquals(esperado, codigos);

        esperado = Arrays.asList(
                new Valor(cod, 0),
                new Valor(cod, 2)
        ).toArray();
        codigos = consulta.
                busca(salario, ">", 1000.50).
                compila(prefix).getCodigos().toArray();

        assertArrayEquals(esperado, codigos);
    }

}