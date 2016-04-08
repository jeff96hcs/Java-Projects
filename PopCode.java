/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.ByteCodes;

import interpreter.*;

/**
 *
 * @author jzhang2
 */
public class PopCode extends ByteCode{
    //Integer indicating # of entries to pop
    private Integer lvlsToPop;
    
    public PopCode(){
        
    }
    
    @Override
    public String toString(){        
        return "POP";
    }
    
    @Override
    //parsing args to int and passing it to lvlsToPop
    public void init(String args) {
        lvlsToPop= Integer.parseInt(args);
    }

    @Override
    public void execute(VirtualMachine vm) {
        //pop i entries
        for(int i=0; i<lvlsToPop; i++){
            //if the current frame is empty then pop that frame and keep going
            if(vm.getRunStackLastFrame().isEmpty()){
                vm.getRunStackFramePointers().pop();
            }
            vm.runStackPop();
        }
        System.out.print(toString() + " " + getArgs() + " ");
    }

    @Override
    //convert lvlsToPop to String as args
    public String getArgs() {
        return lvlsToPop.toString();
    }
}
