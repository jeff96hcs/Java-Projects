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
import interpreter.ByteCodes.*;
public class VirtualMachine {
    private Program program;
    private int pc;
    private boolean isRunning;
    private RunTimeStack runStack;
    private Stack<Integer> returnAddrs;
    //to check whether address is resolved
    private boolean addressResolved = false;
    //a map with the String variable name as the key and the integer value as 
    //the value
    private Map<String, Integer> VarsAndInts = new HashMap<String, Integer>();
    
    public VirtualMachine(Program prg){
        this.program = prg;
        Integer PC = new Integer(pc);
    }
    
    //getting the program for the vm
    public Program getProgram(){
        return program;
    }
    
    //getting the pc (program counter) for the vm
    public int getPC(){
        return pc;
    }
    
    //getting the runStack
    public RunTimeStack getRunStack(){
        return runStack;
    }
    
    //getting the VarsAndInts of the vm
    public Map<String, Integer> getVarsAndInts(){
        return VarsAndInts;
    }
    
    //getting whether the address is resolved
    public boolean getAddressResolved(){
        return addressResolved;
    }   
    
    //pushing the pc to the returnAddrs
    public int pushReturnAddr(){
        return returnAddrs.push(pc);
    }
    
    //popping the top value of returnAddrs
    public int popReturnAddr(){
        return returnAddrs.pop();
    }
    
    //changing the value of pc
    public void setPC(int pc){
        this.pc = pc;
    }
    
    //setting the boolean value of addressResolved to AR
    public void setAddressResolved(boolean AR){
        this.addressResolved = AR;
    }    
    
    //calling the runStack method NewFrameAt with argument offset
    public void runStackNewFrameAt(int offset){
        runStack.newFrameAt(offset);
    }
    
    //calling the runStack method getLastFrame
    public Vector<Integer> getRunStackLastFrame(){
        return runStack.getLastFrame();
    }
    
    //getting the Vector<Vector<Integer>> runStack of runStack
    public Vector<Vector<Integer>> getRunStackRunStack(){
        return runStack.getrunStack();
    }
    
    //calling the runStack method store with int i as argument
    public int runStackStore(int i){
        return runStack.store(i);
    }
    
    //getting the Stack<Integer> framePointers of runStack
    public Stack<Integer> getRunStackFramePointers(){
        return runStack.getFramePointers();
    }
    
    //popping the most current frame of runStack
    public void runStackPopFrame(){
        runStack.popFrame();
    }
    
    //calling the runStack method peek
    public int runStackPeek(){
        return runStack.peek();
    }
    
    //sets isRunning to false
    public void stopRunning(){
        isRunning = false;
    }
    
    //popping and returning the last integer of runStack
    public int runStackPop(){
        return runStack.pop();
    }
    
    //returning and adding int i to the back of runStack
    public int runStackPush(int i){
        return runStack.push(i);
    }
    
    //calling the runStack method load with int i as argument
    public int runStackLoad(int i){
        return runStack.load(i);
    }
    
    
    public void executeProgram(){
        pc = 0;
        runStack = new RunTimeStack();
        returnAddrs = new Stack();
        isRunning = true;
        while (isRunning) {
            ByteCode code = program.getCode(pc);
            code.execute(this);
            runStack.dump();// check that the operation is correct            
            pc++;
        }
    }
}
