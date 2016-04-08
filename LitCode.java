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
public class LitCode extends ByteCode{
    //the int to push
    private Integer arg;
    //the variable to initialize, if any
    private String var;
    
    public LitCode(){
        
    }
    
    @Override
    public String toString(){
        return "LIT";
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
    //pushing arg to runStack. if there is a variable, set it as key with arg as
    //value in VarsAndInts of vm
    public void execute(VirtualMachine vm) {
        vm.runStackPush(arg);        
        if(var!=null){
            vm.getVarsAndInts().put(var, arg);
        }
        System.out.print(toString() + " " + getArgs() + " ");
    }

    @Override
    //getting only the int arg as argument
    public String getArgs() {
        return arg.toString();
    }
}
