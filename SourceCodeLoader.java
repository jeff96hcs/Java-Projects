/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter.debugger;

/**
 *
 * @author Jia Hu
 */
import java.util.*;
import java.io.*;

public class SourceCodeLoader {
    private SourceCodeLoader(){
        
    }
    public static SourceLinesAndBreakPts load(String codeFile) throws FileNotFoundException, IOException {

        BufferedReader code = new BufferedReader(new FileReader(codeFile));
        Vector<String> sourceCode = new Vector<String>();
        SourceLinesAndBreakPts slbp;
        while (code.ready()){
            sourceCode.add(code.readLine());
        }
        slbp = new SourceLinesAndBreakPts(sourceCode);
        return slbp;
    }
}
