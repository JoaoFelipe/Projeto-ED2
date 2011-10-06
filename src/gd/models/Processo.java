/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.models;

/**
 *
 * @author Joao
 */
public interface Processo<T, G> {
    G processar(T type);
}
