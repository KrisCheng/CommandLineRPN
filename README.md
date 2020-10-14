# CommandLineRPN

A command line RPN (reverse polish notation) calculator based on Java, click here for what is [RPN](https://zh.wikipedia.org/wiki/%E9%80%86%E6%B3%A2%E5%85%B0%E8%A1%A8%E7%A4%BA%E6%B3%95).

#### Introduction

This is a command line RPN calculator, which receives typed command line strings as input for calculation and presents the stack result, detailed function points as follows:

* Available operators are +, -, *, /, sqrt, undo, clear.
* Operators pop their parameters off the stack, and push their results back onto the stack.
* The ‘clear’ operator removes all items from the stack.
* The ‘undo’ operator undoes the previous operation, ‘undo undo’ will undo the previous two operations.
* sqrt performs a square root on the top item from the stack.
* The ‘+’, ‘-’, ‘*’, ‘/’ operators perform addition, subtraction, multiplication and division respectively on the top two items from the stack.
* After processing an input string, the calculator displays the current contents of the stack as a space separated list.
* If an operator cannot find a sufficient number of parameters on the stack, a warning is displayed: ```operator <operator> (position: <pos>): insufficient parameters```. After displaying the warning, all further processing of the string terminates and the current state of the stack is displayed.

Demo as follows: 

```$xslt
Please type your RPN calculate string (end with Enter, type `quit` for quit the whole program):
5 2
stack: 5 2 
clear 2 sqrt
stack: 1.4142135624 
clear 1 2 3 4 5
stack: 1 2 3 4 5 
*
stack: 1 2 3 20 
undo
stack: 1 2 3 4 5 
clear 1 2 3 * 5 + * * 6 5
operator * (position: 9): insufficient parameters
stack: 11 
quit
```

#### Implementation

One major implementation concern is the UNDO operation, which means that you have to record the whole calculation process in case of UNDO operation,
so we need take an extra stack for record the instructions, the logic of this stack as follows:

* if input is numbers, push to stack and save the operation as null, when execute UNDO, just pop the valueStack
* if input is operator, push the operator and the first pop value of the valueStack(called as Instruction), when execute UNDO, judge the number of last UNDO process,
for single number, execute the reversed operation, for two numbers, execute the number, reversed operation and number. e.g. ( UNDO -5 is the same as execute  + 5 and repush the 5).

#### TODO

* more math operations, such as n!, Cos etc.
* other operations such as redo, swap etc.
* Command line --> UI.