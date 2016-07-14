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
public class FalseBranchCode extends ByteCode{
    //the function to call
    String function;
    
    public FalseBranchCode(){
        
    }
    
    @Override
    public String toString(){
        return "FALSEBRANCH";
    }
    
    @Override
    //set function equal to args, the argument
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
        function = vm.getProgram().getArgs().get(vm.getPC());
        //popping the top value from runStack        
        int poppedValue = vm.runStackPop();
        //if the popped value is 0
        if(poppedValue==0){
            //vmByteCodes and vmArgs, copies of the vm program's ArrayLists, 
            //byteCodes and args, respectively
            ArrayList<ByteCode> vmByteCodes = vm.getProgram().getByteCodes();
            ArrayList<String> vmArgs = vm.getProgram().getArgs();
            //getting the address, which has been resolved to an int and is placed
            //in the pc-th entry of vmArgs, and storing it in lblAddr
            String lblAddr =vmArgs.get(vm.getPC());
            //converting lblAddr to int and storing it in LabelAddr
            int LabelAddr = Integer.parseInt(lblAddr);
            //setting pc to LabelAddr-2 so that the next byteCode executed will be
            //LABEL
            vm.setPC(LabelAddr-2);            
        }
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
    }

    @Override
    //returning function as Args
    public String getArgs() {
        return function;
    }
}
