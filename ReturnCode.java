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
public class ReturnCode extends ByteCode{
    //to contain any possible function
    private String function;
    
    public ReturnCode(){
        
    }
    
    @Override
    public String toString(){
        return "RETURN";
    }
    
    @Override
    //setting function to args
    public void init(String args) {
        this.function = args;
    }

    @Override
    public void execute(VirtualMachine vm) {
        //pop frame first
        vm.runStackPopFrame();
        //set pc back to original call address, which is the top value of vm's
        //returnAddrs
        int jumpAddr = vm.popReturnAddr();
        vm.setPC(jumpAddr);
        System.out.print(toString() + " " + getArgs() + " ");
    }

    @Override
    public String getArgs() {
        return "";
    }
}
