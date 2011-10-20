/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.models.ER;

import gd.exceptions.ModelException;
import gd.models.ER.Entidade;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joao
 */
public abstract class EntidadeRelacionamento {

    //Aqui está sendo usado o padrão Factory Method
    public static EntidadeRelacionamento criarER(String tipo, List<String> defs) throws ModelException {
        if (tipo.equals("TABELA")) {
            return new Entidade(defs);
        } else if (tipo.equals("REFERENCIA")) {
            Relacionamento relacionamento = new Relacionamento(defs);
            relacionamento.adicionarNaEntidade();
            return relacionamento;
        } else {
            return null;
        }
    }

    public abstract void grava(DataOutputStream saida) throws ModelException;

    public abstract String getNome();

    public void deletar(String prefix) {
        File file = new File(prefix + getNome() + ".dat");
        file.delete();
    }
}
