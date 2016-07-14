/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;
import interpreter.*;
import interpreter.ByteCodes.*;
import java.util.*;
/**
 *
 * @author Jia Hu
 */
public class DebuggerVirtualMachine extends interpreter.VirtualMachine{
    //current ByteCode
    private ByteCode currBC;
    //this was eventually not used, so ignore it
    private Stack<String> callStack;
    private boolean isSet;
    //the initial size of the environmentStack at every time the execute
    //function is called
    private int envSize;
    //this is to keep record of all source lines and current breakpoints
    private SourceLinesAndBreakPts slbp;
    //this condtains all the functions that were called on and have not been
    //exited out of yet, with the current function on top
    private Stack<FunctionEnvironmentRecord> environmentStack;
    private int FirstFuncLine;
    //whether trace is turned ON or OFF
    private boolean traceON;
    //the current step method being used
    private String step;
    //whether there has been a change of line, this will be set to true whenever
    //the LINE byteCode is reached
    private boolean lineChange;
    //the UI to ask user for input
    private String readPrompt;
    //the trace String that results whenever trace is turned on
    private String traceStr;
    //this will be used whenever we run into a function byteCode with startLine
    //greater than 0 and is not the main function, since such function byteCodes
    //would immediately be followed (a) FORMAL byteCode(s)
    private boolean contWFormals;
    //the number of formal byteCodes reached after each function byteCode
    private int formals;
    
    public DebuggerVirtualMachine(Program prg, SourceLinesAndBreakPts slbp) {
        super(prg);
        this.isRunning = true;
        this.pc =0;
        this.slbp = slbp;
        this.isRunning = true;
        this.runStack = new RunTimeStack();
        this.environmentStack = new Stack<FunctionEnvironmentRecord>();
        FunctionEnvironmentRecord main = new FunctionEnvironmentRecord();
        main.setFunc("main", 1, slbp.size());
        main.setCurrLineNo(1);
        this.environmentStack.add(main);       
        this.traceON = false;
        this.lineChange = false;
        readPrompt = "Please input an integer: ";
        this.traceStr = "";
        this.contWFormals = false;
        this.formals = 0;
    }
    
    public int getCurrLine(){
        return environmentStack.lastElement().getCurrLine();
    }
    
    public int getStartLine(){
        return environmentStack.lastElement().getStartLine();
    }
     
    public int getEndLine(){
        return environmentStack.lastElement().getEndLine();
    }
    
    public FunctionEnvironmentRecord getCurrFunc(){
        return environmentStack.lastElement();
    }
    
    public String getCurrFuncString(){
        return environmentStack.lastElement().toString(); 
    }
    
    public SourceLinesAndBreakPts getSlbp(){
        return slbp;
    }
    
    public void setTraceON(boolean trace){
        traceON = trace;
        //traceStr = "";
    }
    
    //to adjust the trace String traceStr if trace is turned on
    public void trace(boolean exit){  
        String funcName = environmentStack.peek().getFuncName();
        if(traceON && funcName!=null){
            //to help format the traceStr
            for (int space = 0; space < environmentStack.size(); space++){
                traceStr += " ";
            }
            //get the name of the function exited as well as the return value
            if(exit){
                if(!this.runStack.getLastFrame().isEmpty()){
                    Integer returnVal =this.runStack.peek();
                    traceStr += "exit: " + funcName +": " + returnVal.toString() + "\n";
                }                
            } else {
                //if entering a function the print function Name as well as
                //parameters, which are placed in the last frame of runStack
                traceStr += funcName+"(";
                for(int i = 0; i < this.runStackLastFrameSize();i++){
                    Integer entry = this.runStack.getLastFrame().get(i);
                    traceStr+=entry.toString();
                    if(i!= this.runStackLastFrameSize()-1){
                        traceStr+=", ";
                    }
                }
                traceStr+=")\n";
            }
        }
    }
        
    public void setCurrLine(int currLine){
        if(environmentStack.lastElement().getCurrLine()!=(currLine-1)){
            lineChange = true;
        }
        if(currLine>=1){
            environmentStack.lastElement().setCurrLineNo(currLine);
        }
    }
    /**
     *
     * @param prg
     */
    
    public boolean isRunning(){
        return this.isRunning;
    }
    
    public void stopRunning(boolean stop){
        this.isRunning = stop;
    }
    
    public boolean setBrkPt(int lineNo){
        //if it is okay to set the breakpoint at the given lineNo, then set
        //breakpoint and return true. Otherwise return false.
        if(isOKBkpt(slbp.get(lineNo-1))){
            slbp.setBreakpoint(lineNo-1);
            return true;
        } else {
            return false;
        }        
    }
    
    //returns whether a breakpoint is set at the given lineNo
    public boolean isBkptSet(int lineNo){
        if(lineNo>0){
            return slbp.isBkpt(lineNo);
        } else {
            return false;
        }
    }
    
    //ignore this method
    public void stop(int LineNo){
        
        if(setBrkPt(LineNo)){
            while(true){
                
            }
        }
        while(true){
            
        }
    }
    
    //setting the step method to the passed parameter step
    public void setStep(String step){
        this.step = step;
    }
    
    //checks the step condition based on the given envStackSize
    //step is set to either continue, step in, step out, or step over
    //the execution will stop whenever this method returns false
    public boolean checkStepCond(int envStackSize){
        boolean cond = false;
        switch(step){
            case "continue":
                if(!isBkptSet(getCurrLine()) /*|| envStackSize == environmentStack.size() */|| !lineChange){
                    //if (!(isBkptSet(getCurrLine()) && lineChange)){
                        cond = true;
                    //}
                }
                break;
            case "step in": 
                //this becomes false and step in stops when the number of formal
                //byteCodes counted with the variable formals is equal to the 
                //size of the last frame of runStack, which contains the variable
                //values. This is used to get all the variables passed into the 
                //function, when it's first called, instead of having the execution
                //simply stop at the function byteCode or only the first formal
                //byteCode when there is more than one formal bytecode
                if(contWFormals){
                    formals++;
                    cond = true;
                    if(formals==this.runStackLastFrameSize()){
                        contWFormals = false;
                        formals = 0;
                        cond = false;
                    }
                }
                if((!lineChange && environmentStack.size()== envStackSize)||
                        //to go into the intrinsic function
                        (currBC.toString().equals("FUNCTION") && environmentStack.peek().getStartLine() > 0)||
                        //for the step in method to get past the call byteCode and stop at a function byteCode
                        (!currBC.toString().equals("FUNCTION")&&environmentStack.peek().getFuncName()==null)){
                    cond = true;
                    //this if statement is to prevent the program from crashing 
                    //at the start due to an uncalled exception of having a null
                    //byteCode
                    if(currBC!=null){
                        //set contWFormals to true if there's a function byteCode,
                        //it's not main and its startline is greater than 0
                        if((currBC.toString().equals("FUNCTION") && environmentStack.peek().getStartLine() > 0)){
                            if(!environmentStack.peek().getFuncName().equals("main")){
                                contWFormals = true;
                                //formals++;
                            }
                        }
                    }
                }
                break;
            case "step out":
                if(environmentStack.size() >= envStackSize){
                    if (!(isBkptSet(getCurrLine()) && lineChange)){
                        cond = true;
                    }
                }
                break;
            case "step over":
                if(!lineChange || (environmentStack.size()>envStackSize)&& !(isBkptSet(getCurrLine()))){
                    cond = true;
                }
                break;
        }
        if(lineChange){
            lineChange = false;
        }
        return cond;
    }
    public void clrBkpt(int lineNo){
        slbp.clearBreakpt(lineNo-1);
    }
    
    //whether it's okay to set the breakpoint at the given SourceLine
    public boolean isOKBkpt(String SourceLine){
        if(SourceLine.contains("{")||SourceLine.contains("int")
            ||SourceLine.contains("if")||SourceLine.contains("while")
            ||SourceLine.contains("return")||SourceLine.contains("=")){
            return true;
        } else {
            return false;
        }
    }
    
    public void stepOver(){
        
    }
    
    public void stepInto(String func){
        
    }
    
    public void setFuncTracing(String func){
        
    }
    
    //prints all the called functions and their current lines
    public void printCallStack(){
        System.out.println();
        System.out.println("Function(s):");
        for(int i = environmentStack.size()-1; i>=0; i--){
            System.out.println(environmentStack.elementAt(i).getFuncName()+": "+(environmentStack.elementAt(i).getCurrLine()+1));
        }
    }
    
    public void printLocalVars(String func){
        
    }
    
    public void changeVariableValues(Vector<String> vars){
        
    }
    
    public void watchVariableValues(String var){
        
    }
    
    //get the value of the variable var
    public int getVarVal(String var){
        int offset = environmentStack.lastElement().getOffset(var);
        return this.getRunStackLastFrame().get(offset);        
    }
  
    public int evalExpr(int first, int second, String operator){
        int result = 0;
        return result;
    }
    
    public void HaltExecution(){
        
    }
    
    //add var with offset to environmentStack
    public void addVar(String var, int offset){
        FunctionEnvironmentRecord fer = environmentStack.pop();
        fer.enter(var, offset);
        environmentStack.add(fer);
    }
    
    //pop the top No vars of the current function
    public void popVars(int No){
        FunctionEnvironmentRecord fer = environmentStack.pop();
        fer.pop(No);
        environmentStack.add(fer);
    }
    
    //add a function and adjust the traceStr accordingly
    public void addFunc(FunctionEnvironmentRecord fer){
        environmentStack.add(fer);
        trace(false);
    }
    
    //pop a function and adjust the traceStr accordingly
    public FunctionEnvironmentRecord popFunc(){
        //trace if the current byteCode is not FUNCTION or else it will be 
        //wrongly formatted
        if(!currBC.toString().equals("FUNCTION")){
            trace(true);
        }
        return environmentStack.pop();        
    }
    
    public void setReadPrompt(String prompt) {
        readPrompt = prompt;
    }
    
    //display current variables
    public void displayCurrVars(){
        System.out.println("Function: " + getCurrFunc());
        
        for(String key: getCurrFunc().getST().keys()){
            System.out.println(key + ": " + getVarVal(key));
        }
        if(getCurrFunc().getST().isEmpty()){
            System.out.println("There are no variables at the moment");
        }
    }
    public void displayVars(){
        
    }
    
    //execute whenever a step command is typed in the DebuggerUI
    public void execute(){
        //set envStackSize to size of environmentStack the first time
        //execute is called
        int envStackSize = environmentStack.size();
        while (checkStepCond(envStackSize) && isRunning) {

            currBC = program.getCode(pc);
            if (currBC.toString().equals("READ"))
                System.out.print(readPrompt);

            currBC.execute(this);
            pc++;
        }
        step = null;
        if (traceON){
            System.out.println(traceStr);
        }
    }
}
