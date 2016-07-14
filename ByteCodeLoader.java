/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author jzhang2
 */
import java.util.*;
import java.io.*;
import interpreter.ByteCodes.*;

public class ByteCodeLoader {    
    BufferedReader source;
    String codeClass;
    public ByteCodeLoader(String codeFile) throws IOException{
        //the BufferedReader source is used to read from the text codeFile
        source = new BufferedReader(new FileReader(codeFile));
    }
    
    protected String getCodeClass(String code){
        codeClass = CodeTable.get(code);
        return codeClass;
    }
    
    public Program loadCodes(){
        //creating a new program
        Program prg = new Program();
        //initializing the string code, otherwise it will cause an error, when
        //passed into the while loop argument
        String code = "1";
        //while code does not equal to null
        while(!code.equals(null)){            
            try{
                //String fullCode will read the code from the next line of the
                //text file
                String fullCode = source.readLine();
                //if fullCode is null, then break. Otherwise an exception will 
                //be thrown and caught
                if(fullCode == null){
                    break;
                }
                //Else, fullCode is split by space and the values are placed in an
                //array called entries
                String[] entries=fullCode.split(" ");
                //The String code will equal to the first entry of entries, 
                //which is a byteCode
                code = entries[0];
                //codeClass to get the code class of code, according to the
                //byteCodes HashMap in CodeTable. An exception will be thrown if
                //none of the keys in byteCodes match code.
                codeClass = getCodeClass(code);
                //creating a String called args. This must be initialized as well.
                //Otherwise it will cause an error upon use
                String args = "";                
                //args will be set equal to the remaining entries, if the length
                //of the array entries is greater than 1.
                if(entries.length>1){
                    for(int i =1; i<entries.length; i++){
                        args += entries[i];
                        //while the last argument is not reached, add a space. If
                        //the a space is included with the last argument, it may
                        //cause errors, especially when the entry is an int
                        if(i!=entries.length-1){
                            args+=" ";
                        }
                    }                    
                }
                //creating a new instance for the current byteCode and adding
                //the bytecode to the ArrayList byteCodes and the argument args 
                //to ArrayList args of program prg, and initializing the 
                //byteCode
                ByteCode bytecode = (ByteCode)(Class.forName("interpreter.ByteCodes."+codeClass).newInstance());
                prg.add(bytecode);
                prg.addArgs(args);
                bytecode.init(args);
            } catch (Exception e){
                //Just to indicate that the exception is reached and with which
                //code
                System.out.println("Class not found!..." + code);
            }            
        }        
        return prg;
    }
}
