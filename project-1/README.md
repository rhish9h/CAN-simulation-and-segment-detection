# Project 1

## Simulation of receiving CAN data and GPS data synced based on their time offset.

### How to run the Maven project:

Since this is a maven project, the recommended method to run this project is 
loading the maven project in an IDE.
1. Load maven project in IDE (preferably Intellij)
2. Run main from CANSimulation

Note:
1. Since the code uses carriage return to print on the same line to mimic
updating line, it's recommended to use a full screen terminal to run the code.
2. Pressing enter when prompted for files will work if the entire project is loaded,
however it might not load if program is run by cli without loading the project.
3. Please use absolute paths for newer test files for the simulation.

### How to run program using CLI:
1. cd project-1/src/main/java
2. javac CANSimulation.java
3. java CANSimulation

Note:
1. If the program is run via CLI, the default files will not work as the resources
directory will either not be present at all or may not be in the right place.
2. Please use absolute paths for all test files for the simulation via CLI.