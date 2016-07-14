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
public class LabelCode extends ByteCode{
    //the function being labelled
    private String function;
    
    public LabelCode(){
        
    }
    
    @Override
    public String toString(){
        return "LABEL";
    }
    
    @Override
    //setting function to args
    public void init(String args) {
        this.function = args;
    }

    @Override
    public void execute(VirtualMachine vm) {
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
    }

    @Override
    public String getArgs() {
        return function;
    }
}
