/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;
import java.util.*;

/**
 *
 * @author jzhang2
 */
public class CallCode extends ByteCode{
    //the function to call
    String function;
    
    public CallCode(){
        
    }
    
    @Override
    public String toString(){
        return "CALL";
    }
    
    //set function equal to args, the argument
    @Override
    public void init(String args) {
        function = args;
    }

    @Override
    public void execute(VirtualMachine vm) {
        //if addressResolved is false, then resolve address and set 
        //addressResolved to true
        if(!vm.getAddressResolved()){
            vm.getProgram().resolveAddress();
            vm.setAddressResolved(true);
        }
        //push the current pc, which is the return address, to returnAddrs
        vm.pushReturnAddr();
        //vmByteCodes and vmArgs, copies of the vm program's ArrayLists, 
        //byteCodes and args, respectively
        ArrayList<ByteCode> vmByteCodes = vm.getProgram().getByteCodes();
        ArrayList<String> vmArgs = vm.getProgram().getArgs();
        //getting the address, which has been resolved to an int and is placed
        //in the pc-th entry of vmArgs, and storing it in lblAddr
        String lblAddr =vmArgs.get(vm.getPC());
        //converting lblAddr to int and storing it in LabelAddr        
        int LabelAddr = Integer.parseInt(lblAddr);
        //setting pc to LabelAddr-1 so that the next byteCode executed will be
        //LABEL
        vm.setPC(LabelAddr-1);
        System.out.print(toString() + " "+ getArgs() + " ");
    }

    //returning function as Args
    @Override
    public String getArgs() {
        return function;
    }
}
