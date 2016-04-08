/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author jzhang2
 */
import interpreter.ByteCodes.*;
import java.util.*;
public class CodeTable extends Object{
    //HashMap containing byteCodes, in which the both keys and values are
    //Strings
    public static java.util.HashMap<String, String> byteCodes = new java.util.HashMap<String, String>();
    
    //Returning the value of the key code in byteCodes
    public static String get(String code){
        return byteCodes.get(code).toString();
    }
    
    //initializing the byteCode as keys and byteCode classes as values
    public static void init(){
        byteCodes.put("ARGS","ArgsCode");
        byteCodes.put("BOP","BopCode");
        byteCodes.put("CALL","CallCode");
        byteCodes.put("ARGS","ArgsCode");
        byteCodes.put("FALSEBRANCH","FalseBranchCode");
        byteCodes.put("GOTO","GotoCode");
        byteCodes.put("HALT","HaltCode");
        byteCodes.put("LABEL","LabelCode");
        byteCodes.put("LIT","LitCode");
        byteCodes.put("LOAD","LoadCode");
        byteCodes.put("POP","PopCode");
        byteCodes.put("READ","ReadCode");
        byteCodes.put("RETURN","ReturnCode");
        byteCodes.put("STORE","StoreCode");
        byteCodes.put("WRITE", "WriteCode");
        byteCodes.put("DUMP", "DumpCode");
    }
    
}
