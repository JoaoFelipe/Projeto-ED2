import gd.models.arquivo.Valor;
import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Registro;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
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
    Entidade e1 = null;
    Entidade e2 = null;

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

        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));


        ListaER.getInstancia().add(e1);
        ListaER.getInstancia().add(e2);

        EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void removerElemento0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void removerElemento1NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 1), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void removerElemento2NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Clara", 20)),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 2), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, 2),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void removerElemento3NaPosicao3() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(3, "Daniela", 20))
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 3), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void removerElemento0NaPosicao1ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void removerElemento0NaPosicao1ComRegistroRemovido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void falhaAoRemoverRegistroInexistente() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(false, arquivo.remove(new Valor(e1.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void removerDependente() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(prefix+e2.getNome()+".dat", Arrays.asList(
            new Registro(e2, Arrays.asList(0, "A", 5, 0)),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));

        Arquivo arquivo = new Arquivo(e2);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e2.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Registro(e2, 2),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getNome()+".dat"));
    }

    @Test
    public void restringirDeletar() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(prefix+e2.getNome()+".dat", Arrays.asList(
            new Registro(e2, Arrays.asList(0, "A", 5, 0)),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(false, arquivo.remove(new Valor(e1.getPk(), 0), 0));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Registro(e2, Arrays.asList(0, "A", 5, 0)),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getNome()+".dat"));
    }
    
    @Test
    public void cascataDeletar() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(prefix+e2.getNome()+".dat", Arrays.asList(
            new Registro(e2, Arrays.asList(0, "A", 5, 0)),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(true, arquivo.remove(new Valor(e1.getPk(), 0), 1));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Registro(e2, 2),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getNome()+".dat"));
    }


}
