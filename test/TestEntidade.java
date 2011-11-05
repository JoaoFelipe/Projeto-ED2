import gd.exceptions.NonUniqueException;
import gd.models.ER.ERList;
import java.io.File;
import utils.ArquivoSequencial;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import gd.models.ER.EntityRelationship;
import gd.models.ER.Entity;
import gd.models.atributos.IntAttr;
import gd.models.atributos.Attribute;
import java.util.List;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestEntidade {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";

    public void deletarArquivos() {
        for (String string : Arrays.asList(metaDadosPath)) {
            File file = new File(string);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Before
    public void setUp() {
        deletarArquivos();
        ERList.apagarInstancia();
    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void criarTabela() throws Exception {
        List<Attribute> attrs = Arrays.asList((Attribute) new IntAttr("cod", true), (Attribute) new IntAttr("idade", false));
        EntityRelationship entidade = new Entity("Teste", attrs);

        EntityRelationship entidadeTestada = EntityRelationship.createER("TABELA", Arrays.asList("Teste", "*cod:int", "idade:int"));

        assertEquals(entidade, entidadeTestada);
    }

    @Test
    public void naoPermitirCriarTabelaComAtributosDeMesmoNome() throws Exception {
        try {
            EntityRelationship.createER("TABELA", Arrays.asList("Teste", "*cod:int", "cod:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Atributo não é único", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaSemChavePrimaria() throws Exception {
        try {
            EntityRelationship.createER("TABELA", Arrays.asList("Teste", "cod:int", "nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Nenhuma chave primária encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaComMaisDeUmaChavePrimaria() throws Exception {
        try {
            EntityRelationship.createER("TABELA", Arrays.asList("Teste", "*cod:int", "*nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Mais de uma chave primária encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaSemNome() throws Exception {
        try {
            EntityRelationship.createER("TABELA", Arrays.asList("", "*cod:int", "nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("O nome não pode estar em branco", e.getMessage());
        }
    }

    @Test
    public void gravarTabela() throws Exception {
        List<Attribute> attrs = Arrays.asList((Attribute) new IntAttr("cod", true), (Attribute) new IntAttr("idade", false));
        Entity entidade = new Entity("Teste", attrs);
        
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        entidade.save(arq);
        arq.close();

        List<String> listaVerificada = ArquivoSequencial.fileToList(metaDadosPath);
        assertArrayEquals(
            Arrays.asList("TABELA", "Teste", "*cod:int", "idade:int").toArray(),
            listaVerificada.toArray()
        );
    }

    @Test
    public void gravar2Tabelas() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        EntityRelationship.createER("TABELA", Arrays.asList("Teste", "*cod:int", "idade:int")).save(arq);
        EntityRelationship.createER("TABELA", Arrays.asList("Teste2", "*cod:int", "nome:char30")).save(arq);
        arq.close();

        List<String> listaVerificada = ArquivoSequencial.fileToList(metaDadosPath);
        assertArrayEquals(
            Arrays.asList(
                "TABELA", "Teste", "*cod:int", "idade:int",
                "TABELA", "Teste2", "*cod:int", "nome:char30").toArray(),
            listaVerificada.toArray()
        );
    }

    @Test
    public void lerTabelasDeArquivoDeEstruturas() throws Exception {
        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntityRelationship e : listaOriginal) {
            e.save(arq);
        }
        arq.close();

        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        assertArrayEquals(listaOriginal.toArray(), inst.getList().toArray());
    }

    @Test
    public void naoPermitirAdicionarTabelasDeMesmoNome() throws Exception {
        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        boolean ok = false;
        for (EntityRelationship e : listaOriginal) {
            try {
                ERList.instanciarTeste(metaDadosPath, prefix).add(e);
            } catch (NonUniqueException exc) {
                assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", exc.getMessage());
                ok = true;
            }
        }
        assertTrue(ok);

    }

    @Test
    public void naoPermitirLerTabelasDeMesmoNomeNoArquivoDeEstruturas() throws Exception {
        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntityRelationship e : listaOriginal) {
            e.save(arq);
        }
        arq.close();

        try {
            ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);
            assertTrue(false);
        } catch (NonUniqueException e) {
            assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", e.getMessage());
        }
    }

  

   

}