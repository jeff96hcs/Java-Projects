/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger.ui;

/**
 *
 * @author Jia Hu
 */
import java.util.*;
import interpreter.debugger.*;
public class DebuggerUI {
    private static DebuggerVirtualMachine dvm;
    private static boolean exit;
    
    private DebuggerUI(){
        
    }
    
    public static void displayUI(DebuggerVirtualMachine d){
        exit = false;
        dvm = d;
        Scanner sc = new Scanner(System.in);
        while(!exit && d.isRunning()){
            System.out.println("Please enter a command");
            System.out.println("Else, type '?' for help");
            System.out.print(">> ");
            String input = sc.nextLine();
            exec(input);      
        }
        System.out.println("Execution Terminated!");
    }
    
    //executes the command user types in
    public static void exec(String command){
        String[] args = command.split(" ");
        String MainComm = args[0];
        String Line = "";
        for(int i = 1; i<args.length;i++){
            Line+=(args[i]+" ");
        }
        switch(MainComm){
            case "?":
                help();
                break;
            case "cont":
                cont();
                break;
            case "StpOvr":
                stepOver();
                break;
            case "StpOut":
                stepOut();
                break;
            case "StpIn":
                stepIn();
                break;
            case "brk":
                setBrkPt(Line);
                break;
            case "clr":
                clrBrkPt(Line);
                break;
            case "brkLst":
                displayBrkPts();
                break;
            case "func":
                displayCurrFunc();
                break;
            case "vars":
                displayVariables();
                break;
            case "trace":
                if(args[1].equals("ON")){
                    dvm.setTraceON(true);
                    System.out.println("Trace set ON!");
                } else {
                    dvm.setTraceON(false);
                    System.out.println("Trace set OFF!");
                }   
                break;
            case "calls":
                dvm.printCallStack();
                break;
            case "quit":
                quit();
                break;
            default:
                System.out.println("Invalid command!");
                break;
        }
    }
    
    //lists the available commands and functions for user to use
    static void help(){
        System.out.println("Command List: ");
        String format = "%1$-15s %2$s \n";
        String commands = String.format(format, "?", "Lists commands and their use")
                + String.format(format, "cont", "Continues execution of the program until a breakpoint is reached")
                + String.format(format, "StpOvr", "Steps over current line")
                + String.format(format, "StpOut", "Step out of the current function")
                + String.format(format, "StpIn", "Step into the function on the current line. Step over current line if there's no function.")
                + String.format(format, "brk N", "Sets a breakpoint at the N-th line of the source code; accepts multiple line numbers")
                + String.format(format, "clr N", "Clears the breakpoint at the N-th line of the source code; accepts multiple line numbers")
                + String.format(format, "brkLst", "Displays a list of the current breakpoint locations")
                + String.format(format, "func", "Displays the source code for the current function")
                + String.format(format, "vars", "Displays a list of the current variables in the program")
                + String.format(format, "trace ON/OFF", "Sets whether or not to trace function calls whenever a step/continue is executed")
                + String.format(format, "calls", "Prints the call stack")
                + String.format(format, "quit", "Quits execution and exits the debugger");        
        System.out.println(commands);        
    }
    
    //perform the step method, execute, then display function
    static void performStep(String step){
        dvm.setStep(step);
        dvm.execute();
        displayCurrFunc();
    }
    static void cont(){
        performStep("continue");
    }
    
    static void stepIn(){
        performStep("step in");
    }
    
    static void stepOut(){
        performStep("step out");
    }
    
    static void stepOver(){
        performStep("step over");
    }
    static void setBrkPt(String args){
        String output = "Breakpoint(s) set: ";
        String[] nos = args.split(" ");
        boolean allInval = true;
        for(int i = 0; i<nos.length; i++){
            Integer no = Integer.parseInt(nos[i]);
            dvm.setBrkPt(no);
            if(dvm.setBrkPt(no)){
                allInval = false;
                output+=(no.toString()+ " ");
            }
        }
        if(allInval){
            output = "Invalid Breakpoint(s)";
        }
        System.out.println(output);
    }
    static void displayCurrFunc(){
        if(dvm.getStartLine()>=0){
        System.out.println(dvm.getCurrFuncString());
        int start = dvm.getStartLine();
        int end = dvm.getEndLine();
        for(int i = start; i<=end-1; i++){
            if(dvm.isBkptSet(i)){
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            String tens = new Integer((i+1)/10).toString();
            String ones = new Integer((i+1)%10).toString();
            System.out.print(tens+ones+" "+dvm.getSlbp().get(i));
            if(i==dvm.getCurrLine()){
                System.out.print("<----");                
            }
            System.out.println();
        }
        }
    }
    
    static void displayVariables(){
        dvm.displayCurrVars();
    }
    
    static void displayBrkPts(){
        Vector<Integer> BrkPts = dvm.getSlbp().getBreakPts();
        System.out.print("Breakpoints: ");
        for(int i = 0; i<BrkPts.size(); i++){
            System.out.print(BrkPts.get(i).toString() + " ");
        }
        System.out.println();
    }
    static void clrBrkPt(String args){
        String output = "Cleared Breakpoint(s): ";
        String[] nos = args.split(" ");
        for(int i = 0; i<nos.length; i++){
            Integer no = Integer.parseInt(nos[i]);
            dvm.clrBkpt(no);
            output+=(no.toString()+" ");
        }
        System.out.println(output);
    }
    
    static void quit(){
        dvm.stopRunning(true);
        exit = true;
    }
}
    

