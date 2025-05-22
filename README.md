# MiniJava Compiler Project

This project implements a compiler for **MiniJava**, including a **MiniC** subset, using **JavaCC** for parsing and the **Visitor pattern** for compiler phases. The compiler transforms MiniJava source code through multiple stages such as syntax tree construction, pretty printing, type checking, and x86-64 assembly code generation.

---

## 🚀 Features

- Parses MiniJava and MiniC programs using JavaCC
- Builds Abstract Syntax Trees (AST)
- Pretty prints MiniJava source code
- Converts C code to equivalent Python code
- Generates x86-64 assembly code
- Constructs symbol tables for scope and type tracking
- Performs type checking for type safety

---

## 🧩 Components

### 🔹 Parser (`Compiler.jj`)
- Tokenizes MiniJava/MiniC syntax
- Defines grammar rules for expressions, statements, and method declarations
- Builds the AST representing the program structure
- Generate the following files that compile and parse the program
    - `Compiler.java`
    - `CompilerConstants.java`
    - `CompilerTokenManager.java`
    - `ParseException.java`
    - `SimpleCharStream.java`
    - `Token.java`
    - `TokenMgrError.java`

### 🔹 Visitor Implementations

#### `AST_Visitor`
- Traverses the AST and prints its structure with indentation
- Displays node class names, hierarchy, and literal values

```
public Object visit(Program node, Object data){
    System.out.println(indentString() + getClassName(node));
    ++indent;
    node.m.accept(this,data);
    if (node.c!=null){
        node.c.accept(this,data);
    }
    --indent;
    return data;
}
```

#### `PP_Visitor` ([Demo](https://drive.google.com/file/d/1LjILeJNdkWzfsiyBsOJMKCORt2FKVhcs/view?usp=sharing))
- Pretty prints MiniJava programs with proper formatting

#### `C2Python_Visitor` ([Demo](https://drive.google.com/file/d/1C8qZZRFb8yVHpoZQxQU4kxo66bPFApuD/view?usp=sharing))
- Converts C code to Python, handling:
  - Function declarations
  - Control structures (if, while)
  - Expressions and type conversions

```
public Object visit(MethodDecl node, Object data){
    int indent = (int) data; 
    String id = (String) node.i.accept(this,indent);
    String formalList = "";
    if (node.f!=null){
        formalList = (String) node.f.accept(this,indent);
    }
    // ...
    return indentString(indent) + "def " + id + "(" + formalList + "):\n" + 
           statementList + indentString(indent+1) + "return " + expr;
} 
```

#### `CodeGen_Visitor_Tiara` ([Demo](https://drive.google.com/file/d/13UVmgpkDLtHj26oEupYr06ojTMbgzRM6/view?usp=sharing))
- Generates x86-64 assembly code
- Manages:
  - Register allocation
  - Stack frame setup
  - Procedure calls
  - Array access
  - Control flow

```
public Object visit(Print node, Object data){ 
    Exp e=node.e;
    String expCode = (String) node.e.accept(this, data);

    String result = 
        expCode
    + "# "+node.accept(ppVisitor, 0)
    +   "popq %rdi\n"
    +   "movb	$0, %al\n"  
    +   "callq print\n";

    return result;
}
```

#### `SymbolTableVisitor` ([Demo](https://drive.google.com/file/d/1YznmKnjb_gPLYhYn3BappxFaodxl2HeJ/view?usp=sharing))
- Builds symbol tables for:
  - Variable declarations
  - Method signatures
  - Class scopes and types

#### `TypeCheckingVisitor` ([Demo](https://drive.google.com/file/d/1DRvRE77O6nA2pUecgWgNDv4Ltyo0njyu/view?usp=sharing))
- Validates type correctness throughout the AST

---

## 🧪 MiniJava Language Support

### MiniJava (Full Subset)
- Classes with methods and instance variables
- Integer, boolean, and array types
- If/else and while control structures
- Binary operations: `+`, `-`, `*`, `<`, `&&`
- Method calls

### MiniC (Restricted Subset)
- No classes or instance variables
- Only method declarations
- Integer and boolean types
- Limited expressions: `<`, `+`, `-`, `*`, literals, and identifiers
- Statements: assignment, `if`, and `print`

---

## 🛠️ Build & Run

The project includes several build scripts:

#### `build.sh`: Compiles the Java files and runs a demo
#### `clean.sh`: Cleans generated files and preserves visitor implementations

## 🔧 Testing

The `tests` directory contains various test files including:
- `hello.c`: Simple C program that calculates and prints a square
- `print.c`: Support code for print functionality
- Other test cases for different language features

## 🗂️ Project Structure
- `src`: Java source files for visitors and compiler logic
- `bin`: Compiled classes and JavaCC grammar file
- `tests`: Test input files and generated outputs
- `syntaxtree`: AST node classes

## 🌳 Abstract Syntax Tree

The AST represents programs as a hierarchy of node types:

- **Program**  
  Top-level node containing `MainClass` and class declarations  
- **MainClass**  
  Entry point with `main` method  
- **Statement nodes**  
  - `Block`  
  - `If`  
  - `While`  
  - `Print`  
  - `Assign`  
- **Expression nodes**  
  - `Plus`  
  - `Minus`  
  - `Times`  
  - `LessThan`  
  - …etc.  
- **Type nodes**  
  - `IntegerType`  
  - `BooleanType`  
  - `IntArrayType`  
- **Lists**  
  - `StatementList`  
  - `ExpList`  
  - `VarDeclList`  
  - …etc.