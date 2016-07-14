/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;
import interpreter.debugger.*;

/**
 *
 * @author jzhang2
 */
public class StoreCode extends ByteCode{
    //arg to use
    private Integer arg;
    //possible variable name where data is stored
    private String var;
    private int store;
    public StoreCode(){
        
    }
    
    @Override
    public String toString(){
        return "STORE";
    }
    
    @Override
    public void init(String args) {
        //splitting args by space due to the possibility of having more than one
        //argument, with the first argument being an integer that will be parsed and
        //stored into arg. 
        String[] arguments = args.split(" ");
        arg = Integer.parseInt(arguments[0]);
        //If there is more than one argument then set var to the 
        //second argument, which is the variable name
        if(arguments.length > 1){
            this.var = arguments[1];
        }
    }

    @Override
    public void execute(VirtualMachine vm) {
        //If there is a variable, then set it as key with arg as value in 
        //VarsAndInts of vm
        if(!var.equals(null)){
            vm.getVarsAndInts().put(var, arg);
        }
        //running the store method of vm's runStack with the value in key var
        //in VarsAndInts
        store= vm.runStackStore(vm.getVarsAndInts().get(var));
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
        /*if(Interpreter.debugMode){
            execute((DebuggerVirtualMachine) vm);
        }*/
    }
    
    /*public void execute(DebuggerVirtualMachine dvm){
        if(var!=null){
            dvm.addVar(var, store);
        }
    }*/

    @Override
    //getting only the int arg as argument
    public String getArgs() {
        if(var==null){
            return arg.toString();
        } else {
            return arg.toString()+  " " +var;
        } 
    }
}
