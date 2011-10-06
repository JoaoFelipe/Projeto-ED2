/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

/**
 *
 * @author Joao
 */
public class FecharCommand extends Command{

    public FecharCommand() {
    }

    @Override
    public void execute(Object... arg) {
        System.exit(0);
    }



}
