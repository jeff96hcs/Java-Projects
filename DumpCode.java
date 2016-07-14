/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;

/**
 *
 * @author Jia Hu
 */
public class DumpCode extends ByteCode {
    //whether or not to dump
    private boolean dump;
    //argument determining whether or not to dump. This can be set to either ON
    //or OFF
    private String dumpOrNo;
    public DumpCode(){
        
    }
    
    @Override
    public String toString(){
        return "DUMP";
    }
    
    @Override
    //if args is equal to ON, when capitalized, then dump is true, else if args
    //is equal to OFF, when capitalized, then dump is false, and setting 
    //dumpOrNo to args
    public void init(String args) {
        if(args.toUpperCase().contains("ON")){
            dump = true;
        } else if (args.toUpperCase().contains("OFF")){
            dump = false;
        }
        this.dumpOrNo = args;
    }

    @Override
    //letting vm runStack dump based on boolean value dump
    public void execute(VirtualMachine vm) {
        vm.getRunStack().setDumpON(dump); 
        if(!dump){
            System.out.println();
        }
    }

    //returning dumpOrNo as args
    @Override
    public String getArgs() {
        return dumpOrNo;
    }
}
