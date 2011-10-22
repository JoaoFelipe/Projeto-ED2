package gd.models.ER;

import gd.exceptions.ModelException;
import java.io.DataOutputStream;
import java.util.List;
import java.io.File;

public abstract class EntidadeRelacionamento {

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
