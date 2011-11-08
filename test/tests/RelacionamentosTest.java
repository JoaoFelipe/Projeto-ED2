package tests;

import utils.ArquivoSequencial;
import dm.exceptions.NotFoundException;
import dm.models.Relation;
import dm.exceptions.NonUniqueException;
import java.lang.reflect.Field;
import dm.models.ERList;
import dm.models.EntityRelationship;
import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import dm.models.Entity;
import java.util.List;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RelacionamentosTest {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    EntityRelationship e1 = null;
    EntityRelationship e2 = null;

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
        
        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        e1 = EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ERList.getInstance().add(e1);
        ERList.getInstance().add(e2);

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }
 

    @Test
    public void criaRelacionamento() throws Exception {
        EntityRelationship relacionamento = new Relation(e2, "cod_emp", e1, "cod");
        EntityRelationship relacionamentoTestado = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));
        assertEquals(relacionamento, relacionamentoTestado);
    }
    

    @Test
    public void naoPermitirCriarRelacionamentoComEntidadeInvalida() throws Exception {
        try {
            EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependentes", "cod_emp", "Empregado", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Entidade não encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComCampoDaEntidadeInvalido() throws Exception {
        try {
            EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "ahh", "Empregado", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Atributo não encontrado na Entidade", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComEntidadeReferenciadaDaEntidadeInvalida() throws Exception {
        try {
            EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Hahaha", "cod"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Entidade Referenciada não encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarRelacionamentoComAtributoDaEntidadeReferenciadaDaEntidadeInvalida() throws Exception {
        try {
            EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "ahh"));
            assertTrue(false);
        } catch (NotFoundException e){
            assertEquals("Atributo não encontrado na Entidade Referenciada", e.getMessage());
        }
    }

    @Test
    public void gravarRelacionamento() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")).save(arq);
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
        

        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")),
            EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntityRelationship e : listaOriginal) {
            e.save(arq);
        }
        arq.close();
        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        assertArrayEquals(listaOriginal.toArray(), inst.getList().toArray());
    }

    @Test
    public void naoPermitirAdicionarReferenciasDeMesmaEntidadeAtributo() throws Exception {

        ERList.getInstance().add(EntityRelationship.createER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")));

        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")),
            EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregados", "cod"))
        );

        boolean ok = false;
        for (EntityRelationship e : listaOriginal) {
            try {
                ERList.getInstance().add(e);
            } catch (NonUniqueException exc) {
                assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", exc.getMessage());
                ok = true;
            }
        }
        assertTrue(ok);

    }

    @Test
    public void naoPermitirLerReferenciasDeMesmoEntidadeAtributoNoArquivoDeEstruturas() throws Exception {
        ERList.getInstance().add(EntityRelationship.createER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")));

        List<EntityRelationship> listaOriginal = Arrays.asList(
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")),
            EntityRelationship.createER("TABELA", Arrays.asList("Empregados", "*cod:int", "nome:char30", "idade:int")),
            EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod")),
            EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregados", "cod"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntityRelationship e : listaOriginal) {
            e.save(arq);
        }
        arq.close();

        ERList.apagarInstancia();
        try {
            ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);
            assertTrue(false);
        } catch (NonUniqueException e) {
            assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", e.getMessage());
        }
    }

    @Test
    public void aoCriarRelacionamentoAReferenciaDeveSerAdicionadaAEntidade() throws Exception {
        EntityRelationship relacionamento = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

        Class c = relacionamento.getClass();
        Field f = c.getDeclaredField("entity");
        f.setAccessible(true);
        Entity e = (Entity) f.get(relacionamento);


        assertEquals(e.getRelation().size(), 1);
        assertEquals(e.getRelation().get(0), relacionamento);
    }

}
