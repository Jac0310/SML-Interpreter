# Simple (Small) Machine Language (SML)

Coursework examining reflection and dependency injection using Spring framework.

## The problem

This is an interpreter for a simple machine language — **SML**. 
The general form of a machine language instruction is:

```
	label instruction register-list
```
where

+ `label` - is the label for the line.<br/>
	Other instructions might “jump” to that label.
+ `instruction` - is the actual instruction.<br/>
	In SML, there are instructions for adding, multiplying and so on, 
	for storing and retrieving integers, and for conditionally branching to other labels 
	(like an `if` statement).
+ `register-list` - is the list of registers that the instruction manipulates.<br/>
	Registers are simple, integer, storage areas in computer memory, much like variables. 
	In SML, there are 32 registers, numbered 0, 1, ... , 31.
	
SML has the following instructions:

| Instruction  | Interpretation  |
|---------------|----------------|
| `L1 add r s1 s2`    |   Add the contents of registers `s1` and `s2` and store the result in register `r` |
| `L1 sub r s1 s2`    |   Subtract the contents of register `s2` from the contents of `s1` and store  the result  |
| |  in register `r` |
| `L1 mul r s1 s2` | Multiply the contents of registers `s1` and `s2` and store the result in register `r` |
| `L1 div r s1 s2` | Divide (Java integer division) the contents of register `s1` by the contents of register `s2` |
| |  and store the result in register `r`|
| `L1 out s1` | Print the contents of register `s1` on the console |
| `L1 lin r x` | Store integer `x` in register `r` |
| `L1 bnz s1 L2` | If the contents of register `s1` is not zero, then make the statement labeled `L2`|
| |  the next one to execute |

where

+ `L1` is any identifier — actually, any sequence of non-whitespace characters.
+ Each statement of a program must be labeled with a different identifier.
+ Each of `s1`, `s2`, and `r` is an integer in the range 0..31 and refers to one of the 32 registers 
	in the machine that executes language SML.
	
Here is an example of an SML program to compute factorial 6:

```
f0 lin 20 6
f1 lin 21 1
f2 lin 22 1
f3 mul 21 21 20
f4 sub 20 20 22
f5 bnz 20 f3
f6 out 21
```
Note that adjacent fields of an instruction (label, `opcode, and operands) are separated by whitespace.

Instructions of a program are executed in order (starting with the first one), unless the order is 
changed by execution of a `bnz` instruction. 
Execution terminates when its last instruction has been executed (and doesn’t change the order of execution).

What the interpreter does:

1. Reads the name of a file that contains the program from the command line (via `String... args`),
2. Reads the program from the file and translate it into an internal form,
3. Prints the program,
4. Executes the program, and
5. Prints the final value of the registers.


## Design of the program

The `Machine` classcontains exactly what is needed to execute
an SML program:

+ the *labels* defined in the program,
+ the program itself in an *internal form*,
+ the *registers* of the machine, and
+ the *program counter* — the number of the next instruction to execute.

Array like structures are used for the labels and the instructions of the machine because there is no limit 
to the size of an SML program. 
An array is used for the registers because there are always exactly 32 registers.

Method `Machine.execute`, is a typical *fetch-decode-execute* cycle that all machines have in some form. 
At each iteration, the instruction to execute is fetched, 
the program counter is incremented, and the instruction is executed. 
The order of the last two instructions is important, because an instruction (e.g. `bnz`) 
might change the program counter.

The `Translator` class contains the methods that read in the program and translate it into an internal form; 
very little error checking goes on here. 
For example, there is no checking for duplicate label definitions, 
for the use of a label that doesn’t exist, and for a register number not in the range 0..31.







