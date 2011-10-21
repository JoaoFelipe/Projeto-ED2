

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Registro;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static utils.FileUtils.*;

/**
 *
 * @author Joao
 */
public class TestInsercao {

    String masterE1 = "test\\EmpregadoMaster.dat";
    String masterE2 = "test\\DependenteMaster.dat";
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
        
        EntidadeRelacionamento relacionamento = EntidadeRelacionamento.criarER("REFERENCIA", Arrays.asList("Dependente", "cod_emp", "Empregado", "cod"));

    }

    @Test
    public void inserirElemento0NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento4NaPosicao0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(4, "Eliana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento1NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(1, "Bia", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento5NaPosicao1() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(5, "Fabiana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento6NaPosicao2() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(6, "Giovana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1, Arrays.asList(6, "Giovana", 20)),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento4NaPosicao1ComConflito() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(4, "Eliana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(4, "Eliana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void expandirArquivoQuandoPassarDeDoisTercos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(2, "Carol", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void inserirElemento4NaPosicao4PoisTamanhoMudou() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(4, "D", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento8NaPosicao3Com2Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(8, "8", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "8", 20)),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento16NaPosicao6Com3Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "8", 20)),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(16, "16", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "8", 20)),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(16, "16", 20)),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void inserirElemento7NaPosicao0Com1Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(3, "Daniela", 20))
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(7, "Hanna", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(7, "Hanna", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(3, "Daniela", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElemento6NaPosicao3Com1Conflitos() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(6, "Giovana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1, Arrays.asList(6, "Giovana", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

     @Test
    public void falharAoTentarInserirElementoComCodigoRepetido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(false, arquivo.insere(new Registro(e1, Arrays.asList(2, "Carolina", 20))));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void falharAoTentarInserirElementoComCodigoRepetidoComConflito() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1, Arrays.asList(6, "Giovana", 20))
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(false, arquivo.insere(new Registro(e1, Arrays.asList(6, "Gisele", 20))));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(2, "Carol", 20)),
            new Registro(e1, Arrays.asList(6, "Giovana", 20))
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void duasExpansoes() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "8", 20)),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1, Arrays.asList(16, "16", 20)),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(17, "17", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(1, "Bia", 20)),
            new Registro(e1, Arrays.asList(17, "17", 20)),
            new Registro(e1, Arrays.asList(16, "16", 20)),
            new Registro(e1, Arrays.asList(4, "D", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1, Arrays.asList(8, "8", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


    @Test
    public void naoInserirQuandoNaoExistirReferenciado() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(prefix+e2.getNome()+".dat", Arrays.asList(
            new Registro(e2),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));

        Arquivo arquivo = new Arquivo(e2);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e2, Arrays.asList(6, "Giovana", 5, 0)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Registro(e2),
            new Registro(e2),
            new Registro(e2),
//            new Registro(e1, Arrays.asList(6, "Giovana", 5)),
            new Registro(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getNome()+".dat"));
    }

    @Test
    public void inserirQuandoExistirReferenciado() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(prefix+e2.getNome()+".dat", Arrays.asList(
            new Registro(e2),
            new Registro(e2),
            new Registro(e2),
            new Registro(e2)
        ));

        Arquivo arquivo = new Arquivo(e2);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e2, Arrays.asList(6, "Giovana", 5, 0)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, Arrays.asList(5, "Fabiana", 20)),
            new Registro(e1),
            new Registro(e1)
        ));

        gerarArquivo(masterE2, Arrays.asList(
            new Registro(e2),
            new Registro(e2),
            new Registro(e2, Arrays.asList(6, "Giovana", 5, 0)),
            new Registro(e2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
        assertArrayEquals(lerArquivo(e2, masterE2), lerArquivo(e2, prefix+e2.getNome()+".dat"));
    }

    @Test
    public void inserirElementoEmPosicaoRemovida() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElementoEmPosicaoRemovida0() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(4, "Ana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(4, "Ana", 20)),
            new Registro(e1),
            new Registro(e1),
            new Registro(e1)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void inserirElementoEmArquivoSoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, 2),
            new Registro(e1, 2),
            new Registro(e1, 2)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        arquivo.insere(new Registro(e1, Arrays.asList(0, "Ana", 20)));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, 2),
            new Registro(e1, 2),
            new Registro(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }

    @Test
    public void naoInserirRegistroRepetidoEmArquivoRemovido() throws Exception {
        gerarArquivo(prefix+e1.getNome()+".dat", Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, 2),
            new Registro(e1, 2)
        ));

        Arquivo arquivo = new Arquivo(e1);
        arquivo.abrir(prefix);
        assertEquals(false, arquivo.insere(new Registro(e1, Arrays.asList(0, "Ana", 20))));
        arquivo.fechar();

        gerarArquivo(masterE1, Arrays.asList(
            new Registro(e1, 2),
            new Registro(e1, Arrays.asList(0, "Ana", 20)),
            new Registro(e1, 2),
            new Registro(e1, 2)
        ));
        assertArrayEquals(lerArquivo(e1, masterE1), lerArquivo(e1, prefix+e1.getNome()+".dat"));
    }


}