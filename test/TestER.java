import java.io.FileOutputStream;
import java.io.DataOutputStream;
import utils.ArquivoSequencial;
import java.util.List;
import java.util.Arrays;
import gd.models.ER.ListaER;
import java.io.File;
import gd.models.ER.EntidadeRelacionamento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestER {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    EntidadeRelacionamento e1 = null;
    EntidadeRelacionamento e2 = null;
    EntidadeRelacionamento e3 = null;

    public void deletarArquivos() {
        for (String string : Arrays.asList(metaDadosPath)) {
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

        e1 = EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ListaER.getInstancia().add(e1);
        ListaER.getInstancia().add(e2);

        e3 = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));
        ListaER.getInstancia().add(e3);

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void removerTabela() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.grava(arq);
        e2.grava(arq);
        arq.close();

        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);
        inst.remove(e1);


        assertArrayEquals(Arrays.asList(e2).toArray(), inst.getLista().toArray());
    }


    @Test
    public void remover2Tabelas() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.grava(arq);
        e2.grava(arq);
        arq.close();

        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);
        inst.remove(e1);
        inst.remove(e2);


        assertArrayEquals(Arrays.asList().toArray(), inst.getLista().toArray());
    }

    @Test
    public void removerTabelaPorNome() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.grava(arq);
        e2.grava(arq);
        arq.close();

        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);
        inst.removePorNome("Empregado");


        assertArrayEquals(Arrays.asList(e2).toArray(), inst.getLista().toArray());
    }

    @Test
    public void removerReferencia() throws Exception {
        ListaER inst = ListaER.getInstancia();

        assertArrayEquals(Arrays.asList(e1, e2, e3).toArray(), inst.getLista().toArray());

        inst.remove(e3);
        assertArrayEquals(Arrays.asList(e1, e2).toArray(), inst.getLista().toArray());
    }

    @Test
    public void removerEntidadeComReferencia() throws Exception {
        ListaER inst = ListaER.getInstancia();

        assertArrayEquals(Arrays.asList(e1, e2, e3).toArray(), inst.getLista().toArray());

        inst.remove(e2);
        assertArrayEquals(Arrays.asList(e1).toArray(), inst.getLista().toArray());
    }


    @Test
    public void gravarListaEntidadesEmArquivo() throws Exception {
        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        ListaER.getInstancia().add(EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")));
        ListaER.getInstancia().add(EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")));

        ListaER.getInstancia().grava();

        List<String> listaVerificada = ArquivoSequencial.fileToList(metaDadosPath);
        assertArrayEquals(
            Arrays.asList(
                "TABELA", "Empregado", "*cod:int", "nome:char30", "idade:int",
                "TABELA", "Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int").toArray(),
            listaVerificada.toArray()
        );
    }


    @Test
    public void gravarListaEREmArquivo() throws Exception {
        ListaER.getInstancia().grava();
        List<String> listaVerificada = ArquivoSequencial.fileToList(metaDadosPath);

        assertArrayEquals(
            Arrays.asList(
                "TABELA", "Empregado", "*cod:int", "nome:char30", "idade:int",
                "TABELA", "Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int",
                "REFERENCIA", "Dependente", "cod_emp", "Empregado", "cod").toArray(),
            listaVerificada.toArray()
        );
    }

    @Test
    public void listarNomes() throws Exception {
        assertArrayEquals(
            Arrays.asList(
                "Empregado",
                "Dependente",
                "R: Dependente cod_emp").toArray(),
            ListaER.getInstancia().getNomes().toArray()
        );
    }

    @Test
    public void listarEntidades() throws Exception {
        assertArrayEquals(
            Arrays.asList(
                "Empregado",
                "Dependente").toArray(),
            ListaER.getInstancia().getNomesTabelas().toArray()
        );
    }

}