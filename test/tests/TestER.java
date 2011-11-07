package tests;

import java.io.FileOutputStream;
import java.io.DataOutputStream;
import utils.ArquivoSequencial;
import java.util.List;
import java.util.Arrays;
import dm.models.ER.ERList;
import java.io.File;
import dm.models.ER.EntityRelationship;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestER {

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    EntityRelationship e1 = null;
    EntityRelationship e2 = null;
    EntityRelationship e3 = null;

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

        e3 = EntityRelationship.createER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));
        ERList.getInstance().add(e3);

    }

    @After
    public void tearDown() throws Exception {
        deletarArquivos();
    }

    @Test
    public void removerTabela() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.save(arq);
        e2.save(arq);
        arq.close();

        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);
        inst.remove(e1);


        assertArrayEquals(Arrays.asList(e2).toArray(), inst.getList().toArray());
    }


    @Test
    public void remover2Tabelas() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.save(arq);
        e2.save(arq);
        arq.close();

        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);
        inst.remove(e1);
        inst.remove(e2);


        assertArrayEquals(Arrays.asList().toArray(), inst.getList().toArray());
    }

    @Test
    public void removerTabelaPorNome() throws Exception {
        DataOutputStream arq = new DataOutputStream(new FileOutputStream(metaDadosPath));
        e1.save(arq);
        e2.save(arq);
        arq.close();

        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);
        inst.removeByName("Empregado");


        assertArrayEquals(Arrays.asList(e2).toArray(), inst.getList().toArray());
    }

    @Test
    public void removerReferencia() throws Exception {
        ERList inst = ERList.getInstance();

        assertArrayEquals(Arrays.asList(e1, e2, e3).toArray(), inst.getList().toArray());

        inst.remove(e3);
        assertArrayEquals(Arrays.asList(e1, e2).toArray(), inst.getList().toArray());
    }

    @Test
    public void removerEntidadeComReferencia() throws Exception {
        ERList inst = ERList.getInstance();

        assertArrayEquals(Arrays.asList(e1, e2, e3).toArray(), inst.getList().toArray());

        inst.remove(e2);
        assertArrayEquals(Arrays.asList(e1).toArray(), inst.getList().toArray());
    }


    @Test
    public void gravarListaEntidadesEmArquivo() throws Exception {
        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        ERList.getInstance().add(EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int")));
        ERList.getInstance().add(EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int")));

        ERList.getInstance().save();

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
        ERList.getInstance().save();
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
            ERList.getInstance().getNames().toArray()
        );
    }

    @Test
    public void listarEntidades() throws Exception {
        assertArrayEquals(
            Arrays.asList(
                "Empregado",
                "Dependente").toArray(),
            ERList.getInstance().getTableNames().toArray()
        );
    }

}