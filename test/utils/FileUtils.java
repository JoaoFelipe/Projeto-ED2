package utils;


import gd.models.ER.Entidade;
import gd.models.arquivo.Registro;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joao
 */
public class FileUtils {

    public static void gerarArquivo(String nomeArquivo, List<Registro> registros) throws Exception{

       RandomAccessFile arquivo = null;
       try {
            //Se arquivo já existe, apaga
            File f = new File(nomeArquivo);
            if (f.exists()) {
                f.delete();
            }
            //Abre o arquivo para leitura e escrita (rw)
            arquivo = new RandomAccessFile(f, "rw");
            //Grava os clientes no arquivo
            for (int i = 0; i < 5; i++) {
                registros.get(i).grava(arquivo);
            }
        } catch (Exception e) {
        } finally {
            if (arquivo != null) {
                arquivo.close();
            }
        }

    }

    public static Object[] lerArquivo(Entidade entidade, String nomeArquivo) throws Exception{

       RandomAccessFile arquivo = null;
       List<Registro> lista = new ArrayList<Registro>();
       try {
            arquivo = new RandomAccessFile( new File(nomeArquivo), "r");

            while (!(arquivo.getFilePointer() == arquivo.length())){
                lista.add(new Registro(entidade, arquivo));
            }
        } catch (Exception e) {
        } finally {
            if (arquivo != null) {
                arquivo.close();
            }
        }
        return lista.toArray();

    }


}
