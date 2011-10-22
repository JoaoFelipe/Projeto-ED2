import utils.ArquivoSequencial;
import gd.exceptions.NotFoundException;
import gd.models.ER.Relacionamento;
import gd.exceptions.NonUniqueException;
import java.lang.reflect.Field;
import gd.models.ER.ListaER;
import gd.models.ER.EntidadeRelacionamento;
import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import gd.models.ER.Entidade;
import java.util.List;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestRelacionamentos {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    EntidadeRelacionamento e1 = null;
    EntidadeRelacionamento e2 = null;

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

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }
 

    @Test
    public void criaRelacionamento() throws Exception {
        EntidadeRelacionamento relacionamento = new Relacionamento(e2, "cod_emp", e1, "cod");
        EntidadeRelacionamento relacionamentoTestado = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));
        assertEquals(relacionamento, relacionamentoTestado);
    }
    

    @Test
    public void naoPermitirCriarRelacionamentoComEntidadeInvalida() throws Exception {
        try {
            EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependentes", "cod_emp", "Empregado", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Entidade não encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComCampoDaEntidadeInvalido() throws Exception {
        try {
            EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "ahh", "Empregado", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Atributo não encontrado na Entidade", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComEntidadeReferenciadaDaEntidadeInvalida() throws Exception {
        try {
            EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Hahaha", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Entidade Referenciada não encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComAtributoDaEntidadeReferenciadaDaEntidadeInvalida() throws Exception {
        try {
            EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "ahh"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Atributo não encontrado na Entidade Referenciada", e.getMessage());
        }
    }

    @Test
    public void gravarRelacionamento() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")).grava(arq);
        arq.close();

        List<String> listaVerificada = ArquivoSequencial.fileToList(metaDadosPath);
        assertArrayEquals(
            Arrays.asList(
                "REFERENCIA", "Dependente", "cod_emp", "Empregado", "cod").toArray(),
            listaVerificada.toArray()
        );
    }

    @Test
    public void lerReferenciasDeArquivoDeEstruturas() throws Exception {
        

        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")),
            EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntidadeRelacionamento e : listaOriginal) {
            e.grava(arq);
        }
        arq.close();
        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        assertArrayEquals(listaOriginal.toArray(), inst.getLista().toArray());
    }

    @Test
    public void naoPermitirAdicionarReferenciasDeMesmaEntidadeAtributo() throws Exception {

        ListaER.getInstancia().add(EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")));

        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")),
            EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregados", "cod"))
        );

        boolean ok = false;
        for (EntidadeRelacionamento e : listaOriginal) {
            try {
                ListaER.getInstancia().add(e);
            } catch (NonUniqueException exc) {
                assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", exc.getMessage());
                ok = true;
            }
        }
        assertTrue(ok);

    }

    @Test
    public void naoPermitirLerReferenciasDeMesmoEntidadeAtributoNoArquivoDeEstruturas() throws Exception {
        ListaER.getInstancia().add(EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")));

        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")),
            EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregados", "cod"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntidadeRelacionamento e : listaOriginal) {
            e.grava(arq);
        }
        arq.close();

        ListaER.apagarInstancia();
        try {
            ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);
            assertTrue(false);
        } catch (NonUniqueException e) {
            assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", e.getMessage());
        }
    }

    @Test
    public void aoCriarRelacionamentoAReferenciaDeveSerAdicionadaAEntidade() throws Exception {
        EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

        Class c = relacionamento.getClass();
        Field f = c.getDeclaredField("entidade");
        f.setAccessible(true);
        Entidade e = (Entidade) f.get(relacionamento);


        assertEquals(e.getRelacionamentos().size(), 1);
        assertEquals(e.getRelacionamentos().get(0), relacionamento);
    }

}
