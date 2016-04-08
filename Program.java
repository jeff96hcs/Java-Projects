/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.ByteCodes.*;
import java.util.*;
/**
 *
 * @author jzhang2
 */
public class Program {
    //creating an ArrayList of ByteCodes called byteCodes to contain the byte 
    //codes of the text file
    private ArrayList<ByteCode> byteCodes = new ArrayList<ByteCode>();
    //creating an ArrayList of Strings called args containing the arguments for
    //each byte code
    private ArrayList<String> args = new ArrayList<String>();
    
    public Program(){
        
    }
    
    //returns byteCodes
    public ArrayList<ByteCode> getByteCodes(){
        return byteCodes;
    }
    
    //returns args
    public ArrayList<String> getArgs(){
        return args;
    }
    
    //adding the byte code to byteCodes
    public void add(ByteCode code){
        byteCodes.add(code);
    }
    
    //adding the corresponding argument to args
    public void addArgs(String arg){
        args.add(arg);
    }
    
    //returning the code at line entry
    public ByteCode getCode(int entry){
        return byteCodes.get(entry);
    }
    
    public void resolveAddress(){     
        //making a temporary copy of args called tmpArgs
        ArrayList<String> tmpArgs = args;
        //for every entry in byteCode
        for(int i = 0; i < byteCodes.size(); i++){
            //bcString to get the String of the current byte code
            String bcString = byteCodes.get(i).toString();
            //if bcString equals FALSEBRANCH, GOTO, or CALL, then 
            if(bcString == "FALSEBRANCH"||bcString == "GOTO"||bcString == "CALL"){
                Integer j;
                //targetArgs, the matching String argument we want to search for
                String targetArgs = tmpArgs.get(i);
                //go down the tmpArgs to find which LABEL has the same argument 
                //and replace the argument with the entry number
                for(j = i + 1; j < byteCodes.size();j++){
                    if((tmpArgs.get(j).equals(targetArgs))&&(byteCodes.get(j).toString().equals("LABEL"))){
                        args.set(i, j.toString());
                        break;
                    }
                }
                //if we went past the last entry of byteCodes, in which the
                //corresponding LABEL bytecode and address aren't found, then
                //look at the entries above targetArgs
                if(j == byteCodes.size()){
                    for(j = i-1; j>=0; j--){
                        if((tmpArgs.get(j).equals(targetArgs))&&(byteCodes.get(j).toString().equals("LABEL"))){
                            args.set(i, j.toString());
                            break;
                        }
                    }
                }  
                
            }
        }
    }
}
