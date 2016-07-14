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
public class SourceLinesAndBreakPts {
    private Vector<String> entries;
    private Vector<Boolean> isBreakPtSet;
    //a class to contain the sourceLines and current breakpoints
    public SourceLinesAndBreakPts(Vector<String> SourceLines){
        this.entries = SourceLines;
        isBreakPtSet = new Vector<Boolean>();
        for(int i = 0; i < SourceLines.size(); i++){
            isBreakPtSet.add(new Boolean(false));
        }
        
    }
    
    public String get(int i){
        return entries.get(i);
    }
    public int size(){
        return entries.size();
    }
    public void setBreakpoint(int LineNo){
        isBreakPtSet.set(LineNo, true);
    }
    
    public boolean isBkpt(int LineNo){
        return isBreakPtSet.get(LineNo);
    }
    public void listCurrentBreakptSettings(){
        
    }
    
    public void clearBreakpt(int LineNo){
        isBreakPtSet.set(LineNo, Boolean.FALSE);
    }
    
    public void printSourceLines(){
        for(int i = 0; i<entries.size();i++){
            if(isBreakPtSet.get(i)){
                System.out.print("*");
            }
            System.out.println((i+1)+". "+entries.get(i));
        }
    }
    
    public Vector<Integer> getBreakPts(){
        Vector<Integer> BP = new Vector<Integer>();
        for(int i = 0; i < isBreakPtSet.size(); i++){
            if(isBreakPtSet.get(i)){
                BP.add(new Integer(i+1));
            }
        }
        return BP;
    } 
}
