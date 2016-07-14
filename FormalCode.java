/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes.debuggerByteCodes;

import interpreter.*;
import interpreter.ByteCodes.*;
import interpreter.debugger.*;

/**
 *
 * @author Jia Hu
 */
public class FormalCode extends ByteCode{
    private String varName;
    private Integer offset;
    @Override
    public String toString() {
        return "FORMAL";
    }

    @Override
    public void init(String args) {
        String[] arg = args.split(" ");
        varName = arg[0];
        offset = Integer.parseInt(arg[1]);
    }

    @Override
    public void execute(VirtualMachine vm) {
        execute((DebuggerVirtualMachine) vm);    
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
    }
    
    public void execute(DebuggerVirtualMachine dvm){
        dvm.addVar(varName, offset);
    }

    @Override
    public String getArgs() {
        return varName + " "+ offset.toString();
    }
}
