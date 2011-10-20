package utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.models.arquivo.Registro;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import java.io.File;
import org.junit.Before;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;


/**
 *
 * @author Joao
 */
public class TestUtils {

    String nomeArquivo = "clientes.dat";
    String nomeArquivoMaster = "ClientesMaster.dat";

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    Entidade e1 = null;
    Entidade e2 = null;

    @Before
    public void setUp() throws Exception {
        File file = new File(metaDadosPath);
        if (file.exists()) {
            file.delete();
        }
        ListaER.apagarInstancia();
        ListaER inst = ListaER.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = (Entidade) EntidadeRelacionamento.criarER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ListaER.getInstancia().add(e1);
        ListaER.getInstancia().add(e2);

    }



    @Test
    public void CompararArquivosVazios() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

    @Test
    public void CompararArquivosCheiosCom1Elemento() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

    @Test
    public void CompararArquivosCheiosComArquivosCheios() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1, Arrays.asList(3, "Dani", 20))
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1, Arrays.asList(3, "Dani", 20))
        ));

        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

}