/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.exceptions.NonUniqueException;
import gd.models.ER.ListaER;
import java.io.File;
import utils.ArquivoSequencial;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import gd.models.atributos.IntAttr;
import gd.models.atributos.Atributo;
import java.util.List;
import java.util.Arrays;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class TestEntidade {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";

    @Before
    public void setUp() {
        File file = new File(metaDadosPath);
        if (file.exists()) {
            file.delete();
        }
        ListaER.apagarInstancia();
    }

    @Test
    public void criarTabela() throws Exception {
        List<Atributo> attrs = Arrays.asList((Atributo) new IntAttr("cod", true), (Atributo) new IntAttr("idade", false));
        EntidadeRelacionamento entidade = new Entidade("Teste", attrs);

        EntidadeRelacionamento entidadeTestada = EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste", "*cod:int", "idade:int"));

        assertEquals(entidade, entidadeTestada);
    }

    @Test
    public void naoPermitirCriarTabelaComAtributosDeMesmoNome() throws Exception {
        try {
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste", "*cod:int", "cod:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Atributo não é único", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaSemChavePrimaria() throws Exception {
        try {
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste", "cod:int", "nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Nenhuma chave primária encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaComMaisDeUmaChavePrimaria() throws Exception {
        try {
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste", "*cod:int", "*nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("Mais de uma chave primária encontrada", e.getMessage());
        }
    }

    @Test
    public void naoPermitirCriarTabelaSemNome() throws Exception {
        try {
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("", "*cod:int", "nome:char"));
            assertTrue(false);
        } catch (Exception e) {
            assertEquals("O nome não pode estar em branco", e.getMessage());
        }
    }

    @Test
    public void gravarTabela() throws Exception {
        List<Atributo> attrs = Arrays.asList((Atributo) new IntAttr("cod", true), (Atributo) new IntAttr("idade", false));
        Entidade entidade = new Entidade("Teste", attrs);
        
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        entidade.grava(arq);
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
        EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste", "*cod:int", "idade:int")).grava(arq);
        EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Teste2", "*cod:int", "nome:char30")).grava(arq);
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
        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntidadeRelacionamento e : listaOriginal) {
            e.grava(arq);
        }
        arq.close();

        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        assertArrayEquals(listaOriginal.toArray(), inst.getLista().toArray());
    }

    @Test
    public void naoPermitirAdicionarTabelasDeMesmoNome() throws Exception {
        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        boolean ok = false;
        for (EntidadeRelacionamento e : listaOriginal) {
            try {
                ListaER.instanciarTeste(metaDadosPath, prefix).add(e);
            } catch (NonUniqueException exc) {
                assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", exc.getMessage());
                ok = true;
            }
        }
        assertTrue(ok);

    }

    @Test
    public void naoPermitirLerTabelasDeMesmoNomeNoArquivoDeEstruturas() throws Exception {
        List<EntidadeRelacionamento> listaOriginal = Arrays.asList(
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")),
            EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"))
        );

        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        for (EntidadeRelacionamento e : listaOriginal) {
            e.grava(arq);
        }
        arq.close();

        try {
            ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);
            assertTrue(false);
        } catch (NonUniqueException e) {
            assertEquals("Não é possível ter tabelas de mesmo nome ou referencias de mesmo <Entidade, Campo>!", e.getMessage());
        }
    }

  

   

}