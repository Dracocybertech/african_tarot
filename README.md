# Introduction
This repository contains a project of an African Tarot in Java. 

# Usage
## Compiling java files in the source repository
1. Clone the repo 
```git clone https://github.com/Dracocybertech/african_tarot.git```
2. Install Java latest JDK depending of your OS : https://www.oracle.com/fr/java/technologies/downloads/.
3. Go to the root of the projet
```cd Path/to/the/project/african_tarot```
You should have the **/src** and **/classes** folders under.
4. Compile the project
```
#Linux/MacOS
javac -classpath ./classes -sourcepath ./src -d ./classes ./src/*.java

#Windows
javac -classpath .\classes\ -sourcepath .\src\ -d .\classes\ .\src\*.java
```
In the folder **/classes**, there should be files with the extension .class, one for each java file in **/src**.

## Testing
This section explain how to run the differents tests with Visual Studio Code. Feel free to use other tools to compile and run them!
1. On Visual Studio Code, install the extension **Test Runner for Java** : https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-test.
2. A new icon Testing should appear in your Activity Bar. Click on it.
3. In the list of the tests, click on the play button of the tests you want to run.

# Installation
Once the java files have been compiled:

1. Go to the root of the projet
```cd Path/to/the/project/african_tarot```
2. Use the command: 
```#Linux/MacOS
java -classpath ./classes/ Main
#Windows
java -classpath .\classes\ Main
```

# Licence
Distributed under the MIT License. See LICENSE.txt for more information.
