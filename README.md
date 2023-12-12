# Chat-App-with-GUI
This is a Multi Client Chat Platform (Group Chat) with GUI made using Java

 Run Server.java in a new terminal 1;
 Run Client.java in a new terminal 2 (client 1);
 Run Client.java again in a new terminal 3 (client 2);
 Can have as many clients as possible (minimum 2 clients);

 Enter username for each client, a GUI window should open when done.


Notes for understanding:

Thread:

- A thread is a sequence of instructions within a program that can be executed independently of other code.
- Threads share a memory space.
- When you launch an executable, it runs in a thread within a process.

Process:

- A computer program becomes a process when it is loaded from some store in the computer’s memory and begins execution
- A process can be executed by a single processor or a set of processors
- A processor description in memory contains information such as the program counter (which instruction is currently being executed), registers, variable stores, file handlers and signals, et-cetera.

To learn about Thread class and runnable interface:

https://www.youtube.com/watch?v=KOeIKuc5FPs

Concurrency(single-core):

- Running 2 or more programs in non overlapping time phases
- At any given time, only one process undergoes execution

!https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ff422832-9ed3-4f00-a2a6-f6ae7c812aab/Untitled.png

Parallel Execution (multi-core):

- tasks performed by a process are broken down into sub tasks and multiple CPUs execute each sub task at the same time
    - At any given time multiple(all) processes are executed
    - Found in systems having multicore processors
    

Stack Trace:

- The stack trace, also called as the backtrace, consists of a collection of stack records, which stores an application movement during execution
