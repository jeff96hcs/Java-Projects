/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

/**
 *
 * @author Jia Hu
 */
import interpreter.*;
public abstract class ByteCode {
    
    //converting the codeClass to its String value, which is the same name
    //capitalized, but with the substring "Code" removed (ie BopCode's String 
    //value will be "BOP"
    public abstract String toString();
    
    //initiating the CodeClass with the parameter args. Some CodeClasses that do
    //not accept arguments will have args set to "", and the method body will be
    //empty
    public abstract void init(String args);
    
    //executing the CodeClass using vm
    public abstract void execute(VirtualMachine vm);
    
    //getting the argument(s) of the ByteCode
    public abstract String getArgs();
}
