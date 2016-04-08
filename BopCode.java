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
public class BopCode extends ByteCode{
    //a String representing the operator
    private String operator;
    //the top integer that will be popped from the runStack
    private int topInt;
    //the lower/second integer that will be popped from the runStack
    private int lowerInt;
    
    public BopCode(){
        
    }
    
    @Override
    public String toString(){
        return "BOP";
    }
    
    //Setting operator equal to args
    @Override
    public void init(String args) {
        this.operator = args;
    }
    
    //popping the top two integers from the runStack and computing the result 
    //using the result method and operator and storing the value into int res
    //to add res to the runStack
    @Override
    public void execute(VirtualMachine vm) {
        topInt = vm.runStackPop();
        lowerInt = vm.runStackPop();
        int res = result(lowerInt, topInt, operator);
        vm.runStackPush(res);
        System.out.print(toString() + " " + getArgs() + " ");
    }

    @Override
    //getting the operator as Args
    public String getArgs() {
        return operator;
    }
    
    //computing the result based on the operator, for boolean operators like <, 
    //>=, and &&, it will return 1 if true and 0 if false. If String operator is
    //not a real operator, the method will return -1
    private int result(int topInt, int lowInt, String operator){
        if(operator.equals("+")){
            return topInt + lowInt;
        } else if (operator.equals("-")){
            return topInt-lowInt;
        } else if (operator.equals("*")){
            return topInt*lowInt;
        } else if (operator.equals("/")){
            return topInt/lowInt;
        } else if (operator.equals("==")){
            if(topInt==lowInt){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals("!=")){
            if(topInt!=lowInt){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals(">=")){
            if(topInt>=lowInt){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals("<=")){
            if(topInt<=lowInt){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals("<")){
            if(topInt<lowInt){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals(">")){
            if((topInt>0) && (lowInt>0)){
                return 1;
            } else {
                return 0;
            }
        } else if (operator.equals("||")){
            if((topInt>0) || (lowInt>0)){
                return 1;
            } else {
                return 0;
            }
        } else{
            return -1;
        }        
    }
}
