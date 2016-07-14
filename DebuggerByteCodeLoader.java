/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

import interpreter.*;
import java.io.IOException;
/**
 *
 * @author Jia Hu
 */
public class DebuggerByteCodeLoader extends ByteCodeLoader{
    
    public DebuggerByteCodeLoader(String codeFile) throws IOException {
        super(codeFile);
    }
    
    @Override
    protected String getCodeClass(String code){
        //load the debugger byteCodes
        if(code.equals("FORMAL")||code.equals("FUNCTION")||code.equals("LINE")
                ||code.equals("POP")||code.equals("LIT")||code.equals("RETURN")
                ||code.equals("CALL")){
            return "debuggerByteCodes."+CodeTable.get(code);
        } else {
            return CodeTable.get(code);
        }
            
    }
}
