/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import interpreter.debugger.*;
import interpreter.debugger.ui.DebuggerUI;
import java.io.*;
import java.util.*;

/**
 *
 * @author Jia Hu
 */
public class Interpreter {
    
    public static boolean debugMode;
    private SourceLinesAndBreakPts sourceCode;
    static String codeFile;
    /**
     * @param args the command line arguments
     */
    ByteCodeLoader bcl;
        //debugMode is to declare whether or not we want to implement the 
        //Interpreter in DebugMode
	public Interpreter(String codeFile, boolean debug) {
            debugMode = debug;
            try {
                //use the .x.cod file for interpretation since it contains the 
                //byteCodes
                this.codeFile= codeFile+".x.cod";
                CodeTable.init();
                //if in debugMode then use the .x file as SourceFile and for UI
                //use the DebuggerByteCodeLoader, so the Debugger ByteCodes could
                //be loaded as well
                if(debug){
                    String SourceFile = codeFile + ".x";
                    bcl = new DebuggerByteCodeLoader(this.codeFile);
                    sourceCode = SourceCodeLoader.load(SourceFile);
                } else {
                    bcl = new ByteCodeLoader(this.codeFile);                    
                }
                
            } catch (IOException e) {
		System.out.println("**** " + e);
            }
	}

	void run() {
            Program program = bcl.loadCodes();
            VirtualMachine vm;
            //If in debugMode use the DebuggerVM instead when running
            if(debugMode){
                vm = new DebuggerVirtualMachine(program, sourceCode);
                DebuggerUI.displayUI((DebuggerVirtualMachine)vm);
            } else{ 
		vm = new VirtualMachine(program);
		vm.executeProgram();
            }
	}

	public static void main(String args[]) {
		/*if (args.length == 0) {
			System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
			System.exit(1);
		} */               
                
                Interpreter i = new Interpreter("test", true);
                i.run();
                //Only type in the program name, not the full program path
                /*if(args[0].equals("-d")){
                    i = new Interpreter(args[1],true);  
                    i.run();
                } else {
                    i = new Interpreter(args[0],false);                
                    i.run();
                }*/
	}
    
}
