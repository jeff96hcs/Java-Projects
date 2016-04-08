/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;

/**
 *
 * @author jzhang2
 */
public class WriteCode extends ByteCode{
    public WriteCode(){
        
    }
    
    @Override
    public String toString(){
        return "WRITE";
    }
    
    @Override
    public void init(String args) {}

    @Override
    public void execute(VirtualMachine vm) {
        //only print the top value of runStack
        System.out.print(toString() + " " + vm.runStackPeek() + " ");
    }

    @Override
    public String getArgs() {
        return "";
    }
}
