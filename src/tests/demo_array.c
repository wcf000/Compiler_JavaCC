#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int arrayDemo(int s,int t){
    int a;
    int[] b;
    a = 0;
    b = new int[s];
    b[0]=100;
    b[t]=200;
    a = b[t];
    print(a);
    return a;
}

int main(int x){
    int a;
    a = arrayDemo(10,3);
    return 1;
}