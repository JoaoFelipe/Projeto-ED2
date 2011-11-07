package utils;


import dm.models.Entity;
import dm.models.Tuple;
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

    public static void gerarArquivo(String nomeArquivo, List<Tuple> registros) throws Exception{

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
            for (int i = 0; i < registros.size(); i++) {
                registros.get(i).save(arquivo);
            }
        } catch (Exception e) {
        } finally {
            if (arquivo != null) {
                arquivo.close();
            }
        }

    }

    public static Object[] lerArquivo(Entity entidade, String nomeArquivo) throws Exception{

       RandomAccessFile arquivo = null;
       List<Tuple> lista = new ArrayList<Tuple>();
       try {
            arquivo = new RandomAccessFile( new File(nomeArquivo), "r");

            while (!(arquivo.getFilePointer() == arquivo.length())){
                lista.add(new Tuple(entidade, arquivo));
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
