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
/** <pre>
* Binder objects group 3 fields
* 1. a value
* 2. the next link in the chain of symbols in the current scope
* 3. the next link of a previous Binder for the same identifier
* in a previous scope
* </pre>
*/
class Binder {
    private Object value;
    private String prevtop; // prior symbol in same scope
    private Binder tail; // prior binder for same symbol
    // restore this when closing scope
    Binder(Object v, String p, Binder t) {
        value=v; prevtop=p; tail=t;
    }
    
    Object getValue() { return value; }
    
    String getPrevtop() { return prevtop; }
    
    Binder getTail() { return tail; }
}

public class SymbolTable {
    private java.util.HashMap<String,Binder> symbols = new java.util.HashMap<String,Binder>();
    private String top; // reference to last symbol added to
    // current scope; this essentially is the
    // start of a linked list of symbols in scope
    private Binder marks; 
    // scope mark; essentially we have a stack of
    // marks - push for new scope; pop when closing
    // scope
    /*
    public static void main(String args[]) {
        Symbol s = Symbol.symbol("a", 1),
        s1 = Symbol.symbol("b", 2),
        s2 = Symbol.symbol("c", 3);
        Table t = new Table();
        t.beginScope();
        t.put(s,"top-level a");
        t.put(s1,"top-level b");
        t.beginScope();
        t.put(s2,"second-level c");
        t.put(s,"second-level a");
        t.endScope();
        t.put(s2,"top-level c");
        t.endScope();
    }
    */
    public SymbolTable(){}
    /**
    * Gets the object associated with the specified symbol in the Table.
    */
    public Object get(String key) {
        Binder e = symbols.get(key);
        return e.getValue();
    }
    /**
        * Puts the specified value into the Table, bound to the specified Symbol.<br>
        * Maintain the list of symbols in the current scope (top);<br>
        * Add to list of symbols in prior scope with the same string identifier
    */
    public void put(String key, Object value) {
        symbols.put(key, new Binder(value, top, symbols.get(key)));
        top = key;
    }
    /**
        * Remembers the current state of the Table; push new mark on mark stack
    */
    public void beginScope() {
        marks = new Binder(null,top,marks);
        top=null;
    }
    /**
        * Restores the table to what it was at the most recent beginScope
        * that has not already been ended.
    */
    public void endScope() {
        while (top!=null) {
            Binder e = symbols.get(top);
            if (e.getTail()!=null){ 
                symbols.put(top,e.getTail());
            }
            else { 
                symbols.remove(top);
            }
        top = e.getPrevtop();
        }
        top=marks.getPrevtop();
        marks=marks.getTail();
    }
    /**
        * @return a set of the Table's symbols.
    */
    public java.util.Set<String> keys() {
        return symbols.keySet();
    }

    public void removeTop(){
        Binder b = symbols.get(top);
        if(top!=null){
            symbols.remove(top);
            top = b.getPrevtop();
        }      
    }
    
    public void popValues(int numOfPops) {
	for (int i = 0; i < numOfPops; i++) {
            Binder e = symbols.get(top);
            if (e.getTail()!=null)
               symbols.put(top,e.getTail());
	   else
               symbols.remove(top);
	   top = e.getPrevtop();
        }
    }
    public HashMap<String, Binder> getSymbols(){
        return symbols;
    }
    public boolean isEmpty(){
        return symbols.isEmpty();
    }
}
