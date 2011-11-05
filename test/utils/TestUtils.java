package utils;

import gd.models.arquivo.Tuple;
import gd.models.ER.EntityRelationship;
import gd.models.ER.Entity;
import gd.models.ER.ERList;
import java.io.File;
import org.junit.Before;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

public class TestUtils {

    String nomeArquivo = "clientes.dat";
    String nomeArquivoMaster = "ClientesMaster.dat";

    String metaDadosPath = "test\\metadados-teste.dat";
    String prefix = "test\\";
    Entity e1 = null;
    Entity e2 = null;

    @Before
    public void setUp() throws Exception {
        File file = new File(metaDadosPath);
        if (file.exists()) {
            file.delete();
        }
        ERList.apagarInstancia();
        ERList inst = ERList.instanciarTeste(metaDadosPath, prefix);

        e1 = (Entity) EntityRelationship.createER("TABELA", Arrays.asList("Empregado", "*cod:int", "nome:char30", "idade:int"));
        e2 = (Entity) EntityRelationship.createER("TABELA", Arrays.asList("Dependente", "*cod:int", "nome:char30", "idade:int", "cod_emp:int"));

        ERList.getInstance().add(e1);
        ERList.getInstance().add(e2);

    }

    @Test
    public void CompararArquivosVazios() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

    @Test
    public void CompararArquivosCheiosCom1Elemento() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1),
            new Tuple(e1),
            new Tuple(e1)
        ));

        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

    @Test
    public void CompararArquivosCheiosComArquivosCheios() throws Exception {
        gerarArquivo(nomeArquivoMaster, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1, Arrays.asList(3, "Dani", 20))
        ));
        gerarArquivo(nomeArquivo, Arrays.asList(
            new Tuple(e1, Arrays.asList(0, "Ana", 20)),
            new Tuple(e1, Arrays.asList(1, "Bia", 20)),
            new Tuple(e1, Arrays.asList(2, "Carol", 20)),
            new Tuple(e1, Arrays.asList(3, "Dani", 20))
        ));

        assertArrayEquals(lerArquivo(e1, nomeArquivoMaster), lerArquivo(e1, nomeArquivo));
    }

}