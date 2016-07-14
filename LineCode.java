/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes.debuggerByteCodes;

import interpreter.ByteCodes.*;
import interpreter.debugger.*;
import interpreter.VirtualMachine;

/**
 *
 * @author Jia Hu
 */
public class LineCode extends ByteCode{
    private Integer LineNo;
    @Override
    public String toString() {
        return "LINE";
    }

    @Override
    public void init(String args) {
        LineNo = Integer.parseInt(args);
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
        if(LineNo>=0){
            dvm.setCurrLine(LineNo);
        }
    }
    @Override
    public String getArgs() {
        return LineNo.toString();
    }
    
}
