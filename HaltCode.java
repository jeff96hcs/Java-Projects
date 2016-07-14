/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

/**
 *
 * @author jzhang2
 */
import interpreter.*;

public class HaltCode extends ByteCode {
    public HaltCode(){
        
    }
    
    @Override
    public String toString(){
        return "HALT";
    }
    @Override
    public void init(String args) {}

    @Override
    //asks for vm to stop running
    public void execute(VirtualMachine vm) {
        vm.stopRunning();
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
    }

    @Override
    public String getArgs() {
        return "";
    }
}
