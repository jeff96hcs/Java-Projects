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

public class RunTimeStack {
    //initializing framePointers as a stack of integers containing the first
    //value of each frame in runStack
    private Stack<Integer> framePointers = new Stack<Integer>();
    //initializing runStack as a Vector of Vectors of Integers, with each Vector
    //of Integers being a frame
    private Vector<Vector<Integer>> runStack = new Vector<Vector<Integer>>();    
    //whether or not to dump
    private boolean dumpON = false;
    
    //constructor; framePointers is set with pushing an initial value of 0.
    //creating a Vector of Integers called mainFrame that has an initial value
    //of 0, and the Vector is added into the runStack
    public RunTimeStack(){
        framePointers.push(0);
        Vector<Integer> mainFrame = new Vector<Integer>();
        mainFrame.add(0);
        runStack.add(mainFrame);
    }    
    
    public void dump(){    
        //if dumpON is true or DUMP is turned on
        if(dumpON){
            //for every frame in runStack
            for(int i = 0; i < runStack.size(); i++){
                System.out.print("[");
                //print all entries in the current frame
                for(int j = 0; j < runStack.get(i).size(); j++){
                    System.out.print(runStack.get(i).get(j));
                    if(j!=(runStack.get(i).size()-1)){
                        System.out.print(", ");
                    }
                }
                System.out.print("] ");
            }
        }    
        System.out.println();    
    }
    
    //setting dumpON to the boolean value of the parameter dumpON
    public void setDumpON(boolean dumpON){
        this.dumpON = dumpON;
    }
    //returning the last element, which is an int, of the most current Vector of
    //Integers in runStack
    public int peek(){
        return runStack.lastElement().lastElement();
    }
    
    //returning and removing the last element, which is an int, of the most
    //current Vector of Integers in runStack.
    public int pop(){
        int last = runStack.lastElement().lastElement();
        runStack.lastElement().removeElementAt(runStack.lastElement().size()-1);
        return last;
    }
    
    //adding int i to the back of the most current Vector of Integers in
    //runStack
    public int push(int i){
        runStack.lastElement().add(i);
        return i;
    }
    
    public void newFrameAt(int offset){
        //creating a temporary vector called tmpVec
        Vector<Integer> tmpVec = new Vector<Integer>();
        if(offset!=0){
            //if offset is not equal to 0, then the int values starting from the
            //one offset positions down from the top will be removed and added
            //to tmpVec
            int startEnt = runStack.lastElement().size()-offset;
            for(int i = startEnt; i < runStack.lastElement().size(); i++){
                int tmpInt = runStack.lastElement().remove(i);
                tmpVec.add(tmpInt);
            }
        } else {
            //if offset equals 0, then only 0 will be added to tmpVec
            tmpVec.add(0);
        }        
        //tmpVec is then added to runStack
        runStack.add(tmpVec);
        //the first entry of the most current frame will then be added to the
        //framePointers
        framePointers.push(tmpVec.firstElement());
    }
    
    //storing the top value of the most current Vector in tmpInt before removing
    //the frame and adding tmpInt to the top of runStack. The top value of 
    //framePointers is popped as well.
    public void popFrame(){
        int tmpInt = peek();
        runStack.remove(runStack.size()-1);
        runStack.lastElement().add(tmpInt);
        framePointers.pop();
    }
    
    //popping the top value in the most current Vector and storing it in tmpInt,
    //and replacing the value at position offset with tmpInt beforing returning
    //tmpInt.
    public int store(int offset){        
        int tmpInt = pop();
        runStack.lastElement().set(offset, tmpInt);
        return tmpInt;
    }
    
    //adding the value at position offset to the back of runStack
    public int load(int offset){
        return push(runStack.lastElement().get(offset));
    }   
    
    //getting size of runStack. This was used for the purpose of testing the
    //runStack class
    public int size(){
        return runStack.size();
    }
    
    //getting Vector at position i of runStack. This was used for the purpose of
    //testing the runStack class
    public Vector<Integer> get(int i){
        return runStack.get(i);
    }
    
    //getting the last frame of runStack
    public Vector<Integer> getLastFrame(){
        return runStack.lastElement();
    }
    
    //getting the Vector<Vector<Integer>> runStack
    public Vector<Vector<Integer>> getrunStack(){
        return runStack;
    }
    
    //getting framePointers
    public Stack<Integer> getFramePointers(){
        return framePointers;
    }
}
