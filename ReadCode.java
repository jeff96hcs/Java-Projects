/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;
import java.util.*;
/**
 *
 * @author jzhang2
 */
public class ReadCode extends ByteCode{
    //using this for input
    private Scanner input = new Scanner(System.in);
    
    public ReadCode(){
        
    }
    
    @Override
    public String toString(){
        return "READ";
    }
    @Override
    public void init(String args) {}

    @Override
    public void execute(VirtualMachine vm) {
        //asks for int input, which will be parsed to int In and pushed to 
        //runStack
        //System.out.print(toString() + " " + getArgs() + " ");
        String in = input.nextLine();
        int In = Integer.parseInt(in);
        vm.runStackPush(In);
        if(vm.getRunStack().dumpON){
            System.out.print(toString() + " " + getArgs() + " ");
            vm.getRunStack().dump();
        }
    }

    @Override
    public String getArgs() {
        return "";
    }
}
