#!/bin/bash
javac Compiler.java
javac CodeGen_Visitor_Tiara.java
echo "compiled java files"
#cp tests/test.c tests/demo.c
cd tests; gcc -c -S print.c
cd ..;java Compiler < tests/demo_array.c > tests/demo_array.s
cd tests; gcc demo_array.s print.s -o demo_array; ./demo_array
