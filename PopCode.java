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
public class PopCode extends interpreter.ByteCodes.PopCode{
    @Override
    public void execute(VirtualMachine vm) {
        execute((DebuggerVirtualMachine) vm);        
    }

    public void execute(DebuggerVirtualMachine dvm) {
        super.execute(dvm);
        dvm.popVars(lvlsToPop);        
    }
}
