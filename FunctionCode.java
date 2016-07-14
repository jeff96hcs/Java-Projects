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
public class FunctionCode extends ByteCode{
    private String func;
    private Integer start;
    private Integer end;
    @Override
    public String toString() {
        return "FUNCTION";
    }

    @Override
    public void init(String args) {
        String[] arg = args.split(" ");
        func = arg[0];
        start = Integer.parseInt(arg[1]);
        end = Integer.parseInt(arg[2]);
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
        FunctionEnvironmentRecord fer = dvm.popFunc();
        fer.setFunc(func, start, end);
        dvm.addFunc(fer);
        if(start>=0){
            dvm.setCurrLine(start);                
        }
    }
    @Override
    public String getArgs() {
        return func+" " + start.toString()+ " " + end.toString();
    }
}
