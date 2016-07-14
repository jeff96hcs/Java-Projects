# Java-Projects

- Run main file in Interpreter
- Components for the main Interpreter:
   ByteCodeLoader
   CodeTable
   Interpreter
   RunTimeStack
   Program
   VirtualMachine
 
- Interpreter Byte Codes:
   ArgsCode
   ByteCode (the abstract class that all other ByteCode classes extend from)
   CallCode
   BopCode
   DumpCode
   FalseBranchCode
   GotoCode
   HaltCode
   LabelCode
   LoadCode
   LitCode
   PopCode
   ReturnCode
   ReadCode
   StoreCode
   WriteCode

- Debugger Byte Codes:
   DebuggerPopCode
   DebuggerLitCode
   FormalCode
   DebuggerReturnCode
   DebuggerCallCode
   FunctionCode
   LineCode

- Additional components for Debugger:
   DebuggerByteCodeLoader
   FunctionEnvironmentRecord
   SourceCodeLoader
   SymbolTable
   DebuggerVirtualMachine
   SourceLinesAndBreakPts

- User interface
   DebuggerUI
