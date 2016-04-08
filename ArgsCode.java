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
public class ArgsCode extends ByteCode {
    //Integer to contain the argument
    private Integer NoOfArgs = 0;
    
    public ArgsCode(){
        
    }
    
    @Override
    public String toString(){
        return "ARGS";
    }
    
    @Override
    //args will be an integer, and NoOrArgs will be set to args when converted 
    //to int/Integer
    public void init(String args) {
        NoOfArgs = Integer.parseInt(args);
    }

    @Override
    //setting a new frame NoOfArgs positions down from top
    public void execute(VirtualMachine vm) {
        vm.runStackNewFrameAt(NoOfArgs);
        System.out.print(toString() + " " + getArgs() + " ");
    }

    @Override
    //converting the int value NoOfArgs to String
    public String getArgs() {
        return NoOfArgs.toString();
    }
}
