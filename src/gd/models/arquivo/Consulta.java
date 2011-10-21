/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models.arquivo;

import gd.models.atributos.Atributo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class Consulta {

    private Arquivo arquivo = null;
    private List<Busca> buscas = null;
    private List<Valor> codigos = null;

    public Consulta(Arquivo arquivo, List<Valor> codigos) {
        this.arquivo = arquivo;
        this.codigos = codigos;
        this.buscas = new ArrayList<Busca>();
    }

    public Consulta busca(Atributo atributo, String operador, Object valor) {
        if (atributo.getPK() && operador.equals("=")){
            codigos = new ArrayList<Valor>();
            codigos.add(new Valor(atributo, valor));
        } else {
            buscas.add(new Busca(atributo, operador, valor));
        }
        return this;
    }

    public Consulta compila() throws IOException{
        return this.compila("");
    }

    public Consulta compila(String prefix) throws IOException{
        arquivo.abrir(prefix);
        if (getCodigos() == null){
            percorreNull();
        } else {
            percorre();
        }
        arquivo.fechar();
        buscas.clear();
        return this;
    }

    private void percorreNull() throws IOException {
        codigos = new ArrayList<Valor>();
        for (int i = 0; i < arquivo.getTamanho(); i++) {
            arquivo.seek(i);
            Registro registro = arquivo.lerRegistro();
            if (registro.isUsado() && verificaCondicoes(registro)) {
                getCodigos().add(registro.getPk());
            }
        }
    }

    private void percorre() throws IOException {
        List<Valor> codigosTemp = new ArrayList<Valor>();
        for (Valor valor : getCodigos()) {
            Resultado resultado = arquivo.busca(valor);
            if (resultado.isEncontrado()) {
                arquivo.seek(resultado.getPosicao());
                Registro registro = arquivo.lerRegistro();
                if (registro.isUsado() && verificaCondicoes(registro)) {
                    codigosTemp.add(registro.getPk());
                }
            }
        }
        codigos = codigosTemp;
    }

    private boolean verificaCondicoes(Registro registro) {
        boolean condicao = true;
        for (Valor valor : registro.valores) {
            for (Busca busca : buscas) {
                if (valor.getTipo() == busca.getAtributo()){
                    condicao = condicao && busca.compara(valor);
                }
            }
        }

        return condicao;
    }

    /**
     * @return the codigos
     */
    public List<Valor> getCodigos() {
        return codigos;
    }


}
