/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class ArquivoSequencial {

    public static List<String> fileToList(String nome) throws FileNotFoundException, IOException {
        DataInputStream verificar = new DataInputStream(new FileInputStream(nome));
        List<String> lista = new ArrayList();
        try {
            while (true) {
                lista.add(verificar.readUTF());
            }
        } catch (EOFException e) {
        }
        verificar.close();
        return lista;
    }
}
