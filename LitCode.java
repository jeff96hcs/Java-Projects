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
public class LitCode extends interpreter.ByteCodes.LitCode{
    @Override
    public void execute(VirtualMachine vm) {
        execute((DebuggerVirtualMachine) vm);
    }

    public void execute(DebuggerVirtualMachine dvm) {
        super.execute(dvm);
        if (var!=(null)) {
            int offset = dvm.getRunStackLastFrame().size() - 1;
            dvm.addVar(var, offset);
        }
    }    
}
