/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;
import java.util.*;
/**
 *
 * @author Jia Hu
 */
public class FunctionEnvironmentRecord {
    private SymbolTable st;
    private String funcName;
    private int startLine, endLine, currLine;
    //a vector of String called args to contain the function Name (funcName), 
    //start line (startLine), end line (endLine) and current line (currLine)
    //respectively
    Vector<String> args = new Vector<String>(4);
    
    public FunctionEnvironmentRecord(){
        //initialize the FunctionEnvironmentRecord with an empty SymbolTable
        //and each entry of args set to "-"
        st = new SymbolTable();       
        for(int i = 0; i<4; i++){
            args.add("-");
        }  
        st.beginScope();
    }
    
    //set the function name to func, start line to start, and end line to end
    public void setFunc(String func, Integer start, Integer end){
        this.funcName = func;
        setStartLine(start-1);
        setEndLine(end);
        args.set(0, func);
        args.set(1, start.toString());
        args.set(2, end.toString());
    }
    
    public int getStartLine(){
        return startLine;
    }
    
    public int getEndLine(){
        return endLine;
    }
    public int getCurrLine(){
        return currLine;
    }
    
    public String getFuncName(){
        return funcName;
    }
    public void setFuncName(String name){
        this.funcName = name;                
    }
    
    //setting current Line to the value 1 less than line, to provide clarity for
    //the user
    public void setCurrLineNo(Integer line){
        currLine = line-1;
        args.set(3, line.toString());
    }
    
    public void setStartLine(int line){
        startLine = line;
    }
    
    public void setEndLine(int line){
        endLine = line;
    }
    
    //enter a key and its value and put it into SymbolTable st
    public void enter(String key, int value){
        st.put(key, value);
    }
    
    //pop the top NoOfTimes levels of st
    public void pop(int NoOfTimes){        
        st.popValues(NoOfTimes);
    }
    
    //change variable value/offset
    public void setVarVal(String var, Binder val){
        st.getSymbols().put(var, val);
    }
    
    //the function will be displayed in a 
    //"( <st keys/values(or offsets)>, funcName, startLine, endLine, currLine)" format
    public String toString(){
        int i = 0;
        String rec = "";
        rec+="( <";
        for(String key: st.keys()){
            if(i!=0){
                rec+=",";
            }
            rec+=key+"/"+st.get(key);            
            i++;
        }
        rec+=">";
        for(i = 0; i<args.size(); i++){
            rec+= ", "+args.get(i);
        }
        rec+=")";
        return rec;
    }
    
    public int getOffset(String var){
        return (Integer)st.get(var);
    }
    
    public SymbolTable getST(){
        return st;
    }
    
    //prints the toString representation of FunctionEnvironmentRecord
    public void dump(){
        System.out.println(toString());
    }
    
    void runCommand(String command){
        String[] entries = command.split(" ");
        String code = entries[0];
        switch (code){
            case "BS":
                //STBeginScope();
                break;
            case "Function":
                setFunc(entries[1], Integer.parseInt(entries[2]), Integer.parseInt(entries[3]));
                break;
            case "Line":
                setCurrLineNo(Integer.parseInt(entries[1]));
                break;
            case "Enter":
                enter(entries[1], Integer.parseInt(entries[2]));
                break;
            case "Pop":
                pop(Integer.parseInt(entries[1]));
                break;
        }
        dump();
    }
    public static void main(String[] args){
        //Scanner sc = new Scanner(System.in);
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord();
        String[] commands = {"BS", "Function g 1 20", "Line 5", "Enter a 4", "Enter b 2", "Enter c 7", "Enter a 1", "Pop 2", "Pop 1"};
        for(int i = 0; i < commands.length; i++){
            System.out.println(commands[i]);
            fctEnvRecord.runCommand(commands[i]);
        }
    }
}
